package com.example.i78k.monitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.i78k.monitor.model.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
   public List <Room> createDemodata () {
       List <Room> Rooms = new ArrayList<>();


       for ( int i=0; i<5; i++)
       {
           Rooms.add(new Room("room "+i));
       }
       return Rooms;
   }
}
