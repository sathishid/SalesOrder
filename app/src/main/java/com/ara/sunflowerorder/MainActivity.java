package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ara.sunflowerorder.utils.AppConstants;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.ADD_COLLECTION_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.ADD_DELIVERY_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.ADD_SALES_ORDER_REQUEST;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void sales_order_onClick(View view) {
        Intent salesOrderActivity = new Intent(this, SalesOrder.class);
        startActivityForResult(salesOrderActivity, ADD_SALES_ORDER_REQUEST);
    }

    @OnClick(R.id.btn_main_delivery)
    public void delivery(View view) {
        Intent salesOrderActivity = new Intent(this, DeliveryActivity.class);
        startActivityForResult(salesOrderActivity, ADD_DELIVERY_REQUEST);
    }

    @OnClick(R.id.btn_main_collection)
    public void collection(View view) {
        Intent salesOrderActivity = new Intent(this, CollectionActivity.class);
        startActivityForResult(salesOrderActivity, ADD_COLLECTION_REQUEST);
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
            case ADD_DELIVERY_REQUEST:
                Snackbar.make(linearyLayout, "Delivery Info Submitted Successfully", Snackbar.LENGTH_LONG).show();
                break;
            case ADD_COLLECTION_REQUEST:
                AppConstants.showSnackbar(linearyLayout, "Collection made Successfully");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a iparent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.nav_sales_order:
                intent = new Intent(this, SalesOrderReportActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_collection:
                intent = new Intent(this, CollectionReportActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_delivery:
                intent = new Intent(this, DeliveryReportActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sales_order) {
            Intent salesOrderActivity = new Intent(this, SalesOrder.class);
            startActivityForResult(salesOrderActivity, ADD_SALES_ORDER_REQUEST);
        } else if (id == R.id.nav_delivery) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
