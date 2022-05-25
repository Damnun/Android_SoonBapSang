package com.example.midtermproj;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.CustomViewHolder> {

    private ArrayList<Shop> mList = null;
    private Activity context = null;


    public ShopAdapter(Activity context, ArrayList<Shop> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView likes;
        protected TextView images;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.shop_list_name);
            this.likes = (TextView) view.findViewById(R.id.shop_list_likes);
            this.images = (TextView) view.findViewById(R.id.shop_list_lmages);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_items, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.name.setText(mList.get(position).getName());
        viewholder.likes.setText(mList.get(position).getLikes());
        viewholder.images.setText(mList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}