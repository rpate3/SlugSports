package com.example.will.slugsports;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.Calendar;

public class createEvent extends FragmentActivity
        implements TimePickerFragment.OnTimeSelectedListener{

    Bundle bundle;
    ParseObject event;
    int hour;
    int minute;
    String AM_PM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        bundle = getIntent().getExtras();

        event = new ParseObject("Event");

        TextView criteria = (TextView) findViewById(R.id.textView4);
        criteria.setText(bundle.getString("sport") + " at " + bundle.getString("location") +
                        " on " + bundle.getInt("month") + "/" +bundle.getInt("day") + "/" +
                        bundle.getInt("year"));

        EditText editText = (EditText) findViewById(R.id.editText10);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }


        });
        //prevent keyboard
        editText.setRawInputType(InputType.TYPE_NULL);
        editText.setFocusable(true);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void buttonPressed(View v){
        EditText editText = (EditText) findViewById(R.id.editText);
        String eventName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText2);
        String preferredPlayers = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText3);
        String eventDescription = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText4);
        String userName = editText.getText().toString();

        if(eventName.length() > 0 && preferredPlayers.length() > 0 && eventDescription.length() > 0 &&
                userName.length() > 0 && AM_PM.length() > 0) {

            if (eventName.length() < 1) {
                eventName = "No Name";
            }

            if (preferredPlayers.length() < 1) {
                preferredPlayers = "2";
            }

            if (eventDescription.length() < 1) {
                eventDescription = "No Description";
            }

            if (userName.length() < 1) {
                userName = "user";
            }

            if (AM_PM.length() < 1) {
                //Toast.makeText(getApplicationContext(), "Please enter a time", Toast.LENGTH_LONG).show();
                AM_PM = "AM";
                //return;
            }


            event.put("userName", userName);
            event.put("eventName", eventName);
            event.put("numPlayers", preferredPlayers); //Not the # of people joined, the # preferred, must change to int sometime
            event.put("numJoined", 1);
            event.put("description", eventDescription);
            event.put("minute", minute);
            event.put("hour", hour);
            event.put("AM_PM", AM_PM);

            int day = bundle.getInt("day");
            int month = bundle.getInt("month");
            int year = bundle.getInt("year");

            event.put("day", day);
            event.put("month", month);
            event.put("year", year);

            String location = bundle.getString("location");
            String sport = bundle.getString("sport");

            event.put("location", location);
            event.put("sport", sport);

            JSONArray usersAttending = new JSONArray();
            usersAttending.put(App.getAcct());
            event.put("usersAttending", usersAttending);

            //Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            Log.i("DEBUG", "Saving in BG");
            //event.saveInBackground();

            event.saveInBackground();
            Log.i("DEBUG", "Saved in BG");

            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            super.onBackPressed();

        } else{
            Toast.makeText(createEvent.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void onTimeSelected(int hourOfEvent, int minuteOfEvent){
        minute = minuteOfEvent;

        if(hourOfEvent > 12){
            hour = hourOfEvent % 12;
            AM_PM = "PM";
        }

        else {
            hour = hourOfEvent;
            AM_PM = "AM";
        }

        EditText editText= (EditText) findViewById(R.id.editText10);
        if(minute > 10)
            editText.setText(hour + ":" + minute + " " + AM_PM);
        else
            editText.setText(hour + ":0" + minute + " " + AM_PM);
    }
}
