package com.uyou.copenaccount.bean;


import com.uyou.copenaccount.utils.StringUtils;

import java.util.List;

/**
 * Created by zdd on 2020/8/17.
 * <p>
 * Description:
 */
public class CommonPackageBean {

    // 前端展示的基础套餐包名
    private String package_name;
    // 用户可选的基础套餐列表
    private List<CommonPackageItemBean> package_list;
    // 用户选择之后,返给后台id的key
    private String back_id_key;
    // 用户选择之后,返给后台名称的key
    private String back_name_key;
    // 前端展示的类型 1下拉  2单选
    private String rule_type;
    // 单选框的规则  当type=1时,该值为1,当type=2是,该值规则如下: 0=非必选，默认不选  1=默认选择,不包含  2=必选
    private String rule_value;
    private String package_desc;

    public String getPackage_name() {
        return StringUtils.checkNull(package_name);
    }

    public List<CommonPackageItemBean> getPackage_list() {
        return package_list;
    }

    public String getBack_id_key() {
        return StringUtils.checkNull(back_id_key);
    }

    public String getBack_name_key() {
        return StringUtils.checkNull(back_name_key);
    }

    public String getRule_type() {
        return StringUtils.checkNull(rule_type);
    }

    public String getRule_value() {
        return StringUtils.checkNull(rule_value);
    }

    public String getPackage_desc() {
        return package_desc;
    }
}
