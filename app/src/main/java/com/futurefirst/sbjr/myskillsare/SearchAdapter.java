package com.futurefirst.sbjr.myskillsare;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sbjr on 1/13/16.
 */
public class SearchAdapter extends ArrayAdapter<String>{

    ArrayList<String> searchresult;
    Context context;
    int resourseLayout;

    public SearchAdapter(Context context,int layoutid,ArrayList<String> data){
        super(context,layoutid,data);
        this.context = context;
        searchresult = data;
        resourseLayout = layoutid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.searchgroup, parent, false);
        }
        TextView name = (TextView) v.findViewById(R.id.search_name);
        TextView location = (TextView) v.findViewById(R.id.search_location);
        ImageView pic = (ImageView) v.findViewById(R.id.search_image);

        DatabaseHelper dbhelper = new DatabaseHelper(context);
        String text = searchresult.get(position);
        String details[] = dbhelper.getUserDetail(text);

        //Log.d("Database","details added for "+details[0]);
        name.setText(details[0]+" "+details[1]);
        location.setText(details[2]);
        Bitmap bitmap = dbhelper.getUserProfilePic(searchresult.get(position));
        if(bitmap!=null)
            pic.setImageBitmap(Bitmap.createScaledBitmap(bitmap,85,85,false));
        //Log.d("Database", "Image added for " + details[0]);

        return v;
    }
}
