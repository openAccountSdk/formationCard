package com.uyou.copenaccount.utils.net.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zdd on 2018/11/28.
 */

public class StringUtil {

    public static final String EMPTY_STRING = "";

    public StringUtil() {
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    public static boolean isBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String checkNull(String str) {
        if (str == null) return "";
        return str;
    }

    public static boolean isNotBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static int indexOf(String str, String searchStr) {
        return str != null && searchStr != null ? str.indexOf(searchStr) : -1;
    }

    public static int indexOf(String str, String searchStr, int startPos) {
        return str != null && searchStr != null ? (searchStr.length() == 0 && startPos >= str.length() ? str.length() : str.indexOf(searchStr, startPos)) : -1;
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }

            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }

                return str.substring(start, end);
            }
        }
    }

    public static boolean contains(String str, String searchStr) {
        return str != null && searchStr != null ? str.indexOf(searchStr) >= 0 : false;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        } else {
            int sz = str.length();

            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public static String getEightTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String getLastDayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime()) + "235959";
    }

    public static String getNextMonthFrist() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);
        lastDate.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(lastDate.getTime()) + "000000";
    }

    public static int toInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean is1705MobileNo(String mobiles) {
        if (mobiles != null && !"".equals(mobiles)) {
            Pattern p = Pattern.compile("^(1705)\\d{7}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } else {
            return false;
        }
    }

    public static boolean matchDate(String date) {
        boolean ans = true;

        try {
            ans = (new SimpleDateFormat("yyyyMMddhhmmss")).parse(date).getTime() - (new Date()).getTime() > 0L;
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return ans;
    }

    public static boolean matchDate1709(String date) {
        boolean ans = true;

        try {
            ans = (new SimpleDateFormat("yyyyMMddhhmmss")).parse(date).getTime() - (new Date()).getTime() > 0L;
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return ans;
    }

    public static boolean matchDate1700(String date) {
        boolean ans = true;

        try {
            ans = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(date).getTime() - (new Date()).getTime() > 0L;
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return ans;
    }

}
