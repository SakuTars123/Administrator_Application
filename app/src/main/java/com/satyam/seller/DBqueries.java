package com.satyam.seller;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBqueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static List<String> addedproductlist = new ArrayList<>();
    public static List<AddedProductsModel> addedProductsModelList = new ArrayList<>();

    public static List<String> addedorderlist = new ArrayList<>();

    public static List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
    public static List<MyOrderItemModel> mypackedmodellist = new ArrayList<>();
    public static List<MyOrderItemModel> mydeliverymodellist = new ArrayList<>();
    public static List<MyOrderItemModel> mydeliveredmodellist = new ArrayList<>();


    public static List<String> addedorderdetailslist = new ArrayList<>();
    public static List<OrderDetailsItemModel> myOrderItemModeldetailsList = new ArrayList<>();

    public static String shopname, shopemail, shopowner, city, address, phone;
    public static int Ordertotalpriceitems = 0;
    public static int Ordertotalconfirmed = 0;
    public static int itemcount, count = 0;


    public static void loadaddedproductlist(final Context context, final Dialog dialog, final boolean loadproductdata) {

        addedproductlist.clear();

        firebaseFirestore.collection("SELLERS").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).collection("SELLER_DATA")
                .document("MY_PRODUCTS").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    for (long x = 0; x < (long) task.getResult().get("listsizeproduct"); x++) {

                        addedproductlist.add(task.getResult().get("product_ID_" + x).toString());


                        if (DBqueries.addedproductlist.contains(ProductDetailsActivity.productid)) {
                            ProductDetailsActivity.addedproductlistquery = true;

                        } else {

                            ProductDetailsActivity.addedproductlistquery = false;
                        }

                        if (loadproductdata) {
                            addedProductsModelList.clear();
                            final String productid = task.getResult().get("product_ID_" + x).toString();

                            firebaseFirestore.collection("PRODUCTS").document(productid)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        addedProductsModelList.add(new AddedProductsModel(productid, task.getResult().get("product_image_1").toString(),
                                                task.getResult().get("product_title").toString(),
                                                (long) task.getResult().get("free_coupouns"),
                                                task.getResult().get("average_rating").toString(),
                                                (long) task.getResult().get("total_ratings"),
                                                task.getResult().get("product_price").toString(),
                                                task.getResult().get("cutted_price").toString(),
                                                (Boolean) task.getResult().get("COD"),
                                                task.getResult().get("product_shop").toString()
                                        ));
                                        AddedProductsFragment.addedProductsAdapter.notifyDataSetChanged();


                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }

                    }

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            }
        });
    }

    public static void removefromaddedproductlist(final int index, final Context context) {

        final String removedproductid = addedproductlist.get(index);
        addedproductlist.remove(index);
        Map<String, Object> updateproductlist = new HashMap<>();

        for (int x = 0; x < addedproductlist.size(); x++) {
            updateproductlist.put("product_ID_" + x, addedproductlist.get(x));

        }
        updateproductlist.put("listsizeproduct", (long) addedproductlist.size());
        firebaseFirestore.collection("SELLERS").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).collection("SELLER_DATA")
                .document("MY_PRODUCTS").set(updateproductlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (addedProductsModelList.size() != 0) {
                        addedProductsModelList.remove(index);
                        AddedProductsFragment.addedProductsAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.addedproductlistquery = false;
                    Toast.makeText(context, "Product removed ", Toast.LENGTH_SHORT).show();
                } else {
                    addedproductlist.add(index, removedproductid);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailsActivity.addedproductlistquery = false;
            }
        });

    }

    public static void loadorders(final Context context, final Dialog dialog, final boolean loadorderdata, final String type) {

        addedorderlist.clear();
        myOrderItemModelList.clear();
        mypackedmodellist.clear();
        mydeliverymodellist.clear();
        mydeliveredmodellist.clear();

        firebaseFirestore.collection("ORDERS").whereArrayContains("selleremailarray", DBqueries.shopemail).orderBy("dateindex", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            for (final DocumentSnapshot orderids : task.getResult().getDocuments()) {
                                HashMap<String, String> status = (HashMap<String, String>) orderids.get("sellersatusarray");
                                String email = orderids.getString("useremail");
                                addedorderlist.add(orderids.getString(orderids.getId()));


                                if (status.get(DBqueries.shopemail).equals("ordered") && type.equals("ordered")) {
                                    myOrderItemModelList.add(new MyOrderItemModel(email, orderids.getId(),
                                            orderids.getDate("orderdate"),
                                            status));
                                    DeliveryCategoryActivity.myOrdersAdapter.notifyDataSetChanged();
                                } else if (status.get(DBqueries.shopemail).equals("packed") && type.equals("packed")) {
                                    mypackedmodellist.add(new MyOrderItemModel(email, orderids.getId(),
                                            orderids.getDate("orderdate"),
                                            status));
                                    PackedCategoryActivity.mypackedorderadapter.notifyDataSetChanged();
                                } else if (status.get(DBqueries.shopemail).equals("shipped") && type.equals("shipped")) {
                                    mydeliverymodellist.add(new MyOrderItemModel(email, orderids.getId(),
                                            orderids.getDate("orderdate"),
                                            status));

                                    ShippedCategoryActivity.myShippedAdapter.notifyDataSetChanged();
                                } else if (status.get(DBqueries.shopemail).equals("delivered") && type.equals("delivered")) {
                                    mydeliveredmodellist.add(new MyOrderItemModel(email, orderids.getId(),
                                            orderids.getDate("orderdate"),
                                            status));
                                    DeliveredCategoryActivity.myDeliveredAdapter.notifyDataSetChanged();
                                }

                            }

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();

                    }
                });

    }

    public static void loadoderdetails(final Context context, final Dialog dialog, String orderid) {

        addedorderdetailslist.clear();
        myOrderItemModeldetailsList.clear();
        firebaseFirestore.collection("ORDERS").document(orderid).collection("Orderitems").whereEqualTo("selleremail", DBqueries.shopemail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            int itemcounter = 0;

                            int price = 0;
                            int cprice = 0;
                            int counter = 0;
                            for (final DocumentSnapshot orderdetailsids : task.getResult().getDocuments()) {


                                addedorderdetailslist.add(orderdetailsids.getString(orderdetailsids.getId()));
                                String perproductprice = orderdetailsids.getString("productprice");
                                Long perproductquantity = orderdetailsids.getLong("productquantity");
                                String orderstatus = orderdetailsids.getString("orderstatus");
                                Boolean is_served = orderdetailsids.getBoolean("is_served");
                                myOrderItemModeldetailsList.add(new OrderDetailsItemModel(is_served, orderdetailsids.getString("orderid"), orderdetailsids.getString("productid"),
                                        orderdetailsids.getString("productimage"),
                                        orderdetailsids.getString("producttitle")
                                        , perproductprice,
                                        orderdetailsids.getString("fullname"),
                                        orderdetailsids.getString("address"),
                                        orderdetailsids.getString("pincode"),
                                        orderdetailsids.getString("paymentmethod"),
                                        orderdetailsids.getString("selltype"),
                                        orderstatus,
                                        perproductquantity,
                                        orderdetailsids.getDate("packeddate"),
                                        orderdetailsids.getDate("shippinddate")));
                                if (orderstatus.equals("packed")) {
                                    cprice = (int) (price + (Integer.valueOf(perproductprice) * perproductquantity));

                                }
                                if (is_served) {
                                    counter++;
                                }

                                price = (int) (price + (Integer.valueOf(perproductprice) * perproductquantity));
                                AllOrderDetailsActivity.myOrdersdetailsAdapter.notifyDataSetChanged();
                                itemcount++;
                            }
                            itemcount = itemcounter;
                            count = counter;
                            Ordertotalconfirmed = cprice;
                            Ordertotalpriceitems = price;


                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();

                    }
                });


    }

    public static void acceptrejectorder(final String productimage, final String producttitle, final String useremail, final Context context, final Dialog dialog, boolean accept, String productid, final String orderid, final LinearLayout linearLayout, final LinearLayout linearLayout2, final TextView textView, final TextView ordertaken, final String price, final Long quantity) {
        if (accept) {

            HashMap<String, Object> updateshopside = new HashMap<>();
            updateshopside.put("packeddate", FieldValue.serverTimestamp());
            updateshopside.put("orderstatus", "packed");
            updateshopside.put("is_served", true);
            firebaseFirestore.collection("ORDERS").document(orderid).collection("Orderitems").document(productid)
                    .update(updateshopside).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        linearLayout.setVisibility(View.GONE);
                        linearLayout2.setVisibility(View.VISIBLE);
                        textView.setText("Order Confirmed by You.");
                        String previous = ordertaken.getText().subSequence(2, ordertaken.length() - 2).toString();
                        String calculation = String.valueOf(Integer.parseInt(previous) + Integer.parseInt(price) * quantity);
                        ordertaken.setText("₹ " + calculation + "/-");
                        count++;
                        linearLayout2.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, "Error " + error, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {

            HashMap<String, Object> updateshopside = new HashMap<>();
            updateshopside.put("packeddate", FieldValue.serverTimestamp());
            updateshopside.put("orderstatus", "shopkeepercancelled");
            updateshopside.put("is_served", true);
            updateshopside.put("dateindex", "");
                firebaseFirestore.collection("ORDERS").document(orderid).collection("Orderitems").document(productid)
                        .update(updateshopside).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Long pprice = Long.parseLong(String.valueOf(-1 * (Long.parseLong(price)) * quantity));
                            firebaseFirestore.collection("ORDERS").document(orderid).update("shopkeeperconfirmedtotalprice",
                                    FieldValue.increment(pprice)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        linearLayout.setVisibility(View.GONE);
                                        linearLayout2.setVisibility(View.VISIBLE);
                                        textView.setText("Order Cancelled by You.");
                                        textView.setTextColor(context.getResources().getColor(R.color.stopred));
                                        textView.setVisibility(View.VISIBLE);
                                        count++;
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, "Error " + error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {

                        }
                    }
                });
            }
            if (count == itemcount) {
                HashMap<String, String> itemstatusupdate = new HashMap<>();
                itemstatusupdate.put(DBqueries.shopemail, "packed");

                HashMap<String, Object> finalstatusupdate = new HashMap<>();
                finalstatusupdate.put("sellersatusarray", itemstatusupdate);


                firebaseFirestore.collection("ORDERS").document(orderid).set(finalstatusupdate, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    HashMap<String, Object> cancelmessage = new HashMap<>();
                                    cancelmessage.put("notificationimage", productimage);
                                    cancelmessage.put("notificationbody", "Your Order " + producttitle + " has been cancelled by shopkeeper.Product price " + price + " would be deducted from amount. KEEP SHOPPING");
                                    cancelmessage.put("readed", false);

                                    firebaseFirestore.collection("USERS").document(useremail)
                                            .collection("USER_NOTIFICATIONS").document()
                                            .set(cancelmessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, "Notification sent to user.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(context, "Error " + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            dialog.dismiss();

        }

}


