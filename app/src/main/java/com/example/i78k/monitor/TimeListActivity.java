package com.example.i78k.monitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.Toast;

import com.example.i78k.monitor.model.Room;
import com.example.i78k.monitor.soap.WebServiceCommunication;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class TimeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelist);

        Bundle b = getIntent().getExtras();
        String id = ""; // or other values
        if(b != null)
            id = b.getString("id");
        new RoomsTask(this).execute(id);
    }

    private void onGetRoomsFinished(Pair<Boolean, String> result) {
        try {
            List<Room> rooms = new ObjectMapper().readValue(result.second,
                    new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Room.class));
            Toast.makeText(this, rooms.size() + " rooms loaded ", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
