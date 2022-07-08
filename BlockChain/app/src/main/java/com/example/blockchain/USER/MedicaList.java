package com.example.blockchain.USER;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.blockchain.COMMON.DoctorMedicalAdaptor;
import com.example.blockchain.COMMON.MedicalViewAdaptor;
import com.example.blockchain.COMMON.PatientViewAdaptor;
import com.example.blockchain.COMMON.Utility;
import com.example.blockchain.DOCTOR.SearchMedicalReport;
import com.example.blockchain.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class MedicaList extends Fragment {

    ListView patientlist;
    List<DataModel> arrayList;
    MedicalViewAdaptor adpater;
    String familyval;


    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    private long fileSize = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_medica_list, container, false);
        patientlist = view.findViewById(R.id.mymedicalreport);
        arrayList=new ArrayList<DataModel>();
        adpater=new MedicalViewAdaptor(getActivity(), arrayList);
        Bundle bundle = this.getArguments();


        progressBar = new ProgressDialog(view.getContext());
        //progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(R.color.light));
        progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(getColor(R.color.purple_200))));
        //progressBar.getWindow().setBackgroundBlurRadius();
        progressBar.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        progressBar.setCancelable(true);
        progressBar.setMessage("Wait for processing ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setCancelable(false);
        progressBar.setMax(100);
        progressBar.show();
        //reset progress bar status
        progressBarStatus = 0;
        //reset filesize
        fileSize = 0;
        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    // process some tasks
                    progressBarStatus = doSomeTasks();
                    // your computer is too fast, sleep 1 second
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Update the progress bar
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                // ok, file is downloaded,
                if (progressBarStatus >= 100) {
                    // sleep 2 seconds, so that you can see the 100%
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // close the progress bar dialog
                    progressBar.dismiss();
                  //  startActivity(new Intent(getContext(), MedicaList.class));
                }
            }
        }).start();

        GetPlantsByFamily();
        return view;
    }

    public void GetPlantsByFamily()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******",response);
                if(!response.trim().equals("failed"))
                {
                Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson=new Gson();
                    arrayList = Arrays.asList(gson.fromJson(response, DataModel[].class));
                    MedicalViewAdaptor adapter = new MedicalViewAdaptor(getActivity(),arrayList);
                    patientlist.setAdapter(adapter);
                    registerForContextMenu(patientlist);
                }
                else
                {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences prefs = getContext().getSharedPreferences("profilepefer", MODE_PRIVATE);
                final String uid = prefs.getString("u_id", "No");
                final Bundle b = getArguments();
                DataModel model = b.getParcelable("clicked_item");
                String pname=model.getPatientname().toString().trim();
                Map<String, String> map = new HashMap<String, String>();
                map.put("key","GetMedicalList");
                map.put("userid",uid);
                map.put("pname",pname);
                return map;
            }
        };
        queue.add(request);
    }
    public int doSomeTasks(){
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
}
