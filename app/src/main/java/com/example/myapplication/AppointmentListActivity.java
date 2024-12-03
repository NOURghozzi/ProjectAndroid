package com.example.myapplication;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListActivity extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        ImageView btnBck = findViewById(R.id.iconChat);
        ImageView iconProfile = findViewById(R.id.iconProfile);

        btnBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppointmentListActivity.this, HomeActivity.class));
            }
        });
        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppointmentListActivity.this, UserProfileActivity.class));
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("shared_Prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", ""); // "default" si non trouvé
        Log.d(TAG, "Inserting username" + username);
        // Récupérer le conteneur où ajouter les cartes
        LinearLayout container = findViewById(R.id.appointments_container);

        // Récupérer les données de la base de données
        Database db = new Database(getApplicationContext());

        int userId = db.getUserIdByUsername(username);  // Exemple d'ID d'utilisateur
        List<Appointment> appointments = db.getAppointmentsByUserId(userId);
        Log.d(TAG, "Fetched Appointments:" + appointments);

        // Ajouter les rendez-vous dynamiquement
        for (Appointment appointment : appointments) {
            // Gonfler le layout de chaque rendez-vous
            View appointmentView = getLayoutInflater().inflate(R.layout.multi_lines, container, false);

            // Récupérer les TextViews
            TextView doctorNameTextView = appointmentView.findViewById(R.id.line_a);
            TextView hospitalAddressTextView = appointmentView.findViewById(R.id.line_b);
            TextView experienceTextView = appointmentView.findViewById(R.id.line_c);
            TextView mobileNoTextView = appointmentView.findViewById(R.id.line_e);
           TextView consultationFeeTextView = appointmentView.findViewById(R.id.line_d);

            // Définir les données sur chaque TextView
            doctorNameTextView.setText("Conseltation");
            hospitalAddressTextView.setText(appointment.getAddress());
            experienceTextView.setText(appointment.getDate());
            mobileNoTextView.setText(appointment.getPhone());
            consultationFeeTextView.setText("nourghozzi9@gmail.com");

            // Ajouter la vue au conteneur
            container.addView(appointmentView);
        }
    }
}
