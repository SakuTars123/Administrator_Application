package com.satyam.seller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText resetemail;
    private Button resetbtn;
    private ImageView cancel_reset_btn,success_reset;
    private ProgressBar progressBar;
    private TextView textView;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        resetbtn = findViewById(R.id.sign_in_button_reset);
        resetemail=findViewById(R.id.sign_in_email_reset);
        cancel_reset_btn= findViewById(R.id.cancel_button_reset_password);
        success_reset=findViewById(R.id.reset_successful);
        progressBar=findViewById(R.id.progressBar_reset);
        textView=findViewById(R.id.text_success);
        firebaseAuth = FirebaseAuth.getInstance();



        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { progressBar.setVisibility(View.VISIBLE);
            checkinputsreset();
            }
        });

        cancel_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainintent = new Intent(ResetPasswordActivity.this, RegistrationActivity.class);
                startActivity(mainintent);
                finish();

            }
        });

    }

    private void checkinputsreset() {
        if(TextUtils.isEmpty(resetemail.getText())) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
        }

        else{

            if(resetemail.getText().toString().matches(emailpattern)){
                firebaseAuth.sendPasswordResetEmail(resetemail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {

                                    progressBar.setVisibility(View.GONE);
                                    textView.setVisibility(View.VISIBLE);
                                    success_reset.setVisibility(View.VISIBLE);
                                    resetbtn.setVisibility(View.INVISIBLE);
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(ResetPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                 }
            else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            }


            }

        }
    }

