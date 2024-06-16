package com.example.myquizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;

    private EditText et_password;

    DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.edit_text_username);
        et_password = findViewById(R.id.edit_text_password);
    }

    public void exitApp(View view) {
        finish();
    }

    @SuppressLint("Range")
    public boolean getAllUserInfo(String input_username, String input_password) {
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("user_info", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex("userName"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                if(username.equals(input_username) && password.equals(input_password)){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    public void login(View view) {
        String input_username = et_username.getText().toString().trim();
        if(TextUtils.isEmpty(input_username)){
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        String input_password = et_password.getText().toString().trim();
        if(TextUtils.isEmpty(input_password)){
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(input_password.length() < 8 || input_password.length() > 16){
            Toast.makeText(this, "密码必须包含超过8个字符，且不超过16个字符！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(getAllUserInfo(input_username, input_password)){
            Toast.makeText(this, "登陆成功!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "用户不存在，请注册!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }

    }

    public void register(View view) {
        Intent intent =new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
