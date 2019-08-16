package com.techolon.robopost;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FOR_DEVELOPMENT();

        hidestatusbar();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent delayIntent = new Intent(MainActivity.this,signup.class);
//                startActivity(delayIntent);
//                finish();
//            }
//        },SPLASH_TIME_OUT);

    }

    private void FOR_DEVELOPMENT() {

        Intent delayIntent = new Intent(MainActivity.this,Post.class);
        startActivity(delayIntent);
        finish();
    }

    private void hidestatusbar() {
        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


    }
}
