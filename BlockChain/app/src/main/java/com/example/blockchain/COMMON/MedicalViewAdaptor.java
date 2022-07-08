package com.example.blockchain.COMMON;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blockchain.R;

import java.io.IOException;
import java.util.List;

public class MedicalViewAdaptor extends ArrayAdapter<DataModel> {

        TextView image;
        TextView group,count,cholestr,patientname,link;
        String NAME,IMAGE,BLO,COUNT,LINK,CHOLERSTROL,BLOOODCOUNT;
        String SHOP,SEMAIL,SWEB,SS;
        List<DataModel> prodList;
        Activity context;

public MedicalViewAdaptor(Activity context, List<DataModel> prodList) {
        super( context, R.layout.activity_medical_view_adaptor,prodList);
        this.prodList = prodList;
        this.context = context;
        }
@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_medical_view_adaptor, null, true);
        image = view.findViewById(R.id.imgDatap);
        group = view.findViewById(R.id.groupp);
        count = view.findViewById(R.id.countp);
        link = view.findViewById(R.id.linkp);
        cholestr=view.findViewById(R.id.cholsetrolp);
        patientname = view.findViewById(R.id.patientnamep);

        //
        NAME = prodList.get(position).getPatientname();
        IMAGE = prodList.get(position).getImage();
        BLOOODCOUNT=prodList.get(position).getCount();
        CHOLERSTROL = prodList.get(position).getCholes();
        BLO=prodList.get(position).getBlood();
        LINK=prodList.get(position).getLink();


        patientname.setText("Name :"+NAME);
        group.setText("Blood Count:"+BLOOODCOUNT);
        link.setText("File Link :"+LINK);
        cholestr.setText("Cholestrol :"+CHOLERSTROL);
count.setText("Blood Group :"+BLO);
        image.setText(IMAGE);

//        try {
//        byte[] imageAsBytes = Base64.decode(IMAGE.getBytes());
//        image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
////             const_img.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)));
//        } catch (IOException e) {
//
//        e.printStackTrace();
//        }
        return view;
        }

        }
