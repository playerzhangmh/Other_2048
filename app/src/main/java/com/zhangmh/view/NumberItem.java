package com.zhangmh.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by coins on 2016/3/21.
 */
public class NumberItem extends FrameLayout {
    private int num;
    private Game_textview mGame_textview;//实际上，textview可以直接在这边new一个出来，毕竟在重写的一个textview里并没有玩出什么花来
    public NumberItem(Context context) {
        super(context);
        initView(0);
    }

    public NumberItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(0);
    }
    public NumberItem(Context context,int num) {
        super(context);
        initView(num);
        this.num=num;
    }
    public int getNum(){
        return num;
    }
    public void initView(int numS){
        mGame_textview=new Game_textview(getContext(),numS);
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);//设定textview控件的尺寸
        params.setMargins(5,5,5,5);
        setBackgroundAndNum(numS);//设定背景和text
        mGame_textview.setGravity(Gravity.CENTER);//内容居中
        addView(mGame_textview,params);
    }

    public void setBackgroundAndNum(int numS){
        num=numS;
        if(numS==0){
            mGame_textview.setText("");
        }else {
            mGame_textview.setText(""+numS);
        }
        //此处设定num为0 的控件不显示内容，是为了后面方便通过num获得布局控件的状态
        switch (numS){
            case 0:
                mGame_textview.setBackgroundColor(Color.GRAY);
                break;
            case 2:
                mGame_textview.setBackgroundColor(0xFFF99561);
                break;
            case 4:
                mGame_textview.setBackgroundColor(0xFFF9DD43);
                break;
            case 8:
                mGame_textview.setBackgroundColor(0xFFF9C300);
                break;
            case 16:
                mGame_textview.setBackgroundColor(0xFFF9AE00);
                break;
            case 32:
                mGame_textview.setBackgroundColor(0xFFFAA000);
                break;
            case 64:
                mGame_textview.setBackgroundColor(0xFFF9AE00);
                break;
            case 128:
                mGame_textview.setBackgroundColor(0xFFFAA000);
                break;
            case 256:
                mGame_textview.setBackgroundColor(0xFFFA9561);
                break;
            case 512:
                mGame_textview.setBackgroundColor(0xFFFFAAAA);
                break;
            case 1024:
                mGame_textview.setBackgroundColor(0xFFF99561);
                break;
            case 2048:
                mGame_textview.setBackgroundColor(0xFFFF0561);
                break;
            case 4096:
                mGame_textview.setBackgroundColor(0xFFFF0041);
                break;

        }
    }
}
