package com.example.worldclock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Spinner Continent,City;
    ArrayAdapter<String> adapter, adapterCity;
    String con;
    Button Check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Continent=findViewById(R.id.spinner1);
        City=findViewById(R.id.spinner2);
        Check=findViewById(R.id.button);
        adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.continent_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Continent.setAdapter(adapter);
        Continent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item=parent.getItemAtPosition(position);
                con = item.toString();
                Log.e("con", con);
                switch (position){
                    case 0:adapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Africa));
                    break;
                    case 1:adapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.America));
                        break;
                    case 2:adapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Antarctica));
                        break;
                    case 3:adapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Asia));
                        break;
                    case 4:adapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Australia));
                        break;
                    case 5:adapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Europe));
                        break;
                   default:adapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Africa));
                }
                adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                City.setAdapter(adapterCity);
                City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item=parent.getItemAtPosition(position);
                        String x = item.toString();
                        String URL="http://worldtimeapi.org/api/timezone/"+con+"/"+x;
                        showDateTime(URL);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showDateTime(String URL){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String dateTime=response.getString("datetime");
                     final String Time=dateTime.substring(11,16);
                    String hour=Time.substring(0,2);
                    int hr=Integer.parseInt(hour);
                    final int x=hr;
                    if(hr>12) {
                        hr =(hr - 12);
                    }
                    final String time=String.valueOf(hr)+Time.substring(2,5);
                    String year=dateTime.substring(0,4);
                    String month=dateTime.substring(4,8);
                    String day=dateTime.substring(8,10);
                    final String Date=day+month+year;
                    Log.e("myResponse",Date);
                    Log.e("myResponse",time);
                    Check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(x>6 && x<19) {
                                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                                i.putExtra("Date", Date);
                                i.putExtra("Time", time);
                                i.putExtra("hr", x);
                                startActivity(i);
                            }
                            else{
                                Intent i = new Intent(MainActivity.this, MainActivity3.class);
                                i.putExtra("Date", Date);
                                i.putExtra("Time", time);
                                i.putExtra("hr", x);
                                startActivity(i);
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("myResponse",error.toString());
            }
        });
        requestQueue.add(objectRequest);
    }
}