package com.example.blockchain.USER;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blockchain.CODE_ALGORITHM.AES;
import com.example.blockchain.COMMON.Base64;
import com.example.blockchain.COMMON.DataModel;
import com.example.blockchain.COMMON.Utility;
import com.example.blockchain.Login;
import com.example.blockchain.R;
import com.example.blockchain.UsaerRegister;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class AddPatient extends Fragment {
    // String patientname[]=new String[100];
    ArrayList<String> patientname = new ArrayList<String>();

    Button BBtn;
    ImageView imgV;
    Uri imageUri;
    private Bitmap bitmap;
    CardView firstcard, scondcard;
    ToggleButton fB, sB;
    EditText pid, name, age, number, addres, email;
    String PID, PNAME, PAGE, PNUMBER, PADDRESS, PEMAIL, UKEY, usernamemedical;
    String PIDq, PNAMEq, PAGEq, PNUMBERq, PADDRESSq, PEMAILq;
    Spinner down;
String da="",randomkey18;

    //addmedical field
    ImageView imgC, imgG, allow;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    EditText Pname,Blood,count,cholestrol,link,eimage;
    String BATIENTNAMEq,BLOOD,COUNT,CHOLESTROL,LINK,bal = "";
    Button aaddmedicalbtn;

    String BLOODq,BNAMEq,COUNTq,CHOLESTROLq,LINKq;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addpatient, container, false);

        firstcard = root.findViewById(R.id.CARDFIRST);
        gettingusername();

        // ActionBar bar=getContext().getApplicationContext();
        // bar.hide();
        down = (Spinner) root.findViewById(R.id.downid);
        patientname.add("-PatientName-");
        scondcard = root.findViewById(R.id.SECCARD);
        fB = root.findViewById(R.id.FTOG);
        sB = root.findViewById(R.id.STOG);
        firstcard.setVisibility(View.INVISIBLE);
        scondcard.setVisibility(View.INVISIBLE);

//   ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, patientname);
//        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        down.setAdapter(adp);
//        down.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//             //   Toast.makeText(getActivity(), "" + proof[position], Toast.LENGTH_SHORT).show();
//                da = patientname.get(position).toString();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        fB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (fB.isChecked()) {
                    firstcard.setVisibility(View.VISIBLE);
                    scondcard.setVisibility(View.GONE);
                }
                else {

                    firstcard.setVisibility(View.GONE);
                    scondcard.setVisibility(View.GONE);
                }
            }
        });



        sB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sB.isChecked()) {
                    firstcard.setVisibility(View.GONE);
                    scondcard.setVisibility(View.VISIBLE);


                } else {
                    firstcard.setVisibility(View.GONE);
                    scondcard.setVisibility(View.GONE);

                }
            }
        });


        imgV = root.findViewById(R.id.imgview);
        imgC = root.findViewById(R.id.imgcamid);
        imgG = root.findViewById(R.id.imggid);
        pid = root.findViewById(R.id.pid);


        name = root.findViewById(R.id.pname);
        age = root.findViewById(R.id.page);
        number = root.findViewById(R.id.pphone);
        addres = root.findViewById(R.id.paddress);
        email = root.findViewById(R.id.pemail);
   allow=root.findViewById(R.id.imageView);

        BBtn = root.findViewById(R.id.addpatientbtn);

        BBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PID = pid.getText().toString().trim();
                PNAME = name.getText().toString().trim();
                PAGE = age.getText().toString().trim();
                PNUMBER = number.getText().toString().trim();
                PADDRESS = addres.getText().toString().trim();
                PEMAIL = email.getText().toString().trim();

