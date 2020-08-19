package com.satyam.seller;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddedProductsFragment extends Fragment {

    public AddedProductsFragment() {
        // Required empty public constructor
    }

    private RecyclerView addedproductlistrecyclerview;
    private Dialog loadingDialog;
    public static AddedProductsAdapter addedProductsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_added_products, container, false);
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        addedproductlistrecyclerview= view.findViewById(R.id.my_added_products_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addedproductlistrecyclerview.setLayoutManager(linearLayoutManager);

        if(DBqueries.addedProductsModelList.size()==0)
        {
            DBqueries.addedproductlist.clear();
            DBqueries.loadaddedproductlist(getContext(),loadingDialog,true);
        }
        else{
            loadingDialog.dismiss();
        }


        addedProductsAdapter = new AddedProductsAdapter(DBqueries.addedProductsModelList,true);
        addedproductlistrecyclerview.setAdapter(addedProductsAdapter);
        addedProductsAdapter.notifyDataSetChanged();
        return view;

    }
}
