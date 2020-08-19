package com.satyam.seller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean addedproductlistquery = false;
    public static Activity productdetailsactivity;


    private ViewPager productImageViewPager;
    private TabLayout viewpagerindicator;
    private TextView producttitle, productshop, averageratingminiview, totalratingsminiview, productprice, cuttedprice,
            tvcodindicator;
    private ImageView codindicator;
    private Button coupounredeembtn;
    private LinearLayout coupounredemptionlayout;


    private TextView rewardtitle;
    private TextView rewardbody;

    /////////////////productdescriptionvariables

    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTablayout;
    private ConstraintLayout productdetailsonlycontainer;
    private ConstraintLayout productdetailstabcontainer;
    private TextView productonlydescriptionbody;

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();


    private String productDescription;
    private String productotherdetails;


    //////////////////////////productdescriptionvariables


    public static FloatingActionButton addTowishlist;
    public static boolean alraedyaddedTocartlist = false;


    ////////////ratings layout
    public static LinearLayout ratenowcontainer;
    private TextView totalratings;
    private LinearLayout ratingnocontainer;
    private TextView totalratingsfigure;
    private LinearLayout ratingsprogressbarcontainer;
    private TextView averageratings;
    public static int initialrating;
    //////////////ratings layout


    private Button buynowbtn;
    private LinearLayout addtocartbtn;
    public static  MenuItem cartitem;
    private  TextView badgecount;

    private FirebaseFirestore firebaseFirestore;
    ////coupoundialog

    public static TextView coupountitle, coupounexpirdate, coupounbody;
    private static RecyclerView coupounsrecyclerview;
    private static LinearLayout selectedcoupoun;

    //////coupoundialog


    private Dialog sign_in_dialog;
    private Dialog loadingDialog;
    private FirebaseUser currentuser;
    public static String productid;


    private DocumentSnapshot documentSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTablayout = findViewById(R.id.product_details_tablayout);
        productImageViewPager = findViewById(R.id.product_image_viewpager);
        viewpagerindicator = findViewById(R.id.viewpager_indicator);
        producttitle = findViewById(R.id.product_order_id);
        averageratingminiview = findViewById(R.id.tv_product_rating_miniview);
        totalratingsminiview = findViewById(R.id.total_ratings_miniview);
        productprice = findViewById(R.id.product_price);
        cuttedprice = findViewById(R.id.cutted_price);
        codindicator = findViewById(R.id.cod_indicatorIimageview);
        tvcodindicator = findViewById(R.id.tv_COD_indicator);
        productdetailstabcontainer = findViewById(R.id.product_details_tabs_container);
        productdetailsonlycontainer = findViewById(R.id.product_details_container);
        productonlydescriptionbody = findViewById(R.id.product_details_body);
        totalratings = findViewById(R.id.total_ratings);
        averageratings = findViewById(R.id.average_rating);
        productid = getIntent().getStringExtra("productID");

        initialrating = -1;


        ////////////loading dialog////////
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////////////loading dialog///////////


        firebaseFirestore = FirebaseFirestore.getInstance();

        final List<String> productImages = new ArrayList<>();
        firebaseFirestore.collection("PRODUCTS").document(productid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    documentSnapshot = task.getResult();

                    for (long x = 1; x < ((long) documentSnapshot.get("no_of_product_images")) + 1; x++) {
                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImageViewPager.setAdapter(productImagesAdapter);
                    producttitle.setText(documentSnapshot.get("product_title").toString());
                    averageratingminiview.setText(documentSnapshot.get("average_rating").toString());
                    totalratingsminiview.setText("(" + (long) documentSnapshot.get("total_ratings") + ")ratings");
                    productprice.setText("₹ " + documentSnapshot.get("product_price").toString() + "/-");
                    cuttedprice.setText("₹ " + documentSnapshot.get("cutted_price").toString() + "/-");

                    if ((boolean) documentSnapshot.get("COD")) {
                        codindicator.setVisibility(View.VISIBLE);
                        tvcodindicator.setVisibility(View.VISIBLE);
                    } else {
                        codindicator.setVisibility(View.INVISIBLE);
                        tvcodindicator.setText("COD Not Available");
                    }

//                    rewardtitle.setText((long) documentSnapshot.get("free_coupouns") + documentSnapshot.get("free_coupoun_title").toString());
  //                  rewardbody.setText(documentSnapshot.get("free_coupoun_body").toString());

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productdetailstabcontainer.setVisibility(View.VISIBLE);
                        productdetailsonlycontainer.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("product_description").toString();

                        productotherdetails = documentSnapshot.get("product_other_details").toString();

                        for (long x = 1; x < (long) documentSnapshot.get("total_specs_titles") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel
                                    (0, documentSnapshot.get("spec_title_" + x).toString()));

                            for (long y = 1; y < ((long) documentSnapshot.get("spec_title_" + x + "_total_fields")) + 1; y++) {
                                productSpecificationModelList.add(new ProductSpecificationModel
                                        (1, documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name").toString()
                                                , documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value").toString()
                                        ));

                            }

                        }

                    } else {
                        productdetailstabcontainer.setVisibility(View.GONE);
                        productdetailsonlycontainer.setVisibility(View.VISIBLE);
                        productonlydescriptionbody.setText(documentSnapshot.get("product_description").toString());

                    }

                    totalratings.setText((long) documentSnapshot.get("total_ratings") + " ratings");
                    averageratings.setText(documentSnapshot.get("average_rating").toString());
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTablayout.getTabCount(), productDescription, productotherdetails, productSpecificationModelList));


                    if (DBqueries.addedproductlist.size() == 0) {
                        DBqueries.loadaddedproductlist(ProductDetailsActivity.this, loadingDialog, false);
                    } else {
                        loadingDialog.dismiss();
                    }

                } else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewpagerindicator.setupWithViewPager(productImageViewPager, true);

        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

            if (DBqueries.addedproductlist.size() == 0) {
                DBqueries.loadaddedproductlist(ProductDetailsActivity.this, loadingDialog, false);

            } else {
                loadingDialog.dismiss();
            }
        invalidateOptionsMenu();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            productdetailsactivity=null;
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.mian_cart) {

                Intent cartintent = new Intent(ProductDetailsActivity.this, MainMainActivity.class);
              //  showCart = true;
                startActivity(cartintent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        productdetailsactivity = null;
        super.onBackPressed();
    }
}
