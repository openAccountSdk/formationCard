package com.uyou.copenaccount.ui.pic;

import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.CODE_PERMISSION_CAMERA;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.base.UOpenBaseActivity;
import com.uyou.copenaccount.dialog.SelectMethodDialog;
import com.uyou.copenaccount.model.CompressConfig;
import com.uyou.copenaccount.model.PictureRequestModel;
import com.uyou.copenaccount.model.PictureResultModel;
import com.uyou.copenaccount.model.SelectItem;
import com.uyou.copenaccount.model.TResult;
import com.uyou.copenaccount.model.TakePhotoOptions;
import com.uyou.copenaccount.utils.DeviceUtils;
import com.uyou.copenaccount.utils.PermissionUtils;
import com.uyou.copenaccount.utils.TConstant;
import com.uyou.copenaccount.view.TakePhoto;
import com.uyou.copenaccount.view.TakePhotoImpl;
import com.uyou.copenaccount.xpopup.XPopup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zdd on 2019/03/29
 * <p>
 * Description: 图片选择 activity
 */
public class PictureSelectorActivity extends UOpenBaseActivity implements TakePhoto.TakeResultListener, SelectMethodDialog.OnDismissListener {

    private static final int CODE_CUSTOM = 97;


    private int requestType;
    private boolean useCustomCamera;
    private int customStep;

    private static final String TAG_DIALOG = "picture_selector_dialog";

    @BindView(R2.id.com_layout_container)
    View layoutContainer;

