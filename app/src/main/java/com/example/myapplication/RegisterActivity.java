package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText edUsername, edEmail, edPassword, edConfirm;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        edUsername = findViewById(R.id.editTextAppFullName);
        edEmail = findViewById(R.id.editTextAppAddress);
        edPassword = findViewById(R.id.editTextContactNumber);
        edConfirm = findViewById(R.id.editTextAppFees);
        btnRegister = findViewById(R.id.buttonBookAppointment);
        tvLogin = findViewById(R.id.textViewExistingUser);

        // Redirect to login screen
        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Handle register button click
        btnRegister.setOnClickListener(view -> handleRegister());
    }

    private void handleRegister() {
        String username = edUsername.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString();
        String confirmPassword = edConfirm.getText().toString();

        Database db = new Database(getApplicationContext());

        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Please fill all details");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("Password and confirm password didn't match");
            return;
        }

        if (!isValidPassword(password)) {
            showToast("Password must contain at least 8 characters, a letter, a digit, and a symbol");
            return;
        }

        // Register the user
        db.register(username, email, password);
        showToast("Registration successful");
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    // Validate password
    private boolean isValidPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasLetter = false, hasDigit = false, hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSymbol = true;

            if (hasLetter && hasDigit && hasSymbol) return true;
        }
        return false;
    }

    // Utility method to show a toast
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
