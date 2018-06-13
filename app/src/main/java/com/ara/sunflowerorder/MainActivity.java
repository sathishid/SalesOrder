package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sales_order_onClick(View view) {
        Intent salesOrderActivity = new Intent(this, SalesOrder.class);
        startActivity(salesOrderActivity);
    }
}
