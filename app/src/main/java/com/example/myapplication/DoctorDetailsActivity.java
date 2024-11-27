package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
    private final String[][] doctor_details1=
            {
                    {"Doctor Name: Ajit Saste","Hospital Address:Pimpri","Exp: 5 yrs","Mobile No: 9898989898","600"},
                    {"Doctor Name: Amira Halouani  ","Hospital Address: Oxford","Exp: 15 yrs","Mobile No :9898989890","400"},
                    {"Doctor Name: Eya Siala ","Hospital Address:Chinchwad","Exp: 8 yrs","Mobile No :9898989860","900"},
                    {"Doctor Name:Deapak Deshmukh ","Hospital Address: Katraj","Exp: 6 yrs","Mobile No :9898989850","500"},
                    {"Doctor Name: Pierre Wajenberg","Hospital Address: Pume","Exp: 7 yrs","Mobile No :9898989840","200"}
            };
    private final String[][] doctor_details2=
            {
                    {"Doctor Name: Ajit Saste","Hospital Address:Pimpri","Exp: 5 yrs","Mobile No: 9898989898","600"},
                    {"Doctor Name: Amira Halouani  ","Hospital Address: Oxford","Exp: 15 yrs","Mobile No :9898989890","400"},
                    {"Doctor Name: Eya Siala ","Hospital Address:Chinchwad","Exp: 8 yrs","Mobile No :9898989860","800"},
                    {"Doctor Name:Deapak Deshmukh ","Hospital Address: Katraj","Exp: 6 yrs","Mobile No :9898989850","800"},
                    {"Doctor Name: Pierre Wajenberg","Hospital Address: Pume","Exp: 7 yrs","Mobile No :9898989840","200"}
            };
    private final String[][] doctor_details3=
            {
                    {"Doctor Name: Ajit Saste","Hospital Address:Pimpri","Exp: 5 yrs","Mobile No: 9898989898","100"},
                    {"Doctor Name: Amira Halouani  ","Hospital Address: Oxford","Exp: 15 yrs","Mobile No :9898989890","400"},
                    {"Doctor Name: Eya Siala","Hospital Address:Chinchwad","Exp: 8 yrs","Mobile No :9898989860","900"},
                    {"Doctor Name:Deapak Deshmukh ","Hospital Address: Katraj","Exp: 6 yrs","Mobile No :9898989850","500"},
                    {"Doctor Name: Pierre Wajenberg","Hospital Address: Pume","Exp: 7 yrs","Mobile No :9898989840","200"}
            };
    private final String[][] doctor_details4=
            {
                    {"Doctor Name: Ajit Saste","Hospital Address:Pimpri","Exp: 5 yrs","Mobile No: 9898989898","600"},
                    {"Doctor Name: Amira Halouani  ","Hospital Address: Oxford","Exp: 15 yrs","Mobile No :9898989890","700"},
                    {"Doctor Name: Eya Siala  ","Hospital Address:Chinchwad","Exp: 8 yrs","Mobile No :9898989860","900"},
                    {"Doctor Name:Deapak Deshmukh ","Hospital Address: Katraj","Exp: 6 yrs","Mobile No :9898989850","300"},
                    {"Doctor Name: Pierre Wajenberg","Hospital Address: Pume","Exp: 7 yrs","Mobile No :9898989840","200"}
            };
    private  final String[][] doctor_details5=
            {
                    {"Doctor Name: Ajit Saste","Hospital Address:Pimpri","Exp: 5 yrs","Mobile No: 9898989898","400"},
                    {"Doctor Name: Amira Halouani  ","Hospital Address: Oxford","Exp: 15 yrs","Mobile No :9898989890","400"},
                    {"Doctor Name: Eya Siala ","Hospital Address:Chinchwad","Exp: 8 yrs","Mobile No :9898989860","860"},
                    {"Doctor Name:Deapak Deshmukh ","Hospital Address: Katraj","Exp: 6 yrs","Mobile No :9898989850","700"},
                    {"Doctor Name: Pierre Wajenberg","Hospital Address: Pume","Exp: 7 yrs","Mobile No :9898989840","230"}
            };
    TextView tv ;
    Button btn ;
    String[][] doctor_details ={};
    ArrayList list ;
    HashMap<String,String> item;
    SimpleAdapter sa ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv= findViewById(R.id.textViewDDTitle);
        btn= findViewById(R.id.buttonLTBack);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);
        if (title.compareTo("Family Physicians")==0)
            doctor_details=doctor_details1;
        else
        if (title.compareTo("Dietician")==0)
            doctor_details=doctor_details2;
        else
        if (title.compareTo("Dentist")==0)
            doctor_details=doctor_details3;
        else
        if (title.compareTo("Surgeon")==0)
            doctor_details=doctor_details4;
        else
            doctor_details=doctor_details5;


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this,FindDoctorActivity.class));
            }
        });

        list = new ArrayList();
        for (int i=0 ;i<doctor_details.length;i++ ) {
            item = new HashMap<String, String>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fess" + doctor_details[i][4] + "/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int []{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        ListView lst = findViewById(R.id.editTextLDTextMultiline);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this,BookAppointmentActivity.class);
                it.putExtra("text1",doctor_details[i][0]);
                it.putExtra("text2",doctor_details[i][1]);
                it.putExtra("text3",doctor_details[i][2]);
                it.putExtra("text4",doctor_details[i][3]);
                it.putExtra("text5",doctor_details[i][4]);
                startActivity(it);

            }
        });
    }
}
