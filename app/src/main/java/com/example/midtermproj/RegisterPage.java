package com.example.midtermproj;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterPage extends AppCompatActivity {
    DBHelper myHelper;
    EditText register_id, register_name, register_password, register_password_check, register_nickname, register_age, register_sex;
    Button register_button;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.register);
        setTitle("회원가입");

        Button register_button = (Button) findViewById(R.id.register_register_button);
        register_id = (EditText) findViewById(R.id.register_id);
        register_password = (EditText) findViewById(R.id.register_password);
        register_password_check = (EditText) findViewById(R.id.register_password_check);
        register_name = (EditText) findViewById(R.id.register_name);
        register_nickname = (EditText) findViewById(R.id.register_nickname);
        register_age = (EditText) findViewById(R.id.register_age);
        register_sex = (EditText) findViewById(R.id.register_sex);

        myHelper = new DBHelper(this);
        register_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL(".table");
//                sqlDB.execSQL("INSERT INTO user VALUES ( '"
//                        + register_name.getText().toString() + "' , "
//                        + register_nickname.getText().toString() + "' , "
//                        + register_id.getText().toString() + "' , "
//                        + register_password.getText().toString() + "' , "
//                        + register_age.getText().toString() + "' , "
//                        + register_sex.getText().toString() + ");");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨", 0).show();
            }
        });
    }

}