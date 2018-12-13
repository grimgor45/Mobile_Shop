package com.example.jolyt.mobile_shop;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.TextView;

import com.example.jolyt.mobile_shop.Database.DBOperation;
import com.example.jolyt.mobile_shop.Database.Tables.Cart;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewModelRV {
    public RVAdapter adapter;
    ObservableField<String> text = new ObservableField<>("aa");
    List<Cart> test;
    private String name;

    public ViewModelRV()
    {
        Realm re = Realm.getDefaultInstance();
        RealmResults<Cart> beforeList= re.where(Cart.class).findAll();
        test = re.copyFromRealm(beforeList);
        re.close();
        adapter = new RVAdapter(test);
    }
    public void create(View v){
        Realm re = Realm.getDefaultInstance();
        DBOperation dbOperation = new DBOperation(re);
        dbOperation.createCart(name, null);
        re.close();
    }
    public void storeName(CharSequence s){
        name = s.toString();
    }
}
