package com.example.game_2048_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private final String FILE_NAME = "MyData";
    private final String HIGHEST_SCORE = "highest_score";

    private TextView textScore;
    private TextView textHighestScore;
    private Button buttonReplay;

    private int score = 0;
    private int highestScore = 0;

    public static MainActivity mainActivity;

    public MainActivity(){
        mainActivity = this;
    }

    public void addScore(int score) {
        this.score += score;
        textScore.setText("当前分数 : " + this.score);
        //更新最高分
        updateHighestScore(this.score);
    }

    private void updateHighestScore(int score){
        if(score > highestScore){
            highestScore = score;
            textHighestScore.setText("历史最高分 : " + score);
            //存储最高分
            SharedPreferences shp = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = shp.edit();

            editor.putInt(HIGHEST_SCORE, highestScore);
            editor.apply();
        }
    }

    public void clearScore(){
        score = 0;
        textScore.setText("当前分数 : " + 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        textScore = findViewById(R.id.textScore);
        textHighestScore = findViewById(R.id.textHighestScore);
        buttonReplay = findViewById(R.id.buttonReplay);

        //读取最高分
        SharedPreferences shp = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        highestScore = shp.getInt(HIGHEST_SCORE, 0);
        textHighestScore.setText("历史最高分 : " + highestScore);

        buttonReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.gameView.replayGame();
            }
        });
    }

    private boolean isExit = false;

    class ExitHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                isExit = false;//修改状态为退出
            }
        }

    };

    ExitHandler mHandler = new ExitHandler();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(!isExit){
                isExit = true;
                Toast.makeText(this, "再按一次退出游戏", Toast.LENGTH_SHORT).show();
                //延迟更改状态信息
                mHandler.sendEmptyMessageDelayed(0, 2000);
            }
            else{
                finish();
            }
        }
        return false;
    }
}

