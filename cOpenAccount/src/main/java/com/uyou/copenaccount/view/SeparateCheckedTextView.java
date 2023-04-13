package com.uyou.copenaccount.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;


/**
 * Created by zdd on 2020/6/19.
 * <p>
 * Description:
 */
public class SeparateCheckedTextView extends AppCompatCheckedTextView {

    private Paint mPaint;
    private int mSeparateColor;

    public SeparateCheckedTextView(Context context) {
        super(context);
    }

    public SeparateCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeparateCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSeparateColor != 0) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            if (width > 0 && height > 0) {
                canvas.save();
                canvas.drawLine(0, height - 1, width, height, getSeparatePaint());
                canvas.restore();
            }
        }
    }

    private Paint getSeparatePaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
        }
        mPaint.setColor(mSeparateColor);
        return mPaint;
    }

    public void setSeparateColor(@ColorInt int separateColor) {
        this.mSeparateColor = separateColor;
    }
}
