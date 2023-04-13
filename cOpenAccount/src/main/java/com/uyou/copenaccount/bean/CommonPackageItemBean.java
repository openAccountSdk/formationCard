package com.uyou.copenaccount.bean;


import com.uyou.copenaccount.utils.StringUtils;

/**
 * Created by zdd on 2020/8/17.
 * <p>
 * Description:
 */
public class CommonPackageItemBean {

    private String id;//offset_id
    private String name;
    private String desc;
    private String type; // 0=非必选，默认不选  1=默认选择,不包含  2=必选
    private boolean isSelect =false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return StringUtils.checkNull(name);
    }

    public String getDesc() {
        return StringUtils.checkNull(desc);
    }

    @Override
    public String toString() {
        return getName();
    }
}
