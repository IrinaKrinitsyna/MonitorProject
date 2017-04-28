package com.example.i78k.monitor.model;


import java.util.ArrayList;
import java.util.List;

public class Room {
    public String name = "3 + 4";
    public String sTiming ;
    public List<Timing> timings;


    public Room(String name,String time) {
        this.name = name;
        this.sTiming = time;
        timings =new ArrayList<Timing>();
        for (int i =0;i<5;i++)
        {
            timings.add(new Timing());
            sTiming += timings.get(i).beginTime + " - " + timings.get(i).endTime + "\n";
        }
    }
}