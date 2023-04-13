package com.uyou.copenaccount.adapter;


import com.uyou.copenaccount.R;
import com.uyou.copenaccount.response.GetAgreementResponse;
import com.uyou.copenaccount.view.rv.BaseQuickAdapter;
import com.uyou.copenaccount.view.rv.BaseViewHolder;

/**
 * Description: 协议列表 adapter
 */
public class ProtocolListAdapter extends BaseQuickAdapter<GetAgreementResponse.ProtocolBean, BaseViewHolder> {

    public ProtocolListAdapter() {
        super(R.layout.bus_adapter_agreement_list);
    }


    @Override
    protected void convert(BaseViewHolder helper, GetAgreementResponse.ProtocolBean item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setChecked(R.id.item_box, item.isSelect);
        helper.addOnClickListener(R.id.item_box);
    }
}
