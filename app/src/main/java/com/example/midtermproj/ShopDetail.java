package com.example.midtermproj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShopDetail extends AppCompatActivity {

    private String jsonString, shop_name, correctNo;
    private ArrayList<Menu> menuArrayList;
    private ArrayList<Menu> menuBasketArrayList;
    private MenuAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ImageView shopImage;
    private Button callButton, addressButton, basketButton;
    private TextView name, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail);
        Intent shopListIntent = getIntent();

        shopImage = (ImageView) findViewById(R.id.detail_image);
        callButton = (Button) findViewById(R.id.detail_call);
        addressButton = (Button) findViewById(R.id.detail_address);
        basketButton = (Button) findViewById(R.id.basket_button);
        name = (TextView) findViewById(R.id.detail_name);
        text = (TextView) findViewById(R.id.detail_text);

        Glide.with(this)
                .load(shopListIntent.getStringExtra("shop_image"))
                .into(shopImage);

        // 이름 지정
        shop_name = shopListIntent.getStringExtra("shop_name");
        name.setText(shop_name);

        // 가게-메뉴 참조키 선언
        correctNo = shopListIntent.getStringExtra("shop_No");

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

        basketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuBasketArrayList.isEmpty())
                    Toast.makeText(getApplicationContext(), "장바구니가 비어있습니다.", Toast.LENGTH_SHORT).show();
                else{
                    Intent basket_intent = new Intent(getApplicationContext(), ShopBasket.class);
                    basket_intent.putExtra("basketName", shop_name);
                    basket_intent.putExtra("basketItem", menuBasketArrayList);
                    startActivity(basket_intent);
                }
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_menu_list);
        mRecyclerView.setHasFixedSize(true); // recyclerview upgrade
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuArrayList = new ArrayList<>();
        menuBasketArrayList = new ArrayList<>();


        mAdapter = new MenuAdapter(this, menuArrayList);
        mAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, View view) {
                Menu item = mAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), "MenuNumber:" + item.getMenuNumber() + ", Data: "
                        + item.getName(), Toast.LENGTH_SHORT).show();


                if (menuBasketArrayList.contains(item))
                    Toast.makeText(getApplicationContext(), "이미 장바구니에 있는 상품입니다.", Toast.LENGTH_SHORT).show();
                else
                    menuBasketArrayList.add(item);
                basketButton.setText("장바구니(" + menuBasketArrayList.size() +")");
                System.out.println(menuBasketArrayList);
            }
        });

        // Item 마다 구분선 추가
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.setAdapter(mAdapter);
        menuArrayList.clear();
        mAdapter.notifyDataSetChanged();

        JsonParse jsonParse = new JsonParse();
        jsonParse.execute("http://sch20185119.dothome.co.kr/getmenu.php");

    }



        public class JsonParse extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;
            String TAG = "JsonParseTest";

            @Override
            protected String doInBackground(String... strings) {
                String url = strings[0];
                try {
                    URL serverURL = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.connect();

                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    }
                    else {
                        inputStream = httpURLConnection.getErrorStream();
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();
                    Log.d(TAG, sb.toString().trim());

                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.d(TAG, "InsertData: Error ", e);
                    String errorString = e.toString();
                    return null;
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(ShopDetail.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                progressDialog.dismiss();
                Log.d(TAG, "response - " + result);

                if(result == null)
                    Toast.makeText(getApplicationContext(), "DB Error.", Toast.LENGTH_SHORT).show();
                else {
                    jsonString = result;
                    showResult();
                }
            }


            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            private void showResult() {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("webnautes");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Menu tmpMenu = new Menu();
                        JSONObject item = jsonArray.getJSONObject(i);
                        tmpMenu.setMenuNumber(item.getString("menu_No"));
                        tmpMenu.setName(item.getString("menu_name"));
                        tmpMenu.setDescription(item.getString("menu_description"));
                        tmpMenu.setImage(item.getString("menu_image"));
                        tmpMenu.setPrice(item.getInt("menu_price"));
                        tmpMenu.setShopNumber(item.getString("shop_No"));

                        // 해당 가게의 메뉴만 리스트에 넣어줌
                        if (tmpMenu.getShopNumber().equals(correctNo))
                            menuArrayList.add(tmpMenu);
                        mAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "showResult : ", e);
                }
            }
        }
    }
