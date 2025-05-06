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

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Find views by ID
        LinearLayout backToLoginLayout = view.findViewById(R.id.back_to_login);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        Button registerButton = view.findViewById(R.id.register_button);
        TextView userName = view.findViewById(R.id.username_input);
        TextView phoneNumber = view.findViewById(R.id.phone_input);
        TextView password = view.findViewById(R.id.password_input);

        backToLoginLayout.setOnClickListener(v -> {
            if (getActivity() instanceof LoginActivity) {
                ((LoginActivity) getActivity()).switchToLoginFragment();
            }
        });

        cancelButton.setOnClickListener(v -> {
            if (getActivity() instanceof LoginActivity) {
                ((LoginActivity) getActivity()).switchToLoginFragment();
            }
        });

        registerButton.setOnClickListener(v -> {
            if (getActivity() instanceof LoginActivity) {
                ((LoginActivity) getActivity()).register(userName.getText().toString(),
                        phoneNumber.getText().toString(),
                        password.getText().toString());
            }
        });

        return view;
    }
}
