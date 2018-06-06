package com.logistics.android.allocate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logistics.android.BaseActivity;
import com.logistics.android.R;
import com.logistics.android.db.Car;
import com.logistics.android.db.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CarAddActivity extends BaseActivity {

    private EditText addCarType; //车型
    private EditText addCarNumber; //车牌号
    private EditText addCarStatus; //车辆状态

    private Button okButton; //确认键

    private String user_id; //用户编号
    private String addCarType_v;
    private String addCarNumber_v;
    private String addCarStatus_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allocate_car_add);
        addCarType = (EditText)findViewById(R.id.addcar_type);
        addCarNumber = (EditText)findViewById(R.id.addcar_number);
        addCarStatus = (EditText)findViewById(R.id.addcar_status);
        okButton = (Button)findViewById(R.id.ok_button);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(CarAddActivity.this, AllocateActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.addcar_status:
                final String[] arr = new String[] { "可调度", "不可调度" };
                AlertDialog.Builder builder = new AlertDialog.Builder(CarAddActivity.this);
                builder.setTitle("请选择车辆状态");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(arr, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addCarStatus.setText(arr[i]);
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                Dialog dialog = builder.create();
                builder.show();
                break;
            case R.id.ok_button:
                addCarType_v = addCarType.getText().toString();
                addCarNumber_v = addCarNumber.getText().toString();
                addCarStatus_v = addCarStatus.getText().toString();
                if("".equals(addCarType_v)) {
                    Toast.makeText(CarAddActivity.this, "车型不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(addCarNumber_v)) {
                    Toast.makeText(CarAddActivity.this, "车牌号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(addCarStatus_v)) {
                    Toast.makeText(CarAddActivity.this, "车辆状态不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Car car = new Car();
                car.setType(addCarType_v);
                car.setNumber(addCarNumber_v);
                if("可调度".equals(addCarStatus_v)) car.setStatus(0);
                else car.setStatus(1);
                List<User> userList = DataSupport.where("id = ?", user_id).find(User.class);
                User user = userList.get(0);
                car.setUser(user); //设置所属用户
                car.save();
                Toast.makeText(CarAddActivity.this, "车辆添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CarAddActivity.this, AllocateActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
                break;
        }
    }
}
