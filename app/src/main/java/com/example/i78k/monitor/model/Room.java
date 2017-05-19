package com.example.i78k.monitor.model;


import java.util.List;

public class Room {
    public String Id = ""; //Первые три переменные из IP
    public String Name = "3 + 4";
    public List<Timing> Timing;

    public Room() {
    }

    public String getTimings() {
        String sTiming = "";
        for (int i = 0; i < Timing.size(); i++) {
            sTiming += Timing.get(i).BeginTime + " - " + Timing.get(i).EndTime + "\n";
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
}
