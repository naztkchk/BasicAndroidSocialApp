package com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.signIn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class SignInFragment extends Fragment implements ISignInView {

    private View root;
    private SignInPresenter signInPresenter;

    private EditText email_et;
    private EditText password_et;

    private Button signIn_btn;

    private TextView forgotPassword_tv;
    private TextView createAccount_tv;

    private static FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_signin, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        initView();
        initPresenter();
        initListeners();

        return root;
    }

    private void initPresenter() {
        signInPresenter = new SignInPresenter(this, getActivity());
    }

    private void initView() {
        email_et = root.findViewById(R.id.enter_email_te_signin);
        password_et = root.findViewById(R.id.enter_password_te_signin);

        signIn_btn = root.findViewById(R.id.signIn_btn);

        forgotPassword_tv = root.findViewById(R.id.forgot_password_tv);
        createAccount_tv = root.findViewById(R.id.signup_tv);
    }

    private void initListeners() {

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();
                signInPresenter.signIn(email, password);
            }
        });

        forgotPassword_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInPresenter.showForgotPassword();
            }
        });

        createAccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInPresenter.showSignUp();
            }
        });
    }

    @Override
    public void showEmailError() {
        email_et.setError("Please enter email");
    }

    @Override
    public void showPasswordError() {
        password_et.setError("Please enter password");
    }
}
