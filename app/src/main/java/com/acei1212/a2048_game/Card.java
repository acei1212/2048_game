package com.acei1212.a2048_game;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Jack on 2017/12/21.
 */

public class Card extends FrameLayout {
    public Card(@NonNull Context context) {
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff); //卡片背景色
        label.setGravity(Gravity.CENTER); //卡片排板置中

        LayoutParams lp = new LayoutParams(-1, -1); //-1,-1 為填滿容器
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);
        setNum(0);
    }

    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if (num <= 0) {

            label.setText(""); //如果Num<=0,則放入空值
        } else {
            label.setText(num + ""); //不小於0,帶入隨機數字
        }
    }


    public boolean equals(Card o) {
        return getNum() == o.getNum();//判斷兩張卡片是否相同
    }

    private TextView label;

}
