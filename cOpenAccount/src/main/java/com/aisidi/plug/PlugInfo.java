package com.aisidi.plug;

import android.os.Parcel;
import android.os.Parcelable;

public class PlugInfo implements Parcelable {
	public static final int BT = 1;
	public static final int OTG = 2;
	public static final int NFC = 3;
	public static final int OTHER1 = 4; // 额外的1
	public static final int OTHER2 = 5; // 额外的2
	private int version;
	private String url = "";
	private int type;
	private String address = ""; // 蓝牙的地址
	private String arg1 = "";
	private String arg2 = "";
	public PlugInfo() {
	}

	

	public PlugInfo(int version, String url, int type, String address,
                    String arg1, String arg2) {
		this.version = version;
		this.url = url;
		this.type = type;
		this.address = address;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}



	public PlugInfo(Parcel in) {
		version = in.readInt();
		url = in.readString();
		type = in.readInt();
		address = in.readString();
		arg1 = in.readString();
		arg2 = in.readString();
	}

	public static final Creator<PlugInfo> CREATOR = new Creator<PlugInfo>() {
		@Override
		public PlugInfo createFromParcel(Parcel in) {
			return new PlugInfo(in);
		}

		@Override
		public PlugInfo[] newArray(int size) {
			return new PlugInfo[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(version);
		dest.writeString(url);
		dest.writeInt(type);
		dest.writeString(address);
		dest.writeString(arg1);
		dest.writeString(arg2);
	}

	public int getVersion() {
		return version;
	}

	public String getUrl() {
		return url;
	}

	public int getType() {
		return type;
	}

	public String getAddress() {
		return address;
	}

	public String getArg1() {
		return arg1;
	}

	public String getArg2() {
		return arg2;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}
}
