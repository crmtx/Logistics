package com.logistics.android.reach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.logistics.android.BaseActivity;
import com.logistics.android.R;
import com.logistics.android.db.Car;
import com.logistics.android.db.User;
import com.logistics.android.db.Waybill;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ReachCarActivity extends BaseActivity {

    private TextView reachCarType; //车型
    private TextView reachCarNumber; //车牌号
    private TextView waybillsNumber; //订单数

    private Button reachButton; //到达按钮

    private String user_id; //用户编号
    private String car_id; //车辆编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reach_car);
        reachCarType = (TextView)findViewById(R.id.reach_type);
        reachCarNumber = (TextView)findViewById(R.id.reach_number);
        waybillsNumber = (TextView)findViewById(R.id.reach_waybillnumber);
        reachButton = (Button)findViewById(R.id.reach_button);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        car_id = intent.getStringExtra("car_id");
        List<Car> carList = DataSupport.where("id = ?", car_id).find(Car.class);
        Car car = carList.get(0); //找到所选车辆
        //显示所选车辆信息
        reachCarType.setText(car.getType());
        reachCarNumber.setText(car.getNumber());
        List<Waybill> waybillList = DataSupport.where("car_id = ?", car_id).find(Waybill.class);
        waybillsNumber.setText(waybillList.size()+"");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(ReachCarActivity.this, ReachActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.unload_button:
                Intent unloadIntent = new Intent(ReachCarActivity.this, UnloadActivity.class);
                unloadIntent.putExtra("user_id", user_id);
                unloadIntent.putExtra("car_id", car_id);
                startActivity(unloadIntent);
                break;
            case R.id.reach_button:
                List<User> userList = DataSupport.where("id = ?", user_id).find(User.class);
                User user = userList.get(0);
                List<Waybill> waybillList = DataSupport.where("car_id = ?", car_id).find(Waybill.class);
                for(Waybill waybill : waybillList) {
                    waybill.setStatus(2); //已到货
                    waybill.setUser(user);
                    waybill.setToDefault("car_id");
                    waybill.save();
                }
                Car car = new Car();
                car.setStatus(1); //到达后车辆状态设为不可调度
                car.updateAll("id = ?", car_id);
                Toast.makeText(ReachCarActivity.this, "车辆已到达", Toast.LENGTH_SHORT).show();
                Intent reachIntent = new Intent(ReachCarActivity.this, ReachActivity.class);
                reachIntent.putExtra("user_id", user_id);
                startActivity(reachIntent);
                finish();
                break;
        }
    }
}
