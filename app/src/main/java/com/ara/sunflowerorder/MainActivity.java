package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import static com.ara.sunflowerorder.utils.AppConstants.ADD_SALES_ORDER_REQUEST;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void sales_order_onClick(View view) {
        Intent salesOrderActivity = new Intent(this, SalesOrder.class);
        startActivityForResult(salesOrderActivity, ADD_SALES_ORDER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Log.i("NO RESULT", "RESULT is NOT OK, ensure everything is going fine");
            return;
        }
        View linearyLayout = findViewById(R.id.layout_linear_main);
        switch (requestCode) {
            case ADD_SALES_ORDER_REQUEST:
                Snackbar.make(linearyLayout, "Sales Order Submitted Successfully", Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}
