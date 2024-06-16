package com.example.myquizapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SQLiteActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    TextView contentTv;

    EditText question_name_Et, choice1_Et, choice2_Et, choice3_Et, choice4_Et, rightAnswer_Et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        // Initialize EditText and TextView
        question_name_Et = findViewById(R.id.question_name_Et);
        choice1_Et = findViewById(R.id.choice1_Et);
        choice2_Et = findViewById(R.id.choice2_Et);
        choice3_Et = findViewById(R.id.choice3_Et);
        choice4_Et = findViewById(R.id.choice4_Et);
        rightAnswer_Et = findViewById(R.id.rightAnswer_Et);
        contentTv = findViewById(R.id.contentTv);

        databaseHelper = new DatabaseHelper(this);
        //Toast.makeText(this, "问题表创建成功", Toast.LENGTH_SHORT).show();
        db = databaseHelper.getWritableDatabase();
    }

    public void addQuestion(View view) {
        String question_info = question_name_Et.getText().toString();
        String choice1 = choice1_Et.getText().toString();
        String choice2 = choice2_Et.getText().toString();
        String choice3 = choice3_Et.getText().toString();
        String choice4 = choice4_Et.getText().toString();
        String rightAnswer = rightAnswer_Et.getText().toString();
        if (question_info.isEmpty() || choice1.isEmpty() || choice2.isEmpty() || choice3.isEmpty() || choice4.isEmpty()|| rightAnswer.isEmpty()) {
            Toast.makeText(this, "题干或选项不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("question_info", question_info);
        values.put("choice1", choice1);
        values.put("choice2", choice2);
        values.put("choice3", choice3);
        values.put("choice4", choice4);
        values.put("rightAnswer", rightAnswer);
        db.insert("questions", null, values);
        Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("Range")
    public void queryAllQuestions(View view) {
        contentTv.setText("");
        Cursor cursor = db.query("questions", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String question_info = cursor.getString(cursor.getColumnIndex("question_info"));
                String choice1 = cursor.getString(cursor.getColumnIndex("choice1"));
                String choice2 = cursor.getString(cursor.getColumnIndex("choice2"));
                String choice3 = cursor.getString(cursor.getColumnIndex("choice3"));
                String choice4 = cursor.getString(cursor.getColumnIndex("choice4"));
                String rightAnswer = cursor.getString(cursor.getColumnIndex("rightAnswer"));
                contentTv.append("题目题干：" + question_info + "，第一个选项：" + choice1 +
                        "，第二个选项：" + choice2 + "，第三个选项：" + choice3 +
                        "，第四个选项：" + choice4 + "，正确答案：" + rightAnswer + "\n");
            } while (cursor.moveToNext());
        }else{
            Toast.makeText(this, "表为空！", Toast.LENGTH_SHORT).show();
        }
        cursor.close();  // Don't forget to close the cursor
    }

    public void updateQuestion(View view) {
        // 根据问题题干更新题目数据
        String question_info = question_name_Et.getText().toString();
        String choice1 = choice1_Et.getText().toString();
        String choice2 = choice2_Et.getText().toString();
        String choice3 = choice3_Et.getText().toString();
        String choice4 = choice4_Et.getText().toString();
        String rightAnswer = rightAnswer_Et.getText().toString();
        if (question_info.isEmpty() || choice1.isEmpty() || choice2.isEmpty() || choice3.isEmpty() || choice4.isEmpty()|| rightAnswer.isEmpty()) {
            Toast.makeText(this, "题干或选项不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("question_info", question_info);
        values.put("choice1", choice1);
        values.put("choice2", choice2);
        values.put("choice3", choice3);
        values.put("choice4", choice4);
        values.put("rightAnswer", rightAnswer);
        db.update("questions", values, "question_info=?", new String[]{question_info});
        Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
    }

    public void deleteQuestion(View view) {
        // 根据问题题干删除问题
        String question_info = question_name_Et.getText().toString();
        if (question_info.isEmpty()) {
            Toast.makeText(this, "题目题干不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        db.delete("questions", "question_info=?", new String[]{question_info});
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    public void returnToUpperLevel(View view) {
        finish();
    }
}