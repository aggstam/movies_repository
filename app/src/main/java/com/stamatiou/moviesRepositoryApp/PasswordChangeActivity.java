// -------------------------------------------------------------
//
// This Activity is used to change Repository cached password.
//
// Author: Aggelos Stamatiou, May 2020
//
// --------------------------------------------------------------

package com.stamatiou.moviesRepositoryApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change_activity);
        findViewById(R.id.newPasswordSubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPasswordSubmitAction();
            }
        });
    }

    // Application validates both submitted passwords.
    // On successful password validation, Repository cached password is updated.
    private void newPasswordSubmitAction() {
        Log.i("message","NewPasswordSubmitAction method started.");
        try {
            Boolean passwordFieldsValidation = validatePasswordFields();
            if (passwordFieldsValidation) {
                String hashedPassword = Utils.hashPassword(this, ((EditText) findViewById(R.id.newPasswordEditText)).getText().toString());
                if (!hashedPassword.equals("")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("repository_password", Context.MODE_PRIVATE);;
                    sharedPreferences.edit().putString("repository_password", hashedPassword).commit();
                }
            }
            Log.i("message","NewPasswordSubmitAction method completed successfully.");
            if (passwordFieldsValidation) finish();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during NewPasswordSubmitAction method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Validates User submitted passwords.
    // Passwords cannot be empty and must match.
    private Boolean validatePasswordFields() {
        Boolean valid = true;
        EditText newPasswordEditText = findViewById(R.id.newPasswordEditText);
        if (newPasswordEditText.getText().toString().trim().length() == 0) {
            newPasswordEditText.setError("Password cannot be empty!");
            valid = false;
        }
        EditText repeatNewPasswordEditText = findViewById(R.id.repeatNewPasswordEditText);
        if (repeatNewPasswordEditText.getText().toString().trim().length() == 0) {
            repeatNewPasswordEditText.setError("Password cannot be empty");
            valid = false;
        } else if (!repeatNewPasswordEditText.getText().toString().equals(newPasswordEditText.getText().toString())) {
            repeatNewPasswordEditText.setError("Passwords must match!");
            valid = false;
        }
        return valid;
    }

}
