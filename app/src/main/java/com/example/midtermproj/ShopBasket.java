package com.example.midtermproj;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShopBasket extends AppCompatActivity {
    private ArrayList<Menu> menuBasketArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_basket);
        Intent shopDetailIntent = getIntent();
        menuBasketArrayList = (ArrayList<Menu>)shopDetailIntent.getSerializableExtra("basketItem");
        System.out.println(menuBasketArrayList);
        System.out.println(menuBasketArrayList.get(0).getName());


    }
}
