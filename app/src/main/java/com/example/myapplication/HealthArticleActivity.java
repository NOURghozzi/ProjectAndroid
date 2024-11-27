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

public class HealthArticleActivity extends AppCompatActivity {
    private  String[][] Health_details=
            {
                    {"Walking Daily","","","","Click on more Details "},
                    {"Home care pf COVID 19  ","","","","Click on more Details "},
                    {"STOP SMOKING ","","","","Click on more Details "},
                    {"Menstrual Cramps","","","","Click on more Details "},
                    {"Healthy Gut","","","","Click on more Details "}
            };
    private int [] images ={
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5
    };
    HashMap <String,String> item ;
    ArrayList list ;
    SimpleAdapter sa ;

    Button btnBck ;
    ListView lst ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article);

        lst = findViewById(R.id.listViewHA);
        btnBck=findViewById(R.id.buttonHABack);

        btnBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticleActivity.this,HomeActivity.class));
            }
        });

        list = new ArrayList();
        for (int i=0 ; i< Health_details.length ;i++){
            item = new HashMap<String,String>();
            item.put("line1",Health_details[i][0]);
            item.put("line2",Health_details[i][1]);
            item.put("line3",Health_details[i][2]);
            item.put("line4",Health_details[i][3]);
            item.put("line5",Health_details[i][4]);
            list.add( item) ;
        }
        sa = new SimpleAdapter(this,list,R.layout.multi_lines,new String[]{"line1","line2","line3","line4","line5"},new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});

        lst.setAdapter(sa);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(HealthArticleActivity.this,HealthArticlesDetailsActivity.class);
                it.putExtra("text1",Health_details[i][0]);
                it.putExtra("text2",images[i]);
                startActivity(it);
            }
        });

    }
}
