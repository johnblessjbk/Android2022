package com.example.blockchain.USER;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.example.blockchain.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CheckPatient extends Fragment {

    ListView patientlist;
    List<DataModel> arrayList;
    PatientViewAdaptor adpater;
    String familyval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_checkpatient, container, false);
        patientlist = view.findViewById(R.id.mypatienntlist);
        arrayList=new ArrayList<DataModel>();
        adpater=new PatientViewAdaptor(getActivity(), arrayList);
        Bundle bundle = this.getArguments();
        patientlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle=new Bundle();
                DataModel patientdetai = arrayList.get(position);
                bundle.putParcelable("clicked_item", (Parcelable) patientdetai);
                MedicaList plantDetails=new MedicaList();
                plantDetails.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,plantDetails).commit();
            }
        });
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
//                 Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson=new Gson();
                    arrayList = Arrays.asList(gson.fromJson(response, DataModel[].class));
                    PatientViewAdaptor adapter = new PatientViewAdaptor(getActivity(),arrayList);
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

                Map<String, String> map = new HashMap<String, String>();
                map.put("key","GetPatientList");
                map.put("userid",uid);
                return map;
            }
        };
        queue.add(request);
    }

}
