package com.uyou.copenaccount.decoration;

import android.content.Context;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.uyou.copenaccount.utils.PhoneUtils;


/**
 * Created by zdd on 2019/4/22.
 * <p>
 * Description:
 */
public class RechargeDecoration extends RecyclerView.ItemDecoration {

    private int mMarginLeft;
    private int mMarginRight;
    private int mMarginTop;
    private int mMarginBottom;

    public RechargeDecoration(Context context) {
        mMarginLeft = PhoneUtils.dip2px(context, 7);
        mMarginRight = mMarginLeft;
        mMarginTop = PhoneUtils.dip2px(context, 5);
        mMarginBottom = mMarginTop;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mMarginLeft, mMarginTop, mMarginRight, mMarginBottom);
    }
}
