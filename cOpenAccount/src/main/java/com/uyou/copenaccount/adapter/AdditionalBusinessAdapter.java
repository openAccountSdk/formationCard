package com.uyou.copenaccount.adapter;


import android.widget.CheckBox;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.bean.CommonPackageItemBean;
import com.uyou.copenaccount.view.rv.BaseQuickAdapter;
import com.uyou.copenaccount.view.rv.BaseViewHolder;

public class AdditionalBusinessAdapter extends BaseQuickAdapter<CommonPackageItemBean, BaseViewHolder> {

    public AdditionalBusinessAdapter() {
        super(R.layout.item_additional_business);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonPackageItemBean item) {
        helper.addOnClickListener(R.id.bus_check_package);

        helper.setText(R.id.bus_check_package, item.getName());

        CheckBox cb = helper.getView(R.id.bus_check_package);
        item.setSelect(cb.isChecked());
        if (item.getType().equals("1")) {// 0=非必选，默认不选  1=默认选择  2=必选
            cb.setChecked(true);
        } else if (item.getType().equals("2")) {
            cb.setChecked(true);
            cb.setClickable(false);
        } else {
            cb.setChecked(false);
        }
    }
}
