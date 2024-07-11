package com.example.bansosapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bansosapp.customer.CustomerDashboardActivity;
import com.example.bansosapp.database.DatabaseContract;
import com.example.bansosapp.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private int customerId;
    private DatabaseHelper databaseHelper;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        databaseHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (validateInputs(username, password)) {

                    if (authenticateCustomer(username, password)) {
                        customerId = getCustomerId(username);
                        openCustomerDashboard();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    private boolean validateInputs(String username, String password) {
        if (username.isEmpty()) {
            editTextUsername.setError("Username is required");
            return false;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            return false;
        }

        return true;
    }

    private boolean authenticateCustomer(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.CustomerEntry.COLUMN_USERNAME,
                DatabaseContract.CustomerEntry.COLUMN_PASSWORD
        };

        String selection = DatabaseContract.CustomerEntry.COLUMN_USERNAME + " = ? AND " +
                DatabaseContract.CustomerEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                DatabaseContract.CustomerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isAuthenticated = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isAuthenticated;
    }

    private int getCustomerId(String username) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {DatabaseContract.CustomerEntry.COLUMN_ID};
        String selection = DatabaseContract.CustomerEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                DatabaseContract.CustomerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int customerId = -1; // Default value if ID is not found
        if (cursor.moveToFirst()) {
            customerId = cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerEntry.COLUMN_ID));
        }

        cursor.close();
        db.close();

        return customerId;
    }


    private void openCustomerDashboard() {
        // Save the customer ID to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("customerId", customerId);
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, CustomerDashboardActivity.class);
        intent.putExtra("role", "customer");
        startActivity(intent);
    }


    private void openRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}