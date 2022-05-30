package com.example.midtermproj;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ShopDetail extends AppCompatActivity {
    ImageView shopImage;
    Button callButton, addressButton;
    TextView name, text;
    private String shop_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail);
        Intent shopListIntent = getIntent();

        shopImage = (ImageView) findViewById(R.id.detail_image);
        callButton = (Button) findViewById(R.id.detail_call);
        addressButton = (Button) findViewById(R.id.detail_address);
        name = (TextView) findViewById(R.id.detail_name);
        text = (TextView) findViewById(R.id.detail_text);

        Glide.with(this)
                .load(shopListIntent.getStringExtra("shop_image"))
                .into(shopImage);

        // 이름 지정
        shop_name = shopListIntent.getStringExtra("shop_name");
        name.setText(shop_name);

        // 전화번호
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(shopListIntent.getStringExtra("shop_call"));
            }
        });

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(shopListIntent.getStringExtra("shop_location"));
            }
        });


    }
}