package com.uyou.copenaccount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.bean.DeviceBean;
import com.uyou.copenaccount.model.OpenTypeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zdd on 2019/5/27.
 * <p>
 * Description:
 */
public class DevicesSpinnerAdapter extends BaseAdapter {

    private List<DeviceBean> mData;
    private Context mContext;

    public DevicesSpinnerAdapter(Context context) {
        mContext = context;
        // 初始化的时候 设置一个亿数的设备填充
        //setNewData(initDevice());
    }

    public void setNewData(List<DeviceBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public DeviceBean getItem(int position) {
        if (mData == null) {
            return null;
        }
        if (position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bus_adapter_spinner, parent, false);
            holder.txtItem = convertView.findViewById(R.id.bus_txt_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DeviceBean info = mData.get(position);
        holder.txtItem.setText(info.getDevice_name());
        return convertView;
    }

    class ViewHolder {
        TextView txtItem;
    }

    private List<DeviceBean> initDevice() {
        List<DeviceBean> list = new ArrayList<>();
        DeviceBean bean = new DeviceBean();
        bean.setDevice_name("南京亿数");
        bean.setDevice_code("YS");
        List<OpenTypeModel> su = new ArrayList<>();
        OpenTypeModel s = new OpenTypeModel("身份证阅读器", 1);
        su.add(s);
        OpenTypeModel s1 = new OpenTypeModel("蓝牙识别", 3);
        su.add(s1);
        OpenTypeModel s2 = new OpenTypeModel("NFC手机扫描", 2);
        su.add(s2);
        bean.setSupport_open_type(su);
        list.add(bean);
        return list;
    }
}
