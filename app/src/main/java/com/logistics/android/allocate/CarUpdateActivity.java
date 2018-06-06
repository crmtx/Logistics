package com.logistics.android.allocate;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logistics.android.BaseActivity;
import com.logistics.android.R;
import com.logistics.android.db.Car;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CarUpdateActivity extends BaseActivity {

    private EditText updateCarType; //车型
    private EditText updateCarNumber; //车牌号
    private EditText updateCarStatus; //车辆状态

    private Button okButton; //确认键
    private Button deleteButton; //删除车辆键

    private String user_id; //用户编号
    private String car_id; //车辆编号
    private String updateCarType_v;
    private String updateCarNumber_v;
    private String updateCarStatus_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allocate_car_update);
        updateCarType = (EditText)findViewById(R.id.updatecar_type);
        updateCarNumber = (EditText)findViewById(R.id.updatecar_number);
        updateCarStatus = (EditText)findViewById(R.id.updatecar_status);
        okButton = (Button)findViewById(R.id.ok_button);
        deleteButton = (Button)findViewById(R.id.delete_button);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        car_id = intent.getStringExtra("car_id");
        List<Car> carList = DataSupport.where("id = ?", car_id).find(Car.class);
        Car car = carList.get(0);
        updateCarType.setText(car.getType());
        updateCarNumber.setText(car.getNumber()+"");
        if(car.getStatus() == 0) updateCarStatus.setText("可调度");
        else updateCarStatus.setText("不可调度");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(CarUpdateActivity.this, AllocateActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.ok_button:
                updateCarType_v = updateCarType.getText().toString();
                updateCarNumber_v = updateCarNumber.getText().toString();
                updateCarStatus_v = updateCarStatus.getText().toString();
                if("".equals(updateCarType_v)) {
                    Toast.makeText(CarUpdateActivity.this, "车型不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(updateCarNumber_v)) {
                    Toast.makeText(CarUpdateActivity.this, "车牌号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(updateCarStatus_v)) {
                    Toast.makeText(CarUpdateActivity.this, "车辆状态不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Car car = new Car();
                car.setType(updateCarType_v);
                car.setNumber(updateCarNumber_v);
                if("可调度".equals(updateCarStatus_v)) car.setStatus(0);
                else car.setStatus(1);
                car.updateAll("id = ?", car_id);
                Toast.makeText(CarUpdateActivity.this, "车辆信息修改成功", Toast.LENGTH_SHORT).show();
                Intent allocateIntent = new Intent(CarUpdateActivity.this, AllocateActivity.class);
                allocateIntent.putExtra("user_id", user_id);
                startActivity(allocateIntent);
                finish();
                break;
            case R.id.delete_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(CarUpdateActivity.this);
                builder.setMessage("确定删除车辆吗？");
                builder.setCancelable(false);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(Car.class, "id = ?", car_id);
                        Toast.makeText(CarUpdateActivity.this, "删除车辆成功", Toast.LENGTH_SHORT).show();
                        Intent allocateIntent = new Intent(CarUpdateActivity.this, AllocateActivity.class);
                        allocateIntent.putExtra("user_id", user_id);
                        startActivity(allocateIntent);
                        finish();
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
        }
    }
}
