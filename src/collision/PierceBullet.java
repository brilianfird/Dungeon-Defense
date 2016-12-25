package collision;


import object.Enemy;
import object.tower.NormalTower;
import object.tower.Tower;

import java.awt.*;
import java.util.ArrayList;

public class PierceBullet extends Bullet{
    ArrayList<Enemy> enemy;
    ArrayList<Enemy> hitEnemy;
    int counter = 0;
    int degree = 0;
    public PierceBullet(int radius, Tower tower, int targetX, int targetY, ArrayList<Enemy> enemy) {
        super(radius, tower, targetX, targetY);
        this.enemy = enemy;
        bulletSpeed = 10;
        hitEnemy = new ArrayList<Enemy>();
        degree = tower.getDegree();
    }

    @Override
    public void bulletHit() {
        for (Enemy anEnemy : enemy){
            int flag = 1;
            collRect = new Rectangle((int)midXCoor-radius/2,(int)midYCoor-radius/2,radius,radius);
            if(collRect.intersects(anEnemy.rect())) {
                if(hitEnemy.isEmpty()){
                    hitEnemy.add(anEnemy);
                    tower.damageEnemy(anEnemy);
                }
                else {
                    for (Enemy aHitEnemy : hitEnemy) {
                        if(aHitEnemy.equals(anEnemy)) flag = 0;
                    }
                    if(flag == 1){
                        tower.damageEnemy(anEnemy);
                        hitEnemy.add(anEnemy);
                    }
                }
            }
        }
    }

    public void paintBullet(Graphics2D g){
        g.setColor(Color.red);
        g.fillArc((int)midXCoor-radius/2,(int)midYCoor-radius/2,radius,radius,0,360);

        collRect = new Rectangle((int)midXCoor-radius/2,(int)midYCoor-(int)radius/2,radius,radius);

        bulletHit();
        if(counter == 25) {
            tower.removeBullet(this);
        }
        counter++;
        if(degree == 0) midYCoor -= bulletSpeed;
        else if(degree == 90 || degree == -270) midXCoor += bulletSpeed;
        else if(degree == 180 || tower.getCurXTile() == -180) midYCoor += bulletSpeed;
        else if(degree == 270 || degree == -90) midXCoor -= bulletSpeed;
    }
}
