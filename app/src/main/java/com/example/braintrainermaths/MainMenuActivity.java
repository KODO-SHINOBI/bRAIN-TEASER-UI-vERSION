package com.example.braintrainermaths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.braintrainermaths.leaderboard.LeaderboardActivity;

public class MainMenuActivity extends AppCompatActivity {

   private Button playbtn,highscorebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        playbtn = findViewById(R.id.bttnplay);
        highscorebtn = findViewById(R.id.bttnhighscore);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                play();
            }
        });
        highscorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highscore();
            }
        });
    }
        public void play(){
            Intent intent=new Intent(MainMenuActivity.this,MainActivity.class);
            startActivity(intent);
    }
    public void highscore(){
        Intent intent=new Intent(MainMenuActivity.this, LeaderboardActivity.class);
        startActivity(intent);
    }
}