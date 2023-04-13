package com.uyou.copenaccount.utils;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zdd on 2019/3/25.
 * <p>
 * Description:
 */
public class DeviceIdUtils {

    public static String getIMEI(Context context) {
        String imei = null;
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String readPhoneStatePermission = Manifest.permission.READ_PHONE_STATE;
            if (Build.VERSION.SDK_INT >= 26 && Build.VERSION.SDK_INT < 29) {
                if (ContextCompat.checkSelfPermission(context, readPhoneStatePermission) == PackageManager.PERMISSION_GRANTED) {
                    imei = manager.getImei();
                }
            } else if (Build.VERSION.SDK_INT < 26) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, readPhoneStatePermission) == PackageManager.PERMISSION_GRANTED) {
                        imei = getDeviceId(manager);
                    }
                } else {
                    imei = getDeviceId(manager);
                }
            }
        } catch (Exception e) {

        }
        if (TextUtils.isEmpty(imei)) {
            imei = getAndroidID(context);
        }
        if (TextUtils.isEmpty(imei)) {
            imei = DeviceIdUtils.getid(context);
        }
        if (imei == null) {
            imei = "";
        }
        return imei;
    }

    private static String getDeviceId(TelephonyManager manager) {
        String imei = manager.getDeviceId();
        if (imei == null || imei == "") {
            imei = manager.getDeviceId(TelephonyManager.PHONE_TYPE_NONE);
        }
        if (imei == null || imei == "") {
            imei = manager.getDeviceId(TelephonyManager.PHONE_TYPE_GSM);
        }
        if (imei == null || imei == "") {
            imei = manager.getDeviceId(TelephonyManager.PHONE_TYPE_CDMA);
        }
        if (imei == null || imei == "") {
            imei = manager.getDeviceId(TelephonyManager.PHONE_TYPE_SIP);
        }
        return imei;
    }

    private static String getAndroidID(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * @param context
     * @return 加密后的字符串
     * @Description: 获取设备的唯一ID，经过多种编号混合,然后经过MD5加密
     * （默认混合数据类型包括：IMEI、Pseudo-Unique ID、Android ID、MAC地址、蓝牙MAC地址
     * --->如果要自定义加密类型，需要手动修改代码）
     */
    public static String getid(Context context) {
        String szImei = ""; // IMEI
        String m_szDevIDShort = "";// Pseudo-Unique ID
        String m_szAndroidID = "";//The Android ID
        String m_szWLANMAC = "";// MAC 地址
        String m_szBTMAC = "";// 蓝牙的MAC
        /**
         * 采用此种方法，需要在AndroidManifest.xml中加入一个许可：android.permission.READ_PHONE_STATE，
         * 并且用户应当允许安装此应用。作为手机来讲，IMEI是唯一的，它应该类似于
         * 359881030314356（除非你有一个没有量产的手机（水货）它可能有无效的IMEI，如：0000000000000）。
         */
        szImei = "";
        if (Build.VERSION.SDK_INT < 29) {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                szImei = TelephonyMgr.getDeviceId(); // The IMEI: 仅仅只对Android手机有效
            } catch (Exception e) {

            }
        }
        if (szImei == null) {
            szImei = "";
        }

        /**
         * Pseudo-Unique ID, 这个在任何Android手机中都有效。
         * 有一些特殊的情况，一些如平板电脑的设置没有通话功能，或者你不愿加入READ_PHONE_STATE许可
         * 。而你仍然想获得唯一序列号之类的东西。
         * 这时你可以通过取出ROM版本、制造商、CPU型号、以及其他硬件信息来实现这一点。
         * 这样计算出来的ID不是唯一的（因为如果两个手机应用了同样的硬件以及Rom镜像）。
         *
         * 但应当明白的是，出现类似情况的可能性基本可以忽略。要实现这一点，你可以使用Build类:
         */
        m_szDevIDShort = "35"
                + // we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10
                + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
                + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
                + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                + Build.USER.length() % 10;

        /**
         * The Android ID
         * 这种方式通常被认为不可信，因为它有时为null。
         * 开发文档中说明了：这个ID会改变如果进行了出厂设置。并且，
         * 如果某个Andorid手机被Root过的话，这个ID也可以被任意改变。
         *
         * Returns: 9774d56d682e549c .无需任何许可
         */
        m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        /**
         * The WLAN MAC Address string 。
         * 需要加入权限：android.permission.ACCESS_WIFI_STATE
         * Returns: 00:11:22:33:44:55(这不是一个真实的地址。而且这个地址能轻易地被伪造。).WLan不必打开，就可读取些值。
         */
        // 这里在android 10.0上会获取随机地址
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            m_szWLANMAC = "02:00:00:00:00:00";
        } else {
            try {
                WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            } catch (Exception e) {

            }
        }

        /**
         * The BT MAC Address string
         * 只在有蓝牙的设备上运行。并且要加入android.permission.BLUETOOTH 权限.
         *
         * Returns: 43:25:78:50:93:38 . 蓝牙没有必要打开，也能读取
         */
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        m_szBTMAC = "";
        try {
            m_szBTMAC = m_BluetoothAdapter.getAddress();
        } catch (Exception e) {

        }
        if (m_szBTMAC == null) {
            m_szBTMAC = "";
        }


        String m_szLongID = szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
        // compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper
            // padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        } // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }
}
