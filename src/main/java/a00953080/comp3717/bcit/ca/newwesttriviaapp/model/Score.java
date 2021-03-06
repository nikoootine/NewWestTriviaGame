package a00953080.comp3717.bcit.ca.newwesttriviaapp.model;

import a00953080.comp3717.bcit.ca.newwesttriviaapp.TriviaApp;

/**
 * Created by Owner on 2017-03-06.
 */

public class Score {

    private long    score;
    private long    previousScore;
    private boolean isGameOver;
    private boolean isPreviousAnswerCorrect;
    private long    wager;
    private long    highscore = 100;


    public Score(long score, boolean isGameOver) {
        this.score      = score;
        this.isGameOver = isGameOver;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getWager() { return wager; }

    public void setWager(long wager) { this.wager = wager; }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public boolean isPreviousAnswerCorrect() {
        return isPreviousAnswerCorrect;
    }

    public void setPreviousAnswerCorrect(boolean previousAnswerCorrect) {
        isPreviousAnswerCorrect = previousAnswerCorrect;
    }
    public boolean isWagerSet(){
        return wager > TriviaApp.CORRECT_SCORE_ADD;
    }

    public long getHighScore() { return highscore; }



    public void correct() {
        this.setPreviousScore(this.score);
        if(isWagerSet())
            this.setScore(this.score + wager);
        else
            this.setScore(this.score + TriviaApp.CORRECT_SCORE_ADD);
        this.setPreviousAnswerCorrect(true);

        if(this.score > highscore){
            this.highscore = this.score;
        }
    }

    public void incorrect() {
        this.setPreviousScore(this.score);
        if(isWagerSet())
            this.setScore(this.score - wager);
        else
            this.setScore(this.score + TriviaApp.WRONG_SCORE_ADD);
        this.setPreviousAnswerCorrect(false);

        if(this.getScore() <= 0) {
            this.setGameOver(true);
        }
    }

    public long getPreviousScore() {
        return previousScore;
    }

    public void setPreviousScore(long previousScore) {
        this.previousScore = previousScore;
    }
}
