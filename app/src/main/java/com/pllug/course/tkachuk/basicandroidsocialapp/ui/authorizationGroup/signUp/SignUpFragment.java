package com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.signUp;

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

public class SignUpFragment extends Fragment implements ISignUpView {

    private View root;
    private SignUpPresenter signUpPresenter;

    private EditText login_te;
    private EditText email_te;
    private EditText password_te;
    private EditText confirm_password_te;

    private Button signUp_btn;

    private TextView alreadyHaveAccount_tv;

    private static FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_signup, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        initView();
        initPresenter();
        initListeners();
        return root;
    }

    private void initPresenter() {
        signUpPresenter = new SignUpPresenter(this, getActivity());
    }

    private void initView() {
        login_te = root.findViewById(R.id.enter_login_te);
        email_te =  root.findViewById(R.id.enter_email_te_signup);
        password_te = root.findViewById(R.id.enter_password_te_signup);
        confirm_password_te = root.findViewById(R.id.confirm_password_te_signup);

        signUp_btn = root.findViewById(R.id.signUp_btn);

        alreadyHaveAccount_tv =root.findViewById(R.id.already_have_account);

        login_te.setError(null);
        email_te.setError(null);
        password_te.setError(null);
        confirm_password_te.setError(null);
    }

    private void initListeners() {
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = login_te.getText().toString();
                String email = email_te.getText().toString();
                String password = password_te.getText().toString();
                String confirmPassword = confirm_password_te.getText().toString();
                signUpPresenter.signUp(login, email, password, confirmPassword);
            }
        });

        alreadyHaveAccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPresenter.showSignIn();
            }
        });
    }

    @Override
    public void showLoginError() {
        login_te.setError("Please enter login");
    }

    @Override
    public void showConfirmPasswordError() {
        confirm_password_te.setError("Confirm Password != password");
    }

    @Override
    public void showEmailError() {
        email_te.setError("Please enter email");
    }

    @Override
    public void showPasswordError() {
        password_te.setError("Please enter password");
    }
}
