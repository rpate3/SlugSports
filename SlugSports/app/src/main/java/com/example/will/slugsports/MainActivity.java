package com.example.will.slugsports;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> spList, locList;
    private String currentList;
    private ListView myListView;
    private ArrayAdapter<String> arrayAdapter;
    private String LOG_TAG = "DEBUG: ";
    private String chosenSp = "";
    private String chosenLoc = "";
    private String chosenCriteria = "";

    public String getSp(){
        return chosenSp;
    }

    public String getLoc(){
        return chosenLoc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        spList = new ArrayList<String>();
        locList = new ArrayList<String>();
        populateSports(spList);
        populateLocs(locList);
        arrayAdapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, spList
        );
        currentList = "Sports";
        myListView = (ListView) findViewById(R.id.listView);
        //Make each list element clickable
        populateListView(spList, "Sports");
        myListView.setAdapter(arrayAdapter);


    }

    @Override
    public void onBackPressed() {
        switch (currentList){
            case "Sports":
                new AlertDialog.Builder(this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                MainActivity.super.onBackPressed();
                            }
                        }).create().show();
                //super.onBackPressed();
                break;
            case "Locations":
                //currentList = "Sports";
                chosenCriteria = "";
                updateChosenView();
                populateListView(spList, "Sports");
                break;
            default:
                break;
        }
        updateChosenView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //populate list with the given criteria
    //also adds a listener to link to the next set of criteria (if available)
    private void populateListView(ArrayList<String> list, String nextList){
        final ArrayList<String> list2 = list;
        currentList = nextList;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list2);
        changeTitle();
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast toast;
                switch(currentList){
                    case "Sports":
                        chosenSp =  list2.get(position);
                        currentList = "Locations";
                        chosenCriteria = list2.get(position);
                        populateListView(locList, "Locations");
                        toast = Toast.makeText(MainActivity.this, list2.get(position), Toast.LENGTH_SHORT);
                        //toast.show();
                        break;
                    case "Locations":
                        chosenLoc = list2.get(position);
                        //currentList = "Days";
                        chosenCriteria = chosenSp + " : " + list2.get(position);
                        //populateListView(dayList, "Days");
                        toast = Toast.makeText(MainActivity.this, list2.get(position), Toast.LENGTH_SHORT);
                        //toast.show();

                        DateDialog d = new DateDialog();
                        d.setLoc(chosenLoc);
                        d.setSport(chosenSp);
                        FragmentTransaction f = getFragmentManager().beginTransaction();
                        d.show(f, "DatePicker");
                        break;
                    default:
                        break;
                }
                updateChosenView();
                Log.i(LOG_TAG, currentList + " : ");
            }
        });
        myListView.setAdapter(arrayAdapter);
    }

    private void changeTitle(){
        TextView temp = (TextView) findViewById(R.id.titleView);
        temp.setText(currentList + " List");
    }

    private void updateChosenView(){
        TextView temp = (TextView) findViewById(R.id.textView2);
        temp.setText(chosenCriteria);
    }

    //Populate spList with the available sports
    private void populateSports(ArrayList<String> spList){
        spList.add("Baseball");
        spList.add("Basketball");
        spList.add("Football");
        spList.add("Frisbee Golf");
        spList.add("Futsal");
        spList.add("Hockey");
        spList.add("Racket Ball");
        spList.add("Soccer");
        spList.add("Swimming");
        spList.add("Tennis");
        spList.add("Track");
        spList.add("Weightlifting");
        spList.add("Other");
    }

    //Populate locList with the available locations
    private void populateLocs(ArrayList<String> locList){
        locList.add("East Field");
        locList.add("East Field House Dance Studio");
        locList.add("East Field House Gym");
        locList.add("East Field House Martial Arts Room");
        locList.add("East Field House Racquetball Court");
        locList.add("East Remote Field");
        locList.add("OPERS Pool");
        locList.add("Wellness Center (Gym)");
        locList.add("West Field House");
        locList.add("West Gym");
        locList.add("West Tennis Courts");
        locList.add("Other");

    }

    public void popupMap(View v){
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
