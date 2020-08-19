package com.satyam.seller;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    private List<OrderDetailsItemModel> orderDetailsItemModelList;
    private Dialog loadingDialog;
    private String email;

    public OrderDetailsAdapter(List<OrderDetailsItemModel> orderDetailsItemModelList, Dialog loadingDialog,String email) {
        this.orderDetailsItemModelList = orderDetailsItemModelList;
        this.loadingDialog = loadingDialog;
        this.email=email;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_details_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Boolean is_served = orderDetailsItemModelList.get(position).getIs_served();
         String orderid= orderDetailsItemModelList.get(position).getOrderid();
        String productid = orderDetailsItemModelList.get(position).getProductid();
        String productimage = orderDetailsItemModelList.get(position).getProductimage();
        String producttitle = orderDetailsItemModelList.get(position).getProducttitle();
        String productprice = orderDetailsItemModelList.get(position).getProductprice();
        String fullnamephone = orderDetailsItemModelList.get(position).getFullnamephone();
        String address      = orderDetailsItemModelList.get(position).getAddress();
        String pincode       = orderDetailsItemModelList.get(position).getPincode();
        String paymentmethod = orderDetailsItemModelList.get(position).getPaymentmethod();
        String productselltype = orderDetailsItemModelList.get(position).getProductselltype();
        String orderstatus = orderDetailsItemModelList.get(position).getOrderstatus();
        Long quantity = orderDetailsItemModelList.get(position).getQuantity();
        Date packeddate = orderDetailsItemModelList.get(position).getPackeddate();
        Date shippeddate = orderDetailsItemModelList.get(position).getShippeddate();


        viewHolder.setData(is_served,orderid,productid,productimage,producttitle,productprice,paymentmethod,productselltype,orderstatus,quantity,packeddate,shippeddate,position);

    }

    @Override
    public int getItemCount() {
        return orderDetailsItemModelList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView Productimage;
        private TextView Producttitle;
        private TextView Productprice;
        // private TextView Fullnamephone;
        private TextView Quantityandselltype;
        private TextView Paymentmethod;
        private Button rejectbtn;
        private Button acceptbtn;
        private LinearLayout confirmationlinearlayout,confirmationlinearlayout2;
        private Dialog passworddialog;
        private Button donebtn, notdonebtn;
        private TextView confirmed;
        private TextView totalpricequantity;

        // private TextView Pincode;
        //private TextView Address;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ////////////paswword dialog////////
            passworddialog = new Dialog(itemView.getContext());
            passworddialog.setContentView(R.layout.password_confirmation_dialog);
            passworddialog.setCancelable(true);
            passworddialog.getWindow().setBackgroundDrawable(itemView.getResources().getDrawable(R.drawable.slider_background));
            passworddialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            donebtn= passworddialog.findViewById(R.id.button_cancel);
            notdonebtn= passworddialog.findViewById(R.id.donebutton);


            /////////////password dialog///////////

            Productimage = itemView.findViewById(R.id.product_image);
            Producttitle = itemView.findViewById(R.id.product_title);
            Productprice = itemView.findViewById(R.id.product_price);
            Quantityandselltype= itemView.findViewById(R.id.quantity_dialog);
            Paymentmethod= itemView.findViewById(R.id.payment_options);
            rejectbtn = itemView.findViewById(R.id.reject_button);
            acceptbtn = itemView.findViewById(R.id.accept_button);
            confirmationlinearlayout=itemView.findViewById(R.id.confirmation_linearlayout);
            confirmationlinearlayout2=itemView.findViewById(R.id.confirmation_linearlayout2);
            confirmed=itemView.findViewById(R.id.confirmed);
            totalpricequantity=itemView.findViewById(R.id.totalpricequantity);


           // Pincode= itemView.findViewById(R.id.);
            //Fullnamephone= itemView.findViewById(R.id.quantity_dialog);
           // Address= itemView.findViewById(R.id.);
        }


        private  void  setData(Boolean Is_served, final String Orderid, final String Productid, final String pimage, final String ptitle, final String pprice, String pmethod, String Pselltype, String Porderstatus, final Long Pquantity, Date Ppackeddate, Date Pshippeddate, long pos) {
            Glide.with(itemView.getContext()).load(pimage).apply(new RequestOptions().placeholder(R.drawable.loadingplaceholders)).into(Productimage);
            Producttitle.setText(ptitle);
            Productprice.setText("₹ " + pprice);
            Quantityandselltype.setText(Pquantity + " " + Pselltype);
            Paymentmethod.setText(pmethod);
            AllOrderDetailsActivity.ordertotalamount.setText("₹ " +DBqueries.Ordertotalpriceitems+"/-");
            AllOrderDetailsActivity.ordertakenamount.setText("₹ " +DBqueries.Ordertotalconfirmed+"/-");
            String pricequant;
            pricequant = String.valueOf(Integer.parseInt(pprice)*Pquantity);
            totalpricequantity.setText("₹ " +pricequant+"/-");
            if (!Is_served) {


                rejectbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        passworddialog.show();
                        donebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadingDialog.show();
                                loadingDialog.setCancelable(false);
                                passworddialog.dismiss();
                                DBqueries.acceptrejectorder(pimage,ptitle,email,itemView.getContext(), loadingDialog, false, Productid, Orderid, confirmationlinearlayout,confirmationlinearlayout2,confirmed,AllOrderDetailsActivity.ordertakenamount,pprice,Pquantity);


                            }
                        });
                        notdonebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                passworddialog.dismiss();
                            }
                        });
                    }
                });
                acceptbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        passworddialog.dismiss();
                        DBqueries.acceptrejectorder(pimage,ptitle,email,itemView.getContext(), loadingDialog, true, Productid, Orderid, confirmationlinearlayout,confirmationlinearlayout2,confirmed,AllOrderDetailsActivity.ordertakenamount,pprice,Pquantity);

                    }
                });

            }
            else
            {
                confirmationlinearlayout.setVisibility(View.GONE);
                confirmationlinearlayout2.setVisibility(View.VISIBLE);
                if(Porderstatus.equals("packed"))
                {

                    confirmed.setText("Order Confirmed by You.");
                    confirmed.setTextColor(itemView.getResources().getColor(R.color.colorPrimary));
                }
                else if(Porderstatus.equals("shopkeepercancelled"))
                {

                    confirmed.setText("Order Cancelled by You.");
                    confirmed.setTextColor(itemView.getResources().getColor(R.color.stopred));
                }
            }
        }


    }

}
