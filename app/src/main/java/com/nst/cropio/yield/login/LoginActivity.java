package com.nst.cropio.yield.login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nst.cropio.yield.R;
import com.nst.cropio.yield.network.LoginResponse;
import com.nst.cropio.yield.util.SharedPrefManager;

import static com.nst.cropio.yield.login.WebActivity.REQUEST_CODE;
import static com.nst.cropio.yield.util.PermissionManager.PERMISSIONS_REQUEST_STORAGE;
import static com.nst.cropio.yield.util.PermissionManager.STORAGE_CODE;
import static com.nst.cropio.yield.util.PermissionManager.askPermission;
import static com.nst.cropio.yield.util.PermissionManager.checkOutPermission;

public class LoginActivity extends AppCompatActivity implements LoginFragment.ResponseHandler {

    private Button startBtn;
    private EditText loginEt;
    private EditText passwordEt;

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private String loginStr, passwordStr;
    private LoginResponse savedResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
//                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (SharedPrefManager.get().isLoggedIn()) {
            startTopLevelActivity();
            return;
        }

        LoginFragment loginFragmnent = LoginFragment.newInstance("zaglushka", "zaglushka");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, loginFragmnent).commit();


    }


    private void startTopLevelActivity() {
//        int flags = Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                Intent.FLAG_ACTIVITY_NEW_TASK;
//        Intent intent = new Intent(this, TopLevelActivity.class);
//        intent.setFlags(flags);
//        this.startActivity(intent);
//        this.finish();
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    private void onSignIn(LoginResponse response) {
        savedResponse = response;
        if (!checkOutPermission(LoginActivity.this, STORAGE_CODE)) {
            askPermission(LoginActivity.this, STORAGE_CODE);
        } else {
//            launchDownloadTask(response);
            Log.e("tag", " launchDownloadTask from onSignIn");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    launchDownloadTask(savedResponse); //// TODO: 9/26/17

                    Log.e("tag", " launchDownloadTask from onReqPerm");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //askPermission(this, STORAGE_CODE);

//                    startSettingScreen(this); // TODO: 9/26/17
                }
            }
        }
    }


    @Override
    public void handleResponse(String password, LoginResponse loginResponse) {
        loginResponse.getUser_api_token();
        Log.e("tag", loginResponse.getUser_api_token());
        Toast.makeText(this, loginResponse.getUser_api_token(), Toast.LENGTH_SHORT).show();
        onSignIn(loginResponse);
    }

    public void onLogginButton(View v) {
        Log.e("tag", "on loggin from activity");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                LoginResponse loginResponse = WebActivity.getAgroinvestDataAsResponse(data);
                Log.e("tag", "resp data= " + loginResponse.toString());
                onSignIn(loginResponse);
            }
        }
    }
}
