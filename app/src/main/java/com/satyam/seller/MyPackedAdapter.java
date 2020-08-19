package com.satyam.seller;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyPackedAdapter extends RecyclerView.Adapter<MyPackedAdapter.ViewHolder> {
    private List<MyOrderItemModel> myOrderItemModelList;
    private Dialog loadingDialog;

    public MyPackedAdapter(List<MyOrderItemModel> myOrderItemModelList, Dialog loadingDialog) {
        this.myOrderItemModelList = myOrderItemModelList;
        this.loadingDialog = loadingDialog;

    }


    @NonNull
    @Override
    public MyPackedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_packed_item_layout, viewGroup, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyPackedAdapter.ViewHolder viewHolder, int position) {

        HashMap<String,String > sellerstatus = myOrderItemModelList.get(position).getSellerstatus();
        String orderid = myOrderItemModelList.get(position).getOrderid();
        Date orderdate = myOrderItemModelList.get(position).getOderdate();

        viewHolder.setdataneworder(sellerstatus, orderid, orderdate, position);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView orderid;
        private TextView orderdate;
        private TextView OrderPrice;
        private ConstraintLayout neworderlayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid= itemView.findViewById(R.id.product_order_id_packed);
            orderdate= itemView.findViewById(R.id.order_time_packed);
            OrderPrice= itemView.findViewById(R.id.total_order_price_packed);
            neworderlayout=itemView.findViewById(R.id.order_constraint_layout_packed);
        }

        private void setdataneworder(HashMap<String, String> Sellerstatus, final String ORDERID, Date DATE, final long pos){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM YYYY hh:mm aa");


            if(Sellerstatus.get(DBqueries.shopemail).equals("packed"))
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
                    itemView.getContext().startActivity(orderdetailsintent);
                }

            });


        }

    }
}
