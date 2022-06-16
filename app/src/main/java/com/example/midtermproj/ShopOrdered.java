package com.example.midtermproj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShopOrdered extends AppCompatActivity {
    private ImageView shopImage;
    private TextView name, time, price, tong, how;
    private Button home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_ordered);
        Intent shopBasketIntent = getIntent();

        shopImage = (ImageView) findViewById(R.id.ordered_image);
        name = (TextView) findViewById(R.id.ordered_name);
        name.setText(shopBasketIntent.getStringExtra("name"));
        time = (TextView) findViewById(R.id.ordered_time);
        time.setText("예상 소요시간은 "+((int)(Math.random() * 20) + 10) +"분 입니다.");
        price = (TextView) findViewById(R.id.ordered_price);
        price.setText(shopBasketIntent.getStringExtra("price") + "원");
        tong = (TextView) findViewById(R.id.ordered_tong);
        how = (TextView) findViewById(R.id.ordered_how);
        how.setText("필요한 반찬통의 수 : " +((int)(Math.random() * 5) + 1)+"개");
        home = (Button) findViewById(R.id.ordered_gohome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home_intent = new Intent(getApplicationContext(),
                        ShopList.class);
                startActivity(home_intent);
            }
        });


    }
}