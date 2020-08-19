package com.satyam.seller;

public class AddedProductsModel {

    public static final int ADDEDITEM = 0;


    private String productid;
    private String productimage;
    private String producttitle;
    private long freecoupouns;
    private String rating;
    private long totalratings;
    private String productprice;
    private String cuttedprice;
    private boolean paymentmethod;
    private String shopname;

    public AddedProductsModel(String productid, String productimage, String producttitle, long freecoupouns, String rating, long totalratings, String productprice, String cuttedprice, boolean paymentmethod, String shopname) {
        this.productid=productid;
        this.productimage = productimage;
        this.producttitle = producttitle;
        this.freecoupouns = freecoupouns;
        this.rating = rating;
        this.totalratings = totalratings;
        this.productprice = productprice;
        this.cuttedprice = cuttedprice;
        this.paymentmethod = paymentmethod;
        this.shopname = shopname;
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

    public long getFreecoupouns() {
        return freecoupouns;
    }

    public void setFreecoupouns(long freecoupouns) {
        this.freecoupouns = freecoupouns;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public long getTotalratings() {
        return totalratings;
    }

    public void setTotalratings(long totalratings) {
        this.totalratings = totalratings;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getCuttedprice() {
        return cuttedprice;
    }

    public void setCuttedprice(String cuttedprice) {
        this.cuttedprice = cuttedprice;
    }

    public boolean getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(boolean paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

}
