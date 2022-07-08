package com.example.blockchain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blockchain.COMMON.Utility;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Doctor_Register extends AppCompatActivity {

    Button btnlink;
    String N,UN,PH,ADD,PASS;
    TextView txt;
    TextInputEditText nam;
    private TextInputEditText email,phon,pas,addr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__register);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        nam=(TextInputEditText)findViewById(R.id.nameyd);
        email=findViewById(R.id.emailyd);
        phon=findViewById(R.id.phoneyd);
        addr=findViewById(R.id.addressyd);
        btnlink=findViewById(R.id.btnyd);
        pas=findViewById(R.id.passsyd);
        txt=findViewById(R.id.linkyd);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


        btnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                N=nam.getText().toString().trim();
                UN=email.getText().toString().trim();
                PH=phon.getText().toString().trim();
                PASS=pas.getText().toString().trim();

                ADD=addr.getText().toString().trim();



                if (N.isEmpty()) {
                    Snackbar.make(nam, "Enter name", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {} })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();

                }
                else if (!N.matches("[ .a-zA-Z]+")) {
                    Snackbar.make(nam, "valid name", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {} })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();

                }else if (!UN.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

                    Snackbar.make(nam, "Invalid Email Id ", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {} })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                } else if (PH.isEmpty()) {
                    Snackbar.make(nam, "Phone Number", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {} })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();

                } else if (PH.length() != 10) {
                    Snackbar.make(nam, "Valid Phone Number", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {} })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();


                } else if (PASS.isEmpty()) {
                    Snackbar.make(nam, "Invalid Password", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {} })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();

                }
                else if (ADD.isEmpty()) {
                    Snackbar.make(nam, "Invalid Address ", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {} })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();

                }
                else {
                    Register();
                }
            }
        });
    }

    private void Register() {

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.d("******", response);

                if(!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(Doctor_Register.this, " success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));

                }else{
                    Toast.makeText(Doctor_Register.this, " failed", Toast.LENGTH_SHORT).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();

                map.put("key", "DoctorReg");
                map.put("name", N.trim());
                map.put("email", UN.trim());
                map.put("addres", ADD.trim());
                map.put("phone", PH.trim());
                map.put("pass",PASS.trim());

                return map;
            }
        };
        queue.add(request);

    }
}