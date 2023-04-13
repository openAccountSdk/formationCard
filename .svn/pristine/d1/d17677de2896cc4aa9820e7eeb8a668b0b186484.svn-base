package com.uyou.copenaccount.view.sign;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.utils.PhoneUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zdd on 2019/9/24.
 * <p>
 * Description:
 */
public class SignView extends View {

    private Paint mSignPaint;
    private Paint mBgDashPaint;
    private Paint mBgPaint;
    private int bgRadius;

    private TouchGestureDetector mTouchGestureDetector;
    private Path mCurrentPath;
    private List<Path> mPathList = new ArrayList<>();
    private float mLastX;
    private float mLastY;

    public SignView(Context context) {
        this(context, null);
    }

    public SignView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta =
                context.obtainStyledAttributes(attrs, R.styleable.ucommon_sv_SignView, defStyleAttr, 0);
        int colorBgDash = Color.parseColor("#90BA21");
        try {
            colorBgDash =
                    ta.getColor(R.styleable.ucommon_sv_SignView_ucommon_sv_bg_dash_color, colorBgDash);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
        // 签字画笔
        mSignPaint = new Paint();
        mSignPaint.setColor(Color.BLACK);
        mSignPaint.setStrokeWidth(PhoneUtils.dip2px(getContext(), 6));
        mSignPaint.setAntiAlias(true);
        mSignPaint.setStyle(Paint.Style.STROKE);
        mSignPaint.setStrokeCap(Paint.Cap.ROUND);

        bgRadius = PhoneUtils.dip2px(getContext(), 5);
        // 背景虚线画笔
        int strokeWidth = PhoneUtils.dip2px(getContext(), 1);
        int dashWidth = PhoneUtils.dip2px(getContext(), 4);
        mBgDashPaint = new Paint();
        mBgDashPaint.setColor(colorBgDash);
        mBgDashPaint.setAntiAlias(true);
        mBgDashPaint.setStrokeWidth(strokeWidth);
        mBgDashPaint.setStyle(Paint.Style.STROKE);
        mBgDashPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgDashPaint.setPathEffect(new DashPathEffect(new float[]{dashWidth, dashWidth / 2}, 0));
        // 背景画笔
        mBgPaint = new Paint();
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);


        // 由手势识别器处理手势
        mTouchGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {

            @Override
            public void onScrollBegin(MotionEvent e) {
                // 滑动开始
                mCurrentPath = new Path();
                mPathList.add(mCurrentPath);
                mCurrentPath.moveTo(e.getX(), e.getY());
                mLastX = e.getX();
                mLastY = e.getY();
                invalidate();
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // 滑动中
                mCurrentPath.quadTo(
                        mLastX,
                        mLastY,
                        (e2.getX() + mLastX) / 2,
                        (e2.getY() + mLastY) / 2); // 使用贝塞尔曲线 让涂鸦轨迹更圆滑
                mLastX = e2.getX();
                mLastY = e2.getY();
                invalidate();
                return true;
            }

            @Override
            public void onScrollEnd(MotionEvent e) {
                // 滑动结束
                mCurrentPath.quadTo(
                        mLastX,
                        mLastY,
                        (e.getX() + mLastX) / 2,
                        (e.getY() + mLastY) / 2);
                mCurrentPath = null; // 轨迹结束
                invalidate();
            }

        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mTouchGestureDetector != null) {
            boolean consumed = mTouchGestureDetector.onTouchEvent(event); // 由手势识别器处理手势
            if (!consumed) {
                return super.dispatchTouchEvent(event);
            }
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.right = width;
        rectF.top = 0;
        rectF.bottom = height;
        // 绘制背景
        canvas.drawRoundRect(rectF, bgRadius, bgRadius, mBgPaint);
        // 绘制背景虚线
        canvas.drawRoundRect(rectF, bgRadius, bgRadius, mBgDashPaint);
        // 绘制path
        if (mPathList != null) {
            for (Path path : mPathList) {
                if (path != null) {
                    canvas.drawPath(path, mSignPaint);
                }
            }
        }
    }

    /**
     * 清除
     */
    public void clear() {
        mPathList.clear();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    /**
     * 保存
     *
     * @return 保存地址
     */
    public String save() {
        String savePath = null;
        if (mPathList != null || mPathList.size() > 0) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            if (width > 0 && height > 0) {
                Bitmap bitmap = Bitmap.createBitmap(330, 400, Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(Color.WHITE);

                float scaleX = 330f / width;
                float scaleY = 400f / height;

                Matrix matrix = new Matrix();
                matrix.setScale(scaleX, scaleY);

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStrokeWidth(PhoneUtils.dip2px(getContext(), 6) * scaleX);

                Path p = null;
                for (Path path : mPathList) {
                    if (path != null) {
                        p = new Path(path);
                        p.transform(matrix);
                        canvas.drawPath(p, paint);
                    }
                }
                canvas.save();

                File parentFile = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }

                File file = new File(parentFile, System.currentTimeMillis() + ".jpg");
                String path = file.getAbsolutePath();

                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    savePath = file.getAbsolutePath();
                    bitmap.recycle();
                    bitmap = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return savePath;
    }

    /**
     * 是否绘制
     */
    public boolean isPainted() {
        return mPathList == null ? false : mPathList.size() > 0;
    }

}
