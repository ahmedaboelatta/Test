package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class DiceActivity extends AppCompatActivity {
    Button btn_Roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        final ImageView img_LeftDice = findViewById(R.id.img_LeftDice);
        final ImageView img_RightDice = findViewById(R.id.img_RightDice);
        btn_Roll = findViewById(R.id.btn_Roll);
        final int[] diceArray = {
                R.drawable.dice1,
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice5,
                R.drawable.dice6};
        btn_Roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Dicee", "Ze Button has been Pressed!");
                Random randomNumberGenerator = new Random();
                int number = randomNumberGenerator.nextInt(6);
                Log.d("Dicee", "The Random Number is: " + number);
                img_LeftDice.setImageResource(diceArray[number]);
                number = randomNumberGenerator.nextInt(6);
                img_RightDice.setImageResource(diceArray[number]);

            }
        });
    }
}