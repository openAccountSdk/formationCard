package com.uyou.copenaccount.ui.pic.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.uyou.copenaccount.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by zdd on 2020/8/31.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, AutoFocusCallback {
    private static final String TAG = "CameraPreview";

    private int viewWidth = 0;
    private int viewHeight = 0;

    /**
     * 监听接口
     */
    private OnCameraStatusListener listener;

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private FocusView mFocusView;


    private HandlerThread mTakePhotoThread;
    private Handler mTakePhotoHandler;


    //创建一个PictureCallback对象，并实现其中的onPictureTaken方法
    private PictureCallback pictureCallback = new PictureCallback() {

        // 该方法用于处理拍摄后的照片数据
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (camera != null) {
                // 停止照片拍摄
                stop();
                // 调用结束事件
                if (null != listener) {
                    listener.onCameraStopped(data);
                }
            } else {
                listener.onCameraStopped(null);
            }
        }
    };

    private int mCameraId;

    public CameraPreview(Context context) {
        this(context, null);
    }

    // Preview类的构造方法
    public CameraPreview(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
        // 获得SurfaceHolder对象
        mHolder = getHolder();
        // 指定用于捕捉拍照事件的SurfaceHolder.Callback对象
        mHolder.addCallback(this);
        // 设置SurfaceHolder对象的类型
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setOnTouchListener(onTouchListener);

        mTakePhotoThread = new HandlerThread("takePhoto");
        mTakePhotoThread.start();
        mTakePhotoHandler = new Handler(mTakePhotoThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        try {
                            mCamera.takePicture(null, null, pictureCallback);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (null != listener) {
                                listener.onCameraStopped(null);
                            }
                        }
                        break;
                }
            }
        };

        if (!DeviceUtils.hasCamera(getContext())) {
            Toast.makeText(getContext(), "摄像头打开失败！没有检测到摄像头", Toast.LENGTH_SHORT).show();
        }
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void startPreview() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (mCamera == null) {
                    Toast.makeText(getContext(), "摄像头打开失败！请检查APP是否具有摄像头权限, 或检测摄像头是否已被其他应用使用", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 尝试停止预览
                try {
                    stop();
                } catch (Exception e) {
                }
                try {
                    // 更新相机参数
                    updateCameraParameters();
                    // 设置相机预览view
                    mCamera.setPreviewDisplay(mHolder);
                    // 开始预览
                    mCamera.startPreview();
                    // 设置
                    setFocus();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "摄像头打开失败！", Toast.LENGTH_SHORT).show();
                    // 释放手机摄像头
                    mCamera.release();
                    mCamera = null;
                }
            }
        });
    }

    // 在surface创建时激发
    public void surfaceCreated(SurfaceHolder holder) {
        // 获得Camera对象
        mCamera = getCameraInstance();
        //startPreview();
    }

    // 在surface的大小发生改变时激发
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        startPreview();
    }

    // 在surface销毁时激发
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 释放手机摄像头
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    //打开闪光灯
    public void openLight() {
        if (mCamera != null) {
            Camera.Parameters parameter;
            parameter = mCamera.getParameters();
            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameter);
        }
    }

    //关闭闪光灯
    public void offLight() {
        if (mCamera != null) {
            Camera.Parameters parameter;
            parameter = mCamera.getParameters();
            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameter);
        }
    }

    /**
     * 点击显示焦点区域
     */
    OnTouchListener onTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int width = mFocusView.getWidth();
                int height = mFocusView.getHeight();
                mFocusView.setX(event.getRawX() - (width / 2));
                mFocusView.setY(event.getRawY() - (height / 2));
                mFocusView.beginFocus();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                focusOnTouch(event);
            }
            return true;
        }
    };

    /**
     * 获取摄像头实例
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera c = null;
        try {
            int cameraCount = 0;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    c = Camera.open(camIdx);//打开后置摄像头
                    mCameraId = camIdx;
                }
            }
            if (c == null) {
                c = Camera.open(0);
                mCameraId = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    private void updateCameraParameters() {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            // 自动对焦
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            // 设置角度
            int degree = displayOrientation(getContext());
            mCamera.setDisplayOrientation(degree);
            // 设置后无效，mCamera.setDisplayOrientation方法有效
            params.set("rotation", degree);
            // 设置照片格式
            params.setPictureFormat(PixelFormat.JPEG);

            // 设置预览和图片大小
            Point destPoint = null;
            if (viewHeight > 0 && viewWidth > 0) {
                destPoint = new Point(viewWidth, viewHeight);
            } else {
                destPoint = new Point(DeviceUtils.getScreenWidth(getContext()), DeviceUtils.getScreenHeidth(getContext()));
            }

            // 相机默认比例大小和view大小相差过大, 获取最佳比例
            Camera.Size bestPreview = getBestPreview(params.getSupportedPreviewSizes(), params.getPreviewSize(), destPoint);
            params.setPreviewSize(bestPreview.width, bestPreview.height);

            Camera.Size bestPicture = getBestPreview(params.getSupportedPictureSizes(), params.getPictureSize(), destPoint);
            params.setPictureSize(bestPicture.width, bestPicture.height);

            try {
                mCamera.setParameters(params);
            } catch (Exception e) {
                try {
                    //遇到上面所说的情况，只能设置一个最小的预览尺寸
                    params.setPreviewSize(1920, 1080);
                    mCamera.setParameters(params);
                } catch (Exception e1) {
                    //到这里还有问题，就是拍照尺寸的锅了，同样只能设置一个最小的拍照尺寸
                    try {
                        params.setPictureSize(1920, 1080);
                        mCamera.setParameters(params);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    // 进行拍照，并将拍摄的照片传入PictureCallback接口的onPictureTaken方法
    public void takePicture() {
        if (mCamera != null) {
            mTakePhotoHandler.sendEmptyMessage(0);
        }
    }

    // 设置监听事件
    public void setOnCameraStatusListener(OnCameraStatusListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
    }

    public void start() {
        if (mCamera != null) {
            stop();
            try {
                mCamera.startPreview();
            } catch (Exception e) {

            }
        }
    }

    public void stop() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 相机拍照监听接口
     */
    public interface OnCameraStatusListener {
        // 相机拍照结束事件
        void onCameraStopped(byte[] data);
    }

