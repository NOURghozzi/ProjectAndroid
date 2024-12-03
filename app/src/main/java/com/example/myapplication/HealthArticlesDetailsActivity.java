package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HealthArticlesDetailsActivity extends AppCompatActivity {

    TextView tv1;
    ImageView img;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles_details);

        btnBack = findViewById(R.id.buttonHADback);
        tv1 = findViewById(R.id.textViewHAD_title);
        img = findViewById(R.id.imageViewHAD);

        // Récupération de l'Intent
        Intent intent = getIntent();

        // Récupérer les données envoyées
        String title = intent.getStringExtra("text1");
        int imageResId = intent.getIntExtra("text2", -1); // Valeur par défaut si l'image est non trouvée
        String description = intent.getStringExtra("text3"); // Description de l'article si nécessaire

        // Affectation des données aux vues
        tv1.setText(title);
        if (imageResId != -1) {
            img.setImageResource(imageResId); // Affichage de l'image
        }
        TextView textViewUrl = findViewById(R.id.textViewUrl);
        textViewUrl.setOnClickListener(v -> {
            String url = "https://www.example.com"; // Remplacez par votre URL
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticlesDetailsActivity.this, HealthArticleActivity.class));
            }
        });
    }
}