package com.nst.cropio.yield.login;

import com.nst.cropio.yield.network.LoginResponse;
import com.nst.cropio.yield.util.LocalizationManager;
import com.nst.cropio.yield.util.SharedPrefManager;



public class DownloadManager {

    public static void saveAccountData(LoginResponse loginResponse, String login) {
        SharedPrefManager handler = SharedPrefManager.get();
        handler.addUserEmail(login);

        String authToken = loginResponse.getUser_api_token();
        String locale = loginResponse.getLanguage();
        String user_id = loginResponse.getUser_id();
        String companyName = loginResponse.getCompany();

        handler.addLanguage(locale);
        LocalizationManager.setUserLanguage();
        handler.addUser_id(user_id);
        handler.addToken(authToken);
        handler.addCompany(companyName);
        handler.setLoggedIn(true);
        //todo Firebase set data
    }

}
