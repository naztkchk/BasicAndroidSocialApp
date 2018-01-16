package com.pllug.course.tkachuk.basicandroidsocialapp.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.fragment.AboutFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.fragment.authorizationGroup.ForgotPasswordFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.fragment.mainScreenGroup.profile.ProfilesFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.fragment.authorizationGroup.SignInFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.fragment.authorizationGroup.SignUpFragment;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        showSignIn();
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_authorization_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void addFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_authorization_container, fragment)
                .commit();
    }

    public void showSignIn() {
        replaceFragment(new SignInFragment());
    }

    public void showSignUp(){
        replaceFragment(new SignUpFragment());
    }

    public void showForgotPassword(){
        replaceFragment(new ForgotPasswordFragment());
    }

    public void showAbout(){
        replaceFragment(new AboutFragment());
    }

    public void signIn(String email, String password) {
        Toast.makeText(this, "You are logged in!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void signUp(String email, String login, String password) {
        Toast.makeText(this, "You are signed up!", Toast.LENGTH_SHORT).show();
    }

    public void sendRecoverCode (String email) {
        Toast.makeText(this, "You will receive recover code!", Toast.LENGTH_SHORT).show();
    }

}
