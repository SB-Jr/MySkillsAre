package com.futurefirst.sbjr.myskillsare;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SkillView extends AppCompatActivity {

    private String category;
    private String skill;
    private String description;
    private String yearOfStart;

    private final int REQUESTFOREDIT=200;


    public static final String SKILLNAMEFOREDIT="SkillNameForEdit";
    public static final String YEAROFPRACTICEFOREDIT="YearOfPracticeForEdit";
    public static final String DESCRIPTIONFOREDIT="DescriptionForEdit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skillsview);

        Intent intent = getIntent();
        category = intent.getStringExtra("Category");
        skill = intent.getStringExtra("Skill");

        setTitle(skill);

        TextView tv1 = (TextView) findViewById(R.id.skillname);
        TextView tv2 = (TextView) findViewById(R.id.yearofpractice);
        TextView tv3 = (TextView) findViewById(R.id.description);

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String querry  = "SELECT * FROM "+ DatabaseContract.SkillTable.TABLENAME+
                " WHERE "+ DatabaseContract.SkillTable.EMAILID+" = \'"+Profile.Emailid+"\'"+
                " AND "+ DatabaseContract.SkillTable.CATEGORY+" = \'"+category+"\'"+
                " AND "+ DatabaseContract.SkillTable.SKILLNAME+" = \'"+skill+"\';";

        Cursor cursor = db.rawQuery(querry,null);
        if(cursor.moveToFirst()){
            tv1.setText(cursor.getString(1));
            tv2.setText(cursor.getString(4));
            tv3.setText(cursor.getString(3));
        }
        db.close();
    }


    public void deleteButton(){
        Log.d(getClass().getSimpleName(),"delete method entered");
        Log.d(getClass().getSimpleName(),"Email id passed: "+Profile.Emailid);
        String querry="DELETE FROM "+ DatabaseContract.SkillTable.TABLENAME+
                " WHERE "+ DatabaseContract.SkillTable.EMAILID+" = \'"+Profile.Emailid+"\'"+
                " AND "+ DatabaseContract.SkillTable.CATEGORY+" = \'"+category+"\'"+
                " AND "+ DatabaseContract.SkillTable.SKILLNAME+" = \'"+skill+"\';";
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.rawQuery(querry,null);
        db.close();
        finish();
    }

    public void editButton(){
        Intent intent = new Intent(getApplicationContext(),EditSkill.class);
        intent.putExtra(SKILLNAMEFOREDIT,skill);
        intent.putExtra(DESCRIPTIONFOREDIT,description);
        intent.putExtra(YEAROFPRACTICEFOREDIT,yearOfStart);

        startActivityForResult(intent,REQUESTFOREDIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.skillmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.deleteskilloption)
            deleteButton();

        else if(item.getItemId()==R.id.editskilloption)
            editButton();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUESTFOREDIT){
            if(resultCode==RESULT_OK){
                String newSkill = data.getStringExtra(CreateSkillFragment.SKILLNAMEEDIT);
                String newDescription = data.getStringExtra(CreateSkillFragment.DESCRIPTIONEDIT);
                String newYearofstart = data.getStringExtra(CreateSkillFragment.STARTDAYEDIT)+"/"+data.getStringExtra(CreateSkillFragment.STARTMONTHEDIT)+"/"+data.getStringExtra(CreateSkillFragment.STARTYEAREDIT);

                String delquery = "DELETE FROM "+ DatabaseContract.SkillTable.TABLENAME+
                        " WHERE "+ DatabaseContract.SkillTable.EMAILID+" = \'"+Profile.Emailid+"\'"+
                        " AND "+ DatabaseContract.SkillTable.CATEGORY+" = \'"+category+"\'"+
                        " AND "+ DatabaseContract.SkillTable.SKILLNAME+" = \'"+skill+"\';";
                DatabaseHelper dbhelper = new DatabaseHelper(this);
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                db.rawQuery(delquery,null);
                db.close();
                dbhelper.dbInsertTemp(newSkill, category, newYearofstart, newDescription);
                dbhelper.dbInsertSkill(Profile.Emailid);
            }
        }
    }
}
