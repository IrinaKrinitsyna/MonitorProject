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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.i78k.monitor.model.Room;
import com.example.i78k.monitor.soap.WebServiceCommunication;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DialogList extends DialogFragment {

    TextView tv;
    String text;
    String roomName;
    String idUserD;
    String idRoom;
    TimePicker timeFrom;
    TimePicker endTime ;
    EditText commentText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(createDialogView())
                .setPositiveButton("Отправить заявку", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new ReservationTask((TimeListActivity) getActivity())
                                .execute(idUserD, idRoom, dateFormat(timeFrom),
                                        dateFormat(endTime), commentText.getText().toString());
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


    public View createDialogView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null, false);
        tv = (TextView) dialogView.findViewById(R.id.textD);
        timeFrom = (TimePicker) dialogView.findViewById(R.id.timeFrom);
        timeFrom.setIs24HourView(true);
        endTime = (TimePicker) dialogView.findViewById(R.id.endTime);
        endTime.setIs24HourView(true);
        commentText = (EditText) dialogView.findViewById(R.id.commentText);
        tv.setText(text);
        return dialogView;
    }

    public void setRoom(Room room,String idUser) {
        text = room.getTimings();
        roomName = room.Name;
        idRoom = room.Id;
        idUserD = idUser;
    }

    public String dateFormat(TimePicker tP) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, tP.getCurrentMinute());
        c.set(Calendar.HOUR_OF_DAY, tP.getCurrentHour());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return simpleDateFormat.format(c.getTime());
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