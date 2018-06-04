package com.logistics.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by crmtx on 2018/6/4.
 */

public class Waybill extends DataSupport {

    private String goodsName; //货名
    private int goodsNumber; //件数
    private String consignor; //发货人
    private String receiver; //收货人
    private String consignor_phone; //发货人电话
    private String receiver_phone; //收货人电话
    private String origin; //来源地
    private String destination; //目的地
    private double cost; //费用
    private boolean cost_status; //0费用已付，1费用到付
    private int status; //订单状态，0未发货，1已发货，2已到货，3已提货

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getConsignor_phone() {
        return consignor_phone;
    }

    public void setConsignor_phone(String consignor_phone) {
        this.consignor_phone = consignor_phone;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isCost_status() {
        return cost_status;
    }

    public void setCost_status(boolean cost_status) {
        this.cost_status = cost_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
