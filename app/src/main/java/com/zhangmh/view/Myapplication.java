package com.zhangmh.view;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.TextView;

/**
 * Created by coins on 2016/3/22.
 */
public class Myapplication extends Application {
//通过它来保存全局数据，比如在第一部分控件中三个textview张的内容，已经用来保存配置数据的SharedPreferences
    SharedPreferences app_sp;
    int tag_Score;
    int record_Score;
    int line_Num;

    public SharedPreferences getApp_sp() {
        return app_sp;
    }

    public void setApp_sp(SharedPreferences app_sp) {
        this.app_sp = app_sp;
    }

    public int getTag_Score() {
        return tag_Score;
    }

    public void setTag_Score(int tag_Score) {
        this.tag_Score = tag_Score;
        SharedPreferences.Editor edit = app_sp.edit();
        edit.putInt("tag_Score", tag_Score);
        edit.commit();
    }

    public int getRecord_Score() {
        return record_Score;
    }

    public void setRecord_Score(int record_Score) {
        this.record_Score = record_Score;
        SharedPreferences.Editor edit = app_sp.edit();
        edit.putInt("record_Score", record_Score);
        edit.commit();
    }

    public int getLine_Num() {
        return line_Num;
    }

    public void setLine_Num(int line_Num) {
        this.line_Num = line_Num;
        SharedPreferences.Editor edit = app_sp.edit();
        edit.putInt("line_Num", line_Num);
        edit.commit();
    }

    public void onCreate() {
        super.onCreate();
        app_sp = getSharedPreferences("config", MODE_PRIVATE);
        tag_Score=app_sp.getInt("tag_Score",1024);
        record_Score=app_sp.getInt("record_Score",0);
        line_Num= app_sp.getInt("line_Num",4);

        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        });

    }
}
