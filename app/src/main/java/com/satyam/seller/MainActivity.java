package com.satyam.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;


public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;

    private EditText finalkey;
    private Button finalbtn;
    private ImageView entry;
    private ProgressBar finalprogressbar;
    private DocumentSnapshot documentSnapshot;
    private FirebaseUser currentuser;
    public static String shopuid;
    public  static String shopname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finalkey = findViewById(R.id.sign_in_admin_passkey);
        finalbtn = findViewById(R.id.final_sign_in_btn);
        entry = findViewById(R.id.successful_final_enrty);
        finalprogressbar = findViewById(R.id.progressBarfinal);
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();



        final DocumentReference documentReference = firebaseFirestore.collection("SELLERS")
                .document(currentuser.getEmail());


        finalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalprogressbar.setVisibility(View.VISIBLE);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            documentSnapshot = task.getResult();
                            DBqueries.shopname = documentSnapshot.get("shop_name").toString();
                            shopuid = documentSnapshot.get("seller_uid").toString();
                            DBqueries.shopemail = documentSnapshot.get("shop_name").toString();
                            DBqueries.shopowner = documentSnapshot.get("owner_name").toString();
                            DBqueries.city = documentSnapshot.get("shop_city").toString();
                            DBqueries.phone = documentSnapshot.get("owner_phone").toString();
                            DBqueries.address = documentSnapshot.get("shop_address").toString();
                            DBqueries.shopemail = documentSnapshot.get("shop_email").toString();


                                if(finalkey.getText().toString().equals( shopuid)){
                                    Intent finalintent = new Intent(MainActivity.this,MainMainActivity.class);
                                    startActivity(finalintent);
                                    finalprogressbar.setVisibility(View.GONE);
                                    finish();
                                }
                                else{
                                    finalprogressbar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "Key does not match .Contact Seller Customer Care", Toast.LENGTH_SHORT).show();
                                }


                        }
                        else{ finalprogressbar.setVisibility(View.GONE);
                            String error = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}