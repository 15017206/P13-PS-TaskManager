package com.myapplicationdev.android.p06_taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static android.R.attr.data;

public class ReplyActivity extends AppCompatActivity {

    Task data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        CharSequence reply = null;
        Intent intent = getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            reply = remoteInput.getCharSequence("status");
        }

        if (reply != null) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ReplyActivity.this);
            int id = preferences.getInt("id", 0);

            Toast.makeText(ReplyActivity.this, "You have indicated: " + reply + id, Toast.LENGTH_SHORT).show();



            if (reply.toString().toLowerCase().contains("done")) {
                Toast.makeText(ReplyActivity.this, "You've " + reply + id , Toast.LENGTH_SHORT).show();
                DBHelper dbh = new DBHelper(ReplyActivity.this);
                dbh.deleteTask(id);
                dbh.close();

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

finish();

            } else if (reply.toString().toLowerCase().contains("not yet")) {
                Toast.makeText(ReplyActivity.this, "You have " + reply + "done your task.. " + reply, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
