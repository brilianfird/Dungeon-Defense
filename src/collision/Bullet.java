package collision;

import object.tower.Tower;

import java.awt.*;

/**
 * Created by Brilian on 24/12/2016.
 */
public class Bullet extends ArcArea{
    int targetX;
    int targetY;
    int bulletSpeed;
    double xSpeed;
    double ySpeed;
    int speed;
    Rectangle collRect;

    public Bullet(int radius, Tower tower, int targetX, int targetY) {
        super(radius, tower);

        this.targetX = targetX;
        this.targetY = targetY;
        collRect = new Rectangle((int)midXCoor-radius/2,(int)midYCoor-(int)radius/2,radius,radius);
        bulletSpeed = 23;
        speed = (int)(Math.abs(targetX - midXCoor) + Math.abs(targetY - midYCoor))/2/(25-bulletSpeed);
        xSpeed =  speed * Math.abs(targetX - midXCoor) / (Math.abs(targetX - midXCoor) + Math.abs(targetY - midYCoor));
        ySpeed =  speed * Math.abs(targetY - midYCoor) / (Math.abs(targetX - midXCoor) + Math.abs(targetY - midYCoor));
    }

    public void paintBullet(Graphics2D g){
        g.setColor(Color.red);
        g.fillArc((int)midXCoor-radius/2,(int)midYCoor-radius/2,radius,radius,0,360);
        if(targetX > midXCoor){
            if(targetX - midXCoor < bulletSpeed) midXCoor = targetX;
            else midXCoor += xSpeed;
        }else if(targetX < midXCoor){
            if(midXCoor - targetX < bulletSpeed) midXCoor = targetX;
            else midXCoor -= xSpeed;
        }

        if(targetY > midYCoor){
            if(targetY - midYCoor < bulletSpeed) midYCoor = targetY;
            else midYCoor += ySpeed;
        }else if(targetY < midYCoor){
            if(midYCoor - targetY < bulletSpeed) midYCoor = targetY;
            else midYCoor -= ySpeed;
        }

        collRect = new Rectangle((int)midXCoor-radius/2,(int)midYCoor-(int)radius/2,radius,radius);

        if(tower.getTarget() != null) {
            if (collRect.intersects(tower.getTarget().rect()) || (midXCoor == targetX && midYCoor == targetY)) {
                bulletHit();
            }
        }else
            bulletHit();
    }

    public void bulletHit(){
        tower.damageEnemy();
    }
}
