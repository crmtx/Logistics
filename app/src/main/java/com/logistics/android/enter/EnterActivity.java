package com.logistics.android.enter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logistics.android.BaseActivity;
import com.logistics.android.MainActivity;
import com.logistics.android.R;
import com.logistics.android.db.User;
import com.logistics.android.db.Waybill;

import org.litepal.crud.DataSupport;

import java.util.List;

public class EnterActivity extends BaseActivity {

    private EditText goodsName; //货名
    private EditText goodsNumber; //件数
    private EditText consignor; //发货人
    private EditText reciever; //收货人
    private EditText consignorPhone; //发货人电话
    private EditText recieverPhone; //收货人电话
    private EditText origin; //始发地
    private EditText destination; //目的地
    private EditText cost; //费用
    private EditText costStatus; //支付状态

    private Button okButton; //确认键

    private String goodsName_v;
    private String goodsNumber_v;
    private String consignor_v;
    private String reciever_v;
    private String consignorPhone_v;
    private String recieverPhone_v;
    private String origin_v;
    private String destination_v;
    private String cost_v;
    private String costStatus_v;

    private String user_id; //用户编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);
        goodsName = (EditText)findViewById(R.id.goods_name);
        goodsNumber = (EditText)findViewById(R.id.goods_number);
        consignor = (EditText)findViewById(R.id.consignor);
        reciever = (EditText)findViewById(R.id.reciever);
        consignorPhone = (EditText)findViewById(R.id.consignor_phone);
        recieverPhone = (EditText)findViewById(R.id.reciever_phone);
        origin = (EditText)findViewById(R.id.origin);
        destination = (EditText)findViewById(R.id.destination);
        cost = (EditText)findViewById(R.id.cost);
        costStatus = (EditText)findViewById(R.id.cost_status);
        okButton = (Button)findViewById(R.id.ok_button);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(EnterActivity.this, MainActivity.class);
                backIntent.putExtra("user_id", user_id);
                startActivity(backIntent);
                break;
            case R.id.cost_status: //支付状态选择
                final String[] arr = new String[] { "已付", "到付" };
                AlertDialog.Builder builder = new AlertDialog.Builder(EnterActivity.this);
                builder.setTitle("请选择支付状态");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(arr, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        costStatus.setText(arr[i]);
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                Dialog dialog = builder.create();
                builder.show();
                break;
            case R.id.ok_button:
                goodsName_v = goodsName.getText().toString();
                goodsNumber_v = goodsNumber.getText().toString();
                consignor_v = consignor.getText().toString();
                reciever_v = reciever.getText().toString();
                consignorPhone_v = consignorPhone.getText().toString();
                recieverPhone_v = recieverPhone.getText().toString();
                origin_v = origin.getText().toString();
                destination_v = destination.getText().toString();
                cost_v = cost.getText().toString();
                costStatus_v = costStatus.getText().toString();
                if("".equals(goodsName_v)) {
                    Toast.makeText(EnterActivity.this, "货名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(goodsNumber_v)) {
                    Toast.makeText(EnterActivity.this, "件数不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(consignorPhone_v)) {
                    Toast.makeText(EnterActivity.this, "发货人电话不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(recieverPhone_v)) {
                    Toast.makeText(EnterActivity.this, "收货人电话不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(destination_v)) {
                    Toast.makeText(EnterActivity.this, "目的地不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(cost_v)) {
                    Toast.makeText(EnterActivity.this, "费用不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(costStatus_v)) {
                    Toast.makeText(EnterActivity.this, "支付状态不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Waybill waybill = new Waybill();
                waybill.setGoodsName(goodsName_v);
                waybill.setGoodsNumber(Integer.parseInt(goodsNumber_v));
                waybill.setConsignor(consignor_v);
                waybill.setReciever(reciever_v);
                waybill.setConsignor_phone(consignorPhone_v);
                waybill.setReciever_phone(recieverPhone_v);
                waybill.setOrigin(origin_v);
                waybill.setDestination(destination_v);
                waybill.setCost(Double.parseDouble(cost_v));
                if("已付".equals(costStatus_v)) waybill.setCost_status(0);
                else waybill.setCost_status(1);
                List<User> userList = DataSupport.where("id = ?", user_id).find(User.class);
                User user = userList.get(0);
                waybill.setUser(user); //设置订单所属用户
                waybill.save();

                Toast.makeText(EnterActivity.this, "订单创建成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EnterActivity.this, MainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
                break;
        }
    }
}
