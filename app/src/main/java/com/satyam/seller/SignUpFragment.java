package com.satyam.seller;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }
    private TextView already_have_account;
    private FrameLayout parentFrameLayout;
    private EditText email,password,fullname,confirmPassword,ownername, address, phone;
    private Button SignUpBtn;
    private FirebaseAuth firebaseAuth;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseFirestore firebaseFirestore;
    private String selectedcity;
    private String []citylist;
    private Dialog loadingDialog;
    private Spinner cityspinner;
    private static long Position;
    private Boolean g=false,e=false,m=false,foo=false,b=false,f=false,s=false,o=false;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        already_have_account=view.findViewById(R.id.have_account);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        email= view.findViewById(R.id.sign_up_email);
        password= view.findViewById(R.id.sign_up_password);
        fullname= view.findViewById(R.id.sign_up_name);
        ownername= view.findViewById(R.id.shopkeepername);
        address= view.findViewById(R.id.shopaddress);
        phone= view.findViewById(R.id.phonenumber);
        confirmPassword= view.findViewById(R.id.sign_up_confirm_password);
        SignUpBtn= view.findViewById(R.id.sign_up_button);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        cityspinner = view.findViewById(R.id.city_spinner);


        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);




        citylist= getResources().getStringArray(R.array.city);
        selectedcity = citylist[0];
        ArrayAdapter spinneradapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,citylist);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityspinner.setAdapter(spinneradapter);
        cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedcity = citylist[position];
                    Position = position;

                }else {
                    if (position == 0) {
                        selectedcity = null;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFragment(new SignInFragment());
            }
        });




        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ownername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });






        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                SignUpBtn.setVisibility(View.INVISIBLE);
                checkEmailAndPassword();
            }
        });
    }

    private void checkEmailAndPassword() {
        if(selectedcity!=null && Position!=0) {
            if (email.getText().toString().matches(emailpattern)) {
                if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        final Map<String, Object> userdata = new HashMap<>();
                                        userdata.put("shop_name", fullname.getText().toString());
                                        userdata.put("seller_uid", firebaseAuth.getUid());
                                        userdata.put("shop_address", address.getText().toString());
                                        userdata.put("owner_phone", phone.getText().toString());
                                        userdata.put("shop_city", selectedcity);
                                        userdata.put("owner_name", ownername.getText().toString());
                                        userdata.put("shop_email", email.getText().toString());

                                        firebaseFirestore.collection("SELLERS").document(firebaseAuth.getCurrentUser().getEmail())
                                                .set(userdata)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            CollectionReference userdatareference = firebaseFirestore.collection("SELLERS").document(firebaseAuth.getCurrentUser().getEmail()).
                                                                    collection("SELLER_DATA");

                                                            ////////////Maps
                                                            Map<String, Object> myproduct = new HashMap<>();
                                                            myproduct.put("listsizeproduct", (long) 0);

                                                            ///////////////maps


                                                            List<String> documentnames = new ArrayList<>();
                                                            documentnames.add("MY_PRODUCTS");


                                                            final List<Map<String, Object>> documentfields = new ArrayList<>();
                                                            documentfields.add(myproduct);


                                                            for (int x = 0; x < documentnames.size(); x++) {

                                                                final int finalX = x;
                                                                userdatareference.document(documentnames.get(x)).set(documentfields.get(x))
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                if (task.isSuccessful()) {
                                                                                    if (finalX == documentfields.size() - 1) {
                                                                                        Map<String,Map<String,Object>> params = new HashMap<>();
                                                                                        params.put(email.getText().toString(),userdata);

                                                                                        Map<Object,Object> order = new HashMap<>();
                                                                                        order.put(selectedcity,params);

                                                                                        firebaseFirestore.collection("CATEGORIES").document("HOME").set(order, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                                if(task.isSuccessful()){
                                                                                                    mainintent();
                                                                                                }else{
                                                                                                    Toast.makeText(getContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                                                                                                }

                                                                                            }
                                                                                        });

                                                                                    }

                                                                                } else {
                                                                                    String error = task.getException().getMessage();
                                                                                    loadingDialog.dismiss();
                                                                                    SignUpBtn.setVisibility(View.VISIBLE);
                                                                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                                                                }

                                                                            }
                                                                        });
                                                            }


                                                        } else {

                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                                        }

                                                    }
                                                });


                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                        loadingDialog.dismiss();
                                        SignUpBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    loadingDialog.dismiss();
                    SignUpBtn.setVisibility(View.VISIBLE);
                    confirmPassword.setError("Password doesn't match");
                }
            } else {
                loadingDialog.dismiss();
                SignUpBtn.setVisibility(View.VISIBLE);
                email.setError("Invalid Email");
            }
        }else{
            loadingDialog.dismiss();
            SignUpBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Select City", Toast.LENGTH_SHORT).show();
        }
    }

    private void mainintent() {
          loadingDialog.dismiss();
            SignUpBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Your account is successfully created.", Toast.LENGTH_SHORT).show();
            Intent mainintent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainintent);
        getActivity().finish();
    }
    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())&& password.length()>=6){

                if(!TextUtils.isEmpty(confirmPassword.getText()))
                {
                    if(!TextUtils.isEmpty(fullname.getText())){
                        if (!TextUtils.isEmpty(address.getText())) {
                            if (!TextUtils.isEmpty(ownername.getText())) {
                                if(!TextUtils.isEmpty(phone.getText())) {
                                    SignUpBtn.setEnabled(true);
                                    SignUpBtn.setTextColor(getResources().getColor(R.color.colorAccent));
                                }else{
                                    SignUpBtn.setEnabled(false);
                                    SignUpBtn.setTextColor(Color.argb(100,255,255,255));
                                }
                            }else{
                                SignUpBtn.setEnabled(false);
                                SignUpBtn.setTextColor(Color.argb(100,255,255,255));
                            }
                            }else{
                            SignUpBtn.setEnabled(false);
                            SignUpBtn.setTextColor(Color.argb(100,255,255,255));
                        }
                    }
                    else
                    {
                        SignUpBtn.setEnabled(false);
                        SignUpBtn.setTextColor(Color.argb(100,255,255,255));
                    }
                }else{
                    SignUpBtn.setEnabled(false);
                    SignUpBtn.setTextColor(Color.argb(100,255,255,255));
                }

            }else
            {
                SignUpBtn.setEnabled(false);
                SignUpBtn.setTextColor(Color.argb(100,255,255,255));
            }
        }
        else
        {
            SignUpBtn.setEnabled(false);
            SignUpBtn.setTextColor(Color.argb(100,255,255,255));
        }

    }


    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_out);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}
