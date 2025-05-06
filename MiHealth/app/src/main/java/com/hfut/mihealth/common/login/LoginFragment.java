package com.hfut.mihealth.common.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hfut.mihealth.R;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        TextView gotoRegisterText = view.findViewById(R.id.goto_register_text);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        Button registerButton = view.findViewById(R.id.register_button);
        View loginBackButton = view.findViewById(R.id.login_back_button);
        TextView phone = view.findViewById(R.id.login_phone_input);
        TextView password = view.findViewById(R.id.login_password_input);
        loginBackButton.setOnClickListener(v -> {
            // Close the activity or perform any other action
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
        gotoRegisterText.setOnClickListener(v -> {
            if (getActivity() instanceof LoginActivity) {
                ((LoginActivity) getActivity()).switchToRegisterFragment();
            }
        });

        cancelButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        registerButton.setOnClickListener(v -> {
            if (getActivity() instanceof LoginActivity) {
                ((LoginActivity) getActivity()).login(phone.getText().toString(),password.getText().toString());
            }
        });
    }
}
