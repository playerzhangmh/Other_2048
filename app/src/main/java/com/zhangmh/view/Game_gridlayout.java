package com.zhangmh.view;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.Toast;

import com.zhangmh.other_2048.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coins on 2016/3/21.
 */
public class Game_gridlayout extends GridLayout {
    private NumberItem[][] numberItemsList;
    private int rowcount;
    private int columncount;
    private List<Point> list;
    private Home mHome;//用来获取application
    private Myapplication application;
    //接下来，我们要修改currentScore了
    private int current_Score;
    private int widthPixels;
     //判定是否有数据产生设置一个flag，并用一个矩阵来记录num
    boolean can_revert;
    private int[][] last_time_State;
    private int last_time_Score;


    public int getRowcount() {
        return rowcount;
    }

    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }

    public int getColumncount() {
        return columncount;
    }

    public void setColumncount(int columncount) {
        this.columncount = columncount;
    }

    float startX;
    float endX;
    float startY;
    float endY;
    private List<Integer> numExtraList=new ArrayList<>();
    public Game_gridlayout(Context context) {
        super(context);
        initView();
    }

    public Game_gridlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    //at begin,any two frames show nums
    public void initView(){
        //从配置文件中获取行列数

        mHome=Home.getmAcitivity();
        application = (Myapplication) mHome.getApplication();
        rowcount=application.getLine_Num();
        columncount=application.getLine_Num();
        numberItemsList=new NumberItem[rowcount][columncount];
        list=new ArrayList<>();
        //getWindowManager()是activity的方法，这里要获取屏幕的尺寸不能用它
        WindowManager mWindowManager = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display defaultDisplay = mWindowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        //int heightPixels = metrics.heightPixels;
        widthPixels = metrics.widthPixels;
        setColumnCount(columncount);
        setRowCount(rowcount);//该布局特性，必须设定行列数
        for(int i=0;i<rowcount;i++){
            for(int j=0;j<columncount;j++){
               // list.add(new Point(i,j));//用来记录每个帧布局实例在集合中的下标，此处实际用不上了，因为下面的update方法已经做了这个事
                numberItemsList[i][j]=new NumberItem(getContext(),0);
                addView(numberItemsList[i][j], widthPixels /columncount, widthPixels /columncount);
            }
        }
        addNum();
        addNum();

    }
    //获取随机下标的帧布局，并修改其中textview控件
    public void addNum(){

        updateList();
        Log.v("HW2", "---" +list.size());
        int size = list.size();
        if(size>0){
            int positon= (int) Math.floor(Math.random()*size);
            Point point=list.get(positon);
            numberItemsList[point.x][point.y].setBackgroundAndNum(2);
        }
    }
    //每次都只添加num为0的项
    public void updateList(){
        list.clear();
        for(int i=0;i<rowcount;i++){
            for(int j=0;j<columncount;j++){
                Log.v("HW2", "---" + numberItemsList[i][j].getNum());
                if(numberItemsList[i][j].getNum()==0){
                   list.add(new Point(i,j));
                }
            }
        }

    }

    //view自带的触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX=event.getX();
                startY=event.getY();
                //记录上一次的棋盘状态
                saveHistory();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endX=event.getX();
                endY=event.getY();
                //防止过短触碰触发事件
                if((Math.abs(endX-startX)>widthPixels/5)||(Math.abs(endY-startY)> widthPixels / 5)) {
                    judgeDirect(startX, endX, startY, endY);
                    mHome.getTv_home_currentScore().setText(current_Score + "");
                    handleResult(getGameStateCode());

                }
                break;
        }

        return true;
    }

    public void revert() {
        //为防止第一次进来就可以回退
        if(can_revert){
            current_Score=last_time_Score;//切记整个活动时间都以改变current_Score来改变积分和上面tv显示内容
            mHome.getTv_home_currentScore().setText(current_Score+"");//回退数据应该先给current_Score，再改变tv控件
            for(int i=0;i<rowcount;i++){
                for(int j=0;j<columncount;j++){
                    numberItemsList[i][j].setBackgroundAndNum(last_time_State[i][j]);;
                }
            }
            //重置数据后，记得将此时数据再次保存，避免来回重置时数据冲突
            saveHistory();
        }


    }

    //记录前一次的棋盘状态，1 棋盘内容 2 分数
    public void saveHistory(){
        last_time_State=new int[rowcount][columncount];
        last_time_Score=current_Score;
        //Log.v("hw2",last_time_Score+"-----"+current_Score);
        for(int i=0;i<rowcount;i++){
            for(int j=0;j<columncount;j++){
                last_time_State[i][j]=numberItemsList[i][j].getNum();
            }
        }
        can_revert=true;
    }

    //通过判断滑动方向操作不同业务
    public void judgeDirect(float start_X,float end_X,float start_Y,float end_Y){

        //在全部为0的时候可以判定下，若果某个轴上没有可以叠加的数据，则滑动无效
         if(Math.abs(start_X-end_X)>Math.abs(end_Y-start_Y)){
             if(start_X>end_X){
                 Log.v("hw2","slideleft");
                 slideLeft();
             }
             else {
                 Log.v("hw2","slideright");
                 slideRight();
             }
         }else {
             if(start_Y>end_Y){
                 Log.v("hw2","slideup");
                 slideUp();
             }else {
                 Log.v("hw2","slidedown");
                 slideDown();
             }
         }
    }

    public void slideLeft(){

        judgeAdd(true, false);

    }
    public void slideRight(){
        judgeAdd(true, true);

    }
    public void slideDown(){

        judgeAdd(false, true);
    }
    public void slideUp(){

        judgeAdd(false, false);
    }
    //设置两个标记，一个是判断移动方向的坐标轴，一个是判断是否为坐标轴的正方向
    public void judgeAdd(boolean slideDirectCount,boolean directFlag){


        if(slideDirectCount){
            //x轴上的移动
            if(directFlag){
                //x坐标轴的正方向
                for(int i=0;i<rowcount;i++){
                    int temp=-1;
                    numExtraList.clear();
                    for(int j=columncount-1;j>=0;j--){
                        if(numberItemsList[i][j].getNum()!=0){
                            if(numberItemsList[i][j].getNum()==temp){
                                if (numExtraList.size()>0){
                                    numExtraList.remove(numExtraList.size() - 1);
                                    numExtraList.add(2 * numberItemsList[i][j].getNum());
                                    current_Score=current_Score+2*numberItemsList[i][j].getNum();
                                    temp=-1;
                                }
                            }else {
                                numExtraList.add(numberItemsList[i][j].getNum());
                                temp=numberItemsList[i][j].getNum();
                            }
                        }
                    }
                    for(int j=columncount-1;j>=0;j--){
                        if(columncount-1-j<numExtraList.size()){
                            numberItemsList[i][j].setBackgroundAndNum(numExtraList.get(columncount-1-j));
                        }else {
                            numberItemsList[i][j].setBackgroundAndNum(0);
                        }
                    }
                }
            }

            //朝着X负方向移动
            else {
                for(int i=0;i<rowcount;i++){
                    int temp=-1;
                    numExtraList.clear();
                    for(int j = 0;j<columncount;j++){
                        if(numberItemsList[i][j].getNum()!=0){
                            if(numberItemsList[i][j].getNum()==temp){
                                if (numExtraList.size()>0){
                                    numExtraList.remove(numExtraList.size()-1);
                                    numExtraList.add(2*numberItemsList[i][j].getNum());
                                    current_Score=current_Score+2*numberItemsList[i][j].getNum();
                                    temp=-1;
                                }
                            }else {
                                numExtraList.add(numberItemsList[i][j].getNum());
                                temp=numberItemsList[i][j].getNum();
                            }
                        }
                    }
                    for(int j=0;j<columncount;j++){
                        if(j<numExtraList.size()){
                            numberItemsList[i][j].setBackgroundAndNum(numExtraList.get(j));
                        }else {
                            numberItemsList[i][j].setBackgroundAndNum(0);
                        }
                    }
                }
            }
        }
        //在Y轴方向上的移动
        else {
            //Y轴正方向，即向下移动
            if(directFlag){
                for(int i=0;i<columncount;i++){
                    int temp=-1;
                    numExtraList.clear();
                    for(int j=rowcount-1;j>=0;j--){
                        if(numberItemsList[j][i].getNum()!=0){
                            if(numberItemsList[j][i].getNum()==temp){
                                if (numExtraList.size()>0){
                                    numExtraList.remove(numExtraList.size()-1);
                                    numExtraList.add(2*numberItemsList[j][i].getNum());
                                    current_Score=current_Score+2*numberItemsList[j][i].getNum();
                                    temp=-1;
                                }
                            }else {
                                numExtraList.add(numberItemsList[j][i].getNum());
                                temp=numberItemsList[j][i].getNum();
                            }
                        }
                    }
                    for(int j=rowcount-1;j>=0;j--){
                        if(rowcount-1-j<numExtraList.size()){
                            numberItemsList[j][i].setBackgroundAndNum(numExtraList.get(rowcount-1-j));
                        }else {
                            numberItemsList[j][i].setBackgroundAndNum(0);
                        }
                    }
                }
            }

            //Y轴负方向的滑动，即向上滑动
            else {
                for(int i=0;i<columncount;i++){
                    int temp=-1;
                    numExtraList.clear();
                    for(int j=0;j<rowcount;j++){
                        if(numberItemsList[j][i].getNum()!=0){
                            if(numberItemsList[j][i].getNum()==temp){
                                if (numExtraList.size()>0){
                                    numExtraList.remove(numExtraList.size()-1);
                                    numExtraList.add(2*numberItemsList[j][i].getNum());
                                    current_Score=current_Score+2*numberItemsList[j][i].getNum();
                                    temp=-1;
                                }
                            }else {
                                numExtraList.add(numberItemsList[j][i].getNum());
                                temp=numberItemsList[j][i].getNum();
                            }
                        }
                    }
                    for(int j=0;j<rowcount;j++){
                        if(j<numExtraList.size()){
                            numberItemsList[j][i].setBackgroundAndNum(numExtraList.get(j));
                        }else {
                            numberItemsList[j][i].setBackgroundAndNum(0);
                        }
                    }
                }
            }

        }
    }
    //除此以外。我们还应该在滑动前判断游戏状态并作出相应处理

    public void handleResult(int resultcode){
        switch (resultcode){
            case 1://游戏可以继续进行，不用处理
                    addNum();
                break;
            case 2://游戏失败，重新开始或退出游戏
                mHome.getTv_home_recordScore().setText(application.getRecord_Score() + "");//死的时候可以看到，嘿嘿
                new AlertDialog.Builder(getContext())
                        .setTitle("fail")
                        .setMessage("Game Over")
                        .setPositiveButton("try again", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                removeAllViews();
                                initView();
                                mHome.getTv_home_currentScore().setText(0 + "");
                                mHome.getTv_home_tagScore().setText(application.getTag_Score()+"");
                            }
                        })
                        .setNegativeButton("drop it", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mHome.finish();
                            }
                        })
                        .show();
                break;
            case 3://游戏成功，重新开始或者挑战更高难度或者选择其他难度
                mHome.getTv_home_recordScore().setText(application.getRecord_Score()+"");
                new AlertDialog.Builder(getContext())
                        .setTitle("success")
                        .setMessage("congratulations!")
                        .setPositiveButton("restart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeAllViews();
                                initView();
                                mHome.getTv_home_currentScore().setText(0 + "");
                                mHome.getTv_home_tagScore().setText(application.getTag_Score()+"");
                            }
                        })
                        .setNegativeButton("challenge higher", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (application.getTag_Score() == 4096 && application.getLine_Num() == 4) {
                                    Toast.makeText(getContext(), "已经是最高难度", Toast.LENGTH_LONG).show();
                                    removeAllViews();
                                    initView();
                                    mHome.getTv_home_currentScore().setText(0 + "");
                                    mHome.getTv_home_tagScore().setText(application.getTag_Score()+"");
                                } else if (application.getTag_Score() == 4096 && application.getLine_Num() > 4) {
                                    application.setLine_Num(application.getLine_Num() - 1);
                                    removeAllViews();
                                    initView();
                                    mHome.getTv_home_currentScore().setText(0 + "");
                                    mHome.getTv_home_tagScore().setText(application.getTag_Score() + "");
                                } else if (application.getTag_Score() < 4096 && application.getLine_Num() == 4) {
                                    application.setTag_Score(application.getTag_Score() + 1024);
                                    removeAllViews();
                                    initView();
                                    mHome.getTv_home_currentScore().setText(0 + "");
                                    mHome.getTv_home_tagScore().setText(application.getTag_Score()+"");
                                } else {
                                    application.setLine_Num(application.getLine_Num() - 1);
                                    application.setTag_Score(application.getTag_Score() + 1024);
                                    removeAllViews();
                                    initView();
                                    mHome.getTv_home_currentScore().setText(0 + "");
                                    mHome.getTv_home_tagScore().setText(application.getTag_Score() + "");

                                }
                            }
                        })
                        .show();
                break;
        }
    }
    //重置游戏
    public void restart(){
        mHome.getTv_home_recordScore().setText(application.getRecord_Score() + "");
        removeAllViews();
        initView();
        mHome.getTv_home_currentScore().setText(0 + "");
        mHome.getTv_home_tagScore().setText(application.getTag_Score()+"");
    }
    //获取游戏状态，1 游戏可以继续进行 2 游戏结束,游戏结束的时候保存分数 3 目标达成，游戏成功，保存分数
    public int getGameStateCode(){
        int tag_score = application.getTag_Score();
        //判断是否有某个模块达到目标分数
        for(int i=0;i<rowcount;i++){
            for(int j=0;j<columncount;j++){
                if(numberItemsList[i][j].getNum()==tag_score){
                    application.setRecord_Score(current_Score);
                    return 3;
                }
            }
        }
        //判断在模块中num全不为0的情况下，是否还能进行游戏
        updateList();
        if(list.size()==0){
            for(int i=0;i<rowcount;i++){
                for(int j=0;j<columncount-1;j++){
                    if(numberItemsList[i][j].getNum()==numberItemsList[i][j+1].getNum()){
                        return 1;
                    }
                }
            }

            for(int i=0;i<columncount;i++){
                for(int j=0;j<rowcount-1;j++){
                    if(numberItemsList[j][i].getNum()==numberItemsList[j+1][i].getNum()){
                        return 1;
                    }
                }
            }
            //若不存在可以相加的模块，则游戏结束
            if(current_Score>=tag_score){
                application.setRecord_Score(current_Score);
            }
            return 2;
        }

        //此时，分数未达目标且list.size()！=0，证明游戏还在进行中
        return 1;
    }

}
