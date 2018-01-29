package com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.signUp;

public interface ISignUpPresenter {

    void signUp (final String login, final String email, final String password,
                 final String confirmPassword);
}
