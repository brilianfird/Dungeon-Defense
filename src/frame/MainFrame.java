package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame {
    //Initialize Class
    static GameInfo gameInfo = new GameInfo();
    static GamePanel gamePanel = new GamePanel();
    static FieldInfo fieldInfo = new FieldInfo();
    Timer spawnTimer;
    Timer checkEnemyTimer;
    Timer statusTimer;
    Timer repaintTimer;
    int counter = 0;

    MainFrame() {
        //Set Main Frame's property
        setSize(620, 600);
        setTitle("Dungeon Defense");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(gamePanel, BorderLayout.CENTER);
        add(gameInfo, BorderLayout.EAST);
        add(fieldInfo, BorderLayout.SOUTH);
        pack();

        setVisible(true);

        gamePanel.setFieldInfo(fieldInfo);
        gamePanel.setGameInfo(gameInfo);
        fieldInfo.setGamePanel(gamePanel);
        gameInfo.setFieldInfo(fieldInfo);
        gameInfo.setGamePanel(gamePanel);
        gameInfo.setMainFrame(this);


        ActionListener statusPeformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameInfo.tick();
            }
        };

        statusTimer = new Timer(1000, statusPeformer);

        ActionListener repaintPeformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.repaint();
                if (gameInfo.getStatus().equals("Battle")) {
                    if (counter == 0)
                        spawnTimer.start();
                    if (counter == 10) {
                        stopSpawn();
                        checkEnemyTimer.start();
                    }
                }
            }
        };

        ActionListener spawnPeformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter != 10)
                    counter++;
                gamePanel.spawnEnemy();
            }
        };

        ActionListener checkEnemy = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.checkEnemiesAlive()) {
                    gameInfo.buildTime();
                    gamePanel.clearEnemy();
                    checkEnemyTimer.stop();
                }

            }
        };

        checkEnemyTimer = new Timer(1000, checkEnemy);
        repaintTimer = new Timer(40, repaintPeformer);
        spawnTimer = new Timer(1000, spawnPeformer);
        while (!gameOver(gameInfo)) {
            statusTimer.start();
            repaintTimer.start();
        }


    }

    public static void main(String[] args) {
        new MainFrame();

    }

    public void stopSpawn() {
        spawnTimer.stop();
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean gameOver(GameInfo gameInfo) {
        if (gameInfo.getHealth() < 1) {
            spawnTimer.stop();
            checkEnemyTimer.stop();
            statusTimer.stop();
            repaintTimer.stop();
            JOptionPane.showMessageDialog(this, "Game Over");
            return true;
        }
        return false;
    }
}
