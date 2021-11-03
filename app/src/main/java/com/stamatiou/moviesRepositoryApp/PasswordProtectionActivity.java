// -------------------------------------------------------------
//
// This is the main Activity, used to password protect the Repository.
// On correct password submission, User is redirected to RepositoryActivity.
//
// Author: Aggelos Stamatiou, May 2020
//
// --------------------------------------------------------------

package com.stamatiou.moviesRepositoryApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordProtectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_protection_activity);
        passwordProtectionInit();
    }

    // Activity initialization method.
    // If no cached password exists, application sets the default value.
    private void passwordProtectionInit() {
        Log.i("message","PasswordProtectionInit method started.");
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("repository_password", Context.MODE_PRIVATE);;
            if (!sharedPreferences.contains("repository_password")) {
                Log.i("message","Repository password doesn't exists. Setting default Password value.");
                sharedPreferences.edit().putString("repository_password", "18f3c96386407ba486f6f6178a14639194e498c4f8338fc61bf2945653fe58a").commit();
            }
            findViewById(R.id.passwordSubmitButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passwordSubmitAction();
                }
            });
            Log.i("message","PasswordProtectionInit method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during PasswordProtectionInit method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Application checks the submitted password, against the cached one.
    // On correct password submission, User is redirected to RepositoryActivity.
    private void passwordSubmitAction() {
        Log.i("message","PasswordSubmitAction method started.");
        try {
            Boolean valid = true;
            SharedPreferences sharedPreferences = getSharedPreferences("repository_password", Context.MODE_PRIVATE);;
            String password = sharedPreferences.getString("repository_password", null);
            EditText passwordEditText = findViewById(R.id.passwordEditText);
            String inputPassword = Utils.hashPassword(this, passwordEditText.getText().toString());
            if (!password.equals(inputPassword)) {
                passwordEditText.setError("Password is incorrect! Hint: Default password is 'p@$$w0rd'");
                valid = false;
            }
            Log.i("message","PasswordSubmitAction method completed successfully.");
            if (valid) {
                Intent intent = new Intent(this, RepositoryActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during PasswordSubmitAction method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ((EditText) findViewById(R.id.passwordEditText)).getText().clear();
    }
}
