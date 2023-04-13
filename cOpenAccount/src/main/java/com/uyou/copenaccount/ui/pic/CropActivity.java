package com.uyou.copenaccount.ui.pic;

import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.model.PictureCropModel;
import com.uyou.copenaccount.utils.AppConfigs;
import com.uyou.copenaccount.view.crop.CropImageView;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zdd on 2019/8/23.
 * <p>
 * Description:
 */
public class CropActivity extends AppCompatActivity {

    private Unbinder mUnBinder;

    @BindView(R2.id.com_view_crop_view)
    CropImageView viewCropView;
    @BindView(R2.id.com_image_crop) ImageView btnCrop;

    private int requestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_activity_crop);

        mUnBinder = ButterKnife.bind(this);

        Object obj = getIntent().getSerializableExtra(ACTION_DATA);
        if (obj == null || !(obj instanceof PictureCropModel)) {
            AppConfigs.showToast(getContext(), getResources().getString(R.string.common_err_data));
            btnCrop.setEnabled(false);
            return;
        }

        PictureCropModel model = (PictureCropModel) obj;
        requestCode = model.getRequestCode();

        if (TextUtils.isEmpty(model.getPath())) {
            btnCrop.setEnabled(false);
            AppConfigs.showToast(getContext(), "图片数据异常, 请重试");
            return;
        }

        File file = new File(model.getPath());
        if (!file.exists()) {
            btnCrop.setEnabled(false);
            AppConfigs.showToast(getContext(), "图片数据异常, 请重试");
            return;
        }

        //截图样式
        viewCropView.setHandleSizeInDp(7);
        viewCropView.setTouchPaddingInDp(16);
        viewCropView.setMinFrameSizeInPx(380);

        viewCropView.setCropMode(CropImageView.CropMode.SQUARE);

        Glide.with(this).load(model.getPath()).into(viewCropView);
    }


    /**
     * 裁切
     */
    @OnClick(R2.id.com_image_crop)
    void cropPic() {
        //获取截图并旋转90度
        Bitmap croppedBitmap = viewCropView.getCroppedBitmap();
        // 图像名称
        String filename = System.currentTimeMillis() + ".jpg";

        File fileCache = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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

            Intent intent = getIntent();
            intent.putExtra(ACTION_DATA, file.getAbsolutePath());
            setResult(RESULT_OK, intent);
        } catch (Exception e) {
            AppConfigs.showToast(getContext(), "图片压缩失败, 请稍后重试");
        }
        finish();
    }


    @OnClick(R2.id.com_image_close_crop)
    void closeCrop() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY)
            mUnBinder.unbind();
        this.mUnBinder = null;
        System.gc();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private Context getContext() {
        return this;
    }

}
