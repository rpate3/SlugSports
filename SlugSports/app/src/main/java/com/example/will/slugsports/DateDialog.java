package com.example.will.slugsports;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by will on 11/13/15.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    String out;
    int yy, mm, dd = 0;
    String location = "";
    String sport = "";

    public DateDialog(){

    }

    public void setSport(String s){
        sport = s;
    }

    public void setLoc(String l){
        location = l;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c= Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog d = new DatePickerDialog(getActivity(), this, year, month, day);
        d.getDatePicker().setCalendarViewShown(true);
        d.getDatePicker().setSpinnersShown(false);
        d.onDateChanged(d.getDatePicker(),year,month,day);
        return d;

    }


    public void onDateSet(DatePicker view, int year, int month, int day){
        String date = day + "/" + (month+1) + "/" + year;
        //txtDate.setText(date);
        out = date;
        yy = year;
        mm = month+1;
        dd = day;

    }

    // Gets called before onDismiss, so we can erase the selectedDate
    @Override
    public void onCancel(DialogInterface dialog) {
        yy = 0;
        mm = 0;
        dd = 0;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (yy != 0) {
            dialog.dismiss();
            Intent intent1 = new Intent(getActivity(), FoundGames.class);
            intent1.putExtra("day", dd);
            intent1.putExtra("month", mm);
            intent1.putExtra("year", yy);
            intent1.putExtra("sport", sport);
            intent1.putExtra("location", location);

            startActivity(intent1);
            getActivity().finish();

        }
    }




































































}
