package com.example.blockchain.USER;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blockchain.COMMON.DataModel;
import com.example.blockchain.COMMON.DoctorMedicalAdaptor;
import com.example.blockchain.COMMON.Utility;
import com.example.blockchain.DOCTOR.SearchMedicalReport;
import com.example.blockchain.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class DataReport extends Fragment {


    String getsaerchvalue,LINKDATA;
    ListView listdata;
    public static final int PERMISSION_STORAGE_CODE = 1000;

    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    List<DataModel> arrayList;
    private long fileSize = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_data_report, container, false);

        listdata =view. findViewById(R.id.datareport);
        getPatientMedicalData();
        return view;
    }

    private void getPatientMedicalData() {
        //  Toast.makeText(getApplicationContext(), "okay", Toast.LENGTH_SHORT).show();
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);

                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(getActivity(), "YES", Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    arrayList = Arrays.asList(gson.fromJson(response, DataModel[].class));
                    DoctorMedicalAdaptor adapter = new DoctorMedicalAdaptor(getActivity(), arrayList);
                    listdata.setAdapter(adapter);
                    registerForContextMenu(listdata);


                } else {
                    Toast.makeText(getContext(), "NO", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences prefs = getActivity().getSharedPreferences("profilepefer", MODE_PRIVATE);
                final String userkey = prefs.getString("UserKey", "No");
                final String uid = prefs.getString("u_id", "No");
                map.put("key", "userdatamedical");
                map.put("userkey", uid.trim());
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
       // DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
      //  downloadManager.enqueue(reust);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_STORAGE_CODE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_DENIED){
                    startdownloding();
                }else{
                    Toast.makeText(getActivity(), "Permission Denied....", Toast.LENGTH_SHORT).show();
                }
        }
    }
}