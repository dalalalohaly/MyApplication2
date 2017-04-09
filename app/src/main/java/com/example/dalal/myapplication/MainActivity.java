package com.example.dalal.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void Go (View view)
    {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }


    public void saved(View view)
    {
        Intent intent = new Intent(MainActivity.this,invitatonssaved.class);
        startActivity(intent);
    }


    public void create(View view)
    {
        Intent intent = new Intent(MainActivity.this,invititioncretion.class);
        startActivity(intent);
    }


    public void send(View view)
    {
        Intent intent = new Intent(MainActivity.this,sentreservation.class);
        startActivity(intent);
    }

}
