package com.logistics.android.take;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.logistics.android.BaseActivity;
import com.logistics.android.MainActivity;
import com.logistics.android.R;
import com.logistics.android.db.Waybill;
import com.logistics.android.reach.UnloadActivity;
import com.logistics.android.reach.UnloadControlActivity;
import com.logistics.android.util.WaybillAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class TakeActivity extends BaseActivity {

    private ListView waybillListView;

    private List<Waybill> waybillList = new ArrayList<>();

    private String user_id; //用户编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        initWaybills();
        WaybillAdapter adapter = new WaybillAdapter(TakeActivity.this, R.layout.item_waybill, waybillList);
        waybillListView = (ListView)findViewById(R.id.waybill_list_view);
        waybillListView.setAdapter(adapter);
        waybillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Waybill waybill = waybillList.get(i);
                Intent takeControlIntent = new Intent(TakeActivity.this, TakeControlActivity.class);
                takeControlIntent.putExtra("user_id", user_id);
                takeControlIntent.putExtra("waybill_id", waybill.getId()+"");
                startActivity(takeControlIntent);
            }
        });
    }

    private void initWaybills() {
        waybillList = DataSupport.where("user_id = ? and status = ?", user_id, "2").find(Waybill.class); //找到已到货订单列表
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(TakeActivity.this, MainActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
        }
    }
}
