package com.example.blockchain.DOCTOR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blockchain.CODE_ALGORITHM.AES;
import com.example.blockchain.COMMON.Utility;
import com.example.blockchain.Login;
import com.example.blockchain.R;

import java.util.HashMap;
import java.util.Map;

public class PaientProfile extends AppCompatActivity {
TextView name,pid,email,age,address,phone;
ImageView logoutdoctors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paient_profile);
        name=findViewById(R.id.namep);
        pid=findViewById(R.id.idp);
        email=findViewById(R.id.emailp);
        age=findViewById(R.id.agep);
        address=findViewById(R.id.addresp);
        phone=findViewById(R.id.phonep);
        logoutdoctors=findViewById(R.id.backdoctore);
        logoutdoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        PatientProfile();
    }
    public void PatientProfile() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                if (!response.trim().equals("failed")) {
                    String[] data = response.trim().split("#");
                    String nam = data[0].toString();
                    String em = data[1].toString();
                    String ag = data[2].toString();
                    String ppid = data[3].toString();
                    String ads = data[4].toString();
                    String ph = data[5].toString();
                   String  naming = AES.decrypt(nam, "secretKey");
                    String  emailng = AES.decrypt(em, "secretKey");
                    String  aging = AES.decrypt(ag, "secretKey");
                    String  ades = AES.decrypt(ads, "secretKey");
                    String  pphon = AES.decrypt(ph, "secretKey");
                    String  idval = AES.decrypt(ppid, "secretKey");
                    name.setText(emailng);
                    email.setText(aging);
                    age.setText(idval);
                    pid.setText(naming);
                    address.setText(ades);
                    phone.setText(pphon);
                } else {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "my Error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences prefs =getSharedPreferences("profilepefer", MODE_PRIVATE);
                final String keyy = prefs.getString("UserKey", "No");
                final String uid = prefs.getString("u_id", "No");

                map.put("key", "patientprofile");
                map.put("userkey", keyy.trim());
                map.put("userid", uid.trim());

                return map;
            }
        };
        queue.add(request);
    }
}