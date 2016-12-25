package collision;

import object.Enemy;
import object.tower.SplashTower;
import object.tower.Tower;

import java.awt.*;
import java.util.ArrayList;


public class SplashBullet extends Bullet {
    ArrayList<Enemy> enemy;
    public SplashBullet(int radius, Tower tower, int targetX, int targetY, ArrayList<Enemy> enemy) {
        super(radius, tower, targetX, targetY);
        this.enemy = enemy;
        bulletSpeed = 20;
    }

    @Override
    public void bulletHit() {
        for (Enemy anEnemy : enemy){
            collRect = new Rectangle((int)midXCoor-150/2,(int)midYCoor-150/2,150,150);
            if(collRect.intersects(anEnemy.rect()))
                tower.damageEnemy(anEnemy);
        }
        tower.removeBullet();
    }
}
