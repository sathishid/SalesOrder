package com.ara.sunflowerorder.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.ara.sunflowerorder.models.SalesOrder;
import com.ara.sunflowerorder.models.UOM;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;

public class AppConstants {
    public static final String REST_API = "http://sunflower.sunflowergroups.com/app/sun_android_app.php?action=";
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final ArrayList<SalesOrder> SalesOrderList = new ArrayList<>();

    public static final UOM[] UOM_ARRAY = {
            new UOM(1, "Gram"),
            new UOM(2, "Liter"),
            new UOM(3, "KG"),
            new UOM(4, "Piece"),
            new UOM(5, "Hours")
    };
    public static final int SEARCH_CUSTOMER_REQUEST = 101;
    public static final int LIST_BRAND_REQUEST = 102;
    public static final int List_PRODUCT_REQUEST = 103;
    public static final int ADD_SALES_ORDER_REQUEST = 104;

    public static final int ADD_ITEM_REQUEST = 105;
    public static final int SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST = 106;
    public static final int LIST_APPROVE_ID_REQUEST = 107;
    public static final int ADD_DELIVERY_REQUEST = 108;
    public static final int DELIVERY_ITEM_EDIT_REQUEST = 109;
    public static final int ADD_COLLECTION_REQUEST = 110;
    public static final int INVOICE_ITEM_EDIT_REQUEST = 111;

    public static final String EXTRA_SEARCH_RESULT = "SearchResult";
    public static final String EXTRA_SELECTED_CUSTOMER = "selectedCustomer";
    public static final String EXTRA_ADD_ITEM = "OrderItem";
    public static final String REQUEST_CODE = "RequestCode";
    public static final String EXTRA_SELECTED_DELIVERY_ITEM = "selecteDeliveryItem";
    public static final String EXTRA_SELECTED_ITEM_INDEX = "SelectedIndex";
    public static final String EXTRA_SELECTED_INVOICE_ITEM = "Invoice Item";


    public static final String DATE_PICKER_ORDER_TAG = "Pick a Order Date";
    public static final String DATE_PICKER_DELIVERY_TAG = "Pick a Delivery Date";
    public static final String DATE_PICKER_FROM_TAG = "Pick a From Date";
    public static final String DATE_PICKER_TO_TAG = "Pick a To Date";


    public static final String CUSTOMER_NAME_PARAM = "customer_name";
    public static final String CUSTOMER_ID_PARAM = "customer_id";
    public static final String BRAND_ID_PARAM = "brand_id";
    public static final String FROM_DATE_PARAM = "from_date";
    public static final String TO_DATE_PARAM = "to_date";
    public static final String USER_ID_PARAM = "user_id";

    public static final String ADD_ITEM_RESULT = "AddAnItemResult";

    public static String getCustomerListURL() {
        return REST_API + "customer";
    }

    public static String getBrandListURL() {
        return REST_API + "brand";
    }

    public static String getProductListURL() {
        return REST_API + "product";
    }

    public static String getInvoiceListURL() {
        return REST_API + "invoice";
    }

    public static String getCollectionSubmitURL() {
        return REST_API + "collection";
    }
    public static String getSalesOrderSubmitURL() {
        return REST_API + "sales_order";
    }
    public static String getDeliverySubmitURL() {
        return REST_API + "delivery";
    }

    public static String getDeliveryReportURL() {
        return REST_API + "delivery_report";
    }

    public static String getSalesOrderReportURL() {
        return REST_API + "sales_report";
    }

    public static String getCollectionReportURL() {
        return REST_API + "collection_report";
    }

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static Gson getGson() {
        return new Gson();
    }

    public static String dateToString(Calendar calendar) {
        return calendar.get(Calendar.DATE) + "-"
                + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.YEAR);
    }

    public static int getUOMSpinnerIndex(Spinner spinner, String itemName) {
        SpinnerAdapter arrayAdapter = spinner.getAdapter();
        for (int i = 0; i < arrayAdapter.getCount(); i++) {
            if (arrayAdapter.getItem(i) == itemName)
                return i;
        }
        return -1;
    }

    public static String formatPrice(double price) {
        return String.format("%8.2f", price).trim();
    }

    public static String formatQuantity(int qty) {
        return String.format("%d", qty).trim();
    }
}
