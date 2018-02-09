package com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.signUp;

import android.app.Activity;
import android.text.TextUtils;

import com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.IAuthorizationView;

public class SignUpPresenter implements ISignUpPresenter {

    private ISignUpView view;
    private IAuthorizationView authorizationView;


    public SignUpPresenter(ISignUpView view, Activity activity) {
        this.view = view;
        authorizationView = (IAuthorizationView) activity;
    }

    public void signUp(final String login, final String email, final String password,
                       final String confirmPassword) {

        if((validateInputLogin(login) && validateInputEmail(email)) &&
        (validateInputPassword(password) && validateConfirmPassword(password, confirmPassword))){
            //Doing signUp-logic new user
            authorizationView.signUp();
        }
    }

    public void showSignIn() {
        authorizationView.showSignIn();
    }


    private boolean validateInputLogin (final String login){
        if(TextUtils.isEmpty(login)) {
            view.showLoginError();
            return false;
        }
        else return true;
    }

    private boolean validateInputEmail(final String email){
        if(TextUtils.isEmpty(email)){
            view.showEmailError();
            return false;
        }
        else return true;
    }

    private boolean validateInputPassword(final String password){
        if(TextUtils.isEmpty(password)){
            view.showPasswordError();
            return false;
        }
        else return true;
    }

    private boolean validateConfirmPassword(final String password, final String confirmPassword){
        if(!(TextUtils.equals(password, confirmPassword))){
            view.showConfirmPasswordError();
            return false;
        }
        else return true;
    }
}
