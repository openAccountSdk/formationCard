package com.uyou.copenaccount.utils.net.tool;

/**
 * Created by zdd on 2018/11/28.
 */

public class Sign {

    public Sign() {
    }

    public static String getSign(String transid, String reqtime, String custid, String servername) {
        String mySign = null;

        try {
            String privateKey = MD5Encrypt.Encrypt(custid).substring(0, 10);
            StringBuffer suff = new StringBuffer();
            suff
                    .append(transid)
                    .append("|")
                    .append(reqtime)
                    .append("|")
                    .append(custid)
                    .append("|")
                    .append(servername)
                    .append("|")
                    .append(privateKey);
            mySign = MD5Encrypt.Encrypt(suff.toString()).toLowerCase();
        } catch (Exception var8) {
            var8.printStackTrace();
            System.out.println(" 签名生成失败！ ");
        }

        return mySign;
    }
}
