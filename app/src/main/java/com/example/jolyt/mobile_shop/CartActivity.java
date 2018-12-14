package com.example.jolyt.mobile_shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jolyt.mobile_shop.Database.DBOperation;
import com.example.jolyt.mobile_shop.Database.Tables.ProductInCart;
import com.example.jolyt.mobile_shop.databinding.ActivityMainBinding;


import io.realm.Realm;
import io.realm.RealmResults;

public class CartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityMainBinding)DataBindingUtil.setContentView(this,R.layout.activity_main)).setMainViewModel(new MainModelView());


        final Realm re = Realm.getDefaultInstance();
        final DBOperation dbOp= new DBOperation(re);

        LinearLayout parent = findViewById(R.id.parentLayout);
        RealmResults<ProductInCart> productList = re.where(ProductInCart.class).findAll();
        final Intent intent= new Intent(this, CartActivity.class);

        for(final ProductInCart prod:productList) {
            final int productid = prod.getId();
            LinearLayout Llayout = new LinearLayout(this);
            Llayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            parent.addView(Llayout);
            final Button btnName = new Button(this);
            btnName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnName.setText(prod.getProduct().getName());
            if(prod.isTaken()){
                btnName.setBackgroundColor(0xffBDBDBD);
            }
            else {
                btnName.setBackgroundColor(0xffFAFAFA);
            }
            btnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    re.beginTransaction();
                    if(prod.isTaken()){
                        prod.setTaken(false);
                        btnName.setBackgroundColor(0xffFAFAFA);
                    }
                    else {
                        prod.setTaken(true);
                        btnName.setBackgroundColor(0xffBDBDBD);
                    }
                    re.commitTransaction();
                }
            });
            TextView tvPrice = new TextView(this);
            tvPrice.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tvPrice.setText(Float.toString(prod.getProduct().getPrice())+"€");
            ImageButton ibEdit = new ImageButton(this);
            ibEdit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ibEdit.setBackgroundColor(0000);
            ibEdit.setImageResource(R.drawable.trash);
            ibEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    re.beginTransaction();
                    re.where(ProductInCart.class).equalTo("id",productid).findFirst().deleteFromRealm();
                    re.commitTransaction();
                    re.close();
                    startActivity(intent);
                }
            });
            Llayout.addView(btnName);
            Llayout.addView(tvPrice);
            Llayout.addView(ibEdit);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.fab).setVisibility(View.GONE);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem Item){
        if(Item.getItemId()==R.id.cartActivity){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

}
