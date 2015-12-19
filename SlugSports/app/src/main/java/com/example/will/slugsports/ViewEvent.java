package com.example.will.slugsports;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

public class ViewEvent extends AppCompatActivity {

    Bundle bundle;
    String objectId;
    String sport,title,creatr,pref,join,des,date,time = "";

    TextView chosenSport, gameTitle, creator, joined, desc, eventDay, eventTime;
    Button joinButton;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        progress = new ProgressDialog(this);


        chosenSport = (TextView) findViewById(R.id.chosenSport);
        gameTitle = (TextView) findViewById(R.id.gameTitle);
        creator = (TextView) findViewById(R.id.gameCreator);
        joined = (TextView) findViewById(R.id.NumJoined);
        desc = (TextView) findViewById(R.id.desc);
        eventDay = (TextView) findViewById(R.id.chosenDay);
        eventTime = (TextView) findViewById(R.id.chosenTime);

        joinButton = (Button) findViewById(R.id.joinGame);

        bundle = getIntent().getExtras();
        objectId = bundle.getString("objectId");
        initializeGame();
        //setFields();

    }

    public void setFields(){
        /*
        TextView chosenSport = (TextView) findViewById(R.id.chosenSport);
        TextView gameTitle = (TextView) findViewById(R.id.gameTitle);
        TextView creator = (TextView) findViewById(R.id.gameCreator);
        TextView preferred = (TextView) findViewById(R.id.PreferredNum);
        TextView joined = (TextView) findViewById(R.id.NumJoined);
        TextView desc = (TextView) findViewById(R.id.desc);
*/
        chosenSport.setText(sport);
        gameTitle.setText(title);
        creator.setText(creatr);
        //preferred.setText(pref);
        joined.setText("Players joined:\n" + join + "/" + pref);
        desc.setText(des);
        eventDay.setText(date);
        eventTime.setText(time);


        //gameTitle.setText(currentGame.getString("eventName"));
        //creator.setText(currentGame.getString("userName"));
        //preferred.setText(currentGame.getInt("numPlayers")+"");
        //joined.setText(currentGame.getInt("numJoined")+"");
        //desc.setText(currentGame.getString("description"));


    }

    public void joinEvent(View v){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject event, ParseException e) {

                boolean alreadyJoined = false;

                JSONArray usersAttending = event.getJSONArray("usersAttending");

                for(int i = 0; i < usersAttending.length(); i++) {
                    try {

                        String user = usersAttending.get(i).toString();
                        if(user.equalsIgnoreCase(App.getAcct())) alreadyJoined = true;

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

               if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    if(!alreadyJoined) {
                        event.put("numJoined", event.getNumber("numJoined").intValue() + 1);
                        event.addAllUnique("usersAttending", Arrays.asList(App.getAcct()));
                        joined.setText("Players joined:\n" + (event.getNumber("numJoined").intValue()) + "/" + pref);
                        joinButton.setText("Un-join");
                        Toast.makeText(ViewEvent.this, "Joined event", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        event.put("numJoined", event.getNumber("numJoined").intValue() - 1);
                        event.removeAll("usersAttending", Arrays.asList(App.getAcct()));
                        joined.setText("Players joined:\n" + (event.getNumber("numJoined").intValue()) + "/" + pref);
                        joinButton.setText("Join");
                        Toast.makeText(ViewEvent.this, "Unjoined event", Toast.LENGTH_SHORT).show();
                    }
                    event.saveInBackground();
                }
            }
        });
    }

    public void initializeGame() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        progress = progress.show(ViewEvent.this, "",
                "Fetching event data...", true);

        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject event, ParseException e) {

                boolean alreadyJoined = false;

                JSONArray usersAttending = event.getJSONArray("usersAttending");

                for(int i = 0; i < usersAttending.length(); i++) {
                    try {

                        String user = usersAttending.get(i).toString();
                        if(user.equalsIgnoreCase(App.getAcct())) alreadyJoined = true;

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

                if(alreadyJoined){
                    joinButton.setText("Un-join");
                }
                else{
                    joinButton.setText("Join");
                }

                if (e == null){
                    sport = "Sport: " + event.getString("sport");
                    title = event.getString("eventName");
                    creatr = "By: " + event.getString("userName");
                    pref = event.getString("numPlayers");
                    join = event.getInt("numJoined")+"";
                    des = "Description: \n" + event.getString("description");
                    date = "Date: " + event.getInt("month") + "/" + event.getInt("day")
                            + "/" + event.getInt("year");
                    time = "Time: " +
                            ((event.getInt("hour") > 1) ? event.getInt("hour") : 12) + ":" +
                            ((event.getInt("minute") > 10) ? event.getInt("minute") : "0"+(event.getInt("minute"))) +
                            " " + event.getString("AM_PM");

                    //
                    //Toast.makeText(ViewEvent.this, "sport is " + sport, Toast.LENGTH_SHORT).show();

                    //works
                    //TextView chosenSport = (TextView) findViewById(R.id.chosenSport);
                    //chosenSport.setText(sport);

                    setFields();
                } else{

                }

                try {
                    Thread.sleep(500);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                progress.dismiss();

            }

        });

    }
}
