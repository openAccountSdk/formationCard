package com.uyou.copenaccount.view.rv;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.uyou.copenaccount.view.rv.listener.OnOpenRefreshBeforeListener;
import com.uyou.copenaccount.view.rv.refresh.AppBarStateChangeListener;
import com.uyou.copenaccount.view.rv.refresh.ArrowRefreshHeader;


/**
 * Created by 郑冬冬 on 2017/10/29.
 */

public class PullRecyclerView extends RecyclerView {

    private boolean isSupportPullToRefresh = false;

    private float mLastY = -1;
    private static final float DRAG_RATE = 3;
    private AppBarStateChangeListener.State appbarState = AppBarStateChangeListener.State.EXPANDED;
    private ArrowRefreshHeader mRefreshHeader;

    private OnOpenRefreshBeforeListener mOpenListener;

    public PullRecyclerView(Context context) {
        super(context);
    }

    public PullRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSupportPullToRefresh) {
            Adapter adapter = getAdapter();
            if (adapter != null && adapter instanceof BaseQuickAdapter) {
                BaseQuickAdapter wrapAdapter = (BaseQuickAdapter) adapter;
                if (mRefreshHeader == null) mRefreshHeader = wrapAdapter.getRefreshHeader();
                if (mRefreshHeader != null) {
                    if (mLastY == -1) {
                        mLastY = ev.getRawY();
                    }
                    switch (ev.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mLastY = ev.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            final float deltaY = ev.getRawY() - mLastY;
                            mLastY = ev.getRawY();

                            if (isOnTop()) {
                                if (mOpenListener == null || (mOpenListener != null && mOpenListener.onOpenRefreshBefore(deltaY / DRAG_RATE))) {
                                    if (wrapAdapter.isRefreshEnable() && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                                        mRefreshHeader.onMove(deltaY / DRAG_RATE);
                                        if (mRefreshHeader.getVisibleHeight() > 0 && mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING) {
                                            return false;
                                        }
                                    }
                                }
                            }
                            break;
                        default:
                            mLastY = -1; // reset
                            if (isOnTop()) {
                                if (wrapAdapter.isRefreshEnable() && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                                    if (mRefreshHeader.releaseAction()) {
                                        if (!wrapAdapter.isLoading()) {
                                            BaseQuickAdapter.RequestRefreshListener mRequestRefreshListener = wrapAdapter.getRequestRefreshListener();
                                            if (mRequestRefreshListener != null) {
                                                wrapAdapter.setLoading();
                                                mRequestRefreshListener.onRefreshRequested();
                                            }
                                        } else {
                                            mRefreshHeader.refreshComplete();
                                        }
                                    }
                                }

                            }
                            if (canScrollVertically(1)) {
                                if (mOpenListener != null) {
                                    mOpenListener.onOpenReset();
                                }
                            }
                            break;
                    }
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setOnOpenRefreshListener(OnOpenRefreshBeforeListener listener) {
        this.mOpenListener = listener;
    }

    public boolean isLoading() {
        Adapter adapter = getAdapter();
        if (adapter != null && adapter instanceof BaseQuickAdapter) {
            BaseQuickAdapter wrapAdapter = (BaseQuickAdapter) adapter;
            return wrapAdapter.isLoading();
        }
        return false;
    }

    private boolean isOnTop() {
        if (mRefreshHeader.getParent() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null && adapter instanceof BaseQuickAdapter) {
            isSupportPullToRefresh = true;
            mRefreshHeader = ((BaseQuickAdapter) adapter).getRefreshHeader();
        }
        super.setAdapter(adapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //解决和CollapsingToolbarLayout冲突的问题
        AppBarLayout appBarLayout = null;
        ViewParent p = getParent();
        while (p != null) {
            if (p instanceof CoordinatorLayout) {
                break;
            }
            p = p.getParent();
        }
        if (p instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) p;
            final int childCount = coordinatorLayout.getChildCount();
            for (int i = childCount - 1; i >= 0; i--) {
                final View child = coordinatorLayout.getChildAt(i);
                if (child instanceof AppBarLayout) {
                    appBarLayout = (AppBarLayout) child;
                    break;
                }
            }
            if (appBarLayout != null) {
                appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                    @Override
                    public void onStateChanged(AppBarLayout appBarLayout, State state) {
                        appbarState = state;
                    }
                });
            }
        }
    }
}
