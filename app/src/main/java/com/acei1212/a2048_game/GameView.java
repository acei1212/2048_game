package com.acei1212.a2048_game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2017/12/21.
 */

public class GameView extends GridLayout {
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

    private void initGameView() {
        setColumnCount(4); //固定4列
        setBackgroundColor(0xffbbada0);


        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    //判斷用戶動作
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                System.out.println("left");
                                swipeLeft();

                            } else if (offsetX > 5) {
                                System.out.println("right");
                                swipeRight();
                            }

                        } else {
                            if (offsetY < -5) {
                                System.out.println("up");
                                swipeUp();

                            } else if (offsetY > 5) {
                                System.out.println("down");
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { //動態調整寬、長
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardWidth); //正方型

        startGame();
    }

    private void addCards(int cardWidth, int cardHeight) {
        Card c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight);

                cardsMap[x][y] = c;
            }
        }

    }

    private void startGame() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0); //期初歸零
            }
        }
        addRandomNum(); //添加隨機數1
        addRandomNum(); //添加隨機數2

    }

    private void addRandomNum() {  //建立隨機數字
        emptyPoints.clear(); //預設清空

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4); //在空白欄位如為>0.1,則隨機顯示2; 反之則為4
    }


    private void swipeLeft() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--; //無數值不合併
                            break;

                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) { //假設數值相同，則合併
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); //合併的數值*2
                            cardsMap[x1][y].setNum(0); //合併後，前者歸0
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            break;
                        }
                    }
                }
            }
        }
        addRandomNum(); //添加隨機數1
        checkComplete();
    }


    private void swipeRight() {
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            break;

                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) { //假設數值相同，則合併
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); //合併的數值*2
                            cardsMap[x1][y].setNum(0); //合併後，前者歸0
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            break;
                        }
                    }
                }
            }
        }
        addRandomNum(); //添加隨機數1
        checkComplete();
    }

    private void swipeUp() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            break;

                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) { //假設數值相同，則合併
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); //合併的數值*2
                            cardsMap[x][y1].setNum(0); //合併後，前者歸0
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            break;
                        }
                    }
                }
            }
        }
        addRandomNum(); //添加隨機數1
        checkComplete();
    }

    private void swipeDown() {
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            break;

                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) { //假設數值相同，則合併
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); //合併的數值*2
                            cardsMap[x][y1].setNum(0); //合併後，前者歸0
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            break;
                        }
                    }
                }
            }
        }
        addRandomNum(); //添加隨機數1
        checkComplete();
    }

    private void checkComplete() {
        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 ||
                        ( x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y]))||
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))||
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))||
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))){
                    complete = false;
                    break ALL;
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("Alert").setMessage("GameOver").setPositiveButton("Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                startGame();
                }
            }).show();
        }
    }


    private Card[][] cardsMap = new Card[4][4]; //記錄卡片數組

    private List<Point> emptyPoints = new ArrayList<Point>();
}
