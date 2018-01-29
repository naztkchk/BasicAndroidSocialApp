package com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.forgotPassword;

import android.app.Activity;
import android.text.TextUtils;

import com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.AuthorizationActivity;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.IAuthorizationView;

public class ForgotPasswordPresenter extends AuthorizationActivity implements IForgotPasswordPresenter {

    private IForgotPasswordView view;
    private IAuthorizationView authorizationView;

    public ForgotPasswordPresenter(IForgotPasswordView view, Activity activity){
        this.view = view;
        authorizationView = (IAuthorizationView) activity;
    }

    public void sendRecoveryCode(String email) {
        if(isValidEmail(email)){
            view.recoveryCodeSend();
        }
        else{
            view.showEmailError();
        }
    }

    public void showSignIn (){
        authorizationView.showSignIn();
    }

    private boolean isValidEmail(final String email){
        if(TextUtils.isEmpty(email))
            return false;
        else
            return true;
    }
}
