package com.example.myquizapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username;

    private EditText et_password1;
    private EditText et_password2;

    DatabaseHelper databaseHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_username = findViewById(R.id.etName);
        et_password1 = findViewById(R.id.etPassword1);
        et_password2 = findViewById(R.id.etPassword2);

        databaseHelper = new DatabaseHelper(this);
        Toast.makeText(this, "用户表创建成功", Toast.LENGTH_SHORT).show();
        db = databaseHelper.getWritableDatabase();
    }

    public void register_submit_button(View view) {
        String username = et_username.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        String password1 = et_password1.getText().toString().trim();
        if(TextUtils.isEmpty(password1)){
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password1.length() < 8 || password1.length() > 16){
            Toast.makeText(this, "密码必须包含超过8个字符，且不超过16个字符！", Toast.LENGTH_SHORT).show();
            return;
        }
        String password2 = et_password2.getText().toString().trim();
        if(TextUtils.isEmpty(password2)){
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password2.length() < 8 || password2.length() > 16){
            Toast.makeText(this, "密码必须包含超过8个字符，且不超过16个字符！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password1.equals(password2)){
            ContentValues values = new ContentValues();
            values.put("userName", username);
            values.put("password", password2);
            db.insert("user_info", null, values);
            Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "两次输入的密码不匹配，请重新输入！", Toast.LENGTH_SHORT).show();
        }


    }

    public void register_cancel_button(View view) {
        finish();
    }
}