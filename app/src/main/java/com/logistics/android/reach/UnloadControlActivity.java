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

public class UnloadControlActivity extends BaseActivity {

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
    private String car_id; //车辆编号
    private String waybill_id; //订单编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reach_unload_control);
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
        car_id = intent.getStringExtra("car_id");
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
                Intent backIntent = new Intent(UnloadControlActivity.this, UnloadActivity.class);
                backIntent.putExtra("user_id", user_id);
                backIntent.putExtra("car_id", car_id);
                startActivity(backIntent);
                break;
            case R.id.ok_button:
                List<User> userList = DataSupport.where("id = ?", user_id).find(User.class);
                User user = userList.get(0);
                List<Waybill> waybillList = DataSupport.where("id = ?", waybill_id).find(Waybill.class);
                Waybill waybill = waybillList.get(0);
                waybill.setStatus(2); //订单状态设为已到货
                waybill.setToDefault("car_id"); //下车
                waybill.setUser(user);
                waybill.save();

                Toast.makeText(UnloadControlActivity.this, "订单到货成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UnloadControlActivity.this, UnloadActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("car_id", car_id);
                startActivity(intent);
                finish();
                break;
        }
    }
}
