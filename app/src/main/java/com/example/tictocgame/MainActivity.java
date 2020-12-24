package com.example.tictocgame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    int mActivePlayer = 0;
    int[] mGameState = {2,2,2,2,2,2,2,2,2};
    int[][] mWinningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean mGameActive = true;

    TextView mWinnerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWinnerTextView = (TextView) findViewById(R.id.winnerTextView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(this, R.raw.music);
        }
        mMediaPlayer.start();
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (mGameState[tappedCounter] == 2 && mGameActive) {
            counter.setTranslationY(-1500);
            mGameState[tappedCounter] = mActivePlayer;
            if (mActivePlayer == 1) {
                counter.setImageResource(R.drawable.yellow);
                mActivePlayer = 0;
            } else {
                counter.setImageResource(R.drawable.red);
                mActivePlayer = 1;
            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(100);

            for (int[] winningPosition : mWinningPositions) {
                String winner = "";
                if (mGameState[winningPosition[0]] == mGameState[winningPosition[1]]
                        && mGameState[winningPosition[1]] == mGameState[winningPosition[2]]
                        && mGameState[winningPosition[0]] != 2) {

                    mGameActive = false;
                    if (mActivePlayer == 0) {
                        winner = "Yellow ";
                    } else  {
                        winner = "Red ";
                    }
                    mWinnerTextView.setText(winner + " has won!");

                    mWinnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {
        mWinnerTextView.setVisibility(View.INVISIBLE);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for (int i = 0; i< mGameState.length; i++) {
            mGameState[i] = 2;
        }
        mActivePlayer = 0;
        mGameActive = true;
    }

    public void exitGame(View view){
        mMediaPlayer.stop();
        finish();
    }
}