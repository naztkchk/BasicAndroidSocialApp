package com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.forgotPassword.ForgotPasswordFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.signIn.*;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.signUp.SignUpFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.MainActivity;

public class AuthorizationActivity extends AppCompatActivity implements IAuthorizationView{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_authorization_container, new SignInFragment())
                .addToBackStack(null);
                fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_authorization_container, fragment)
                .addToBackStack(null);
        if(!this.isDestroyed()){
                fragmentTransaction.commit();
        }
    }
    public void showSignIn() {replaceFragment(new SignInFragment());}

    public void showSignUp(){replaceFragment(new SignUpFragment());}

    public void showForgotPassword(){replaceFragment(new ForgotPasswordFragment());}


    public void signUp(){
        Toast.makeText(this, "You are sign Up!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
