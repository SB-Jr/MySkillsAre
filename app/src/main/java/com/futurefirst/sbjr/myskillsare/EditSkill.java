package com.futurefirst.sbjr.myskillsare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DateKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class EditSkill extends AppCompatActivity {

    private EditText skillname=null;
    private EditText description=null;
    private EditText monthText=null;
    private EditText yearText=null;
    Calendar calendar;
    int startYear=0;
    int startMonth=0;
    int presentMonth;
    int presentYear;
    boolean edit = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createskill);

        setTitle("Set the Skill");
        Log.d("Skill", "editing started");

        skillname = (EditText) findViewById(R.id.skillnameedit);
        description = (EditText) findViewById(R.id.descriptionedit);
        monthText = (EditText) findViewById(R.id.date_month);
        yearText = (EditText) findViewById(R.id.date_year);


        calendar = Calendar.getInstance();
        presentYear = calendar.get(Calendar.YEAR)-1;
        presentMonth = calendar.get(Calendar.MONTH)+1;


        Intent intent = getIntent();
        if(intent!=null){
            try {
                skillname.setText(intent.getStringExtra(SkillView.SKILLNAMEFOREDIT));
                description.setText(intent.getStringExtra(SkillView.DESCRIPTIONFOREDIT));
                String date = intent.getStringExtra(SkillView.YEAROFPRACTICEFOREDIT);
                String datebroken[] = date.split("/");

                startMonth = Integer.parseInt(datebroken[0]);
                startYear = Integer.parseInt(datebroken[1]);
                monthText.setText(startMonth);
                yearText.setText(startYear);

            }
            catch (Exception e){ }
        }

    }

    public int datePick(){
        TextView warning = (TextView) findViewById(R.id.datewarning);

        int month,year;
        try {
            month = Integer.parseInt(monthText.getText().toString());
            year = Integer.parseInt(yearText.getText().toString());
        }
        catch (Exception e){
            warning.setText("Enter a valid Month and Year");
            return 0;
        }

        if(month>12||month<=0) {
            warning.setText("Month Has to be between 1 And 12");
            return 0;
        }
        if(year>presentYear||year<1940) {
            warning.setText("Date has to be between 1940 and "+presentYear);
            return 0;
        }
        if(year==presentYear){
            if(month>presentMonth) {
                warning.setText("Date has to be in the past and not in the future");
                return 0;
            }
        }
        presentMonth = month;
        presentYear = year;
        return 1;
    }

    public void doneSkillEdit(View v){
        if(datePick()==1) {
            //Log.d("Skill","new intent is being  created");
            String skill = skillname.getText().toString();
            String desc = description.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(CreateSkillFragment.SKILLNAMEEDIT, skill);
            intent.putExtra(CreateSkillFragment.DESCRIPTIONEDIT, desc);
            String sty = presentYear + "";
            String stm = presentMonth + "";
            intent.putExtra(CreateSkillFragment.STARTYEAREDIT, sty);
            intent.putExtra(CreateSkillFragment.STARTMONTHEDIT, stm);

            if (edit == false) {

                //Log.d("Skill","result sent ok");
                setResult(RESULT_OK, intent);
            }
            else if (edit) {
                //Log.d("Skill","result sent edited");
                setResult(SkillView.EDITED, intent);
            }
            finish();
        }
    }
}
