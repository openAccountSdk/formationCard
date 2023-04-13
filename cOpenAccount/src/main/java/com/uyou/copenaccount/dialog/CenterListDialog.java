package com.uyou.copenaccount.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.decoration.CommonItemDecoration;
import com.uyou.copenaccount.model.OpenTypeModel;
import com.uyou.copenaccount.view.rv.BaseQuickAdapter;
import com.uyou.copenaccount.view.rv.BaseViewHolder;
import com.uyou.copenaccount.xpopup.core.CenterPopupView;

import java.util.List;

/**
 * Created by zdd on 2019/6/17.
 * <p>
 * Description:
 */
public class CenterListDialog extends CenterPopupView {

    private CenterListAdapter mAdapter;

    public CenterListDialog(@NonNull Context context, List<OpenTypeModel> datas, final OnItemClickListener listener) {
        super(context);

        RecyclerView recyclerView = centerPopupContainer.findViewById(R.id.common_recycler_view);
        mAdapter = new CenterListAdapter();
        mAdapter.setNewData(datas);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (listener != null) {
                    if (position >= 0 && position < mAdapter.getData().size()) {
                        listener.onItemClick(mAdapter.getData().get(position));
                    }
                }
                dismiss();
            }
        });

        recyclerView.addItemDecoration(new CommonItemDecoration(getContext(), 0, 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);

        centerPopupContainer.findViewById(R.id.common_txt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_dialog_center_list;
    }

    private class CenterListAdapter extends BaseQuickAdapter<OpenTypeModel, BaseViewHolder> {

        public CenterListAdapter() {
            super(R.layout.common_adapter_center_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, OpenTypeModel item) {
            helper.setText(R.id.common_txt_item, item.getName());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(OpenTypeModel model);
    }
}
