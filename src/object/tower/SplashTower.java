package object.tower;


import collision.ArcArea;
import collision.SplashBullet;
import object.Enemy;

import java.awt.*;

public class SplashTower extends NormalTower{
    public SplashTower(int xTile, int yTile){
        this.curXTile = xTile;
        this.curYTile = yTile;
        curXCoor = xTile*50+10;
        curYCoor = yTile*50+15;
        price = 55;
        attackSpeed = 2;
        attackDamage = 20;
        attackRadius = 150;
        attackArea = new ArcArea(attackRadius,this);
        lockOn = false;
        counter = attackSpeed*25;
    }

    public void damageEnemy(Enemy enemy) {
        enemy.getDamage(attackDamage);
    }

    public void shootEnemy(){
        if(counter == attackSpeed*25){
            if(target != null && lockOn) {
                counter = 0;
                bullet = new SplashBullet(20,this,target.getCurX()+target.getWidth()/2,target.getCurY()+target.getHeight()/2, allEnemy);
            }
        }else {
            if(counter < attackSpeed*25)
                counter++;
        }
    }


    public void paintMain(Graphics2D g){
        g.translate(curXCoor+15,curYCoor+10);
        g.rotate(degree*Math.PI/180);
        g.translate(-(curXCoor+15),-(curYCoor+10));

        g.setColor(Color.yellow);
        g.fillRect(curXCoor,curYCoor,30,20);
        g.setColor(Color.gray);
        g.fillRect(curXCoor+15-10,curYCoor-5,20,5);

        g.translate(curXCoor+15,curYCoor+10);
        g.rotate((360-degree)*Math.PI/180);
        g.translate(-(curXCoor+15),-(curYCoor+10));
    }
}
