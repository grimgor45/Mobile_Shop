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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.jolyt.mobile_shop.Database.DBOperation;
import com.example.jolyt.mobile_shop.Database.Tables.Cart;
import com.example.jolyt.mobile_shop.Database.Tables.Product;
import com.example.jolyt.mobile_shop.Database.Tables.Shelf;
import com.example.jolyt.mobile_shop.Database.Tables.Users;
import com.example.jolyt.mobile_shop.databinding.ActivityMainBinding;

import java.lang.reflect.ReflectPermission;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityMainBinding)DataBindingUtil.setContentView(this,R.layout.activity_main)).setMainViewModel(new MainModelView());

        LinearLayout parent = findViewById(R.id.parentLayout);
        for(int i=0;i<100;i++) {
            LinearLayout Llayout = new LinearLayout(this);
            Llayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            parent.addView(Llayout);
            Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btn.setText("this is working");
            Llayout.addView(btn);
        }
                //Do not touch needed for database

        //toadd object  use : new oject then new transaction and copy
        //for PK autogenerate
        //mutableRealmInteger
        //https://stackoverflow.com/questions/40174920/how-to-set-primary-key-auto-increment-in-realm-android

        Realm re = Realm.getDefaultInstance();
        DBOperation dbOp= new DBOperation(re);
        Shelf shelf = new Shelf();
        shelf.setId();
        shelf.setName("Rayon bannanes");
        dbOp.createItem(shelf);
        shelf = dbOp.readWithId(re.where(Shelf.class).max("id").intValue(), Shelf.class);
        Product product = new Product();
        product.setName("Bannania");
        product.setShelf(shelf);
        product.setId();
        dbOp.createItem(product);


        //

        /*for (int i = 0; i < re.where(Cart.class).findAll().size()+1; i++){
            dbOp.deleteCartId(i);
        }*/
        Log.i("MaxProd", ""+re.where(Product.class).findAll().size());
        re.close();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Intent intent= new Intent(this, DetailActivity.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("idProduct", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
