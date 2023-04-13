package com.uyou.copenaccount.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.view.SeparateCheckedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zdd on 2020/6/19.
 * <p>
 * Description:
 */
public class ProductPackageSpinnerAdapter<T> extends ArrayAdapter<T> {

    private int colorSeparate;

    public ProductPackageSpinnerAdapter(Context context) {
        super(context, R.layout.bus_adapter_spinner_show, R.id.bus_txt_item, new ArrayList<>());
        setDropDownViewResource(R.layout.bus_adapter_spinner_check_style);
        colorSeparate = Color.parseColor("#cccccc");
    }

    public void setNewData(List<T> data) {
        if (data == null) {
            clear();
        } else {
            setNotifyOnChange(false);
            clear();
            setNotifyOnChange(true);
            addAll(data);
        }
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        View item = view.findViewById(R.id.bus_txt_item);
        if (item != null && item instanceof SeparateCheckedTextView) {
            SeparateCheckedTextView txt = (SeparateCheckedTextView) item;
            if (position + 1 == getCount()) {
                txt.setSeparateColor(0);
            } else {
                txt.setSeparateColor(colorSeparate);
            }
        }
        return view;
    }

}
