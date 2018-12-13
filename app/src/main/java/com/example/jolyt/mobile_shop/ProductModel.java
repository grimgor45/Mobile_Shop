package com.example.jolyt.mobile_shop;

import com.example.jolyt.mobile_shop.Database.Tables.Product;

import java.util.ArrayList;

import io.realm.Realm;

public class ProductModel {
    public String name;
    public String shelf;
    public String comment;
    public ProductModel(String name, String shelf, String comment){
        this.name = name;
        this.shelf = shelf;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public ArrayList<ProductModel> getArrayListProduct(){
        ArrayList<ProductModel>  productModelArrayList = new ArrayList<>();
        Realm re = Realm.getDefaultInstance();
        re.close();
        return productModelArrayList;
    }
}
