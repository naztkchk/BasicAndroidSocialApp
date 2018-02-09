package com.pllug.course.tkachuk.basicandroidsocialapp.ui.authorizationGroup.forgotPassword;

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
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class ForgotPasswordFragment extends Fragment implements IForgotPasswordView {

    private View root;
    private ForgotPasswordPresenter forgotPasswordPresenter;

    private EditText email_et;
    private Button sendRecoverCode_btn;
    private TextView backToSignIn_tv;

    private static FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        initView();
        initPresenter();
        initListeners();
        return root;
    }

    private void initPresenter() {
        forgotPasswordPresenter = new ForgotPasswordPresenter(this, getActivity());
    }

    private void initView() {
        email_et = root.findViewById(R.id.enter_email_te_forgot_password);
        sendRecoverCode_btn = root.findViewById(R.id.send_recover_code_btn);
        backToSignIn_tv = root.findViewById(R.id.back_to_sign_in_tv);
     }

    private void initListeners() {
        sendRecoverCode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_et.setError(null);
                String email = email_et.getText().toString();
                forgotPasswordPresenter.sendRecoveryCode(email);
            }
        });

        backToSignIn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordPresenter.showSignIn();
            }
        });
    }

    @Override
    public void recoveryCodeSend() {
        Toast.makeText(getActivity(), "You will receive recover code!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmailError() {
        email_et.setError("Please enter email");
    }
}
