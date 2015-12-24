package com.futurefirst.sbjr.myskillsare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createprofile);
    }

    public void profile(View v){
        Intent intent = new Intent(getApplicationContext(),Profile.class);
        startActivity(intent);
    }
}
