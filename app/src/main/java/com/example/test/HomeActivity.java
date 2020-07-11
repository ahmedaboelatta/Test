package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void PlayDice(View view) {
        Intent intent = new Intent(HomeActivity.this, DiceActivity.class);
        startActivity(intent);
    }
    public void PlayPiano(View view) {
        Intent intent = new Intent(HomeActivity.this, PianoActivity.class);
        startActivity(intent);
    }
    public void btn_Settings(View view) {
        Intent intent = new Intent(HomeActivity.this, User_Login_Activity.class);
        startActivity(intent);
    }
}