//Encryption Started


                PIDq = AES.encrypt(PID, "secretKey");
                PNAMEq = AES.encrypt(PNAME, "secretKey");
                PAGEq = AES.encrypt(PAGE, "secretKey");
                PNUMBERq = AES.encrypt(PNUMBER, "secretKey");
                PEMAILq = AES.encrypt(PEMAIL, "secretKey");
                PADDRESSq = AES.encrypt(PADDRESS, "secretKey");
                //   Toast.makeText(getContext(), ""+PIDq+"---------"+PNAMEq+"============="+PAGEq+"numm"+PNUMBERq+"....."+PEMAILq+"aaaaaaa"+PADDRESSq, Toast.LENGTH_SHORT).show();

                if (PID.isEmpty()) {
                    Snackbar.make(pid, "Enter Patient ID", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (!PNAME.matches("[ .a-zA-Z]+")) {
                    Snackbar.make(name, "valid name", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (!PEMAIL.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

                    Snackbar.make(email, "Invalid Email Id ", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (PNUMBER.isEmpty()) {
                    Snackbar.make(number, "Phone Number", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (PNUMBER.length() != 10) {
                    Snackbar.make(number, "Valid Phone Number", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();


                } else if (PADDRESS.isEmpty()) {
                    Snackbar.make(addres, "Invalid Address", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (PAGE.isEmpty()) {
                    Snackbar.make(age, "Invalid Age ", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setCancelable(false);
                    dialog.setTitle("UserKey");
//           Random r = new Random();
//           int myRandom = r.nextInt(851 - 781) + 281;
//           String substr=PNAME.substring(0,3);
//          String  UP=substr.toUpperCase();
//           String rabdomno = String.valueOf(myRandom);
//          String IDCODE=UP+rabdomno;
                    byte[] nonce = new byte[12];
                    new SecureRandom().nextBytes(nonce);
                    System.out.println(convertBytesToHex(nonce));
                    randomkey18=convertBytesToHex(nonce).toString();
                // util to print bytes in hex

                    //    Log.v("random", rabdomno);
                 //   String getname = PNAMEq.substring(0, 7);
                  //  String getpid = PIDq.substring(0, 3);

                   // UKEY = getname + PID + getpid;
                    dialog.setIcon(R.drawable.ic_baseline_key_24);
                    dialog.setMessage("Your Key  : " + convertBytesToHex(nonce));
                    dialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //startActivity(new Intent(getApplicationContext(), Shopregistration.class));
                            AddPatients();
                        }
                    });
                    AlertDialog alert = dialog.create();
                    alert.show();
                }
            }
        });

        cholestrol=root.findViewById(R.id.Cholesterol);
        link=root.findViewById(R.id.medicallink);
        count=root.findViewById(R.id.hbcount);
        Pname=root.findViewById(R.id.pname);
        Blood=root.findViewById(R.id.bloodgroup);


        aaddmedicalbtn=root.findViewById(R.id.addmedicalbutton);
        //addmedical Field Action
  aaddmedicalbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
         // PATNAME = Pname.getText().toString().trim();
          BLOOD = Blood.getText().toString().trim();
          CHOLESTROL = cholestrol.getText().toString().trim();
          LINK = link.getText().toString().trim();
          COUNT = count.getText().toString().trim();
          //Encryption Started
         //BATIENTNAMEq = AES.encrypt(usernamemedical, "secretKey");
          BLOODq = AES.encrypt(BLOOD, "secretKey");
          CHOLESTROLq = AES.encrypt(CHOLESTROL, "secretKey");
          COUNTq = AES.encrypt(COUNT, "secretKey");
          LINKq = AES.encrypt(LINK, "secretKey");

           //   Toast.makeText(getContext(), ""+PIDq+"---------"+PNAMEq+"============="+PAGEq+"numm"+PNUMBERq+"....."+PEMAILq+"aaaaaaa"+PADDRESSq, Toast.LENGTH_SHORT).show();
          if (usernamemedical.isEmpty()) {
              Snackbar.make(Pname, "Select Patient name", Snackbar.LENGTH_LONG)
                      .setAction("DISMISS", new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                          }
                      })
                      .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                      .show();

          }
           if (usernamemedical.equals("-PatientName-")) {
              Snackbar.make(Pname, "Select Patient name", Snackbar.LENGTH_LONG)
                      .setAction("DISMISS", new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                          }
                      })
                      .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                      .show();

          } else if (BLOOD.isEmpty()) {
              Snackbar.make(Blood, "Add Blood Group", Snackbar.LENGTH_LONG)
                      .setAction("DISMISS", new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                          }
                      })
                      .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                      .show();

          } else if (COUNT.isEmpty()) {

              Snackbar.make(count, "Add Blood Count", Snackbar.LENGTH_LONG)
                      .setAction("DISMISS", new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                          }
                      })
                      .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                      .show();
          } else if (LINK.isEmpty()) {
              Snackbar.make(
                      link, "Add File link", Snackbar.LENGTH_LONG)
                      .setAction("DISMISS", new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                          }
                      })
                      .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                      .show();

          }

          if (bal.isEmpty()) {
              Snackbar.make(allow, "Select Image ", Snackbar.LENGTH_LONG)
                      .setAction("DISMISS", new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                          }
                      })
                      .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                      .show();

          }
          else{
              addmedicalDataField();
          }
      }
  });
        imgC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String fileName = "new-photo-name.jpg";
                //create parameters for Intent with filename
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by camera");
                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //imageUri =getContext(). getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //create new Intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, PICK_Camera_IMAGE);

            }
        });

        imgG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // TODO Auto-generated method stub
                try {
                    Intent gintent = new Intent();
                    gintent.setType("image/*");
                    gintent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gintent, "Select Picture"), PICK_IMAGE);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        });

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, patientname);
        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        down.setAdapter(adp);
        down.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Toast.makeText(getActivity(), "" + proof[position], Toast.LENGTH_SHORT).show();
                usernamemedical = patientname.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return root;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;
		 		    	/*Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        if (selectedImageUri != null) {
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }
    private static String convertBytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte temp : bytes) {
            result.append(String.format("%02x", temp));
        }
        return result.toString();
    }
    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);
        imgV.setImageBitmap(bitmap);
        //...
        // Base64.InputStream is;
        BitmapFactory.Options bfo;
        Bitmap bitmapOrg;
        ByteArrayOutputStream bao;
        bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 2;
        //bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);
        bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
          bal = Base64.encodeBytes(ba);
    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
    private void AddPatients() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(getContext(), "Added Success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences prefs = getContext().getSharedPreferences("profilepefer", MODE_PRIVATE);
                final String uid = prefs.getString("u_id", "No");
                map.put("key", "addPatient");
                map.put("uid", uid.trim());
                map.put("pid", PIDq.trim());
                map.put("name", PNAMEq.trim());
                map.put("phone", PNUMBERq.trim());
                map.put("address", PADDRESSq.trim());
                map.put("email", PEMAILq.trim());
                map.put("age", PAGEq.trim());
                map.put("userkey", randomkey18.trim());
                return map;
            }
        };
        queue.add(request);
    }

    //getting spinner data from usertable

    public void gettingusername() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(getContext(), "Okay getting", Toast.LENGTH_LONG).show();
//                    int h = 1;
//                    DataModel dm = new DataModel();
//                    while (h <= 4) {
//
//                        String val = dm.getName().toString();
//                        patientname.add(val);
//                        h++;
//                    }
                    String data = response;
                    String respArr[] = data.trim().split("#");
                    int h=0;
                     while (h<respArr.length){
                         patientname.add(respArr[h]);
                         h++;
                     }
                }else {
                    Toast.makeText(getContext(), "" + response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "my Error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences prefs = getContext().getSharedPreferences("profilepefer", MODE_PRIVATE);
                final String uid = prefs.getString("u_id", "No");
                map.put("key", "gettingusernameS");
                map.put("uid", uid.trim());
                return map;
            }
        };
        queue.add(request);
    }


    public void addmedicalDataField(){

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(getContext(), "Added Success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences prefs = getContext().getSharedPreferences("profilepefer", MODE_PRIVATE);
                final String uid = prefs.getString("u_id", "No");
                map.put("key", "addMedical");
                map.put("uid", uid.trim());
                map.put("BATIENTNAMEq", usernamemedical.trim());
                map.put("blood", BLOODq.trim());
                map.put("count", COUNTq.trim());
                map.put("cholestrol", CHOLESTROLq.trim());
                map.put("link", LINKq.trim());
                map.put("imagess", bal.trim());
                return map;
            }
        };
        queue.add(request);


    }

}