//    @Override
//    protected void onMeasure(int widthSpec, int heightSpec) {
//        viewWidth = MeasureSpec.getSize(widthSpec);
//        viewHeight = MeasureSpec.getSize(heightSpec);
//        super.onMeasure(
//                MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));
//    }

    /**
     * 设置焦点和测光区域
     *
     * @param event
     */
    public void focusOnTouch(MotionEvent event) {
        if (mCamera == null) {
            return;
        }
        RelativeLayout relativeLayout = null;
        ViewParent parent = getParent();
        if (parent instanceof RelativeLayout) {
            relativeLayout = (RelativeLayout) parent;
        } else {
            parent = parent.getParent();
            if (parent instanceof RelativeLayout) {
                relativeLayout = (RelativeLayout) parent;
            }
        }
        if (relativeLayout != null) {
            int[] location = new int[2];
            relativeLayout.getLocationOnScreen(location);
            Rect focusRect = calculateTapArea(
                    mFocusView.getWidth(),
                    mFocusView.getHeight(),
                    1f,
                    event.getRawX(),
                    event.getRawY(),
                    location[0],
                    location[0] + relativeLayout.getWidth(),
                    location[1],
                    location[1] + relativeLayout.getHeight()
            );
            Rect meteringRect = calculateTapArea(
                    mFocusView.getWidth(),
                    mFocusView.getHeight(),
                    1.5f,
                    event.getRawX(),
                    event.getRawY(),
                    location[0],
                    location[0] + relativeLayout.getWidth(),
                    location[1],
                    location[1] + relativeLayout.getHeight()
            );

            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                if (parameters.getMaxNumFocusAreas() > 0) {
                    List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
                    focusAreas.add(new Camera.Area(focusRect, 1000));
                    parameters.setFocusAreas(focusAreas);
                }
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
                    meteringAreas.add(new Camera.Area(meteringRect, 1000));
                    parameters.setMeteringAreas(meteringAreas);
                }
                mCamera.setParameters(parameters);
                mCamera.autoFocus(this);
            } catch (Exception e) {

            }
        }
    }

    /**
     * 设置聚焦的图片
     *
     * @param focusView
     */
    public void setFocusView(FocusView focusView) {
        this.mFocusView = focusView;
    }

    /**
     * 设置自动聚焦，并且聚焦的圈圈显示在屏幕中间位置
     */
    public void setFocus() {
        if (mCamera == null) {
            return;
        }
        if (!mFocusView.isFocusing()) {
            try {
                mCamera.autoFocus(this);
                View view = (View) mFocusView.getParent();
                int height = view.getMeasuredHeight();
                int width = view.getMeasuredWidth();
                if (height == 0 || width == 0) {
                    height = DeviceUtils.getScreenHeidth(getContext());
                    width = DeviceUtils.getScreenWidth(getContext());
                }
                mFocusView.setX((width - mFocusView.getWidth()) / 2);
                mFocusView.setY((height - mFocusView.getHeight()) / 2);
                mFocusView.beginFocus();
            } catch (Exception e) {
            }
        }
    }


    /**
     * 计算焦点及测光区域
     *
     * @param focusWidth
     * @param focusHeight
     * @param areaMultiple
     * @param x
     * @param y
     * @param previewleft
     * @param previewRight
     * @param previewTop
     * @param previewBottom
     * @return Rect(left, top, right, bottom) : left、top、right、bottom是以显示区域中心为原点的坐标
     */
    private Rect calculateTapArea(int focusWidth, int focusHeight,
                                  float areaMultiple, float x, float y, int previewleft,
                                  int previewRight, int previewTop, int previewBottom) {
        int areaWidth = (int) (focusWidth * areaMultiple);
        int areaHeight = (int) (focusHeight * areaMultiple);
        int centerX = (previewleft + previewRight) / 2;
        int centerY = (previewTop + previewBottom) / 2;
        double unitx = ((double) previewRight - (double) previewleft) / 2000;
        double unity = ((double) previewBottom - (double) previewTop) / 2000;
        int left = clamp((int) (((x - areaWidth / 2) - centerX) / unitx),
                -1000, 1000);
        int top = clamp((int) (((y - areaHeight / 2) - centerY) / unity),
                -1000, 1000);
        int right = clamp((int) (left + areaWidth / unitx), -1000, 1000);
        int bottom = clamp((int) (top + areaHeight / unity), -1000, 1000);

        return new Rect(left, top, right, bottom);
    }

    private int clamp(int x, int min, int max) {
        if (x > max)
            return max;
        if (x < min)
            return min;
        return x;
    }

    public void onDestroy() {
        if (mTakePhotoThread != null) {
            try {
                mTakePhotoThread.quit();
            } catch (Exception e) {
            }
        }
    }

    private Camera.Size getBestPreview(List<Camera.Size> sizes, Camera.Size defaultSize, Point screenResolution) {
        int width = screenResolution.x;
        int height = screenResolution.y;

        if (sizes != null) {
            // 从大到小排序
            Collections.sort(sizes, new Comparator<Camera.Size>() {
                @Override
                public int compare(Camera.Size a, Camera.Size b) {
                    int aPixels = a.height * a.width;
                    int bPixels = b.height * b.width;
                    if (bPixels < aPixels) {
                        return -1;
                    }
                    if (bPixels > aPixels) {
                        return 1;
                    }
                    return 0;
                }
            });

            // 获取完全一样比例
            List<Camera.Size> candidates = new ArrayList<>();
            for (Camera.Size size : sizes) {
                if (size.width >= width && size.height >= height && size.width * height == size.height * width) {
                    // 比例相同
                    candidates.add(size);
                } else if (size.height >= width && size.width >= height && size.width * width == size.height * height) {
                    // 反比例
                    candidates.add(size);
                }
            }
            if (!candidates.isEmpty()) {
                Camera.Size size = candidates.get(candidates.size() - 1);
                return size;
            }

            // 没有找到完全一样的比例, 查找近似比例
            double screenAspectRatio = (screenResolution.x > screenResolution.y) ?
                    ((double) screenResolution.x / (double) screenResolution.y) :
                    ((double) screenResolution.y / (double) screenResolution.x);

            Camera.Size selectedSize = null;
            double selectedMinus = -1;
            int MIN_PREVIEW_PIXELS = 640 * 480;
            for (Camera.Size size : sizes) {
                if (size.height * size.width > MIN_PREVIEW_PIXELS) {
                    double aRatio = (size.width > size.height) ?
                            ((double) size.width / (double) size.height) :
                            ((double) size.height / (double) size.width);
                    double minus = Math.abs(aRatio - screenAspectRatio);

                    boolean selectedFlag = false;
                    if ((selectedMinus == -1 && minus <= 0.25f)
                            || (selectedMinus >= minus && minus <= 0.25f)) {
                        selectedFlag = true;
                    }
                    if (selectedFlag) {
                        selectedMinus = minus;
                        selectedSize = size;
                    }
                }
            }
            if (selectedSize != null) {
                return selectedSize;
            }
        }
        return defaultSize;
    }

    private int displayOrientation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                degrees = 0;
                break;
        }
        int result = (0 - degrees + 360) % 360;
        if (hasGingerbread()) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(mCameraId, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }
        }
        return result;
    }

    private boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public void setViewWidthAndHeight(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }
}