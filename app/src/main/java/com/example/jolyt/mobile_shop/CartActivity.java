package com.example.jolyt.mobile_shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.SharedLibraryInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jolyt.mobile_shop.Database.DBOperation;
import com.example.jolyt.mobile_shop.Database.Tables.Cart;
import com.example.jolyt.mobile_shop.Database.Tables.Product;
import com.example.jolyt.mobile_shop.Database.Tables.ProductInCart;
import com.example.jolyt.mobile_shop.Database.Tables.Shelf;
import com.example.jolyt.mobile_shop.Database.Tables.Users;
import com.example.jolyt.mobile_shop.databinding.ActivityMainBinding;

import java.lang.reflect.ReflectPermission;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityMainBinding)DataBindingUtil.setContentView(this,R.layout.activity_main)).setMainViewModel(new MainModelView());


        //Do not touch needed for database

        //toadd object  use : new oject then new transaction and copy
        //for PK autogenerate
        //mutableRealmInteger
        //https://stackoverflow.com/questions/40174920/how-to-set-primary-key-auto-increment-in-realm-android
        final Realm re = Realm.getDefaultInstance();
        final DBOperation dbOp= new DBOperation(re);
        Shelf shelf = new Shelf();
        shelf.setId();
        shelf.setName("Rayon bananes");
        dbOp.createItem(shelf);
        shelf = dbOp.readWithId(re.where(Shelf.class).max("id").intValue(), Shelf.class);
        dbOp.createProductInCart("commentary",null,re.where(Product.class).findFirst(),1);


        long sizeOfProductTable = re.where(ProductInCart.class).count();


        LinearLayout parent = findViewById(R.id.parentLayout);
        RealmResults<ProductInCart> productList = re.where(ProductInCart.class).findAll();
        int cnt =0;
        final Intent intent= new Intent(this, CartActivity.class);

        for(final ProductInCart prod:productList) {
            cnt++;
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


        //

        /*for (int i = 0; i < re.where(Cart.class).findAll().size()+1; i++){
            dbOp.deleteCartId(i);
        }*/
        Log.i("MaxProd", ""+re.where(Product.class).findAll().size());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.fab).setVisibility(View.GONE);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
