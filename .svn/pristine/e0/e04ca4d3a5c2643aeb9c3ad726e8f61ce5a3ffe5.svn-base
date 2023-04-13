package com.uyou.copenaccount.ui.pic;

import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.ui.pic.view.CameraPreview;
import com.uyou.copenaccount.ui.pic.view.CameraProgressDialog;
import com.uyou.copenaccount.ui.pic.view.FocusView;
import com.uyou.copenaccount.utils.AppConfigs;
import com.uyou.copenaccount.utils.BitmapUtils;
import com.uyou.copenaccount.utils.DeviceUtils;
import com.uyou.copenaccount.view.crop.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by zdd on 2019/7/12.
 * <p>
 * Description: 自定义相机(包含裁切)
 */
public class CustomCameraActivity extends Activity implements CameraPreview.OnCameraStatusListener, SensorEventListener {

    private Unbinder mUnBinder;

    @BindView(R2.id.com_layout_take_photo)
    LinearLayout layoutTakePhoto;

    @BindView(R2.id.com_layout_card_and_sim)
    LinearLayout imageCardAndSim; //身份证正面和卡板
    @BindView(R2.id.front_horizontal)
    ImageView imageFrontHorizontal; //身份证正面横向
    @BindView(R2.id.obverse_horizontal)
    ImageView imageObverseHorizontal; //身份证反面横向
    @BindView(R2.id.com_image_card_sim)
    ImageView imageSim;
    @BindView(R2.id.com_image_card_and_person)
    ImageView imageCardAndPerson;
    @BindView(R2.id.com_layout_camera_container)
    ViewGroup layoutCamera;
    @BindView(R2.id.com_view_focus_view)
    FocusView viewFocusView;

    @BindView(R2.id.com_layout_crop)
    LinearLayout layoutCrop;
    @BindView(R2.id.com_view_crop_view)
    CropImageView viewCropView;
    @BindView(R2.id.com_txt_ratio_16_9)
    TextView imageRatio169;
    @BindView(R2.id.com_txt_ratio_9_16)
    TextView imageRatio916;

    @BindView(R2.id.com_image_crop)
    View btnCrop;

    private CameraPreview viewCameraPreview;


    private SensorManager mSensorManager;
    private Sensor mAccel;

    /**
     * 1身份证正面, 2身份证反面, 3人证合一, 其他默认拍正方形图片
     */
    private int mType;

