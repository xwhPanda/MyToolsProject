package com.com.tools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/23 14:00
 */
public class XWHSwipeRefreshLayout extends ViewGroup{
    private static final int[] LAYOUT_ATTRS = new int[]{
            android.R.attr.enabled
    };
    //加载图标默认背景
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private static final int ALPHA_ANIMATION_DURATION = 300;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float MAX_PROGRESS_ANGLE = .8f;
    private static final int MAX_ALPHA = 255;
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = INVALID_POINTER;
    private View mTarget;
    /* 滑动多少触发滚动 */
    private int mTouchSlop;
    /* 加载图标所在的view */
    private CircleImageView mCircleView;
    /* 加载图标 */
    private MaterialProgressDrawable mProgress;
    /* 圆圈的大小 */
    private int mCircleDiameter = 100;
    /* circleView的索引，在修改子view绘制顺序的时候用到 */
    private int mCircleViewIndex;
    /* circleView当前位置 */
    private int mCurrentTargetOffsetTop;
    /* circleView初始位置 */
    private int mOriginalOffsetTop;
    /* 正在刷新 */
    private boolean mRefreshing = false;
    /* 正在下拉 */
    private boolean mIsBeingDragged;
    /* 按下时Y坐标 */
    private float mInitialDownY;
    private Animation mAlphaStartAnimation;
    private Animation mAlphaMaxAnimation;
    private int STARTING_PROGRESS_ALPHA = 76;
    private float mTotalDragDistance = -1;
    private float mSpinnerFinalOffset;
    private OnRefreshListener mListener;
    /* 是否可以刷新 */
    private boolean refreshAble;
    /* 是否可以加载 */
    private boolean loadAble;

    public XWHSwipeRefreshLayout(Context context) {
        super(context);
    }

