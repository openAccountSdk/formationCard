package com.uyou.copenaccount.reader;

import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.aisidi.plug.PlugInfo;
import com.aisidi.plug.ResultInfo;
import com.otg.idcard.OTGReadCardAPI;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.reader.base.BaseFragmentAct;
import com.uyou.copenaccount.reader.base.Frg_one_btn;
import com.uyou.copenaccount.reader.base.Frg_two_btn;
import com.uyou.copenaccount.reader.dialog.ReaderMyDialog;
import com.uyou.copenaccount.utils.AppConfigs;
import com.uyou.copenaccount.utils.DeviceIdUtils;


/**
 * Created by zdd on 2019/7/8.
 * <p>
 * Description: 南京亿数读卡器
 */
public class YSReaderActivity extends BaseFragmentAct {

    private static final int REQUEST_ENABLE_BT = 99;
    private static final int SETTING_NFC = 100;

    public static final int MESSAGE_VALID_OTGBUTTON = 15;
    public static final int MESSAGE_VALID_NFCBUTTON = 16;
    public static final int MESSAGE_VALID_BTBUTTON = 17;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    /**
     * 读取类型
     */
    private int mReadType;

    /**
     * 蓝牙地址
     */
    private String mBluetoothAddress;
    private String mBluetoothName;

    private Intent thisIntent;

    private OTGReadCardAPI ysReadCardAPI;

    /**
     * 阅读器处理handler
     */
    private MyHandler mHandler;

    private BluetoothAdapter mBlueAdapter;

    // nfc相关
    private NfcAdapter mNFCAdapter;
    private PendingIntent mNfcPendingIntent = null;
    //滤掉组件无法响应和处理的Intent
    private IntentFilter mNfcTagDetected = null;
    private String[][] mNfcTechLists;


    /**
     * 弹窗文字修改
     */
    private Handler handler_YSBDialog = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle data = msg.getData();
                    boolean isEnabled = data.getBoolean("isEnabled");
                    String title = data.getString("title");
                    String message = data.getString("message");
                    frg_one_btn.setMessage(message);
                    frg_one_btn.setButtonEnabled(true);
                    frg_one_btn.setTitle(title);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        PlugInfo info = getIntent().getParcelableExtra(ACTION_DATA);

        if (info == null) {
            AppConfigs.showToast(this, getResources().getString(R.string.common_err_data));
            finish();
            return;
        }

        mReadType = info.getType();
        mHandler = new MyHandler();

