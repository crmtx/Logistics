package com.logistics.android.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.logistics.android.BaseActivity;
import com.logistics.android.MainActivity;
import com.logistics.android.R;
import com.logistics.android.db.User;
import com.logistics.android.util.ActivityCollector;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SetupActivity extends BaseActivity {

    private TextView userName; //用户姓名
    private TextView companyName; //公司名称
    private TextView companyAddress; //公司地址

    private Button modifyInformationButton; //修改信息
    private Button quitLoginButton; //退出登录

    private String user_id; //用户编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setup);
        userName = (TextView)findViewById(R.id.username);
        companyName = (TextView)findViewById(R.id.companyname);
        companyAddress = (TextView)findViewById(R.id.address);
        modifyInformationButton = (Button)findViewById(R.id.modifyinformation_button);
        quitLoginButton = (Button)findViewById(R.id.quitlogin_button);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        List<User> userList = DataSupport.where("id = ?", user_id).find(User.class);
        User user = userList.get(0);
        userName.setText(user.getUserName());
        companyName.setText(user.getCompanyName());
        companyAddress.setText(user.getCompanyAddress());
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(SetupActivity.this, MainActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.modifyinformation_button:
                Intent modifyIntent = new Intent(SetupActivity.this, ModifyActivity.class);
                modifyIntent.putExtra("user_id", user_id);
                startActivity(modifyIntent);
                break;
            case R.id.quitlogin_button:
                ActivityCollector.finishAll();
                Intent quitIntent = new Intent(SetupActivity.this, LoginActivity.class);
                startActivity(quitIntent);
                break;
        }
    }
}
