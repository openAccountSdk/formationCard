package com.uyou.copenaccount.adapter;

import android.widget.RadioButton;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.bean.ProductBean;
import com.uyou.copenaccount.view.rv.BaseQuickAdapter;
import com.uyou.copenaccount.view.rv.BaseViewHolder;


/**
 * Created by zdd on 2019/8/21.
 * <p>
 * Description:
 */
public class MainPackageAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {

    //选择的位置
    private int mSelectedPos = -1;//变量保存当前选中的position

    public MainPackageAdapter() {
        super(R.layout.item_main_package);
    }

    private String cardType;

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getSelectedPos() {
        return mSelectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        mSelectedPos = selectedPos;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {
        helper.setText(R.id.item, item.getProduct_name());
        helper.addOnClickListener(R.id.item);
        if (cardType != null && cardType.equals("1")) {
            RadioButton view = helper.getView(R.id.item);
            view.setChecked(true);
            view.setClickable(false);
        }
        if (mSelectedPos == helper.getLayoutPosition()) {
            helper.setChecked(R.id.item, true);
        } else {
            helper.setChecked(R.id.item, false);
        }
    }
}
