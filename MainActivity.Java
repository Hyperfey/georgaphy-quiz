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
    //Initiating all views so they can be assigned once for the entire scope
    RadioGroup radioGroup;
    ImageView approve;
    ImageView disapprove;
    View questionView;
    TextView questionText;
    View screenWelcome;
    TextView verdictText;
    View verdictView;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        arrayCreate();
    }
    //We assign the views,just once, at startup
    public void assignViews(){
        radioGroup = (RadioGroup)findViewById(R.id.radio1);
        approve = (ImageView)findViewById(R.id.approve);
        disapprove = (ImageView)findViewById(R.id.disapprove);
        questionView = findViewById(R.id.questions_view);
        questionText = (TextView) findViewById(R.id.question_textView);
        screenWelcome = findViewById(R.id.screen_welcome);
        verdictText = (TextView)findViewById(verdict_text);
        verdictView = findViewById(R.id.verdict_view);
        radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton)findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton)findViewById(R.id.radioButton4);
    }
    public void start(View view){
        // Creates the first question
        createQuestion(ask[questionNum][0],ask[questionNum][1],ask[questionNum][2],ask[questionNum][3],ask[questionNum][4],ask[questionNum][5]);
        // Changes visibility to the 2 layouts so the first disappears and the second(with the questions) appears.
        screenWelcome.setVisibility(View.GONE);
        questionView.setVisibility(View.VISIBLE);
    }
    public void nextQuestion(View view) throws InterruptedException {
        // Get the id and then get the selected button index
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int position = radioGroup.indexOfChild(radioButton);
        // If nothing is checked returns an error Toast and loops until a choice is given
        if (position==-1) {
            Toast.makeText(this, "Choose one please", Toast.LENGTH_SHORT).show();
            return ;
        }
        // Checks if the choice made is the right one and gives 10 points if it is
        if (ask[questionNum][position + 1].equals(ask[questionNum][5])) {
            score = score + 10;
            questionView.setVisibility(View.GONE);
            approve.setVisibility(View.VISIBLE);
        }else{
            questionView.setVisibility(View.GONE);
            disapprove.setVisibility(View.VISIBLE);
        }
        // Delaying before calling resetview() so the "right" or "wrong" image is visible for 2 seconds
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                resetview();
            }

        }.start();
        // Adds one ot the question number and calls the questionCreation to post the next question
        radioGroup.clearCheck();
        return ;
    }
    public void resetview(){
        approve.setVisibility(View.INVISIBLE);
        disapprove.setVisibility(View.INVISIBLE);
        if (questionNum < 9) {
            changingQuestion();
        }else{
            if (score > 50){
                verdictText.setText("Congratulations you did great" + "\nYour final score is: " + score);
            }else{
                verdictText.setText("You can do better" + "\nYour final score is: " + score);
            }
            verdictView.setVisibility(View.VISIBLE);
        }
        return;
    }
    public void changingQuestion(){
        //unchecks the radioGroup
        radioGroup.clearCheck();
        // Adds one ot the question number and calls the questionCreation to post the next question
        questionNum = questionNum + 1;
        createQuestion(ask[questionNum][0],ask[questionNum][1],ask[questionNum][2],ask[questionNum][3],ask[questionNum][4],ask[questionNum][5]);
        questionView.setVisibility(View.VISIBLE);
        return ;
    }
    // The method that changes the textViews for every question
    public void createQuestion(String question, String answer_1, String answer_2, String answer_3, String answer_4,String right_answer){
        // We set the text to the desired one
        radioButton1.setText(answer_1);
        radioButton2.setText(answer_2);
        radioButton3.setText(answer_3);
        radioButton4.setText(answer_4);
        questionText.setText(question);
        return ;
    }
    // A method to call at start to construct the questions/answers' array
    // The first element is the question,the last is the right answer and the rest are the possible choices.
    public void arrayCreate(){
        ask[0] = new String[]{"Whats the capital of Greece","Nicosia","Paris","Vilnius","Athens","Athens"};
        ask[1] = new String[]{"Whats the capital of Ukraine","Bucharest","Kyiv","Dnipro","Donetsk","Kyiv"};
        ask[2] = new String[]{"Whats the capital of Romania","Bucharest","Kyiv","Brussels","Budapest","Bucharest"};
        ask[3] = new String[]{"Whats the capital of Hungary","Paris","Kyiv","Budapest","Donetsk","Budapest"};
        ask[4] = new String[]{"Whats the capital of France","Madrid","Zagreb","Paris","Bern","Paris"};
        ask[5] = new String[]{"Whats the capital of Austria","Vienna","Budapest","Dnipro","Paris","Vienna"};
        ask[6] = new String[]{"Whats the capital of Spain","Bucharest","Bern","Madrid","Donetsk","Madrid"};
        ask[7] = new String[]{"Whats the capital of Croatia","Paris","Kyiv","Zagreb","Donetsk","Zagreb"};
        ask[8] = new String[]{"Whats the capital of Lithuania","Bucharest","Zagreb","Dnipro","Vilnius","Vilnius"};
        ask[9] = new String[]{"Whats the capital of Switcherland","Bern","Paris","Vilnius","Donetsk","Bern"};
    }
}
