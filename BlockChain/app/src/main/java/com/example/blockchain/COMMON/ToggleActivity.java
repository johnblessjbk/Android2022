package com.example.blockchain.COMMON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.blockchain.R;

public class ToggleActivity extends AppCompatActivity {
CardView firstcard,scondcard;
ToggleButton fB,sB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);
        firstcard=findViewById(R.id.CARDFIRST);
        scondcard=findViewById(R.id.SECCARD);
        fB=findViewById(R.id.FTOG);
        sB=findViewById(R.id.STOG);
        firstcard.setVisibility(View.INVISIBLE);
        scondcard.setVisibility(View.INVISIBLE);
  //      fB.setOnCheckedChangeListener(new View);
      fB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (fB.isChecked()) {
                  firstcard.setVisibility(View.VISIBLE);
                  scondcard.setVisibility(View.GONE);


              } else {
                  firstcard.setVisibility(View.GONE);
                  scondcard.setVisibility(View.GONE);

              }
          }
      });
        sB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sB.isChecked()) {
                    firstcard.setVisibility(View.GONE);
                    scondcard.setVisibility(View.VISIBLE);


                } else {
                    firstcard.setVisibility(View.GONE);
                    scondcard.setVisibility(View.GONE);

                }
            }
        });
    }
}