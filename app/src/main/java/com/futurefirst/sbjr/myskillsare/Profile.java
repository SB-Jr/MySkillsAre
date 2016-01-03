package com.futurefirst.sbjr.myskillsare;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {


    public static final int FRAGMENTTYPEPROFILE = 123456;

    CreateSkillFragment fragment[];
    ViewPager vp;

    public static String Emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilview);

        setTitle("Profile");

        Emailid = getIntent().getStringExtra(Register.EMAILIDKEY);

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        String s[] = dbhelper.getUserDetail(Emailid);


        fragment = new CreateSkillFragment[4];
        for(int i=0;i<4;i++) {
                fragment[i] = CreateSkillFragment.createInstance(CreateProfile.CATEGORYLIST[i],FRAGMENTTYPEPROFILE);
        }


        vp = (ViewPager) findViewById(R.id.profileviewpager);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(new ProfilePagerAdapter(getSupportFragmentManager()));


        TextView tv = (TextView) findViewById(R.id.nameprofile);
        tv.setText(s[0]+" "+s[1]);
        TextView tv1 = (TextView) findViewById(R.id.locationprofile);
        tv1.setText(s[2]);

    }


    public class ProfilePagerAdapter extends FragmentPagerAdapter{

        static final int COUNT=4;


        ProfilePagerAdapter(FragmentManager fm){
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
            return CreateProfile.CATEGORYLIST[position];
        }

        @Override
        public int getCount() {
            return COUNT;
        }
    }

}
