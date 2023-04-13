package com.uyou.copenaccount.reader.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.uyou.copenaccount.R;


/**
 * 提示
 * <p>
 * 正在识别身份证，请稍后...
 * <p>
 * 取消
 */
public class Frg_one_btn extends Fragment {

    private View view = null;
    public static int index = -1;
    private TextView message, title_tv;
    private Button button_one_btn;
    private String message_str = "正在识别身份证，请稍后...";
    private boolean isenable = true;

    public static Frg_one_btn newInstance(int index) {
        Frg_one_btn f = new Frg_one_btn();
        Bundle args = new Bundle();
        args.putInt("index", index);
        Frg_one_btn.index = index;
        f.setArguments(args);
        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        if (view != null) // 复用View。
            return view;
        view = inflater.inflate(R.layout.reader_dialog, container, false);
        message = (TextView) view.findViewById(R.id.message);
        title_tv = (TextView) view.findViewById(R.id.title_tv);
        button_one_btn = (Button) view.findViewById(R.id.button_one_btn);
        button_one_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				if()
                getActivity().finish();
            }
        });
        return view;
    }

    public void setButtonEnabled(boolean enabled) {
        button_one_btn.setEnabled(enabled);
    }

    public void setMessage(String str) {
        message.setText(str);
    }

    public void setTitle(String str) {
        title_tv.setText(str);
    }

    public void setBtnEven(String str, OnClickListener listener) {
        button_one_btn.setText(str);
        button_one_btn.setOnClickListener(listener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv = (TextView) view.findViewById(R.id.message);
        tv.setText(message_str);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (null != view) {
//            ((ViewGroup) view.getParent()).removeView(view);
//        }
    }

}
