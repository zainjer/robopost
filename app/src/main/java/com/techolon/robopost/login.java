package com.techolon.robopost;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class login extends AppCompatActivity {

    EditText pass,email;
    TextView tvSignup;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    SignInButton googleSignInButton;
    CallbackManager callbackManager;
    private static final String FBEMAIL = "email";
    private int RC_SIGN_IN = 101;
    LoginButton fblogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hidestatusbar();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            updateUI(user);
            startActivity(new Intent(login.this, HomeActivity.class));
            finish();
        } else {


            pass = findViewById(R.id.etPassword);
            email = findViewById(R.id.etEmail);
            tvSignup = findViewById(R.id.tvsignup);
            googleSignInButton = findViewById(R.id.btnGoogleSignIn);
            fblogin = findViewById(R.id.btnFblogin);

            //Google Sign-in object
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            //Google signInClient object initialization
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mAuth = FirebaseAuth.getInstance();

            //Facebook callback
            callbackManager = CallbackManager.Factory.create();
            fblogin.setReadPermissions(Arrays.asList(FBEMAIL));


//


            tvSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login.this, signup.class));
                    finish();
                }
            });

            googleSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            });

        }
    }
    public void handleFacebookToken(AccessToken accessToken) {

        AuthCredential credential= FacebookAuthProvider.getCredential(accessToken.getToken());

        if(mAuth.getCurrentUser()!=null)
             mAuth.signOut();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    updateUI(currentUser);
                    //Toast.makeText(login.this, currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(login.this,HomeActivity.class));
                    finish();
                }else{
                    Toast.makeText(login.this, "Could not register to firebase", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void hidestatusbar() {
        getSupportActionBar().hide();
    }

    public void loginbtn(View view) {

        String p,e;
        e = email.getText().toString();
        p = pass.getText().toString();
        
        if(e.equals("")||p.equals("")){
            Toast.makeText(this, "Error!\nEmpty feilds", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(e,p)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(login.this, "Signed-in", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                startActivity(new Intent(login.this,HomeActivity.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google Sign-in failed\nPlease try another Sign-in method", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent i = new Intent(login.this,HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.


                            updateUI(null);
                        }

                        // ...
                    }
                });


    }


    private void updateUI(FirebaseUser currentUser) {
        ProfileData.setUser(currentUser);
        ProfileData.setDisplay_name(currentUser.getDisplayName());
        ProfileData.setAccount_email(currentUser.getEmail());
        ProfileData.setDisplay_picture_url(currentUser.getPhotoUrl());
    }

    public void loginFb(View view) {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        handleFacebookToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(login.this, "Facebook login cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(login.this, "Facebook Login Error\n"+exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}


