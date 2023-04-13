package com.uyou.copenaccount.ui;


import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.CHECK_NUMBER;
import static com.uyou.copenaccount.base.UCConstants.CODE_PERMISSION_LOCATION;
import static com.uyou.copenaccount.base.UCConstants.CODE_PERMISSION_SR_READER;
import static com.uyou.copenaccount.base.UCConstants.CREATE_CUST_INFO_AND_CHECK_NUM;
import static com.uyou.copenaccount.base.UCConstants.ERROR_DATA;
import static com.uyou.copenaccount.base.UCConstants.ERROR_READER_NEED_DOWNLOAD;
import static com.uyou.copenaccount.base.UCConstants.GET_CREDENTIALS;
import static com.uyou.copenaccount.base.UCConstants.NET_DATA_ERROR;
import static com.uyou.copenaccount.base.UCConstants.PERMISSION_CHECK;
import static com.uyou.copenaccount.base.UCConstants.QUERY_DEVICES;
import static com.uyou.copenaccount.base.UCConstants.QUERY_PACKAGE;
import static com.uyou.copenaccount.base.UCConstants.SDK_VERSION;
import static com.uyou.copenaccount.base.UCConstants.SHARE_CREDENTIAL;
import static com.uyou.copenaccount.base.UCConstants.SHARE_DEFAULT_DEVICE;
import static com.uyou.copenaccount.base.UCConstants.SHARE_DISPLAY_NAME;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_001;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_002;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_003;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_004;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_005;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_006;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_007;
import static com.uyou.copenaccount.utils.StringUtils.getViewText;
import static com.uyou.copenaccount.utils.net.Api.UPLOAD_HEAD_PIC;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aisidi.plug.PlugInfo;
import com.aisidi.plug.ResultInfo;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.google.gson.Gson;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.adapter.AdditionalBusinessAdapter;
import com.uyou.copenaccount.adapter.DevicesSpinnerAdapter;
import com.uyou.copenaccount.adapter.MainPackageAdapter;
import com.uyou.copenaccount.adapter.ProductPackageSpinnerAdapter;
import com.uyou.copenaccount.base.UOpenBaseActivity;
import com.uyou.copenaccount.bean.BasePackageItem;
import com.uyou.copenaccount.bean.CommonPackageBean;
import com.uyou.copenaccount.bean.CommonPackageItemBean;
import com.uyou.copenaccount.bean.DeviceBean;
import com.uyou.copenaccount.bean.ProductBean;
import com.uyou.copenaccount.decoration.GridSpacingItemDecoration;
import com.uyou.copenaccount.dialog.CenterListDialog;
import com.uyou.copenaccount.dialog.down.PlugDownloadManager;
import com.uyou.copenaccount.model.CheckboxItem;
import com.uyou.copenaccount.model.OpenAccountAction;
import com.uyou.copenaccount.model.OpenAccountActionModel;
import com.uyou.copenaccount.model.OpenTypeModel;
import com.uyou.copenaccount.model.ReaderModel;
import com.uyou.copenaccount.model.SpinnerItem;
import com.uyou.copenaccount.plug.IPlugDownloadManager;
import com.uyou.copenaccount.reader.YSReaderActivity;
import com.uyou.copenaccount.response.CheckNumberResponse;
import com.uyou.copenaccount.response.QueryDevicesResponse;
import com.uyou.copenaccount.response.QueryPackageResponse;
import com.uyou.copenaccount.response.UploadHeadImageServiceResponse;
import com.uyou.copenaccount.utils.AESOperator;
import com.uyou.copenaccount.utils.APKUtils;
import com.uyou.copenaccount.utils.AccountUtils;
import com.uyou.copenaccount.utils.AppConfigs;
import com.uyou.copenaccount.utils.DeviceIdUtils;
import com.uyou.copenaccount.utils.KeyboardUtils;
import com.uyou.copenaccount.utils.LocationUtils;
import com.uyou.copenaccount.utils.MD5Utils;
import com.uyou.copenaccount.utils.PermissionUtils;
import com.uyou.copenaccount.utils.RegexUtils;
import com.uyou.copenaccount.utils.net.UYouHttpClient;
import com.uyou.copenaccount.view.rv.BaseQuickAdapter;
import com.uyou.copenaccount.xpopup.XPopup;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * Created by zdd on 2019/04/16
 * <p>
 * Description: 成卡开户 activity
 */
public class OpenAccountActivity extends UOpenBaseActivity {

    private static final int REQUEST_CODE_READER_ID_CARD = 11;

    // 输入
    @BindView(R2.id.bus_edit_phone)
    EditText editPhone;
    @BindView(R2.id.bus_edit_job_number)
    EditText editJobNum;//开户工号
    @BindView(R2.id.bus_edit_number_val)
    EditText editNumberVal;
    // 详情
    @BindView(R2.id.bus_layout_detail_container)
    LinearLayout layoutDetail;
    // 身份证
    @BindView(R2.id.bus_txt_card_name)
    TextView txtCardName;
    @BindView(R2.id.bus_txt_card_number)
    TextView txtCardNumber;
    @BindView(R2.id.bus_txt_card_address)
    TextView txtCardAddress;
    // 套餐名称
    @BindView(R2.id.bus_txt_product)
    TextView txtProduct;

    // 资费说明
    @BindView(R2.id.bus_layout_fee_detail_container)
    LinearLayout layoutFeeDetail;
    @BindView(R2.id.bus_txt_fee_detail)
    TextView txtFeeDetail;