    public XWHSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        createProgressView();
        setChildrenDrawingOrderEnabled(true);
        mOriginalOffsetTop = mCurrentTargetOffsetTop = -mCircleDiameter;
        mTotalDragDistance = mSpinnerFinalOffset = DEFAULT_CIRCLE_TARGET;
        final TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
        setEnabled(a.getBoolean(0, true));
        a.recycle();
    }


    /**
     * 创建加载刷新view
     */
    private void createProgressView(){
        mCircleView = new CircleImageView(getContext(),CIRCLE_BG_LIGHT);
        mProgress = new MaterialProgressDrawable(getContext(),this);
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        mCircleView.setImageDrawable(mProgress);
        mCircleView.setVisibility(View.GONE);
        addView(mCircleView);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        mListener = onRefreshListener;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null)
            ensureTarget();
        if (mTarget == null)
            return;
        mTarget.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),MeasureSpec.EXACTLY));
        mCircleView.measure(MeasureSpec.makeMeasureSpec(mCircleDiameter,MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mCircleDiameter,MeasureSpec.EXACTLY));
        for(int index = 0 ; index < getChildCount(); index++){
            if (getChildAt(index) == mCircleView){
                mCircleViewIndex = index;
                break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();

        if (getChildCount() == 0)
            return;
        if (mTarget == null)
            ensureTarget();
        if (mTarget == null)
            return;
        final View child = mTarget;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft,childTop, childLeft + childWidth, childTop + childHeight);
        int circleWidth = mCircleView.getMeasuredWidth();
        int circleHeight = mCircleView.getMeasuredHeight();
        mCircleView.layout((width / 2 - circleWidth / 2), mCurrentTargetOffsetTop,
                (width / 2 + circleWidth / 2), mCurrentTargetOffsetTop + circleHeight);
    }

    /**
     * 这个方法有点意思，用来改变子view的绘制顺序
     * 现在这个效果是先绘制mTarget，再绘制mCircleView
     * @param childCount
     * @param i
     * @return
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mCircleViewIndex < 0) {
            return i;
        } else if (i == childCount - 1) {
            return mCircleViewIndex;
        } else if (i >= mCircleViewIndex) {
            return i + 1;
        } else {
            return i;
        }
    }

    private void ensureTarget(){
        if (mTarget == null)
            for (int i = 0;i < getChildCount(); i++){
                View child = getChildAt(i);
                if (!child.equals(mCircleView)){
                    mTarget = child;
                    break;
                }
            }
    }

    private boolean canChildScrollUp(){
        if (Build.VERSION.SDK_INT < 14){
            if (mTarget instanceof AbsListView){
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0
                        || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            }else
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
        }else {
            return ViewCompat.canScrollVertically(mTarget,-1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex;
        //如果下拉被禁用、mTarget没有到达顶部、正在刷新
        //不拦截，不处理点击事件，处理权交还mTarget
        if (!isEnabled() || canChildScrollUp() || mRefreshing || !refreshAble) {
            return false;
        }
       switch (action){
           case MotionEvent.ACTION_DOWN:
               setTargetOffsetTopAndBottom(mCurrentTargetOffsetTop - mCircleView.getTop(),true);
               mActivePointerId = ev.getPointerId(0);
               mIsBeingDragged = false;
               pointerIndex = ev.findPointerIndex(mActivePointerId);
               if (pointerIndex < 0)
                   return false;
               mInitialDownY = ev.getY(pointerIndex);
               break;
           case MotionEvent.ACTION_MOVE:
               if (mActivePointerId == INVALID_POINTER)
                   return false;
               pointerIndex = ev.findPointerIndex(mActivePointerId);
               if (pointerIndex < 0)
                   return false;
               final float y = ev.getY(pointerIndex);
               startDragging(y);
               break;
           case MotionEvent.ACTION_UP:
           case MotionEvent.ACTION_CANCEL:
               mIsBeingDragged = false;
               mActivePointerId = INVALID_POINTER;
               break;
       }
        return mIsBeingDragged;
    }

    /* 重写这个方法，不然mTarget获取事件后，父view再也拿不到事件
     * 造成mTarget往上滑动一段，然后下拉，不会马上刷新，除非松开手指，重新往下拉 */
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private void startDragging(float y){
        float yDiff = y - mInitialDownY;
        if (yDiff > mTouchSlop && !mIsBeingDragged){
            mIsBeingDragged = true;
            mProgress.setAlpha(STARTING_PROGRESS_ALPHA);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        int pointerIndex = -1;
        if (!isEnabled() || canChildScrollUp() || mRefreshing || !refreshAble) {
            return false;
        }
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                mIsBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE: {
                pointerIndex = event.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = event.getY(pointerIndex);
                startDragging(y);
                if (mIsBeingDragged) {
                    /* circleView下拉的距离 */
                    final float overScrollTop = y - mInitialDownY - mTouchSlop;
                    if (overScrollTop > 0) {
                        moveSpinner(overScrollTop);
                    } else
                        return false;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                pointerIndex = event.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0){
                    return false;
                }
                if (mIsBeingDragged){
                    final float y = event.getY(pointerIndex);
                    final float overScrollTop = y - mInitialDownY - mTouchSlop;
                    mIsBeingDragged = false;
                    finishSpinner(overScrollTop);
                }
                mActivePointerId = INVALID_POINTER;
                return false;
            case MotionEvent.ACTION_CANCEL:
                return false;
        }
        return true;
    }

    private void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
        mCircleView.bringToFront();
        mCircleView.setVisibility(View.VISIBLE);
        ViewCompat.offsetTopAndBottom(mCircleView, offset);
        mCurrentTargetOffsetTop = mCircleView.getTop();
        if (requiresUpdate && Build.VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    private void  finishSpinner(float overScrollTop) {
        if (overScrollTop > mTotalDragDistance){
            setRefreshing(true);
        }else {
            mRefreshing = false;
            mProgress.setStartEndTrim(0f,0f);
            mProgress.showArrow(false);
            mCircleView.setVisibility(View.GONE);
        }
    }

    public void setRefreshing(boolean refreshing){
        if (mRefreshing != refreshing){
            ensureTarget();
            mRefreshing = refreshing;
            if (mRefreshing){//刷新
                if (mListener != null)
                    mListener.onRefresh();
                mCurrentTargetOffsetTop = mCircleView.getTop();
                mAnimateToCorrectPosition.setDuration(1000);
                mAnimateToCorrectPosition.setInterpolator(new DecelerateInterpolator(2f));
                mCircleView.clearAnimation();
                mCircleView.startAnimation(mAnimateToCorrectPosition);
            }else{
                mCircleView.offsetTopAndBottom(-mCurrentTargetOffsetTop - mCircleView.getHeight());
                mCurrentTargetOffsetTop = mCircleView.getTop();
                mCircleView.setVisibility(View.GONE);
            }
        }
    }

    private final Animation mAnimateToCorrectPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int target = (int)((mTouchSlop - mCurrentTargetOffsetTop) * interpolatedTime);
            setTargetOffsetTopAndBottom(target, false);
        }
    };

    private void moveSpinner(float overScrollTop){
        float originalDragPercent = overScrollTop / mTotalDragDistance;
        float targetY = overScrollTop * (1 - originalDragPercent);
        if (targetY >= 0)
            setTargetOffsetTopAndBottom((int)(targetY - mCurrentTargetOffsetTop), true);
    }

    private boolean isAnimationRunning(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    private void startProgressAlphaStartAnimation() {
        mAlphaStartAnimation = startAlphaAnimation(mProgress.getAlpha(), STARTING_PROGRESS_ALPHA);
    }

    private void startProgressAlphaMaxAnimation() {
        mAlphaMaxAnimation = startAlphaAnimation(mProgress.getAlpha(), MAX_ALPHA);
    }

    private Animation startAlphaAnimation(final int startingAlpha, final int endingAlpha) {
        Animation alpha = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                mProgress.setAlpha(
                        (int) (startingAlpha + ((endingAlpha - startingAlpha) * interpolatedTime)));
            }
        };
        alpha.setDuration(ALPHA_ANIMATION_DURATION);
        mCircleView.setAnimationListener(null);
        mCircleView.clearAnimation();
        mCircleView.startAnimation(alpha);
        return alpha;
    }

    public void setRefreshAble(boolean refreshAble){
        this.refreshAble = refreshAble;
    }

    public boolean getRefreshAble(){
        return refreshAble;
    }

    public void setLoadAble(boolean loadAble){
        this.loadAble = loadAble;
    }

    public boolean getLoadAble(){
        return loadAble;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}
