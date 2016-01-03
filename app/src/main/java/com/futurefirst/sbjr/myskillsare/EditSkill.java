package com.futurefirst.sbjr.myskillsare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }

    public void datePick(View v){
        StartYearDatePick startYearDatePick = new StartYearDatePick();
        startYearDatePick.show(EditSkill.this.getFragmentManager(),"Select Start of Practice Date");
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
        intent.putExtra(CreateSkillFragment.STARTMONTHEDIT,stm);
        intent.putExtra(CreateSkillFragment.STARTDAYEDIT,std);
        setResult(RESULT_OK, intent);
        finish();
    }


    public class StartYearDatePick extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        StartYearDatePick(){ }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DatePickerDialog dialog = new DatePickerDialog(EditSkill.this, this, startYear,startMonth-1,startDay);
            return dialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startYear = year;
            startMonth = monthOfYear+1;
            startDay = dayOfMonth;
        }
    }
}
