package com.ara.sunflowerorder;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ara.sunflowerorder.adapters.CollectionReportAdapter;
import com.ara.sunflowerorder.models.view.CollectionReport;
import com.ara.sunflowerorder.utils.DatePickerFragment;
import com.ara.sunflowerorder.utils.DatePickerListener;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.DATE_PICKER_FROM_TAG;
import static com.ara.sunflowerorder.utils.AppConstants.DATE_PICKER_TO_TAG;
import static com.ara.sunflowerorder.utils.AppConstants.FROM_DATE_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.TO_DATE_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.USER_ID_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.getCollectionReportURL;

public class CollectionReportActivity extends AppCompatActivity implements DatePickerListener {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.coll_list_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_coll_from_date)
    TextView fromDate;
    @BindView(R.id.tv_coll_to_date)
    TextView toDate;
    DialogFragment newFragment;

    Calendar from;
    Calendar to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_report);
        ButterKnife.bind(this);

        // recyclerView = (RecyclerView) findViewById(R.id.del_rpt_list_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //If list is empty then recycler view will throw exception. So need to hide if empty.
        recyclerView.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_coll_to_date, R.id.tv_coll_from_date})
    public void chooseDate(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_coll_from_date:
                newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), DATE_PICKER_FROM_TAG);
                break;
            case R.id.tv_coll_to_date:
                newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), DATE_PICKER_TO_TAG);
                break;
        }
    }

    @Override
    public void updateDate(Calendar date) {
        if (newFragment.getTag().equals(DATE_PICKER_FROM_TAG)) {
            from = date;
            fromDate.setText(DatePickerFragment.dateToString(date));
        } else {
            to = date;
            toDate.setText(DatePickerFragment.dateToString(date));
//            if (from.before(to)) {
//                return;}

            HttpRequest httpRequest = new HttpRequest(getCollectionReportURL(), HttpRequest.POST);
            httpRequest.addParam(USER_ID_PARAM, "1");
            String strFromDate = fromDate.getText().toString();
            String strToDate = toDate.getText().toString();
            strFromDate = strFromDate.replace('-', '/');
            strToDate = strToDate.replace('-', '/');
            httpRequest.addParam(FROM_DATE_PARAM, strFromDate);
            httpRequest.addParam(TO_DATE_PARAM, strToDate);
            new HttpCaller(this, "Delivery Report..") {
                @Override
                public void onResponse(HttpResponse response) {
                    if (response.getStatus() == HttpResponse.ERROR) {
                        Log.i("DeliveryReport", "Fetching error");
                    } else {
                        List<CollectionReport> collectionReportList = CollectionReport.fromJsonArray(response.getMesssage());
                        mAdapter = new CollectionReportAdapter(collectionReportList, null);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }.execute(httpRequest);

        }

    }
}
