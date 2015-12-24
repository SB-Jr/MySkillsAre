package com.futurefirst.sbjr.myskillsare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Login extends AppCompatActivity {

    private String firstname = null;
    private String lastname = null;
    private String emailid = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        firstname = sharedPreferences.getString(getString(R.string.firstnamedefault),null);
        lastname = sharedPreferences.getString(getString(R.string.lastnamedefault),null);
        emailid = sharedPreferences.getString(getString(R.string.emailiddefault),null);
        if(!(firstname==null||lastname==null||emailid==null)){
            Intent intent = new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
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
        if(item.getItemId()==R.id.search)
            search();
        return super.onOptionsItemSelected(item);
    }

    public void register(View v){
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }

    public void search(){
        Intent intent = new Intent(getApplicationContext(),Search.class);
        startActivity(intent);
    }
}
