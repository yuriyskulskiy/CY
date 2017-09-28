package com.nst.cropio.yield.network;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdfsResponse {
    private boolean supportAdfs;
    private String tenant;
    private String redirect;

    public boolean isSupportAdfs() {
        return supportAdfs;
    }

    public void setSupportAdfs(boolean supportAdfs) {
        this.supportAdfs = supportAdfs;
    }

    public String getTenant() {
        return tenant;
    }


    public boolean isRedirect() {
        return !TextUtils.isEmpty(redirect);
    }
}
