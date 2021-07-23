 package com.kodoshinobi.braintrainermaths;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;


 public class MainActivity extends AppCompatActivity {
    Button startbutton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainbutton;
    TextView sumtext;
    TextView maxscorevalue;
    TextView timertext;
    ArrayList<Integer> answers=new ArrayList<Integer>();
    int locationofcorrectanswer;
    int maxscore;
    TextView resultText;
    TextView pointstextview;
    int score=0;
    int numberofquestion=0;
    int upperlimit=5,lowerlimit=5;
    public void playAgain(View view){

    //    upperlimit=Integer.parseInt(getIntent().getStringExtra("x"));
    //    lowerlimit=Integer.parseInt(getIntent().getStringExtra("y"));
        generateQuestion(lowerlimit,upperlimit);
        score=0;
        numberofquestion=0;
        timertext.setText("30s");
        pointstextview.setText("0/0");
        resultText.setText("");
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        maxscorevalue.setVisibility(View.INVISIBLE);
        playAgainbutton.setVisibility(View.INVISIBLE);

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timertext.setText(String.valueOf(millisUntilFinished/1000)+"s");
                if(millisUntilFinished/1000==5){
                    MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.clock);
                    mediaPlayer.start();

                }
            }

            @Override
            public void onFinish() {
                timertext.setText("0s");
                button0.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                maxscorevalue.setVisibility(View.VISIBLE);
                resultText.setText("Your Score is : "+ Integer.toString(score)+"/"+Integer.toString(numberofquestion));
                maxscorevalue.setText("Highest score is :"+Integer.toString(maxscore) );
                playAgainbutton.setVisibility(View.VISIBLE);

                Intent intent=new Intent(MainActivity.this, SocreActivity.class);
                intent.putExtra("score",score);
                intent.putExtra("total",numberofquestion);
                startActivity(intent);
                finish();
                return;



            }
        }.start();


    }
    public void generateQuestion(int x , int y){
        Random random = new Random();
        int numberOfMethods = 3;

        switch(random.nextInt(numberOfMethods)) {
            case 0:
                generateQuestionsum(x,y);
                break;
            case 1:
                generateQuestionsub(x,y);
                break;
            case 2:
                generateQuestionproduct(x,y);
                break;
            default:
                generateQuestionsum(x,y);
        }
    }

    public void generateQuestionsum(int x,int y){

        Random rand=new Random();
        int a=rand.nextInt(x);
        int b=rand.nextInt(y);
        sumtext.setText(Integer.toString(a)+"+"+Integer.toString(b));
        answers.clear();
        locationofcorrectanswer=rand.nextInt(4);
        int incorrectanswer;
        for(int i=0;i<4;i++){
            if(i==locationofcorrectanswer){
                answers.add(a+b);
            }
            else{
                incorrectanswer=rand.nextInt(x);
                while(a+b==incorrectanswer){
                    incorrectanswer=rand.nextInt(x);
                }
                answers.add(incorrectanswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void generateQuestionsub(int x,int y){

        Random rand=new Random();
        int a=rand.nextInt(x);
        int b=rand.nextInt(y);
        sumtext.setText(Integer.toString(a)+"-"+Integer.toString(b));
        answers.clear();
        locationofcorrectanswer=rand.nextInt(4);
        int incorrectanswer;
        for(int i=0;i<4;i++){
            if(i==locationofcorrectanswer){
                answers.add(a-b);
            }
            else{
                incorrectanswer=rand.nextInt(x);
                while(a-b==incorrectanswer){
                    incorrectanswer=rand.nextInt(y);
                }
                answers.add(incorrectanswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
    public void generateQuestionproduct(int x,int y){

        Random rand=new Random();
        int a=rand.nextInt(x);
        int b=rand.nextInt(x);
        sumtext.setText(Integer.toString(a)+"*"+Integer.toString(b));
        answers.clear();
        locationofcorrectanswer=rand.nextInt(4);
        int incorrectanswer;
        for(int i=0;i<4;i++){
            if(i==locationofcorrectanswer){
                answers.add(a*b);
            }
            else{
                incorrectanswer=rand.nextInt(x);
                while(a*b==incorrectanswer){
                    incorrectanswer=rand.nextInt(x);
                }
                answers.add(incorrectanswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
    










    public void start(View view){

        startbutton.setVisibility(View.INVISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        maxscorevalue.setVisibility(View.VISIBLE);
        playAgain(playAgainbutton);



    }

    public void chosseanswer(View view){
        if(view.getTag().toString().equals(Integer.toString(locationofcorrectanswer))){
            score++;
            resultText.setText("Correct!");
        }else
        {
            resultText.setText("Wrong!");
        }
        numberofquestion++;
        if(maxscore<score)
        {
            maxscore=score;
        }
        pointstextview.setText(Integer.toString(score)+"/"+Integer.toString(numberofquestion));
        generateQuestion(upperlimit,lowerlimit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startbutton= findViewById(R.id.startButton);
        sumtext=findViewById(R.id.SumtextView);
        button0=findViewById(R.id.button0);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        resultText=findViewById(R.id.resultTextView);
        pointstextview=findViewById(R.id.pointsTextView);
        timertext=findViewById(R.id.timertTextView);
        playAgainbutton=findViewById(R.id.playagainbtn);
        maxscorevalue=findViewById(R.id.highetscore);
    //   playAgain(findViewById(R.id.playagainbtn));
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        maxscorevalue.setVisibility(View.INVISIBLE);
        start(startbutton);
    }





}