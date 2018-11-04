package com.a59070055.healthy_h1;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS sleep (_id INTEGER PRIMARY KEY AUTOINCREMENT, currentdate VARCHAR(200), timetosleep VARCHAR(200), timetowakeup VARCHAR(200), counttime VARCHAR(200))");
        setContentView(R.layout.activity_main);
       if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
        }
        }
    }

