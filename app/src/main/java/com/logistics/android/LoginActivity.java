package com.logistics.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.logistics.android.db.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends BaseActivity {

    private EditText loginId;
    private EditText loginPassword;
    private CheckBox rememberPassword;
    private CheckBox autoLogin;
    private Button loginButton;
    private Button registerButton;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private String id_v;
    private String password_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginId = (EditText)findViewById(R.id.login_id);
        loginPassword = (EditText)findViewById(R.id.login_password);
        rememberPassword = (CheckBox)findViewById(R.id.login_rememberpassword);
        autoLogin = (CheckBox)findViewById(R.id.login_autologin);
        loginButton = (Button)findViewById(R.id.login_button);
        registerButton = (Button)findViewById(R.id.register_button);
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sp.edit();

        if(sp.getBoolean("remember_ischecked", false)) {
            rememberPassword.setChecked(true);
            loginId.setText(sp.getString("ID", ""));
            loginPassword.setText(sp.getString("PASSWORD", ""));
            if(sp.getBoolean("auto_ischecked", false)) {
                autoLogin.setChecked(true);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                id_v = loginId.getText().toString();
                password_v = loginPassword.getText().toString();
                List<User> users = DataSupport.where("userId = ? and password = ?", id_v, password_v).find(User.class);
                if(users.size() == 0) {
                    Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(rememberPassword.isChecked()) {
                    editor.putString("ID", id_v);
                    editor.putString("PASSWORD", password_v);
                    editor.commit();
                }
                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(loginIntent);
                finish();
                break;
            case R.id.register_button:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.login_rememberpassword:
                if(rememberPassword.isChecked()) {
                    editor.putBoolean("remember_ischecked", true).commit();
                } else {
                    editor.putBoolean("remember_ischecked", false).commit();
                }
                break;
            case R.id.login_autologin:
                if(autoLogin.isChecked()) {
                    editor.putBoolean("auto_ischecked", true).commit();
                } else {
                    editor.putBoolean("auto_ischecked", false).commit();
                }
                break;
        }
    }
}
