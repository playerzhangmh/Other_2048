package com.zhangmh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by coins on 2016/3/21.
 */
public class Game_textview extends TextView {
    int num;
    public Game_textview(Context context) {
        super(context);
    }

    public Game_textview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Game_textview(Context context,int num) {
        super(context);
        this.num=num;
    }

    //get the area of textview


    @Override
    protected void onDraw(Canvas canvas) {


        //draw a frame for textview ,its parentview will set some propetry for it ,then add it.
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(1,1,getMeasuredWidth(),getMeasuredHeight(),paint);
        super.onDraw(canvas);

    }
}
