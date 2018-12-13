package com.example.jolyt.mobile_shop.Database.Tables;

import com.example.jolyt.mobile_shop.Database.DBOperation;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Cart extends RealmObject {

    private Users user;
    @PrimaryKey
    private int id;
    private String name;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = DBOperation.GeneratePrimKey(Cart.class);
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
