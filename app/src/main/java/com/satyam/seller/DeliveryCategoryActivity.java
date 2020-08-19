package com.satyam.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

public class DeliveryCategoryActivity extends AppCompatActivity {
    private Dialog loadingDialog;
    private RecyclerView mydeliveryodersrecyclerview;
    public static MyOrdersAdapter myOrdersAdapter;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_category);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        type = getIntent().getStringExtra("type");
        getSupportActionBar().setTitle("New Orders");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();


        mydeliveryodersrecyclerview = findViewById(R.id.delivery_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mydeliveryodersrecyclerview.setLayoutManager(linearLayoutManager);

        DBqueries.loadorders(this, loadingDialog, true,"ordered");

        myOrdersAdapter = new MyOrdersAdapter(DBqueries.myOrderItemModelList, loadingDialog);
        mydeliveryodersrecyclerview.setAdapter(myOrdersAdapter);
        myOrdersAdapter.notifyDataSetChanged();



       }
    }


