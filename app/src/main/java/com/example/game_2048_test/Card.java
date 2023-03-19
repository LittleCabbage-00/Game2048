package com.example.game_2048_test;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Card extends FrameLayout {

    TextView textView;

    private int num;

    static Map<Integer, Integer> backgroundColorIdMap = new HashMap<>();
    static Map<Integer, Integer> textColorIdMap = new HashMap<>();

    static {
        textColorIdMap.put(0, R.color.textColor0);
        textColorIdMap.put(2, R.color.textColor2);
        textColorIdMap.put(4, R.color.textColor4);

        backgroundColorIdMap.put(0, R.color.backgroundColor0);
        backgroundColorIdMap.put(2, R.color.backgroundColor2);
        backgroundColorIdMap.put(4, R.color.backgroundColor4);
        backgroundColorIdMap.put(8, R.color.backgroundColor8);
        backgroundColorIdMap.put(16, R.color.backgroundColor16);
        backgroundColorIdMap.put(32, R.color.backgroundColor32);
        backgroundColorIdMap.put(64, R.color.backgroundColor64);
        backgroundColorIdMap.put(128, R.color.backgroundColor128);
        backgroundColorIdMap.put(256, R.color.backgroundColor256);
        backgroundColorIdMap.put(512, R.color.backgroundColor512);
        backgroundColorIdMap.put(1024, R.color.backgroundColor1024);
    }

    public Card(@NonNull Context context) {
        super(context);

        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(getNum() + "");
        textView.setTextSize(50);

        setNum(0);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 10, 10);

        addView(textView, lp);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        textView.setText(num + "");
        //数字改变时，同时改变改变字体大小和颜色
        changeColor(num);
        changeSize(num);
    }

    private void changeSize(int num){
        if(num >= 1024){
            textView.setTextSize(25);
        }
        else if(num >= 128){
            textView.setTextSize(35);
        }
        else if(num >= 16){
            textView.setTextSize(42);
        }
        else{
            textView.setTextSize(50);
        }
    }

    private void changeColor(int num){
        if(num >= 8){
            textView.setTextColor(getResources().getColor(R.color.textColorCommon));
        }
        else{
            textView.setTextColor(getResources().getColor(textColorIdMap.get(num)));
        }
        if(num >= 2048){
            textView.setBackgroundColor(getResources().getColor(R.color.backgroundColorBiggerThan2048));
        }
        else{
            textView.setBackgroundColor(getResources().getColor(backgroundColorIdMap.get(num)));
        }
    }
}

