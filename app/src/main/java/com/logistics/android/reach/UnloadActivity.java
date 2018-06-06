package com.logistics.android.reach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.logistics.android.BaseActivity;
import com.logistics.android.R;
import com.logistics.android.db.Waybill;
import com.logistics.android.util.WaybillAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class UnloadActivity extends BaseActivity {

    private ListView waybillListView;

    private List<Waybill> waybillList = new ArrayList<>();

    private String user_id; //用户编号
    private String car_id; //车辆编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reach_unload);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        car_id = intent.getStringExtra("car_id");
        initWaybills();
        WaybillAdapter adapter = new WaybillAdapter(UnloadActivity.this, R.layout.item_waybill, waybillList);
        waybillListView = (ListView)findViewById(R.id.waybill_list_view);
        waybillListView.setAdapter(adapter);
        waybillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Waybill waybill = waybillList.get(i);
                Intent unloadControlIntent = new Intent(UnloadActivity.this, UnloadControlActivity.class);
                unloadControlIntent.putExtra("user_id", user_id);
                unloadControlIntent.putExtra("car_id", car_id);
                unloadControlIntent.putExtra("waybill_id", waybill.getId()+"");
                startActivity(unloadControlIntent);
            }
        });
    }

    private void initWaybills() {
        waybillList = DataSupport.where("car_id = ? and status = ?", car_id, "1").find(Waybill.class); //找到车上订单列表
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(UnloadActivity.this, ReachCarActivity.class);
                backIntent.putExtra("user_id", user_id);
                backIntent.putExtra("car_id", car_id);
                startActivity(backIntent);
                break;
        }
    }
}
