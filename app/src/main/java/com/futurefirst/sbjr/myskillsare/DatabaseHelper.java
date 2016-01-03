package com.futurefirst.sbjr.myskillsare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by sbjr on 12/25/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME="myskillsare.db";
    private static final int ver = 1;

    public static int row_count=0;

    public DatabaseHelper(Context context){
        super(context,DBNAME,null,ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATEMEMBERTABLE="CREATE TABLE IF NOT EXISTS "+ DatabaseContract.MemberTable.TABLENAME+
                "("+ DatabaseContract.MemberTable.EMAILID+" varchar(30) PRIMARY KEY, "+
                DatabaseContract.MemberTable.FIRSTNAME+" varchar(30) NOT NULL, "+
                DatabaseContract.MemberTable.LASTNAME+" varchar(30) NOT NULL, "+
                DatabaseContract.MemberTable.LOCATION+" varchar(30) NOT NULL);";

        final String CREATESKILLTABLE="CREATE TABLE IF NOT EXISTS "+ DatabaseContract.SkillTable.TABLENAME+
                "("+ DatabaseContract.SkillTable.EMAILID+" varchar(30) NOT NULL,"+
                DatabaseContract.SkillTable.SKILLNAME+" varchar(30) NOT NULL, "+
                DatabaseContract.SkillTable.CATEGORY+" varchar(30) NOT NULL, "+
                DatabaseContract.SkillTable.DESCRIPTION+" varchar(200) NOT NULL, "+
                DatabaseContract.SkillTable.STARTYEAR+" char(10) NOT NULL, "+
                "PRIMARY KEY( "+ DatabaseContract.SkillTable.EMAILID+" , "+ DatabaseContract.SkillTable.SKILLNAME+" ));";

        final String CREATECATEGORYTABLE="CREATE TABLE IF NOT EXISTS "+ DatabaseContract.CategoryTable.TABLENAME+
                "("+ DatabaseContract.CategoryTable.CATEGORY+" varchar(30) primary key, "+
                DatabaseContract.CategoryTable.SKILLNAME+" varchar(30));";

        final String CREATETEMPTABLE="CREATE TABLE IF NOT EXISTS "+ DatabaseContract.TempTable.TABLENAME+
                "("+DatabaseContract.TempTable.Index+" varchar(5) primary key, "+
                DatabaseContract.TempTable.SKILLNAME+" varchar(30) NOT NULL, "+
                DatabaseContract.TempTable.CATEGORY+" varchar(30) NOT NULL, "+
                DatabaseContract.TempTable.DESCRIPTION+" varchar(200) NOT NULL, "+
                DatabaseContract.TempTable.STARTYEAR+" char(10) NOT NULL);";


        db.execSQL(CREATEMEMBERTABLE);
        db.execSQL(CREATESKILLTABLE);
        //db.execSQL(CREATECATEGORYTABLE);
        db.execSQL(CREATETEMPTABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void dbInsertSkill(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DatabaseContract.TempTable.TABLENAME, new String[]{DatabaseContract.TempTable.SKILLNAME, DatabaseContract.TempTable.CATEGORY, DatabaseContract.TempTable.DESCRIPTION, DatabaseContract.TempTable.STARTYEAR}, null, null, null, null, null);
        ContentValues cv = new ContentValues();
        if(cursor.moveToFirst()){
            do {
                //Log.d("CHECKINGTAG","cursor has data "+cursor.getColumnName(0)+" "+cursor.getColumnName(1));
                String skillname = cursor.getString(0);
                String category = cursor.getString(1);
                String description = cursor.getString(2);
                String startyear = cursor.getString(3);
                cv.put(DatabaseContract.SkillTable.SKILLNAME, skillname);
                cv.put(DatabaseContract.SkillTable.CATEGORY, category);
                cv.put(DatabaseContract.SkillTable.DESCRIPTION, description);
                cv.put(DatabaseContract.SkillTable.STARTYEAR, startyear);
                cv.put(DatabaseContract.SkillTable.EMAILID, email);
                db.insert(DatabaseContract.SkillTable.TABLENAME, null, cv);
            } while (cursor.moveToNext());
        }
        //Log.d("CHECKINGTAG","cursor done with data");
        db.delete(DatabaseContract.TempTable.TABLENAME,null,null);
        String delquerry = "DROP TABLE "+ DatabaseContract.TempTable.TABLENAME+";";
        String delrowqery = "DELETE FROM "+ DatabaseContract.TempTable.TABLENAME+";";
        db.rawQuery(delrowqery,null);
        db.rawQuery(delquerry,null);
        db.close();
    }


    public void dbInsertMember(String fname,String lname,String email,String loc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.MemberTable.EMAILID,email);
        cv.put(DatabaseContract.MemberTable.FIRSTNAME,fname);
        cv.put(DatabaseContract.MemberTable.LASTNAME,lname);
        cv.put(DatabaseContract.MemberTable.LOCATION, loc);
        db.insert(DatabaseContract.MemberTable.TABLENAME, null, cv);
        db.close();
    }

    public void dbInsertTemp(String skillname,String category,String date,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.TempTable.Index,row_count+1);
        row_count++;
        cv.put(DatabaseContract.TempTable.SKILLNAME,skillname);
        cv.put(DatabaseContract.TempTable.CATEGORY,category);
        cv.put(DatabaseContract.TempTable.DESCRIPTION,description);
        cv.put(DatabaseContract.TempTable.STARTYEAR,date);
        db.insert(DatabaseContract.TempTable.TABLENAME, null, cv);
        db.close();
    }

    public String[] getUserDetail(String emailid){
        String s[] = new String[3];

        SQLiteDatabase db = this.getReadableDatabase();
        String querry = "SELECT * FROM "+ DatabaseContract.MemberTable.TABLENAME+
                " WHERE " +DatabaseContract.MemberTable.EMAILID+ " = \'"+emailid+"\';";
        Cursor cursor = db.rawQuery(querry,null);
        String fname=null;
        String lname = null;
        String location = null;
        if(cursor.moveToFirst()) {
            fname = cursor.getString(1);
            lname = cursor.getString(2);
            location = cursor.getString(3);
        }
        s[0] = fname;
        s[1] = lname;
        s[2] = location;
        db.close();

        return  s;
    }


}
