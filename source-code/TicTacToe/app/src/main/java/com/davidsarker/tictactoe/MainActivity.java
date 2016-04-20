package com.davidsarker.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mPlayerOneEditText;
    private EditText mPlayerTwoEditText;
    private Button mPlayButton;
    private TextView mPreviousWinnerTextBox;
    private TextView mCurrentStreak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayerOneEditText = (EditText) findViewById(R.id.player_one_name);
        mPlayerTwoEditText = (EditText) findViewById(R.id.player_two_name);
        mPlayButton = (Button) findViewById(R.id.start_game_button);
        mPreviousWinnerTextBox = (TextView) findViewById(R.id.last_winner_text);
        mCurrentStreak = (TextView) findViewById(R.id.current_streak_text);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerOneEditText.getText().toString().trim().equals("")
                        && mPlayerTwoEditText.getText().toString().trim().equals("")) {
                    mPlayerOneEditText.setError("Please enter your name.");
                    mPlayerTwoEditText.setError("Please enter your name.");
                } else if (mPlayerOneEditText.getText().toString().trim().equals("")) {
                    mPlayerOneEditText.setError("Please enter your name.");
                } else if (mPlayerTwoEditText.getText().toString().trim().equals("")) {
                    mPlayerTwoEditText.setError("Please enter your name.");
                } else {
                    Intent i = new Intent(MainActivity.this, GameActivity.class);
                    i.putExtra("PLAYERONE", mPlayerOneEditText.getText().toString());
                    i.putExtra("PLAYERTWO", mPlayerTwoEditText.getText().toString());
                    startActivityForResult(i, 0);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("com.davidsarker.tictactoe", Context.MODE_PRIVATE);
        String previousWinner = sharedPreferences.getString("WINNER", "None");
        int currentStreak = sharedPreferences.getInt("STREAK", 0);
        mPreviousWinnerTextBox.setText("Previous Winner: " + previousWinner);
        mCurrentStreak.setText("Current Streak: " + currentStreak);
    }
}