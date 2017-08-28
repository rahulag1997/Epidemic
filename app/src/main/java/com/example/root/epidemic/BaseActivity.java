package com.example.root.epidemic;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private final ViewGroup nl=null;
    private NavigationView navigationView;
    private DrawerLayout fullLayout;
    private Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        //root layout
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, nl);
        //frame layout
        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        //setting the inflated layout
        super.setContentView(fullLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        navigationView = (NavigationView) findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);

        setUpNavView();
    }

    private void setUpNavView()
    {
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                R.string.nav_drawer_opened,
                R.string.nav_drawer_closed);

        fullLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        fullLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId())
        {
            case R.id.nav_home: Toast.makeText(this,"Hello home",Toast.LENGTH_SHORT).show();
                                break;

        }


        return false;
    }
}
