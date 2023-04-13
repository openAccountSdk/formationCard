package com.uyou.copenaccount.bean;


import com.uyou.copenaccount.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by zdd on 2019/7/30.
 * <p>
 * Description:
 */
public class PackageBean implements Serializable {

    public String cid_package;
    public String product_id;
    public String product_name;
    public String show_laixian;

    public String getCid_package() {
        return cid_package;
    }

    public void setCid_package(String cid_package) {
        this.cid_package = cid_package;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return StringUtils.checkNull(product_name);
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getShow_laixian() {
        return show_laixian;
    }

    public void setShow_laixian(String show_laixian) {
        this.show_laixian = show_laixian;
    }

    @Override
    public String toString() {
        return "PhoneCardPackage [cid_package=" + cid_package + ", product_id=" + product_id + ", product_name="
                + product_name + ", show_laixian=" + show_laixian + "]";
    }
}
