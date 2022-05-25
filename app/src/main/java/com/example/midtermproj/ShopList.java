package com.example.midtermproj;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
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
    private String jsonString;
    private ArrayList<Shop> shopArrayList;
    private ShopAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_list);

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        shopArrayList = new ArrayList<>();
        mAdapter = new ShopAdapter(this, shopArrayList);
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
