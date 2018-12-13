package com.example.jolyt.mobile_shop.Database.Tables;

import com.example.jolyt.mobile_shop.Database.DBOperation;

import io.realm.MutableRealmInteger;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ProductInCart extends RealmObject {

    @PrimaryKey
    private int id;
    private Product product;
    private Cart cart;
    private boolean taken;
    private String commentary;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = DBOperation.GeneratePrimKey(ProductInCart.class);

    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
