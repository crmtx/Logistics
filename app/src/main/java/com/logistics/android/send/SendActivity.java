package com.logistics.android.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.logistics.android.BaseActivity;
import com.logistics.android.MainActivity;
import com.logistics.android.R;
import com.logistics.android.db.Car;
import com.logistics.android.util.CarAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SendActivity extends BaseActivity {

    private ListView carListView; //可调度车辆列表

    private List<Car> carList = new ArrayList<>();

    private String user_id; //用户编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        initCars();
        CarAdapter adapter = new CarAdapter(SendActivity.this, R.layout.item_car, carList);
        carListView = (ListView)findViewById(R.id.car_list_view);
        carListView.setAdapter(adapter);
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Car car = carList.get(i);
                Intent carControlIntent = new Intent(SendActivity.this, CarControlActivity.class);
                carControlIntent.putExtra("user_id", user_id);
                carControlIntent.putExtra("car_id", car.getId()+"");
                startActivity(carControlIntent);
            }
        });
    }

    private void initCars() {
        carList = DataSupport.where("user_id = ? and status = ?", user_id, "0").find(Car.class); //找到可调度车辆列表
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(SendActivity.this, MainActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
        }
    }
}
