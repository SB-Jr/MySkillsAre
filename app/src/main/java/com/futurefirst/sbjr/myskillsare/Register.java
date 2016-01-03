package com.futurefirst.sbjr.myskillsare;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    public static final String FIRSTNAMEKEY="FIRSTNAMEKEY";
    public static final String LASTNAMEKEY="LASTNAMEKEY";
    public static final String EMAILIDKEY="EMAILIDKEY";
    public static final String LOCATIONKEY="LOCATIONKEY";


    EditText firstname=null;
    EditText lastname=null;
    EditText emailid=null;
    EditText location=null;

    TextView warning = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        setTitle("Register");

        firstname = (EditText) findViewById(R.id.firstnameregister);
        lastname = (EditText) findViewById(R.id.lastnameregister);
        emailid = (EditText) findViewById(R.id.emailidregister);
        location = (EditText) findViewById(R.id.locationregister);

        warning = (TextView) findViewById(R.id.warningregister);

        //getActionBar().setTitle("Register");
    }

    public void createprofile(View v){

        if(firstname.getText().length()==0){
            warning.setText("Enter a valid First Name");
        }
        else if(lastname.getText().length()==0){
            warning.setText("Enter a valid Last Name");
        }
        else if(emailid.getText().length()==0){
            warning.setText("Enter a valid Email ID");
        }else if(location.getText().length()==0){
            warning.setText("Enter a valid Location");
        }
        else {

            String email = emailid.getText().toString();

            String queryemail="SELECT * FROM "+ DatabaseContract.MemberTable.TABLENAME+
                    " WHERE "+ DatabaseContract.MemberTable.EMAILID+" = \'"+email+"\';";

            DatabaseHelper dbhelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();

            Cursor qei = db.rawQuery(queryemail,null);

            if(qei.moveToFirst()){
                warning.setText("Email Id already registered");
                db.close();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), CreateProfile.class);
                intent.putExtra(FIRSTNAMEKEY, firstname.getText().toString());
                intent.putExtra(LASTNAMEKEY, lastname.getText().toString());
                intent.putExtra(EMAILIDKEY, emailid.getText().toString());
                intent.putExtra(LOCATIONKEY, location.getText().toString());
                db.close();
                startActivity(intent);
            }
        }
    }

}
