package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText edUsername, edPassword ;
    Button btn ;
    TextView tv,testing ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername= findViewById(R.id.editTextLoginUsername);
        edPassword= findViewById(R.id.editTextLoginPassword);
        btn= findViewById(R.id.buttonLogin);
        tv= findViewById(R.id.textViewNewUser);
        testing=findViewById(R.id.textView);

        btn.setOnClickListener(this:: onClick);
        tv.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this ,RegisterActivity.class)));
        btn.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,HomeActivity.class)));

    }

    private void onClick(View view) {
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();
        try (Database db = new Database(getApplicationContext(), "santé", null, 1)) {

            if (username.length() == 0 || password.length() == 0) {
                Toast.makeText(getApplicationContext(), "please fill all details ", Toast.LENGTH_SHORT).show();
            } else {
                if (db.Login(username, password) == 1) {
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("Shared_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    // to save our data with key and value .
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "invalid username and password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
