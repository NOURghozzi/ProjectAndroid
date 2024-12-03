package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {

    ListView listViewOrder;
    TextView tvTotalPrice;
    Button btnConfirmOrder;
    Button bntback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        listViewOrder = findViewById(R.id.listViewOrder);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        bntback = findViewById(R.id.buttonLDBack2);
        // Get username from intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        bntback.setOnClickListener(view -> startActivity(new Intent(OrderSummaryActivity.this, LabTestActivity.class)));

        if (username == null ) {
            Toast.makeText(this, "Username is missing!", Toast.LENGTH_SHORT).show();
            return; // Exit the method if the username is missing
        }

        // Load cart items from the database
        Database db = new Database(getApplicationContext());
        int id = db.getUserIdByUsername(username);

        if (id == -1) {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            return; // Exit the method if the user is not found
        }

        // Fetch cart items and total price
        List<CartItem> cartItems = db.getCartItems(id);
        float totalPrice = db.getCartTotal(id);

        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
        } else {
            // Display cart items in the ListView
            ArrayAdapter<CartItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cartItems);
            listViewOrder.setAdapter(adapter);
        }

        // Display total price
        tvTotalPrice.setText("Total Price: " + totalPrice + " /-");

        // Confirm order action
        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Order confirmed!", Toast.LENGTH_SHORT).show();
                // You can add additional logic here to process the order, such as updating the database or navigating to another screen
            }
        });
    }
}