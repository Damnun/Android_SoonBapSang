package com.example.midtermproj;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginPage extends Activity {
    private String jsonString;
    ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);
        Button loginButton = (Button) findViewById(R.id.login_login_button);
        Button registerButton = (Button) findViewById(R.id.login_register_button);
        EditText idText = (EditText) findViewById(R.id.login_id);
        EditText pwText = (EditText) findViewById(R.id.login_password);


        // 숫자 혹은 영어만 사용하는 필터
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend){
                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                if(!ps.matcher(source).matches()){
                    return "";
                }
                return null;
            }
        };

        // 필터 적용
        idText.setFilters(new InputFilter[] {filter});
        pwText.setFilters(new InputFilter[] {filter});

        // 로그인 버튼 리스너
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "로그인 할게요",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 회원가입 버튼 리스너
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent register_intent = new Intent(getApplicationContext(),
                        RegisterPage.class);
                startActivity(register_intent);
            }
        });

        // DB 동기화를 위한 json 변환과정 <-> php 서버
        JsonParse jsonParse = new JsonParse();
        jsonParse.execute("http://192.168.219.102:80/connect.php");
    }

    // DB 동기화, json 변환 후 읽어오기기
    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest";
        @Override
        protected String doInBackground(String... strings){
            String url = strings[0];
            try{
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, sb.toString().trim());

                return sb.toString().trim();
             } catch(Exception e){
                Log.d(TAG, "InsertData: Error", e);
                String errorString = e.toString();
                return null;
            }
        }

//        @Override
//        protected void onPostExecute(String fromdoInBackgroundString) {
//            super.onPostExecute(fromdoInBackgroundString);
//
//            if(fromdoInBackgroundString == null)
//                System.out.println("error");
//            else {
//                jsonString = fromdoInBackgroundString;
//                userArrayList = doParse();
//                Log.d(TAG, userArrayList.get(0).getName());
//                System.out.println(userArrayList.get(0).getName());
//            }
//        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        private ArrayList<User> doParse(){
            ArrayList<User> tmpUserArray = new ArrayList<User>();
            try{
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("User");

                for(int i = 0; i < jsonArray.length(); i++){
                    User tmpUser = new User();
                    JSONObject item = jsonArray.getJSONObject(i);
                    tmpUser.setName(item.getString("이름"));
                    tmpUser.setNickname(item.getString("닉네임"));
                    tmpUser.setUser_id(item.getString("아이디"));
                    tmpUser.setUser_pw(item.getString("비밀번호"));
                    tmpUser.setUser_age(item.getInt("나이"));
                    tmpUser.setUser_mail(item.getString("이메일"));
                    tmpUser.setUser_register_date("가입일자");
                    tmpUser.setUser_sex("성별");

                    tmpUserArray.add(tmpUser);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return tmpUserArray;
        } // JSON을 Arraylist에

    }
}
