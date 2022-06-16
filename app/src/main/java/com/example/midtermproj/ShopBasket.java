package com.example.midtermproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShopBasket extends AppCompatActivity {
    private Button goButton;
    private ArrayList<Menu> menuBasketArrayList;
    private RecyclerView mRecyclerView;
    private ShopBasketAdapter mAdapter;
    private TextView shopName;
    private int priceSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_basket);
        Intent shopDetailIntent = getIntent();
        menuBasketArrayList = (ArrayList<Menu>)shopDetailIntent.getSerializableExtra("basketItem");
        shopName = (TextView) findViewById(R.id.basket_shop_name);
        shopName.setText(shopDetailIntent.getStringExtra("basketName"));
        goButton = (Button) findViewById(R.id.basket_go);
        for(int i = 0; i < menuBasketArrayList.size(); i++)
            priceSum += menuBasketArrayList.get(i).price;
        goButton.setText("배달 주문하기(" + priceSum + "원)");

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent order_intent = new Intent(getApplicationContext(),
                        ShopOrdered.class);
                System.out.println("주는 " + priceSum);
                order_intent.putExtra("price", Integer.toString(priceSum));
                order_intent.putExtra("name", shopName.getText());
                startActivity(order_intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_basket_list);
        mRecyclerView.setHasFixedSize(true); // recyclerview upgrade
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShopBasketAdapter(this, menuBasketArrayList);
        // Item 마다 구분선 추가
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }
}
