package com.example.myquizapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionsShowListView extends Activity {

    DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    List<String> question_list = new ArrayList<String>();
    List<String> choose_list = new ArrayList<String>();
    List<String> correct_list = new ArrayList<String>();

    List<Integer> icon_list = new ArrayList<Integer>();
    @SuppressLint("Range")
    public void showAllQuestions() {
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("questions", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String question_info = cursor.getString(cursor.getColumnIndex("question_info"));
//                String choice1 = cursor.getString(cursor.getColumnIndex("choice1"));
//                String choice2 = cursor.getString(cursor.getColumnIndex("choice2"));
//                String choice3 = cursor.getString(cursor.getColumnIndex("choice3"));
//                String choice4 = cursor.getString(cursor.getColumnIndex("choice4"));
                String rightAnswer = cursor.getString(cursor.getColumnIndex("rightAnswer"));
                question_list.add(question_info);
//                choose_list.add(choice1);
//                choose_list.add(choice2);
//                choose_list.add(choice3);
//                choose_list.add(choice4);
                correct_list.add(rightAnswer);
                icon_list.add(R.drawable.logo);
            } while (cursor.moveToNext());
        }
        cursor.close();  // Don't forget to close the cursor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_show_list_view);

        showAllQuestions();

        //4.获取ListView对象
        ListView listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);

        //单击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(QuestionsShowListView.this, "題目：" + question_list.get(position) +
                        " 答案：" + correct_list.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        //长按事件，比如长按删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(QuestionsShowListView.this, "长按事件" + question_list.get(position), Toast.LENGTH_SHORT).show();
                return false; //return false 可包括单击事件， return true则不会产生单击事件
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return question_list.size();
        }

        @Override
        public Object getItem(int position) {
            return question_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyViewHolder holder;
            if(convertView == null){
                //1.加载布局
                convertView = View.inflate(QuestionsShowListView.this, R.layout.item_list, null);
                //2.获取控件
                holder = new MyViewHolder();
                holder.iv_icon = convertView.findViewById(R.id.question_image);
                holder.questionTv = convertView.findViewById(R.id.questionTv);
                holder.answer = convertView.findViewById(R.id.answer);
                convertView.setTag(holder);
            }else{
                holder = (MyViewHolder) convertView.getTag();
            }
            //3.设置数据
            holder.iv_icon.setImageResource(icon_list.get(position));
            holder.questionTv.setText(question_list.get(position));
            holder.answer.setText(correct_list.get(position));
            return convertView;
        }
    }

    class MyViewHolder{
        ImageView iv_icon;
        TextView questionTv;
        TextView answer;
    }
}