package a00953080.comp3717.bcit.ca.newwesttriviaapp.activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import a00953080.comp3717.bcit.ca.newwesttriviaapp.R;
import a00953080.comp3717.bcit.ca.newwesttriviaapp.TriviaApp;
import a00953080.comp3717.bcit.ca.newwesttriviaapp.model.Question;
import a00953080.comp3717.bcit.ca.newwesttriviaapp.model.RandomOption;
import a00953080.comp3717.bcit.ca.newwesttriviaapp.model.Score;

public class QuestionActivity extends AppCompatActivity {
    private TextView questionView;
    private TextView feedBackView;
    private TextView countdownView;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;
    private Button clickedButton;

    ///this might have to change unless we can get a random question from a different class and call it here
    private Question        question;
    private RandomOption    randomOption;
    private CountDownTimer  cTimer;
    private Score           score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final float startSize = 40; // Size in pixels
        final float endSize = 25;
        final int   animationDuration = 1000; // Animation duration in ms

        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_question);

        questionView  = (TextView)findViewById(R.id.question_text);
        countdownView = (TextView)findViewById(R.id.countdownTimer);
        answerButton1 = (Button)findViewById(R.id.answerButton1);
        answerButton2 = (Button)findViewById(R.id.answerButton2);
        answerButton3 = (Button)findViewById(R.id.answerButton3);
        answerButton4 = (Button)findViewById(R.id.answerButton4);

        question      = ((TriviaApp) getApplication()).generateQuestion();
        score         = ((TriviaApp) getApplication()).getScore();
        randomOption  = new RandomOption(question);

        //sets the question on the page to the question of Question object
        questionView.setText(question.getQuestion());

        //sets the buttons to the values of the options of the question Object
        answerButton1.setText(randomOption.getFirstPosition());
        answerButton2.setText(randomOption.getSecondPosition());
        answerButton3.setText(randomOption.getThirdPosition());
        answerButton4.setText(randomOption.getFourthPosition());

        answerButton1.setAlpha(0.0f);
        answerButton2.setAlpha(0.0f);
        answerButton3.setAlpha(0.0f);
        answerButton4.setAlpha(0.0f);
        countdownView.setAlpha(0.0f);

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                questionView.setTextSize(animatedValue);
            }
        });

        questionView.startAnimation(AnimationUtils.loadAnimation(QuestionActivity.this, R.anim.movetitle));
        animator.setStartDelay(2500);
        animator.start();

        answerButton1.animate().setStartDelay(3000).alpha(1.0f);
        answerButton2.animate().setStartDelay(3100).alpha(1.0f);
        answerButton3.animate().setStartDelay(3200).alpha(1.0f);
        answerButton4.animate().setStartDelay(3300).alpha(1.0f);
        countdownView.animate().setStartDelay(3300).alpha(1.0f);
        startTimer();
    }

    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    //somehow need to get the question
    public void checkAnswer(final View View){
        feedBackView  = (TextView)findViewById(R.id.feedback);
        clickedButton = (Button) View;

        if(clickedButton.getText().equals(question.getAnswer())) {
            score.correct();
        } else {
            score.incorrect();
        }

        if(score.isGameOver()) {
            goToGameOver();
        } else {
            goToResult();
        }
    }

    public void goToResult(){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToGameOver(){
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
        finish();
    }

    public void backToHomePage(final View view) {
        finish();
    }

    private void startTimer() {
        final Score   score   = this.score;

        cTimer = new CountDownTimer(45000, 1000) {
            public void onTick(long millisUntilFinished)  {
                countdownView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                score.setGameOver(true);

                ((TriviaApp) getApplication()).setScore(score);
                goToGameOver();
            }
        };

        cTimer.start();
    }

    //cancel timer
    private void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
}
