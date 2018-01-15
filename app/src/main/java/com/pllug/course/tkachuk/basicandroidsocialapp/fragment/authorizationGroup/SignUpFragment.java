package com.pllug.course.tkachuk.basicandroidsocialapp.fragment.authorizationGroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.activity.AuthorizationActivity;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class SignUpFragment extends Fragment {

    private View root;

    private EditText login_te;
    private EditText email_te;
    private EditText password_te;
    private EditText confirm_password_te;

    private Button signUp_btn;

    private TextView alreadyHaveAccount_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_signup, container, false);

        initView();
        initListeners();
        return root;
    }

    private void initView() {
        login_te = (EditText) root.findViewById(R.id.enter_login_te);
        email_te = (EditText) root.findViewById(R.id.enter_email_te_signup);
        password_te = (EditText) root.findViewById(R.id.enter_password_te_signup);
        confirm_password_te = (EditText) root.findViewById(R.id.confirm_password_te_signup);

        signUp_btn =  (Button) root.findViewById(R.id.signUp_btn);

        alreadyHaveAccount_tv = (TextView) root.findViewById(R.id.already_have_account);
    }

    private void initListeners() {
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });

        alreadyHaveAccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthorizationActivity) getActivity()).showSignIn();
            }
        });
    }

    private void validateInput() {
        login_te.setError(null);
        email_te.setError(null);
        password_te.setError(null);
        confirm_password_te.setError(null);

        String login = login_te.getText().toString();
        String email = email_te.getText().toString();
        String password = password_te.getText().toString();
        String confirmPassword = confirm_password_te.getText().toString();

        if(login.isEmpty()){
            login_te.setError("Please enter login!");
            return;
        }

        if(email.isEmpty()){
            email_te.setError("Please enter email!");
            return;
        }

        if(password.isEmpty()){
            password_te.setError("Please enter password!");
            return;
        }

        if(confirmPassword.isEmpty()){
            confirm_password_te.setError("Please enter confirm password!");
            return;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(getActivity(), "Passwords aren't equal!", Toast.LENGTH_SHORT).show();
            return;
        }

        ((AuthorizationActivity) getActivity()).signUp(email, login, password);
    }
}
