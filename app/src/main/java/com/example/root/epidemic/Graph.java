package com.example.root.epidemic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class Graph extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        WebView grview = (WebView) findViewById(R.id.graph);
        ConnectivityManager cm =(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            Toast.makeText(this,"You need to be connected to internet to submit a new request",Toast.LENGTH_SHORT).show();
            return;
        }
        grview.loadUrl("https://docs.google.com/spreadsheets/d/e/2PACX-1vRcAXlEyLzL9vulgw4JlSQODV52rLG4rQLr0v9FRhyvJJqvi7xfa62a7MGDyrTp-yFd97EK9amd_QKq/pubchart?oid=234498120&format=interactive");
    }
}
