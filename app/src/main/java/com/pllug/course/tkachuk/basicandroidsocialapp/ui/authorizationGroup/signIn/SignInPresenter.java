package com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.signIn;

import android.app.Activity;
import android.text.TextUtils;

import com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.IAuthorizationView;


public class SignInPresenter implements ISignInPresenter{

    private ISignInView view;
    private IAuthorizationView authorizationView;

    public SignInPresenter(ISignInView view, Activity activity) {
        this.view = view;
        authorizationView = (IAuthorizationView) activity;
    }

    @Override
    public void signIn(final String email, final String password) {
        if(validateInputEmail(email) && validateInputPassword(password))
            authorizationView.openMainActivity();
    }

    private boolean validateInputEmail(final String email){
        if(TextUtils.isEmpty(email)) {
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

    public void showForgotPassword() {
        authorizationView.showForgotPassword();
    }

    public void showSignUp() {
        authorizationView.showSignUp();
    }
}
