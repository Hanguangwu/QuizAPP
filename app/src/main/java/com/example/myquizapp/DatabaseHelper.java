package com.example.myquizapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "quiz_app.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String sql_questions = "create table if not exists questions(id integer primary key autoincrement, question_info text, choice1 text, choice2 text, " +
                "choice3 text, choice4 text, rightAnswer text)";
        //执行sql语句
        db.execSQL(sql_questions);

        //创建表
        String sql_user = "create table if not exists user_info(id integer primary key autoincrement, userName text, password text, totalScore int)";
        //执行sql语句
        db.execSQL(sql_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
    }
}