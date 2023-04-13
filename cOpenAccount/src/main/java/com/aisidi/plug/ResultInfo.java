package com.aisidi.plug;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 序列化的类
 *
 * @author 王攀
 */
public class ResultInfo implements Parcelable {

    // 注意，这里变量生命的顺序不能变
    private int code;// 是否读取成功 200成功
    private String device = ""; // 设备的厂商
    private String device_id = "";// 设备的ID
    private int device_type;// 使用设备的识别类型
    private String message; // 信息
    private String agr1; // 预留字段1

    private IDCardInfo cardInfo;// 身份证信息

    public ResultInfo() {

    }

    /**
     * @param code
     * @param device
     * @param device_id
     * @param device_type
     * @param message
     * @param agr1
     * @param cardInfo
     */
    public ResultInfo(int code, String device, String device_id,
                      int device_type, String message, String agr1, IDCardInfo cardInfo) {
        this.code = code;
        this.device = device;
        this.device_id = device_id;
        this.device_type = device_type;
        this.message = message;
        this.agr1 = agr1;
        this.cardInfo = cardInfo;
    }

    public int getCode() {
        return code;
    }

    public String getDevice() {
        return device;
    }

    public String getDevice_id() {
        return device_id;
    }

    public int getDevice_type() {
        return device_type;
    }

    public String getMessage() {
        return message;
    }

    public String getAgr1() {
        return agr1;
    }

    public IDCardInfo getCardInfo() {
        return cardInfo;
    }

    protected ResultInfo(Parcel in) {
        code = in.readInt();
        device = in.readString();
        device_id = in.readString();
        device_type = in.readInt();
        message = in.readString();
        agr1 = in.readString();
        cardInfo = in.readParcelable(IDCardInfo.class.getClassLoader());
    }

    public static final Creator<ResultInfo> CREATOR = new Creator<ResultInfo>() {
        @Override
        public ResultInfo createFromParcel(Parcel in) {
            return new ResultInfo(in);
        }

        @Override
        public ResultInfo[] newArray(int size) {
            return new ResultInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(device); // 设备的厂商
        dest.writeString(device_id);// 设备的ID
        dest.writeInt(device_type);// 使用设备的识别类型
        dest.writeString(message); // 信息
        dest.writeString(agr1); // 预留字段1
        dest.writeParcelable(cardInfo, flags);

    }

    // ===================================================

    public static class IDCardInfo implements Parcelable {
        private String name = ""; // 姓名
        private String sex = ""; // 性别
        private String cardNo = ""; // 身份证号
        private String nation = "";// 民族
        private String born = ""; // 出生日期
        private String address = ""; // 地址
        private String authority = ""; // 签发机关
        private String date = ""; // 有效期截止日期

        private Bitmap img = null;  // 图片

        public IDCardInfo() {
        }


        public IDCardInfo(String name, String sex, String cardNo,
                          String nation, String born, String address, String authority,
                          String date, Bitmap img) {
            super();
            this.name = name;
            this.sex = sex;
            this.cardNo = cardNo;
            this.nation = nation;
            this.born = born;
            this.address = address;
            this.authority = authority;
            this.date = date;
            this.img = img;
        }

        protected IDCardInfo(Parcel in) {
            name = in.readString();
            sex = in.readString();
            cardNo = in.readString();
            nation = in.readString();
            born = in.readString();
            address = in.readString();
            date = in.readString();
            img = in.readParcelable(Bitmap.class.getClassLoader());
        }

        public static final Creator<IDCardInfo> CREATOR = new Creator<IDCardInfo>() {
            @Override
            public IDCardInfo createFromParcel(Parcel in) {
                return new IDCardInfo(in);
            }

            @Override
            public IDCardInfo[] newArray(int size) {
                return new IDCardInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(sex); // 设备的厂商
            dest.writeString(cardNo);// 设备的ID
            dest.writeString(nation);// 使用设备的识别类型
            dest.writeString(born); // 信息
            dest.writeString(address); // 预留字段1
            dest.writeString(date); // 有效期
            dest.writeParcelable(img, flags);

        }

        public String getCardNo() {
            return checkVal(cardNo);
        }

        public String getName() {
            return checkVal(name);
        }

        public String getSex() {
            return checkVal(sex);
        }

        public String getNation() {
            return checkVal(nation);
        }

        public String getBorn() {
            return checkVal(born);
        }

        public String getAddress() {
            return checkVal(address);
        }

        public Bitmap getImg() {
            return img;
        }

        public String getAuthority() {
            return checkVal(authority);
        }

        public String getDate() {
            return checkVal(date);
        }

        private String checkVal(String val) {
            if (val == null) return "";
            return val;
        }
    }
}