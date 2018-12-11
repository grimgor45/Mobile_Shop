package com.example.jolyt.mobile_shop;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingConversion;

import java.util.Objects;

public class DetailFields extends BaseObservable {
    private String field1;
    private String name;
    private String comment;
    private String shelf;
    private float price;

    @Bindable
    public String getField1(){
        return this.field1;
    }
    public void set(String field1){
        this.field1=field1;
        //notifyPropertyChanged(BR.field1);
    }

    public String getName() {
        return name;
    }
    @Bindable
    public void setName(String name) {
        this.name = name;
        //notifyPropertyChanged(BR.name);
    }

    public String getComment() {
        return comment;
    }
    @Bindable
    public void setComment(String comment) {
        this.comment = comment;
        //notifyPropertyChanged(BR.comment);
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isEmpty(){
        return field1 ==null || field1.isEmpty();
    }
}
