package com.example.myapplication;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = "UserProfileActivity";
    private TextView userInfoTextView;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ImageView home = findViewById(R.id.iconChat);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,HomeActivity.class));

            }
        });
        ImageView profile = findViewById(R.id.iconProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,UserProfileActivity.class));

            }
        });
        // Initialiser le TextView
        userInfoTextView = findViewById(R.id.userInfoTextView);
        dbHelper = new Database(this);

        // Récupérer le nom d'utilisateur depuis l'intent
        SharedPreferences sharedPreferences = getSharedPreferences("shared_Prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", ""); // "default" si non trouvé
        Log.d(TAG, "Inserting username" + username);
        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Nom d'utilisateur manquant !", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer les informations de l'utilisateur
        List<String> userInfo = dbHelper.getUserInfoByName(username);

        // Afficher les informations
        if (userInfo.isEmpty()) {
            userInfoTextView.setText("Aucune information trouvée pour : " + username);
            Log.d(TAG, "Aucune information trouvée.");
        } else {
            StringBuilder infoBuilder = new StringBuilder();
            for (String info : userInfo) {
                infoBuilder.append(info).append("\n");
            }
            userInfoTextView.setText(infoBuilder.toString());
            Log.d(TAG, "Informations affichées : " + infoBuilder.toString());
        }
    }
}
