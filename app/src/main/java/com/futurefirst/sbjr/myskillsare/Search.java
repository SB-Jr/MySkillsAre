package com.futurefirst.sbjr.myskillsare;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText searchtext;
    ListView searchList;
    ArrayList<String> searchresult;
    SearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchresult = new ArrayList<String>();
        adapter = new SearchAdapter(getApplicationContext(),R.layout.searchgroup,searchresult);
        searchList = (ListView) findViewById(R.id.search_list);
        searchList.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String emailid = searchresult.get(position);
                Intent intent = new Intent(getApplicationContext(),Profile.class);
                intent.putExtra(Register.EMAILIDKEY,emailid);
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();

        actionBar.setCustomView(R.layout.search_actionbar);

        searchtext = (EditText) actionBar.getCustomView().findViewById(R.id.search_text);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM|ActionBar.DISPLAY_SHOW_HOME);
    }

    public void searchQuerry(View v){
        String text = searchtext.getText().toString();
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        ArrayList<String> temp = dbhelper.search(text);
        searchresult.clear();
        searchresult.addAll(temp);
        if(searchresult.contains(Profile.Emailid))
            searchresult.remove(Profile.Emailid);
        //Log.d("Database", searchresult.get(0) + " Added");
        if(searchresult.size()!=0) {
            //Log.d("Database","inside if");
            adapter.notifyDataSetChanged();
            //Log.d("Database", "executed notify statement");
        }
        else{
            TextView tv = (TextView) findViewById(R.id.no_result);
            tv.setText("No Result Found for "+text);
        }
    }

}
