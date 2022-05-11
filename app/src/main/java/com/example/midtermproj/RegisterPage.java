package com.example.midtermproj;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;


public class RegisterPage extends AppCompatActivity {
    EditText register_id, register_name, register_password, register_password_check, register_nickname, register_age, register_sex;
    Button register_button;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.register);
        setTitle("회원가입");

        // 네트워크 설정
        NetworkUtil.setNetworkPolicy();


        Button register_button = (Button) findViewById(R.id.register_register_button);
        register_id = (EditText) findViewById(R.id.register_id);
        register_password = (EditText) findViewById(R.id.register_password);
        register_password_check = (EditText) findViewById(R.id.register_password_check);
        register_name = (EditText) findViewById(R.id.register_name);
        register_nickname = (EditText) findViewById(R.id.register_nickname);
//        register_age = (EditText) findViewById(R.id.register_age);
//        register_sex = (EditText) findViewById(R.id.register_sex);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RegisterPHPRequest request = new RegisterPHPRequest("http://192.168.219.102:80/Data_insert.php");
                    String result = request.PHPRequest(String.valueOf(register_id.getText()), String.valueOf(register_password.getText()),
                            String.valueOf(register_name.getText()), String.valueOf(register_nickname.getText()));
                    System.out.println(String.valueOf(register_id.getText()) + String.valueOf(register_password.getText()) +
                            String.valueOf(register_name.getText()) + String.valueOf(register_nickname.getText()));
                    if(result.equals("1")) {
                        Toast.makeText(getApplication(), "데이터 입력됨", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplication(), "데이터 입력 오류", Toast.LENGTH_SHORT).show();
                    }
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}