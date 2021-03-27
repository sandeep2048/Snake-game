package com.sanson.serpent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class SnakeView extends View {

    public SnakeView(Context context, AttributeSet attr)
    {
        super(context,attr);
    }

    private Paint mPaint=new Paint();
    private TileType snakeViewMap[][];

    public SnakeView(MainActivity mainActivity) {
        super(mainActivity);
    }


    public void setSnakeViewMap(TileType[][] map) {
        this.snakeViewMap = map;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

            if(snakeViewMap!=null)
            {
                float tilesizex=canvas.getWidth()/snakeViewMap.length;
                float tilesizey=canvas.getHeight()/snakeViewMap[0].length;

                float circlesize=Math.min(tilesizex,tilesizey)/2;

                for(int x=0;x< snakeViewMap.length;x++)
                {
                    for(int y=0;y< snakeViewMap[0].length;y++)
                    {
                        switch (snakeViewMap[x][y]){

                            case nothing:
                                mPaint.setColor(WHITE);break;

                            case snakehead:
                                mPaint.setColor(BLUE);break;

                            case snaketail:
                                mPaint.setColor(BLUE);break;

                            case wall:
                                mPaint.setColor(BLACK);break;
                            case apple:
                                mPaint.setColor(RED);break;

                        }
                        canvas.drawCircle(x*tilesizex+tilesizex/2f+circlesize/2,y*tilesizey+tilesizey/2f+circlesize/2,circlesize,mPaint);
                    }
                }
            }
        }

    }
