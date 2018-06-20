package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ara.sunflowerorder.adapters.OrderItemAdapter;
import com.ara.sunflowerorder.models.Customer;
import com.ara.sunflowerorder.models.OrderItem;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.DatePickerFragment;
import com.ara.sunflowerorder.utils.DatePickerListener;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.ADD_ITEM_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.DATE_PICKER_DELIVERY_TAG;
import static com.ara.sunflowerorder.utils.AppConstants.DATE_PICKER_ORDER_TAG;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_ADD_ITEM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_CUSTOMER_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.SalesOrderList;
import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;
import static com.ara.sunflowerorder.utils.AppConstants.getCollectionSubmitURL;
import static com.ara.sunflowerorder.utils.AppConstants.getSalesOrderSubmitURL;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class SalesOrder extends AppCompatActivity implements DatePickerListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    com.ara.sunflowerorder.models.SalesOrder salesOrderModel;

    @BindView(R.id.tv_customer)
    TextView customer_tv;
    @BindView(R.id.order_date_edit)
    TextView order_date_tv;
    @BindView(R.id.delivery_date_edit)
    TextView delivery_date_tv;
    @BindView(R.id.sales_order_total_amount)
    TextView total_amount_tv;


    DialogFragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order);
        getSupportActionBar().hide();
        mRecyclerView = findViewById(R.id.order_item_list_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        salesOrderModel = new com.ara.sunflowerorder.models.SalesOrder();
        mRecyclerView.setVisibility(View.GONE);
        salesOrderModel.setItems(new ArrayList<OrderItem>());
        mAdapter = new OrderItemAdapter(salesOrderModel.getItems());
        mRecyclerView.setAdapter(mAdapter);
        ButterKnife.bind(this);


        Calendar today = Calendar.getInstance();
        order_date_tv.setText(AppConstants.dateToString(today));
        today.add(Calendar.DATE, 2);
        delivery_date_tv.setText(AppConstants.dateToString(today));
    }

    public void select_customer_onClick(View view) {
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
                    Customer customer = Customer.fromJSON(json);
                    customer_tv.setText(customer.getName());
                    salesOrderModel.setCustomer(customer);
                }
                break;
            case ADD_ITEM_REQUEST:
                if (resultCode == RESULT_OK) {
                    String json = data.getStringExtra(EXTRA_ADD_ITEM);
                    OrderItem item = OrderItem.fromJson(json);
                    salesOrderModel.addItem(item);
                    double total = salesOrderModel.getTotal();
                    total += item.getPrice() * item.getQuantity();
                    salesOrderModel.setTotal(total);
                    total_amount_tv.setText(formatPrice(total));
                    mAdapter.notifyItemChanged(salesOrderModel.getItems().size() - 1);
                    if (salesOrderModel.getItems().size() == 1)
                        mRecyclerView.setVisibility(View.VISIBLE);

                }
                break;

        }

    }

    @OnClick({R.id.order_date_edit, R.id.delivery_date_edit})
    public void dateClicked(View view) {
        if (view.getId() == R.id.order_date_edit) {
            newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), DATE_PICKER_ORDER_TAG);
        } else {
            newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), DATE_PICKER_DELIVERY_TAG);
        }
    }

    @OnClick(R.id.btn_submit_order)
    public void onSubmit(View view) {
        if (!validate())
            return;

        final HttpRequest httpRequest = new HttpRequest(getSalesOrderSubmitURL(), HttpRequest.POST);
        httpRequest.addParam("user_id", "1");
        httpRequest.addParam("data", salesOrderModel.toJson());
        new HttpCaller(this, "Submitting") {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.getStatus() == HttpResponse.ERROR)
                    showSnackbar(response.getMesssage());
                else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }.execute(httpRequest);

    }

    private void showSnackbar(String messsage) {
        Snackbar.make(customer_tv, messsage, Snackbar.LENGTH_LONG).show();
    }

    public boolean validate() {
        if (salesOrderModel.getItems().size() == 0) {
            showSnackbar("Add atleast one item.");
            return false;
        }
        if (salesOrderModel.getCustomer() == null) {
            showSnackbar("Choose a Customer.");
            return false;
        }
        return true;
    }


    @OnClick(R.id.btn_order_add_item)
    public void onAddItemClick(View view) {
        Intent addItemIntent = new Intent(this, com.ara.sunflowerorder.OrderItem.class);
        startActivityForResult(addItemIntent, ADD_ITEM_REQUEST);
    }


    @Override
    public void updateDate(Calendar date) {
        if (newFragment.getTag().equals(DATE_PICKER_ORDER_TAG)) {
            order_date_tv.setText(DatePickerFragment.dateToString(date));
        } else {
            delivery_date_tv.setText(DatePickerFragment.dateToString(date));
        }

    }
}
