package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User_Login_Activity extends AppCompatActivity {

    TextView myEmail;
    Button sign_out_Button, play_Dice;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        myEmail = findViewById(R.id.myEmail);
        sign_out_Button = findViewById(R.id.sign_out_Button);
        sign_out_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(User_Login_Activity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        if (mUser != null){
            String email = mUser.getEmail();

            myEmail.setText(email);
        }
    }
}