package com.example.i78k.monitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.i78k.monitor.model.Room;

import java.util.ArrayList;
import java.util.List;

public class TimeListActivity extends AppCompatActivity {

    protected ArrayAdapter<Room> adapter;
    protected ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelist);

        adapter = new ArrayAdapter<>(TimeListActivity.this,R.layout.activity_timelist);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        adapter.clear();

        shourooms(createDemodata());
    }

    public List<Room> createDemodata () {
        List <Room> Rooms = new ArrayList<>();

        for ( int i=0; i<5; i++)
        {
            Rooms.add(new Room("Комната "+ i,""));
        }
        return Rooms;
    }
    public void shourooms(final List <Room> rooms) {

        adapter = new MonoAdapter(TimeListActivity.this);
        listview = (ListView) findViewById(R.id.listview);


        for (int i = 0; i < rooms.size(); i++) {
            adapter.add(rooms.get(i)); //TODO вывести список времен
        }
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Toast toast = Toast.makeText(getApplicationContext(),
                        "Открытие", Toast.LENGTH_SHORT);
                toast.show();*/

                DialogList dialog = new DialogList();
                dialog.list_pri(rooms,position);
                //tv = (TextView)getView().findViewById(R.id.textD);
                //tv.setText(text);
                dialog.show(getSupportFragmentManager(), "custom");

            }
        });
    }
    public class MonoAdapter extends ArrayAdapter<Room>  {

        public MonoAdapter(Context context) {
            super(context, R.layout.post_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Room room = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.post_item, null);
            }
            ((TextView) convertView.findViewById(R.id.textV1))
                    .setText(room.name);
            ((TextView) convertView.findViewById(R.id.textV2))
                    .setText(room.sTiming);
            return convertView;
        }
    }


}
