package com.futurefirst.sbjr.myskillsare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DateKeyListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class EditSkill extends AppCompatActivity {

    private EditText skillname=null;
    private EditText description=null;
    Calendar calendar;
    int startYear;
    int startMonth;
    int startDay;
    boolean edit = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createskill);

        setTitle("Set the Skill");


        skillname = (EditText) findViewById(R.id.skillnameedit);
        description = (EditText) findViewById(R.id.descriptionedit);

        calendar = Calendar.getInstance();
        startYear = calendar.get(Calendar.YEAR);
        startMonth = calendar.get(Calendar.MONTH)+1;
        startDay = calendar.get(Calendar.DAY_OF_MONTH);


        Intent intent = getIntent();
        if(intent!=null){
            try {
                skillname.setText(intent.getStringExtra(SkillView.SKILLNAMEFOREDIT));
                description.setText(intent.getStringExtra(SkillView.DESCRIPTIONFOREDIT));
                String date = intent.getStringExtra(SkillView.YEAROFPRACTICEFOREDIT);
                String datebroken[] = date.split("/");
                //startDay = Integer.parseInt(datebroken[0]);
                startMonth = Integer.parseInt(datebroken[0]);
                startYear = Integer.parseInt(datebroken[1]);
            }
            catch (Exception e){ }
        }

    }

    public void datePick(View v){
        StartYearDatePick startYearDatePick = new StartYearDatePick();
        createDialogWithoutDateField(getApplicationContext(),startYearDatePick).show();
    }

    public void doneSkillEdit(View v){
        String skill = skillname.getText().toString();
        String desc = description.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(CreateSkillFragment.SKILLNAMEEDIT,skill);
        intent.putExtra(CreateSkillFragment.DESCRIPTIONEDIT,desc);
        String sty = startYear+"";
        String stm = startMonth+"";
        String std = startDay+"";
        intent.putExtra(CreateSkillFragment.STARTYEAREDIT,sty);
        intent.putExtra(CreateSkillFragment.STARTMONTHEDIT, stm);
        //intent.putExtra(CreateSkillFragment.STARTDAYEDIT, std);
        if(edit==false)
            setResult(RESULT_OK, intent);
        else if(edit)
            setResult(SkillView.EDITED,intent);
        finish();
    }


    private DatePickerDialog createDialogWithoutDateField(Context context,StartYearDatePick obj) {


        DatePickerDialog dpd = new DatePickerDialog(context,obj, startYear, startMonth-1, startDay);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        //Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }

    public class StartYearDatePick implements DatePickerDialog.OnDateSetListener{

        StartYearDatePick(){ }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startYear = year;
            startMonth = monthOfYear+1;
            //startDay = dayOfMonth;
        }
    }
}
