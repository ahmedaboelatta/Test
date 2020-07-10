package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    //Firebase Instance Variable
    private FirebaseAuth mAuth;
    //UI Reference
    private AutoCompleteTextView enter_Email;
    private EditText enter_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enter_Email = findViewById(R.id.edt_emailLogin);
        enter_Password = findViewById(R.id.edt_passwordLogin);

        enter_Password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.integer.login || actionId == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        //Get an Instance of Firebase
        mAuth = FirebaseAuth.getInstance();
    }

    // Login Button Pressed
    public void signInUser(View view) {
        attemptLogin();
    }

    // Register Button Pressed
    public void registerNewUser(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        // finish();
    }

    //Complete the attemptLogin
    private void attemptLogin() {
        String email = enter_Email.getText().toString();
        String password = enter_Password.getText().toString();

        if (email.equals("") || password.equals("")) return;
        Toast.makeText(this, "Login in Progress ...", Toast.LENGTH_SHORT).show();
        //Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Test","signInWithEmail() onComplete: " + task.isSuccessful());
                if (!task.isSuccessful()){
                    Log.d("Test","Problem sign in: " + task.getException());
                    showErrorDialog("There was Problem Signing in");
                }else {
                    Intent intent = new Intent(LoginActivity.this, User_Login_Activity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    //Show error on screen with an alert dialog
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}