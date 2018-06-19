package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ara.sunflowerorder.models.Brand;
import com.ara.sunflowerorder.models.Delivery;
import com.ara.sunflowerorder.models.Product;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static com.ara.sunflowerorder.utils.AppConstants.BRAND_ID_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_APPROVE_ID_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_BRAND_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.List_PRODUCT_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.getBrandListURL;
import static com.ara.sunflowerorder.utils.AppConstants.getProductListURL;

public class ListHelperActivity extends AppCompatActivity {

    private int requestCode;
    int searchId;
    @BindView(R.id.list_helper)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_helper);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        requestCode = intent.getIntExtra(REQUEST_CODE, -1);

        searchId = intent.getIntExtra(BRAND_ID_PARAM, -1);
        fetchData();
    }

    private HttpRequest getHttpRequest() {
        HttpRequest httpRequest = null;
        switch (requestCode) {
            case LIST_APPROVE_ID_REQUEST:
                httpRequest = new HttpRequest(getBrandListURL(), HttpRequest.GET);
                break;
            case LIST_BRAND_REQUEST:
                httpRequest = new HttpRequest(getBrandListURL(), HttpRequest.GET);
                break;
            case List_PRODUCT_REQUEST:
                httpRequest = new HttpRequest(getProductListURL(), HttpRequest.GET);
                httpRequest.addParam(BRAND_ID_PARAM, searchId + "");
                break;
        }
        return httpRequest;
    }

    public void fetchData() {
        HttpRequest httpRequest = getHttpRequest();
        if (httpRequest == null) {
            Snackbar.make(listView, "Mr Developer: Please set the Request Code:",
                    Snackbar.LENGTH_INDEFINITE).show();
            return;
        }
        new HttpCaller(this, "Loading data..") {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.getStatus() == HttpResponse.ERROR) {
                    Snackbar.make(listView, response.getMesssage(), Snackbar.LENGTH_LONG).show();
                } else {
                    setListView(response.getMesssage());
                }
            }
        }.execute(httpRequest);
    }

    private void setListView(String json) {
        switch (requestCode) {
            case LIST_APPROVE_ID_REQUEST:
                List<Delivery> deliveryListList = Delivery.fromJSONArray(json);
                ArrayAdapter<Delivery> deliveryArrayAdapter = new ArrayAdapter<Delivery>(
                        listView.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        deliveryListList
                );
                listView.setAdapter(deliveryArrayAdapter);
                break;
            case LIST_BRAND_REQUEST:
                List<Brand> brandList = Brand.fromJSONArray(json);
                ArrayAdapter<Brand> brands = new ArrayAdapter<Brand>(
                        listView.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        brandList
                );
                listView.setAdapter(brands);
                break;

            case List_PRODUCT_REQUEST:
                List<Product> productList = Product.fromJSONArray(json);
                ArrayAdapter<Product> products = new ArrayAdapter<Product>(
                        listView.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        productList
                );
                listView.setAdapter(products);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @OnItemClick(R.id.list_helper)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String result = null;
        switch (requestCode) {
            case LIST_APPROVE_ID_REQUEST:
                Delivery delivery = (Delivery) listView.getAdapter().getItem(position);
                result = delivery.toJson();
                break;
            case LIST_BRAND_REQUEST:
                Brand brand = (Brand) listView.getAdapter().getItem(position);
                result = brand.toJson();
                break;
            case List_PRODUCT_REQUEST:
                Product product = (Product) listView.getAdapter().getItem(position);
                result = product.toJson();
                break;

        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SEARCH_RESULT, result);
        setResult((result != null) ? RESULT_OK : RESULT_CANCELED, intent);
        finish();
    }

}