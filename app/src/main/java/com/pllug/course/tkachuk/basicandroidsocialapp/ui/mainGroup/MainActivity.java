package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.image.ImageFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.posts.PostsFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.profiles.ProfilesFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.todo.TodoFragment;

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

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void addFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_main_container, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.nav_profile:
                replaceFragment(new ProfilesFragment());
                break;

            case R.id.nav_posts:
                replaceFragment(new PostsFragment());
                break;

            case R.id.nav_images:
                replaceFragment(new ImageFragment());
                break;

            case R.id.nav_albums:
                replaceFragment(new PostsFragment());
                break;

            case R.id.nav_todo:
                replaceFragment(new TodoFragment());
                break;

            case R.id.nav_settings:
                Toast.makeText(this, "settings choose", Toast.LENGTH_SHORT).show();
                break;
        }

        mDrawerLayout.closeDrawers();
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
