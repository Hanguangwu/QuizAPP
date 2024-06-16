package com.example.myquizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private final String[] sizes = new String[]{"小号5个", "默认7个", "中号10个"};
    private final int[] numOfQuestions = new int[]{5, 7, 10};

    private int selectTheNumOfQuestions;

    private int index;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void selectNumOfQuestionsByYourself(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择问题数量")
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
                        Intent intent = new Intent(MainMenuActivity.this , PlayActivity.class);
                        intent.putExtra("selectTheNumOfQuestions", selectTheNumOfQuestions);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectTheNumOfQuestions = 7;
                        Intent intent = new Intent(MainMenuActivity.this , PlayActivity.class);
                        intent.putExtra("selectTheNumOfQuestions", selectTheNumOfQuestions);
                        startActivity(intent);
                    }
                })
                .create();
        alertDialog.show();
    }

    public void main_menu_btn(View view) {

        if(view.getId() == R.id.btn_play){
            selectNumOfQuestionsByYourself(view);
        }else if(view.getId() == R.id.btn_setting){
            startActivity(new Intent(MainMenuActivity.this , SettingActivity.class));
        }else if(view.getId() == R.id.btn_exit){
            //this.finishAffinity();
            finish();
        }
    }
}