    private TakePhoto takePhoto;


    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.com_activity_picture_selector;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);

        Object obj = getIntent().getSerializableExtra(ACTION_DATA);
        if (obj != null && obj instanceof PictureRequestModel) {
            PictureRequestModel model = (PictureRequestModel) obj;
            requestType = model.getRequestType();
            customStep = model.getCustomStep();
            useCustomCamera = model.isUseCustomCamera();
        }

        if (requestType == PictureRequestModel.TYPE_NORMAL) {
            showSelectDialog();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = new TakePhotoImpl(this, this);
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        if (result != null && result.getImage() != null) {
            String compressPath = result.getImage().getCompressPath();
            String originalPath = result.getImage().getOriginalPath();
            String resultPath = originalPath;
            if (!TextUtils.isEmpty(compressPath)) {
                resultPath = compressPath;
            }
            PictureResultModel resultModel = new PictureResultModel();
            resultModel.setPath(resultPath);
            Intent intent = new Intent();
            intent.putExtra(ACTION_DATA, resultModel);
            setResult(RESULT_OK, intent);
        } else {
            showToast(getStringRes(R.string.common_err_file_not_exists));
        }
        finishActivity();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        showToast(getStringRes(R.string.common_err_file_not_exists));
        finishActivity();
    }

    @Override
    public void takeCancel() {
        finishActivity();
    }

    private void setTakePhotoConfig() {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setCorrectImage(true);
        getTakePhoto().setTakePhotoOptions(builder.create());

        CompressConfig config = new CompressConfig.Builder()
                //.setMaxSize(1536 * 1024) // 单位b  1.5M
                .setMaxPixel(1300) // 最大像素边
                .enableQualityCompress(false) // 质量压缩 和 MaxSize 不允许质量压缩
                .enablePixelCompress(true) // 像素压缩
                .enableReserveRaw(false) // 保留源文件
                .create();


        getTakePhoto().onEnableCompress(config, false);
    }

    /**
     * 调用系统相册选择
     */
    private void toGallery() {
        setTakePhotoConfig();
        getTakePhoto().onPickFromGallery();
    }

    /**
     * 调用系统相机拍照
     */
    private void toCamera() {
        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        setTakePhotoConfig();
        getTakePhoto().onPickFromCapture(imageUri);
    }

    /**
     * 调用自定义相机拍照
     */
    private void toCustomCamera() {
        if (DeviceUtils.hasCamera(getContext())) {
            Intent intent = new Intent(getContext(), CustomCameraActivity.class);
            intent.putExtra(ACTION_DATA, customStep);
            startActivityForResult(intent, CODE_CUSTOM);
        } else {
            showToast(getStringRes(R.string.common_err_no_camera));
            finishActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_CUSTOM:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String path = data.getStringExtra("path");
                        PictureResultModel model = new PictureResultModel();
                        model.setPath(path);
                        Intent intent = new Intent();
                        intent.putExtra(ACTION_DATA, model);
                        setResult(RESULT_OK, intent);
                    } else {
                        showToast(getStringRes(R.string.common_err_file_not_exists));
                    }
                }
                finishActivity();
                return;
            case TConstant.RC_PICK_PICTURE_FROM_CAPTURE:
            case TConstant.RC_PICK_PICTURE_FROM_DOCUMENTS_ORIGINAL:
            case TConstant.RC_PICK_PICTURE_FROM_GALLERY_ORIGINAL:
            case TConstant.RC_PICK_PICTURE_FROM_DOCUMENTS_CROP:
            case TConstant.RC_PICK_PICTURE_FROM_CAPTURE_CROP:
            case TConstant.RC_PICK_PICTURE_FROM_GALLERY_CROP:
            case TConstant.RC_CROP:
                getTakePhoto().onActivityResult(requestCode, resultCode, data);
                return;
        }
        finishActivity();
    }

    /**
     * 弹出选择框, 对应requestType - TYPE_NORMAL
     */
    private void showSelectDialog() {
        layoutContainer.postDelayed(() -> {
            List<SelectItem> list = new ArrayList<>();
            SelectItem itemPhoto = new SelectItem();
            itemPhoto.setType(SelectItem.TYPE_NORMAL);
            itemPhoto.setTitle("拍照");
            itemPhoto.setListener((popupView, view) -> {
                requestCameraPermission();
            });
            SelectItem itemGallery = new SelectItem();
            itemGallery.setType(SelectItem.TYPE_NORMAL);
            itemGallery.setTitle("相册");
            itemGallery.setListener((popupView, view) -> {
                requestStorePermission();
            });
            SelectItem itemCancel = new SelectItem();
            itemCancel.setType(SelectItem.TYPE_CANCEL);
            itemCancel.setListener((popupView, view) -> {
                popupView.dismiss();
            });
            list.add(itemPhoto);
            list.add(itemGallery);
            list.add(itemCancel);
            XPopup.get(getContext())
                    .asCustom(
                            new SelectMethodDialog(
                                    getContext(),
                                    list,
                                    PictureSelectorActivity.this
                            ))
                    .dismissOnTouchOutside(true)
                    .dismissOnBackPressed(true)
                    .autoDismiss(false)
                    .show(TAG_DIALOG);
        }, 80);
    }

    /**
     * 申请读写权限
     */
    private void requestStorePermission() {
        boolean request = PermissionUtils.request(PictureSelectorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 111);

        if (request) {
            toGallery();
        } else {
            showToast(getStringRes(R.string.common_permission_storage));
            finishActivity();
        }
    }


    /**
     * 申请摄像头权限
     */
    private void requestCameraPermission() {
        if (PermissionUtils.request(PictureSelectorActivity.this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSION_CAMERA)) {
            if (useCustomCamera) {
                toCustomCamera();
            } else {
                toCamera();
            }
        } else {
            showToast(getStringRes(R.string.common_permission_camera));
            finishActivity();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_PERMISSION_CAMERA) {
            boolean hasPermission = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    hasPermission = false;
                    break;
                }
            }
            if (hasPermission) {
                if (useCustomCamera) {
                    toCustomCamera();
                } else {
                    toCamera();
                }
            } else {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("需要您授权[摄像头][读写手机存储]权限, 才能正常使用")
                        .setTitle("提示")
                        .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                try {
                                    Intent intent = new Intent();
                                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e) {
                                    showToast("打开设置失败, 请手动打开定位权限");
                                    finish();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();
                            }
                        })
                        .create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void finishActivity() {
        if (requestType == PictureRequestModel.TYPE_NORMAL) {
            if (XPopup.isShow(TAG_DIALOG)) {
                XPopup.get(getContext()).dismiss(TAG_DIALOG);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onDismiss() {
        if (!isActivityClose()) {
            finish();
        }
    }
}
