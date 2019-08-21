package com.techolon.robopost;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPassword;
    EditText etfullname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etfullname = findViewById(R.id.etName);
        mAuth = FirebaseAuth.getInstance();

    }

    public void signupbtn(View view) {

        String email,password,name;


        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        name = etfullname.getText().toString();

        if(email.equals("")||password.equals("")||name.equals("")){
            Toast.makeText(this, "Error!\nEmpty Fields", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                ProfileData.setUser(user);

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(etfullname.getText().toString())
                                        .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(signup.this, "Submitted", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                Toast.makeText(signup.this, "Sign-Up Successful\nPlease login to continue", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signup.this,login.class));
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(signup.this, "Signing up Failed\n Please Try again", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                });

        }

    }

    private void updateUI(FirebaseUser user) {
    }
}
