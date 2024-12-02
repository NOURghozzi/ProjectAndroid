package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
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

        // Initialiser le TextView
        userInfoTextView = findViewById(R.id.userInfoTextView);
        dbHelper = new Database(this);

        // Récupérer le nom d'utilisateur depuis l'intent
        String username = getIntent().getStringExtra("username");
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
