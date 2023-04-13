package com.uyou.copenaccount.reader.base;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.reader.YSReaderActivity;


/**
 * 蓝牙设备未设置!
 * <p>
 * 取消 | 去设置
 */
public class Frg_two_btn extends Fragment {
    private View view;
    public static int index = -1;

    private Button btn1, btn2;
    TextView tv;

    public static Frg_two_btn newInstance(int index) {
        Frg_two_btn f = new Frg_two_btn();
        Bundle args = new Bundle();
        args.putInt("index", index);
        Frg_two_btn.index = index;
        f.setArguments(args);
        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        if (view != null) // 复用View。
            return view;
        view = inflater.inflate(R.layout.reader_dialog_two, container, false);
        btn1 = (Button) view.findViewById(R.id.button_two_leftbtn);
        btn2 = (Button) view.findViewById(R.id.button_two_rightbtn);
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        btn2.setText("去设置");
        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ((YSReaderActivity) getActivity()).changeFrg(Frg_bt_list.index);
            }
        });
        tv = (TextView) view.findViewById(R.id.message);
        tv.setText("蓝牙设备未设置！");
        return view;
    }

    public void setMessage(String message) {
        tv.setText(message);
    }

    public void setBtnEnver(String str1, String str2,
                            OnClickListener listener1, OnClickListener listener2) {
        if (str1 != null)
            btn1.setText(str1);
        if (str2 != null)
            btn2.setText(str2);
        if (listener1 != null)
            btn1.setOnClickListener(listener1);
        if (listener2 != null)
            btn2.setOnClickListener(listener2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (null != view) {
//            ((ViewGroup) view.getParent()).removeView(view);
//        }
    }
}
