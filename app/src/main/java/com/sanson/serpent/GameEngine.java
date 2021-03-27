package com.sanson.serpent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {

    public static final int gw=28;
    public static final int gh=42;

    public GameEngine(){}

    private List<coordinate> walls=new ArrayList<>();
    private List<coordinate> snake=new ArrayList<>();
    private List<coordinate> apples=new ArrayList<>();
    private Random random=new Random();
    private boolean increasetail=false;
    private Directions currentDirection=Directions.right;
    private GameState currentGameState=GameState.running;
    public int c=0;

    private coordinate getSnakeHead()
    {
        return snake.get(0);
    }

    public void initGame()
    {
        addsnake();
        addwalls();
        addapples();
    }

    public void UpdateDirection(Directions newDirection)
    {
        if(Math.abs(newDirection.ordinal()-currentDirection.ordinal()) %2 ==1)
        {
            currentDirection=newDirection;
        }
    }

    public void Update()
    {
        //updating the snake status
        switch(currentDirection){
            case up:UpdateSnake(0,-1);break;
            case right:UpdateSnake(1,0);break;
            case left:UpdateSnake(-1,0);break;
            case down:UpdateSnake(0,1);break;}
            //checking for wall collisions
            for (int i = 0; i < walls.size(); i++) {
                coordinate w = walls.get(i);
                if (snake.get(0).equals(w)) {
                    currentGameState = GameState.lost;
                }
            }

            //check for self collisions
        for (int i = 1; i < snake.size(); i++) {
            if(getSnakeHead().equals(snake.get(i)))
            {
                currentGameState=GameState.lost;
                return;
            }
        }

        //remove apples
        coordinate appletoremove=null;
        for (coordinate apple:apples) {
            if(getSnakeHead().equals(apple))
            {
                appletoremove=apple;
                increasetail=true;
                c++;
            }
        }
        if(appletoremove!=null)
        {
            apples.remove(appletoremove);
            addapples();
        }
        }


    public TileType[][] getMap(){

        TileType[][] map=new TileType[gw][gh];

        for (int x=0;x<gw;x++){
            for(int y=0;y<gh;y++){
                map[x][y]=TileType.nothing;
            }
        }

        for(coordinate s:snake)
        {
            map[s.getX()][s.getY()]=TileType.snaketail;
        }
        map[snake.get(0).getX()][snake.get(0).getY()]=TileType.snakehead;

        for(coordinate wall:walls)
        {
            map[wall.getX()][wall.getY()]=TileType.wall;
        }

        for(coordinate a:apples)
        {
            map[a.getX()][a.getY()]=TileType.apple;
        }
        return map;}

        private void UpdateSnake(int x,int y)
        {
            int newX=snake.get(snake.size()-1).getX();
            int newY=snake.get(snake.size()-1).getY();
            for(int i=snake.size()-1;i>0;i--)
            {
                snake.get(i).setX(snake.get(i-1).getX());
                snake.get(i).setY(snake.get(i-1).getY());
            }

            //increase tail length

            if(increasetail)
            {
                snake.add(new coordinate(newX,newY));
                increasetail=false;
            }

            snake.get(0).setX(snake.get(0).getX()+x);
            snake.get(0).setY(snake.get(0).getY()+y);
        }

        private void addsnake()
        {
            snake.clear();
            snake.add(new coordinate(7,7));
            snake.add(new coordinate(6,7));
            snake.add(new coordinate(5,7));
            snake.add(new coordinate(4,7));
            snake.add(new coordinate(3,7));
            snake.add(new coordinate(2,7));
        }

    private void addwalls() {
        for(int x=0;x<gw;x++)
        {
            walls.add(new coordinate(x,0));
            walls.add(new coordinate(x,gh-1));
        }

        for(int y=1;y<gh;y++)
        {
            walls.add(new coordinate(0,y));
            walls.add(new coordinate(gw-1,y));
        }
    }

    private void addapples()
    {
        coordinate coordinate_ob=null;
        boolean added=false;

        while (!added)
        {
            int x=1+random.nextInt(gw-2);
            int y=1+random.nextInt(gh-2);

            coordinate_ob=new coordinate( x,y );
            boolean collision = false;
            for(coordinate s : snake)
            {
                if(s.equals(coordinate_ob))
                {
                    collision=true;
                    break;
                }
            }
            if(collision)
            {
                continue;
            }

            for (coordinate a:apples)
            {
                if(a.equals(coordinate_ob))
                {
                    collision=true;
                    break;
                }
            }

            added=!collision;
        }

        apples.add(coordinate_ob);
    }

    public GameState getCurrentGameState()
    {
        return currentGameState;
    }

}
