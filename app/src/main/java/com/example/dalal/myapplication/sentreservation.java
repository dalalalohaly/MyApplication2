package com.example.dalal.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;

public class sentreservation extends AppCompatActivity {

    ListView lv ;//where attendees names inflate
    InputStream is ;//where attendees names inflate
    String line = null;//to save what has been read from DB
    String result = null;//to convert what have been read from string builder to String
    String [] arr ;//to suplet the result in array for easy modification

    ArrayList<String> invitations ;// to save the invitations
    ArrayList<String> venueId;// to save the venueId for each invitations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentreservation);

        invitations = new ArrayList<String>();
        venueId= new ArrayList<String>();

        lv = (ListView) findViewById(R.id.sentList);

        // this line of code will fill the  list view with the content in the array
        // lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,names));

        StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // this in order to set up the code to fetch data from database

        try {
            HttpClient httpClient = new DefaultHttpClient();

            // spacifi url for the retrive

            HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Dalal/sentInv.php");
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
            result=result.replace('"',' ');
            int length =result.length();
            String sreOne =result.substring(1,length-2);//i did not start from index 0 cause the string is retreved with spaces at the beging
            // chek the data
            // chek the data
            System.out.println("*******here is my Data************");
            System.out.println(sreOne);
            arr= sreOne.split(",");
            int arrLength = arr.length ;



            for (int i=0;i<arrLength;i++)
            {
                if(i%2==0) {
                    boolean add = invitations.add(arr[i]);
                    if(add==true)
                        System.out.println("added successfully");
                }
                else if(i%2==1)
                {
                    boolean add = venueId.add(arr[i]);
                    if (add == true)
                        System.out.println("added successfully");
                }

            }


            // now fill the list view with the names
            lv.setAdapter(new ArrayAdapter<String>(sentreservation.this,android.R.layout.simple_list_item_1,invitations));

            // let provied the on click lstiener when and item is clicked

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(sentreservation.this,sentInveDetales.class);
                    intent.putExtra("venueId",venueId.get(position));
                    System.out.println(" the position : "+position);
                    startActivity(intent);

                }
            });



        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