    private Handler mDealHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        dismissProgressDialog();
                        Object bb = msg.obj;
                        if (bb instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) bb;
                            //准备截图
                            viewCropView.setImageBitmap(bitmap);
                        }
                        break;
                    case 2:
                        dismissProgressDialog();
                        AppConfigs.showToast(getContext(), "图片获取失败");
                        break;
                    case 3:
                        dismissProgressDialog();
                        String path = "";
                        if (msg.obj != null) {
                            path = (String) msg.obj;
                        }
                        Intent intent = getIntent();
                        intent.putExtra("path", path);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case 4:
                        dismissProgressDialog();
                        AppConfigs.showToast(getContext(), "图片压缩失败, 请稍后重试");
                        closeCrop();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.com_activity_custom_camera);
        mUnBinder = ButterKnife.bind(this);


        viewCameraPreview = new CameraPreview(getContext());
        // 限定16:9
        int screenWidth = DeviceUtils.getScreenWidth(getContext());
        int width = screenWidth;
        int height = screenWidth * 16 / 9;
        ViewGroup.LayoutParams layoutParams = layoutCamera.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        layoutCamera.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.gravity = Gravity.CENTER;
        viewCameraPreview.setLayoutParams(params);
        viewCameraPreview.setViewWidthAndHeight(width, height);
        layoutCamera.addView(viewCameraPreview, 0);


        //截图样式
        viewCropView.setHandleSizeInDp(7);
        viewCropView.setTouchPaddingInDp(16);
        viewCropView.setMinFrameSizeInPx(380);

        mType = getIntent().getIntExtra(ACTION_DATA, 0);
        switch (mType) {
            case 1://正面
            case 4:
                imageFrontHorizontal.setVisibility(View.VISIBLE);
                viewCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                break;
            case 2: //反面
                imageObverseHorizontal.setVisibility(View.VISIBLE);
                viewCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                break;
            case 3://人证合一
                imageCardAndPerson.setVisibility(View.VISIBLE);
                viewCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                break;
            case 5:
                imageCardAndSim.setVisibility(View.VISIBLE); //身份证与卡板
                viewCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                break;
            default:
                imageRatio169.setVisibility(View.GONE);
                imageRatio916.setVisibility(View.GONE);
                viewCropView.setCropMode(CropImageView.CropMode.SQUARE);
                break;
        }

        viewCameraPreview.setFocusView(viewFocusView);
        viewCameraPreview.setOnCameraStatusListener(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * 拍照
     */
    @OnClick(R2.id.com_image_take_photo)
    void takePhoto() {
        showProgressDialog();
        if (viewCameraPreview != null) {
            viewCameraPreview.takePicture();
        }
    }

    @Override
    public void onCameraStopped(byte[] data) {
        if (data == null) {
            Message message = new Message();
            message.what = 2;
            mDealHandler.sendMessage(message);
            showCropperLayout();
            return;
        }
        new Thread(() -> {
            try {
                // 创建图像
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                BitmapFactory.decodeByteArray(data, 0, data.length, options);

                int width = options.outWidth;
                int height = options.outHeight;
                int inSampleSize = 1;

                int reqHeight = DeviceUtils.getScreenHeidth(getContext());
                int reqWidth = DeviceUtils.getScreenWidth(getContext());

                if (reqWidth <= 800) {
                    reqHeight = (int) (reqHeight * 1.2f);
                    reqWidth = (int) (reqWidth * 1.2f);
                } else if (reqWidth > 2400) {
                    int count = 0;
                    while (reqWidth > 2400) {
                        reqHeight = (int) (reqHeight * 0.8f);
                        reqWidth = (int) (reqWidth * 0.8f);
                        count++;
                        if (count > 10) {
                            reqHeight = 1760 * reqHeight / reqWidth;
                            reqWidth = 1760;
                            break;
                        }
                    }
                }


                if (width > reqWidth || height > reqHeight) {
                    int widthRatio = Math.round((float) width / (float) reqWidth);
                    int heightRatio = Math.round((float) height / (float) reqHeight);
                    inSampleSize = widthRatio < heightRatio ? widthRatio : heightRatio;
                }

                options.inJustDecodeBounds = false;
                options.inSampleSize = inSampleSize;


                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

                // 检测图片方向
                try {
                    ExifInterface exifInterface = new ExifInterface(new ByteArrayInputStream(data));
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int rotationDegrees = 0;
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotationDegrees = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotationDegrees = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotationDegrees = 270;
                            break;
                    }
                    if (rotationDegrees != 0) {
                        bitmap = BitmapUtils.rotateBitmap(bitmap, rotationDegrees);
                    }
                } catch (Exception e) {
                }

                Message message = new Message();
                message.what = 1;
                message.obj = bitmap;
                mDealHandler.sendMessage(message);
            } catch (Exception e) {
                Message message = new Message();
                message.what = 2;
                mDealHandler.sendMessage(message);
            }
        }).start();
        showCropperLayout();
    }

    /**
     * 切换16:9
     */
    @OnClick(R2.id.com_txt_ratio_16_9)
    void ratio169() {
        viewCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
    }

    /**
     * 旋转
     */
    @OnClick(R2.id.com_txt_rotate)
    void rotate() {
        viewCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
    }

    /**
     * 切换9:16
     */
    @OnClick(R2.id.com_txt_ratio_9_16)
    void ratio916() {
        viewCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
    }

    /**
     * 裁切
     */
    @OnClick(R2.id.com_image_crop)
    void cropPic() {
        if (btnCrop != null) {
            btnCrop.setEnabled(false);
        }
        showProgressDialog();
        new Thread(() -> {
            //获取截图并旋转90度
            Bitmap croppedBitmap = viewCropView.getCroppedBitmap();

            if (croppedBitmap != null) {
            }
            // 图像名称
            String filename = System.currentTimeMillis() + ".jpg";

            File fileCache = new File(getCacheDir() + File.separator + "cachePic");
            if (!fileCache.exists()) {
                fileCache.mkdirs();
            }

            File file = new File(fileCache, filename);

            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream);
                if (croppedBitmap != null && !croppedBitmap.isRecycled()) {
                    croppedBitmap.recycle();
                    croppedBitmap = null;
                }

                Message message = new Message();
                message.what = 3;
                message.obj = file.getAbsolutePath();
                mDealHandler.sendMessage(message);
            } catch (Exception e) {
                Message message = new Message();
                message.what = 4;
                mDealHandler.sendMessage(message);
            }
        }).start();
    }

    private void showTakePhotoLayout() {
        layoutTakePhoto.setVisibility(View.VISIBLE);
        layoutCrop.setVisibility(View.GONE);
        if (viewCameraPreview != null) {
            viewCameraPreview.start();   //继续启动摄像头
        }
    }

    private void showCropperLayout() {
        runOnUiThread(() -> {
            if (btnCrop != null) {
                btnCrop.setEnabled(true);
            }
            layoutTakePhoto.setVisibility(View.GONE);
            layoutCrop.setVisibility(View.VISIBLE);
        });
    }

    private CameraProgressDialog mLoadingDialog;

    private void showProgressDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new CameraProgressDialog(this);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.text("处理中..");
        }
        if (!isFinishing()) {
            mLoadingDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    @OnClick(R2.id.com_image_close_crop)
    void closeCrop() {
        showTakePhotoLayout();
    }

    @OnClick(R2.id.com_image_close_camera)
    void close() {
        finish();
    }

    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;
    private boolean mInitialized = false;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            mInitialized = true;
        }
        float deltaX = Math.abs(mLastX - x);
        float deltaY = Math.abs(mLastY - y);
        float deltaZ = Math.abs(mLastZ - z);

        if (deltaX > 0.8 || deltaY > 0.8 || deltaZ > 0.8) {
            viewCameraPreview.setFocus();
        }
        mLastX = x;
        mLastY = y;
        mLastZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        if (viewCameraPreview != null) {
            viewCameraPreview.onDestroy();
        }
        mLoadingDialog = null;
        super.onDestroy();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY)
            mUnBinder.unbind();
        this.mUnBinder = null;
        System.gc();
    }

    private Context getContext() {
        return this;
    }
}
