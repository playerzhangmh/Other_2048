
package com.zhangmh.other_2048;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangmh.view.Game_gridlayout;
import com.zhangmh.view.Myapplication;

public class Home extends Activity {

    private RelativeLayout rl_home_gameview;
    private TextView tv_home_tagScore;
    private TextView tv_home_currentScore;
    private TextView tv_home_recordScore;
    private Myapplication application;
    private SharedPreferences app_sp;
    private Game_gridlayout mGame_gridlayout;
    private static Home mAcitivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        application = (Myapplication) getApplication();
        mAcitivity=this;
        mGame_gridlayout = new Game_gridlayout(this);

        rl_home_gameview = (RelativeLayout) findViewById(R.id.rl_home_gameview);
        rl_home_gameview.addView(mGame_gridlayout);
        //从sharepreference中获取配置参数，放到三个对应的控件中
        tv_home_tagScore = (TextView) findViewById(R.id.tv_home_tagScore);
        tv_home_currentScore = (TextView) findViewById(R.id.tv_home_currentScore);
        tv_home_recordScore = (TextView) findViewById(R.id.tv_home_recordScore);
        tv_home_tagScore.setText("" + application.getTag_Score());
        tv_home_currentScore.setText(""+0);
        tv_home_recordScore.setText("" + application.getRecord_Score());


    }

    public TextView getTv_home_currentScore() {
        return tv_home_currentScore;
    }

    public TextView getTv_home_recordScore() {
        return tv_home_recordScore;
    }

    public TextView getTv_home_tagScore() {
        return tv_home_tagScore;
    }
    public static Home getmAcitivity(){
        return mAcitivity;
    }


    //底部三个方法
    public void bt_home_revert(View v){

        mGame_gridlayout.revert();

    }
    public void bt_home_restart(View v){
       mGame_gridlayout.restart();

    }
    public void bt_home_options(View v){
        startActivityForResult(new Intent(this,Options.class),100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            mGame_gridlayout.restart();
        }

    }
}
