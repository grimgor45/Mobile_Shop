package com.example.jolyt.mobile_shop.Database.Tables;

import com.example.jolyt.mobile_shop.Database.DBOperation;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Users extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = DBOperation.GeneratePrimKey(Users.class);

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
