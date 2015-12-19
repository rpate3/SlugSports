package com.example.will.slugsports;

import android.app.Application;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.parse.Parse;

/**
 * Created by will on 11/15/15.
 */
public class App extends Application{

    static private String account = "";
    static private GoogleAccountCredential cred = null;

    @Override public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(getApplicationContext());

        Parse.initialize(this, "7st18qTMNhNJICNJx1hY5cbk8BzSKB99fKx1qCgP", "zeyvANSw3bh0yLOiPtQJ05052qaKFNIaV7cP83Og");

    }

    static public void setCred(GoogleAccountCredential c){
        cred = c;
    }

    static public void setAcct() {
        account = cred.getSelectedAccountName();
    }

    static public String getAcct(){
        return account;
    }

    static public GoogleAccountCredential getCred(){
        return cred;
    }
}
