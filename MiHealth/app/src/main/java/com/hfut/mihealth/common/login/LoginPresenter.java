package com.hfut.mihealth.common.login;

import static java.sql.DriverManager.println;

import android.annotation.SuppressLint;

import com.hfut.mihealth.network.DTO.LoginRequest;
import com.hfut.mihealth.network.DTO.RegisterRequest;
import com.hfut.mihealth.network.UserService;
import com.hfut.mihealth.network.client.RetrofitClient;
import com.hfut.mihealth.util.SharedPreferencesHelper;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Cipher;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter {
    private LoginView view;

    public LoginPresenter(LoginActivity loginActivity) {
        this.view = loginActivity;
    }

    String publicKey;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void loadPublicKey() {
        Disposable disposable = RetrofitClient.INSTANCE.getRetrofit()
                .create(UserService.class)
                .getPublicKey() // 返回 Observable<String>
                .subscribeOn(Schedulers.io())         // 在IO线程发起网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程更新UI
                .subscribe(result -> {
                    // 处理结果
                    publicKey = result.getPublicKey();
                }, throwable -> {
                    println("throwable");
                    // 错误处理
                });

        compositeDisposable.add(disposable); // 添加到管理器中
    }

    public void onDestroy() {
        compositeDisposable.clear(); // 在不需要时清理所有订阅
    }

    public void register(String userName, String phone, String password) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(userName);
        registerRequest.setPhone(phone);
        String encryptedPassword;
        try {
            encryptedPassword = encrypt(password, publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        registerRequest.setPassword(encryptedPassword);

        Disposable register =
                RetrofitClient.INSTANCE.getRetrofit()
                        .create(UserService.class)
                        .register(registerRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            // 处理结果
                            if (Objects.equals(result.getSuccess(), "success")) {
                                view.registerSuccess("注册成功");
                            }
                        }, throwable -> {
                            println("throwable");
                            // 错误处理
                            view.loginFail(throwable.getMessage());
                        });
        compositeDisposable.add(register);
    }

    public void login(String phone, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone(phone);
        String encryptedPassword;
        try {
            encryptedPassword = encrypt(password, publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        loginRequest.setPassword(encryptedPassword);
        Disposable loginDisposable = RetrofitClient.INSTANCE.getRetrofit()
                .create(UserService.class)
                .login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    // 处理结果
                    println(result.getSuccess());
                    if (Objects.equals(result.getSuccess(), "success")) {
                        view.loginSuccess("登录成功", result.getToken(), result.getUsername());
                    } else {
                        view.loginFail("登录失败,请重新输入电话或密码");
                    }
                }, throwable -> {
                    println("throwable");
                    // 错误处理
                    view.loginFail(throwable.getMessage());
                });
        compositeDisposable.add(loginDisposable);
    }

    /**
     * 使用给定的RSA公钥加密数据。
     *
     * @param data         要加密的数据
     * @param publicKeyStr Base64编码的公钥字符串
     * @return 加密后的Base64编码字符串
     * @throws Exception 如果加密过程中出现错误
     */
    @SuppressLint("NewApi")
    public static String encrypt(String data, String publicKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}