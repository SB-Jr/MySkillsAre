package com.futurefirst.sbjr.myskillsare;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sbjr on 12/29/15.
 */
public class SKillSetAdapter  extends ArrayAdapter<String[]> {

    Context context;
    int resourceLayoutId;

    ArrayList<String[]> skills;


    public SKillSetAdapter(Context context,int layoutid,ArrayList<String[]> data){
        super(context,layoutid,data);
        this.context = context;
        resourceLayoutId=layoutid;
        skills = data;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView tv1,tv2;
        if(v==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            v = inflater.inflate(resourceLayoutId,parent,false);
        }

        tv1 =(TextView) v.findViewById(R.id.list_item_skill_name);
        tv2 =(TextView) v.findViewById(R.id.list_item_skill_description);

        if(skills.size()>position){
            String s[] = skills.get(position);
            tv1.setText(s[0]);
            tv2.setText(s[1]);
        }
        return v;
    }

}
