package com.futurefirst.sbjr.myskillsare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //getActionBar().setTitle("Register");
    }

    public void createprofile(View v){
        Intent intent = new Intent(getApplicationContext(),CreateProfile.class);
        startActivity(intent);
    }

}
