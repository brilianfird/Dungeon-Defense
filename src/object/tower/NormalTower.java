package object.tower;

import collision.ArcArea;
import collision.Bullet;
import collision.PierceBullet;
import object.Enemy;

import java.awt.*;


public class NormalTower extends Tower {

    public NormalTower(){

    }
    public NormalTower(int xTile, int yTile){
        this.curXTile = xTile;
        this.curYTile = yTile;
        curXCoor = xTile*50+15;
        curYCoor = yTile*50+15;
        price = 50;
        attackSpeed = 1;
        attackDamage = 25;
        attackRadius = 200;
        attackArea = new ArcArea(attackRadius,this);
        lockOn = false;
        counter = attackSpeed*25;
    }

    public void calculateTargetDegree(){
        int x1 = curXTile * 50 + 25;
        int y1 = curYTile * 50 + 25;
        int targetDegree = (int)Math.toDegrees(Math.atan2(target.getCurX()-x1,y1-target.getCurY()));
        if(lockOn)
            degree = targetDegree;
        else{

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

    public void addTargetCandidate(Enemy e){
        if(target == null && e.isAlive()) {
            targetCandidate.add(e);
        }
    }

    double calculateEuclideanDistance(int x2, int y2){
        int x1 = curXTile * 50 + 25;
        int y1 = curYTile * 50 + 25;
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }

    public void selectTarget(){
        for(Enemy candidate : targetCandidate){
            if(target == null){
                target = candidate;
            }else{
                if(calculateEuclideanDistance(candidate.getCurX(),candidate.getCurY()) < calculateEuclideanDistance(target.getCurX(),target.getCurY())){
                    target = candidate;
                }
            }
        }
        targetCandidate.clear();
    }

    public void checkTarget(){
        if(target != null) {
            calculateTargetDegree();
            if (!inAttackRange(target) || !target.isAlive()) {
                target = null;
                lockOn = false;
            }
        }
    }
    public boolean inAttackRange(Enemy e){
        return attackArea.collideCheck(e.rect());
    }

    public void paintAttackRadius(Graphics2D g){
        attackArea.paintRadius(g);
    }

    public void paint(Graphics2D g){
        if(bullet != null)
            bullet.paintBullet(g);
        paintMain(g);

    }

    public void paintMain(Graphics2D g){
        g.translate(curXCoor+10,curYCoor+10);
        g.rotate(degree*Math.PI/180);
        g.translate(-(curXCoor+10),-(curYCoor+10));

        g.setColor(Color.yellow);
        g.fillRect(curXCoor,curYCoor,20,20);
        g.setColor(Color.red);
        g.fillRect(curXCoor+10-5,curYCoor-5,10,5);

        g.translate(curXCoor+10,curYCoor+10);
        g.rotate((360-degree)*Math.PI/180);
        g.translate(-(curXCoor+10),-(curYCoor+10));
    }

    public void shootEnemy(){
        if(counter == attackSpeed*25){
            if(target != null && lockOn) {
                counter = 0;
                bullet = new Bullet(10,this,target.getCurX()+target.getWidth()/2,target.getCurY()+target.getHeight()/2);
            }
        }else {
            if(counter < attackSpeed*25)
                counter++;
        }
    }
    public void damageEnemy() {
        if (target != null)
            target.getDamage(attackDamage);
        removeBullet();
    }

    public void removeBullet(){
        bullet = null;
    }
    public void removeBullet(PierceBullet bullet){}

}
