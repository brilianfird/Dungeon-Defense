package object.tower;


import collision.ArcArea;
import collision.Bullet;
import collision.PierceBullet;
import object.Enemy;

import java.awt.*;
import java.util.ArrayList;

public class Tower {
    protected int curXTile;
    protected int curYTile;
    protected int curXCoor;
    protected int curYCoor;
    protected int middleX, middleY;


    protected int attackRadius;
    protected int price;
    protected double attackSpeed;
    protected int attackDamage;
    protected ArcArea attackArea;
    protected int degree;
    protected ArrayList<Enemy> targetCandidate;
    protected ArrayList<Enemy> allEnemy;
    protected Enemy target;
    protected boolean lockOn;
    protected int rotateSpeed;
    protected Bullet bullet;
    protected double counter;

    public Tower() {
        targetCandidate = new ArrayList<Enemy>();
        rotateSpeed = 20;
    }

    public void setAllEnemy(ArrayList<Enemy> allEnemy) {
        this.allEnemy = allEnemy;
    }

    public Enemy getTarget() {
        return target;
    }

    public int getPrice() {
        return price;
    }

    public void upCounter() {
        counter++;
    }

    public int getCurXTile() {
        return curXTile;
    }

    public void setCurXTile(int curXTile) {
        this.curXTile = curXTile;
    }

    public int getCurYTile() {
        return curYTile;
    }

    public void setCurYTile(int curYTile) {
        this.curYTile = curYTile;
    }

    public int getDegree() {
        return degree;
    }

    public int getCurXCoor() {
        return curXCoor;
    }

    public void setCurXCoor(int curXCoor) {
        this.curXCoor = curXCoor;
    }

    public int getCurYCoor() {
        return curYCoor;
    }

    public void setCurYCoor(int curYCoor) {
        this.curYCoor = curYCoor;
    }

    public void paint(Graphics2D g) {

    }

    public boolean inAttackRange(Enemy e) {
        return true;
    }

    public void paintAttackRadius(Graphics2D g) {
    }

    public void addTargetCandidate(Enemy e) {
    }

    public void selectTarget() {
    }

    public void checkTarget() {
    }

    public void shootEnemy() {
    }

    public void damageEnemy() {
    }

    public void removeBullet() {
        bullet = null;
    }

    public void damageEnemy(Enemy enemy) {
    }

    public void paintMain(Graphics2D g) {
    }

    public void removeBullet(PierceBullet bullet) {
    }
}
