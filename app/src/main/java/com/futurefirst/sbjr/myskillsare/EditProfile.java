package com.futurefirst.sbjr.myskillsare;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.InputStream;

public class EditProfile extends AppCompatActivity {

    private String email,oldfname,oldlname,oldlocation;
    private Bitmap oldProfile;
    public int PICTUREEDITREQUEST=3945;

    TextView fnametv;
    TextView lnametv;
    TextView locationtv;
    ImageButton profileib;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = sharedPreferences.getString(getString(R.string.emailiddefault),null);
        String s[] = dbhelper.getUserDetail(email);


        fnametv = (TextView) findViewById(R.id.firstnamesettings);
        lnametv = (TextView) findViewById(R.id.lastnamesettings);
        locationtv = (TextView) findViewById(R.id.locationsettings);
        profileib = (ImageButton) findViewById(R.id.profileimagesettings);
        profileib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PICTUREEDITREQUEST);
            }
        });

        oldfname=s[0];
        oldlname=s[1];
        oldlocation=s[2];


        fnametv.setText(s[0]);
        lnametv.setText(s[1]);
        locationtv.setText(s[2]);

        Bitmap image = dbhelper.getUserProfilePic(Profile.Emailid);
        if(image!=null) {
            //Log.d("IMAGE THING", "image not empty and the size being " + image.getWidth()+"x"+image.getHeight());
            profileib.setImageBitmap(image);
        }

    }



    public void updateProfile(View v) {
        String newFname,newLname,newLocation;
        newFname = fnametv.getText().toString();
        newLname = lnametv.getText().toString();
        newLocation = locationtv.getText().toString();
        DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());
        dbhelper.updateuser(email,newFname,newLname,newLocation,oldProfile);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.firstnamedefault), newFname);
        editor.putString(getString(R.string.lastnamedefault), newLname);
        editor.putString(getString(R.string.emailiddefault), email);
        editor.commit();
        finish();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICTUREEDITREQUEST){
            if(resultCode==RESULT_OK){
                Uri uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    image = Bitmap.createScaledBitmap(image,200,200,false);
                    profileib.setImageBitmap(image);
                    oldProfile= image;
                }
                catch (Exception e){ }
            }
        }
    }
}
