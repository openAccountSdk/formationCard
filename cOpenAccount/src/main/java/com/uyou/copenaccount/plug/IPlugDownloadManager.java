package com.uyou.copenaccount.plug;

import android.content.Intent;

/**
 * Created by zdd on 2019/6/13.
 * <p>
 * Description:
 */
public interface IPlugDownloadManager {

    void clear();
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
