package com.example.blockchain.USER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.example.blockchain.COMMON.MedicalViewAdaptor;
import com.example.blockchain.COMMON.Utility;
import com.example.blockchain.Login;
import com.example.blockchain.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class HistoryPatient extends Fragment {
    EditText REQ;
    TextView txt,afterbuttn;
    Button requBTN;
    String DataRequest="";
ImageView failedimage,LOGOYT;
    ToggleButton Viewbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        txt = root.findViewById(R.id.idvalue);
        REQ = root.findViewById(R.id.myrequest);
        requBTN = root.findViewById(R.id.Dorequestbtn);
        failedimage=root.findViewById(R.id.failedimage);
        failedimage.setVisibility(View.INVISIBLE);
        LOGOYT=root.findViewById(R.id.logoutpatient);
        Viewbtn=root.findViewById(R.id.ViewButton);
        afterbuttn=root.findViewById(R.id.afterbuttn);

        Viewbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Viewbtn.isChecked()) {
                    afterbuttn.setVisibility(View.VISIBLE);
                }
                else {

                    afterbuttn.setVisibility(View.GONE);
                    afterbuttn.setVisibility(View.GONE);
                }
            }
        });
        LOGOYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Login.class));
            }
        });
        requBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRequest = REQ.getText().toString().trim();
                if (DataRequest.isEmpty()) {
                    Snackbar.make(REQ, "Request is Empty", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    BlockChainTransaction();

                }
            }
        });
        return root;
    }

    private void BlockChainTransaction() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
                    txt.setText("Block Chain  Verify :TRUE- Success");
                    Viewbtn.setVisibility(View.VISIBLE);
                    afterbuttn.setText(response);
                    txt.setTextColor(getActivity().getColor(R.color.green));
                    txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
                    failedimage.setVisibility(View.INVISIBLE);
                }else{
                    txt.setText("Black Chain Verify : False, Transaction Failed");
                    txt.setTextColor(getActivity().getColor(R.color.red));
                    Viewbtn.setVisibility(View.GONE);

                    txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
                    failedimage.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Failed Transaction", Toast.LENGTH_LONG).show();
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
                String uid = prefs.getString("u_id", "No");
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", "Blockchain");
                map.put("userid", uid);
                map.put("transacrequest", DataRequest);
                return map;
            }
        };
        queue.add(request);

    }
}