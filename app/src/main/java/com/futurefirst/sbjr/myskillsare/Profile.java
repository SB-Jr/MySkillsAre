package com.futurefirst.sbjr.myskillsare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {


    public static final int FRAGMENTTYPEPROFILE = 123456;
    public static final String EMAILFROMPROFILE="EmailFromProfile";
    public static int PROFILEEDITKEY=3456;


    boolean doubleBackToExitPressedOnce = false;


    CreateSkillFragment fragment[];
    ViewPager vp;
    DrawerLayout profilenavigation;
    ActionBarDrawerToggle navigationToggle;

    public static String Emailid;
    public String userEmail;

    private String navList[] = {"Edit Profile","Technology","Arts","Communication","Science","Sign Out"};
    private ArrayList<String> navArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilview);

        setTitle("Profile");


        Emailid = getIntent().getStringExtra(Register.EMAILIDKEY);


        fragment = new CreateSkillFragment[4];
        for(int i=0;i<4;i++) {
                fragment[i] = CreateSkillFragment.createInstance(CreateProfile.CATEGORYLIST[i],FRAGMENTTYPEPROFILE);
        }


        vp = (ViewPager) findViewById(R.id.profileviewpager);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(new ProfilePagerAdapter(getSupportFragmentManager()));


        profilenavigation = (DrawerLayout) findViewById(R.id.profiledrawer);
        navigationToggle = new ActionBarDrawerToggle(this,profilenavigation,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        profilenavigation.setDrawerListener(navigationToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationToggle.syncState();


        ListView navigationListView = (ListView) findViewById(R.id.profileview_navigation_list);
        navArrayList = new ArrayList<>();
        for(int i=0;i<=5;i++)
            navArrayList.add(navList[i]);
        ArrayAdapter<String> navListAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.navigationlist_element,navArrayList);
        navigationListView.setAdapter(navListAdapter);
        navigationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:Intent editProfileIndent = new Intent(getApplicationContext(),EditProfile.class);
                        startActivityForResult(editProfileIndent, PROFILEEDITKEY);
                        break;
                    case 1:vp.setCurrentItem(0);
                        break;
                    case 2:vp.setCurrentItem(1);
                        break;
                    case 3:vp.setCurrentItem(2);
                        break;
                    case 4:vp.setCurrentItem(3);
                        break;
                    case 5:SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.firstnamedefault),null);
                        editor.putString(getString(R.string.lastnamedefault),null);
                        editor.putString(getString(R.string.emailiddefault), null);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(),Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
                profilenavigation.closeDrawers();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        String s[] = dbhelper.getUserDetail(Emailid);
        TextView tv = (TextView) findViewById(R.id.nameprofile);
        tv.setText(s[0] + " " + s[1]);
        TextView tv1 = (TextView) findViewById(R.id.locationprofile);
        tv1.setText(s[2]);

        ImageView picview = (ImageView) findViewById(R.id.profilepic_profileview);
        ImageView navpic = (ImageView) findViewById(R.id.profileview_navigation_profilepic);
        Bitmap image = dbhelper.getUserProfilePic(Emailid);
        if(image!=null) {
            //Log.d("IMAGE THING", "image not empty and the size being " + image.getWidth()+"x"+image.getHeight());
            picview.setImageBitmap(image);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userEmail = sharedPreferences.getString(getString(R.string.emailiddefault), "you Are not Logged In");
        TextView tv2 = (TextView) findViewById(R.id.profileview_navigation_name);
        tv2.setTextColor(Color.WHITE);
        String userinfo[] = dbhelper.getUserDetail(userEmail);
        tv2.setText(userinfo[0]+" "+userinfo[1]);
        TextView tv3 = (TextView) findViewById(R.id.profileview_navigation_emailid);
        tv3.setTextColor(Color.WHITE);
        tv3.setText(userEmail);
        Bitmap userimage = dbhelper.getUserProfilePic(userEmail);
        if(userimage!=null) {
            //Log.d("IMAGE THING", "image not empty and the size being " + image.getWidth()+"x"+image.getHeight());
            navpic.setImageBitmap(Bitmap.createScaledBitmap(userimage, 140, 140, false));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.loginmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.search) {
            Intent intent = new Intent(getApplicationContext(),Search.class);
            startActivity(intent);
            return true;
        }
        if(navigationToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
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


    @Override
    public void onBackPressed() {

        if (userEmail.equals(Emailid)) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
        else
            super.onBackPressed();
    }

}
