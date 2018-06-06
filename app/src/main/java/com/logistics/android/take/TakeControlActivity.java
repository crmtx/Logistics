package com.logistics.android.take;

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
import com.logistics.android.db.Waybill;
import com.logistics.android.send.WaybillAddActivity;
import com.logistics.android.send.WaybillControlActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

public class TakeControlActivity extends BaseActivity {

    private TextView goodsName; //货名
    private TextView goodsNumber; //件数
    private TextView consignor; //发货人
    private TextView reciever; //收货人
    private TextView consignorPhone; //发货人电话
    private TextView recieverPhone; //收货人电话
    private TextView origin; //始发地
    private TextView destination; //目的地
    private TextView cost; //费用
    private TextView costStatus; //支付状态

    private Button okButton; //确认键

    private String user_id; //用户编号
    private String waybill_id; //订单编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_control);
        goodsName = (TextView)findViewById(R.id.goods_name);
        goodsNumber = (TextView)findViewById(R.id.goods_number);
        consignor = (TextView)findViewById(R.id.consignor);
        reciever = (TextView)findViewById(R.id.reciever);
        consignorPhone = (TextView)findViewById(R.id.consignor_phone);
        recieverPhone = (TextView)findViewById(R.id.reciever_phone);
        origin = (TextView)findViewById(R.id.origin);
        destination = (TextView)findViewById(R.id.destination);
        cost = (TextView)findViewById(R.id.cost);
        costStatus = (TextView)findViewById(R.id.cost_status);
        okButton = (Button)findViewById(R.id.ok_button);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        waybill_id = intent.getStringExtra("waybill_id");
        List<Waybill> waybillList = DataSupport.where("id = ?", waybill_id).find(Waybill.class);
        Waybill waybill = waybillList.get(0);

        goodsName.setText(waybill.getGoodsName());
        goodsNumber.setText(waybill.getGoodsNumber()+"");
        consignor.setText(waybill.getConsignor());
        reciever.setText(waybill.getReciever());
        consignorPhone.setText(waybill.getConsignor_phone());
        recieverPhone.setText(waybill.getReciever_phone());
        origin.setText(waybill.getOrigin());
        destination.setText(waybill.getDestination());
        cost.setText(waybill.getCost()+"");
        if(waybill.getCost_status() == 0) costStatus.setText("已付");
        else costStatus.setText("到付");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(TakeControlActivity.this, TakeActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.ok_button:
                Waybill waybill = new Waybill();
                waybill.setStatus(3); //订单状态设为已提货
                waybill.updateAll("id = ?", waybill_id);

                Toast.makeText(TakeControlActivity.this, "订单签收成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TakeControlActivity.this, TakeActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
                break;
        }
    }
}
