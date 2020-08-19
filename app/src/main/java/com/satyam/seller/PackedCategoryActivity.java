package com.satyam.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;

public class PackedCategoryActivity extends AppCompatActivity {
    public static MyPackedAdapter mypackedorderadapter;
    private String type;
    private Dialog loadingDialog;
    private RecyclerView mydeliveryodersrecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packed_category);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        type = getIntent().getStringExtra("type");
        getSupportActionBar().setTitle("Packed Orders");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        mydeliveryodersrecyclerview = findViewById(R.id.packed_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mydeliveryodersrecyclerview.setLayoutManager(linearLayoutManager);

        DBqueries.loadorders(this, loadingDialog, true,"packed");

        mypackedorderadapter = new MyPackedAdapter(DBqueries.mypackedmodellist, loadingDialog);
        mydeliveryodersrecyclerview.setAdapter(mypackedorderadapter);
        mypackedorderadapter.notifyDataSetChanged();

    }
}
