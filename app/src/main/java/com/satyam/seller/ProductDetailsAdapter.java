package com.satyam.seller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    private String productDescription;
    private String productotherdetails;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailsAdapter(@NonNull FragmentManager fm, int totalTabs, String productDescription, String productotherdetails, List<ProductSpecificationModel> productSpecificationModelList) {
        //noinspection deprecation
        super(fm);
        this.totalTabs = totalTabs;
        this.productDescription = productDescription;
        this.productotherdetails = productotherdetails;
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                productDescriptionFragment1.body=productDescription;
                return productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelList = productSpecificationModelList;
                return productSpecificationFragment;
            case 2:
                ProductDescriptionFragment productDescriptionFragment2 = new ProductDescriptionFragment();
                productDescriptionFragment2.body=productotherdetails;
                return productDescriptionFragment2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
