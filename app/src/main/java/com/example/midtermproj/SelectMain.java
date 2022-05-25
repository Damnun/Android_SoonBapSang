package com.example.midtermproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SelectMain extends Activity {
    Button marketButton, takeoutButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.select_main);
        setTitle("서비스 선택");

        Button marketButton = (Button) findViewById(R.id.select_market_button);
        Button takeoutButton = (Button) findViewById(R.id.select_takeout_button);

        marketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp_intent = new Intent(getApplicationContext(), ShopList.class);
                startActivity(temp_intent);
            }
        });

        takeoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeout_intent = new Intent(getApplicationContext(), ShopMain.class);
                startActivity(takeout_intent);
            }
        });
    }
}
