package com.nst.cropio.yield.top;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.nst.cropio.yield.R;

import static java.lang.Thread.sleep;


public class TopLevelActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_estimation);



                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_harvest);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_market);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.top_level_activity_menu, menu);

//        loadAvatarImage(menu);

        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.menu_search:
//                SearchActivity.start(this);
//                break;
//            case R.id.menu_season:
//                showSeasonsPicker();
//                break;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }

//        return true;
//    }

}
