package com.example.i78k.monitor.model;


import java.util.ArrayList;
import java.util.List;

public class Room {
    public String Id = "";
    public String Name = "3 + 4";
    public List<Timing> Timing;
    public String sTiming = "";
    public String sTimingFull = "";

    public Room(String name) {
        this.Name = name;
        Timing = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Timing.add(new Timing());
            sTiming += Timing.get(i).BeginTime + " - " + Timing.get(i).EndTime + "\n";
            sTimingFull += Timing.get(i).Person + "\n" + Timing.get(i).BeginTime +
                    " - " + Timing.get(i).EndTime + "\n" + Timing.get(i).Сomment + "\n"+ "\n" ;
        }
    }

    public Room() {
    }
}
