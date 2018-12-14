package com.example.jolyt.mobile_shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jolyt.mobile_shop.Database.DBOperation;
import com.example.jolyt.mobile_shop.Database.Tables.Product;
import com.example.jolyt.mobile_shop.Database.Tables.ProductInCart;
import com.example.jolyt.mobile_shop.Database.Tables.Shelf;
import com.example.jolyt.mobile_shop.databinding.ActivityMainBinding;


import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityMainBinding)DataBindingUtil.setContentView(this,R.layout.activity_main)).setMainViewModel(new MainModelView());



        final Realm re = Realm.getDefaultInstance();
        final DBOperation dbOp = new DBOperation(re);
        if (re.where(Shelf.class).findFirst()==null) {

            Shelf shelf = new Shelf();
            shelf.setId();
            shelf.setName("Rayon Fruits");
            dbOp.createItem(shelf);
        }
        if (re.where(Product.class).findFirst()==null){
            dbOp.createProduct("Carottes", "400g",re.where(Shelf.class).equalTo("name", "Rayon Fruits").findFirst(),1,null);
        }
        if (re.where(ProductInCart.class).findFirst()==null)
        {
            dbOp.createProductInCart("Comment", null, re.where(Product.class).equalTo("name", "Carottes").findFirst(),1);
        }


        final LinearLayout parent = findViewById(R.id.parentLayout);
        RealmResults<Product> productList = re.where(Product.class).findAll();
        final Intent intent= new Intent(this, DetailActivity.class);

        for(final Product prod:productList) {
            final int productid = prod.getId();
            LinearLayout Llayout = new LinearLayout(this);
            Llayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            parent.addView(Llayout);
            final Button btnName = new Button(this);
            btnName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnName.setText(prod.getName());
            btnName.setBackgroundColor(0xffFAFAFA);
            btnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbOp.createProductInCart(prod.getCommentary(),null,prod,1);
                    Toast.makeText(parent.getContext(), prod.getName()+" added to cart", Toast.LENGTH_LONG).show();
                }
            });
            TextView tvPrice = new TextView(this);
            tvPrice.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tvPrice.setText(Float.toString(prod.getPrice())+"€");
            ImageButton ibEdit = new ImageButton(this);
            ibEdit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ibEdit.setBackgroundColor(0000);
            ibEdit.setImageResource(R.drawable.edit);
            ibEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("idProduct",productid);
                    startActivity(intent);
                }
            });
            Llayout.addView(btnName);
            Llayout.addView(tvPrice);
            Llayout.addView(ibEdit);
        }

        Log.i("MaxProd", ""+re.where(Product.class).findAll().size());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("idProduct", -1);
                re.close();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem Item){
        if(Item.getItemId()==R.id.cartActivity){
            Intent intent = new Intent(this,CartActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
