package com.logistics.android.allocate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.logistics.android.BaseActivity;
import com.logistics.android.MainActivity;
import com.logistics.android.R;
import com.logistics.android.db.Car;
import com.logistics.android.util.CarAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class AllocateActivity extends BaseActivity {

    private ListView carListView; //车辆列表
    private Button carAddButton; //添加车辆按钮

    private List<Car> carList = new ArrayList<>();

    private String user_id; //用户编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allocate);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        initCars();
        CarAdapter adapter = new CarAdapter(AllocateActivity.this, R.layout.item_car, carList);
        carListView = (ListView)findViewById(R.id.car_list_view);
        carAddButton = (Button)findViewById(R.id.car_add);
        carListView.setAdapter(adapter);
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Car car = carList.get(i);
                Intent carUpdateIntent = new Intent(AllocateActivity.this, CarUpdateActivity.class);
                carUpdateIntent.putExtra("user_id", user_id);
                carUpdateIntent.putExtra("car_id", car.getId()+"");
                startActivity(carUpdateIntent);
            }
        });
    }

    private void initCars() {
        carList = DataSupport.where("user_id = ? and status < ?", user_id, "2").find(Car.class); //未发车辆列表
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(AllocateActivity.this, MainActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.car_add:
                Intent intent = new Intent(AllocateActivity.this, CarAddActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                break;
        }
    }
}
