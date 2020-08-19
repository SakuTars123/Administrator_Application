package com.satyam.seller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.collection.LLRBNode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    private List<MyOrderItemModel> myOrderItemModelList;
    private Dialog loadingDialog;

    public MyOrdersAdapter(List<MyOrderItemModel> myOrderItemModelList, Dialog loadingDialog) {
        this.myOrderItemModelList = myOrderItemModelList;
        this.loadingDialog = loadingDialog;

    }


    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout, viewGroup, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.ViewHolder viewHolder, int position) {

        HashMap<String,String > sellerstatus = myOrderItemModelList.get(position).getSellerstatus();
        String orderid = myOrderItemModelList.get(position).getOrderid();
        Date orderdate = myOrderItemModelList.get(position).getOderdate();
        String useremail = myOrderItemModelList.get(position).getEmail();

        viewHolder.setdataneworder(sellerstatus, orderid, orderdate, position,useremail);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView orderid;
        private TextView orderdate;
        private TextView OrderPrice;
        private ConstraintLayout neworderlayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderid= itemView.findViewById(R.id.product_order_id);
            orderdate= itemView.findViewById(R.id.order_time);
            OrderPrice= itemView.findViewById(R.id.total_order_price);
            neworderlayout=itemView.findViewById(R.id.order_constraint_layout);
        }

        private void setdataneworder(HashMap<String, String> Sellerstatus, final String ORDERID, Date DATE, final long pos, final String email){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM YYYY hh:mm aa");


            if(Sellerstatus.get(DBqueries.shopemail).equals("ordered"))
            {
                neworderlayout.setVisibility(View.VISIBLE);
                orderdate.setText("Order Date: "+(simpleDateFormat.format(DATE)));
                orderid.setText("OrderID: "+ORDERID);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderdetailsintent = new Intent(itemView.getContext(),AllOrderDetailsActivity.class);
                    orderdetailsintent.putExtra("position",pos);
                    orderdetailsintent.putExtra("orderid",ORDERID);
                    orderdetailsintent.putExtra("useremail",email);
                    itemView.getContext().startActivity(orderdetailsintent);
                }

            });


        }

    }
}
