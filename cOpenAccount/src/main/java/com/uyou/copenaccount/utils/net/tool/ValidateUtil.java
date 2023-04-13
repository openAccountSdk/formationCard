package com.uyou.copenaccount.utils.net.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zdd on 2018/11/28.
 */

public class ValidateUtil {

    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    public static final String MOBILE = "^1[3587]\\d{9}$";
    public static final String IPADDRESS = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";
    public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";
    public static final String INTEGER_NEGATIVE = "^[1-9]\\d*|0$";
    public static final String INTEGER_POSITIVE = "^-[1-9]\\d*|0$";
    public static final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
    public static final String DOUBLE_NEGATIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
    public static final String DOUBLE_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
    public static final String AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
    public static final String CODE = "[1-9]\\d{5}(?!\\d)";
    public static final String STR_ENG_NUM_ = "^\\w+$";
    public static final String STR_ENG_NUM = "^\\w+$";
    public static final String STR_ENG = "^[A-Za-z]+$";
    public static final String STR_CHINA = "^[/u0391-/uFFE5]+$";
    public static final String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    public static final String STR_ENG_CHA_NUM = "^[a-zA-Z0-9/u4e00-/u9fa5]+$";
    public static final String DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-///\\._]?)(10|12|0?[13578])([-///\\._]?)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-///\\._]?)(11|0?[469])([-///\\._]?)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-///\\._]?)(0?2)([-///\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([3579][26]00)([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([1][89][0][48])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([2-9][0-9][0][48])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([1][89][2468][048])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([1][89][13579][26])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([2-9][0-9][13579][26])([-///\\._]?)(0?2)([-///\\._]?)(29)$))";
    public static final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(//?)?(((\\w*%)*(\\w*//?)*(\\w*:)*(\\w*//+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*//?)*(\\w*:)*(\\w*//+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
    public static final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
    public static final String SCIENTIFIC_A = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$";
    public static final String SCIENTIFIC_B = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$";
    public static final String SCIENTIFIC_C = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$";
    public static final String SCIENTIFIC_D = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$";

    public ValidateUtil() {
    }

    public static synchronized boolean StrisNull(String str) {
        return "null".equals(str)?true:str == null || str.trim().length() <= 0;
    }

    public static boolean StrNotNull(String str) {
        return !StrisNull(str);
    }

    public static String nulltoStr(String str) {
        return StrisNull(str)?"":str;
    }

    public static String nulltoStr(String str, String defaut) {
        return StrisNull(str)?defaut:str;
    }

    public static boolean isEmail(String str) {
        return Regular(str, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public static boolean isPhone(String str) {
        return Regular(str, "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)");
    }

    public static boolean isMobile(String str) {
        return Regular(str, "^1[3587]\\d{9}$");
    }

    public static boolean isUrl(String str) {
        return Regular(str, "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(//?)?(((\\w*%)*(\\w*//?)*(\\w*:)*(\\w*//+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*//?)*(\\w*:)*(\\w*//+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$");
    }

    public static boolean isIpaddress(String str) {
        return Regular(str, "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))");
    }

    public static boolean isNumber(String str) {
        return Regular(str, "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");
    }

    public static boolean isInteger(String str) {
        return Regular(str, "^-?(([1-9]\\d*$)|0)");
    }

    public static boolean isINTEGER_NEGATIVE(String str) {
        return Regular(str, "^[1-9]\\d*|0$");
    }

    public static boolean isINTEGER_POSITIVE(String str) {
        return Regular(str, "^-[1-9]\\d*|0$");
    }

    public static boolean isDouble(String str) {
        return Regular(str, "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");
    }

    public static boolean isDOUBLE_NEGATIVE(String str) {
        return Regular(str, "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$");
    }

    public static boolean isDOUBLE_POSITIVE(String str) {
        return Regular(str, "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$");
    }

    public static boolean isDate(String str) {
        return Regular(str, "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-///\\._]?)(10|12|0?[13578])([-///\\._]?)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-///\\._]?)(11|0?[469])([-///\\._]?)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-///\\._]?)(0?2)([-///\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([3579][26]00)([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([1][89][0][48])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([2-9][0-9][0][48])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([1][89][2468][048])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([1][89][13579][26])([-///\\._]?)(0?2)([-///\\._]?)(29)$)|(^([2-9][0-9][13579][26])([-///\\._]?)(0?2)([-///\\._]?)(29)$))");
    }

    public static boolean isAge(String str) {
        return Regular(str, "^(?:[1-9][0-9]?|1[01][0-9]|120)$");
    }

    public static boolean isLengOut(String str, int leng) {
        return StrisNull(str)?false:str.trim().length() > leng;
    }

    public static boolean isIdCard(String str) {
        return StrisNull(str)?false:(str.trim().length() != 15 && str.trim().length() != 18?false:Regular(str, "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))"));
    }

    public static boolean isCode(String str) {
        return Regular(str, "[1-9]\\d{5}(?!\\d)");
    }

    public static boolean isChina(String str) {
        return Regular(str, "^[/u0391-/uFFE5]+$");
    }

    public static boolean isEnglish(String str) {
        return Regular(str, "^[A-Za-z]+$");
    }

    public static boolean isENG_NUM(String str) {
        return Regular(str, "^\\w+$");
    }

    public static boolean isENG_NUM_(String str) {
        return Regular(str, "^\\w+$");
    }

    public static String filterStr(String str) {
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    private static boolean Regular(String str, String pattern) {
        if(str != null && str.trim().length() > 0) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            return m.matches();
        } else {
            return false;
        }
    }

    public static boolean isScientific(String str) {
        return StrisNull(str)?false:Regular(str, "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$");
    }
}
