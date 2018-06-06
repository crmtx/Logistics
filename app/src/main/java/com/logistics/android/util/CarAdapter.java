package com.logistics.android.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.logistics.android.R;
import com.logistics.android.db.Car;

import java.util.List;

/**
 * Created by crmtx on 2018/6/5.
 */

public class CarAdapter extends ArrayAdapter<Car> {

    private int resourceId;

    public CarAdapter(Context context, int textViewResourceId, List<Car> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Car car = getItem(position);
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }
        TextView carType = (TextView)view.findViewById(R.id.car_type);
        TextView carNumber = (TextView)view.findViewById(R.id.car_number);
        TextView carStatus = (TextView)view.findViewById(R.id.car_status);
        carType.setText("车型：" + car.getType());
        carNumber.setText("车牌号：" + car.getNumber());
        if(car.getStatus() == 0) carStatus.setText("车辆状态：可调度");
        else if(car.getStatus() == 1) carStatus.setText("车辆状态：不可调度");
        else carStatus.setText("车辆状态：已发车");
        return view;
    }
}
