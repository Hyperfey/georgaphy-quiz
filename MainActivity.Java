package com.example.panagiotis.geographyquiz;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

import java.util.Arrays;
import java.util.Collections;

import static android.R.attr.duration;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.panagiotis.geographyquiz.R.id.radioButton4;
import static com.example.panagiotis.geographyquiz.R.id.verdict_text;

public class MainActivity extends AppCompatActivity {
    // Declaring public variables that we will be using for the entire scope.
    // The score,the questions/answers' array and the current question number
    int score = 0;
    String[][] ask = new String[10][6];
    int questionNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayCreate();
    }
    public void start(View view){
        // Creates the first question
        createQuestion(ask[questionNum][0],ask[questionNum][1],ask[questionNum][2],ask[questionNum][3],ask[questionNum][4],ask[questionNum][5]);
        // Changes visibility to the 2 layouts so the first disappears and the second(with the questions) appears.
        View view1 = findViewById(R.id.screen_welcome);
        view1.setVisibility(View.GONE);
        View view2 = findViewById(R.id.questions_view);
        view2.setVisibility(View.VISIBLE);
    }
    public void nextQuestion(View view) throws InterruptedException {
        // Assigns the radioGroup to View1 to get the id and then get the selected button index
        RadioGroup view1 = (RadioGroup)findViewById(R.id.radio1);
        int radioButtonID = view1.getCheckedRadioButtonId();
        View radioButton = view1.findViewById(radioButtonID);
        int position = view1.indexOfChild(radioButton);
        // Assigns views to right/wrong prompt depending on the answer.
        ImageView approve = (ImageView)findViewById(R.id.approve);
        ImageView disapprove = (ImageView)findViewById(R.id.disapprove);
        View questions = findViewById(R.id.questions_view);
        // If nothing is checked returns an error Toast and loops until a choice is given
        if (position==-1) {
            Toast.makeText(this, "Choose one please", Toast.LENGTH_SHORT).show();
            return ;
        }
        // Checks if the choice made is the right one and gives 10 points if it is
        if (ask[questionNum][position + 1].equals(ask[questionNum][5])) {
            score = score + 10;
            questions.setVisibility(View.GONE);
            approve.setVisibility(View.VISIBLE);
        }else{
            questions.setVisibility(View.GONE);
            disapprove.setVisibility(View.VISIBLE);
        }
        // Delaying
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                resetview();
            }

        }.start();
        // Adds one ot the question number and calls the questionCreation to post the next question
        //Toast.makeText(this, "Score is: " + score + "pos is " + position, Toast.LENGTH_SHORT).show();
        view1.clearCheck();
        return ;
    }
    public void resetview(){
        ImageView approve = (ImageView)findViewById(R.id.approve);
        ImageView disapprove = (ImageView)findViewById(R.id.disapprove);
        approve.setVisibility(View.INVISIBLE);
        disapprove.setVisibility(View.INVISIBLE);
        if (questionNum < 9) {
            changingQuestion();
        }else{
            TextView textView = (TextView)findViewById(verdict_text);
            if (score > 50){
                textView.setText("Congratulations you did great" + "\nYour final score is: " + score);
            }else{
                textView.setText("You can do better" + "\nYour final score is: " + score);
            }
            View view = findViewById(R.id.verdict_view);
            view.setVisibility(View.VISIBLE);

        }
        return;
    }
    public void changingQuestion(){
        RadioGroup view1 = (RadioGroup)findViewById(R.id.radio1);
        View questions = findViewById(R.id.questions_view);
        view1.clearCheck();
        questionNum = questionNum + 1;
        createQuestion(ask[questionNum][0],ask[questionNum][1],ask[questionNum][2],ask[questionNum][3],ask[questionNum][4],ask[questionNum][5]);
        questions.setVisibility(View.VISIBLE);
        return ;
    }
    // The method that changes the textViews for every question
    public void createQuestion(String question, String answer_1, String answer_2, String answer_3, String answer_4,String right_answer){
        // We assign the views to the radioButtons and the text of the question
        RadioButton view1 = (RadioButton)findViewById(R.id.radioButton1);
        RadioButton view2 = (RadioButton)findViewById(R.id.radioButton2);
        RadioButton view3 = (RadioButton)findViewById(R.id.radioButton3);
        RadioButton view4 = (RadioButton)findViewById(R.id.radioButton4);
        TextView view5 = (TextView) findViewById(R.id.question_textView);
        // Then we set the text to the desired one
        view1.setText(answer_1);
        view2.setText(answer_2);
        view3.setText(answer_3);
        view4.setText(answer_4);
        view5.setText(question);
        return ;
    }
    // A method to call at start to create the questions/answers' array
    // The first element is the question,the last is the right answer and the rest are the possible choices.
    public void arrayCreate(){
        ask[0] = new String[]{"Whats the capital of Greece","Nicosia","Salonica","Patra","Athens","Athens"};
        ask[1] = new String[]{"Whats the capital of Ukraine","Bucharest","Kyiv","Dnipro","Donetsk","Kyiv"};
        ask[2] = new String[]{"Whats the capital of Romania","Bucharest","Kyiv","Brussels","Budapest","Bucharest"};
        ask[3] = new String[]{"Whats the capital of Hungary","Bucharest","Kyiv","Budapest","Donetsk","Budapest"};
        ask[4] = new String[]{"Whats the capital of France","Bucharest","Kyiv","Paris","Donetsk","Paris"};
        ask[5] = new String[]{"Whats the capital of Austria","Vienna","Kyiv","Dnipro","Donetsk","Vienna"};
        ask[6] = new String[]{"Whats the capital of Spain","Bucharest","Kyiv","Madrid","Donetsk","Madrid"};
        ask[7] = new String[]{"Whats the capital of Croatia","Bucharest","Kyiv","Zagreb","Donetsk","Zagreb"};
        ask[8] = new String[]{"Whats the capital of Lithuania","Bucharest","Kyiv","Dnipro","Vilnius","Vilnius"};
        ask[9] = new String[]{"Whats the capital of Switcherland","Bern","Kyiv","Dnipro","Donetsk","Bern"};
    }
}
