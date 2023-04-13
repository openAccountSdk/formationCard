package com.uyou.copenaccount.utils.net.tool;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by zdd on 2018/11/28.
 */

public class TransactionUtil {

    private static final String transSt = "6099075116";

    public TransactionUtil() {
    }

    public static String getTransid() {
        return "6099075116" + StringUtil.getTimeStamp() + getRandomFourNum();
    }

    private static String getRandomFourNum() {
        Random random = new Random();
        return String.format("%04d", new Object[]{Integer.valueOf(random.nextInt(10000))});
    }

    public static boolean checkTransId(String transid) {
        if(ValidateUtil.StrisNull(transid)) {
            return false;
        } else if(transid.indexOf("6099075116") != 0) {
            return false;
        } else {
            String tem = transid.substring("6099075116".length(), transid.length() - getRandomFourNum().length());

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                sdf.parse(tem);
                return true;
            } catch (Exception var3) {
                var3.printStackTrace();
                return false;
            }
        }
    }
}
