package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        // Récupérer les données passées avec l'Intent
        Intent intent = getIntent();
        String appointmentDate = intent.getStringExtra("appointment_date");
        String appointmentAddress = intent.getStringExtra("appointment_address");
        String appointmentPhone = intent.getStringExtra("appointment_phone");

        // Afficher les données ou les utiliser
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvPhone = findViewById(R.id.tvPhone);

        tvDate.setText(appointmentDate);
        tvAddress.setText(appointmentAddress);
        tvPhone.setText(appointmentPhone);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_Prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", ""); // "default" si non trouvé
        Database db = new Database(getApplicationContext());

        int id = db.getUserIdByUsername(username);
        List<String> appointments = db.getAppointmentsByUserId(id);

        // Log ou autres opérations si nécessaires
        for (String appointment : appointments) {
            Log.d("Appointments", appointment);
        }
    }
}
