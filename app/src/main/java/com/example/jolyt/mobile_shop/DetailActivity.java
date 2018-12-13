package com.example.jolyt.mobile_shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jolyt.mobile_shop.databinding.MvDetailBinding;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int idProduct = intent.getIntExtra("idProduct", 1);
        ((MvDetailBinding)DataBindingUtil.setContentView(this, R.layout.mv_detail)).setDetailModel(new DetailModelView(idProduct));
    }

}