        if (mReadType == PlugInfo.NFC) {
            mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            mNfcTagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);//.ACTION_TAG_DISCOVERED);
            mNfcTagDetected.addCategory(Intent.CATEGORY_DEFAULT);
            mNfcTechLists = new String[][]{new String[]{NfcB.class.getName()}};
        } else if (mReadType == PlugInfo.OTG) {
            // 注册OTG 当连接成功, 会有回调
            showYS_Dialog("提示", "正在连接设备...", true);
            try {
                IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
                registerReceiver(mUsbReceiver, filter);
            } catch (Exception e) {
                showYS_Dialog("提示", "OTG连接失败.", true);
            }
        }
    }

    private OTGReadCardAPI getReadApi() {
        if (ysReadCardAPI == null) {
            ysReadCardAPI = new OTGReadCardAPI(getApplicationContext());
            // ysReadCardAPI.setlogflag(1);
        }
        return ysReadCardAPI;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReadType == PlugInfo.NFC) {
            openNfC();
        } else if (mReadType == PlugInfo.OTG) {
            // 开始OTG读卡
        } else if (mReadType == PlugInfo.BT) {
            // 开始 蓝牙设置
            readCardBlueTooth();
        }
    }

    private void startNFC_Listener() {
        try {
            mNFCAdapter.enableForegroundDispatch(this, mNfcPendingIntent, new IntentFilter[]{mNfcTagDetected}, mNfcTechLists);
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    private void stopNFC_Listener() {
        try {
            mNFCAdapter.disableForegroundDispatch(this);
        } catch (Exception e) {

        }
    }

    private void openNfC() {
        if (mNFCAdapter == null) {
            try {
                mNFCAdapter = NfcAdapter.getDefaultAdapter(this);
            } catch (Exception e) {
            }
        }
        if (mNFCAdapter == null) {
            showYS_Dialog("提示", "抱歉，您的手机不支持NFC功能", true);
            return;
        }
        if (!mNFCAdapter.isEnabled()) {
            new ReaderMyDialog.Builder(this)
                    .setTitle("NFC")
                    .setNegativeButton("取消", null)
                    .setPositiveButton(
                            "去设置",
                            (dialog, which) -> {
                                dialog.dismiss();
                                startActivityForResult(new Intent("android.settings.NFC_SETTINGS"), SETTING_NFC);
                            }
                    )
                    .setMessage("您的NFC功能没有打开！")
                    .create().show();
            return;
        }

        showYS_Dialog("NFC", "请将身份证放置在NFC识别区域", true);
        // 开启nfc
        startNFC_Listener();
    }

    /**
     * 蓝牙读卡方式
     */
    protected void readCardBlueTooth() {
        if (mBlueAdapter == null) {
            mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBlueAdapter == null) {
            showYS_Dialog("提示", "蓝牙不可用", true);
            return;
        }
        if (!mBlueAdapter.isEnabled()) {
            // 请求开启蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            return;
        }
        if (mBluetoothAddress == null || mBluetoothAddress.length() <= 0) {
            // 蓝牙设备未设置
            changeFrg(Frg_two_btn.index);
            return;
        }
        showYS_Dialog("提示", "正在连接设备...", true);

        if (getReadApi() != null) {
            getReadApi().setmac(mBluetoothAddress);
        }

        mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_BTBUTTON, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == 100) {
                    readCardBlueTooth();
                }
                break;
            case SETTING_NFC:
                break;
        }
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (getReadApi() == null) {
                showYS_Dialog("提示", "设备初始化错误", true);
                return;
            }
            int tt;
            String message = "身份证读取失败！";
            switch (msg.what) {
                case MESSAGE_VALID_BTBUTTON: //读取蓝牙
                    // 设置蓝牙地址
                    getReadApi().setmac(mBluetoothAddress);
                    // 开始蓝牙读卡
                    try {
                        tt = getReadApi().BtReadCard(mBlueAdapter);


                        if (tt == 90) {
                            readCardSuccess();
                            return;
                        }

                        if (tt == 2) {
                            message = "接收数据超时！";
                        } else if (tt == 41) {
                            message = "读卡失败！";
                        } else if (tt == 42) {
                            message = "没有找到服务器！";
                        } else if (tt == 43) {
                            message = "服务器忙！";
                        }
                    } catch (Exception e) {

                    }
                    showYS_Dialog("提示", message, true);
                    break;
                case MESSAGE_VALID_NFCBUTTON: //读取nfc
                    // 开始nfc读卡
                    try {
                        tt = getReadApi().NfcReadCard(thisIntent);
                        if (tt == 90) {
                            readCardSuccess();
                            return;
                        }
                        if (tt == 2) {
                            message = "接收数据超时！";
                        } else if (tt == 41) {
                            message = "读卡失败！";
                        } else if (tt == 42) {
                            message = "没有找到服务器！";
                        } else if (tt == 43) {
                            message = "服务器忙！";
                        }
                    } catch (Exception e) {

                    }
                    showYS_Dialog("提示", message, true);
                    break;
                case MESSAGE_VALID_OTGBUTTON: //读取otg
                    // 检测设备是否连接
                    try {
                        tt = getReadApi().ConnectStatus();
                        if (tt == 0) {
                            showYS_Dialog("提示", "设备未连接！", true);
                            break;
                        }
                        if (tt == 2) {
                            // 没有权限, 重试连接
                            showYS_Dialog("提示", "再次读卡！", true);
                            break;
                        }

                        // 开始otg读卡
                        tt = getReadApi().OTGReadCard();
                        if (tt == 90) {
                            readCardSuccess();
                            return;
                        }
                        if (tt == 2) {
                            message = "接收数据超时！";
                        } else if (tt == 41) {
                            message = "读卡失败！";
                        } else if (tt == 42) {
                            message = "没有找到服务器！";
                        } else if (tt == 43) {
                            message = "服务器忙！";
                        }
                    } catch (Exception e) {

                    }
                    showYS_Dialog("提示", message, true);
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mReadType == PlugInfo.NFC) {
            // NFC开始读卡
            thisIntent = intent;
            mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_NFCBUTTON, 100);
        }
    }

    /**
     * 蓝牙回调地址
     *
     * @param success
     * @param name
     * @param address
     */
    @Override
    public void address(boolean success, String name, String address) {
        if (success) {
            // 开始识别身份证
            changeFrg(Frg_one_btn.index);

            frg_one_btn = (Frg_one_btn) fragments.get(Frg_one_btn.index);
            frg_one_btn.setMessage("正在连接设备...");
            mBluetoothAddress = address;
            mBluetoothName = name;

            readCardBlueTooth();
        } else {
            // 去设置蓝牙设备
            changeFrg(Frg_two_btn.index);
        }
    }

    /**
     * 修改文字
     *
     * @param title
     * @param message
     * @param isEnabled 是否清空自定义点击事件，恢复默认事件
     */
    private void showYS_Dialog(String title, String message, boolean isEnabled) {
        if (!(Frg_one_btn.index == getFragmentIndex()))
            changeFrg(Frg_one_btn.index);
        Message msg = Message.obtain();
        msg.what = 0;
        Bundle data = new Bundle();
        data.putBoolean("isEnabled", isEnabled);
        data.putString("title", title);
        data.putString("message", message);
        msg.setData(data);
        handler_YSBDialog.sendMessage(msg);
    }

    /**
     * 读卡成功的处理
     */
    public void readCardSuccess() {
        if (getReadApi() != null) {
            getReadApi().release();
            String name = trim(getReadApi().Name());
            String sex = trim(getReadApi().SexL());
            String birthday = trim(getReadApi().BornL());
            String nation = trim(getReadApi().NationL());
            String address = trim(getReadApi().Address());
            String number = trim(getReadApi().CardNo());
            String qianfa = trim(getReadApi().Police());
            String effdate = trim(getReadApi().endtext);


            Bitmap head = Bytes2Bitmap(getReadApi().GetImage());
            if (head == null) {
                AppConfigs.showToast(getContext(), "头像读取失败");
            }

            try {
                ResultInfo.IDCardInfo idCardInfo = new ResultInfo.IDCardInfo(
                        name,
                        sex,
                        number,
                        nation,
                        birthday,
                        address,
                        qianfa,
                        effdate,
                        head
                );
                String deviceId = "";
                if (mReadType == PlugInfo.NFC) {
                    deviceId = "NFC-" + DeviceIdUtils.getIMEI(getContext());
                } else if (mReadType == PlugInfo.BT) {
                    deviceId = mBluetoothName + "-" + mBluetoothAddress;
                } else {
                    deviceId = "YS-OTG";
                }
                ResultInfo info = new ResultInfo(
                        200,
                        "YS",
                        deviceId,
                        mReadType,
                        mBluetoothAddress,
                        "",
                        idCardInfo);
                setActResult(info);
            } catch (Exception e) {
                showYS_Dialog("提示", "读卡失败, 请重试", true);
            }

        } else {
            showYS_Dialog("提示", "读卡失败, 请重试", true);
        }

    }

    private String trim(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    public Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        if (mReadType == PlugInfo.OTG) {
            try {
                this.unregisterReceiver(mUsbReceiver);
            } catch (Exception e) {

            }
        }
        super.onDestroy();
        mHandler = null;
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            showYS_Dialog("提示", "正在读卡,请稍候", true);
                            mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_OTGBUTTON, 100);
                        }
                    } else {
                        showYS_Dialog("提示", "OTG初始化失败", true);
                    }
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (mReadType == PlugInfo.NFC) {
            stopNFC_Listener();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private Context getContext() {
        return this;
    }
}
