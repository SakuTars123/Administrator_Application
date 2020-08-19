package com.satyam.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

public class AllOrderDetailsActivity extends AppCompatActivity {
    private Dialog loadingDialog;
    private int position;
    private String orderid,email;
    private RecyclerView myodersrecyclerview;
    public static OrderDetailsAdapter myOrdersdetailsAdapter;
    public static TextView ordertotalamount, ordertakenamount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order_details);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ordertotalamount=findViewById(R.id.order_price);
        ordertakenamount=findViewById(R.id.accepted_price);

        ////////////loading dialog////////
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////////////loading dialog///////////

        myodersrecyclerview = findViewById(R.id.recyclerVieworder);

        position=getIntent().getIntExtra("position",-1);
        email= getIntent().getStringExtra("useremail");
        orderid= getIntent().getStringExtra("orderid");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myodersrecyclerview.setLayoutManager(linearLayoutManager);


        DBqueries.loadoderdetails(this,loadingDialog,orderid);

        myOrdersdetailsAdapter = new OrderDetailsAdapter(DBqueries.myOrderItemModeldetailsList,loadingDialog,email);
        myodersrecyclerview.setAdapter(myOrdersdetailsAdapter);
        myOrdersdetailsAdapter.notifyDataSetChanged();

    }

}
