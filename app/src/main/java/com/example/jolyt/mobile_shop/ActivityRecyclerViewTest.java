package com.example.jolyt.mobile_shop;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jolyt.mobile_shop.databinding.ActivityRecyclerViewBinding;

public class ActivityRecyclerViewTest extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ((ActivityRecyclerViewBinding)DataBindingUtil.setContentView(this, R.layout.activity_recycler_view)).setRvModel(new ViewModelRV());
        mRecyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
}
