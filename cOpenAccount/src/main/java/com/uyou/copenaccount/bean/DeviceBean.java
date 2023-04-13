package com.uyou.copenaccount.bean;


import com.uyou.copenaccount.model.OpenTypeModel;
import com.uyou.copenaccount.utils.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zdd on 2019/5/27.
 * <p>
 * Description:
 */
public class DeviceBean implements Serializable {

    private String device_name;
    private String device_code;
    private String URL;
    private String Action;
    private int version;
    private String URI;
    private List<OpenTypeModel> support_open_type;

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }


    public String getDevice_code() {
        return StringUtils.checkNull(device_code);
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public void setSupport_open_type(List<OpenTypeModel> support_open_type) {
        this.support_open_type = support_open_type;
    }

    public String getDevice_name() {
        return StringUtils.checkNull(device_name);
    }

    public String getURL() {
        return URL;
    }

    public String getAction() {
        return Action;
    }

    public int getVersion() {
        return version;
    }

    public String getURI() {
        return URI;
    }

    public List<OpenTypeModel> getSupport_open_type() {
        return support_open_type;
    }

    @Override
    public String toString() {
        return device_name;
    }
}
