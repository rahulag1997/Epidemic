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
        grview.getSettings().setJavaScriptEnabled(true);
        grview.loadUrl("file:///android_asset/loadBarGraph.html");
    }
}
