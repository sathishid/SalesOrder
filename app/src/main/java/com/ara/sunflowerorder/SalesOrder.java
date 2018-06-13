package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ara.sunflowerorder.models.Customer;
import com.ara.sunflowerorder.models.OrderItem;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.DatePickerFragment;
import com.ara.sunflowerorder.utils.DatePickerListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.ADD_ITEM_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.DATE_PICKER_DELIVERY_TAG;
import static com.ara.sunflowerorder.utils.AppConstants.DATE_PICKER_ORDER_TAG;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_ADD_ITEM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_CUSTOMER;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_CUSTOMER_REQUEST;

public class SalesOrder extends AppCompatActivity implements DatePickerListener {

    com.ara.sunflowerorder.models.SalesOrder salesOrderModel;
    @BindView(R.id.tv_customer)
    TextView customer_tv;
    @BindView(R.id.order_date_edit)
    TextView order_date_tv;
    @BindView(R.id.delivery_date_edit)
    TextView delivery_date_tv;

    DialogFragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order);
        ButterKnife.bind(this);
        salesOrderModel = new com.ara.sunflowerorder.models.SalesOrder();

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
