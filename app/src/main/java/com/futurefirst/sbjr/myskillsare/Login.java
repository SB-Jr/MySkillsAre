package com.futurefirst.sbjr.myskillsare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private String firstname = null;
    private String lastname = null;
    private String emailid = null;


    EditText firstnameet = null;
    EditText lastnameet = null;
    EditText emailidet = null;

    TextView warning=null;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        setTitle("Login");

        firstnameet = (EditText) findViewById(R.id.firstnamelogin);
        lastnameet = (EditText) findViewById(R.id.lastnamelogin);
        emailidet = (EditText) findViewById(R.id.emailidlogin);

        warning = (TextView) findViewById(R.id.warninglogin);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        firstname = sharedPreferences.getString(getString(R.string.firstnamedefault),null);
        lastname = sharedPreferences.getString(getString(R.string.lastnamedefault),null);
        emailid = sharedPreferences.getString(getString(R.string.emailiddefault),null);
        if(!(firstname==null||lastname==null||emailid==null)){
            Intent intent = new Intent(getApplicationContext(),Profile.class);
            intent.putExtra(Register.EMAILIDKEY,emailid);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        if(item.getItemId()==R.id.search) {
            search();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void register(View v){
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }

    public void search(){
        Intent intent = new Intent(getApplicationContext(),Search.class);
        startActivity(intent);
    }

    public void login(View v){

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        if(firstnameet.getText().length()==0){
            warning.setText("Enter a proper First Name");
        }
        else if(lastnameet.getText().length()==0){
            warning.setText("Enter a proper Last Name");
        }
        else if(emailidet.getText().length()==0){
            warning.setText("Enter a proper Email ID");
        }
        else{
            Intent intent = new Intent(getApplicationContext(),Profile.class);


            String firstname = firstnameet.getText().toString();
            String lastname = lastnameet.getText().toString();
            String emailid = emailidet.getText().toString();

            String query="SELECT * FROM "+ DatabaseContract.MemberTable.TABLENAME+//";";
                    " WHERE "+ DatabaseContract.MemberTable.FIRSTNAME+" = \'"+firstname+
                    "\' AND "+DatabaseContract.MemberTable.LASTNAME+" = \'"+lastname+
                    "\' AND "+ DatabaseContract.MemberTable.EMAILID+" = \'"+emailid+"\';";

            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()) {
                warning.setText("Logging You In");
                intent.putExtra(Register.EMAILIDKEY,emailid);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.firstnamedefault), firstname);
                editor.putString(getString(R.string.lastnamedefault), lastname);
                editor.putString(getString(R.string.emailiddefault), emailid);
                editor.commit();
                db.close();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else{
                warning.setText("Wrong Credentials");
                db.close();
            }
        }
    }
}
