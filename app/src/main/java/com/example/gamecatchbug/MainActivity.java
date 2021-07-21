package com.example.gamecatchbug;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBug;
    private TextView tvResult;
    private ConstraintLayout constraintLayout;
    private float x;
    private float y;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initViews();
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(100,100);
        ivBug.setLayoutParams(params);
        changeCoordinates();
        thread = new Thread(new ChangeCoordsThread());
        thread.start();
        constraintLayout.setOnClickListener(v -> {
            if (!thread.isAlive()){
                startNewThread();
            }else {
                tvResult.setText("Bug not fixed!");
            }
        });
        ivBug.setOnClickListener(this::bugFixed);
    }

    private void changeCoordinates(){
        x =  (float)(Math.random()*600);
        y =  (float)(Math.random()*700);
        ivBug.setX(x);
        ivBug.setY(y);
    }

    private void startNewThread(){
        thread = new Thread(new ChangeCoordsThread());
        thread.start();
        changeCoordinates();
        ivBug.setImageResource(R.drawable.bug);
        tvResult.setTextColor(Color.parseColor("#FF0000"));
        tvResult.setText(R.string.new_bug_found);
    }

    private void initViews(){
        ivBug = findViewById(R.id.ivBug);
        tvResult = findViewById(R.id.tvResult);
        constraintLayout = findViewById(R.id.constraintLayout);
    }

    private void bugFixed(View v) {
        tvResult.setTextColor(Color.parseColor("#FF673AB7"));
        tvResult.setText("Congratulation! Bug has been fixed! ");
        Toast toast = Toast.makeText(this, "tap on the box", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 600);
        toast.show();
        ivBug.setImageResource(R.drawable.bug_fixed);
        thread.interrupt();
    }

    class ChangeCoordsThread implements Runnable {

        @Override
        public void run() {

            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                changeCoordinates();
            }
        }
    }
}