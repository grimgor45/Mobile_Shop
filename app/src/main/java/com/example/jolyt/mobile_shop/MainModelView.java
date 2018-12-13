package com.example.jolyt.mobile_shop;

import android.databinding.ObservableField;

public class MainModelView {

    public static ObservableField<String> parentLayoutId = new ObservableField<>();
    public static ObservableField<String> scrollLayoutId = new ObservableField<>();

    public MainModelView(){
        parentLayoutId.set("parentLayout");
        scrollLayoutId.set("scroll");
    }
    public static String getparentLayoutId(){
        return String.valueOf(parentLayoutId);
    }
}
