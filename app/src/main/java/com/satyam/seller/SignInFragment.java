package com.satyam.seller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }
    private TextView dont_have_account, forgot_password;
    private FrameLayout parentFrameLayout;
    private EditText email,password;
    private Button signinbtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseFirestore firebaseFirestore;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        dont_have_account=view.findViewById(R.id.dont_have_account);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        email= view.findViewById(R.id.sign_in_email);
        password= view.findViewById(R.id.sign_in_password);
        signinbtn=view.findViewById(R.id.sign_in_button);
        progressBar=view.findViewById(R.id.sign_in_progressbar);
        firebaseAuth=FirebaseAuth.getInstance();
        forgot_password=view.findViewById(R.id.forgot_password);
        firebaseFirestore = FirebaseFirestore.getInstance();


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFragment(new SignUpFragment());
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent forgotintent = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(forgotintent);

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


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                signinbtn.setVisibility(View.INVISIBLE);
                checkEmailAndPassword();
            }
        });

    }

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches(emailpattern)) {
            if(password.length()>=6){

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                 mainintent();
                                }
                                else
                                {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    signinbtn.setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else{
                progressBar.setVisibility(View.GONE);
                signinbtn.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            progressBar.setVisibility(View.GONE);
            signinbtn.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void mainintent() {

            progressBar.setVisibility(View.GONE);
            signinbtn.setVisibility(View.VISIBLE);
            Intent mainintent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainintent);
        getActivity().finish();
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())&& password.length()>=6)
            {
                signinbtn.setEnabled(true);

            }
            else
            {

                signinbtn.setEnabled(false);
            }
        }
        else
        {

            signinbtn.setEnabled(false);

        }

    }


    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.animation,R.anim.slide_left_out);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
