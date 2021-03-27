package com.sanson.serpent;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler=new Handler();
    private final long updateDelay=125;
    private float prevX,prevY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEngine=new GameEngine();
        gameEngine.initGame();

        snakeView=(SnakeView)findViewById(R.id.snakeview);
        snakeView.setOnTouchListener(this);
        startUpdateHandler();

        //snakeView.setSnakeViewMap(gameEngine.getMap());
        //snakeView.invalidate();
    }

    private void startUpdateHandler()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();

                if(gameEngine.getCurrentGameState()==GameState.running)
                {
                    handler.postDelayed(this,updateDelay);
                }
                if(gameEngine.getCurrentGameState()==GameState.lost)
                {
                    onGameLost();
                }
                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();
            }
        },updateDelay);
    }

    private void onGameLost()
    {
        Toast.makeText(this,"You Lost. Your score was "+gameEngine.c,Toast.LENGTH_SHORT).show();
        gameEngine=new GameEngine();
        gameEngine.initGame();

        snakeView=(SnakeView)findViewById(R.id.snakeview);
        snakeView.setOnTouchListener(this);
        startUpdateHandler();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                prevX=event.getX();
                prevY=event.getY();
            break;

            case MotionEvent.ACTION_UP:
                float newX=event.getX();
                float newY=event.getY();

                if(Math.abs(newX-prevX)>Math.abs(newY-prevY))
                {
                    if(newX>prevX)
                    {
                        gameEngine.UpdateDirection(Directions.right);
                    }
                    else
                    {
                        gameEngine.UpdateDirection(Directions.left);
                    }
                }

                else
                {
                    if(newY>prevY)
                    {
                        gameEngine.UpdateDirection(Directions.down);
                    }
                    else
                    {
                        gameEngine.UpdateDirection(Directions.up);
                    }
                }
                break;
        }

        return true;
    }
}