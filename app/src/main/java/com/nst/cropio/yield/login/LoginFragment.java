package com.nst.cropio.yield.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nst.cropio.yield.R;
import com.nst.cropio.yield.YieldApplication;
import com.nst.cropio.yield.network.AdfsResponse;
import com.nst.cropio.yield.network.ApiClient;
import com.nst.cropio.yield.network.LoginRequest;
import com.nst.cropio.yield.network.LoginResponse;
import com.nst.cropio.yield.rx.SimpleSubscriber;

import java.util.concurrent.Callable;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.nst.cropio.yield.network.ApiClient.DOMAIN;

public class LoginFragment extends Fragment {

    private EditText emailEt;
    private EditText passwordEt;
    private Button loginBtn;
    private static final String EXTRA_EMAIL = "email";
    private static final String EXTRA_PASSWORD = "password";


    private Subscription emailCheckSubscription;
    private Subscription signInSubscription;
    private ResponseHandler responseHandler;

    interface ResponseHandler {
        void handleResponse(String password, LoginResponse loginResponse);
    }

    public static LoginFragment newInstance(String email, String password) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_EMAIL, email);
        bundle.putString(EXTRA_PASSWORD, password);
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        emailEt = (EditText) fragmentView.findViewById(R.id.etEmail);
        emailEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_PREVIOUS) {
                    Log.e("asd", "dodod");
                    checkEmailForAdfs();
                    return true;
                }
                Log.e("asd", "action Id = " + actionId);

                return false;
            }
        });
        emailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.e("asd", "lostFocus");
                    checkEmailForAdfs();
                }
            }
        });
        passwordEt = (EditText) fragmentView.findViewById(R.id.etPassword);

        passwordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.e("asd", "password clicked enter --------------- get response and doSign in");
                    onEnterOrStartClicked();
                    return true;
                }
                Log.e("asd", "action Id = " + actionId);

                return false;
            }
        });

        loginBtn = (Button) fragmentView.findViewById(R.id.btnLoggin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnterOrStartClicked();
            }
        });
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        responseHandler = (ResponseHandler) getActivity();
    }


    //-------------
    private String getLogin() {
        return emailEt.getText().toString();
    }

    private String getPassword() {
        return passwordEt.getText().toString();
    }

    private void checkEmailForAdfs() {

        emailCheckSubscription = checkEmail(getLogin()).subscribe(new SimpleSubscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("tag", "onStart " + getLogin());
            }

            @Override
            public void onNext(String needAuth) {
                Log.e("tag", "onNext input param =: " + needAuth);
                super.onNext(needAuth);
                if (needAuth != null) {
                    Log.e("tag", "need oauth: " + needAuth);
                    showAdfsDialog(needAuth);
                } else {
                    // its not adfs account! // TODO: 9/26/17
                    //dont do nothing
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                Log.e("tag", "onError");
                Log.e("tag", e.getMessage());
            }

            private void showAdfsDialog(String tenant) {
                String baseUrl = "https://cropio.com/d/users/auth/adfs?mobile=true";
                baseUrl = baseUrl
                        + "&email=" + getLogin()
                        + "&tenant=" + tenant;


//                WebActivity.startWebActivity(baseUrl, getActivity());
                WebActivity.startWebActivityForResult(baseUrl, getActivity());
            }
        });


    }


    //-----------

    private Observable<String> checkEmail(String email) {

        return Observable.fromCallable(new CheckEmailTask(email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class CheckEmailTask implements Callable<String> {

        private String email;

        CheckEmailTask(String email) {
            this.email = email;
        }

        @Override
        public String call() throws Exception {
            AdfsResponse response = new ApiClient(DOMAIN, null).getAdfsStatus(email);
            if (response.isSupportAdfs() && response.isRedirect()) {
                return response.getTenant();
            } else {
                return null;
            }
        }
    }

    private static class LoginTask implements Callable<LoginResponse> {

        private String login;
        private String password;

        LoginTask(String login, String password) {
            this.login = login;
            this.password = password;
        }

        @Override
        public LoginResponse call() throws Exception {
            Log.e("tag", "call method started");
            LoginResponse loginResponse;
            LoginRequest loginRequest = new LoginRequest(login, password);
            try {
                loginResponse = new ApiClient(ApiClient.API_V3, null).login(loginRequest);
                Log.e("tag", "Wrong loggin or password in try");
            } catch (Exception e) {
                RetrofitError error = (RetrofitError) e.getCause();
                //THL
                if (error != null && error.getKind() == RetrofitError.Kind.NETWORK &&
                        error.getMessage().equals("No authentication challenges found")) {
                    return new LoginResponse();
                } else {
                    //Nexus
                    if (error != null && error.getKind() == RetrofitError.Kind.HTTP
                            && error.getResponse().getStatus()==401) {
                        return new LoginResponse();
                    } else {
                        return null;
                    }
                }
            }
            return loginResponse;
        }
    }


    public void onEnterOrStartClicked() {
        cancelSignIn();
        cancelEmailCheck(); // i think that the place for it

        signInSubscription = Observable.fromCallable(new LoginTask(getLogin(), getPassword()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<LoginResponse>() {

                    @Override
                    public void onStart() {
                        super.onStart();

                        //may be show progress bar
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e("tag","asd error");
                        if (e instanceof RetrofitError){

                            Log.e("tag","retrofit error");

                        }

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        super.onNext(loginResponse);

                        // stod and dismiss progress bar


                        if (loginResponse != null) {
                            if (loginResponse.isSuccess()) {
                                loginIsSuccess(loginResponse);
                            } else {
                                Toast.makeText(YieldApplication.get(), "Wrong loggin or password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(YieldApplication.get(), "Check internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                    void loginIsSuccess(LoginResponse loginResponse) {
                        responseHandler.handleResponse(getPassword(), loginResponse);
                    }
                });
    }

    void cancelEmailCheck() {
        if (emailCheckSubscription != null) {
            emailCheckSubscription.unsubscribe();
        }
    }

    void cancelSignIn() {
        if (signInSubscription != null) {
            signInSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelEmailCheck();
        cancelSignIn();
    }




}
