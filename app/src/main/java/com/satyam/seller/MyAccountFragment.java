package com.satyam.seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    private TextView name,ownername,phone,email,city,address;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View view= inflater.inflate(R.layout.fragment_my_account, container, false);
        name = view.findViewById(R.id.shopname);
        ownername = view.findViewById(R.id.ownername);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        city = view.findViewById(R.id.city);
        address = view.findViewById(R.id.address);

        name.setText(DBqueries.shopname);
        ownername.setText(DBqueries.shopowner);
        phone.setText(DBqueries.phone);
        email.setText(DBqueries.shopemail);
        city.setText(DBqueries.city);
        address.setText(DBqueries.address);
              return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        name.setText(DBqueries.shopname);
        ownername.setText(DBqueries.shopowner);
        phone.setText(DBqueries.phone);
        email.setText(DBqueries.shopemail);
        city.setText(DBqueries.city);
        address.setText(DBqueries.address);

    }
}
