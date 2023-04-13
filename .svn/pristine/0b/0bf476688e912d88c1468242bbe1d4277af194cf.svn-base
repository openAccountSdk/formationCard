package com.uyou.copenaccount.utils;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zdd on 2019/3/15.
 * <p>
 * Description: String 工具类
 */
public class StringUtils {

    /**
     * 去null
     *
     * @param val
     * @return
     */
    public static String checkNull(String val) {
        return val == null ? "" : val;
    }


    /**
     * 即将所有的数字、字母、标点符号全部转为全角字符，
     * 使他们同汉字一样占用两个字节，这样就可以避免由于占位混乱导致的排版混乱问题
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        if (input == null) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    public static void setViewText(TextView view, String content) {
        if (view != null && content != null) {
            view.setText(content);
        }
    }

    /**
     * String转为int
     *
     * @param val
     * @return
     */
    public static int toInt(String val) {
        if (val == null) {
            return 0;
        }
        try {
            return Integer.valueOf(val);
        } catch (Exception e) {
            return 0;
        }
    }

    public static float toFloat(String val) {
        if (val == null) {
            return 0f;
        }
        try {
            return Float.valueOf(val);
        } catch (Exception e) {
            return 0f;
        }
    }

    public static double toDouble(String val) {
        if (val == null) {
            return 0f;
        }
        try {
            return Double.valueOf(val);
        } catch (Exception e) {
            return 0f;
        }
    }

    public static String getYMDFormTime(Date date) {
        return formatTime(date, "yyyy-MM-dd");
    }

    public static String formatTime(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 判断日期是否过期
     */
    public static boolean isDateBeforeToday(String date) {
        SimpleDateFormat ff = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date endDate = ff.parse(date);
            Calendar cToday = Calendar.getInstance();
            Calendar cEndDate = Calendar.getInstance();
            cEndDate.setTime(endDate);
            int yearEnd = cEndDate.get(Calendar.YEAR);
            int monthEnd = cEndDate.get(Calendar.MONTH);
            int dayEnd = cEndDate.get(Calendar.DAY_OF_MONTH);
            int yearToday = cToday.get(Calendar.YEAR);
            int monthToday = cToday.get(Calendar.MONTH);
            int dayToday = cToday.get(Calendar.DAY_OF_MONTH);
            if (yearEnd < yearToday) {
                return true;
            }
            if (yearEnd == yearToday && monthEnd < monthToday) {
                return true;
            }
            if (yearEnd == yearToday && monthEnd == monthToday && dayEnd < dayToday) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getViewText(TextView view) {
        if (view == null) {
            return null;
        }
        return view.getText().toString().trim();
    }
}
