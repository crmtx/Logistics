package com.logistics.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logistics.android.db.User;
import com.logistics.android.util.ActivityCollector;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends BaseActivity {

    private EditText id;
    private EditText password;
    private EditText password2;
    private EditText userName;
    private EditText phone;
    private EditText companyName;
    private EditText companyAddress;

    private Button okButton;

    private String id_v;
    private String password_v;
    private String password2_v;
    private String userName_v;
    private String phone_v;
    private String companyName_v;
    private String companyAddress_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        id = (EditText)findViewById(R.id.register_id);
        password = (EditText)findViewById(R.id.register_password);
        password2 = (EditText)findViewById(R.id.register_password2);
        userName = (EditText)findViewById(R.id.register_username);
        phone = (EditText)findViewById(R.id.register_phone);
        companyName = (EditText)findViewById(R.id.register_companyname);
        companyAddress = (EditText)findViewById(R.id.register_companyaddress);
        okButton = (Button)findViewById(R.id.ok_button);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.ok_button:
                id_v = id.getText().toString();
                password_v = password.getText().toString();
                password2_v = password2.getText().toString();
                if("".equals(id_v)) {
                    Toast.makeText(RegisterActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(password_v)) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(password2_v)) {
                    Toast.makeText(RegisterActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password_v.equals(password2_v)) {
                    Toast.makeText(RegisterActivity.this, "密码与确认密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> users = DataSupport.where("userId = ?", id_v).find(User.class);
                if(users.size() != 0) {
                    Toast.makeText(RegisterActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                userName_v = userName.getText().toString();
                phone_v = phone.getText().toString();
                companyName_v = companyName.getText().toString();
                companyAddress_v = companyAddress.getText().toString();
                User user = new User();
                user.setUserId(id_v);
                user.setPassword(password_v);
                user.setUserName(userName_v);
                user.setPhone(phone_v);
                user.setCompanyName(companyName_v);
                user.setCompanyAddress(companyAddress_v);
                user.save();

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("恭喜");
                builder.setMessage("注册成功！！！");
                builder.setCancelable(false);
                builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();

                break;
        }
    }
}
