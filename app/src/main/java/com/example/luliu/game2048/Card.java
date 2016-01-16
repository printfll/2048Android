package com.example.luliu.game2048;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by luliu on 16/1/15.
 */
public class Card extends FrameLayout{
    private int num=0;
    private  TextView label;
    public Card(Context context) {
        super(context);
        label=new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);

        label.setGravity(Gravity.CENTER);
        LayoutParams lp=new LayoutParams(-1,-1);
        addView(label,lp);
        setNum(0);
        lp.setMargins(10,10,0,0);
    }


    public int getNum(){
        return num;
    }

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label) {
        this.label = label;
    }

    public void setNum(int num) {
        this.num = num;
        if(num<=0){
            label.setText("");
        }else{
            label.setText(num+"");
        }


    }

    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }


}
