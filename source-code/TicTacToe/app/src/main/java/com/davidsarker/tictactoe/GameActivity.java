package com.davidsarker.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView mBox1, mBox2, mBox3, mBox4, mBox5, mBox6, mBox7, mBox8, mBox9;
    private TextView mGameMessage;
    private Boolean mPlayerOneTurn = true;
    private String mPlayerOneName, mPlayerTwoName;
    private TextView[][] mWinningCombinations;
    private int mTurnCounter;
    private ImageButton mResetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mPlayerOneName = getIntent().getStringExtra("PLAYERONE");
        mPlayerTwoName = getIntent().getStringExtra("PLAYERTWO");
        mTurnCounter = 0;

        mGameMessage = (TextView) findViewById(R.id.game_message_text);
        mGameMessage.setText(mPlayerOneName + "'s Turn");

        mBox1 = (TextView) findViewById(R.id.textView1);
        mBox2 = (TextView) findViewById(R.id.textView2);
        mBox3 = (TextView) findViewById(R.id.textView3);
        mBox4 = (TextView) findViewById(R.id.textView4);
        mBox5 = (TextView) findViewById(R.id.textView5);
        mBox6 = (TextView) findViewById(R.id.textView6);
        mBox7 = (TextView) findViewById(R.id.textView7);
        mBox8 = (TextView) findViewById(R.id.textView8);
        mBox9 = (TextView) findViewById(R.id.textView9);

        mWinningCombinations = new TextView[][]{
                {mBox1, mBox2, mBox3},
                {mBox1, mBox5, mBox9},
                {mBox1, mBox4, mBox7},
                {mBox2, mBox5, mBox8},
                {mBox3, mBox6, mBox9},
                {mBox3, mBox5, mBox7},
                {mBox4, mBox5, mBox6},
                {mBox7, mBox8, mBox9}};

        mBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });
        mBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });

        mBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });

        mBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });

        mBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });

        mBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });
        mBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });
        mBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });
        mBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox((TextView) v);
            }
        });

        mResetButton = (ImageButton) findViewById(R.id.resetButton);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    public void switchName() {
        if (mPlayerOneTurn) {
            mGameMessage.setText(mPlayerTwoName + "'s Turn");
        } else {
            mGameMessage.setText(mPlayerOneName + "'s Turn");
        }
    }

    public void markBox(TextView view) {
        if (mPlayerOneTurn) {
            view.setText("X");
        } else {
            view.setText("O");
        }

        view.setEnabled(false);
        mTurnCounter++;

        switchName();
        checkWinner();
        mPlayerOneTurn = !mPlayerOneTurn;

        if (mTurnCounter == 9) {
            disableGame();
        }

    }

    public void checkWinner() {
        for (int i = 0; i < 8; i++) {
            String symbol = mWinningCombinations[i][0].getText().toString();
            if ((symbol.equals("X") || symbol.equals("O")) &&
                    (symbol.equals(mWinningCombinations[i][1].getText().toString()) &&
                            symbol.equals(mWinningCombinations[i][2].getText().toString()))) {
                SharedPreferences sharedPreferences = getSharedPreferences("com.davidsarker.tictactoe", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String previousWinner = sharedPreferences.getString("WINNER", "None");

                if (mPlayerOneTurn) {
                    mGameMessage.setText(mPlayerOneName + " wins!");
                    editor.putString("WINNER", mPlayerOneName);

                    if (previousWinner.equals(mPlayerOneName)) {
                        editor.putInt("STREAK", sharedPreferences.getInt("STREAK", 0) + 1);
                    } else {
                        editor.putInt("STREAK", 1);
                    }
                } else {
                    mGameMessage.setText(mPlayerTwoName + " wins!");
                    editor.putString("WINNER", mPlayerTwoName);

                    if (previousWinner.equals(mPlayerTwoName)) {
                        editor.putInt("STREAK", sharedPreferences.getInt("STREAK", 0) + 1);
                    } else {
                        editor.putInt("STREAK", 1);
                    }

                }

                disableGame();
                editor.commit();
                break;
            }
        }
    }

    public void disableGame() {
        if (mTurnCounter == 9) {
            mGameMessage.setText("Tie!");
        }

        mBox1.setEnabled(false);
        mBox2.setEnabled(false);
        mBox3.setEnabled(false);
        mBox4.setEnabled(false);
        mBox5.setEnabled(false);
        mBox6.setEnabled(false);
        mBox7.setEnabled(false);
        mBox8.setEnabled(false);
        mBox9.setEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void resetGame() {
        mTurnCounter = 0;
        mBox1.setEnabled(true);
        mBox2.setEnabled(true);
        mBox3.setEnabled(true);
        mBox4.setEnabled(true);
        mBox5.setEnabled(true);
        mBox6.setEnabled(true);
        mBox7.setEnabled(true);
        mBox8.setEnabled(true);
        mBox9.setEnabled(true);

        mBox1.setText("");
        mBox2.setText("");
        mBox3.setText("");
        mBox4.setText("");
        mBox5.setText("");
        mBox6.setText("");
        mBox7.setText("");
        mBox8.setText("");
        mBox9.setText("");

        if (mPlayerOneTurn) {
            mGameMessage.setText(mPlayerOneName + "'s Turn");
        } else {
            mGameMessage.setText(mPlayerTwoName + "'s Turn");
        }
    }
}
