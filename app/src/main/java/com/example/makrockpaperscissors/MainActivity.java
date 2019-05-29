package com.example.makrockpaperscissors;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgView;
    TextView text_result, score_opponent, score_player;
    int myImages[] = new int[]{R.mipmap.c_rock_round, R.mipmap.c_paper_round, R.mipmap.c_scissors_round};
    int score_pl= 0, score_op= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        score_player = findViewById(R.id.score_player);
        score_opponent = findViewById(R.id.score_opponent);
        imgView = findViewById(R.id.c_turn);
        text_result = findViewById(R.id.text_result);
        imgView.setBackgroundResource(R.mipmap.c_questionmark_round);
    }
    public void onClick(View v) {
        int id = v.getId();
        //User choosing rock
        if (id == R.id.btn1)
        {   setOutput(0);   }
        //User choosing paper
        else if (id == R.id.btn2)
        {   setOutput(1);   }
        //User choosing scissors
        else if (id == R.id.btn3)
        {   setOutput(2);   }
    }
    public void setOutput(int user_input)
    {
        int img_num = (int) (Math.random()*3);
        imgView.setBackgroundResource(myImages[img_num]);
        checkResult(user_input, img_num);
    }
    private void checkResult(int user_input, int img_num)
    {
        try {
            /** Tie Situation **/
            if ((user_input == 0 && img_num == 0) || (user_input == 1 && img_num == 1) || (user_input == 2 && img_num == 2)) {
                text_result.setText("TIE");
            }
            /** Rock - Paper **/
            else if (user_input == 0 && img_num == 1) {     score_op++;
                result("Opponent", score_pl, score_op, "Paper > Rock");
            }
            /** Paper - Rock **/
            else if (user_input == 1 && img_num == 0) {     score_pl++;
                result("User", score_pl, score_op, "Paper > Rock");
            }

            /** Rock - Scissors **/
            else if (user_input == 0 && img_num == 2) {     score_pl++;
                result("User", score_pl, score_op, "Rock > Scissors");
            }
            /** Scissors - Rock **/
            else if (user_input == 2 && img_num == 0) {     score_op++;
                result("Opponent", score_pl, score_op, "Rock > Scissors");
            }

            /** Paper - Scissors **/
            else if (user_input == 1 && img_num == 2) {     score_op++;
                result("Opponent", score_pl, score_op, "Scissors > Paper");
            }
            /** Scissors - Paper**/
            else if (user_input == 2 && img_num == 1) {     score_pl++;
                result("User", score_pl, score_op, "Scissors > Paper");
            }
        }
        catch (Exception e)
        {   text_result.setText(e.toString());  }
    }
    private void result(String userType, int score_pl, int score_op, String winningSituation)
    {
        if (score_pl < 10 && score_op < 10) {
            text_result.setText(userType + " Win by " + winningSituation);
            if (userType == "User")
            {   score_player.setText("" + score_pl);    }
            else if (userType == "Opponent")
            {   score_opponent.setText("" + score_op);  }
        }
        else {
            score_player.setText("" + score_pl);
            score_opponent.setText("" + score_op);
            if (score_pl > score_op) {
                /*** User win **/
                dialog_restart("User Wins", 10, score_op);
            } else if (score_pl < score_op) {
                /*** Opponenet wins **/
                dialog_restart("Opponent Wins", score_pl, 10);
            } else if (score_pl == score_op) {
                /** It's a Tie **/
                dialog_draw("Draw");
            }
        }
    }
    private void dialog_restart(String winner, int pl, int op)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(winner)
                .setMessage("You: "+pl+"\nOpponent: "+op)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).setCancelable(false)
                .show();
    }
    private void dialog_draw(String draw)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(draw)
                .setMessage("Restart game?")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        }).setCancelable(false)
                .show();
    }
    public void activity_restart(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("RESTART?")
                .setMessage("Restart game?")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /** Back to activity **/
            }
        }).setCancelable(false)
                .show();
    }
    public void activity_quit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("QUIT?")
                .setMessage("Quit game?")
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /** Back to Activity **/
            }
        }).setCancelable(false)
                .show();
    }
}