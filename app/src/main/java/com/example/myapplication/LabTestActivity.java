package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {

    // Define your packages with improved readability
    private String[][] packages = {
        {"Package1 : Full Body Checkup", "Description: Comprehensive health checkup covering vital tests.", "Duration: 1 hour", "Price: 999", "Notes: Ideal for overall health monitoring."},
        {"Package2: Blood Glucose Fasting", "Description: Test for fasting blood glucose levels to check for diabetes.", "Duration: 30 minutes", "Price: 299", "Notes: Recommended for early diabetes detection."},
        {"Package3: COVID-19 Antibody", "Description: Test to determine immunity from COVID-19.", "Duration: 30 minutes", "Price: 899", "Notes: Important for post-vaccination or recovery monitoring."},
        {"Package4: Thyroid Check", "Description: Test to assess thyroid function and detect any abnormalities.", "Duration: 45 minutes", "Price: 499", "Notes: Recommended for individuals with fatigue or weight issues."},
        {"Package5: Immunity Check", "Description: Test to evaluate the strength of your immune system.", "Duration: 1 hour", "Price: 699", "Notes: Helps in understanding your body's defense mechanisms."}

        };

    // Define the details for each package
    private String[] packageDetails = {
            "Blood Glucose Fasting\nHbA1c\nIron Studies\nKidney Function Test\nLDH Lactate Dehydrogenase,",
            "Serum\nLipid Profile\nLiver Function Test",
            "Lipid Profile"
    };

    private HashMap<String, String> item;
    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter sa;
    private Button btnGoToCart, btnBack;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        // Initialize views
        btnGoToCart = findViewById(R.id.buttonLDAddToCart);
        btnBack = findViewById(R.id.buttonLTBack);
        listView = findViewById(R.id.listViewLT);

        // Set back button action
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        // Prepare data for ListView
        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total Cost " + packages[i][4] + "/-");
            list.add(item);
        }

        // Set SimpleAdapter to ListView
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);

        // Set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                it.putExtra("text1", packages[position][0]);
                it.putExtra("text2", packageDetails[position]);
                it.putExtra("text3", packages[position][4]);
                startActivity(it);
            }
        });
    }
}
