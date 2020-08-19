package com.satyam.seller;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryOrderFragment extends Fragment {

    private Button neworder, delivery, outdelivery, completed;

    public DeliveryOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_delivery_order, container, false);

        neworder = view.findViewById(R.id.neworder);
        delivery = view.findViewById(R.id.delivery);
        outdelivery = view.findViewById(R.id.outdelivery);
        completed = view.findViewById(R.id.completed);

        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),DeliveryCategoryActivity.class);
                intent.putExtra("type","neworder");
                startActivity(intent);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),PackedCategoryActivity.class);
                intent.putExtra("type","delivery");
                startActivity(intent);
            }
        });
        outdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),ShippedCategoryActivity.class);
                intent.putExtra("type","outdelivery");
                startActivity(intent);
            }
        });
        completed .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),DeliveredCategoryActivity.class);
                intent.putExtra("type","completed");
                startActivity(intent);
            }
        });

        return view;
    }
}
