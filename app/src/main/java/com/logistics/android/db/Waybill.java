package com.logistics.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by crmtx on 2018/6/4.
 */

public class Waybill extends DataSupport {

    private int id; //编号
    private String goodsName; //货名
    private int goodsNumber; //件数
    private String consignor; //发货人
    private String reciever; //收货人
    private String consignor_phone; //发货人电话
    private String reciever_phone; //收货人电话
    private String origin; //来源地
    private String destination; //目的地
    private double cost; //费用
    private int cost_status; //支付状态，0费用已付，1费用到付
    private int status; //订单状态，0未发货，1已发货，2已到货，3已提货
    private User user;
    private Car car;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getConsignor_phone() {
        return consignor_phone;
    }

    public void setConsignor_phone(String consignor_phone) {
        this.consignor_phone = consignor_phone;
    }

    public String getReciever_phone() {
        return reciever_phone;
    }

    public void setReciever_phone(String reciever_phone) {
        this.reciever_phone = reciever_phone;
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

    public int getCost_status() {
        return cost_status;
    }

    public void setCost_status(int cost_status) {
        this.cost_status = cost_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
