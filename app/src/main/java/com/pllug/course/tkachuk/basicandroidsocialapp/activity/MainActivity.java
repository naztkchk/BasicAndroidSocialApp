package com.pllug.course.tkachuk.basicandroidsocialapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener
{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private Toolbar mToolbar;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListeners();

        //set new Action Bar
        setSupportActionBar(mToolbar);

        //DrawerToggle
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        //Add Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.nav_profile:
                Toast.makeText(this, "profile choose", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_posts:
                Toast.makeText(this, "posts choose", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_images:
                intent = new Intent(this, ImageActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_albums:
                intent = new Intent(this, AlbumActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settings:
                Toast.makeText(this, "settings choose", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_about:
                Toast.makeText(this, "about choose", Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Add click to Home button
        if(mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar = (Toolbar) findViewById(R.id.nav_action_bar);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
    }

    private void initListeners() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }
}
