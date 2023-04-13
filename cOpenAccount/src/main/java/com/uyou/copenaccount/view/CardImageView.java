package com.uyou.copenaccount.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;


/**
 * Created by zdd on 2019/11/6.
 * <p>
 * Description:
 */
public class CardImageView extends AppCompatImageView {

    public CardImageView(Context context) {
        super(context);
        setScaleType(ScaleType.FIT_XY);
    }

    public CardImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.FIT_XY);
    }

    public CardImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSize * (364f / 660)), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
