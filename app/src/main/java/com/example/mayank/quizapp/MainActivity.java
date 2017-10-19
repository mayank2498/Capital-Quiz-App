package com.example.mayank.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;





    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    String quizData[][] = {
            {"China", "Beijing", "Jakarta", "Manila", "Stockholm"},
            {"India", "New Delhi", "Beijing", "Bangkok", "Seoul"},
            {"Indonesia", "Jakarta", "Manila", "New Delhi", "Kuala Lumpur"},
            {"Japan", "Tokyo", "Bangkok", "Taipei", "Jakarta"},
            {"Thailand", "Bangkok", "Berlin", "Havana", "Kingston"},
            {"Brazil", "Brasilia", "Havana", "Bangkok", "Copenhagen"},
            {"Canada", "Ottawa", "Bern", "Copenhagen", "Jakarta"},
            {"Cuba", "Havana", "Bern", "London", "Mexico City"},
            {"Mexico", "Mexico City", "Ottawa", "Berlin", "Santiago"},
            {"United States", "Washington D.C.", "San Jose", "Buenos Aires", "Kuala Lumpur"},
            {"France", "Paris", "Ottawa", "Copenhagen", "Tokyo"},
            {"Germany", "Berlin", "Copenhagen", "Bangkok", "Santiago"},
            {"Italy", "Rome", "London", "Paris", "Athens"},
            {"Spain", "Madrid", "Mexico City", "Jakarta", "Havana"},
            {"United Kingdom", "London", "Rome", "Paris", "Singapore"}
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = (TextView)findViewById(R.id.countLabel);
        questionLabel = (TextView)findViewById(R.id.questionLabel);
        answerBtn1 = (Button)findViewById(R.id.answerBtn1);
        answerBtn2 = (Button)findViewById(R.id.answerBtn2);
        answerBtn3 = (Button)findViewById(R.id.answerBtn3);
        answerBtn4 = (Button)findViewById(R.id.answerBtn4);

        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY",0);
        Log.v("CATEGORY_TAG", quizCategory + "");

        for(int i=0;i<quizData.length;i++){
            ArrayList<String> temp = new ArrayList<>();
            temp.add(quizData[i][0]);
            temp.add(quizData[i][1]);
            temp.add(quizData[i][2]);
            temp.add(quizData[i][3]);
            temp.add(quizData[i][4]);
            quizArray.add(temp);
        }

        showNextQuiz();
    }
    public void showNextQuiz(){
        countLabel.setText("Q"+quizCount);
        Random random = new Random();
        int r_no = random.nextInt(quizArray.size());
        ArrayList<String> quiz = quizArray.get(r_no);

        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);
        quiz.remove(0);
        Collections.shuffle(quiz);
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));
        quizArray.remove(r_no);

    }

    public void checkAnswer(View view){
        Button answerBtn = (Button) findViewById(view.getId());
        String btnText = answerBtn.getText().toString();
        String alertTitle;

        if (btnText.equals(rightAnswer)){
            alertTitle = "Correct";
            rightAnswerCount++;
        }else{
            alertTitle = "Wrong";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Answer is : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT){
                    //show result
                    Intent intent = new Intent(getApplicationContext(), resultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);
                }
                else{
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    
}
