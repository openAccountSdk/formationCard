package com.uyou.copenaccount.reader.base;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aisidi.plug.ResultInfo;
import com.uyou.copenaccount.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragmentAct extends FragmentActivity implements
		Frg_bt_list.BTAddressCallBack {

	private FragmentManager fragmentManager;
	protected List<Fragment> fragments;

	protected Frg_one_btn frg_one_btn;//用于一般显示
	protected Frg_two_btn frg_two_btn;//用于显示蓝牙设置
	protected Frg_bt_list frg_bt_list;//用于显示蓝牙列表

	private int index = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reader_activity_ys);
		initFragment();
	}

	/**
	 * 必须设置返回数据
	 */
	protected void setActResult(ResultInfo info) {
		Intent intent = new Intent();
		intent.putExtra("result", info);
		//设置数据，返回给调用它的Activity
		setResult(RESULT_OK, intent);
		//退出当前插件，关闭插件页面
		this.finish();
	}

	/**
	 * 初始化Fragment
	 */
	protected void initFragment() {
		fragments = new ArrayList<Fragment>();
		frg_one_btn = Frg_one_btn.newInstance(0);
		fragments.add(frg_one_btn);
		frg_two_btn = Frg_two_btn.newInstance(1);
		fragments.add(frg_two_btn);
		frg_bt_list = Frg_bt_list.newInstance(2);
		frg_bt_list.setBTAddressCallBack(BaseFragmentAct.this);
		fragments.add(frg_bt_list);
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.activity_main, fragments.get(0), "0");
		transaction.add(R.id.activity_main, fragments.get(1), "1");
		transaction.add(R.id.activity_main, fragments.get(2), "2");
		for (Fragment f : fragments) {
			Bundle bundle = f.getArguments();
			if (bundle.getInt("index") == 0) {
				transaction.show(f);
				index = 0;
			} else
				transaction.hide(f);
		}
		transaction.commit();
	}

	/**
	 * 切换要显示的Fragment
	 * 
	 * @param index
	 *            序号
	 */
	protected void changeFrg(int index) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (index < 0 || index >= fragments.size())
			return;
		for (Fragment f : fragments) {
			Bundle bundle = f.getArguments();
			if (bundle.getInt("index") == index) {
				transaction.show(f);
				this.index = index;
			} else
				transaction.hide(f);
		}
		transaction.commit();
	}

	public int getFragmentIndex() {
		return index;
	}

	@SuppressWarnings("unchecked")
	protected <T extends Fragment> T getFragmentByIndex(int index) {
		if (index < 0 || index >= fragmentManager.getFragments().size())
			return null;
		for (Fragment f : fragmentManager.getFragments()) {
			Bundle bundle = f.getArguments();
			if (bundle.getInt("index") == index)
				return (T) f;
		}
		return null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		return;
	}

	/**
	 * 蓝牙地址的回调
	 */
	@Override
	public abstract void address(boolean success, String name, String address);
}
