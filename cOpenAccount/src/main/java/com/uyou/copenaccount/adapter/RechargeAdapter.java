package com.uyou.copenaccount.adapter;


import com.uyou.copenaccount.R;
import com.uyou.copenaccount.bean.RechargeBean;
import com.uyou.copenaccount.view.rv.BaseQuickAdapter;
import com.uyou.copenaccount.view.rv.BaseViewHolder;

/**
 * Created by zdd on 2019/4/22.
 * <p>
 * Description:
 */
public class RechargeAdapter extends BaseQuickAdapter<RechargeBean, BaseViewHolder> {

    public RechargeAdapter() {
        super(R.layout.pay_adapter_recharge);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeBean item) {
        String name = item.getName();
        if (!name.contains("元")) {
            name += "元";
        }
        String price = item.getDiscount_price();
        if (price.length() > 0 && !price.contains("元")) {
            price += "元";
        }
        helper.setText(R.id.bus_txt_recharge_origin_price, name);
        helper.setText(R.id.bus_txt_recharge_price, price);
        if (item.isChecked()) {
            helper.itemView.setBackgroundResource(R.drawable.pay_bg_recharge_selected);
        } else {
            helper.itemView.setBackgroundResource(R.drawable.pay_drawable_bg_recharge_item);
        }
    }
}
