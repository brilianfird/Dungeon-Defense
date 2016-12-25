package object.tower;


import collision.ArcArea;
import collision.Bullet;
import collision.PierceBullet;
import collision.SplashBullet;
import object.Enemy;

import java.awt.*;
import java.util.ArrayList;

public class PierceTower extends NormalTower {
    ArrayList<Bullet> bulletList;
    public PierceTower(int xTile, int yTile){
        this.curXTile = xTile;
        this.curYTile = yTile;
        curXCoor = xTile*50+15;
        curYCoor = yTile*50+10;
        price = 55;
        attackSpeed = 0.5;
        attackDamage = 15;
        attackRadius = 100;
        attackArea = new ArcArea(attackRadius,this);
        lockOn = false;
        counter = attackSpeed*25;
        bulletList = new ArrayList<Bullet>();
    }

    public boolean checkDegree(){
        if(degree == 0 || Math.abs(degree) == 90 || Math.abs(degree) == 180 || Math.abs(degree) == 270) return true;
        else return false;
    }

    public void shootEnemy(){
        if(counter >= attackSpeed*25){
            if(target != null && lockOn &&(checkDegree())) {
                counter = 0;
                bullet = new PierceBullet(10,this,target.getCurX()+target.getWidth()/2,target.getCurY()+target.getHeight()/2, allEnemy);
                bulletList.add(bullet);
                bullet = null;
            }
        }else {
            if(counter < attackSpeed*25)
                counter++;
        }
    }
    public void damageEnemy(Enemy enemy) {
        enemy.getDamage(attackDamage);
    }

    public void paint(Graphics2D g){
        try {
            for (Bullet aBullet : bulletList) {
                aBullet.paintBullet(g);
            }
        } catch (Exception e) {}
        paintMain(g);
    }

    public void removeBullet(PierceBullet bullet){
        bulletList.remove(bullet);
    }

    public void paintMain(Graphics2D g){
        g.translate(curXCoor+10,curYCoor+15);
        g.rotate(degree*Math.PI/180);
        g.translate(-(curXCoor+10),-(curYCoor+15));

        g.setColor(Color.yellow);
        g.fillRect(curXCoor,curYCoor,20,30);
        g.setColor(Color.cyan);
        g.fillRect(curXCoor+10-5,curYCoor-5,10,5);

        g.translate(curXCoor+10,curYCoor+15);
        g.rotate((360-degree)*Math.PI/180);
        g.translate(-(curXCoor+10),-(curYCoor+15));
    }

    public int calculateNearestDegree(int targetDegree){
        int r1,r2,r3,r4;
        r1 = Math.abs(targetDegree) - 0;
        r2 = Math.abs(targetDegree) - 90;
        r3 = Math.abs(targetDegree) - 180;
        r4 = Math.abs(targetDegree) - 270;

        r1 = Math.abs(r1);
        r2 = Math.abs(r2);
        r3 = Math.abs(r3);
        r4 = Math.abs(r4);
        if(targetDegree > 0) {
            if (r1 <= r2) {
                if (r1 <= r3) {
                    if (r1 <= r4) return 0;
                    else return 270;
                } else return 180;
            } else {
                if (r2 <= r3) {
                    if (r2 <= r4) return 90;
                    else return 270;
                } else return 180;
            }
        }
        else {
            if (r1 <= r2) {
                if (r1 <= r3) {
                    if (r1 <= r4) return 0;
                    else return -270;
                } else return -180;
            } else {
                if (r2 <= r3) {
                    if (r2 <= r4) return -90;
                    else return -270;
                } else return -180;
            }
        }
    }

    public void calculateTargetDegree(){
        int x1 = curXTile * 50 + 25;
        int y1 = curYTile * 50 + 25;
        int targetDegree = (int)Math.toDegrees(Math.atan2(target.getCurX()-x1,y1-target.getCurY()));
        targetDegree = calculateNearestDegree(targetDegree);
        if(targetDegree > degree){
            if(targetDegree - degree < rotateSpeed) degree = targetDegree;
            else degree += rotateSpeed;
        }else if(targetDegree < degree){
            if(degree - targetDegree < rotateSpeed) degree = targetDegree;
            else degree -= rotateSpeed;
        }
        if(degree == targetDegree) lockOn = true;
    }
}
