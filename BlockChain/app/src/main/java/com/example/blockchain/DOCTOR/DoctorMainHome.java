package com.example.blockchain.DOCTOR;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blockchain.COMMON.DataModel;
import com.example.blockchain.COMMON.PatientViewAdaptor;
import com.example.blockchain.COMMON.Utility;
import com.example.blockchain.Login;
import com.example.blockchain.R;
import com.example.blockchain.USER.MedicaList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorMainHome extends AppCompatActivity {

    ListView patientlist;
    List<DataModel> arrayList;
    PatientViewAdaptor adpater;
    String familyval;
ImageView imgg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main_home);
        arrayList=new ArrayList<DataModel>();
        patientlist=findViewById(R.id.mydoctorlist);
        imgg=findViewById(R.id.logoutdoctorsss);
        imgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        patientdetail();
        adpater=new PatientViewAdaptor(DoctorMainHome.this, arrayList);
        patientlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                DataModel selItem = (DataModel) arrayList.get(i);
//                String value= selItem.getName();
//                Toast.makeText(DoctorMainHome.this, ""+value, Toast.LENGTH_SHORT).show();
              //  String patientdetai = arrayList.get(position).getName();
                Intent intt=new Intent(getApplicationContext(),SearchMedicalReport.class);
             //   intt.putExtra("mysel",patientdetai);
               // Toast.makeText(DoctorMainHome.this, ".."+patientdetai, Toast.LENGTH_SHORT).show();
                startActivity(intt);
            }
        });

    }
    public void patientdetail()
    {
        RequestQueue queue = Volley.newRequestQueue(DoctorMainHome.this);
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******",response);
                if(!response.trim().equals("failed"))
                {
//                 Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson=new Gson();
                    arrayList = Arrays.asList(gson.fromJson(response, DataModel[].class));
                    PatientViewAdaptor adapter = new PatientViewAdaptor(DoctorMainHome.this,arrayList);
                    patientlist.setAdapter(adapter);
                    registerForContextMenu(patientlist);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences prefs = getSharedPreferences("profilepefer", MODE_PRIVATE);
                final String uid = prefs.getString("u_id", "No");

                Map<String, String> map = new HashMap<String, String>();
                map.put("key","getallpatientrecordat");
              //  map.put("userid",uid);
                return map;
            }
        };
        queue.add(request);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Logout the Account", Toast.LENGTH_SHORT).show();
    }
}
