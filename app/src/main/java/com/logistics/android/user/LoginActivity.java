package com.logistics.android.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.logistics.android.BaseActivity;
import com.logistics.android.MainActivity;
import com.logistics.android.R;
import com.logistics.android.db.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends BaseActivity {

    private EditText loginId; //账号
    private EditText loginPassword; //密码
    private CheckBox rememberPassword; //记住密码
    private Button loginButton; //登录
    private Button registerButton; //注册

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private String id_v; //输入的账号
    private String password_v; //输入的密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        loginId = (EditText)findViewById(R.id.login_id);
        loginPassword = (EditText)findViewById(R.id.login_password);
        rememberPassword = (CheckBox)findViewById(R.id.login_rememberpassword);
        loginButton = (Button)findViewById(R.id.login_button);
        registerButton = (Button)findViewById(R.id.register_button);
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sp.edit();

        if(sp.getBoolean("remember_ischecked", false)) {
            rememberPassword.setChecked(true);
            loginId.setText(sp.getString("ID", ""));
            loginPassword.setText(sp.getString("PASSWORD", ""));
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                id_v = loginId.getText().toString();
                password_v = loginPassword.getText().toString();
                List<User> userList = DataSupport.where("userId = ? and password = ?", id_v, password_v).find(User.class);
                if(userList.size() == 0) {
                    Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = userList.get(0);
                if(rememberPassword.isChecked()) {
                    editor.putString("ID", id_v);
                    editor.putString("PASSWORD", password_v);
                    editor.commit();
                }
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                mainIntent.putExtra("user_id", user.getId()+"");
                startActivity(mainIntent);
                finish();
                break;
            case R.id.register_button:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.login_rememberpassword:
                if(rememberPassword.isChecked()) {
                    editor.putBoolean("remember_ischecked", true).commit();
                } else {
                    editor.putBoolean("remember_ischecked", false).commit();
                }
                break;
        }
    }
}
