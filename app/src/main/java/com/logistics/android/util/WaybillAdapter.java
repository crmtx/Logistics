package com.logistics.android.util;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.logistics.android.R;
import com.logistics.android.db.Waybill;

import java.util.List;

/**
 * Created by crmtx on 2018/6/5.
 */

public class WaybillAdapter extends ArrayAdapter<Waybill> {

    private int resourceId;

    public WaybillAdapter(Context context, int textViewResourceId, List<Waybill> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Waybill waybill = getItem(position);
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }
        TextView waybillGoodsName = (TextView)view.findViewById(R.id.waybill_goodsname);
        TextView waybillGoodsNumber = (TextView)view.findViewById(R.id.waybill_goodsnumber);
        TextView waybillOrigin = (TextView)view.findViewById(R.id.waybill_origin);
        TextView waybillDestination = (TextView)view.findViewById(R.id.waybill_destination);
        waybillGoodsName.setText("货名：" + waybill.getGoodsName());
        waybillGoodsNumber.setText("件数：" + waybill.getGoodsNumber());
        waybillOrigin.setText("始发地：" + waybill.getOrigin());
        waybillDestination.setText("目的地：" + waybill.getDestination());
        return view;
    }
}