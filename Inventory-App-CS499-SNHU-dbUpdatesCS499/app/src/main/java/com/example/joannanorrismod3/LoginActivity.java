package com.example.joannanorrismod3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity{
    private EditText usernameInput;
    private EditText passwordInput;
    private UserDatabaseHelper db;
    private static final int SMS_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.editTextTextEmailAddress);
        passwordInput = findViewById(R.id.editTextTextPassword);
        db = new UserDatabaseHelper(this);
    }

    //when login button is pressed
    public void onLoginClicked(View view) {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        //if username or password is left blank message
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.validateUser(username, password)) {
            checkSmsPermission();
        } else {
            Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
        }
    }

    //called when create account is pressed
    public void onCreateAccountClicked(View view) {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        //if username or password is left blank message
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        //if username already exists message
        if (db.userExists(username)) {
            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        //confirmation message
        if (db.addUser(username, password)) {
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            checkSmsPermission();
        }
    }


    //check SMS permissions
    private void checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
            goToInventory();
        }
        else {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }
    }

    //go to inventory page
    private void goToInventory() {
        Intent intent = new Intent(LoginActivity.this, InventoryActivity.class);
        startActivity(intent);
        finish(); // close LoginActivity
    }

    //handle SMS permissions results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //if yes
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            }
            else {
                //if no
                Toast.makeText(this, "SMS permission denied. Some features may not work.", Toast.LENGTH_LONG).show();
            }
            //go to InventoryActivity regardless of answer
            goToInventory();
        }
    }
}
