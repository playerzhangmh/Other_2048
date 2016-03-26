package com.zhangmh.other_2048;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zhangmh.view.Myapplication;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

public class Options extends ActionBarActivity implements View.OnClickListener{

    private Button bt_option_linenum;
    private Button bt_option_tagScore;
    private Button bt_option_cancel;
    private Button bt_option_confirm;
    private Myapplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        bt_option_linenum = (Button) findViewById(R.id.bt_option_linenum);
        bt_option_tagScore = (Button) findViewById(R.id.bt_option_tagScore);
        bt_option_cancel = (Button) findViewById(R.id.bt_option_cancel);
        bt_option_confirm = (Button) findViewById(R.id.bt_option_confirm);

        bt_option_linenum.setOnClickListener(this);
        bt_option_tagScore.setOnClickListener(this);
        bt_option_cancel.setOnClickListener(this);
        bt_option_confirm.setOnClickListener(this);
        application = (Myapplication) getApplication();
        bt_option_linenum.setText(application.getLine_Num()+"");
        bt_option_tagScore.setText(application.getTag_Score()+"");

        //插入广告条
        // 实例化广告条
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);

        // 获取要嵌入广告条的布局
        LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);

       // 将广告条加入到布局中
        adLayout.addView(adView);



        adView.setAdListener(new AdViewListener() {
            @Override
            public void onReceivedAd(AdView adView) {
                Log.i("OptionActivity", "onReceivedAd");
            }

            @Override
            public void onSwitchedAd(AdView adView) {
                Log.i("OptionActivity", "onSwitchedAd");
            }

            @Override
            public void onFailedToReceivedAd(AdView adView) {
                Log.i("OptionActivity", "onFailedToReceivedAd");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_option_linenum:
                selectLinenum();
                break;
            case R.id.bt_option_tagScore:
                selectTagscore();
                break;
            case R.id.bt_option_cancel:
                finish();//直接挂掉，绝不允许后退到这个页面
                break;
            case R.id.bt_option_confirm:
                application.setLine_Num(Integer.parseInt(bt_option_linenum.getText().toString()));
                application.setTag_Score(Integer.parseInt(bt_option_tagScore.getText().toString()));
                finish();
                break;
        }
    }


    public void selectLinenum(){
        final String[] items=new String[]{"4","5","6"};
        new AlertDialog.Builder(this)
                .setTitle("选择游戏行列数")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bt_option_linenum.setText(items[which]);
                    }
                })
                .show();
    }

    public void selectTagscore(){
        final String[] items=new String[]{"1024","2048","4096"};
        new AlertDialog.Builder(this)
                .setTitle("选择游戏行列数")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bt_option_tagScore.setText(items[which]);
                    }
                })
                .show();
    }

}
