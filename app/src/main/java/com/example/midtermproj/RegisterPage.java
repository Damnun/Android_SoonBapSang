package com.example.midtermproj;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPage extends AppCompatActivity {
    EditText register_id, register_name, register_password, register_password_check, register_nickname, register_age, register_sex;
    Button register_button, register_id_check_button;
    private AlertDialog dialog;
    private boolean validate = false; // 아이디 중복 체크


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.register);
        setTitle("회원가입");

        Button register_button = (Button) findViewById(R.id.register_register_button);
        Button register_id_check_button = (Button) findViewById(R.id.register_id_check_button);
        register_id = (EditText) findViewById(R.id.register_id);
        register_password = (EditText) findViewById(R.id.register_password);
        register_password_check = (EditText) findViewById(R.id.register_password_check);
        register_name = (EditText) findViewById(R.id.register_name);
        register_nickname = (EditText) findViewById(R.id.register_nickname);

        register_id_check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserId = register_id.getText().toString();
                if (validate) {
                    return; // 중복 체크 완료
                }

                if (UserId.equals("")) { // 아이디가 비어 있을 경우
                    Toast.makeText(getApplication(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Toast.makeText(getApplication(), "사용할 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                register_id.setEnabled(false);
                                register_id_check_button.setEnabled(false);
                                validate = true;
                            } else {
                                Toast.makeText(getApplication(), "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(UserId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterPage.this);
                queue.add(validateRequest);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate) {
                    Toast.makeText(getApplication(), "아이디 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!String.valueOf(register_password.getText()).equals(String.valueOf(register_password_check.getText()))) {
                    Toast.makeText(getApplication(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (String.valueOf(register_id.getText()).equals("") || String.valueOf(register_password.getText()).equals("") ||
                        String.valueOf(register_name.getText()).equals("") || String.valueOf(register_nickname.getText()).equals("")){
                    Toast.makeText(getApplication(), "모든 칸을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    RegisterRequest request = new RegisterRequest("http://sch20185119.dothome.co.kr/Data_insert.php");
                    String result = request.PHPRequest(String.valueOf(register_id.getText()), String.valueOf(register_password.getText()),
                            String.valueOf(register_name.getText()), String.valueOf(register_nickname.getText()));
                    System.out.println(String.valueOf(register_id.getText()) + String.valueOf(register_password.getText()) +
                            String.valueOf(register_name.getText()) + String.valueOf(register_nickname.getText()));
                    if (result.equals("1")) {
                        Toast.makeText(getApplication(), "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                        finish(); // 회원가입 완료되면 로그인 창으로 돌아가기.
                    } else {
                        Toast.makeText(getApplication(), "데이터 입력 오류", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}