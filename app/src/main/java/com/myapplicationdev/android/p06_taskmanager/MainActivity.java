package com.myapplicationdev.android.p06_taskmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Task> tasks;
    ArrayAdapter<Task> adapter;
    Button btnAdd;
    int actReqCode = 1;

    @Override
    protected void onResume() {
tasks.clear();
        DBHelper dbh = new DBHelper(this);
        tasks = dbh.getAllTasks();
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, tasks);
        lv.setAdapter(adapter);
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        final DBHelper dbh = new DBHelper(this);
        tasks = dbh.getAllTasks();
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, tasks);
        lv.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i, actReqCode);
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_APN_SETTINGS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            //requesting permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_APN_SETTINGS}, 1);
        } else {
            //permission is granted and you can change APN settings
        }

lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "position is: "+ position + ", id is: " + id + ", view is: " + view.getId() , Toast.LENGTH_SHORT).show();
//        DBHelper dbh = new DBHelper(MainActivity.this);
//        dbh.deleteTask(id);
//        dbh.close();
    }
});

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //premission granted by user
                } else {
                    //permission denied by user
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == actReqCode) {
            if (resultCode == RESULT_OK) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                tasks.clear();
                tasks.addAll(dbh.getAllTasks());
                dbh.close();
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
