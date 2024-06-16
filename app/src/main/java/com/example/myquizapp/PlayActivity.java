package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    List<String> question_list = new ArrayList<String>();
    List<String> choose_list1 = new ArrayList<String>();
    List<String> choose_list2 = new ArrayList<String>();
    List<String> choose_list3 = new ArrayList<String>();
    List<String> choose_list4 = new ArrayList<String>();
    List<String> correct_list = new ArrayList<String>();


    TextView cpt_question , text_question;
    Button btn_choose1 , btn_choose2 , btn_choose3 , btn_choose4 , btn_next;

    DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    int currentQuestion =  0  ;
    int scorePlayer =  0  ;
    boolean isClickBtn = false;
    String valueChoose = "";
    Button btn_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        cpt_question = findViewById(R.id.cpt_question);
        text_question = findViewById(R.id.text_question);

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        btn_next = findViewById(R.id.btn_next);

        findViewById(R.id.image_back).setOnClickListener(
                a-> finish()
        );
        Intent intent = getIntent();
        int numOfQuestions = intent.getIntExtra("selectTheNumOfQuestions", 7);
        getAllQuestions(numOfQuestions);
        showData();
        btn_next.setOnClickListener(
                view -> {
                    if (isClickBtn){
                        isClickBtn = false;

                        if(!valueChoose.equals(correct_list.get(currentQuestion))){
                            Toast.makeText(PlayActivity.this , "很遗憾，你选错了",Toast.LENGTH_SHORT).show();
                            btn_click.setBackgroundResource(R.drawable.background_btn_error);

                        }else {
                            Toast.makeText(PlayActivity.this , "恭喜你，你选对了！",Toast.LENGTH_SHORT).show();
                            btn_click.setBackgroundResource(R.drawable.background_btn_correct);

                            scorePlayer++;
                        }
                        new Handler().postDelayed(() -> {
                            if(currentQuestion != question_list.size() - 1){
                                currentQuestion = currentQuestion + 1;
                                showData();
                                valueChoose = "";
                                btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
                                btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
                                btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
                                btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);

                            }else {
                                Intent intentOfResult  = new Intent(PlayActivity.this , ResultActivity.class);
                                intentOfResult.putExtra("finalScore" , scorePlayer);
                                startActivity(intentOfResult);
                                finish();
                            }

                        },2000);

                    }else {
                        Toast.makeText(PlayActivity.this ,  "你可以选择一个",Toast.LENGTH_SHORT).show();
                    }
                }
        );


    }

    void showData(){
        cpt_question.setText((currentQuestion + 1) + "/" + question_list.size());
        text_question.setText(question_list.get(currentQuestion));

        btn_choose1.setText(choose_list1.get(currentQuestion));
        btn_choose2.setText(choose_list2.get(currentQuestion));
        btn_choose3.setText(choose_list3.get(currentQuestion));
        btn_choose4.setText(choose_list4.get(currentQuestion));

    }

    public void ClickChoose(View view) {
        btn_click = (Button)view;

        if (isClickBtn) {
            btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);
        }
        chooseBtn();


    }
    void chooseBtn(){

        btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
        isClickBtn = true;
        valueChoose = btn_click.getText().toString();
    }

    @SuppressLint("Range")
    public void getAllQuestions(int numOfQuestions) {
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("questions", null, null, null, null, null, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                String question_info = cursor.getString(cursor.getColumnIndex("question_info"));
                String choice1 = cursor.getString(cursor.getColumnIndex("choice1"));
                String choice2 = cursor.getString(cursor.getColumnIndex("choice2"));
                String choice3 = cursor.getString(cursor.getColumnIndex("choice3"));
                String choice4 = cursor.getString(cursor.getColumnIndex("choice4"));
                String rightAnswer = cursor.getString(cursor.getColumnIndex("rightAnswer"));
                question_list.add(question_info);
                choose_list1.add(choice1);
                choose_list2.add(choice2);
                choose_list3.add(choice3);
                choose_list4.add(choice4);
                correct_list.add(rightAnswer);
                count++;
            } while (cursor.moveToNext() && count < numOfQuestions);
        }
        cursor.close();
        if(count < numOfQuestions){
            Toast.makeText(this, "数据库中问题数量不够，请添加足够数量的问题", Toast.LENGTH_SHORT).show();
        }
        shuffleTheOrderOfQuestions();
    }

    public void shuffleTheOrderOfQuestions(){
        long seed = System.nanoTime();

        Collections.shuffle(question_list, new Random(seed));
        Collections.shuffle(choose_list1, new Random(seed));
        Collections.shuffle(choose_list2, new Random(seed));
        Collections.shuffle(choose_list3, new Random(seed));
        Collections.shuffle(choose_list4, new Random(seed));
        Collections.shuffle(correct_list, new Random(seed));
    }
}