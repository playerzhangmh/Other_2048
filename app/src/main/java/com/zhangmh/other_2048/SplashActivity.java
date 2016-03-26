package com.zhangmh.other_2048;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this,Home.class));
                        finish();
                    }
                });
            }
        }).start();
        AdManager.getInstance(this).init("14e7a20eb1e50cfe", "45db0e0bde7d0924", true);
        SpotManager.getInstance(this).loadSpotAds();
        SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
        SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_SIMPLE);
        //SpotManager.getInstance(this).showSpotAds(this);
        SpotManager.getInstance(this).showSpotAds(this, new SpotDialogListener() {
            @Override
            public void onShowSuccess() {
                Log.v("hw2","onShowSuccess");
            }

            @Override
            public void onShowFailed() {
                Log.v("hw2","onShowFailed");

            }

            @Override
            public void onSpotClosed() {
                Log.v("hw2","onSpotClosed");

            }

            @Override
            public void onSpotClick(boolean b) {
                Log.v("hw2","onSpotClick");

            }
        });
    }
  /*  @Override
    public void onBackPressed() {
        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(this).disMiss()) {
            // 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
    }*/
}
