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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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
    private Button loginButton, registerButton;
    private EditText idText, pwText;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);
        Button loginButton = (Button) findViewById(R.id.login_login_button);
        Button registerButton = (Button) findViewById(R.id.login_register_button);
        EditText idText = (EditText) findViewById(R.id.login_id);
        EditText pwText = (EditText) findViewById(R.id.login_password);


        // 숫자, 영어, 특수문자만 사용하는 필터
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                if (!ps.matcher(source).matches()) {
                    return "";
                }
                return null;
            }
        };

        // 필터 적용
        idText.setFilters(new InputFilter[]{filter});

        // 로그인 버튼 리스너
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = idText.getText().toString();
                String user_pw = pwText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // For Encoding issue, korean DB can't login
                            System.out.println("hello" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) { // 로그인 성공
                                String user_id = jsonObject.getString("user_id");
                                String user_pw = jsonObject.getString("user_pw");

                                Toast.makeText(getApplicationContext(), String.format("로그인 되었습니다."), Toast.LENGTH_SHORT).show();
                                // 로그인 이후 화면 추가 예정
                                Intent login_intent = new Intent(getApplicationContext(), Map.class);

                                login_intent.putExtra("user_id", user_id);
                                login_intent.putExtra("user_pw", user_pw);
                                startActivity(login_intent);

                            } else { // 로그인 실패
                                Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(user_id, user_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginPage.this);
                queue.add(loginRequest);
            }
        });

        // 회원가입 버튼 리스너
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(getApplicationContext(),
                        RegisterPage.class);
                startActivity(register_intent);
            }
        });
    }
}