package com.futurefirst.sbjr.myskillsare;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateSkillFragment extends Fragment {

    public Button addSkillButton;

    public ListView skillList=null;
    public ArrayList<String[]> skillListArray = new ArrayList<String[]>();
    SKillSetAdapter adapter;

    private static final String CATEGORYKEY ="CATEGORYKEY";
    private static final String FRAGMENTTYPE ="FRAGMENTTYPE";
    public final static String SKILLNAMEEDIT="SKILLNAMEEDIT";
    public final static String DESCRIPTIONEDIT="DESCRIPTIONEDIT";
    public final static String STARTYEAREDIT="STARTYEAREDI";
    public final static String STARTMONTHEDIT="STARTMONTHEDIT";
    public final static String STARTDAYEDIT="STARTDAYEDIT";

    String category=null;

    public final int SKILLREQUEST = 100;

    private int fragmenttype;


    public static CreateSkillFragment createInstance(String categoryname,int type){
        CreateSkillFragment frag = new CreateSkillFragment();
        Bundle b = new Bundle();
        b.putString(CATEGORYKEY,categoryname);
        b.putInt(FRAGMENTTYPE,type);
        frag.setArguments(b);
        return frag;
    }



    public void addSkillToList(String s[]){
        skillListArray.add(s);//45953
        adapter.notifyDataSetChanged();
    }

    public int getCount(){
        return skillListArray.size();
    }

    public void setButtonInvisible(){
        addSkillButton.setClickable(false);
        addSkillButton.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public CreateSkillFragment() {    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_create_skill, container, false);

        Bundle b = getArguments();

        if(b!=null) {
            if (b.getString(CATEGORYKEY, null) != null) {
                category = b.getString(CATEGORYKEY);
                fragmenttype = b.getInt(FRAGMENTTYPE);
            }
        }

        skillList = (ListView) v.findViewById(R.id.skill_list);
        adapter = new SKillSetAdapter(getContext(),R.layout.skill__list_item,skillListArray);

        skillList.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        if(fragmenttype==Profile.FRAGMENTTYPEPROFILE) {
            skillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), SkillView.class);
                    intent.putExtra("Category", category);
                    String s[] = skillListArray.get(position);
                    String skill = s[0];
                    intent.putExtra("Skill", skill);
                    startActivity(intent);
                }
            });
        }


        addSkillButton = (Button) v.findViewById(R.id.addskillbutton);

        addSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditSkill.class);
                startActivityForResult(intent, SKILLREQUEST);
            }
        });


        if(fragmenttype==Profile.FRAGMENTTYPEPROFILE){
            DatabaseHelper dbhelper = new DatabaseHelper(getContext());
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            String querry = "SELECT * FROM "+ DatabaseContract.SkillTable.TABLENAME+
                    " WHERE "+ DatabaseContract.SkillTable.CATEGORY+" = \'"+category+"\' AND "+
                    DatabaseContract.SkillTable.EMAILID+" = \'"+Profile.Emailid+"\';";
            Cursor cursor = db.rawQuery(querry, null);
            if(cursor.moveToFirst()){
                do{
                    String skn = cursor.getString(1);
                    String skd = cursor.getString(4);
                    String s[] = {skn,skd};
                    addSkillToList(s);
                }while (cursor.moveToNext());
            }
            db.close();
            if(skillListArray.size()>=3)
                setButtonInvisible();
        }


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SKILLREQUEST){
            if(resultCode==Activity.RESULT_OK){
                String skill = data.getStringExtra(SKILLNAMEEDIT);
                String date = data.getStringExtra(STARTDAYEDIT)+"/"+data.getStringExtra(STARTMONTHEDIT)+"/"+data.getStringExtra(STARTYEAREDIT);

                String s[]={skill,date};

                addSkillToList(s);

                if(skillListArray.size()>=3){
                    setButtonInvisible();
                }

                DatabaseHelper dbhelper = new DatabaseHelper(getContext());
                String desc = data.getStringExtra(DESCRIPTIONEDIT);
                dbhelper.dbInsertTemp(skill, category, date, desc);
                if(fragmenttype==Profile.FRAGMENTTYPEPROFILE){
                    dbhelper.dbInsertSkill(Profile.Emailid);
                }
            }
        }
    }
}
