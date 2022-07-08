package com.example.blockchain.COMMON;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blockchain.R;

import java.net.URI;

public class UploadPDF extends AppCompatActivity {
    EditText upload;
    Button btnm;
    public static final int PERMISSION_STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_p_d_f);
        upload = findViewById(R.id.fileload);
        btnm = findViewById(R.id.filebtn);
        btnm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String permission[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_STORAGE_CODE);
                    } else {
                        startdownloding();
                    }
                } else {
                    startdownloding();
                }
            }
        });
    }
    private void startdownloding() {
    String url=upload.getText().toString();
        DownloadManager.Request reust=new DownloadManager.Request(Uri.parse(url));
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