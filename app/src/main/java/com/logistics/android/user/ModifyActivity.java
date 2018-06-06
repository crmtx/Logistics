package com.logistics.android.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.logistics.android.BaseActivity;
import com.logistics.android.MainActivity;
import com.logistics.android.R;
import com.logistics.android.db.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ModifyActivity extends BaseActivity {

    private TextView id; //账号
    private EditText passwordOld; //旧密码
    private EditText passwordNew; //新密码
    private EditText passwordNew2; //确认密码
    private EditText userName; //用户姓名
    private EditText phone; //联系电话
    private EditText companyName; //公司名称
    private EditText companyAddress; //公司地址

    private Button okButton; //确认键

    private String user_id; //用户编号
    private String id_v;
    private String passwordold_v;
    private String passwordnew_v;
    private String passwordnew2_v;
    private String userName_v;
    private String phone_v;
    private String companyName_v;
    private String companyAddress_v;

    List<User> userList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_modify);
        id = (TextView)findViewById(R.id.modify_id);
        passwordOld = (EditText)findViewById(R.id.modify_passwordold);
        passwordNew = (EditText)findViewById(R.id.modify_passwordnew);
        passwordNew2 = (EditText)findViewById(R.id.modify_passwordnew2);
        userName = (EditText)findViewById(R.id.modify_username);
        phone = (EditText)findViewById(R.id.modify_phone);
        companyName = (EditText)findViewById(R.id.modify_companyname);
        companyAddress = (EditText)findViewById(R.id.modify_companyaddress);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        userList = DataSupport.where("id = ?", user_id).find(User.class);
        user = userList.get(0);
        id.setText(user.getUserId());
        userName.setText(user.getUserName());
        phone.setText(user.getPhone());
        companyName.setText(user.getCompanyName());
        companyAddress.setText(user.getCompanyAddress());
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(ModifyActivity.this, SetupActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.ok_button:
                id_v = id.getText().toString();
                passwordold_v = passwordOld.getText().toString();
                passwordnew_v = passwordNew.getText().toString();
                passwordnew2_v = passwordNew2.getText().toString();
                userName_v = userName.getText().toString();
                phone_v = phone.getText().toString();
                companyName_v = companyName.getText().toString();
                companyAddress_v = companyAddress.getText().toString();

                if(!passwordold_v.equals(user.getPassword())) {
                    Toast.makeText(ModifyActivity.this, "密码不正确无法修改信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user2 =  new User();
                if(!"".equals(passwordnew_v) || !"".equals(passwordnew2_v)) {
                    if(!passwordnew_v.equals(passwordnew2_v)) {
                        Toast.makeText(ModifyActivity.this, "新密码与确认密码不一致", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        user2.setPassword(passwordnew_v);
                    }
                }
                user2.setUserName(userName_v);
                user2.setPhone(phone_v);
                user2.setCompanyName(companyName_v);
                user2.setCompanyAddress(companyAddress_v);
                user2.updateAll("id = ?", user_id);
                Toast.makeText(ModifyActivity.this, "信息修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ModifyActivity.this, MainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                break;
        }
    }
}
