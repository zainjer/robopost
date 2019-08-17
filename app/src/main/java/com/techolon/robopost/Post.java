package com.techolon.robopost;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Post extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

    }

    public void saveBtn(View view) {
        Toast.makeText(this, "Feature Under Development\nReturning to Home Screen", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent delayIntent = new Intent(Post.this,HomeActivity.class);
                startActivity(delayIntent);
                finish();
            }
        },2000);
    }
}
