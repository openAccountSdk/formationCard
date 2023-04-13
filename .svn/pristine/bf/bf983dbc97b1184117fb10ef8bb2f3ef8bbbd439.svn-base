package com.uyou.copenaccount.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.model.SelectItem;
import com.uyou.copenaccount.utils.PhoneUtils;
import com.uyou.copenaccount.xpopup.core.BasePopupView;
import com.uyou.copenaccount.xpopup.core.BottomPopupView;

import java.util.List;

/**
 * Created by zdd on 2019/4/1.
 * <p>
 * Description: 通用底部选择 dialog
 */
public class SelectMethodDialog extends BottomPopupView {

    private List<SelectItem> items;
    private Context mContext;

    private OnDismissListener onDismissListener;

    public SelectMethodDialog(@NonNull Context context, List<SelectItem> items, OnDismissListener listener) {
        super(context);
        this.items = items;
        this.mContext = context;
        this.onDismissListener = listener;
        enableGesture(false);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_dialog_select_method;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        boolean hasCancelItem = false;
        if (items != null) {
            ViewGroup parent = bottomPopupContainer.findViewById(R.id.common_layout_container);
            int padding = PhoneUtils.dip2px(mContext, 10);
            for (int i = 0; i < items.size(); i++) {
                final SelectItem item = items.get(i);
                if (item != null) {
                    if (item.getType() == SelectItem.TYPE_CANCEL) {
                        hasCancelItem = true;
                        bottomPopupContainer.findViewById(R.id.common_txt_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (item.getListener() != null) {
                                    item.getListener().onClick(SelectMethodDialog.this, v);
                                }
                            }
                        });
                    } else {
                        TextView tv = new TextView(mContext);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (item.getListener() != null) {
                                    item.getListener().onClick(SelectMethodDialog.this, v);
                                }
                            }
                        });
                        tv.setText(item.getTitle());
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(0, padding, 0, padding);
                        parent.addView(tv);
                    }
                }
            }
        }
        if (!hasCancelItem) {
            bottomPopupContainer.findViewById(R.id.common_txt_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectMethodDialog.this.dismiss();
                }
            });
        }
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public interface OnClickListener {
        void onClick(BasePopupView popupView, View view);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
