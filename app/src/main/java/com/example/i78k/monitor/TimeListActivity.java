package com.example.i78k.monitor;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i78k.monitor.model.Room;
import com.example.i78k.monitor.soap.WebServiceCommunication;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class TimeListActivity extends AppCompatActivity {
    protected ArrayAdapter<Room> adapter;
    protected ListView listview;
    String idUser= ""; // or other values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelist);

        adapter = new ArrayAdapter<>(TimeListActivity.this,R.layout.activity_timelist);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        adapter.clear();

        Bundle b = getIntent().getExtras();

        if(b != null)
            idUser = b.getString("id");
        new RoomsTask(this).execute(idUser);
    }

    private void onGetRoomsFinished(Pair<Boolean, String> result) {
        try {
            List<Room> rooms = new ObjectMapper().readValue(result.second,
                    new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Room.class));
             shourooms(rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onReservationFinished(Pair<Boolean, String> result) {
        Toast.makeText(this, "Отправлено", Toast.LENGTH_SHORT).show();
    }

    private class RoomsTask extends AsyncTask<String, Void, Pair<Boolean, String>> {

        private WebServiceCommunication wsCommunication;
        WeakReference<TimeListActivity> weakActivity;

        public RoomsTask(TimeListActivity weakActivity) {
            this.weakActivity = new WeakReference<>(weakActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsCommunication = new WebServiceCommunication();
        }

        @Override
        protected Pair<Boolean, String> doInBackground(String... params) {
            return wsCommunication.busyConference(params[0]);
        }

        @Override
        protected void onPostExecute(Pair<Boolean, String> result) {
            super.onPostExecute(result);
            if (weakActivity.get() != null) {
                weakActivity.get().onGetRoomsFinished(result);
            }
        }
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
                DialogList dialog = new DialogList();
                dialog.setRoom(rooms.get(position),idUser);
                dialog.show(getSupportFragmentManager(), "custom");

            }
        });
    }
    public class MonoAdapter extends ArrayAdapter<Room> {
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
            ((TextView) convertView.findViewById(R.id.textCaption))
                    .setText(room.Name);
            ((TextView) convertView.findViewById(R.id.textTimingd))
                    .setText(room.getTimings());
            return convertView;
        }
    }


}

