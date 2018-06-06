package com.logistics.android.reach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ReachActivity extends BaseActivity {

    private ListView carListView; //已发车辆列表

    private List<Car> carList = new ArrayList<>();

    private String user_id; //用户编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reach);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        initCars();
        CarAdapter adapter = new CarAdapter(ReachActivity.this, R.layout.item_car, carList);
        carListView = (ListView)findViewById(R.id.car_list_view);
        carListView.setAdapter(adapter);
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Car car = carList.get(i);
                Intent reachCarIntent = new Intent(ReachActivity.this, ReachCarActivity.class);
                reachCarIntent.putExtra("user_id", user_id);
                reachCarIntent.putExtra("car_id", car.getId()+"");
                startActivity(reachCarIntent);
            }
        });
    }

    private void initCars() {
        carList = DataSupport.where("user_id = ? and status = ?", user_id, "2").find(Car.class); //找到已发车辆列表
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(ReachActivity.this, MainActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
        }
    }
}
