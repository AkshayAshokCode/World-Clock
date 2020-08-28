package com.example.worldclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView date, time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        time=findViewById(R.id.textDate);
        date=findViewById(R.id.textTime);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        String Date=b.getString("Date");
        String Time=b.getString("Time");
        date.setText(Date);
        int hr=b.getInt("hr");
        if(hr<12){
            String T=Time+" AM";
            time.setText(T);
        }else
        {
            String T=Time+" PM";
            time.setText(T);
        }


    }
}