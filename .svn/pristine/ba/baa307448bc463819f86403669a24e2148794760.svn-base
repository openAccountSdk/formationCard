package com.uyou.copenaccount.ui;


import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.ACTION_SIGNATURE_UPLOAD_CODE;
import static com.uyou.copenaccount.base.UCConstants.APP_DOMAIN;
import static com.uyou.copenaccount.base.UCConstants.GET_AGREEMENT_LIST;
import static com.uyou.copenaccount.base.UCConstants.NO_FINISHED_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.NO_INPUT_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.PRE_INPUT_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.SDK_VERSION;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_013;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_014;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.adapter.ProtocolListAdapter;
import com.uyou.copenaccount.base.UOpenBaseActivity;
import com.uyou.copenaccount.model.OpenAccountAction;
import com.uyou.copenaccount.model.UploadImageModel;
import com.uyou.copenaccount.progress.ProgressInfo;
import com.uyou.copenaccount.progress.ProgressListener;
import com.uyou.copenaccount.progress.ProgressManager;
import com.uyou.copenaccount.response.GetAgreementResponse;
import com.uyou.copenaccount.response.UploadImageServiceResponse;
import com.uyou.copenaccount.utils.AccountUtils;
import com.uyou.copenaccount.utils.AppUtils;
import com.uyou.copenaccount.utils.StatusBarUtil;
import com.uyou.copenaccount.utils.net.Api;
import com.uyou.copenaccount.utils.net.UYouHttpClient;
import com.uyou.copenaccount.view.rv.BaseQuickAdapter;
import com.uyou.copenaccount.view.sign.SignView;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by zdd on 2020/08/18
 * <p>
 * Description: 签名页面
 */
public class OpenAccountSignatureActivity extends UOpenBaseActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R2.id.bus_sign_view)
    SignView signView;
    @BindView(R2.id.bus_btn_next)
    TextView btnNext;
    @BindView(R2.id.rv_agreement)
    RecyclerView rv_agreement_list;
    private OpenAccountAction mAction;

    // 提交成功标识, 通过此值判断, 在提交 成功后到打开支付页面间隔防止再次提交
    private boolean commitSuccess = false;

    private ProtocolListAdapter protocolListAdapter;


    @Override
    public String getPageTitle() {
        return getStringRes(R.string.bus_title_open_account_sign);
    }

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.bus_activity_open_account_signature;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setStatusColor(this, true);
        Object object = getIntent().getSerializableExtra(ACTION_DATA);

        if (object != null) {
            if (object instanceof OpenAccountAction) {
                mAction = (OpenAccountAction) object;
            }
        }
        initRecyclerView();

        try {
            JSONObject params = new JSONObject();
            params.put("phoneNum", mAction.openAccountModel.getPhone_num());
            params.put("reqType", mAction.isOnline ? NO_FINISHED_CARD_OPEN : mAction.isNotPreRecordCard ? NO_INPUT_CARD_OPEN : PRE_INPUT_CARD_OPEN);
            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_013, GET_AGREEMENT_LIST, params, SDK_VERSION, OpenAccountSignatureActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        rv_agreement_list.setLayoutManager(new LinearLayoutManager(OpenAccountSignatureActivity.this));
        protocolListAdapter = new ProtocolListAdapter();
        rv_agreement_list.setAdapter(protocolListAdapter);
        protocolListAdapter.setOnItemClickListener(this);
        protocolListAdapter.setOnItemChildClickListener(this);
    }

    /**
     * 提交
     */
    @OnClick(R2.id.bus_btn_next)
    void toCommit() {
        for (int i = 0; i < protocolListAdapter.getData().size(); i++) {
            if (!protocolListAdapter.getData().get(i).isSelect()) {
                showToast(String.format(getResources().getString(R.string.bus_toast_agree_protocol), protocolListAdapter.getData().get(i).getName()));
                return;
            }
        }
        if (!signView.isPainted()) {
            showToast("请签名后提交");
            return;
        }
        btnNext.setEnabled(false);
        String path = signView.save();
        if (TextUtils.isEmpty(path)) {
            showToast(getStringRes(R.string.common_err_file_not_exists));
            return;
        }
        btnNext.setEnabled(false);
        try {
            File file = new File(path);
            UploadImageModel uploadImageModel = new UploadImageModel();
            uploadImageModel.setUrl(rebuildUploadUrl());
            uploadImageModel.setFile(file);
            uploadImageModel.setCode("0012");
            uploadImageModel.setPhone_num(mAction.mobileNum);
            uploadImageModel.setLoginNum(AccountUtils.getUserName(getContext()));
            uploadImageModel.setChannel("6");
            UYouHttpClient.getInstance().uploadImage(
                    uploadImageModel,
                    THREAD_ID_014,
                    OpenAccountSignatureActivity.this
            );

        } catch (Exception e) {
            showToast("图片转换错误, 请重试");
        }
    }




    @OnClick(R2.id.bus_btn_resign)
    void toResign() {
        signView.clear();
    }

    private String rebuildUploadUrl() {
        return ProgressManager.getInstance().addDiffRequestListenerOnSameUrl(APP_DOMAIN + Api.URL_UPLOAD_IMAGE, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
            }

            @Override
            public void onError(long id, Exception e) {
            }
        });
    }


    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response, String path) {
        Gson gson = new Gson();
        UploadImageServiceResponse uploadImageServiceResponse = gson.fromJson(response, UploadImageServiceResponse.class);
        String uploadCode = uploadImageServiceResponse.getData().getUpload_code();
        if (threadId == THREAD_ID_014) {
            btnNext.setEnabled(true);
            Intent intent = new Intent(this, ConfirmAccountOpeningActivity.class);
            intent.putExtra(ACTION_DATA, mAction);
            intent.putExtra(ACTION_SIGNATURE_UPLOAD_CODE,uploadCode);
            startActivity(intent);
        }
    }

    @Override
    protected void onHttpCodeError(int threadId, int code, String message) {
        super.onHttpCodeError(threadId, code, message);
        if (threadId == THREAD_ID_014) {
            btnNext.setEnabled(true);
        }
    }
    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response) {
        Gson gson = new Gson();
        if (threadId == THREAD_ID_013) {
            GetAgreementResponse getAgreementResponse = gson.fromJson(response, GetAgreementResponse.class);
            GetAgreementResponse.Data data = getAgreementResponse.getData();
            if (data != null) {
                protocolListAdapter.setNewData(data.agreementList);
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GetAgreementResponse.ProtocolBean protocolBean = protocolListAdapter.getData().get(position);
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
                if (!AppUtils.isFastClick()) {
                    protocolBean.isSelect = true;
                    protocolListAdapter.notifyItemChanged(position);
                    Intent intent;
                    if ("".equals(protocolBean.getUrl())) {
                        intent = new Intent(OpenAccountSignatureActivity.this, ReceiptActivity.class);
                        intent.putExtra(ACTION_DATA, mAction);
                    } else {
                        intent = new Intent(this, NetProtocolActivity.class);
                        intent.putExtra("title", protocolBean.getName());
                        intent.putExtra(ACTION_DATA, protocolBean.getUrl());
                    }
                    startActivity(intent);
                }

                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.item_box) {
            GetAgreementResponse.ProtocolBean protocolBean = protocolListAdapter.getData().get(position);
            protocolBean.isSelect = !protocolBean.isSelect;
            protocolListAdapter.notifyItemChanged(position);
        }
    }
}
