package com.logistics.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.logistics.android.allocate.AllocateActivity;
import com.logistics.android.enter.EnterActivity;
import com.logistics.android.reach.ReachActivity;
import com.logistics.android.send.SendActivity;
import com.logistics.android.take.TakeActivity;
import com.logistics.android.user.SetupActivity;

public class MainActivity extends BaseActivity {

    private Button recieveButton; //收货键
    private Button sendButton; //发运键
    private Button reachButton; //到货键
    private Button takeButton; //提货键
    private Button allocateButton; //配载键
    private Button setupButton; //设置键

    private String user_id; //用户编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        recieveButton = (Button)findViewById(R.id.recieve_button);
        sendButton = (Button)findViewById(R.id.send_button);
        reachButton = (Button)findViewById(R.id.reach_button);
        takeButton = (Button)findViewById(R.id.take_button);
        allocateButton = (Button)findViewById(R.id.allocate_button);
        setupButton = (Button)findViewById(R.id.setup_button);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.recieve_button:
                Intent recieveIntent = new Intent(MainActivity.this, EnterActivity.class);
                recieveIntent.putExtra("user_id", user_id);
                startActivity(recieveIntent);
                break;
            case R.id.send_button:
                Intent sendIntent = new Intent(MainActivity.this, SendActivity.class);
                sendIntent.putExtra("user_id", user_id);
                startActivity(sendIntent);
                break;
            case R.id.reach_button:
                Intent reachIntent = new Intent(MainActivity.this, ReachActivity.class);
                reachIntent.putExtra("user_id", user_id);
                startActivity(reachIntent);
                break;
            case R.id.take_button:
                Intent takeIntent = new Intent(MainActivity.this, TakeActivity.class);
                takeIntent.putExtra("user_id", user_id);
                startActivity(takeIntent);
                break;
            case R.id.allocate_button:
                Intent allocateIntent = new Intent(MainActivity.this, AllocateActivity.class);
                allocateIntent.putExtra("user_id", user_id);
                startActivity(allocateIntent);
                break;
            case R.id.setup_button:
                Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                setupIntent.putExtra("user_id", user_id);
                startActivity(setupIntent);
                break;
        }
    }
}
