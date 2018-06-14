package com.ara.sunflowerorder.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.ara.sunflowerorder.models.UOM;
import com.google.gson.Gson;

import java.util.Calendar;

public class AppConstants {

    public static final UOM[] UOM_ARRAY = {
            new UOM(1, "Gram"),
            new UOM(2, "Liter"),
            new UOM(3, "KG"),
            new UOM(4, "Piece"),
            new UOM(5, "Hours")
    };
    public static final int SEARCH_CUSTOMER_REQUEST = 101;
    public static final int SEARCH_BRAND_REQUEST = 102;
    public static final int SEARCH_PRODUCT_REQUEST = 103;
    public static final String EXTRA_SEARCH_RESULT = "SearchResult";
    public static final int ADD_ITEM_REQUEST = 104;

    public static final String EXTRA_SELECTED_CUSTOMER = "selectedCustomer";
    public static final String EXTRA_ADD_ITEM = "OrderItem";
    public static final String REQUEST_CODE = "RequestCode";

    public static final String DATE_PICKER_ORDER_TAG = "Pick a Order Date";
    public static final String DATE_PICKER_DELIVERY_TAG = "Pick a Delivery Date";
    public static final String ADD_ITEM_RESULT = "AddAnItemResult";

    public static String getCustomerListURL() {
        return "http://names.drycodes.com/10";
    }

    public static String getBrandListURL() {
        return "http://names.drycodes.com/10";
    }

    public static String getProductListURL() {
        return "http://names.drycodes.com/10";
    }

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

    public static int getUOMSpinnerIndex(Context context, int itemId) {

        for (int i = 0; i < UOM_ARRAY.length; i++) {
            if (UOM_ARRAY[i].getId() == itemId)
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
