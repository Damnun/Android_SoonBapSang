package com.example.midtermproj;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.CustomViewHolder> {

    private String imageStr = "";
    private ArrayList<Shop> mList = null;
    private Activity context = null;

    public interface OnItemClickListener {
        void onItemClicked(int position, View view);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public ShopAdapter(Activity context, ArrayList<Shop> list) {
        this.context = context;
        this.mList = list;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView picture;
        protected TextView name;
        protected TextView likes;
        protected TextView description;

        public CustomViewHolder(View view) {
            super(view);
            this.picture = (ImageView) view.findViewById(R.id.shop_list_item_image);
            this.name = (TextView) view.findViewById(R.id.shop_list_name);
            this.likes = (TextView) view.findViewById(R.id.shop_list_likes);
            this.description = (TextView) view.findViewById(R.id.shop_list_description);
        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_items, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClicked(position, view);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.name.setText(mList.get(position).getName());
        viewholder.likes.setText(mList.get(position).getLikes());
        viewholder.description.setText(mList.get(position).getDescription());
        imageStr = mList.get(position).getImage().toString();
        Glide.with(context)
                .load(imageStr)
                .into(viewholder.picture);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public Shop getItem(int position) {
        return mList.get(position);
    }

}