package com.techolon.robopost;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
    }

    public void signupbtn(View view) {

        Toast.makeText(this, "This Feature is under development\n Go to Login instead", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent delayIntent = new Intent(signup.this,login.class);
                startActivity(delayIntent);
                finish();
            }
        },2000);
    }
}
