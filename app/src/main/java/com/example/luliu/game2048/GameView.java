package com.example.luliu.game2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luliu on 16/1/15.
 */
public class GameView extends GridLayout{
    private  Card[][] cardsMap=new Card[4][4];
    private List<Point> emptyPoint=new ArrayList();
    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }


    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

        this.setOnTouchListener(new View.OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) { //horizon
                            if (offsetX < -5) {  // to the left
                                swipeLeft();
                            } else if (offsetX > 5) {  //to the right
                                swipeRight();
                            }
                        } else {  //vertical
                            if (offsetY < -5) {  // to the left
                                swipeUp();
                            } else if (offsetY > 5) {  //to the right
                                swipeDown();
                            }
                        }

                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("onSizeChanged");
        int cardWidth=(Math.min(w,h)-10)/4;
        addCards(cardWidth, cardWidth);
        startGame();
    }

    private void addCards(int width,int height){
        Card c;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                c=new Card(getContext());
                c.setNum(0);
                addView(c,width,height);
                cardsMap[i][j]=c;
            }
        }
    }

    private void startGame(){
        System.out.println("startGame");
        for (Card[]cs:cardsMap){
            for (Card c:cs){
                c.setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
        for (Card[]cs:cardsMap){
            for (Card c:cs){
                System.out.print(c.getNum()+",");
            }System.out.println();
        }
        MainActivity.getMainActivity().clearScore();
    }

    private void addRandomNum(){

        emptyPoint.clear();
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (cardsMap[i][j].getNum()<=0){
                    emptyPoint.add(new Point(i,j));
                }
            }
        }

        Point p=emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    private void swipeLeft(){
        System.out.println("swipeLeft");
        boolean ifMerge=false;
        for (int i=0;i<4;i++){//j:col
            for (int j=0;j<3;j++){//i:row
                System.out.print(cardsMap[i][j].getNum()+",");

                int tmp0=cardsMap[i][j].getNum();
                int tmp1=cardsMap[i][j+1].getNum();
                   // System.out.println("i:"+i+",j:"+j+",tmp0:"+tmp0+",tmp1:"+tmp1);
                    if (tmp1>0){
                        if (tmp0<=0){
                            cardsMap[i][j].setNum(tmp1);
                            cardsMap[i][j+1].setNum(0);
                            ifMerge=true;

                        }else if (tmp0==tmp1){
                            cardsMap[i][j].setNum(2*tmp0);
                            cardsMap[i][j+1].setNum(0);
                            j--;
                            MainActivity.getMainActivity().addScore(2*tmp0);
                            ifMerge=true;
                        }
                    }

            }
            System.out.println();
        }
        if (ifMerge) addRandomNum();
    }

    private void swipeRight(){
        System.out.println("swipeRight");
        boolean ifMerge=false;
        for (int i=0;i<4;i++) {//j:col
            for (int j = 3; j >0; j--) {//i:row
                System.out.print(cardsMap[i][j].getNum() + ",");

                int tmp0 = cardsMap[i][j].getNum();
                int tmp1 = cardsMap[i][j -1].getNum();
              //  System.out.println("i:" + i + ",j:" + j + ",tmp0:" + tmp0 + ",tmp1:" + tmp1);
                if (tmp1 > 0) {
                    if (tmp0 <= 0) {
                        cardsMap[i][j].setNum(tmp1);
                        cardsMap[i][j - 1].setNum(0);
                        ifMerge=true;
                    } else if (tmp0 == tmp1) {
                        cardsMap[i][j].setNum(2 * tmp0);
                        cardsMap[i][j - 1].setNum(0);
                        j++;
                        MainActivity.getMainActivity().addScore(2*tmp0);
                        ifMerge=true;
                    }
                }

            }
            System.out.println();
        }
        if (ifMerge) addRandomNum();
    }

    private void swipeUp(){
        System.out.println("swipeUp");
        boolean ifMerge=false;
        for (int j=0;j<4;j++) {//j:col
            for (int i = 0; i <3; i++) {//i:row
                System.out.print(cardsMap[i][j].getNum() + ",");

                int tmp0 = cardsMap[i][j].getNum();
                int tmp1 = cardsMap[i+1][j].getNum();
                //  System.out.println("i:" + i + ",j:" + j + ",tmp0:" + tmp0 + ",tmp1:" + tmp1);
                if (tmp1 > 0) {
                    if (tmp0 <= 0) {
                        cardsMap[i][j].setNum(tmp1);
                        cardsMap[i+1][j].setNum(0);
                        ifMerge=true;
                    } else if (tmp0 == tmp1) {
                        cardsMap[i][j].setNum(2 * tmp0);
                        cardsMap[i+1][j].setNum(0);
                        i--;
                        MainActivity.getMainActivity().addScore(2*tmp0);
                        ifMerge=true;

                    }
                }

            }
            System.out.println();
        }
        if (ifMerge) addRandomNum();
    }

    private void swipeDown(){
        System.out.println("swipeDown");
        boolean ifMerge=false;
        for (int j=0;j<4;j++) {//j:col
            for (int i =3; i >0; i--) {//i:row
                System.out.print(cardsMap[i][j].getNum() + ",");

                int tmp0 = cardsMap[i][j].getNum();
                int tmp1 = cardsMap[i-1][j].getNum();
                //  System.out.println("i:" + i + ",j:" + j + ",tmp0:" + tmp0 + ",tmp1:" + tmp1);
                if (tmp1 > 0) {
                    if (tmp0 <= 0) {
                        cardsMap[i][j].setNum(tmp1);
                        cardsMap[i-1][j].setNum(0);
                        ifMerge=true;
                    } else if (tmp0 == tmp1) {
                        cardsMap[i][j].setNum(2 * tmp0);
                        cardsMap[i-1][j].setNum(0);
                        i++;
                        MainActivity.getMainActivity().addScore(2*tmp0);
                        ifMerge=true;

                    }
                }

            }
            System.out.println();
        }

        if (ifMerge) addRandomNum();
    }


}

