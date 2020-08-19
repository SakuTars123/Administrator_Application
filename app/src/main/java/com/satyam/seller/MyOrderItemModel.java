package com.satyam.seller;

import java.util.Date;
import java.util.HashMap;

public class MyOrderItemModel {
    private String email;
    private String orderid;
    private Date oderdate;
    private HashMap<String,String> sellerstatus;

    public MyOrderItemModel(String email,String orderid, Date oderdate, HashMap<String, String> sellerstatus) {
        this.email=email;
        this.orderid = orderid;
        this.oderdate = oderdate;
        this.sellerstatus = sellerstatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, String> getSellerstatus() {
        return sellerstatus;
    }

    public void setSellerstatus(HashMap<String, String> sellerstatus) {
        this.sellerstatus = sellerstatus;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Date getOderdate() {
        return oderdate;
    }

    public void setOderdate(Date oderdate) {
        this.oderdate = oderdate;
    }


}
