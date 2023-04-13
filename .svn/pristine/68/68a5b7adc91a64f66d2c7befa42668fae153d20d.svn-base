package com.uyou.copenaccount.model;


import com.uyou.copenaccount.dialog.SelectMethodDialog;
import com.uyou.copenaccount.utils.StringUtils;

/**
 * Created by zdd on 2019/4/1.
 * <p>
 * Description:
 */
public class SelectItem {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_CANCEL = 1;

    private int type;
    private String title;
    private SelectMethodDialog.OnClickListener listener;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return StringUtils.checkNull(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SelectMethodDialog.OnClickListener getListener() {
        return listener;
    }

    public void setListener(SelectMethodDialog.OnClickListener listener) {
        this.listener = listener;
    }
}
