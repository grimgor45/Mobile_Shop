package com.example.jolyt.mobile_shop.Database;

import android.util.Log;

import com.example.jolyt.mobile_shop.Database.Tables.Cart;
import com.example.jolyt.mobile_shop.Database.Tables.Product;
import com.example.jolyt.mobile_shop.Database.Tables.ProductInCart;
import com.example.jolyt.mobile_shop.Database.Tables.Shelf;
import com.example.jolyt.mobile_shop.Database.Tables.Users;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DBOperation {

    private Realm re;
    public DBOperation(Realm re){
        this.re = re;
    }

    public <T extends RealmObject> T readWithId(int id, Class<T>type){
        return re.where(type).equalTo("id", id).findFirst();
    }

    /*public Cart readCart(int id){
        final Cart cart = re.where(Cart.class).equalTo("id", id).findFirst();
        return cart;
    }
    Product readProduct(int id){

    }
    ProductInCart readProductinCart(Realm realm){

    }
    Shelf readShelf(Realm realm){

    }
    public Users readUsers(int id){
        final Users user= re.where(Users.class).equalTo("id", id).findFirst();
        return user;
    }*/

    public <T extends RealmObject>void createItem(T item){
        re.beginTransaction();
        re.copyToRealm(item);
        re.commitTransaction();
    }

    public void createCart(String name, Users user){
        re.beginTransaction();
        Cart cart = re.createObject(Cart.class, GenerateKey(Cart.class));
        cart.setName(name);
        cart.setUser(user);
        re.commitTransaction();
    }
    void createProduct(String name, String commentary, Shelf shelf, float price){
        re.beginTransaction();
        Product product = re.createObject(Product.class, GenerateKey(Product.class));
        product.setShelf(shelf);
        product.setName(name);
        product.setCommentary(commentary);
        product.setPrice(price);
        re.commitTransaction();
    }
    void createProductInCart(String commentary, Cart cart, Product product, int quantity){
        re.beginTransaction();
        ProductInCart prodTo = re.createObject(ProductInCart.class, GenerateKey(ProductInCart.class));
        prodTo.setCommentary(commentary);
        prodTo.setCart(cart);
        prodTo.setTaken(false);
        prodTo.setQuantity(quantity);
        re.commitTransaction();
    }
    void createShlef(String name){
        re.beginTransaction();
        Shelf shelf = re.createObject(Shelf.class, GenerateKey(Shelf.class));
        shelf.setName(name);
        re.commitTransaction();
    }
    public void createUser(String name){
        re.beginTransaction();
        Users cart = re.createObject(Users.class, GenerateKey(Users.class));
        cart.setName(name);
        re.commitTransaction();
    }

    public void deleteCartId(int id){
        re.beginTransaction();
        RealmResults<Cart> toDelete = re.where(Cart.class).equalTo("id", id).findAll();
        toDelete.deleteAllFromRealm();
        Log.i("Deleted", ""+id);
        re.commitTransaction();
    }
    public void deleteProductId(int id){
        re.beginTransaction();
        RealmResults<Product> toDelete = re.where(Product.class).equalTo("id", id).findAll();
        toDelete.deleteAllFromRealm();
        Log.i("Deleted", ""+id);
        re.commitTransaction();
    }
    public void deleteShelfId(int id){
        re.beginTransaction();
        RealmResults<Shelf> toDelete = re.where(Shelf.class).equalTo("id", id).findAll();
        toDelete.deleteAllFromRealm();
        Log.i("Deleted", ""+id);
        re.commitTransaction();
    }
    public void deleteUserId(int id){
        re.beginTransaction();
        RealmResults<Users> toDelete = re.where(Users.class).equalTo("id", id).findAll();
        toDelete.deleteAllFromRealm();
        Log.i("Deleted", ""+id);
        re.commitTransaction();
    }

    public void updateCartId(int id, String name, int userId){
        Cart toEdit = re.where(Cart.class).equalTo("id", id).findFirst();
        re.beginTransaction();
        toEdit.setName(name);
        toEdit.setUser(re.where(Users.class).equalTo("id", id).findFirst());
        re.commitTransaction();
    }
    public void updateProduct(int id, String name, int shelfId, String commentary, float price){
        Product toEdit = re.where(Product.class).equalTo("id", id).findFirst();
        re.beginTransaction();
        toEdit.setName(name);
        toEdit.setCommentary(commentary);
        toEdit.setPrice(price);
        toEdit.setShelf(re.where(Shelf.class).equalTo("id", shelfId).findFirst());
        re.commitTransaction();

    }
    public void updateProductInCart(int id, int prodId, int cartId, boolean taken, String commentary, int quantity){
        ProductInCart toEdit = re.where(ProductInCart.class).equalTo("id", id).findFirst();
        re.beginTransaction();
        toEdit.setProduct(re.where(Product.class).equalTo("id", id).findFirst());
        toEdit.setTaken(taken);
        toEdit.setCart(re.where(Cart.class).equalTo("id", id).findFirst());
        toEdit.setCommentary(commentary);
        toEdit.setQuantity(quantity);
        re.commitTransaction();
    }
    public void updateShelf(int id, String name){
        Shelf toEdit = re.where(Shelf.class).equalTo("id", id).findFirst();
        re.beginTransaction();
        toEdit.setName(name);
        re.commitTransaction();
    }
    public void updateUser(int id, String name){
        Users toEdit = re.where(Users.class).equalTo("id", id).findFirst();
        re.beginTransaction();
        toEdit.setName(name);
        re.commitTransaction();
    }

    int GenerateKey(Class c) {
        int id = 0;
        try {
            if (re.where(c).max("id") !=null){
                id = re.where(c).max("id").intValue() + 1;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            id = 0;
        }

        return id;
    }

    public static int GeneratePrimKey(Class c) {
        int id = 0;
        Realm re = Realm.getDefaultInstance();
        try {
            if (re.where(c).max("id") !=null){
                id = re.where(c).max("id").intValue() + 1;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            id = 0;
        }
        re.close();
        return id;
    }
}
