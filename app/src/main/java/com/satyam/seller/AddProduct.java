package com.satyam.seller;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProduct extends AppCompatActivity {



    private static final int GalleryPick = 1;
    private Uri ImageUri,uri;
    private StorageReference ProductImagesRef;
    private String saveCurrentDate, saveCurrentTime;
    private Button AddNewProductButton;
    private String productRandomKey;
    private DatabaseReference ProductsRef;
    private String downloadImageUrl;
    private String shopname, shopuid;
    private EditText pname, pprice, pcuttedprice, pdetails, pbrand;
    private ImageView selectimage;
    private FirebaseFirestore firebaseFirestore;
    private DocumentSnapshot documentSnapshot;
    private String productid;
    private Dialog loadingDialog;
    private  FirebaseAuth firebaseAuth;
    private String [] categorylist;
    private Spinner categoryspinner;
    private String selectedcategory;
    private String [] selltypelist;
    private Spinner selltypespinner;
    private String selectedselltype;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo ;
    private ImageView nointernetconnection;
    private Button retrybutton;
    private String maincategory;
    private String mainedcategory;
    private  ArrayList<String> tags;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        pname = findViewById(R.id.product_title_full);
        pbrand = findViewById(R.id.brand_name);
        pprice = findViewById(R.id.product_mrp);
        pcuttedprice = findViewById(R.id.product_cuttedprice);
        pdetails = findViewById(R.id.product_description);
        AddNewProductButton = findViewById(R.id.add_new_product_btn);
        selectimage = findViewById(R.id.imagechosebtn);
        categoryspinner = findViewById(R.id.subcategorylist);
        selltypespinner = findViewById(R.id.selltype);

   ////todo     nointernetconnection = findViewById(R.id.no_internet_connection);
   //////todo     retrybutton = findViewById(R.id.retry_button);





        loadingDialog = new Dialog(AddProduct.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();





        firebaseFirestore = FirebaseFirestore.getInstance();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("products");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("PRODUCTS");
        firebaseAuth=FirebaseAuth.getInstance();

        Integer position = getIntent().getIntExtra("Position",-1);


        if(position>0) {
            switch (position) {
                case 1:
                categorylist = getResources().getStringArray(R.array.mobile);
                mainedcategory = "Mobiles Phones Mobile phone accessories Cell";
                break;
                case 2:
                    categorylist = getResources().getStringArray(R.array.laptops);
                    mainedcategory="electronics";
                    break;
                case 3:
                    categorylist = getResources().getStringArray(R.array.speakers);
                    mainedcategory = "mobile and phones";
                    break;
                case 4:
                    categorylist = getResources().getStringArray(R.array.electronics);
                    mainedcategory = "Electronics Home Appliances";
                    break;
                case 5:
                    categorylist = getResources().getStringArray(R.array.groceries);
                    mainedcategory = "books and stationery";
                    break;
                case 6:
                    categorylist = getResources().getStringArray(R.array.footwear);
                    mainedcategory = "fruits and vegetables";
                    break;
                case 7:
                    categorylist = getResources().getStringArray(R.array.books);
                    mainedcategory = "snacks and ice cream";
                    break;
                case 8:
                    categorylist = getResources().getStringArray(R.array.others);
                    mainedcategory = null;
                    break;
            }
        }///////////////////////switch


        ArrayAdapter spinneradapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,categorylist);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoryspinner.setAdapter(spinneradapter);

        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedcategory = categorylist[position];
                }
                else
                {

                    selectedcategory = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddProduct.this, "Select Sub-Category", Toast.LENGTH_SHORT).show();

            }
        });


        selltypelist= getResources().getStringArray(R.array.selltypecategory);
        ArrayAdapter spinneradapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,selltypelist);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selltypespinner.setAdapter(spinneradapter2);

        selltypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedselltype = selltypelist[position];
                }
                else
                {
                    selectedselltype = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddProduct.this, "Select Selltype", Toast.LENGTH_SHORT).show();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                AddNewProductButton.setVisibility(View.GONE);
                ValidateProductImage();
            }
        });


        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if (AddProduct.this.checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent galleryintent = new Intent(Intent.ACTION_PICK);
                        galleryintent.setType("image/*");
                        startActivityForResult(galleryintent, 1);
                    }else
                    {
                        AddProduct.this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                    }
                }else{
                    Intent galleryintent = new Intent(Intent.ACTION_PICK);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, 1);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==AddProduct.this.RESULT_OK){
                if(data!= null){
                    ImageUri = data.getData();
                    selectimage.setImageURI(ImageUri);

                }else{
                    Toast.makeText(AddProduct.this, "Image not selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryintent = new Intent(Intent.ACTION_PICK);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, 1);

            }else{
                Toast.makeText(AddProduct.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ValidateProductImage() {

                if (!TextUtils.isEmpty(pname.getText())) {
                    if(!TextUtils.isEmpty(pbrand.getText())) {
                        if (!TextUtils.isEmpty(pprice.getText())) {
                            if (!TextUtils.isEmpty(pcuttedprice.getText())) {
                                if (selectedcategory!=null) {
                                    if (selectedselltype != null) {
                                        if (!TextUtils.isEmpty(pdetails.getText())) {
                                            if (ImageUri != null) {

                                                StoreProductInformation();

                                            } else {
                                                AddNewProductButton.setVisibility(View.VISIBLE);
                                                loadingDialog.dismiss();
                                                Toast.makeText(AddProduct.this, "Please select image.", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {
                                            AddNewProductButton.setVisibility(View.VISIBLE);
                                            loadingDialog.dismiss();
                                            pdetails.requestFocus();
                                            Toast.makeText(AddProduct.this, "Please provide details of product.", Toast.LENGTH_SHORT).show();

                                        }

                                    } else {
                                        AddNewProductButton.setVisibility(View.VISIBLE);
                                        loadingDialog.dismiss();
                                        selltypespinner.requestFocus();
                                        Toast.makeText(AddProduct.this, "Please provide Rate Type.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    AddNewProductButton.setVisibility(View.VISIBLE);
                                    loadingDialog.dismiss();
                                    categoryspinner.requestFocus();
                                    Toast.makeText(AddProduct.this, "Please provide Product category.", Toast.LENGTH_SHORT).show();
                                    }
                            }else{
                                AddNewProductButton.setVisibility(View.VISIBLE);
                                loadingDialog.dismiss();
                                pcuttedprice.requestFocus();
                                Toast.makeText(AddProduct.this, "Please provide MRP price", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            AddNewProductButton.setVisibility(View.VISIBLE);
                            loadingDialog.dismiss();
                            pprice.requestFocus();

                            Toast.makeText(AddProduct.this, "Please provide Your Best Price .", Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        AddNewProductButton.setVisibility(View.VISIBLE);
                        loadingDialog.dismiss();
                        pbrand.requestFocus();
                        Toast.makeText(AddProduct.this, "Please provide product brand.", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    AddNewProductButton.setVisibility(View.VISIBLE);
                    loadingDialog.dismiss();
                    pname.requestFocus();
                    Toast.makeText(AddProduct.this, "Please provide product name.", Toast.LENGTH_SHORT).show();

                }
            }
    private void StoreProductInformation() {


        if (networkInfo != null && networkInfo.isConnected() == true) {
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            productRandomKey = saveCurrentDate + saveCurrentTime + UUID.randomUUID() + MainActivity.shopname + MainActivity.shopuid;


            final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] fileInBytes = baos.toByteArray();


            final UploadTask uploadTask = filePath.putBytes(fileInBytes);


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismiss();
                    String message = e.toString();
                    Toast.makeText(AddProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddProduct.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();


                            }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();

                                Toast.makeText(AddProduct.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
                                if (downloadImageUrl != null) {

                                    SaveProductInfoToDatabase();
                                } else {
                                    loadingDialog.dismiss();
                                    Toast.makeText(AddProduct.this, "Something went wrong ! Please try again...", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                }
            });
        }else{
            nointernetconnection.setVisibility(View.VISIBLE);
            retrybutton.setVisibility(View.VISIBLE);
        }
    }
    private void SaveProductInfoToDatabase() {

        String pdbname = pname.getText().toString();
        String pdbprice = pprice.getText().toString();
        String pdbcuttedprice = pcuttedprice.getText().toString();
        String pdbdescription = pdetails.getText().toString();
        String pdbbrand = pbrand.getText().toString();
        tags= new ArrayList<>();
 ////////////////////////////////////////////////////////////////////////////////////////////
        searchgenerator(pdbname);
        searchgenerator(pdbbrand);
        searchgenerator(DBqueries.shopname);
        if(mainedcategory!=null) {
            searchgenerator(mainedcategory);
            }
        searchgenerator(selectedcategory);


////////////////////////////////////////////////////////////////////////////////////////////////////
        final HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("selleremail", DBqueries.shopemail);
        productMap.put("ShopName", DBqueries.shopname.trim().toLowerCase());
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("product_image_1", downloadImageUrl);
        productMap.put("product_title", pdbname);
        productMap.put("free_coupouns", 0);
        productMap.put("offers_applied", 1);
        productMap.put("average_rating", "5");
        productMap.put("product_price", pdbprice);
        productMap.put("cutted_price", pdbcuttedprice);
        productMap.put("COD", true);
        productMap.put("product_shop", DBqueries.shopname);
        productMap.put("1_star", 0);
        productMap.put("2_star", 0);
        productMap.put("3_star", 0);
        productMap.put("4_star", 0);
        productMap.put("5_star", 1);
        productMap.put("total_ratings", 1);
        productMap.put("free_coupoun_body", "");
        productMap.put("free_coupoun_title", "");
        productMap.put("in_stock", true);
        productMap.put("product_description", pdbdescription);
        productMap.put("use_tab_layout", true);
        productMap.put("total_specs_titles", 1);
        productMap.put("spec_title_1", "General Info:");
        productMap.put("product_other_details", "");
        productMap.put("no_of_product_images", 1);
        productMap.put("Category",maincategory);
        productMap.put("SubCategory",selectedcategory);
        productMap.put("spec_title_1_field_1_name","Brand");
        productMap.put("spec_title_1_field_1_value",pdbbrand);
        productMap.put("spec_title_1_field_2_name","Category");
        productMap.put("spec_title_1_field_3_name","Sub-Category");
        productMap.put("spec_title_1_field_3_value",selectedcategory);
        productMap.put("spec_title_1_field_2_value",mainedcategory.endsWith(" "));
        productMap.put("spec_title_1_total_fields",3);
        productMap.put("sell-type",selectedselltype);
        productMap.put("is_visible",true);
        productMap.put("city",DBqueries.city);
        productMap.put("tags",tags);
        productMap.put("max-quantity",100);


        firebaseFirestore.collection("PRODUCTS").add(productMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        if (!ProductDetailsActivity.addedproductlistquery) {
                            ProductDetailsActivity.addedproductlistquery = true;

                              productid= documentReference.getId();

                            Map<String,Object> addproductid = new HashMap<>();
                            addproductid.put("product_ID_",productid);
                            firebaseFirestore.collection("PRODUCTS").document(documentReference.getId()).update(addproductid)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){


                                                Map<String, Object> addproduct = new HashMap<>();
                                                addproduct.put("product_ID_" + String.valueOf(DBqueries.addedproductlist.size()), productid);
                                                addproduct.put("listsizeproduct", (long) (DBqueries.addedproductlist.size() + 1));
                                                firebaseFirestore.collection("SELLERS").document(firebaseAuth.getCurrentUser().getEmail()).collection("SELLER_DATA")
                                                        .document("MY_PRODUCTS").update(addproduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                                loadingDialog.dismiss();
                                                            AddNewProductButton.setVisibility(View.VISIBLE);
                                                            ProductDetailsActivity.addedproductlistquery = true;
                                                            DBqueries.addedproductlist.add(productid);
                                                            Toast.makeText(AddProduct.this, "Product Successfully Added. Check in My Products", Toast.LENGTH_SHORT).show();
                                                            //Fragment fragment = new AddProductsFragment();
                                                            //FragmentManager fragmentManager = getSupportFragmentManager();
                                                            //fragmentManager.beginTransaction();
                                                            finish();

                                                        } else {
                                                            AddNewProductButton.setVisibility(View.VISIBLE);
                                                            loadingDialog.dismiss();
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(AddProduct.this, error, Toast.LENGTH_SHORT).show();
                                                        }
                                                        ProductDetailsActivity.addedproductlistquery = false;

                                                    }
                                                });
                                            }
                                            //////////////////////
                                            else {
                                                AddNewProductButton.setVisibility(View.VISIBLE);
                                                loadingDialog.dismiss();
                                                String error = task.getException().getMessage();
                                                Toast.makeText(AddProduct.this, error, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.dismiss();
                        AddNewProductButton.setVisibility(View.VISIBLE);
                        String message = e.toString();
                        Toast.makeText(AddProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddProduct.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

    private void searchgenerator(String search) {

        String[] inputtags3 =search.split("[\\s+.,!@|#$/%^&*():;<>?]");

        System.out.println(Arrays.toString(inputtags3));

        for(int i=0;i<inputtags3.length;i++) {
            if (!tags.contains(inputtags3[i].toLowerCase()) && inputtags3[i]!=null && inputtags3[i]!="") {
                tags.add(inputtags3[i].toLowerCase());
            }
        }
    }


}








