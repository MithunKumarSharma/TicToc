package com.example.tictocgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameActive) {
            counter.setTranslationY(-1500);
            gameState[tappedCounter] = activePlayer;
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 0;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(100);

            for (int[] winningPosition : winningPositions) {
                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                String winner = "";
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {

                    gameActive = false;
                    if (activePlayer == 0) {
                        winner = "Yellow ";
                    } else  {
                        winner = "Red ";
                    }

                    winnerTextView.setText(winner + " has won!");

                    winnerTextView.setVisibility(View.VISIBLE);

                }
            }
        }
    }

    public void playAgain(View view) {

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);

        }

        for (int i=0; i<gameState.length; i++) {

            gameState[i] = 2;

        }

        activePlayer = 0;

        gameActive = true;

    }

    public void exitGame(View view){
        finish();
    }
}