package com.example.blockchain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blockchain.COMMON.Utility;
import com.example.blockchain.DOCTOR.DoctorHome;
import com.example.blockchain.DOCTOR.DoctorMainHome;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    ImageView btnlink;
    String N,UN,PH,ADD,PASS;
    TextView txt,bbb,doctorlink;
EditText email,nam,pas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        bbb=findViewById(R.id.blinky);
        email=findViewById(R.id.username);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.play);
        bbb.startAnimation(animation);
        doctorlink=findViewById(R.id.doctorlink);
        pas=findViewById(R.id.password);
        txt=findViewById(R.id.linktoreg);
        btnlink=findViewById(R.id.loginbtn);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UsaerRegister.class));
            }
        });

 doctorlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Doctor_Register.class));
            }
        });
        btnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UN=email.getText().toString().trim();

                PASS=pas.getText().toString().trim();

                if (UN.isEmpty()) {
                    Snackbar.make(email, "Enter name", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {} })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }
//            else if (!UN.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
//
//                    Snackbar.make(email, "Invalid Email Id ", Snackbar.LENGTH_LONG)
//                            .setAction("DISMISS", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {} })
//                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
//                            .show();
//                }
            else if (PASS.isEmpty()) {
                    Snackbar.make(pas, "Invalid Password", Snackbar.LENGTH_LONG)
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "DoubleClick the Button to Exit", Toast.LENGTH_SHORT).show();
    }

    private void Register() {

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                Log.d("******", response);
                if(!response.trim().equals("failed")) {



                    String data = response;
                    String respArr[] = data.trim().split("#");
                    SharedPreferences.Editor editor = getSharedPreferences("profilepefer", MODE_PRIVATE).edit();
                    editor.putString("u_id", "" + respArr[1]);
                    editor.putString("type", "" + respArr[0]);
                    editor.putString("UserKey", "" + respArr[2]);

                    editor.commit();

                    String s=respArr[0].toString();
                    if (s.equals("user")) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), UserHome.class));
                    }

                    else if (s.equals("doctor")) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DoctorMainHome.class));
                    }

                    else {
                        Toast.makeText(getApplicationContext() , "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Login.this, "login failed", Toast.LENGTH_SHORT).show();
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
                map.put("key", "login");
                map.put("uname", UN.trim());
                map.put("pass",PASS.trim());
                return map;
            }
        };
        queue.add(request);
    }
}