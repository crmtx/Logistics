package com.logistics.android.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.logistics.android.BaseActivity;
import com.logistics.android.MainActivity;
import com.logistics.android.R;
import com.logistics.android.db.Car;
import com.logistics.android.db.Waybill;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CarControlActivity extends BaseActivity {

    private TextView controlCarType; //车型
    private TextView controlCarNumber; //车牌号
    private TextView waybillsNumber; //订单数

    private Button modifyInformationButton; //订单维护按钮
    private Button sendCarButton; //发车按钮

    private String user_id; //用户编号
    private String car_id; //车辆编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_car_control);
        controlCarType = (TextView)findViewById(R.id.controlcar_type);
        controlCarNumber = (TextView)findViewById(R.id.controlcar_number);
        waybillsNumber = (TextView)findViewById(R.id.controlcar_waybillnumber);
        modifyInformationButton = (Button)findViewById(R.id.modifyinformation_button);
        sendCarButton = (Button)findViewById(R.id.sendcar_button);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        car_id = intent.getStringExtra("car_id");
        List<Car> carList = DataSupport.where("id = ?", car_id).find(Car.class);
        Car car = carList.get(0); //找到所选车辆
        //显示所选车辆信息
        controlCarType.setText(car.getType());
        controlCarNumber.setText(car.getNumber());
        List<Waybill> waybillList = DataSupport.where("car_id = ?", car_id).find(Waybill.class);
        waybillsNumber.setText(waybillList.size()+"");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(CarControlActivity.this, SendActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.modifyinformation_button:
                Intent addIntent = new Intent(CarControlActivity.this, WaybillAddActivity.class);
                addIntent.putExtra("user_id", user_id);
                addIntent.putExtra("car_id", car_id);
                startActivity(addIntent);
                break;
            case R.id.sendcar_button:
                Car car = new Car();
                car.setStatus(2); //车辆状态设为已发车
                car.updateAll("id = ?", car_id);
                Toast.makeText(CarControlActivity.this, "发车成功", Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent(CarControlActivity.this, SendActivity.class);
                sendIntent.putExtra("user_id", user_id);
                startActivity(sendIntent);
                finish();
                break;
        }
    }

}
