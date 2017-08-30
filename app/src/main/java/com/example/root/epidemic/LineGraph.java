package com.example.root.epidemic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class LineGraph extends BaseActivity
{
    String[] types;
    String url;
    private WebView grview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        //Hide FAB
        ((FloatingActionButton)findViewById(R.id.fab)).hide();
        grview = (WebView) findViewById(R.id.graph);
        grview.getSettings().setJavaScriptEnabled(true);

        types = getResources().getStringArray(R.array.types);
        Spinner spinner = (Spinner)findViewById(R.id.spin_graphview);
        ArrayAdapter<String> madpater = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,types);
        spinner.setAdapter(madpater);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                switch (i)
                {
                    case 0:  url = "file:///android_asset/loadLineGraph.html";
                        setURL();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });
    }
    private void setURL()
    {
        ConnectivityManager cm =(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            Toast.makeText(this,"You need to be connected to internet to submit a new request",Toast.LENGTH_SHORT).show();
            return;
        }
        grview.loadUrl(url);
    }
}
