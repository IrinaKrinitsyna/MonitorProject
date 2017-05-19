package com.example.i78k.monitor;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i78k.monitor.model.Room;
import com.example.i78k.monitor.soap.WebServiceCommunication;

import java.lang.ref.WeakReference;

public class DialogList extends DialogFragment {
    TextView tv;
    String text;
    String roomeName;
    String idRoom;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle(roomeName);
        builder.setView(onCreateView())
                .setPositiveButton("Отправить заявку", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new ReservationTask((TimeListActivity) getActivity()).execute();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast toast = Toast.makeText(getActivity(), "Отмена", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

        return builder.create();
    }


    public View onCreateView() {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View V = inflater.inflate(R.layout.dialog, null, false);
        tv = (TextView) V.findViewById(R.id.textD);
        tv.setText(text);
        return V;
    }

    public void setRoom(Room room) {
        text = room.getTimings();
        roomeName = room.Name;
    }
    private class ReservationTask extends AsyncTask<String, Void, Pair<Boolean, String>> {

        private WebServiceCommunication wsCommunication;
        WeakReference<TimeListActivity> weakActivity;

        public ReservationTask(TimeListActivity weakActivity) {
            this.weakActivity = new WeakReference<>(weakActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsCommunication = new WebServiceCommunication();
        }
        @Override
        protected Pair<Boolean, String> doInBackground(String... params) {
            return wsCommunication.reservation(params[0], params[1], params[2], params[3], params[4]);
        }
        @Override
        protected void onPostExecute(Pair<Boolean, String> result) {
            super.onPostExecute(result);
            if (weakActivity.get() != null) {
                weakActivity.get().onReservationFinished(result);
            }
        }
    }

}