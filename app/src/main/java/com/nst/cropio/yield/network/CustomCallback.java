package com.nst.cropio.yield.network;

import android.util.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;




public class CustomCallback<T> implements Callback<T> {

    private T result;
    private boolean success = true;
    private RetrofitError error;
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();

    @Override
    public void success(T result, Response response) {
        lock.lock();
        this.result = result;
        done.signalAll();
        lock.unlock();
    }

    @Override
    public void failure(RetrofitError error) {
        error.getKind();
        lock.lock();
        success = false;
        this.error = error;
        done.signalAll();
        lock.unlock();

    }

    public T handleCallBack() throws RetrofitException {
        lock.lock();
        try {
            done.await();
        } catch (InterruptedException e) {
            Log.e("tag" ,CustomCallback.class+ " handleCallBack", e);
        }finally {
            lock.unlock();
        }
        if (success) {
            return result;
        } else {
            throw new RetrofitException(error);
        }
    }

}
