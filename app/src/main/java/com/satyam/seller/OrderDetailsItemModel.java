package com.satyam.seller;

import java.util.Date;

public class OrderDetailsItemModel {

    private String orderid,productid,productimage,producttitle, productprice, fullnamephone,address,pincode,paymentmethod,productselltype,orderstatus;
    private long quantity;
    private Date packeddate,shippeddate;
    private Boolean is_served;

    public OrderDetailsItemModel(Boolean is_served,String orderid,String productid,String productimage, String producttitle, String productprice, String fullnamephone, String address, String pincode, String paymentmethod, String productselltype, String orderstatus, long quantity, Date packeddate, Date shippeddate) {
        this.is_served=is_served;
        this.orderid=orderid;
        this.productid=productid;
        this.productimage = productimage;
        this.producttitle = producttitle;
        this.productprice = productprice;
        this.fullnamephone = fullnamephone;
        this.address = address;
        this.pincode = pincode;
        this.paymentmethod = paymentmethod;
        this.productselltype = productselltype;
        this.orderstatus = orderstatus;
        this.quantity = quantity;
        this.packeddate = packeddate;
        this.shippeddate = shippeddate;
    }

    public Boolean getIs_served() {
        return is_served;
    }

    public void setIs_served(Boolean is_served) {
        this.is_served = is_served;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProducttitle() {
        return producttitle;
    }

    public void setProducttitle(String producttitle) {
        this.producttitle = producttitle;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getFullnamephone() {
        return fullnamephone;
    }

    public void setFullnamephone(String fullnamephone) {
        this.fullnamephone = fullnamephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getProductselltype() {
        return productselltype;
    }

    public void setProductselltype(String productselltype) {
        this.productselltype = productselltype;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Date getPackeddate() {
        return packeddate;
    }

    public void setPackeddate(Date packeddate) {
        this.packeddate = packeddate;
    }

    public Date getShippeddate() {
        return shippeddate;
    }

    public void setShippeddate(Date shippeddate) {
        this.shippeddate = shippeddate;
    }
}
