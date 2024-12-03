package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
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

public class LabTestDetailsActivity extends AppCompatActivity {

    private TextView tvPackageName, tvTotalCost;
    private EditText edDetails;
    private Button btnAddToCart, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        // Initialisation des vues
        tvPackageName = findViewById(R.id.textViewLDPackageName);
        tvTotalCost = findViewById(R.id.textViewLDTotalCost);
        edDetails = findViewById(R.id.editTextLDTextMultiline);
        btnAddToCart = findViewById(R.id.buttonLDAddToCart);
        btnBack = findViewById(R.id.buttonLDBack);

        // Désactiver la saisie dans le champ de texte multiligne
        edDetails.setKeyListener(null);

        // Récupération des données passées via l'intent
        Intent intent = getIntent();
        if (intent != null) {
            String packageName = intent.getStringExtra("text1");
            String totalCost = intent.getStringExtra("text2");
            String details = intent.getStringExtra("text3");

            tvPackageName.setText(packageName != null ? packageName : "Unknown Package");
            tvTotalCost.setText(totalCost != null ? totalCost : "0");
            edDetails.setText(details != null ? "Total Cost: " + details + "/-" : "Details not available");
        }

        // Action pour le bouton Retour
        btnBack.setOnClickListener(view -> startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class)));

        // Action pour le bouton Ajouter au panier
        btnAddToCart.setOnClickListener(view -> {
            // Vérification de l'utilisateur dans les préférences partagées
            SharedPreferences sharedPreferences = getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");
            Log.d("sharedPreferences", "user est " + sharedPreferences + ", " + username);

            if (username == null ) {
                Log.d("SharedPrefsTest", "No user logged in.");
                Toast.makeText(getApplicationContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Connexion à la base de données
            Database db = new Database(getApplicationContext());
            Log.d("user", "username est "   +", " + username);

            // Récupérer l'ID utilisateur
            int userId = db.getUserIdByUsername(username);
            if (userId == -1) {
                Log.d("DatabaseTest", "User not found: " + username);
                Toast.makeText(getApplicationContext(), "User not found in the database!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Récupérer les données pour le panier
            String product = tvPackageName.getText().toString();
            float price = getPriceFromDatabase(product);

            // Vérifier si le produit est déjà dans le panier
            if (db.CheckCart(userId, product) == 1) {
                Log.d("CartTest", "Product already in cart: " + product);
                Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();

                // Lancer OrderSummaryActivity
                Intent intent1 = new Intent(LabTestDetailsActivity.this, OrderSummaryActivity.class);
                intent1.putExtra("username", username);
                startActivity(intent1);
            } else {
                // Ajouter l'article au panier
                db.addCart(userId, product, price, "lab");
                Log.d("CartTest", "Product added to cart: " + product);
                Toast.makeText(getApplicationContext(), "Product Added to Cart", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(LabTestDetailsActivity.this, OrderSummaryActivity.class);
                intent1.putExtra("username", username);
                startActivity(intent1);
            }
        });

    }
        private float getPriceFromDatabase(String productName) {
        float price;

        switch (productName) {
            case "Package1: Full Body Checkup":
                price = 199;
                break;
            case "Package2: Blood Glucose Fasting":
                price = 299;
                break;
            case "Package3: COVID-19 Antibody":
                price = 399;
                break;
            case "Package4: Thyroid Check":
                price = 499;
                break;
            case "Package5: Immunity Check":
                price = 599;
                break;
            default:
                price = 100; // Produit non trouvé
                Toast.makeText(this, "Price not found for product: " + productName, Toast.LENGTH_SHORT).show();
        }

        return price;
    }
}
