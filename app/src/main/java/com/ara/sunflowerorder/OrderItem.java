package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;

import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_BRAND_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_PRODUCT_REQUEST;

public class OrderItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
    }

    @OnClick({R.id.tv_order_product_name, R.id.tv_order_brandName})
    public void chooseItem(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_order_product_name:
                Intent chooseProduct = new Intent(this, SearchHelper.class);
                startActivityForResult(chooseProduct, SEARCH_PRODUCT_REQUEST);
                break;
            case R.id.tv_order_brandName:
                Intent chooseBrand = new Intent(this, SearchHelper.class);
                startActivityForResult(chooseBrand, SEARCH_BRAND_REQUEST);
                break;
        }
    }

   
}
