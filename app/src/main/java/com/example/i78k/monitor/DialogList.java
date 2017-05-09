package com.example.i78k.monitor;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i78k.monitor.model.Room;

import java.util.List;

public class DialogList extends DialogFragment {
    TextView tv;
    String text ;
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
            Toast toast = Toast.makeText(getActivity(),"Отправлено", Toast.LENGTH_SHORT);
                toast.show();
        }
    })
            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            Toast toast = Toast.makeText(getActivity(),"Отмена", Toast.LENGTH_SHORT);
                toast.show();
        }
    });

        return builder.create();
        }

    public View onCreateView() {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View V = inflater.inflate(R.layout.dialog, null, false);
        tv = (TextView)V.findViewById(R.id.textD);
        tv.setText(text);
        return V;
    }
        public void list_pri(List<Room> rooms, int id){
            text =  rooms.get(id).sTiming;
            idRoom = Integer.toString(id);
            roomeName =  rooms.get(id).Name;

        }

   }