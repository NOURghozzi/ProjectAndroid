package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticleActivity extends AppCompatActivity {

    private String[][] Health_details = {
            {"Walking Daily", "on 2024", "Marcher quotidiennement améliore la santé cardiovasculaire et réduit le stress.",
                    "https://www.google.com/url?sa=i&...", "Voir détails"},
            {"Home care of COVID-19", "on 2021", "Conseils pour gérer les symptômes du COVID-19 à domicile et éviter la transmission.",
                    "https://www.pinterest.com/...", "Voir détails"},
            {"STOP SMOKING", "on 2022", "Arrêter de fumer améliore votre santé globale et réduit les risques de maladies graves.",
                    "https://www.cancerresearchuk.org/...", "Voir détails"},
            {"Menstrual Cramps", "on 2022", "Soulager les douleurs menstruelles avec des remèdes naturels et une bonne alimentation.",
                    "https://www.parents.com/...", "Voir détails"},
            {"Healthy Gut", "on 2021", "Maintenir un microbiome intestinal sain pour améliorer la digestion et l'immunité.",
                    "https://sunrisehospital.com/...", "Voir détails"}
    };

    private int[] images = {
            R.drawable.img_24,
            R.drawable.img_28,
            R.drawable.img_25,
            R.drawable.img_26,
            R.drawable.img_27
    };

    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;

    Button btnBck;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article);

        lst = findViewById(R.id.listViewHA);
        btnBck = findViewById(R.id.buttonHABack);

        btnBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticleActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < Health_details.length; i++) {
            item = new HashMap<>();
            item.put("line1", Health_details[i][0]);
            item.put("line2", Health_details[i][1]);
            item.put("line3", Health_details[i][2]);
            item.put("line4", Health_details[i][3]);
            item.put("line5", Health_details[i][4]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3",  "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_e});

        lst.setAdapter(sa);

        // Lier le clic sur un élément de la liste à l'activité de détail
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(HealthArticleActivity.this, HealthArticlesDetailsActivity.class);
                // Envoi des données supplémentaires dans l'intent
                it.putExtra("text1", Health_details[i][0]);  // Titre de l'article
                it.putExtra("text2", images[i]);              // Image correspondante
                it.putExtra("text3", Health_details[i][2]);  // Description ou autre info si nécessaire
                it.putExtra("url", Health_details[i][3]);    // URL à ouvrir sur long click
                startActivity(it);
            }
        });


    }
}
