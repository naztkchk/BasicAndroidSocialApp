package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class ProfileEditFragment extends Fragment {

    View root;
    private TextView editDone_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        initView();
        initListeners();

        return root;
    }

    private void initView() {
        editDone_tv = root.findViewById(R.id.profile_edit_done_iv);
    }

    private void initListeners() {
        editDone_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
    }

    private void saveProfile() {
        Toast.makeText(getContext(), "Your profile saved", Toast.LENGTH_SHORT).show();
    }
}
