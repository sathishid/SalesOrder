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

public class AppConstants {
    public static final String REST_API = "http://sunflower.sunflowergroups.com/app/sun_android_app.php?action=";

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
    public static final int SEARCH_PRODUCT_REQUEST = 103;
    public static final int ADD_SALES_ORDER_REQUEST = 104;

    public static final int ADD_ITEM_REQUEST = 105;
    public static final int SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST = 106;
    public static final int LIST_APPROVE_ID_REQUEST = 107;
    public static final int ADD_DELIVERY_REQUEST = 108;
    public static final int DELIVERY_ITEM_EDIT_REQUEST = 109;
    public static final int ADD_COLLECTION_REQUEST = 110;

    public static final String EXTRA_SEARCH_RESULT = "SearchResult";
    public static final String EXTRA_SELECTED_CUSTOMER = "selectedCustomer";
    public static final String EXTRA_ADD_ITEM = "OrderItem";
    public static final String REQUEST_CODE = "RequestCode";
    public static final String EXTRA_SELECTED_DELIVERY_ITEM = "selecteDeliveryItem";
    public static final String EXTRA_SELECTED_ITEM_INDEX = "SelectedIndex";

    public static final String DATE_PICKER_ORDER_TAG = "Pick a Order Date";
    public static final String DATE_PICKER_DELIVERY_TAG = "Pick a Delivery Date";


    public static final String CUSTOMER_NAME_PARAM="customer_name";
    public static final String CUSTOMER_ID_PARAM="customer_id";

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

    public static String getInvoiceListURL(){return REST_API + "invoice";}

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static Gson getGson() {
        return new Gson();
    }

    public static String dateToString(Calendar calendar) {
        return calendar.get(Calendar.DATE) + "-"
                + calendar.get(Calendar.MONTH) + "-"
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
