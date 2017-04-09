package com.example.dalal.myapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class sentInveDetales extends AppCompatActivity {

   public ListView  lv ;//where attendees names inflate
   public ListView  appsentlv ;//where apsent names inflate
   public ListView  suggestionlv ;//where attendees names inflate
   public InputStream is ;//where attendees names inflate
   public String line = null;//to save what has been read from DB
   public String result = null;//to convert what have been read from string builder to String
   public String [] arr ;//to suplet the result in array for easy modification
   public String value ="1" ;// to save the venue Id for the where condition in php file

    public int numOfSuggestions ;// to save the number Of Suggestions
    public int numOfPresent ;// to save the number Of Present
    public int numOfAppsents ;// to save the  number Of Appsents

    public ArrayList<String> Suggestions ; // to save the Suggestions and the suggester
    public ArrayList<String> Present ;     // to save the  Present Guest's name
    public ArrayList<String> Appsents ;    // to save the  Appsents Guest's name


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_inve_detales);

        //initializations
        TextView presentTextView = (TextView) findViewById(R.id.textView4);
        TextView AppsentTextView = (TextView) findViewById(R.id.textView5);
        TextView suggestionTextView = (TextView) findViewById(R.id.textView7);


        lv = (ListView) findViewById(R.id.plist);
        appsentlv = (ListView) findViewById(R.id.apsentlist);
        suggestionlv = (ListView) findViewById(R.id.suggestionList);
        Suggestions = new ArrayList<String>();
        Present     = new ArrayList<String>();
        Appsents    = new ArrayList<String>();

      //retrieve the value of the venueID
        String venueId = (getIntent().getExtras().getString("venueId"));
        System.out.println("the venueId: "+venueId);

        // this is important to connect to the internet <not sure>
        StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // this in order to set up the code to fetch data from database
        try {
            HttpClient httpClient = new DefaultHttpClient();
            // specific url for the retrieve
            HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Dalal/sentInvDeatales.php?value="+value);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            // set up the input stream to receive the data
            is = entity.getContent();
        }//exception handel code
        catch (Exception e)
        {System.out.print("exception 1 caught");}

        //read the retrieved data
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);

            // create String builder object to hold the data
            StringBuilder sb = new StringBuilder();
            while ((line=reader.readLine())!=null)
                sb.append(line+"\n");

            result=sb.toString();
            // chek the data
            System.out.println("*******here is my Data************");
            System.out.print(result);

            int lengthResult =result.length();
            String sreOne =result.substring(1,lengthResult-2);//i did not start from index 0 cause the string is retreved with spaces at the beging
            result=sreOne.replace('"',' ');
            arr= result.split(",");




//*********************************************split the contant of the retrieved Data********************************
            int length = arr.length ;
            int i=0 ;
            while (i< length)
            {
              if(arr[i+1].contains("coming"))
              {Present.add(arr[i]);}//to add the name

              else if(arr[i+1].contains("notComing"))
               {Appsents.add(arr[i]);} //to add the name

              else if(arr[i+1].contains("suggestion"))
              {Suggestions.add(arr[i]+": "+arr[i+2]);} //to add the name
                i=i+3;
            }

            System.out.println("the coming guests : ");

            for (int a=0;a<Present.size();a++)
            {

                System.out.println(Present.get(a));

            }

            System.out.println("the not coming guests : ");

            for (int a=0;a<Appsents.size();a++)
            {

                System.out.println(Appsents.get(a));

            }

            System.out.println("the Suggestions  : ");

            for (int a=0;a<Suggestions.size();a++)
            {

                System.out.println(Suggestions.get(a));

            }
            System.out.println();

            // now fill the list view with the names

            presentTextView.setText("الحاضرين : "+Present.size());
            AppsentTextView.setText("الغير حاضريـن"+Appsents.size());
            suggestionTextView.setText("الإقتراحــات"+Suggestions.size());

            lv.setAdapter(new ArrayAdapter<String>(sentInveDetales.this,android.R.layout.simple_list_item_1,Present));
            appsentlv.setAdapter(new ArrayAdapter<String>(sentInveDetales.this,android.R.layout.simple_list_item_1,Appsents));
            suggestionlv.setAdapter(new ArrayAdapter<String>(sentInveDetales.this,android.R.layout.simple_list_item_1,Suggestions));
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }


}
