package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    // Constants
    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";
    //UI Reference
    private AutoCompleteTextView edt_UserName;
    private AutoCompleteTextView edt_Email;
    private EditText edt_Password;
    private EditText edt_ConfirmPassword;
    //Firebase Instance Variable
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_UserName = findViewById(R.id.edt_UserNameRegister);
        edt_Email = findViewById(R.id.edt_EmailRegister);
        edt_Password = findViewById(R.id.edt_PasswordRegister);
        edt_ConfirmPassword = findViewById(R.id.edt_ConfirmPasswordRegister);

        edt_ConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.edt_ConfirmPasswordRegister || actionId == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });
        //Get hold of an Instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
    }


    // Register Button Pressed
    public void signUpRegister(View view) {
        attemptRegistration();
    }

    private void attemptRegistration() {
        // Reset error displayed in the form
        edt_Email.setError(null);
        edt_Password.setError(null);

        //Store value at the time of the login attempt
        String email = edt_Email.getText().toString();
        String password = edt_Password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            edt_Password.setError(getString(R.string.error_invalid_password));
            focusView = edt_Password;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            edt_Email.setError(getString(R.string.error_field_required));
            focusView = edt_Email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            edt_Email.setError(getString(R.string.error_invalid_email));
            focusView = edt_Email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //Call create FirebaseUser
            createFirebaseUser();
        }

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //Check Valid Password
        String confirmPassword = edt_Password.getText().toString();
        return confirmPassword.equals(password) && password.length() > 4;
    }

    //Create a Firebase User
    private void createFirebaseUser() {
        String email = edt_Email.getText().toString();
        String password = edt_Password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Test", "createUser onComplete: " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.d("Test", "user creation failed");
                    showErrorDialog("Registration attempt failed");
                } else {
                    saveDisplayName();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void saveDisplayName() {
        String displayName = edt_UserName.getText().toString();
        SharedPreferences pref = getSharedPreferences(CHAT_PREFS, 0);
        pref.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }

    // Create an alert dialog to show in case registration failed
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}