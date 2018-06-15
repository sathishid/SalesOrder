package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ara.sunflowerorder.models.Brand;
import com.ara.sunflowerorder.models.Product;
import com.ara.sunflowerorder.models.UOM;
import com.ara.sunflowerorder.utils.AppConstants;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_BRAND_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_PRODUCT_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.UOM_ARRAY;

public class OrderItem extends AppCompatActivity {

    com.ara.sunflowerorder.models.OrderItem orderItemModel;
    Brand brand;

    @BindView(R.id.tv_order_brandName)
    TextView tvBrandName;
    @BindView(R.id.tv_order_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_order_product_code)
    TextView tvProductCode;

    @BindView(R.id.tv_order_uom)
    TextView textViewOrderUOM;
    @BindView(R.id.edit_order_qty)
    EditText editOrderQty;
    @BindView(R.id.edit_order_price)
    EditText editTextOrderPrice;
    @BindView(R.id.tv_order_available_qty)
    TextView tvAvailableQty;
    @BindView(R.id.tv_order_total_amount)
    TextView tvTotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
        ButterKnife.bind(this);
        orderItemModel = new com.ara.sunflowerorder.models.OrderItem();
        orderItemModel.setQuantity(1);

    }

    @OnClick({R.id.tv_order_product_name, R.id.tv_order_brandName})
    public void chooseItem(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_order_product_name:
                Intent chooseProduct = new Intent(this, SearchHelper.class);
                chooseProduct.putExtra(REQUEST_CODE, SEARCH_PRODUCT_REQUEST);
                startActivityForResult(chooseProduct, SEARCH_PRODUCT_REQUEST);
                break;
            case R.id.tv_order_brandName:
                Intent chooseBrand = new Intent(this, ListHelperActivity.class);
                chooseBrand.putExtra(REQUEST_CODE, LIST_BRAND_REQUEST);
                startActivityForResult(chooseBrand, LIST_BRAND_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        String result = data.getStringExtra(EXTRA_SEARCH_RESULT);
        switch (requestCode) {
            case LIST_BRAND_REQUEST:
                this.brand = Brand.fromJSON(result);
                tvBrandName.setText(this.brand.getName());
                break;
            case SEARCH_PRODUCT_REQUEST:
                Product product = Product.fromJSON(result);
                product.setBrand(this.brand);
                tvProductName.setText(product.getName());
                orderItemModel.setPrice(product.getPrice());
                orderItemModel.setProduct(product);
                tvAvailableQty.setText(String.format("%d", product.getAvailableQty()));
                editTextOrderPrice.setText(String.format("%8.2f", product.getPrice()).trim());
                tvProductCode.setText(product.getCode());
                textViewOrderUOM.setText(product.getUom());
                tvTotalAmount.setText(String.format("%8.2f", product.getPrice()).trim());
                break;
        }
    }

    @OnClick(R.id.btn_order_an_item)
    public void sendItem(View view) {
        if (!validate()) {
            return;
        }

        String tempText = editOrderQty.getText().toString();
        orderItemModel.setQuantity(Integer.parseInt(tempText));
        tempText = editTextOrderPrice.getText().toString();
        orderItemModel.setPrice(Double.parseDouble(tempText));
        String json = orderItemModel.toJson();
        Intent intent = new Intent();
        intent.putExtra(AppConstants.EXTRA_ADD_ITEM, json);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void showSnackbar(String message) {
        Snackbar.make(tvBrandName, message, Snackbar.LENGTH_LONG).show();
    }

    private boolean validate() {
        if (brand == null) {
            showSnackbar("Please select the Brand.");
            return false;
        }
        if (orderItemModel.getProduct() == null) {
            showSnackbar("Please select the product.");
            return false;
        }
        return true;
    }
}
