package com.uyou.copenaccount.xpopup.core;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.xpopup.animator.PopupAnimator;
import com.uyou.copenaccount.xpopup.enums.PopupStatus;
import com.uyou.copenaccount.xpopup.util.XPopupUtils;
import com.uyou.copenaccount.xpopup.widget.SmartDragLayout;


/**
 * Description: 在底部显示的Popup
 * Create by lxj, at 2018/12/11
 */
public class BottomPopupView extends BasePopupView {
    protected SmartDragLayout bottomPopupContainer;
    boolean enableGesture = true; //是否启用手势交互，默认启用

    public BottomPopupView(@NonNull Context context) {
        super(context);
        bottomPopupContainer = findViewById(R.id.bottomPopupContainer);
        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), bottomPopupContainer, false);
        bottomPopupContainer.addView(contentView);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout.common_xpopup_bottom_popup_view;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        bottomPopupContainer.enableGesture(enableGesture);
        bottomPopupContainer.dismissOnTouchOutside(popupInfo.isDismissOnTouchOutside);
        bottomPopupContainer.hasShadowBg(popupInfo.hasShadowBg);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight());

        bottomPopupContainer.setOnCloseListener(new SmartDragLayout.OnCloseListener() {
            @Override
            public void onClose() {
                doAfterDismiss();
            }

            @Override
            public void onOpen() {
                BottomPopupView.super.doAfterShow();
            }
        });

        bottomPopupContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void doAfterShow() {
        //do nothing self.
    }

    @Override
    public void doShowAnimation() {
        if (enableGesture) {
            bottomPopupContainer.open();
        } else {
            super.doShowAnimation();
        }
    }

    @Override
    public void doDismissAnimation() {
        if (enableGesture) {
            bottomPopupContainer.close();
        } else {
            super.doDismissAnimation();
        }
    }

    /**
     * 动画是跟随手势发生的，所以不需要额外的动画器，因此动画时间也清零
     *
     * @return
     */
    @Override
    public int getAnimationDuration() {
        return enableGesture ? 0 : super.getAnimationDuration();
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        // 移除默认的动画器
        return enableGesture ? null : super.getPopupAnimator();
    }

    @Override
    public void dismiss() {
        if (enableGesture) {
            if (popupStatus == PopupStatus.Dismissing) return;
            popupStatus = PopupStatus.Dismissing;
            // 关闭Drawer，由于Drawer注册了关闭监听，会自动调用dismiss
            bottomPopupContainer.close();
        } else {
            super.dismiss();
        }
    }

    /**
     * 具体实现的类的布局
     *
     * @return
     */
    protected int getImplLayoutId() {
        return 0;
    }

    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? XPopupUtils.getWindowWidth(getContext())
                : popupInfo.maxWidth;
    }

    public BottomPopupView enableGesture(boolean enableGesture) {
        this.enableGesture = enableGesture;
        return this;
    }
}
