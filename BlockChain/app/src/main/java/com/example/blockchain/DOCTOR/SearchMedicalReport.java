package com.example.blockchain.DOCTOR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blockchain.COMMON.DataModel;
import com.example.blockchain.COMMON.DoctorMedicalAdaptor;
import com.example.blockchain.COMMON.MedicalViewAdaptor;
import com.example.blockchain.COMMON.Utility;
import com.example.blockchain.Login;
import com.example.blockchain.R;
import com.example.blockchain.UsaerRegister;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchMedicalReport extends AppCompatActivity {

    ImageView searh;
    EditText enterdata;
    String getsaerchvalue,LINKDATA;
    ListView listdata;
    public static final int PERMISSION_STORAGE_CODE = 1000;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    List<DataModel> arrayList;
    DoctorMedicalAdaptor adpater;
    private long fileSize = 0;
    TextView ifnot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medical_report);
        searh = findViewById(R.id.searching);
        enterdata = findViewById(R.id.entering);
        listdata = findViewById(R.id.idvalue);
        ifnot=findViewById(R.id.ifnotshow);
        ifnot.setVisibility(View.VISIBLE);

        listdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             LINKDATA=arrayList.get(position).getLink();
                AlertDialog.Builder dialog = new AlertDialog.Builder(SearchMedicalReport.this);
                dialog.setCancelable(false);
                dialog.setTitle("File Downloading");
                dialog.setMessage("Do you want to download : "+LINKDATA);
                dialog.setIcon(R.drawable.ic_baseline_download_for_offline_24);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  startActivity(new Intent(getActivity(), Shopregistration.class));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                String permission[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permission, PERMISSION_STORAGE_CODE);
                            }else{
                                startdownloding();
                            }
                        } else {
                            startdownloding();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });
        searh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getsaerchvalue = enterdata.getText().toString();
                if (getsaerchvalue.isEmpty()) {
                    Snackbar.make(enterdata, "Please enter patient id", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    getPatientMedicalData();
                }
            }
        });
    }

    private void getPatientMedicalData() {
        //  Toast.makeText(getApplicationContext(), "okay", Toast.LENGTH_SHORT).show();
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.d("******", response);

                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(SearchMedicalReport.this, "YES", Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    arrayList = Arrays.asList(gson.fromJson(response, DataModel[].class));
                    DoctorMedicalAdaptor adapter = new DoctorMedicalAdaptor(SearchMedicalReport.this, arrayList);
                    listdata.setAdapter(adapter);
                    registerForContextMenu(listdata);
                    listdata.setVisibility(View.VISIBLE);
ifnot.setVisibility(View.INVISIBLE);
                } else {
                    listdata.setVisibility(View.INVISIBLE);
                    ifnot.setVisibility(View.VISIBLE);
                    ifnot.setText("No Medical Data Found");

                    Toast.makeText(SearchMedicalReport.this, "No Medical Data Found", Toast.LENGTH_SHORT).show();
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

                map.put("key", "SerachingMedicalReport");
               // map.put("userkey", keyy.trim());
              //  map.put("doctorid", id.trim().toString());
                map.put("searchdata", getsaerchvalue.trim());

                return map;
            }
        };
        queue.add(request);

    }

    public int doSomeTasks() {

        while (fileSize <= 1000000) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;
            } else if (fileSize == 200000) {
                return 20;
            } else if (fileSize == 300000) {
                return 30;
            }
            // ...add your own

        }

        return 100;

    }
    private void startdownloding() {

        DownloadManager.Request reust=new DownloadManager.Request(Uri.parse(LINKDATA));
        reust.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI|DownloadManager.Request.NETWORK_MOBILE);
        reust.setTitle("Donloading");
        reust.setDescription("PDF downloading ...");
        reust.allowScanningByMediaScanner();
        reust.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        reust.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());
        DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(reust);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_STORAGE_CODE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_DENIED){
                    startdownloding();
                }else{
                    Toast.makeText(getApplicationContext(), "Permission Denied....", Toast.LENGTH_SHORT).show();
                }
        }
    }
}