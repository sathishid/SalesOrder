package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.ara.sunflowerorder.models.Collection;
import com.ara.sunflowerorder.models.Customer;
import com.ara.sunflowerorder.models.Invoice;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_CUSTOMER_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class CollectionActivity extends AppCompatActivity {

    Customer customer;
    Collection collection;

    @BindView(R.id.tv_coll_customer)
    TextView tvCustomer;
    @BindView(R.id.tv_coll_date)
    TextView tvTodayDate;
    @BindView(R.id.tv_coll_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.spinner_coll_payment_mode)
    Spinner spinnerPaymentMode;
    @BindView(R.id.coll_item_list_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collection = new Collection();
    }

    @OnClick(R.id.tv_coll_customer)
    public void selectCustomer(View view) {
        Intent customerList = new Intent(this, SearchHelper.class);
        customerList.putExtra(REQUEST_CODE, SEARCH_CUSTOMER_REQUEST);
        startActivityForResult(customerList, SEARCH_CUSTOMER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SEARCH_CUSTOMER_REQUEST:
                if (resultCode == RESULT_OK) {
                    String json = data.getStringExtra(EXTRA_SEARCH_RESULT);
                    customer = Customer.fromJSON(json);

                    HttpRequest httpRequest=new HttpRequest(AppConstants.getInvoiceListURL(),HttpRequest.GET);
                    httpRequest.addParam(AppConstants.CUSTOMER_ID_PARAM,customer.getId()+"");
                    new HttpCaller(this,"Loading Invoices"){
                        @Override
                        public void onResponse(HttpResponse response) {
                            if(response.getStatus()== HttpResponse.ERROR){
                                showSnackbar(tvCustomer,"Somenthing went wrong, contact support");
                            }
                            else{
                                String json=response.getMesssage();
                                List<Invoice> invoices=Invoice.fromJSONArray(json);

                            }
                        }
                    }.execute(httpRequest);

                    tvCustomer.setText(customer.getName());
                    collection.setCustomer(customer);
                    collection.setDate(AppConstants.dateToString(Calendar.getInstance()));
                    tvTodayDate.setText(collection.getDate());
                }
                break;
        }
    }
}
