package com.example.will.slugsports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import android.webkit.WebViewClient;


public class MapActivity extends AppCompatActivity {

    Spinner locs ;
    String[] facilities = {
            "East Field",
            "East Field House Dance Studio",
            "East Field House Gym",
            "East Field House Martial Arts Room",
            "East Field House Racquetball Court",
            "East Remote Field",
            "OPERS Pool",
            "Wellness Center",
            "West Field House",
            "West Gym",
            "West Tennis Courts"
    };
    WebView myWebView;
    static final public String baseUrl = "http://maps.ucsc.edu/content/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_map);
        locs = (Spinner) findViewById(R.id.spinner);

        locs.getOnItemSelectedListener();

        ArrayList<String> facilityList = new ArrayList<String>(Arrays.asList(facilities));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, facilityList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locs.setAdapter(adapter);
        AdapterView.OnItemSelectedListener l = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String replaced = parent.getItemAtPosition(position).toString().replace(' ', '-');
                myWebView.loadUrl(baseUrl+replaced);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        locs.setOnItemSelectedListener(l);

        initWebview();
    }


    private void initWebview(){
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
    }


}
