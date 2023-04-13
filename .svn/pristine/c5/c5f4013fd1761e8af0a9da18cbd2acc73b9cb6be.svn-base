package com.uyou.copenaccount.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.text.TextUtils;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by zdd on 2019/4/3.
 * <p>
 * Description:
 */
public class AppUtils {

    private static final String TAG_UPDATE_DIALOG = "need_to_update_and_alert_dialog";
    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /*
     *用途:判断蓝牙是否有效来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static boolean notHasBlueTooth() {
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            if (TextUtils.isEmpty(name)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /*
     *作者:赵星海
     *时间:2019/2/21 17:55
     *用途:依据是否存在光传感器来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static Boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (null == sensor8) {
            return true;
        } else {
            return false;
        }
    }

    /*
     *作者:赵星海
     *时间:2019/2/21 17:56
     *用途:根据部分特征参数设备信息来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static boolean isFeatures() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    /*
     *作者:赵星海
     *时间:2019/2/21 17:58
     *用途:根据CPU是否为电脑来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static boolean checkIsNotRealPhone() {
        String cpuInfo = readCpuInfo();
        if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
            return true;
        }
        return false;
    }

    /*
     *作者:赵星海
     *时间:2019/2/21 17:58
     *用途:根据CPU是否为电脑来判断是否为模拟器(子方法)
     *返回:String
     */
    public static String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {

        }
        return result;
    }

    /*
     *作者:赵星海
     *时间:2019/2/21 17:50
     *用途:检测模拟器的特有文件
     *返回:true 为模拟器
     */
    private static String[] known_pipes = {"/dev/socket/qemud", "/dev/qemu_pipe"};

    public static boolean checkPipes() {
        for (int i = 0; i < known_pipes.length; i++) {
            String pipes = known_pipes[i];
            File qemu_socket = new File(pipes);
            if (qemu_socket.exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是真机还是模拟器
     *
     * @param context
     * @return
     */
    public static boolean isAdopt(Context context) {
        IntentFilter intentFilter = new IntentFilter(

                Intent.ACTION_BATTERY_CHANGED);

        Intent batteryStatusIntent = context.registerReceiver(null, intentFilter);

        int voltage = batteryStatusIntent.getIntExtra("voltage", 99999);

        int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);

        if (((voltage == 0) && (temperature == 0))

                || ((voltage == 10000) && (temperature == 0))) {
//这是通过电池的伏数和温度来判断是真机还是模拟器

            return true;

        } else {
            return false;

        }

    }


}
