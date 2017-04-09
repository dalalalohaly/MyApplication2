package com.example.dalal.myapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class invitatonssaved extends AppCompatActivity {
    ListView lv ;
    String [] names = {"Amy","John","joseph","Carl"};
    InputStream is ;
    String line = null;
    String result = null;
    String temp ="";
    String [] arr ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitatonssaved);

        lv = (ListView) findViewById(R.id.list);

        // this line of code will fill the  list view with the content in the array
        // lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,names));

        StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // this in order to set up the code to fetch data from database

        try {
            HttpClient httpClient = new DefaultHttpClient();

            // spacifi url for the retrive

            HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Dalal/retrive.php");
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            // set up the input stream to receive the data

            is = entity.getContent();


        }catch (Exception e){
            System.out.print("exception 1 caught");
            //exception handel code
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);

            // creat String builder object to hold the data

            StringBuilder sb = new StringBuilder();
            while ((line=reader.readLine())!=null)
                sb.append(line+"\n");

            //use toString() to get the data result
            result=sb.toString();
            // chek the data
            System.out.print("*******here is my Data************");
            System.out.print(result);
            arr= result.split(",");
            // now fill the list view with the names
            lv.setAdapter(new ArrayAdapter<String>(invitatonssaved.this,android.R.layout.simple_list_item_1,arr));



        }  catch (IOException e) {
            e.printStackTrace();
        }

    }
}
