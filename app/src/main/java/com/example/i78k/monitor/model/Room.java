package com.example.i78k.monitor.model;


import java.util.ArrayList;
import java.util.List;

public class Room {
    String name = "3 + 4";
    List<Timing> timings;

    public Room(String name) {
        this.name = name;
        timings =new ArrayList<Timing>();
        for (int i =0;i<10;i++)
        {
            timings.add(new Timing());
        }
    }
}
