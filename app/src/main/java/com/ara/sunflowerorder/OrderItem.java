package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_BRAND_REQUEST;
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

    @BindView(R.id.spinner_order_uom)
    Spinner spinnerOrderUOM;
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
        editOrderQty.setText("1");
        ArrayAdapter<UOM> uoms = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                UOM_ARRAY);
        spinnerOrderUOM.setAdapter(uoms);
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
                Intent chooseBrand = new Intent(this, SearchHelper.class);
                chooseBrand.putExtra(REQUEST_CODE, SEARCH_BRAND_REQUEST);
                startActivityForResult(chooseBrand, SEARCH_BRAND_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getStringExtra(EXTRA_SEARCH_RESULT);
        switch (requestCode) {
            case SEARCH_BRAND_REQUEST:
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
                int index = AppConstants.getUOMSpinnerIndex(this, product.getUom().getId());

                spinnerOrderUOM.setSelection(index);
                tvTotalAmount.setText(String.format("%8.2f", product.getPrice()).trim());
                break;
        }
    }

    @OnClick(R.id.btn_order_an_item)
    public void sendItem(View view) {
        UOM uom = (UOM) spinnerOrderUOM.getSelectedItem();
        orderItemModel.getProduct().setUom(uom);
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
}
