package com.acei1212.a2048_game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

public MainActivity(){
    mainActivity = this;
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView)findViewById(R.id.tvScore);
    }
    public void clearScore(){
    score = 0;
    showScore();
    }
    private void showScore(){
        tvScore.setText(score+"");
    }
    public void addScore(int s){
        score+=s;
        showScore();
    }
    private int score = 0;
    private TextView tvScore;


private static MainActivity mainActivity = null;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
}
