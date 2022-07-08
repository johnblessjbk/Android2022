package com.example.blockchain.COMMON;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.blockchain.R;

import java.util.List;

public class PatientViewAdaptor extends ArrayAdapter<DataModel> implements Filterable {

    Activity context;
    List<DataModel> rest_List;
    String NAME;
    ImageView gg;
    TextView name;

    public PatientViewAdaptor(Activity context, List<DataModel> rest_List) {
        super(context, R.layout.activity_patient_view_adaptor, rest_List);
        this.context = context;
        this.rest_List = rest_List;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_patient_view_adaptor, null, true);
        name = view.findViewById(R.id.patientname);
        NAME=rest_List.get(position).getPatientname();
        name.setText(NAME);
        return view;
    }


}
