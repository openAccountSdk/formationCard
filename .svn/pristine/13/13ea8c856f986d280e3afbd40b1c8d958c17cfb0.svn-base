package com.uyou.copenaccount.utils;


import static com.uyou.copenaccount.base.UCConstants.TOAST_LOCATION_FAIL;
import static com.uyou.copenaccount.base.UCConstants.TOAST_LOCATION_FAIL_EMPTY;
import static com.uyou.copenaccount.base.UCConstants.TOAST_LOCATION_FAIL_KEY;
import static com.uyou.copenaccount.base.UCConstants.TOAST_LOCATION_FAIL_NET;
import static com.uyou.copenaccount.base.UCConstants.TOAST_LOCATION_FAIL_RETRY;
import static com.uyou.copenaccount.base.UCConstants.TOAST_LOCATION_FAIL_RE_GEOCODE;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.uyou.copenaccount.base.UCConstants;

/**
 * Created by zdd on 2019/8/28.
 * <p>
 * Description:
 */
public class LocationUtils {

    private static final String TAG = "LocationUtils";
    /**
     * 处理直接定位信息
     */
    public static void dealLocationResult(final Context context, AMapLocation aMapLocation, final OnLocationResultListener listener) {
        // 每次定位结果清除临时经纬度
        if (context != null && aMapLocation != null) {
            int code = aMapLocation.getErrorCode();
            ULogger.e(TAG, "location result : " + code + " , " + aMapLocation.getErrorInfo());
            switch (code) {
                case 0: // 定位成功
                    ULogger.e(TAG, "location : " + aMapLocation.toStr());

                    final double latitude = aMapLocation.getLatitude();
                    final double longitude = aMapLocation.getLongitude();
                    // 若经纬度都为0, 则表示定位失败
                    if (latitude == 0 && longitude == 0) {
                        if (listener != null) listener.onFail(-1, TOAST_LOCATION_FAIL_EMPTY);
                    } else {
                        // 经过验证 下面代码调用次数不多, 基本每次定位都会有些偏差, 如果需要, 应更精确判断而不是模糊的直接相等
                        // 判断缓存定位和此次定位是否相同
//                        String latStr = getLat(context);
//                        String lngStr = getLng(context);
//                        if (String.valueOf(latitude).equals(latStr) && String.valueOf(longitude).equals(lngStr)) {
//                            // 若相同, 且缓存地址有信息, 不再调用请求反地理编码, 直接返回成功
//                            // 若不同, 或缓存地址为空, 请求反地理编码
//                            String province = getProvince(context);
//                            String detail = getDetail(context);
//                            if (!TextUtils.isEmpty(province) && !TextUtils.isEmpty(detail)) {
//                                Timber.e("使用缓存定位, 不再反地理编码");
//                                if (listener != null) {
//                                    listener.onSuccess();
//                                }
//                                return;
//                            }
//                        }

                        // 请求反地理编码
                        try {
                            GeocodeSearch search = new GeocodeSearch(context);
                            search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                                @Override
                                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int code) {
                                    // 处理反地理编码结果
                                    dealRegeocodeSearchedResult(
                                            context,
                                            latitude,
                                            longitude,
                                            regeocodeResult,
                                            code,
                                            listener);
                                }

                                @Override
                                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                                }
                            });
                            LatLonPoint point = new LatLonPoint(latitude, longitude);
                            // 请求反地理编码
                            search.getFromLocationAsyn(new RegeocodeQuery(point, 500, GeocodeSearch.AMAP));
                        } catch (AMapException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case 1: // 一些重要参数为空
                    if (listener != null) listener.onFail(code, TOAST_LOCATION_FAIL + code);
                    break;
                case 2: // 定位失败，由于仅扫描到单个wifi，且没有基站信息
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请检查您的网络和定位权限后重试");
                    break;
                case 3: // 获取到的请求参数为空，可能获取过程中出现异常。
                    if (listener != null) listener.onFail(code, TOAST_LOCATION_FAIL_NET + code);
                    break;
                case 4: // 请求服务器过程中的异常，多为网络情况差，链路不通导致
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL_NET + code);
                    break;
                case 5: // 请求被恶意劫持，定位结果解析失败
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请求被恶意劫持, 定位结果解析失败");
                    break;
                case 6: // 定位服务返回定位失败
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请检查您的网络和定位权限后重试。" + getErrorMsg(aMapLocation.getLocationDetail()));
                    break;
                case 7: // KEY鉴权失败
                    if (listener != null) listener.onFail(code, TOAST_LOCATION_FAIL_KEY);
                    break;
                case 8: // Android exception常规错误
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + "。" + getErrorMsg(aMapLocation.getLocationDetail()));
                    break;
                case 9: // 定位初始化时出现异常
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 初始化失败, 请重试或重启应用");
                    break;
                case 10: // 定位客户端启动失败
                    if (listener != null) listener.onFail(code, TOAST_LOCATION_FAIL + code);
                    break;
                case 11: // 定位时的基站信息错误
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 基站信息错误, 请重试");
                    break;
                case 12: // 缺少定位权限
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请检查您的网络, 并查看应用是否拥有定位权限或打开手机定位服务");
                    break;
                case 13: // 定位失败，由于未获得WIFI列表和基站信息，且GPS当前不可用
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请检查GPS/WIFI是否开启, 或应用是否拥有定位权限");
                    break;
                case 14: // GPS 定位失败，由于设备当前 GPS 状态差
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", GPS信号差, 请重试");
                    break;
                case 15: // 定位结果被模拟导致定位失败
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请勿使用模拟定位");
                    break;
                case 18: // 定位失败，由于手机WIFI功能被关闭同时设置为飞行模式
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请不要设置为飞行模式");
                    break;
                case 19: // 定位失败，由于手机没插sim卡且WIFI功能被关闭
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请插上SIM卡，或打开WIFI开关, 确保网络畅通后重试");
                    break;
                default:
                    if (listener != null)
                        listener.onFail(code, TOAST_LOCATION_FAIL + code + ", 请检查您的网络和定位权限后重试");
                    break;
            }
        } else {
            if (listener != null)
                listener.onFail(-1, TOAST_LOCATION_FAIL_RETRY);
        }
    }

    /**
     * 处理反地理编码信息
     */
    private static void dealRegeocodeSearchedResult(Context context, double lat, double lng, RegeocodeResult result, int code, OnLocationResultListener listener) {
        if (context != null) {
            if (result != null) {
                if (code == 1000) {
                    // 保存地址
                    RegeocodeAddress geocodeAddress = result.getRegeocodeAddress();
                    String country = geocodeAddress.getCountry(); //
                    String province = geocodeAddress.getProvince();//省
                    String city = geocodeAddress.getCity();//市
                    String district = geocodeAddress.getDistrict();//区
                    String town = geocodeAddress.getTownship(); // 镇
                    String road = geocodeAddress.getNeighborhood(); //所在街道
                    String poiName = geocodeAddress.getBuilding();//PIO名称
                    String cityCode = geocodeAddress.getCityCode();// 城市编码
                    String adCode = geocodeAddress.getAdCode();// 区域编码
                    String detail = geocodeAddress.getFormatAddress(); // 详细地址
                    String detail_address = province + city + district + town + road + poiName; // 详细地址(拼接)
                    if (TextUtils.isEmpty(detail)) {
                        detail = detail_address;
                    }

                    ULogger.e(TAG, "country : " + country + ", province : " + province + ", city : " + city + ", district : " + district + ", town : " + town + ", road : " + road + ", poiName : " + poiName + ", detail : " + detail);

                    // || TextUtils.isEmpty(city) 省直辖县级行政单位 高德地图获取不到市
                    // 如 湖北省：仙桃市、潜江市、天门市, 河南省：济源市 等
                    if (TextUtils.isEmpty(province) || TextUtils.isEmpty(detail)) {
                        if (listener != null)
                            listener.onFail(-3, TOAST_LOCATION_FAIL_RE_GEOCODE + ", CODE - 3");
                    } else {
                        AppConfigs.setStringSF(context, UCConstants.SHARE_LATITUDE, String.valueOf(lat));
                        AppConfigs.setStringSF(context, UCConstants.SHARE_LONGITUDE, String.valueOf(lng));

                        // 省级直辖县, 定位取不到市, 需要从区级单位取值
                        if (TextUtils.isEmpty(city)) {
                            city = district;
                        }

                        AppConfigs.setStringSF(context, UCConstants.SHARE_COUNTRY, country);
                        AppConfigs.setStringSF(context, UCConstants.SHARE_PROVINCE, province);
                        AppConfigs.setStringSF(context, UCConstants.SHARE_CITY, city);
                        AppConfigs.setStringSF(context, UCConstants.SHARE_ADDRESS_DISTRICT, district);
                        AppConfigs.setStringSF(context, UCConstants.SHARE_TOWNSHIP, town);
                        AppConfigs.setStringSF(context, UCConstants.SHARE_ADDRESS_DETAIL, detail);

                        // 成功回调
                        if (listener != null) listener.onSuccess();
                    }
                } else {
                    if (listener != null)
                        listener.onFail(-2, TOAST_LOCATION_FAIL_RE_GEOCODE + ", CODE - 2(" + code + ")");
                }
            } else {
                if (listener != null)
                    listener.onFail(-4, TOAST_LOCATION_FAIL_RE_GEOCODE + ", CODE - 4");
            }
        } else {
            if (listener != null)
                listener.onFail(-1, TOAST_LOCATION_FAIL_RE_GEOCODE + ", CODE - 1");
        }
    }

    /**
     * 清除定位信息
     */
    public static void clearLocation(Context context) {
        if (context != null) {
            AppConfigs.setStringSF(context, UCConstants.SHARE_LATITUDE, "");
            AppConfigs.setStringSF(context, UCConstants.SHARE_LONGITUDE, "");
            AppConfigs.setStringSF(context, UCConstants.SHARE_COUNTRY, "");
            AppConfigs.setStringSF(context, UCConstants.SHARE_PROVINCE, "");
            AppConfigs.setStringSF(context, UCConstants.SHARE_CITY, "");
            AppConfigs.setStringSF(context, UCConstants.SHARE_ADDRESS_DETAIL, "");
            AppConfigs.setStringSF(context, UCConstants.SHARE_ADDRESS_DISTRICT, "");
        }
    }

    public static String getLat(Context context) {
        if (context != null) {
            return AppConfigs.getStringSF(context, UCConstants.SHARE_LATITUDE);
        }
        return "";
    }

    public static String getLng(Context context) {
        if (context != null) {
            return AppConfigs.getStringSF(context, UCConstants.SHARE_LONGITUDE);
        }
        return "";
    }

    public static String getCountry(Context context) {
        if (context != null) {
            return AppConfigs.getStringSF(context, UCConstants.SHARE_COUNTRY);
        }
        return "";
    }

    public static String getProvince(Context context) {
        if (context != null) {
            return AppConfigs.getStringSF(context, UCConstants.SHARE_PROVINCE);
        }
        return "";
    }

    public static String getCity(Context context) {
        if (context != null) {
            return AppConfigs.getStringSF(context, UCConstants.SHARE_CITY);
        }
        return "";
    }

    public static String getTownship(Context context) {
        if (context != null) {
            return AppConfigs.getStringSF(context, UCConstants.SHARE_TOWNSHIP);
        }
        return "";
    }

    public static String getDetail(Context context) {
        if (context != null) {
            return AppConfigs.getStringSF(context, UCConstants.SHARE_ADDRESS_DETAIL);
        }
        return "";
    }

    public static String getDistrict(Context context) {
        if (context != null) {
            return AppConfigs.getStringSF(context, UCConstants.SHARE_ADDRESS_DISTRICT);
        }
        return "";
    }

    private static String getErrorMsg(String msg) {
        if (msg == null) {
            return "";
        }
        if (msg.length() < 50) {
            return msg;
        }
        return msg.substring(0, 48) + "..";
    }

    public interface OnLocationResultListener {
        void onSuccess();

        void onFail(int code, String message);
    }

}