    // 按钮
    @BindView(R2.id.bus_btn_check)
    Button btnCheck;
    @BindView(R2.id.bus_btn_scan)
    Button btnScan;
    @BindView(R2.id.bus_btn_next)
    Button btnNext;

    //主套餐recyclerview
    @BindView(R2.id.rv_main_package)
    RecyclerView rvMainPackage;
    //赠费说明
    @BindView(R2.id.gift_fee_detail)
    TextView giftFeeDetail;
    @BindView(R2.id.bus_txt_low_consumption_des)
    TextView tvLowConsumptionDes;//低消说明
    @BindView(R2.id.ll_gift_fee_description_container)
    LinearLayout llGiftFeeDetailContainer;
    @BindView(R2.id.layout_equipment)
    LinearLayout layout_equipment;
    // 套餐容器
    @BindView(R2.id.bus_layout_package_container)
    LinearLayout layoutPackageContainer;
    @BindView(R2.id.bus_layout_multiple_choice_package)
    LinearLayout layoutMultipleChoicePackage;
    @BindView(R2.id.bus_spinner_devices)
    public Spinner spinnerDevices;
    //附加项列表数据
    List<BasePackageItem> mPackageResultList = new ArrayList<>();
    /**
     * 开户号码
     */
    private String mOpenPhoneNum;
    /**
     * 开户ICCID后三位
     */
    private String mOpenCardNum;
    /**
     * 按钮展示文字
     */
    private String mStrButton;
    /**
     * 使用相机图库的控制 0-double;1-camera
     */
    private String mDiscern = "0";

    private List<ProductBean> mProductList;
    private String mErpId;
    private String mNumSection;
    private String mPhonenumType;
    // 主套餐
    private String mProductId;
    private String mProductName;

    /**
     * 获取到的身份证信息
     */
    private String mCardSex;
    private String mCardNation;
    private String mCardBirth;

    // 身份证小图片上传码
    private String mUploadCode;
    private String mEncryption;
    /**
     * 身份照片是否校验
     */
    private boolean isValid;

    private MainPackageAdapter mMainPackageAdapter;
    private AdditionalBusinessAdapter mAdditionalBusinessAdapter;
    private String mCardType;
    private String userType;
    private String remark;
    private AMapLocationClient mLocationClient;


    /**
     * 设备选择adapter
     */
    protected DevicesSpinnerAdapter adapterDevices;

    /**
     * 当前选中设备编号, 若未设置过,默认是亿数
     */
    protected String mCurrentDevice = "YS";
    /**
     * 设备编号
     */
    protected String mDeviceSerialCode;
    /**
     * 蓝牙
     */
    protected String mBluetoothAddress = "";
    /**
     * 蓝牙名称
     */
    protected String mBlueName = "";
    /**
     * 选择的开卡方式
     */
    protected int mSelectReadOperateType;

    /**
     * 下载管理器
     */
    private IPlugDownloadManager mDownloadManager;


