package com.example.i78k.monitor;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextWatcher;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DialogList extends DialogFragment {
    TextView tv;
    String text;
    String roomeName;
    String idUserD;
    String idRoom;
    EditText timeFrom;
    EditText endTime ;
    EditText comentText ;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(onCreateView())

                .setPositiveButton("Отправить заявку", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new ReservationTask((TimeListActivity) getActivity())
                                .execute(idUserD,idRoom,dateFormat(timeFrom.getText().toString()),
                                        dateFormat(endTime.getText().toString()),comentText.getText().toString());
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
        timeFrom = (EditText) V.findViewById(R.id.timeFrom);
        endTime = (EditText) V.findViewById(R.id.endTime);
        comentText = (EditText) V.findViewById(R.id.comentText);
        tv.setText(text);
        return V;
    }

    public void setRoom(Room room,String idUser) {
        text = room.getTimings();
        roomeName = room.Name;
        idRoom = room.Id;
        idUserD= idUser;
    }
    public String dateFormat(String str) {


        DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String pattern = "yyyy-MM-dd'T'hh:mm:ss";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern);

        String date1 = simpleDateFormat.format(new Date());
        return date1;

//        Date parsData = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        sdf.format(parsData);
//
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
//
//        SimpleDateFormat viewFormat = new SimpleDateFormat("HH:mm");
//        parseFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        viewFormat.setTimeZone(TimeZone.getTimeZone("MSK"));
//
//        try {
//          return parseFormat.format(viewFormat.parse(date));
////            return parseFormat.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return "0";
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