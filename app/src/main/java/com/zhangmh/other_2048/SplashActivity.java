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

    private boolean jumpFlag=true;
    private boolean pausejumpFlag;//用来判断从onpause到onresume状态的标示


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
                        if(jumpFlag) {
                            Log.v("hw2","onCreatejumpFlag");
                            Intent intent=new Intent(SplashActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
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
                Log.v("hw2", "onShowSuccess");
            }

            @Override
            public void onShowFailed() {
                Log.v("hw2", "onShowFailed");

            }

            @Override
            public void onSpotClosed() {
                Log.v("hw2", "onSpotClosed");

            }

            @Override
            public void onSpotClick(boolean b) {
                Log.v("hw2", "onSpotClick");

            }
        });

    }
    //按back键后，将当前activity干掉，不再跳入下个activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        jumpFlag=false;
        Log.v("hw2","onBackPressed");
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

   /* @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        jumpFlag=false;
        Log.v("hw2", "onUserLeaveHint");
    }*/


    //当home键再次进来的时候

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(SplashActivity.this, Home.class));
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //只有没有进入不可见状态才会从这里跳转
        if(pausejumpFlag){
            startActivity(new Intent(SplashActivity.this, Home.class));
            this.finish();
        }
    }

    @Override
    //只有当跳到下一个activity或后退或home等的时候，才会调用这个方法
    //而当oncreate中跳到下一个activity时，这里已经没有用了
    //当back键时会直接finish掉
    //而按Home键后，通过restart去直接跳转
    //当只进入不可操作状态时候，获取焦点后直接在onresume中跳转
    //而一旦onstop状态存在过，resume就不会执行跳转
    protected void onPause() {
        super.onPause();
        Log.v("hw2", "onPause");
        jumpFlag=false;
        pausejumpFlag=true;//如果调用到onstop方法的话，将他置为false,不执行跳转，若没有执行到，则会跳转

    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.v("hw2", "onStop");
        pausejumpFlag=false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("hw2", "onDestroy");

    }
}
