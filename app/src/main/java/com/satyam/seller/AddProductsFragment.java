package com.satyam.seller;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductsFragment extends Fragment {

    public AddProductsFragment() {
        // Required empty public constructor
    }
    private Dialog loadingDialog;
    private Button add_prod_btn;
    private DocumentSnapshot documentSnapshot;
    private FirebaseUser currentuser;
    private FirebaseFirestore firebaseFirestore;
    private String [] maincategorylist;
    private Spinner maincategoryspinner;
    private String mainselectedcategory;
    private int Position;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_products, container, false);
        add_prod_btn=view.findViewById(R.id.add_prod_btn);
        maincategoryspinner = view.findViewById(R.id.categorylist);

        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();

        ////////////category list////////
        maincategorylist= getResources().getStringArray(R.array.categories);
        ArrayAdapter spinneradapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,maincategorylist);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        maincategoryspinner.setAdapter(spinneradapter);

        maincategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    mainselectedcategory = maincategorylist[position];
                    Position = position;
                }
                else
                {

                    mainselectedcategory = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Select Category", Toast.LENGTH_SHORT).show();

            }
        });

////////////category list//////// /

        final DocumentReference documentReference = firebaseFirestore.collection("SELLERS")
                .document(currentuser.getEmail());

        add_prod_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainselectedcategory != null) {
                    Intent finalintent = new Intent(getContext(), AddProduct.class);
                    finalintent.putExtra("Position",Position);
                    startActivity(finalintent);
                }else{
                    Toast.makeText(getContext(), "Select Product Category", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }
}
