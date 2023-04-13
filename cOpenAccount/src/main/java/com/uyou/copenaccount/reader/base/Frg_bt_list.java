package com.uyou.copenaccount.reader.base;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uyou.copenaccount.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 选择设备连接
 * <p>
 * JYxxxxxxx
 * SRxxxxxxxxx
 * <p>
 * 搜索
 */
public class Frg_bt_list extends Fragment {
    public static int index = -1;
    private View view;
    private BluetoothAdapter mBtAdapter;

    private String search = "正在搜索设备";
    private ListView list_news, list_pairs;
    private DevicesAdapter adapter_news, adapter_pairs;
    private View bt_list_progress, // 进度条
            bt_list_search_view // 搜索list
                    ;
    private BTAddressCallBack callBack;

    public static Frg_bt_list newInstance(int index) {
        Frg_bt_list f = new Frg_bt_list();
        Bundle args = new Bundle();
        args.putInt("index", index);
        Frg_bt_list.index = index;
        f.setArguments(args);
        return f;
    }

    public void setBTAddressCallBack(BTAddressCallBack addressCallBack) {
        callBack = addressCallBack;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (mBtAdapter != null) {
                mBtAdapter.cancelDiscovery();
                adapter_news.getData().clear();
                adapter_news.notifyDataSetChanged();
                bt_list_search_view.setVisibility(View.GONE);
                view.findViewById(R.id.bt_list_search).setVisibility(View.VISIBLE);
                finishDiscovery();
            }
        } else {
            setBTDevices();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBT();
    }

    private void registerBT() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.reader_dialog_search_bluetooth, container, false);
        return view;
    }

    private void setBTDevices() {
        // Log.i("tag", "设置蓝牙设备了");
        if (mBtAdapter == null)
            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter_pairs != null)
            adapter_pairs.getData().clear();
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if (pairedDevices.size() < 1) {
            adapter_pairs.addData(new DevicesBean("没有已经配对的设备", "点击按钮搜索附近设备"));
        }
        for (BluetoothDevice device : pairedDevices) {
            adapter_pairs.addData(new DevicesBean(device.getName(), device.getAddress()));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt_list_progress = view.findViewById(R.id.bt_list_progress);
        bt_list_search_view = view.findViewById(R.id.bt_list_search_view);
        list_news = (ListView) view.findViewById(R.id.bt_list_news);
        list_pairs = (ListView) view.findViewById(R.id.bt_list_paired);
        adapter_news = new DevicesAdapter();
        adapter_pairs = new DevicesAdapter();
        list_news.setAdapter(adapter_news);
        list_pairs.setAdapter(adapter_pairs);
        view.findViewById(R.id.bt_list_back).setOnClickListener(
                // 返回按钮
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // changeFragment(Frg__btn.index);
                        callBack.address(false, "", "");
                    }

                });
        view.findViewById(R.id.bt_list_search).setOnClickListener(new OnClickListener() {
            // 搜索按钮
            @Override
            public void onClick(View v) {
                startDiscovery();
            }
        });
    }


    /**
     * 开始扫描
     */
    private void startDiscovery() {
        view.findViewById(R.id.bt_list_search).setVisibility(View.GONE);
        bt_list_progress.setVisibility(View.VISIBLE);
        bt_list_search_view.setVisibility(View.VISIBLE);
        adapter_news.getData().clear();
        adapter_news.addData(new DevicesBean(search, "请稍候..."));
        mBtAdapter.startDiscovery();
    }

    /**
     * 完成扫描
     */
    private void finishDiscovery() {
        bt_list_progress.setVisibility(View.GONE);
    }

    /**
     * 适配器
     *
     * @author wp
     */
    private class DevicesAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public DevicesAdapter() {
            inflater = LayoutInflater.from(getActivity());
        }

        public List<DevicesBean> getData() {
            return items;
        }

        public void addData(DevicesBean item) {
            items.add(item);
            notifyDataSetChanged();
        }

        private List<DevicesBean> items = new ArrayList<DevicesBean>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public DevicesBean getItem(int arg0) {
            return items.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            arg1 = inflater.inflate(R.layout.reader_adapter_bluetooth_item, arg2, false);
            TextView tv1 = (TextView) arg1.findViewById(R.id.bt_list_item_tv1);
            TextView tv2 = (TextView) arg1.findViewById(R.id.bt_list_item_tv2);
            final DevicesBean item = getItem(arg0);
            tv1.setText(item.name);
            tv2.setText(item.address);
            arg1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!search.equals(item.name)) {
                        if ("点击按钮搜索附近设备".equals(item.address)) {
                            return;
                        }
                        callBack.address(true, item.name, item.address);
                        // changeFragment(Frg_one_btn.index);
                    }
                }
            });
            return arg1;
        }

    }

    /**
     * bean对象
     *
     * @author wp
     */
    private class DevicesBean {
        public DevicesBean(String name, String address) {
            this.name = name;
            this.address = address;
        }

        public String name;
        public String address;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) { // 未配对
                    if (adapter_news.getData().size() == 1) {
                        if (null != adapter_news.getData().get(0).name
                                && adapter_news.getData().get(0).name.equals(search)) {
                            adapter_news.getData().clear();
                            adapter_news.addData(new DevicesBean(device.getName(), device.getAddress()));
                        } else {
                            adapter_news.addData(new DevicesBean(device.getName(), device.getAddress()));
                        }

                    } else {
                        adapter_news.addData(new DevicesBean(device.getName(), device.getAddress()));
                    }

                } else { // 已配对
                    // findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
                    // mPairedDevicesArrayAdapter.add(device.getName() + "\n" +
                    // device.getAddress());
                    // mPairedDevicesArrayAdapter.notifyDataSetChanged();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                finishDiscovery();
                // setProgressBarIndeterminateVisibility(false);
                // setTitle("选择需要连接的设备");
                // if (mNewDevicesArrayAdapter.getCount() == 0) {
                // String noDevices = "没有找到设备";
                // mNewDevicesArrayAdapter.add(noDevices);
                // }
            }
        }
    };

    public interface BTAddressCallBack {
        void address(boolean success, String name, String address);
    }
}
