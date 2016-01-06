package com.futurefirst.sbjr.myskillsare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.InputStream;

public class CreateProfile extends AppCompatActivity {

    public static final int FRAGMENTTYPECREATEPROFILE = 654321;
    public static final String[] CATEGORYLIST={"Technology","Art","Communication","Science"};
    private final int PICTUREREQUEST=300;


    CreateSkillFragment fragment[];
    ViewPager vp;
    ImageButton profilePic;
    Bitmap profilepicbitmap = null;
    boolean setpic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createprofile);

        profilePic = (ImageButton) findViewById(R.id.profilepic_createprofile);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PICTUREREQUEST);
            }
        });

        fragment = new CreateSkillFragment[4];
        for(int i=0;i<4;i++)
            fragment[i] = CreateSkillFragment.createInstance(CATEGORYLIST[i],FRAGMENTTYPECREATEPROFILE);

        vp = (ViewPager) findViewById(R.id.viewpagercreateprofile);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(new ViewPageAdapter(getSupportFragmentManager()));
    }


    public void profile(View v){
        Intent backintent = getIntent();
        String firstname = backintent.getStringExtra(Register.FIRSTNAMEKEY);
        String lastname = backintent.getStringExtra(Register.LASTNAMEKEY);
        String emailid = backintent.getStringExtra(Register.EMAILIDKEY);
        String location = backintent.getStringExtra(Register.LOCATIONKEY);

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        if(setpic==false)
            dbhelper.dbInsertMember(firstname,lastname,emailid,location,null);
        else
            dbhelper.dbInsertMember(firstname, lastname,emailid,location,profilepicbitmap);
        dbhelper.dbInsertSkill(emailid);

        Intent intent = new Intent(getApplicationContext(),Profile.class);
        intent.putExtra(Register.EMAILIDKEY,emailid);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



    public class ViewPageAdapter extends FragmentPagerAdapter {

        static final int COUNT=4;

        public ViewPageAdapter(FragmentManager fm){
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            for(int i=0;i<4;i++) {
                if(position==i)
                    return fragment[i];
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CATEGORYLIST[position];
        }

        @Override
        public int getCount() {
            return COUNT;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICTUREREQUEST){
            if(resultCode==RESULT_OK){
                Uri uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    image = Bitmap.createScaledBitmap(image,200,200,false);
                    profilePic.setImageBitmap(image);
                    profilepicbitmap = image;
                    setpic = true;
                }
                catch (Exception e){ }
            }
        }
    }
}
