package com.hfut.mihealth.common.login;

public interface LoginView {
    void loginFail(String message);

    void loginSuccess(String message, String token, String username);

    void registerSuccess(String message);

    void registerFail(String message);
}
