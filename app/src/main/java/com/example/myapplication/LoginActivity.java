package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText edUsername, edPassword;
    Button btn;
    TextView tv, testing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.editTextLoginUsername);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUser);
        testing = findViewById(R.id.textView);

        // On clique sur le bouton de connexion
        btn.setOnClickListener(this::onClick);

        // Enregistrer un nouveau compte
        tv.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void onClick(View view) {
        String username = edUsername.getText().toString().trim().toLowerCase();
        String password = edPassword.getText().toString().trim();


        // Vérification si les champs sont vides
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
        } else {
            try (Database db = new Database(getApplicationContext())) {

                // Affichage des valeurs dans les logs pour vérifier ce qui est entré
                Log.d("Login", "Entered Username: " + username);
                Log.d("Login", "Entered Password: " + password);

                // Hash the entered password and check login
                String hashedPassword = hashPassword(password);

                if (db.Login(username, hashedPassword) == 1) {
                    // Connexion réussie
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                    // Enregistrer le nom d'utilisateur dans SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);  // Assurez-vous que `username` n'est pas vide
                    editor.apply();
                    Log.d("SharedPrefsTest", "Username saved: " + username);
                    // Ouvrir l'activité d'accueil
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Fermer l'activité de connexion pour ne pas revenir en arrière
                } else {
                    // Échec de la connexion
                    Log.d("Login", "Invalid credentials: " + username + ", " + password);  // Affiche dans les logs
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Hash the password (use the same hashing method used in RegisterActivity)
    private String hashPassword(String password) {
        return password; // Use the same hashing function (e.g., bcrypt)
    }
}
