package com.example.i78k.monitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText login;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

    }

   public void onClickLogin(View view){
       String massage = "Кнопка нажата пользователем" + login.getText();
       Toast.makeText(this,massage,Toast.LENGTH_LONG).show();
       // TODO ask server for  login
       Intent intent = new Intent(this, TimeListActivity.class);
       startActivity(intent);
   }
}
