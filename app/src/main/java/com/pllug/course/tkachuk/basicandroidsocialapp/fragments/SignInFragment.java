package com.pllug.course.tkachuk.basicandroidsocialapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pllug.course.tkachuk.basicandroidsocialapp.AuthorizationActivity;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class SignInFragment extends Fragment {

    private View root;

    private EditText email_et;
    private EditText password_et;

    private Button signIn_btn;

    private TextView forgotPassword_tv;
    private TextView createAccount_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_signin, container, false);

        initView();
        initListeners();
    return root;
    }

    private void initView() {
        email_et = (EditText) root.findViewById(R.id.enter_email_te_signin);
        password_et = (EditText) root.findViewById(R.id.enter_password_te_signin);

        signIn_btn = (Button) root.findViewById(R.id.signIn_btn);

        forgotPassword_tv = (TextView) root.findViewById(R.id.forgot_password_tv);
        createAccount_tv = (TextView) root.findViewById(R.id.signup_tv);
    }

    private void initListeners() {

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });

        forgotPassword_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthorizationActivity) getActivity()).showForgotPassword();
            }
        });

        createAccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthorizationActivity) getActivity()).showSignUp();
            }
        });
    }

    private void validateInput() {
        email_et.setError(null);
        password_et.setError(null);

        String email = email_et.getText().toString();
        String password = password_et.getText().toString();

        if(email.isEmpty()){
            email_et.setError("Please enter login");
            return;
        }

        if(password.isEmpty()){
            password_et.setError("Please enter password");
            return;
        }

        ((AuthorizationActivity) getActivity()).signIn(email, password);
    }
}
