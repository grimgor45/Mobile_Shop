package com.example.jolyt.mobile_shop;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jolyt.mobile_shop.Database.DBOperation;
import com.example.jolyt.mobile_shop.Database.Tables.Cart;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
    public ObservableField<String> text = new ObservableField<>();
    private List<Cart> mDataset;
    int element;

    public RVAdapter(List<Cart> mDataset){
        this.mDataset = mDataset;
        element = 0;
    }
    @NonNull
    @Override
    public RVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_view_element,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position).getName());

        final int id = mDataset.get(position).getId();
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm re = Realm.getDefaultInstance();
                RealmObject rO = re.where(Cart.class).equalTo("id", id).findFirst();
                DBOperation dBop = new DBOperation(re);
                dBop.deleteCartId(id);
                re.close();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public Button button;
        public MyViewHolder(View v){
            super(v);
            button = (Button)v.findViewById(R.id.button);
            mTextView = (TextView)v.findViewById(R.id.tv);
        }
    }

}
