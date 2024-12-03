package com.example.myapplication;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnBook, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        tv = findViewById(R.id.textViewAppTitle);
        ed1 = findViewById(R.id.editTextAppFullName);
        ed2 = findViewById(R.id.editTextAppAddress);
        ed3 = findViewById(R.id.editTextContactNumber);
        ed4 = findViewById(R.id.editTextAppFees);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        btnBook = findViewById(R.id.buttonBookAppointment);
        btnBack = findViewById(R.id.buttonAppBack);

        // Rendre les EditText non modifiables mais cliquables
        ed1.setFocusable(false);
        ed2.setFocusable(false);
        ed3.setFocusable(false);
        ed4.setFocusable(false);
        ed1.setClickable(true);
        ed2.setClickable(true);
        ed3.setClickable(true);
        ed4.setClickable(false);

        // Récupérer les données passées via Intent
        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String fullname = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText("Cons Fees: " + fees + "/-");

        // Initialiser DatePicker et TimePicker
        initDatePicker();
        dateButton.setOnClickListener(view -> datePickerDialog.show());

        initTimePicker();
        timeButton.setOnClickListener(view -> timePickerDialog.show());

        // Retourner à la page précédente
        btnBack.setOnClickListener(view -> startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class)));
        btnBook.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                String title = "Consultation"; // Exemple de titre
                String date = dateButton.getText().toString();
                String time = timeButton.getText().toString();
                String address = ed1.getText().toString();
                String phone = ed3.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("shared_Prefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", ""); // "default" si non trouvé
                Log.d(TAG, "Inserting username" + username);

                if (date.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                Database db = new Database(getApplicationContext());
                int userId = db.getUserIdByUsername(username); // Remplacer par l'ID réel de l'utilisateur

                Log.d(TAG, "Inserting Appointment: " +
                        "Date: " +dateButton.getText().toString()+
                        ", Address: " + ed1.getText().toString() +
                        ", Phone: " +ed3.getText().toString() +
                        ", User ID: " + userId);
                // Créer un objet Appointment
                Appointment appointment = new Appointment(title, date,time, address, phone, userId);

                // Enregistrer dans la base de données

                db.bookAppointment(appointment);
                Intent intent = new Intent(BookAppointmentActivity.this, AppointmentListActivity.class);
                startActivity(intent);

            }
        });


        // Gérer le clic sur l'adresse pour ouvrir Google Maps
        ed1.setOnClickListener(view -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(fullname));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            try {
                startActivity(mapIntent);
            } catch (ActivityNotFoundException e) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Google Maps n'est pas installé ou activé.")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });




        // Gérer le clic sur l'email pour ouvrir une application de messagerie
        ed2.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822"); // Ensure the intent is compatible with email apps
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{fullname}); // Recipient email
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the email");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body of the email");

            try {
                startActivity(Intent.createChooser(emailIntent, "Choisissez une application de messagerie"));
            } catch (android.content.ActivityNotFoundException e) {
                // No email app is available
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Aucune application de messagerie n'est installée.")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });



        // Gérer le clic sur le numéro de contact pour lancer un appel téléphonique
        ed3.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contact));
            startActivity(callIntent);
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            dateButton.setText(day + "/" + month + "/" + year);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000); // Ajouter 1 jour
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hourOfDay, minute) -> {
            timeButton.setText(String.format("%02d:%02d", hourOfDay, minute));
        };

        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, timeSetListener, hrs, mins, true);
    }
}
