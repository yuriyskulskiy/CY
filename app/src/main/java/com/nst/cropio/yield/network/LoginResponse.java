package com.nst.cropio.yield.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by yuriy on 9/28/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse implements Serializable {

    private boolean success;
    private String language;

    private String user_api_token;

    private String company;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String user_id;

    private boolean crop_seasons;

    public boolean isSuccess() {
        return success;
    }

    public String getLanguage() {
        return language;
    }

    public String getUser_api_token() {
        return user_api_token;
    }

    public String getCompany() {
        return company;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setUser_api_token(String user_api_token) {
        this.user_api_token = user_api_token;
    }

    public void setCompany_name(String company_name) {
        this.company = company_name;
    }

    public boolean isCrop_seasons() {
        return crop_seasons;
    }

    public void setCrop_seasons(boolean crop_seasons) {
        this.crop_seasons = crop_seasons;
    }


    @Override
    public String toString() {
        return "isSuccess = " + success + "; company " + company + " ;language " + language + "; token " + user_api_token;
    }
}
