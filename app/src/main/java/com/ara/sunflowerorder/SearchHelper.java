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
import com.ara.sunflowerorder.models.Customer;
import com.ara.sunflowerorder.models.Product;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_CUSTOMER;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_BRAND_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_CUSTOMER_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_PRODUCT_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.getBrandListURL;
import static com.ara.sunflowerorder.utils.AppConstants.getCustomerListURL;
import static com.ara.sunflowerorder.utils.AppConstants.getProductListURL;

public class SearchHelper extends AppCompatActivity {

    int requestCode;
    @BindView(R.id.list_view_items)
    ListView listViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_helper);
        ButterKnife.bind(this);
        requestCode = getIntent().getIntExtra(REQUEST_CODE, -1);

    }

    private HttpRequest getHttpRequest() {
        HttpRequest httpRequest = null;
        switch (requestCode) {
            case SEARCH_CUSTOMER_REQUEST:
                httpRequest = new HttpRequest(getCustomerListURL(), HttpRequest.GET);
                break;
            case SEARCH_BRAND_REQUEST:
                httpRequest = new HttpRequest(getBrandListURL(), HttpRequest.GET);
                break;
            case SEARCH_PRODUCT_REQUEST:
                httpRequest = new HttpRequest(getProductListURL(), HttpRequest.GET);
                break;
        }
        return httpRequest;
    }

    @OnClick(R.id.search_btn)
    public void searchName(View view) {
        HttpRequest httpRequest =getHttpRequest();
        new HttpCaller(this, "Loading data..") {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.getStatus() == HttpResponse.ERROR) {
                    Snackbar.make(listViewItems, response.getMesssage(), Snackbar.LENGTH_LONG).show();
                } else {
                    setListView(response.getMesssage());
                }
            }
        }.execute(httpRequest);
    }

    private void setListView(String json) {
        switch (requestCode) {
            case SEARCH_CUSTOMER_REQUEST:
                List<Customer> customerList = Customer.fromJSONArray(json);
                ArrayAdapter<Customer> customers = new ArrayAdapter<Customer>(
                        listViewItems.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        customerList
                );
                listViewItems.setAdapter(customers);
                break;
            case SEARCH_BRAND_REQUEST:
                List<Brand> brandList = Brand.fromJSONArray(json);
                ArrayAdapter<Brand> brands = new ArrayAdapter<Brand>(
                        listViewItems.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        brandList
                );
                listViewItems.setAdapter(brands);
                break;
            case SEARCH_PRODUCT_REQUEST:
                List<Product> productList = Product.fromJSONArray(json);
                ArrayAdapter<Product> products = new ArrayAdapter<Product>(
                        listViewItems.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        productList
                );
                listViewItems.setAdapter(products);
                break;
        }


    }

    @OnItemClick(R.id.list_view_items)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (requestCode) {
            case SEARCH_CUSTOMER_REQUEST:
                Intent customerListResult = new Intent();
                Customer selectedCustomer = (Customer) listViewItems.getAdapter().getItem(position);
                customerListResult.putExtra(EXTRA_SELECTED_CUSTOMER, selectedCustomer.toJson());
                setResult(RESULT_OK, customerListResult);
                finish();
                break;
        }

    }
}