    @Override
    public String getPageTitle() {
        return getStringRes(R.string.bus_title_open_account);
    }

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.bus_activity_open_account;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // step 1 申请定位权限并定位
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.request(this, Manifest.permission.ACCESS_FINE_LOCATION, CODE_PERMISSION_LOCATION)) {
                toLocation();
            }
        } else {
            toLocation();
        }
        initDeviceList();
        //设置主套餐recyclerview
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMainPackage.setLayoutManager(layoutManager);
        rvMainPackage.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));
        mMainPackageAdapter = new MainPackageAdapter();
        rvMainPackage.setAdapter(mMainPackageAdapter);
        mMainPackageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int selectedPos = mMainPackageAdapter.getSelectedPos();
                if (selectedPos != position) {
                    //取消之前的选中状态
                    if (selectedPos != -1) {
                        mProductList.get(selectedPos).setSelected(false);
                    }
                    mMainPackageAdapter.setSelectedPos(position);
                    mProductList.get(position).setSelected(true);
                    mMainPackageAdapter.notifyDataSetChanged();
                    //获取套餐信息
                    queryPackage(mProductList.get(position));
                }
            }
        });

        editJobNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editPhone.setText("");
                editNumberVal.setText("");
                btnCheck.setVisibility(View.VISIBLE);
                layout_equipment.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 初始化设备列表
     */
    private void initDeviceList() {
        mCurrentDevice = AppConfigs.getStringSF(getContext(), SHARE_DEFAULT_DEVICE, mCurrentDevice);
        adapterDevices = new DevicesSpinnerAdapter(getContext());
        spinnerDevices.setAdapter(adapterDevices);
        spinnerDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DeviceBean bean = adapterDevices.getItem(position);
                if (bean != null) {
                    if (!bean.getDevice_code().equals(mCurrentDevice)) {
                        // 若两次设备不相同, 清空蓝牙记录
                        mBluetoothAddress = "";
                        mBlueName = "";
                    }
                    // 记录当前设备选择
                    mCurrentDevice = bean.getDevice_code();
                    AppConfigs.setStringSF(getContext(), SHARE_DEFAULT_DEVICE, bean.getDevice_code());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void toLocation() {
        if (mLocationClient == null) {
            try {
                AMapLocationClient.updatePrivacyShow(getApplicationContext(), true, true);
                AMapLocationClient.updatePrivacyAgree(getApplicationContext(), true);
                mLocationClient = new AMapLocationClient(getApplicationContext());
                mLocationClient.setLocationListener(aMapLocation -> {
                    LocationUtils.dealLocationResult(getContext(), aMapLocation, new LocationUtils.OnLocationResultListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFail(int code, String message) {
                        }
                    });
                    mLocationClient.stopLocation();
                });
                AMapLocationClientOption locationOption = new AMapLocationClientOption();
                locationOption.setOnceLocation(true);
                locationOption.setNeedAddress(false);
           /*     boolean mockEnable = LocationUtils.getMockEnable(getContext());
                boolean locationMode = LocationUtils.getLocationMode(getContext());
                if (mockEnable) {
                    locationOption.setMockEnable(false);
                }
                if (locationMode) {
                    locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
                }*/
                locationOption.setLocationCacheEnable(false);
                locationOption.setInterval(5000);
                mLocationClient.setLocationOption(locationOption);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mLocationClient.startLocation();

    }

    public void queryPackage(ProductBean bean) {
        resetPackageInfo(false);
        mProductId = bean.getProductId();
        mProductName = bean.getProduct_name();
        txtProduct.setText(mProductName);
        queryPackage(mProductId);
    }

    @OnTextChanged({R2.id.bus_edit_phone, R2.id.bus_edit_number_val, R2.id.bus_edit_job_number})
    void onTextChange(CharSequence charSequence) {
        if (layoutDetail.getVisibility() == View.VISIBLE) {
            resetAll();
        }
    }

    @OnClick(R2.id.bus_btn_check)
    void toCheck() {
        String jobNumber = editJobNum.getText().toString().trim();
        if (!TextUtils.isEmpty(jobNumber)) {
            if (!RegexUtils.checkMobile(jobNumber)) {
                inputError(editJobNum, R.string.common_err_phone_error);
                return;
            }

            AppConfigs.setStringSF(getContext(), SHARE_DISPLAY_NAME, jobNumber);
            try {
                JSONObject json = new JSONObject();
                json.put("login_name", jobNumber);
                json.put("phoneCode", DeviceIdUtils.getid(OpenAccountActivity.this));
                AppConfigs.setStringSF(OpenAccountActivity.this, SHARE_CREDENTIAL, "");
                UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_001, GET_CREDENTIALS, json, SDK_VERSION, OpenAccountActivity.this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 证件扫描
     */
    @OnClick(R2.id.bus_btn_scan)
    void toScan() {
        // 重置状态/显示
        resetAll();
        mOpenPhoneNum = getViewText(editPhone);
        mOpenCardNum = getViewText(editNumberVal);

        // 数据校验
        if (TextUtils.isEmpty(mOpenPhoneNum)) {
            inputError(editPhone, R.string.common_err_phone_empty);
            return;
        }
        if (!RegexUtils.checkMobile(mOpenPhoneNum) && !mOpenPhoneNum.startsWith("8888888")) {
            inputError(editPhone, R.string.common_err_phone_error);
            return;
        }

        if (mOpenCardNum.length() < 3) {
            inputError(editNumberVal, R.string.bus_toast_val_num_empty);
            return;
        }

        KeyboardUtils.hideSoftInput(editNumberVal);
        if (PermissionUtils.request(OpenAccountActivity.this, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, CODE_PERMISSION_SR_READER)) {
            // 检测插件是否安装
            if (isPlugInstall()) {
                // 请求号码校验
                String jointNum = MD5Utils.MD5Encode("BAPP:" + AccountUtils.getUserName(getContext()), "UTF-8");

                try {

                    JSONObject js = new JSONObject();
                    js.put("phone_num", mOpenPhoneNum);
                    js.put("uim_code", mOpenCardNum);
                    js.put("jointNum", jointNum);

                    UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_004, CHECK_NUMBER, js, SDK_VERSION, OpenAccountActivity.this);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected boolean isPlugInstall() {
        // 检测插件是否安装
        if (!"YS".equals(mCurrentDevice)) {
            DeviceBean bean = adapterDevices.getItem(spinnerDevices.getSelectedItemPosition());
            if (bean == null) {
                showToast(getStringRes(R.string.common_toast_data_error));
                return false;
            }
            if (TextUtils.isEmpty(bean.getURL())) {
                showToast(getStringRes(R.string.bus_toast_no_plug));
                return false;
            }
            if (TextUtils.isEmpty(bean.getAction()) || TextUtils.isEmpty(bean.getURI())) {
                showToast(getStringRes(R.string.bus_toast_plug_error));
                return false;
            }
            Intent intent = new Intent(bean.getAction(), Uri.parse(bean.getURI()));
            List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, 0);
            if (resolveInfos != null && resolveInfos.size() > 0) {
                String packageName = resolveInfos.get(0).activityInfo.packageName;
                int versionCode = APKUtils.getVersionCode(getContext(), packageName);
                if (versionCode > 0) {
                    if (versionCode < bean.getVersion()) {
                        // 插件需要升级
                        downloadPlug(bean, getStringRes(R.string.bus_open_dialog_update_plug_content, bean.getDevice_name()));
                        return false;
                    }
                }
            } else {
                // 插件不存在
                downloadPlug(bean, getStringRes(R.string.bus_open_dialog_down_plug_content, bean.getDevice_name())
                );
                return false;
            }
        }
        return true;
    }

    /**
     * 下载插件弹窗
     */
    private void downloadPlug(DeviceBean bean, String content) {
        final Dialog customDialog = new Dialog(this, R.style.Dialog);


        //获得dialog的window窗口
        Window window = customDialog.getWindow();
        //设置dialog在屏幕中间
        window.setGravity(Gravity.CENTER);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //宽度设置为屏幕的0.7
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        lp.width = (int) (defaultDisplay.getWidth() * 0.7);
        //将设置好的属性set回去
        window.setAttributes(lp);


        View dialogView = LayoutInflater.from(this).inflate(R.layout.common_dialog_confirm, null);
        TextView btn_confirm = (TextView) dialogView.findViewById(R.id.tv_confirm);
        TextView btn_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        TextView tv_content = (TextView) dialogView.findViewById(R.id.tv_content);
        tv_content.setText(content);
        //将自定义布局加载到dialog上
        customDialog.setContentView(dialogView);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
                // 如果manager不为空, 先清空数据
                if (mDownloadManager != null) {
                    mDownloadManager.clear();
                    mDownloadManager = null;
                }
                // 请求下载插件
                mDownloadManager = new PlugDownloadManager(OpenAccountActivity.this, bean.getURL(), bean.getDevice_name() + ".apk");
            }
        });
        //设置点击dialog外是否可以取消
        customDialog.setCancelable(false);
        customDialog.show();

    }

    /**
     * 查询主产品套餐
     */
    private void queryPackage(String productId) {
        try {
            // 重置状态/显示

            JSONObject js = new JSONObject();
            js.put("productId", productId);
            js.put("numSection", mNumSection);
            js.put("ERPID", mErpId);
            js.put("phonenumType", mPhonenumType);
            js.put("phoneNum", getViewText(editPhone));
            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_005, QUERY_PACKAGE, js, SDK_VERSION, OpenAccountActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 重置views
    private void resetPackageList(List<CommonPackageBean> packages) {
        LayoutInflater layoutInflater = LayoutInflater.from(OpenAccountActivity.this);
        if (packages != null && packages.size() > 0) {
            for (CommonPackageBean aPackage : packages) {
                String rule_type = aPackage.getRule_type();
                List<CommonPackageItemBean> package_list = aPackage.getPackage_list();
                if (package_list != null && package_list.size() > 0) {
                    if (rule_type.equals("2")) {
                        // 单选样式
                        String ruleValue = aPackage.getRule_value();
                        // 0=非必选，默认不选  1不包含  2=必选 3 默认选择 可以取消
                        View inflate = layoutInflater.inflate(R.layout.bus_item_package_checkbox, layoutMultipleChoicePackage, false);
                        CheckBox checkBox = inflate.findViewById(R.id.bus_check_package);
                        CommonPackageItemBean item = package_list.get(0);
                        checkBox.setText(item.getName());
                        if ("3".equals(ruleValue)) {
                            checkBox.setChecked(true);
                        } else if ("2".equals(ruleValue)) {
                            checkBox.setChecked(true);
                            checkBox.setClickable(false);
                        } else {
                            checkBox.setChecked(false);
                        }
                        if (!TextUtils.isEmpty(aPackage.getPackage_desc())) {
                            TextView txtDesc = inflate.findViewById(R.id.bus_txt_package_desc);
                            txtDesc.setVisibility(View.VISIBLE);
                            txtDesc.setText(aPackage.getPackage_desc());
                        }
                        // 添加到临时列表
                        CheckboxItem checkboxItem = new CheckboxItem();
                        checkboxItem.id = item.getId();
                        checkboxItem.checkBox = checkBox;
                        checkboxItem.back_id_key = aPackage.getBack_id_key();
                        checkboxItem.back_name_key = aPackage.getBack_name_key();
                        mPackageResultList.add(checkboxItem);
                        layoutMultipleChoicePackage.addView(inflate);
                    } else {//是低消包，使用下拉样式选择
                        // 下拉样式
                        View inflate = layoutInflater.inflate(R.layout.bus_item_package_spinner, layoutPackageContainer, false);
                        TextView txtName = inflate.findViewById(R.id.bus_txt_package_name);
                        TextView txtDesc = inflate.findViewById(R.id.bus_txt_package_desc);
                        TextView txtDescItem = inflate.findViewById(R.id.bus_txt_package_item_desc);
                        Spinner spinnerPackage = inflate.findViewById(R.id.bus_spinner_package);
                        txtName.setText(aPackage.getPackage_name());
                        spinnerPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Object selectedItem = parent.getSelectedItem();
                                if (selectedItem instanceof CommonPackageItemBean) {
                                    CommonPackageItemBean bean = (CommonPackageItemBean) selectedItem;
                                    txtDescItem.setVisibility(TextUtils.isEmpty(bean.getDesc()) ? View.GONE : View.VISIBLE);
                                    txtDescItem.setText(bean.getDesc());
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        ProductPackageSpinnerAdapter<CommonPackageItemBean> adapter = new ProductPackageSpinnerAdapter(getContext());
                        spinnerPackage.setAdapter(adapter);
                        adapter.setNewData(package_list);
                        if (!TextUtils.isEmpty(aPackage.getPackage_desc())) {
                            txtDesc.setVisibility(View.VISIBLE);
                            txtDesc.setText(aPackage.getPackage_desc());
                        }
                        // 添加到临时列表
                        SpinnerItem spinnerItem = new SpinnerItem();
                        spinnerItem.adapter = adapter;
                        spinnerItem.spinner = spinnerPackage;
                        spinnerItem.back_id_key = aPackage.getBack_id_key();
                        spinnerItem.back_name_key = aPackage.getBack_name_key();
                        mPackageResultList.add(spinnerItem);
                        if (layoutPackageContainer != null) {
                            layoutPackageContainer.addView(inflate);
                        }
                    }
                }
            }
        }
    }

    /**
     * 提交资料
     */
    @OnClick(R2.id.bus_btn_next)
    void toNext() {
        if (!isValid) {
            // 未上传身份图片/身份信息未校验
            showToast(getStringRes(R.string.bus_toast_read_not_valid));
            return;
        }
        if (mSelectReadOperateType == 0) {
            // 还未选择设备读卡
            return;
        }

        try {
            // 创建客户资料并校验证卡数
            JSONObject js = new JSONObject();
            js.put("name", getViewText(txtCardName));
            js.put("address", getViewText(txtCardAddress));
            js.put("cardId", getViewText(txtCardNumber));
            js.put("productId", mProductId);
            js.put("reqType", isNotPreRecordCard() ? "2" : "1");
            js.put("phoneNum", getViewText(editPhone));
            js.put("type", "1");

            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_006, CREATE_CUST_INFO_AND_CHECK_NUM, js, SDK_VERSION, OpenAccountActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void toNextPage() {
        // 身份信息
        String cardNumber = getViewText(txtCardNumber);
        String credential = AppConfigs.getStringSF(getContext(), SHARE_CREDENTIAL);
        String cardName = getViewText(txtCardName);

        JSONObject json = getPackageJson();
        if (json == null) {
            showToast(ERROR_DATA);
            return;
        }

        OpenAccountActionModel request = new OpenAccountActionModel();
        // 主套餐
        request.setProductId(mProductId);
        request.setProductName(mProductName);
        // 其他套餐
        request.setPackageJson(json.toString());

        // 手机号
        request.setPhone_num(mOpenPhoneNum);
        request.setUim_code(mOpenCardNum);
        // 设备信息
        request.setDevice_num(mDeviceSerialCode);
        request.setOpen_account_factory(mCurrentDevice);
        request.setOpen_account_type(String.valueOf(mSelectReadOperateType));
        // 亿数(已下线)
        if ("YS".equals(mCurrentDevice)) {
            request.setAccept(String.valueOf((new Date().getTime() / 1000)));
        }
        // 身份证信息
        request.setUpload_code(mUploadCode);
        request.setName(cardName);
        request.setSex(mCardSex);
        request.setNation(mCardNation);
        request.setBirth(mCardBirth);
        request.setCert_num(cardNumber);
        request.setAddress(getViewText(txtCardAddress));

        request.setEncryption(mEncryption);
        // 构建秘钥
        if (cardNumber.length() > 10 && mOpenPhoneNum.length() > 10) {
            String ivParameter = mOpenPhoneNum.substring(1, 11) + cardNumber.substring(0, 6);
            String key = credential.substring(0, 16);
            String securekey = mOpenPhoneNum + "#" + cardNumber + "#" + cardName;
            try {
                String securekey_value = AESOperator.getInstance(key, ivParameter).encrypt(securekey);
                request.setSecurekey(securekey_value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        OpenAccountAction action = new OpenAccountAction();
        action.openAccountModel = request;
        action.discern = mDiscern;
        action.mobileNum = mOpenPhoneNum;
        action.noIsOnline = true;
        action.Select_Shibie_FangShi = mSelectReadOperateType;
        action.showText = mStrButton;
        action.userType = userType;
        action.remark = remark;
        action.isNotPreRecordCard = isNotPreRecordCard();
        // 跳转至上传图片页面
        Intent intent = new Intent(this, UploadOpenAccountPicActivity.class);
        intent.putExtra(ACTION_DATA, action);
        startActivity(intent);

    }

    /**
     * 重置状态
     */
    private void resetAll() {
        btnScan.setEnabled(true);
        mCardType = null;
        mOpenCardNum = null;
        mOpenPhoneNum = null;
        mStrButton = null;
        mDiscern = "0";
        isValid = false;
        mUploadCode = null;
        mCardBirth = null;
        mCardNation = null;
        mCardSex = null;
        mEncryption = null;
        mSelectReadOperateType = 0;

        mNumSection = null;
        mPhonenumType = null;

        layoutDetail.setVisibility(View.GONE);

        txtCardName.setText("");
        txtCardAddress.setText("");
        txtCardNumber.setText("");
        txtCardName.setEnabled(false);
        txtCardNumber.setEnabled(false);
        mMainPackageAdapter.setSelectedPos(-1);
        resetPackageInfo(true);
    }

    /**
     * 初始化套餐信息
     */
    private void resetPackageInfo(boolean containerMain) {
        if (containerMain) {
            txtProduct.setText("");
            mProductList = null;
            mErpId = null;
            mProductId = null;
            mProductName = null;
        }

        // 清空套餐
        mPackageResultList.clear();
        layoutPackageContainer.removeAllViews();
        layoutMultipleChoicePackage.removeAllViews();
        llGiftFeeDetailContainer.setVisibility(View.GONE);
        layoutFeeDetail.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
    }

    /**
     * 选择读卡方式弹窗
     *
     * @param position
     * @param list
     */

    /**
     * 选择读卡方式弹窗
     *
     * @param position
     * @param list
     */
    protected void showSelectMethodDialog(int position, List<OpenTypeModel> list) {
        XPopup.get(getContext())
                .asCustom(
                        new CenterListDialog(
                                getContext(),
                                list,
                                model -> {
                                    mDeviceSerialCode = "";
                                    if (position >= 0 && position < adapterDevices.getCount()) {
                                        DeviceBean bean = adapterDevices.getItem(position);

                                        if ("YS".equals(bean.getDevice_code()) || (model.getCode() == 2 && "KE".equals(bean.getDevice_code()))) {
                                            if (PermissionUtils.request(OpenAccountActivity.this, new String[]{
                                                    Manifest.permission.READ_PHONE_STATE,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            }, CODE_PERMISSION_SR_READER)) {
                                                toReadId(model, bean);

                                            } else {
                                                showToast("需要[读写SD卡]和[读取手机信息]权限才能正常使用");
                                            }
                                        } else {
                                            toReadId(model, bean);
                                        }
                                    }
                                }
                        )
                )
                .show();
    }

    private void toReadId(OpenTypeModel model, DeviceBean bean) {
        mSelectReadOperateType = model.getCode();
        // 去读卡, 传入基本信息
        ReaderModel readerModel = new ReaderModel();
        readerModel.setTypeCode(model.getCode());
        readerModel.setAction(bean.getAction());
        readerModel.setURI(bean.getURI());
        readerModel.setURL(bean.getURL());
        readerModel.setDeviceCode(bean.getDevice_code());
        readerModel.setVersion(bean.getVersion());
        if (TextUtils.isEmpty(mBluetoothAddress) || TextUtils.isEmpty(mBlueName)) {
            readerModel.setBluetoothAddress("");
            readerModel.setBlueName("");
        } else {
            readerModel.setBluetoothAddress(mBluetoothAddress);
            readerModel.setBlueName(mBlueName);
        }
        readerModel.setRequestCode(REQUEST_CODE_READER_ID_CARD);

        PlugInfo info = new PlugInfo();
        info.setVersion(readerModel.getVersion());
        info.setUrl(readerModel.getURL());
        // 是否使用亿数nfc
        boolean useYsNFC = false;
        switch (readerModel.getTypeCode()) {
            case 1: // OTG
                info.setType(PlugInfo.OTG);
                break;
            case 2: // NFC
                info.setType(PlugInfo.NFC);
                // NFC 特殊处理
                if ("KE".equals(readerModel.getDeviceCode())) {
                    useYsNFC = true;
                }
                break;
            case 3: // 蓝牙
                info.setAddress(readerModel.getBluetoothAddress());
                info.setArg1(readerModel.getBlueName());
                info.setType(PlugInfo.BT);

                break;
            case 4: // 云识别
                return;
            case 5: // 本地扫描
                return;
        }
        if ("YS".equals(readerModel.getDeviceCode()) || useYsNFC) {
            Intent intent = new Intent(this, YSReaderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(ACTION_DATA, info);
            intent.putExtras(bundle);
            startActivityForResult(intent, readerModel.getRequestCode());

        } else {
            try {
                Intent intent = new Intent(readerModel.getAction(), Uri.parse(readerModel.getURI()));
                intent.putExtra("info", info);
                if (APKUtils.checkApkExist(OpenAccountActivity.this, intent)) {
                    startActivityForResult(intent, readerModel.getRequestCode());

                } else {
                    showToast(ERROR_READER_NEED_DOWNLOAD);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("打开插件失败, 请重试.");
            }
        }

    }

    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response) {
        switch (threadId) {
            case THREAD_ID_001:
                try {
                    if (json.has("data")) {
                        String data = json.getString("data");
                        if (!TextUtils.isEmpty(data)) {
                            String credential = data;
                            AppConfigs.setStringSF(OpenAccountActivity.this, SHARE_CREDENTIAL, credential);
                            JSONObject js = new JSONObject();
                            js.put("loginType", "openAccount");
                            js.put("city", LocationUtils.getCity(OpenAccountActivity.this));
                            js.put("province", LocationUtils.getProvince(OpenAccountActivity.this));
                            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_002, PERMISSION_CHECK, js, SDK_VERSION, OpenAccountActivity.this);
                        }
                    } else {
                        showToast(NET_DATA_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case THREAD_ID_002:
                try {
                    if (json.has("code")) {
                        btnCheck.setVisibility(View.GONE);
                        layout_equipment.setVisibility(View.VISIBLE);
                        // 请求设备列表
                        JSONObject js = new JSONObject();
                        UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_003, QUERY_DEVICES, js, SDK_VERSION, OpenAccountActivity.this);

                    } else {
                        showToast(NET_DATA_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case THREAD_ID_003:
                try {
                    if (!TextUtils.isEmpty(response)) {
                        Gson gson = new Gson();
                        QueryDevicesResponse queryDevicesResponse = gson.fromJson(response, QueryDevicesResponse.class);
                        List<DeviceBean> data = queryDevicesResponse.getData();
                        if (data != null) {
                            adapterDevices.setNewData(data);
                            // 设置默认选中设备
                            for (int i = 0; i < data.size(); i++) {
                                if (mCurrentDevice.equals(data.get(i).getDevice_code())) {
                                    spinnerDevices.setSelection(i);
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case THREAD_ID_004:
                try {
                    if (!TextUtils.isEmpty(response)) {
                        Gson gson = new Gson();
                        CheckNumberResponse checkNumberResponse = gson.fromJson(response, CheckNumberResponse.class);
                        CheckNumberResponse.Data bean = checkNumberResponse.getData();
                        mDiscern = bean.getDiscern();
                        mStrButton = bean.getSubmit_context();
                        userType = bean.getUserType();

                        mErpId = bean.getERPID();
                        mNumSection = bean.getNumSection();
                        mPhonenumType = bean.getPhonenumType();

                        mCardType = bean.getCardType();
                        remark = bean.getRemark();
                        mProductList = bean.getProductNameList();
                        mMainPackageAdapter.setCardType(mCardType);
                        //主套餐只有一个的情况时 默认选中
                        if (mProductList != null && mProductList.size() == 1) {
                            mMainPackageAdapter.setSelectedPos(0);
                            //获取套餐信息
                            if ("2".equals(mCardType)) {
                                queryPackage(mProductList.get(0));
                            }
                        }

                        if (mCardType != null && mCardType.equals("1")) {
                            // mCardType为1预录入卡（相当于是成卡开户）
                            layoutFeeDetail.setVisibility(View.VISIBLE);
                            txtFeeDetail.setText(remark);

                            String expenseDescription = bean.getExpenseDescription();
                            if (expenseDescription != null && !expenseDescription.equals("")) {
                                llGiftFeeDetailContainer.setVisibility(View.VISIBLE);
                                giftFeeDetail.setText(expenseDescription);
                            }
                            String gearDes = bean.getGearDes();
                            if (!TextUtils.isEmpty(gearDes)) {
                                tvLowConsumptionDes.setVisibility(View.VISIBLE);
                                tvLowConsumptionDes.setText(gearDes);
                            }

                            mProductId = bean.getProductId();
                            mProductName = bean.getProductName();
                            txtProduct.setText(mProductName);
                            List<CommonPackageBean> packages = bean.getPackages();
                            resetPackageList(packages);
                            // 请求成功才显示下一步
                            btnNext.setVisibility(View.VISIBLE);
                        }
                        mMainPackageAdapter.setNewData(mProductList);

                        //选择当前选中设备的开卡方式并弹窗
                        int position = spinnerDevices.getSelectedItemPosition();
                        if (position >= 0 && position < adapterDevices.getCount()) {
                            DeviceBean deviceBean = adapterDevices.getItem(position);
                            if (deviceBean != null) {
                                List<OpenTypeModel> list = deviceBean.getSupport_open_type();
                                ArrayList<CheckNumberResponse.Data.OpenType> listFilter = bean.getOpen_type();
                                if (listFilter == null || listFilter.size() <= 0) {
                                    // 弹窗
                                    showSelectMethodDialog(position, list);
                                } else {
                                    // 进行筛选 过滤暂不支持的开卡方式
                                    List<OpenTypeModel> listResult = new ArrayList<>();

                                    for (OpenTypeModel model : list) {
                                        for (CheckNumberResponse.Data.OpenType filter : listFilter) {
                                            if (model.getCode() == filter.getCode()) {
                                                listResult.add(model);
                                                break;
                                            }
                                        }
                                    }
                                    //弹窗
                                    showSelectMethodDialog(position, listResult);
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case THREAD_ID_005:

                try {
                    if (!TextUtils.isEmpty(response)) {
                        Gson gson = new Gson();
                        QueryPackageResponse queryPackageResponse = gson.fromJson(response, QueryPackageResponse.class);
                        QueryPackageResponse.Data data = queryPackageResponse.getData();
                        List<CommonPackageBean> packages = data.getPackages();

                        resetPackageList(packages);
                        layoutFeeDetail.setVisibility(View.VISIBLE);
                        remark = data.getRemark();
                        txtFeeDetail.setText(remark);

                        String expenseDescription = data.getExpenseDescription();
                        if (expenseDescription != null && !expenseDescription.equals("")) {
                            llGiftFeeDetailContainer.setVisibility(View.VISIBLE);
                            giftFeeDetail.setText(expenseDescription);
                        } else {
                            llGiftFeeDetailContainer.setVisibility(View.GONE);
                        }
                        String gearDes = data.getGearDes();
                        if (!TextUtils.isEmpty(gearDes)) {
                            tvLowConsumptionDes.setVisibility(View.VISIBLE);
                            tvLowConsumptionDes.setText(gearDes);
                        }

                        // 请求成功才显示下一步
                        btnNext.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case THREAD_ID_006:
                try {
                    if (!TextUtils.isEmpty(response)) {
                        toNextPage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case THREAD_ID_007:
                if (!TextUtils.isEmpty(response)) {
                    isValid = true;

                    Gson gson = new Gson();
                    UploadHeadImageServiceResponse uploadHeadImageServiceResponse = gson.fromJson(response, UploadHeadImageServiceResponse.class);
                    UploadHeadImageServiceResponse.Data data = uploadHeadImageServiceResponse.getData();

                    mUploadCode = data.getUpload_code();
                    mEncryption = data.getEncryption();
                    // 把用户信息组的布局给显示出来
                    layoutDetail.setVisibility(View.VISIBLE);
                    boolean editSwitch = data.isEditSwitch();
                    txtCardName.setEnabled(editSwitch);
                    txtCardNumber.setEnabled(editSwitch);

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_READER_ID_CARD) {
            if (resultCode == RESULT_OK) {
                // 读卡插件返回结果
                if (data == null) {
                    showToast(getStringRes(R.string.bus_toast_read_error));
                    return;
                }
                ResultInfo resultInfo = data.getParcelableExtra("result");
                if (resultInfo == null || resultInfo.getCardInfo() == null) {
                    showToast(getStringRes(R.string.bus_toast_read_error));
                    return;
                }
                if (resultInfo.getDevice_type() == PlugInfo.BT) {
                    mBluetoothAddress = resultInfo.getMessage();
                    mBlueName = resultInfo.getAgr1();
                }

                // 获取身份证信息
                mCardSex = resultInfo.getCardInfo().getSex().trim();
                mCardNation = resultInfo.getCardInfo().getNation().trim();
                mCardBirth = resultInfo.getCardInfo().getBorn().trim();
                mDeviceSerialCode = resultInfo.getDevice_id().trim();
                String cardEndDate = resultInfo.getCardInfo().getDate().trim();
                String cardName = resultInfo.getCardInfo().getName().trim();
                String cardNumber = resultInfo.getCardInfo().getCardNo().trim();
                String cardAddress = resultInfo.getCardInfo().getAddress().trim();

                // 将用户信息显示到界面上
                txtCardName.setText(cardName);
                txtCardNumber.setText(cardNumber);
                txtCardAddress.setText(cardAddress);

                // 上传图片
                Bitmap bitmap = resultInfo.getCardInfo().getImg();

                File file = null;

                if (bitmap == null) {
//                    showToast(getStringRes(R.string.bus_toast_upload_head_error));
//                    return;
                } else {
                    File dir = new File(getCacheDir() + File.separator + "cachePic");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    file = new File(dir, "header.jpg");
                    if (file.exists()) {
                        file.delete();
                    }
                    try {
                        if (file.createNewFile()) {
                            FileOutputStream outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        }
                    } catch (Exception e) {
                        //showToast(getStringRes(R.string.bus_toast_upload_head_error));
                        //return;
                    }
                }


                try {
                    UYouHttpClient.getInstance().uploadHeadImage(
                            UPLOAD_HEAD_PIC,
                            AccountUtils.getUserName(getContext()),
                            getViewText(editPhone),
                            resultInfo.getCardInfo().getName(),
                            resultInfo.getCardInfo().getCardNo(),
                            cardEndDate,
                            file == null ? null : file.getAbsolutePath(),
                            mSelectReadOperateType + "_" + mCurrentDevice,
                            THREAD_ID_007,
                            OpenAccountActivity.this
                    );

                } catch (Exception e) {
                    showToast("图片转换错误, 请重试");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            if (mDownloadManager != null) {
                mDownloadManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    /**
     * 是否是未预录入卡
     *
     * @return true 是未预录入卡, false 是预录入卡
     */
    private boolean isNotPreRecordCard() {
        if (mCardType != null && mCardType.equals("1")) {
            return false;
        } else {
            return true;
        }
    }

    protected JSONObject getPackageJson() {
        // 构建套餐信息
        JSONObject json = new JSONObject();
        try {
            for (int i = 0; i < mPackageResultList.size(); i++) {
                BasePackageItem item = mPackageResultList.get(i);
                if (item != null) {
                    if (item instanceof SpinnerItem) {
                        SpinnerItem spinnerItem = (SpinnerItem) item;
                        if (spinnerItem.spinner != null && spinnerItem.adapter != null) {
                            int position = spinnerItem.spinner.getSelectedItemPosition();
                            if (position >= 0 && position < spinnerItem.adapter.getCount()) {
                                CommonPackageItemBean b = spinnerItem.adapter.getItem(position);
                                if (b != null && b.getId() != null) {
                                    json.put(spinnerItem.back_id_key, b.getId());
                                    json.put(spinnerItem.back_name_key, b.getName());
                                }
                            }
                        }
                    } else if (item instanceof CheckboxItem) {
                        CheckboxItem checkboxItem = (CheckboxItem) item;
                        CheckBox checkBox = checkboxItem.checkBox;
                        if (checkBox != null && checkBox.isChecked()) {
                            if (checkboxItem.id != null) {
                                json.put(checkboxItem.back_id_key, checkboxItem.id);
                                json.put(checkboxItem.back_name_key, getViewText(checkBox));
                            }
                        }
                    }
                }
            }
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            try {
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
            } catch (Exception e) {

            }
            mLocationClient = null;
        }

        if (mDownloadManager != null) {
            mDownloadManager.clear();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toLocation();
            } else {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("需要您授权[定位]权限, 才能正常使用")
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
                                } catch (Exception e) {
                                    showToast("打开设置失败, 请手动打开定位权限");
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        }
        if (requestCode ==CODE_PERMISSION_SR_READER){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 检测插件是否安装
                if (isPlugInstall()) {
                    // 请求号码校验
                    String jointNum = MD5Utils.MD5Encode("BAPP:" + AccountUtils.getUserName(getContext()), "UTF-8");

                    try {

                        JSONObject js = new JSONObject();
                        js.put("phone_num", mOpenPhoneNum);
                        js.put("uim_code", mOpenCardNum);
                        js.put("jointNum", jointNum);

                        UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_004, CHECK_NUMBER, js, SDK_VERSION, OpenAccountActivity.this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("需要[读写SD卡]和[读取手机信息]权限才能正常使用")
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
                                } catch (Exception e) {
                                    showToast("打开设置失败, 请手动打开权限");
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

}
