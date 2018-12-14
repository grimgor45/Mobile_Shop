package com.example.jolyt.mobile_shop;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;

import com.example.jolyt.mobile_shop.Database.DBOperation;
import com.example.jolyt.mobile_shop.Database.Tables.Product;
import com.example.jolyt.mobile_shop.Database.Tables.Shelf;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DetailModelView {
    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> comment = new ObservableField<>();
    public final ObservableField<String> shelf = new ObservableField<>();
    public boolean create;
    public DetailFields detail;
    public final ObservableFloat price = new ObservableFloat();
    private int idProduct;
    public final ObservableArrayList<String> shelfList = new ObservableArrayList<>();
    public final ObservableInt initialPos = new ObservableInt();
    Realm re;

    RealmResults<Shelf> allShelf;

    public DetailModelView(int idProduct){
        re = Realm.getDefaultInstance();
        create=idProduct==-1?true:false;
        DBOperation dbOp = new DBOperation(re);
        this.idProduct = idProduct;
        detail = new DetailFields();
        RealmObject product;
        allShelf = re.where(Shelf.class).findAll();
        if(!create){
            product = re.where(Product.class).equalTo("id", idProduct).findFirst();
            name.set(((Product) product).getName());
            comment.set(((Product) product).getCommentary());
            shelf.set(((Product) product).getShelf().getName());
            price.set(((Product) product).getPrice());
            initialPos.set(allShelf.indexOf(allShelf.where().equalTo("name", ((Product) product).getShelf().getName()).findFirst()));
        }


        List<Shelf> listShelf= re.copyFromRealm(allShelf);
        for (Shelf shel :  listShelf){
            shelfList.add(shel.getName());
        }
    }

    public void saveChanges(View view){
        DBOperation dbOp = new DBOperation(re);
        int idTrue = re.where(Shelf.class).equalTo("name",allShelf.get(initialPos.get()).getName()).findFirst().getId();
        Toast toast;
        if(!create){
            dbOp.updateProduct(idProduct, detail.getName(), idTrue, detail.getComment(), detail.getPrice());
            toast = Toast.makeText(view.getContext(), "update product", Toast.LENGTH_LONG);

        }
        else {
            dbOp.createProduct(detail.getName(),detail.getComment(),re.where(Shelf.class).equalTo("id",idTrue).findFirst(), detail.getPrice(),null);
            toast = Toast.makeText(view.getContext(), "product created", Toast.LENGTH_LONG);

        }
        toast.show();
    }

    public void createShelf(View view){

        if (detail.getShelf() != null) {
            DBOperation dbOp = new DBOperation(re);
            Shelf shelf = new Shelf();
            shelf.setId();
            shelf.setName(detail.getShelf());
            dbOp.createItem(shelf);
            Toast toast = Toast.makeText(view.getContext(), "created shelf", Toast.LENGTH_LONG);
            toast.show();

        }
        else
        {
            Toast toast = Toast.makeText(view.getContext(), "could not add shelf", Toast.LENGTH_LONG);
            toast.show();

        }
    }

    public void afterTextChange(CharSequence s)
    {
        detail.set(s.toString());
    }
    public void afterNameChange(CharSequence s)
    {
        detail.setName(s.toString());
        name.set(s.toString());
    }
    public void afterCommentChange(CharSequence s)
    {
        detail.setComment(s.toString());
        comment.set(s.toString());
    }
    public void afterPriceChange(CharSequence s)
    {
        if (s.length() !=0) {
            detail.setPrice(Float.valueOf(s.toString()));
            price.set(detail.getPrice());
        }
    }

    public void afterShelfNameCreation(CharSequence s){
        if (s.length()!=0){
            detail.setShelf(s.toString());
        }
    }


    public void startAcitivityList(View view){
        Context context = view.getContext();
        Intent intent = new Intent(context, MainActivity.class);
        re.close();
        context.startActivity(intent);
    }

    public void deleteProduct(View view){
        DBOperation dbOp = new DBOperation(re);
        if(create) {
            dbOp.deleteProductId(idProduct);
            Toast toast = Toast.makeText(view.getContext(), "deleted product", Toast.LENGTH_LONG);
            toast.show();
            re.close();
            startAcitivityList(view);
        }
        else{Toast.makeText(view.getContext(), "can't delete items in creation mode", Toast.LENGTH_LONG).show();}
    }




}
