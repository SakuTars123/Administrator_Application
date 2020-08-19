package com.satyam.seller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AddedProductsAdapter  extends RecyclerView.Adapter<AddedProductsAdapter.ViewHolder> {


    private List<AddedProductsModel> addedProductsModelList;
    private Boolean Wishlist;
    private int lastposition=-1;

    public AddedProductsAdapter(List<AddedProductsModel> wishListModelList, Boolean Wishlist) {
        this.addedProductsModelList = wishListModelList;
        this.Wishlist=Wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seller_products_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String productid = addedProductsModelList.get(position).getProductid();
        String resource = addedProductsModelList.get(position).getProductimage();
        String title = addedProductsModelList.get(position).getProducttitle();
        long freecoupouns = addedProductsModelList.get(position).getFreecoupouns();
        String rating = addedProductsModelList.get(position).getRating();
        long totalratings = addedProductsModelList.get(position).getTotalratings();
        String productprice = addedProductsModelList.get(position).getProductprice();
        String cuttedprice = addedProductsModelList.get(position).getCuttedprice();
        boolean paymentmethod = addedProductsModelList.get(position).getPaymentmethod();
        String sellername = addedProductsModelList.get(position).getShopname();

        viewHolder.setdata(productid,resource,title,freecoupouns,rating,totalratings,productprice,cuttedprice,paymentmethod,sellername,position);

        if(lastposition<position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastposition=position;
        }

    }

    @Override
    public int getItemCount() {
        return addedProductsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productimage;
        private TextView producttitle;
        private TextView freecoupouns;
        private TextView ratings;
        private TextView totalratings;
        private View pricecut;
        private ImageView coupounicon;
        private TextView productprice;
        private TextView cuttedprice;
        private TextView paymentmethod;
        private ImageButton deletebtn;
        private TextView sellername;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productimage = itemView.findViewById(R.id.product_image_seller);
            producttitle = itemView.findViewById(R.id.product_order_id);
            freecoupouns = itemView.findViewById(R.id.free_coupouns);
            ratings = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalratings = itemView.findViewById(R.id.total_ratings);
            pricecut = itemView.findViewById(R.id.price_cut);
            coupounicon = itemView.findViewById(R.id.coupoun_icon);
            cuttedprice = itemView.findViewById(R.id.cutted_price);
            paymentmethod = itemView.findViewById(R.id.payment_option);
            deletebtn = itemView.findViewById(R.id.delete_button);
            productprice=itemView.findViewById(R.id.product_price);
            sellername= itemView.findViewById(R.id.seller_name);


        }
        private void setdata(final String productid, String resource, String title, long freecoupounsNo, String averagerate, long totalratingsNo, String price, String cuttedpriceValue, boolean COD, String shopname, final int index){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.loadingplaceholders)).into(productimage);

            producttitle.setText(title);
            if(freecoupounsNo!=0){
                coupounicon.setVisibility(View.VISIBLE);
                if(freecoupounsNo==1)
                    freecoupouns.setText("free "+ freecoupounsNo+" coupon");
                else
                {
                    freecoupouns.setText("free "+ freecoupounsNo+" coupons");
                }

            }else{
                coupounicon.setVisibility(View.INVISIBLE);
                freecoupouns.setVisibility(View.INVISIBLE);
            }
            ratings.setText(averagerate);
            totalratings.setText("("+totalratingsNo +")ratings");
            productprice.setText("₹"+price+"/-");
            cuttedprice.setText("₹"+cuttedpriceValue+"/-");
            if(COD) {
                paymentmethod.setVisibility(View.VISIBLE);
                paymentmethod.setText("CASH ON DELIVERY AVAILABLE");

            }
            else
            {
                paymentmethod.setVisibility(View.INVISIBLE);
                paymentmethod.setText("Sorry! Cash On Delivery is not Available");
            }
            sellername.setText(shopname);
            if(Wishlist){
                deletebtn.setVisibility(View.VISIBLE);
            }
            else{
                deletebtn.setVisibility(View.GONE);
            }

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ProductDetailsActivity.addedproductlistquery) {
                        ProductDetailsActivity.addedproductlistquery = true;

                        DBqueries.removefromaddedproductlist(index, itemView.getContext());
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productdetailsintent = new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    productdetailsintent.putExtra("productID",productid);
                    itemView.getContext().startActivity(productdetailsintent);
                }
            });
        }
    }





}
