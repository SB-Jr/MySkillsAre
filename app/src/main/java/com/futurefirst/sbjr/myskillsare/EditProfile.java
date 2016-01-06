package com.futurefirst.sbjr.myskillsare;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        String s[] = dbhelper.getUserDetail(Profile.Emailid);


        TextView tv1 = (TextView) findViewById(R.id.firstnamesettings);
        TextView tv2 = (TextView) findViewById(R.id.lastnamesettings);
        TextView tv3 = (TextView) findViewById(R.id.locationsettings);
        ImageButton ib = (ImageButton) findViewById(R.id.profileimagesettings);

        tv1.setText(s[0]);
        tv2.setText(s[1]);
        tv3.setText(s[2]);

        Bitmap image = dbhelper.getUserProfilePic(Profile.Emailid);
        if(image!=null) {
            //Log.d("IMAGE THING", "image not empty and the size being " + image.getWidth()+"x"+image.getHeight());
            ib.setImageBitmap(image);

        }

    }
}
