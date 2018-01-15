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

import com.pllug.course.tkachuk.basicandroidsocialapp.activity.AuthorizationActivity;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class ForgotPasswordFragment extends Fragment {


    private View root;

    private EditText email_et;
    private Button sendRecoverCode_btn;
    private TextView backToSignIn_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        initView();
        initListeners();

    return root;
    }

    private void initView() {
        email_et = (EditText) root.findViewById(R.id.enter_email_te_forgot_password);
        sendRecoverCode_btn = (Button) root.findViewById(R.id.send_recover_code_btn);
        backToSignIn_tv = (TextView) root.findViewById(R.id.back_to_sign_in_tv);
     }

    private void initListeners() {
        sendRecoverCode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        backToSignIn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthorizationActivity) getActivity()).showSignIn();
            }
        });
    }

    private void validateData() {
        email_et.setError(null);

        String email = email_et.getText().toString();
        if(email.isEmpty()){
            email_et.setError("Please enter valid email");
            return;
        }

        ((AuthorizationActivity) getActivity()).sendRecoverCode(email);
    }
}
