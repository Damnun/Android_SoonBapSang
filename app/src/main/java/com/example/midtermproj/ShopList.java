package com.example.midtermproj;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShopList extends AppCompatActivity {
    private String jsonString, selectedField;
    private ArrayList<Shop> shopArrayList;
    private ShopAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextViewResult;
    private Button koreaButton, chinaButton, japanButton, fastfoodButton, bunsikButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_list);

        koreaButton = (Button) findViewById(R.id.list_korea_button);
        koreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopArrayList.clear();
                selectedField = "korea";
                JsonParse jsonParse = new JsonParse();
                jsonParse.execute("http://sch20185119.dothome.co.kr/getshop.php");
                mAdapter.notifyDataSetChanged();
            }
        });

        chinaButton = (Button) findViewById(R.id.list_china_button);
        chinaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopArrayList.clear();
                selectedField = "china";
                JsonParse jsonParse = new JsonParse();
                jsonParse.execute("http://sch20185119.dothome.co.kr/getshop.php");
                mAdapter.notifyDataSetChanged();
            }
        });

        japanButton = (Button) findViewById(R.id.list_japan_button);
        japanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopArrayList.clear();
                selectedField = "japan";
                JsonParse jsonParse = new JsonParse();
                jsonParse.execute("http://sch20185119.dothome.co.kr/getshop.php");
                mAdapter.notifyDataSetChanged();
            }
        });

        fastfoodButton = (Button) findViewById(R.id.list_fastfood_button);
        fastfoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopArrayList.clear();
                selectedField = "fastfood";
                JsonParse jsonParse = new JsonParse();
                jsonParse.execute("http://sch20185119.dothome.co.kr/getshop.php");
                mAdapter.notifyDataSetChanged();
            }
        });

        bunsikButton = (Button) findViewById(R.id.list_bunsik_button);
        bunsikButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopArrayList.clear();
                selectedField = "bunsik";
                JsonParse jsonParse = new JsonParse();
                jsonParse.execute("http://sch20185119.dothome.co.kr/getshop.php");
                mAdapter.notifyDataSetChanged();
            }
        });

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setHasFixedSize(true); // recyclerview upgrade
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        shopArrayList = new ArrayList<>();
        mAdapter = new ShopAdapter(this, shopArrayList);
        mAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, View view) {
                Shop item = mAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), "Position:" + position + ", Data: "
                        +item.getName() , Toast.LENGTH_SHORT).show();
                Intent detail_intent = new Intent(getApplicationContext(),
                        ShopDetail.class);
                detail_intent.putExtra("shop_name", item.getName());
                detail_intent.putExtra("shop_image", item.getImage());
                detail_intent.putExtra("shop_call", item.getLatitude());
                detail_intent.putExtra("shop_location", item.getLongitude());
                startActivity(detail_intent);
            }
        });

        // Item 마다 구분선 추가
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.setAdapter(mAdapter);
        shopArrayList.clear();
        mAdapter.notifyDataSetChanged();
        
        JsonParse jsonParse = new JsonParse();
        jsonParse.execute("http://sch20185119.dothome.co.kr/getshop.php");
        
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
            progressDialog = ProgressDialog.show(ShopList.this, "Please Wait", null, true, true);
        }
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
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
                    Shop tmpShop = new Shop();
                    JSONObject item = jsonArray.getJSONObject(i);
                    tmpShop.setNumber(item.getString("shop_No"));
                    tmpShop.setName(item.getString("shop_name"));
                    tmpShop.setDescription(item.getString("shop_description"));
                    tmpShop.setLikes(item.getString("shop_likes"));
                    tmpShop.setImage(item.getString("shop_image"));
                    tmpShop.setLatitude(item.getString("shop_location_latitude"));
                    tmpShop.setLongitude(item.getString("shop_location_longitude"));
                    tmpShop.setField(item.getString("shop_field"));

                    if (tmpShop.getField().equals(selectedField) || selectedField == null)
                        shopArrayList.add(tmpShop);
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "showResult : ", e);
            }
        }
    }
}
