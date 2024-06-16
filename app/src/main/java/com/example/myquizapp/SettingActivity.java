package com.example.myquizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    private Button addQuestion_button;

    private final String[] sizes = new String[]{"小号5个", "默认7个", "中号10个"};
    private final int[] numOfQuestions = new int[]{5, 7, 10};

    private int selectTheNumOfQuestions;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        addQuestion_button = findViewById(R.id.addQuestion_button);
        // Setting click listeners for each button
        addQuestion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SQLiteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void exit_setting(View view) {
        finish();
    }

    public void addQuestionByYourself(View view) {
        Intent intent = new Intent(this, SQLiteActivity.class);
        startActivity(intent);
    }

    public void queryAllInsertedQuestions(View view) {
        Intent intent = new Intent(this, QuestionsShowListView.class);
        startActivity(intent);
    }

    public void selectNumOfQuestionsByYourself(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择问题数量，点击确认立即生效，点击取消会取默认值")
                .setSingleChoiceItems(sizes, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        index = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectTheNumOfQuestions = numOfQuestions[index];
                        Intent intent = new Intent(SettingActivity.this , PlayActivity.class);
                        intent.putExtra("selectTheNumOfQuestions", selectTheNumOfQuestions);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectTheNumOfQuestions = numOfQuestions[index];
                        Intent intent = new Intent(SettingActivity.this , PlayActivity.class);
                        intent.putExtra("selectTheNumOfQuestions", selectTheNumOfQuestions);
                        startActivity(intent);
                    }
                })
                .create();
        alertDialog.show();
    }
}