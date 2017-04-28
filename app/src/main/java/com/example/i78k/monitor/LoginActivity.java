package com.example.i78k.monitor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.i78k.monitor.soap.WebServiceCommunication;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
    }

    public void onClickLogin(View view) {
        new LoginTask(this).execute(login.getText().toString(), password.getText().toString());
    }

    private void onLoginFinished(Pair<Boolean, String> result) {
        Toast.makeText(this, result.second, Toast.LENGTH_LONG).show();
        if (result.first){
            Intent intent = new Intent(this, TimeListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, result.second, Toast.LENGTH_LONG).show();
        }
    }

    private class LoginTask extends AsyncTask<String, Void, Pair<Boolean, String>> {

        private WebServiceCommunication wsCommunication;
        WeakReference<LoginActivity> weakActivity;

        public LoginTask(LoginActivity weakActivity) {
            this.weakActivity = new WeakReference<>(weakActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsCommunication = new WebServiceCommunication();
        }
        @Override
        protected Pair<Boolean, String> doInBackground(String... params) {
            return wsCommunication.login(params[0], params[1]);
        }
        @Override
        protected void onPostExecute(Pair<Boolean, String> result) {
            super.onPostExecute(result);
            if (weakActivity.get() != null) {
                weakActivity.get().onLoginFinished(result);
            }
        }
    }
}
