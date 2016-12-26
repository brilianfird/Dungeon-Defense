package frame;


import object.Enemy;
import object.Stone;
import object.tower.NormalTower;
import object.tower.PierceTower;
import object.tower.SplashTower;
import object.tower.Tower;
import pathfinding.floodfill.FloodFill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener {
    char[][] map;
    int monsterLair;
    int baseCamp;
    int hoverX;
    int hoverY;
    boolean build;
    FieldInfo fieldInfo;
    GameInfo gameInfo;
    Stone stone;
    FloodFill floodFill;
    ArrayList<Enemy> enemy;
    ArrayList<Tower> tower;
    int curX;
    int curY;

    GamePanel() {
        hoverX = -1;
        hoverY = -1;
        curY = -1;
        curX = -1;
        enemy = new ArrayList<Enemy>();
        tower = new ArrayList<Tower>();
        build = true;
        monsterLair = (int) (Math.random() * 1342 % 10);
        baseCamp = (int) (Math.random() * 1234 % 10);

        setPreferredSize(new Dimension(501, 501));
        setLayout(null);
        map = new char[10][10];
        for (int y = 0; y < 10; y++)
            for (int x = 0; x < 10; x++)
                if (y == monsterLair && x == 0)
                    map[y][x] = 'M';
                else if (y == baseCamp && x == 9)
                    map[y][x] = 'B';
                else
                    map[y][x] = ' ';


        addMouseMotionListener(this);
        addMouseListener(this);
        stone = new Stone();
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public void setFieldInfo(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public void addTower(String type) {
        if (type.equals("Normal")) {
            if (gameInfo.buy(50)) {
                tower.add(new NormalTower(curX, curY));
                map[curY][curX] = 'N';
            } else {
                gameInfo.setNotification("Not enough gold");
            }
            fieldInfo.show(map[curY][curX]);
        } else if (type.equals("Splash")) {
            if (gameInfo.buy(55)) {
                tower.add(new SplashTower(curX, curY));
                map[curY][curX] = 'O';
            } else {
                gameInfo.setNotification("Not enough gold");
            }
            fieldInfo.show(map[curY][curX]);

        } else if (type.equals("Pierce")) {
            if (gameInfo.buy(55)) {
                tower.add(new PierceTower(curX, curY));
                map[curY][curX] = 'P';
            } else {
                gameInfo.setNotification("Not enough gold");
            }
            fieldInfo.show(map[curY][curX]);

        }
    }

    public void resetActive() {
        if (curX != -1 && curY != -1 && map[curY][curX] == ' ') {
            map[curY][curX] = 'A';
            fieldInfo.show('A');
        }
    }

    public void buildStone() {
        floodFill = new FloodFill(map);
        if (floodFill.doFloodFill()) {
            if (gameInfo.buy(stone.getPrice())) {
                map[curY][curX] = stone.getSymbol();
                fieldInfo.hideAll();
            } else {
                gameInfo.setNotification("Not enough gold");
            }
            fieldInfo.show(map[curY][curX]);
        } else {
            gameInfo.setNotification("Bad Place");
        }

    }

    public void spawnEnemy() {
        int wave = gameInfo.getWave();
        enemy.add(new Enemy(wave, baseCamp, map, monsterLair));
        enemy.get(enemy.size() - 1).setCurX((25 - enemy.get(0).getHeight() / 2));
        enemy.get(enemy.size() - 1).setCurY(monsterLair * 50 + (25 - enemy.get(0).getHeight() / 2));
        enemy.get(enemy.size() - 1).setNextX(0);
        enemy.get(enemy.size() - 1).setNextY(monsterLair);
        enemy.get(enemy.size() - 1).setGameInfo(gameInfo);
    }

    public void printMap() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

    public void removeActive() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (map[y][x] == 'A') map[y][x] = ' ';
            }
        }
        curY = -1;
        curX = -1;
    }

    public boolean removeActivePanel() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (map[y][x] == 'A') {
                    map[y][x] = ' ';
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                g2d.setColor(Color.black);
                g2d.drawRect(x * 50, y * 50, 50, 50);

                //Empty
                if (map[y][x] == ' ')
                    g2d.setColor(new Color(144, 238, 144));
                    //Hover
                else if (map[y][x] == 'H')
                    g2d.setColor(Color.GREEN);
                    //Monster Lair
                else if (map[y][x] == 'M')
                    g2d.setColor(Color.PINK);
                    //Basecamp
                else if (map[y][x] == 'B')
                    g2d.setColor(Color.RED);
                    //Active
                else if (map[y][x] == 'A')
                    g2d.setColor(Color.yellow);
                    //Stone
                else if (map[y][x] == stone.getSymbol() || map[y][x] == 'N' || map[y][x] == 'O' || map[y][x] == 'P')
                    g2d.setColor(Color.darkGray);
                g2d.fillRect(x * 50 + 1, y * 50 + 1, 49, 49);
            }
        }
        if (curY != -1 && curX != -1) {
            g2d.setColor(Color.red);
            g2d.drawRect(curX * 50, curY * 50, 50, 50);
        }

        for (Enemy anEnemy : enemy) {
            if (anEnemy.getCurXMap() == 9 && anEnemy.getCurYMap() == baseCamp && anEnemy.isAlive()) {
                anEnemy.setAlive(false);
                gameInfo.damaged();
            }
            if (anEnemy.isAlive()) {
                g2d.setColor(Color.black);
                anEnemy.paint(g2d);
                anEnemy.calcNewCoordinate();
            }
        }

        for (Tower aTower : tower) {
            aTower.paint(g2d);

            //paintattackradius
            if (aTower.getCurXTile() == hoverX && aTower.getCurYTile() == hoverY)
                aTower.paintAttackRadius(g2d);

            aTower.setAllEnemy(enemy);
            aTower.checkTarget();
            for (Enemy anEnemy : enemy) {
                if (aTower.inAttackRange(anEnemy) && anEnemy.isAlive()) {
                    aTower.addTargetCandidate(anEnemy);
                }
            }
            aTower.selectTarget();
            aTower.shootEnemy();
        }
    }

    public void sell() {
        if (map[curY][curX] == 'S') {
            gameInfo.sell(stone.getPrice());
            map[curY][curX] = 'A';
        } else if (map[curY][curX] == 'N' || map[curY][curX] == 'P' || map[curY][curX] == 'O') {
            Tower tempTower = new Tower();
            for (Tower aTower : tower) {
                if (aTower.getCurXTile() == curX && aTower.getCurYTile() == curY) {
                    tempTower = aTower;
                }
            }
            gameInfo.sell(tempTower.getPrice());
            tower.remove(tempTower);
            map[curY][curX] = 'S';
        }
        fieldInfo.show(map[curY][curX]);
    }

    public boolean checkEnemiesAlive() {
        for (Enemy anEnemy : enemy) {
            if (anEnemy.isAlive()) return true;
        }
        return false;
    }

    public void clearEnemy() {
        enemy.clear();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (map[y][x] == 'H') map[y][x] = ' ';
            }
        }
        if (e.getX() / 50 < 10 && e.getY() / 50 < 10 && map[e.getY() / 50][e.getX() / 50] == ' ') {
            map[e.getY() / 50][e.getX() / 50] = 'H';
        }
        hoverX = e.getX() / 50;
        hoverY = e.getY() / 50;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            curY = e.getY() / 50;
            curX = e.getX() / 50;

            if (curX == 10) curX--;
            if (curY == 10) curY--;
            fieldInfo.hideAll();
            if (map[curY][curX] == 'S') {
                fieldInfo.show('S');
            }
            if (map[curY][curX] == 'T') {
                fieldInfo.show('T');
            }
            if (build) {
                if (map[curY][curX] == 'H') {
                    removeActivePanel();
                    map[curY][curX] = 'A';
                }
                fieldInfo.show(map[curY][curX]);
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            removeActive();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
