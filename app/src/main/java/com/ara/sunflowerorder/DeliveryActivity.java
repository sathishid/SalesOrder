package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ara.sunflowerorder.adapters.DeliveryItemAdapter;
import com.ara.sunflowerorder.listeners.ListViewClickListener;
import com.ara.sunflowerorder.models.Customer;
import com.ara.sunflowerorder.models.Delivery;
import com.ara.sunflowerorder.models.DeliveryItem;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.DELIVERY_ITEM_EDIT_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_CUSTOMER;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_DELIVERY_ITEM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_ITEM_INDEX;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_APPROVE_ID_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.getSalesOrderSubmitURL;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class DeliveryActivity extends AppCompatActivity implements ListViewClickListener {

    Delivery deliveryModel;
    Customer customer;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.tv_delivery_customer)
    TextView tv_customer;
    @BindView(R.id.delivery_delivery_date)
    TextView tv_deliveryDate;
    @BindView(R.id.delivery_today_date)
    TextView tv_deliveryTodayDate;
    @BindView(R.id.tv_delivery_approve_id)
    TextView tv_approveId;
    @BindView(R.id.delivery_item_list_view)
    RecyclerView recyclerView;
    @BindView(R.id.delivery_total_qty)
    TextView tv_totalQty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        tv_deliveryTodayDate.setText(AppConstants.dateToString(Calendar.getInstance()));
        recyclerView = findViewById(R.id.delivery_item_list_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //If list is empty then recycler view will throw exception. So need to hide if empty.
        recyclerView.setVisibility(View.GONE);


    }

    @OnClick(R.id.tv_delivery_customer)
    public void chooseCustomer(View view) {
        Intent customerList = new Intent(this, SearchHelper.class);
        customerList.putExtra(REQUEST_CODE, SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST);
        startActivityForResult(customerList, SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST);
    }

    @OnClick(R.id.tv_delivery_approve_id)
    public void chooseApproveNo(View view) {
        if (customer == null) {
            showSnackbar(tv_customer, "Please choose Customer first");
            return;
        }
        Intent intent = new Intent(this, ListHelperActivity.class);
        intent.putExtra(EXTRA_SELECTED_CUSTOMER, customer.getId());
        intent.putExtra(REQUEST_CODE, LIST_APPROVE_ID_REQUEST);
        startActivityForResult(intent, LIST_APPROVE_ID_REQUEST);
    }

    @OnClick(R.id.submit_delivery)
    public void onDeliverySubmit() {
        if (deliveryModel.getDeliveryItems().size() == 0) {
            showSnackbar(tv_customer, "No Items found to deliver");
            return;
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        String json = data.getStringExtra(EXTRA_SEARCH_RESULT);
        switch (requestCode) {
            case SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST:

                customer = Customer.fromJSON(json);
                tv_customer.setText(customer.getName());

                break;
            case LIST_APPROVE_ID_REQUEST:
                deliveryModel = Delivery.fromJSON(json);
                deliveryModel.setCustomer(customer);
                tv_approveId.setText(deliveryModel.getApproval().getId() + "");
                tv_deliveryDate.setText(deliveryModel.getApproval().getDate());

                updateAcceptQuantities();
                List<DeliveryItem> items = deliveryModel.getDeliveryItems();

                mAdapter = new DeliveryItemAdapter(deliveryModel.getDeliveryItems(), this);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                updateTotal();
                break;
            case DELIVERY_ITEM_EDIT_REQUEST:
                int position = data.getIntExtra(EXTRA_SELECTED_ITEM_INDEX, -1);
                DeliveryItem deliveryItem = DeliveryItem.fromJSON(json);
                updateRecyclerView(deliveryItem, position);
                updateTotal();
                break;
        }
    }

    @Override
    public void onItemClick(Object selectedObject, int position) {
        Intent intent = new Intent(this, DeliveryItemActivity.class);
        DeliveryItem deliveryItem = (DeliveryItem) selectedObject;
        String json = deliveryItem.toJson();
        intent.putExtra(EXTRA_SELECTED_DELIVERY_ITEM, json);
        intent.putExtra(EXTRA_SELECTED_ITEM_INDEX, position);
        startActivityForResult(intent, DELIVERY_ITEM_EDIT_REQUEST);
    }

    private void updateRecyclerView(DeliveryItem deliverItem, int position) {
        List<DeliveryItem> items = deliveryModel.getDeliveryItems();
        DeliveryItem actualItem = items.get(position);
        actualItem.setAccept(deliverItem.getAccept());
        actualItem.setReject(deliverItem.getReject());
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.notifyItemChanged(position);
    }

    private void updateAcceptQuantities() {
        List<DeliveryItem> items = deliveryModel.getDeliveryItems();
        for (int i = 0; i < items.size(); i++) {
            DeliveryItem item = items.get(i);
            item.setAccept(item.getQuantity());
            item.setReject(0);
        }
    }

    private void updateTotal() {
        int total = 0;
        for (DeliveryItem item : deliveryModel.getDeliveryItems()) {
            total += item.getAccept();
        }
        tv_totalQty.setText(total + "");
    }

    @OnClick(R.id.submit_delivery)
    public void submitDelivery(View view) {
        if (!validate()) {

            return;
        }

        final HttpRequest httpRequest = new HttpRequest(getSalesOrderSubmitURL(), HttpRequest.POST);
        httpRequest.addParam("user_id", "1");
        httpRequest.addParam("data", deliveryModel.toJson());
        new HttpCaller(this, "Submitting") {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.getStatus() == HttpResponse.ERROR)
                    showSnackbar(tv_approveId, response.getMesssage());
                else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }.execute(httpRequest);


    }

    private boolean validate() {
        boolean isValid = true;
        if (deliveryModel.getDeliveryItems().size() == 0) {
            showSnackbar(tv_approveId, "Add one or more item.");
            return false;
        }
        if (deliveryModel.getCustomer() == null) {
            showSnackbar(tv_approveId, "Select a Customer Name.");

            return false;
        }
        return true;
    }
}
