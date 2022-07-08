package com.example.blockchain.DOCTOR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blockchain.R;

public class DoctorHome extends AppCompatActivity {
ImageView profile,search;
String PROFILE,SEARCH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        profile=findViewById(R.id.profile);
        search=findViewById(R.id.searchid);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PaientProfile.class));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SearchMedicalReport.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Logout the Account", Toast.LENGTH_SHORT).show();
    }
}