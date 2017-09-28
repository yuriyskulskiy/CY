package com.nst.cropio.yield.network;

import retrofit.RetrofitError;

/**
 * Created by yuriy on 9/28/17.
 */

public class RetrofitException extends Exception {

    public final int status;

    public RetrofitException(RetrofitError error){
        super(error);
        if(error != null && error.getResponse() != null) {
            status = error.getResponse().getStatus();
        }else{
            status = 0;
        }
    }

    public int getStatus(){
        return status;
    }
}
