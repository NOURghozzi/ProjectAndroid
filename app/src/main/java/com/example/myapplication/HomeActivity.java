package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageView iconProfile = findViewById(R.id.iconProfile);

        // Gestionnaire de clic pour iconProfile

        // Récupérer le nom d'utilisateur depuis SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_Prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", ""); // "default" si non trouvé
        iconProfile.setOnClickListener(view -> {
            Intent intent1 = new Intent(HomeActivity.this, UserProfileActivity.class);
            intent1.putExtra("username", username);  // Passez le username via l'Intent
            startActivity(intent1);
        });
        if (!username.isEmpty()) {
            // Afficher un message de bienvenue si l'utilisateur est authentifié
            Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
        } else {
            // Si le nom d'utilisateur n'est pas trouvé, rediriger vers la page de connexion
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish(); // Empêche de revenir à l'écran d'accueil
        }

        // Gérer les cartes pour la navigation
        CardView exit = findViewById(R.id.cardExit);
        exit.setOnClickListener(view -> {
            // Déconnexion de l'utilisateur et suppression des données
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });

        // Autres actions des cartes
        CardView findDoctor = findViewById(R.id.cardFindDoctor);
        findDoctor.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, FindDoctorActivity.class)));

        CardView labTest = findViewById(R.id.cardLabTest);
        labTest.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, LabTestActivity.class)));

        CardView HealthArticles = findViewById(R.id.cardHealthDoctor);
        HealthArticles.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, HealthArticleActivity.class)));

        CardView carts = findViewById(R.id.carts);
        carts.setOnClickListener(view -> {
            // Créez l'Intent et ajoutez le username
            Intent intent1 = new Intent(HomeActivity.this, OrderSummaryActivity.class);
            intent1.putExtra("username", username);  // Passez le username via l'Intent
            startActivity(intent1);  // Lancez l'Activity
        });
        CardView appointment = findViewById(R.id.cardAppointment);
        appointment.setOnClickListener(view -> {
            // Créez l'Intent et ajoutez le username
            Intent intent1 = new Intent(HomeActivity.this, AppointmentListActivity.class);
            intent1.putExtra("username", username);  // Passez le username via l'Intent
            startActivity(intent1);  // Lancez l'Activity
        });

    }}
