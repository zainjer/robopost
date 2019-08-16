package com.techolon.robopost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText pass,email;
    TextView tvSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hidestatusbar();
        pass = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        tvSignup = findViewById(R.id.tvsignup);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, signup.class));
                finish();
            }
        });
    }

    private void hidestatusbar() {
        getSupportActionBar().hide();
    }

    public void loginbtn(View view) {

        if(email.getText().toString().equals("admin")&&pass.getText().toString().equals("admin")){
            startActivity(new Intent(login.this, HomeActivity.class));
        }
        else{
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }

    }
}
