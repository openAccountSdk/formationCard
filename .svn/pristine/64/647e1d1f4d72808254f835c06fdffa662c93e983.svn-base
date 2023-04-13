package com.uyou.copenaccount.base;


import static com.uyou.copenaccount.base.UCConstants.ACTION_PATH;

import com.uyou.copenaccount.utils.net.HttpRequest;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description: UOpenBaseActivity
 */
public abstract class UOpenBaseActivity extends UBaseActivity implements HttpRequest {


    protected String mPath;


    @Override
    protected void initBeforeData() {
        if (getIntent() != null) {
            mPath = getIntent().getStringExtra(ACTION_PATH);
        }
    }
}
