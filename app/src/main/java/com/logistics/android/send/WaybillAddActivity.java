package com.logistics.android.send;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class WaybillAddActivity extends BaseActivity {

    private ListView waybillListView;

    private List<Waybill> waybillList = new ArrayList<>();

    private String user_id; //用户编号
    private String car_id; //车辆编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_waybill_add);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        car_id = intent.getStringExtra("car_id");
        initWaybills();
        WaybillAdapter adapter = new WaybillAdapter(WaybillAddActivity.this, R.layout.item_waybill, waybillList);
        waybillListView = (ListView)findViewById(R.id.waybill_list_view);
        waybillListView.setAdapter(adapter);
        waybillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Waybill waybill = waybillList.get(i);
                Intent waybillControlIntent = new Intent(WaybillAddActivity.this, WaybillControlActivity.class);
                waybillControlIntent.putExtra("user_id", user_id);
                waybillControlIntent.putExtra("car_id", car_id);
                waybillControlIntent.putExtra("waybill_id", waybill.getId()+"");
                startActivity(waybillControlIntent);
            }
        });
    }

    private void initWaybills() {
        waybillList = DataSupport.where("user_id = ? and status = ?", user_id, "0").find(Waybill.class); //找到未发货订单列表
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(WaybillAddActivity.this, CarControlActivity.class);
                backIntent.putExtra("user_id", user_id);
                backIntent.putExtra("car_id", car_id);
                startActivity(backIntent);
                break;
        }
    }
}
