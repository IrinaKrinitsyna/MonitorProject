package com.example.i78k.monitor.model;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class Room {
    public String Id = ""; //Первые три переменные из IP
    public String Name = "3 + 4";
    public List<Timing> Timing;

    public Room() {
    }

    public String getTimings() {
        String sTiming = "";
        for (int i = 0; i < Timing.size(); i++) {
            sTiming += dateFormat(Timing.get(i).BeginTime) + " - " + dateFormat(Timing.get(i).EndTime) + "\n";
        }
        return sTiming;
    }

    public String getTimingsFull() {
        String sTimingFull = "";
        for (int i = 0; i < Timing.size(); i++) {
            sTimingFull += Timing.get(i).Person + "\n" + Timing.get(i).BeginTime +
                    " - " + Timing.get(i).EndTime + "\n" + Timing.get(i).Сomment + "\n" + "\n";
        }
        return sTimingFull;

    }

    public String dateFormat(String date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat viewFormat = new SimpleDateFormat("HH:mm");
        parseFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // TODO Check it
        viewFormat.setTimeZone(TimeZone.getTimeZone("MSK")); // TODO Check it
        try {
            return viewFormat.format(parseFormat.parse(date));
//            return parseFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }

}
