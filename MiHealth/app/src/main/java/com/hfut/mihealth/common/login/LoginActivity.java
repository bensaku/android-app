package com.hfut.mihealth.common.login;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.hfut.mihealth.R;
import com.hfut.mihealth.common.MyApplication;
import com.hfut.mihealth.common.mainPage.viewmodel.MainViewModel;
import com.hfut.mihealth.network.client.AuthInterceptor;
import com.hfut.mihealth.util.SharedPreferencesHelper;

public class LoginActivity extends AppCompatActivity implements LoginView {
    LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
        init();
    }

    private void init() {
        presenter.loadPublicKey();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public void switchToRegisterFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new RegisterFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void switchToLoginFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new LoginFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void register(String userName, String phone, String password) {
        presenter.register(userName, phone, password);
    }

    public void login(String phone, String password) {
        presenter.login(phone, password);
    }

    @Override
    public void loginFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(String message, String token, String username) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(this);
        sharedPreferencesHelper.saveTokenAndUserId(
                token,
                username
        );
        AuthInterceptor.INSTANCE.setToken(token);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        MyApplication app = (MyApplication) getApplication();
        MainViewModel viewModel = app.getAppViewModel();
        viewModel.setUsername(username);
        finish();
    }

    @Override
    public void registerSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}