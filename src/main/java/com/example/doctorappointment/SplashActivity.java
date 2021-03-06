package com.example.doctorappointment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseAppCompatActivity {
    Handler handler;
    Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        globals = (Globals) getApplicationContext();

    }

    @Override
    protected void onResume() {
        performOperation();
        super.onResume();
    }

    public void performOperation() {

        startHandler();

    }

    private void startHandler() {
        handler = new Handler();
        handler.postDelayed(runnable, Constant.AB_SPLASH_TIME);//call runnable
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            openNavigationActivity();
        }
    };


    public void openNavigationActivity() {

        if (globals.getUserDetails() == null) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, AddressBookListingActivity.class);
            startActivity(intent);
        }
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }


}
