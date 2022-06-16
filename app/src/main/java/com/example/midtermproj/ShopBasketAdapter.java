package com.example.midtermproj;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShopBasketAdapter extends RecyclerView.Adapter<ShopBasketAdapter.CustomViewHolder> {

    private String imageStr = "";
    private ArrayList<Menu> mList = null;
    private Activity context = null;


    public ShopBasketAdapter(Activity context, ArrayList<Menu> list) {
        this.context = context;
        this.mList = list;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView picture;
        protected TextView name;
        protected TextView price;
//        protected Button delete;

        public CustomViewHolder(View view) {
            super(view);
            this.picture = (ImageView) view.findViewById(R.id.basket_list_item_image);
            this.name = (TextView) view.findViewById(R.id.basket_list_name);
            this.price = (TextView) view.findViewById(R.id.basket_list_price);
//            this.delete = (Button) view.findViewById(R.id.basket_list_button);
        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.basket_items, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.name.setText(mList.get(position).getName());
        viewholder.price.setText(Integer.toString(mList.get(position).getPrice()) + "원");
        imageStr = mList.get(position).getImage().toString();
        Glide.with(context)
                .load(imageStr)
                .into(viewholder.picture);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public Menu getItem(int position) {
        return mList.get(position);
    }

}