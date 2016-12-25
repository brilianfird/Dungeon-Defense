package object;


import frame.GameInfo;
import javafx.scene.shape.Arc;
import pathfinding.astar.Astar;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;


public class Enemy {
    private int speed;
    private int hp;
    private int curHp;
    private String type;
    private int width;
    private int height;
    private Color color;
    private int baseCamp;
    private char[][] map;
    private boolean alive;
    private int bounty;
    //kordinat
    private int curX;
    private int curY;
    private int targetX;
    private int targetY;

    //map
    private int nextX;
    private int nextY;
    private int curXMap;
    private int curYMap;
    private GameInfo gameInfo;

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    private Rectangle rectangle;
    public int getCurXMap() {
        return curXMap;
    }

    public int getCurYMap() {
        return curYMap;
    }

    Astar astar;

    public void paint(Graphics2D g)
    {
        rectangle = new Rectangle(curX,curY,width,height);

        g.setColor(Color.black);
        g.drawRect(curX+width/2-10,curY-10,20,5);
        g.setColor(Color.red);
        g.fillRect(curX+width/2-10+1,curY-10+1,curHp*19/hp,5-1);
        g.setColor(Color.black);
        g.fillArc(curX, curY, width, height, 0, 360);
    }

    public void paintCollisionArea(Graphics2D g){
        g.setColor(Color.red);
        g.drawRect(curX,curY,width,height);
    }
    public void getDamage(int damage){
        curHp -= damage;
        if(curHp < 1 && alive == true){
            try {
                alive = false;
                gameInfo.addMoney(bounty);
            }catch(Exception e){
            }
        }
    }

    public void printMap(){
        for(int y = 0; y < 10; y++){
            for(int x = 0; x < 10; x++){
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }
    public void calcNewPoint(){
        astar = new Astar(nextX,nextY,9,baseCamp,map);
        astar.doAstar();
        curXMap = nextX;
        curYMap = nextY;
        nextX = astar.getCalculatedX();
        nextY = astar.getCalculatedY();
    }

    public void calcNewCoordinate(){
        if(alive) {
            targetX = nextX * 50 + 25 - height / 2;
            targetY = nextY * 50 + 25 - height / 2;
            if (targetX < curX) {
                if (curX - targetX < speed / 25)
                    curX = targetX;
                else
                    curX -= speed / 25;
            } else if (targetY < curY) {
                if (curY - targetY < speed / 25)
                    curY = targetY;
                else
                    curY -= speed / 25;
            } else if (targetX > curX) {
                if (targetX - curX < speed / 25)
                    curX = targetX;
                else
                    curX += speed / 25;
            } else if (targetY > curY) {
                if (targetY - curY < speed / 25)
                    curY = targetY;
                else
                    curY += speed / 25;
            }
            if (targetX == curX && targetY == curY) {
                calcNewPoint();
            }
        }

    }

    public int getCurX() {
        return curX;
    }

    public void setCurX(int curX) {
        this.curX = curX;
    }

    public int getCurY() {
        return curY;
    }

    public void setCurY(int curY) {
        this.curY = curY;
    }

    public int getNextX() {
        return nextX;
    }

    public void setNextX(int nextX) {
        this.nextX = nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public void setNextY(int nextY) {
        this.nextY = nextY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Enemy(int wave, int baseCamp, char[][] map, int monsterLair) {
        alive = true;

        curXMap = 0;
        curYMap = monsterLair;
        this.map = new char[10][10];
        for(int y = 0; y < 10; y++){
            for(int x = 0;x < 10;x++) {
                this.map[y][x] = map[y][x];
            }
        }
        this.baseCamp = baseCamp;
        if(wave % 3 == 0 && wave % 5 == 0)
            type = "Fast";
        else if(wave % 3 == 0)
            type = "Fast";
        else if(wave % 5 == 0)
            type = "Huge";
        else
            type="Normal";

        color = Color.black;

        if(type.equals("Normal")){
            speed = 48 + wave * 3;
            hp = 100 + wave * 3;
            width = 25;
            height = 25;

        }else if(type.equals("Fast")){
            speed = 64 + wave * 4;
            hp = 75 + wave * 2;
            width = 15;
            height = 15;
        }else if(type.equals("Huge")){
            speed = 32 + wave * 2;
            hp = 125 + wave * 4;
            width = 35;
            height = 35;
        }
        bounty = wave + 1;
        curHp = hp;
    }

    public Area getBounds() {
        double dCurX = (double)curX;
        double dCurY = (double)curY;
        double dWidth = (double)width;
        double dHeight = (double)height;
        Shape tempShape = new Arc2D.Double(dCurX,dCurY,dWidth,dHeight,0.0,360.0,1);
        return new Area(tempShape);
    }

    public Rectangle rect(){
        return new Rectangle(curX,curY,width,height);
    }

    //collision test
    public boolean collision(Enemy enemy) {
        Area area = new Area();
        area = getBounds();
        area.intersect(enemy.getBounds());
        if(!area.isEmpty()) return true;
        else return false;
    }
}
