package com.example.jolyt.mobile_shop;

import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import com.example.jolyt.mobile_shop.Database.DBOperation;
import com.example.jolyt.mobile_shop.Database.Tables.Product;
import com.example.jolyt.mobile_shop.Database.Tables.Shelf;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DetailModelView {
    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> comment = new ObservableField<>();
    public final ObservableField<String> shelf = new ObservableField<>();
    public DetailFields detail;
    public final ObservableField<String> buttonLabel = new ObservableField<>();
    public final ObservableFloat price = new ObservableFloat();
    private int idProduct;


    public DetailModelView(int idProduct){
        this.idProduct = idProduct;
        detail = new DetailFields();
        Realm re = Realm.getDefaultInstance();
        RealmObject product = re.where(Product.class).equalTo("id", idProduct).findFirst();
        name.set(((Product) product).getName());
        comment.set(((Product) product).getCommentary());
        shelf.set(((Product) product).getShelf().getName());
        re.close();
        buttonLabel.set("blablabla");
    }

    public void saveChanges(){
        Log.i("savechanges", comment.get());

        Realm re = Realm.getDefaultInstance();
        DBOperation dbOp = new DBOperation(re);
        dbOp.updateProduct(idProduct, name.get(), re.where(Shelf.class).findFirst().getId(), comment.get(), price.get());
        re.close();
    }

    public void afterTextChange(CharSequence s)
    {
        detail.set(s.toString());
    }
    public void afterNameChange(CharSequence s)
    {
        Log.i("truc", s.toString());
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
        detail.setPrice(Float.valueOf(s.toString()));
        price.set(detail.getPrice());
    }
    public void afterShelfChange(CharSequence s)
    {
        detail.setShelf(s.toString());
    }


}
