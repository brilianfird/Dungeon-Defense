package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class MainFrame extends JFrame {
    //Initialize Class
    private final GameInfo gameInfo;
    private final GamePanel gamePanel;
    private final FieldInfo fieldInfo;
    private Timer spawnTimer;
    private Timer checkEnemyTimer;
    private final Timer statusTimer;
    private final Timer repaintTimer;
    private int counter;

    private MainFrame() {
        //Set Main Frame's property
        counter = 0;
        gameInfo = new GameInfo();
        gamePanel = new GamePanel();
        fieldInfo = new FieldInfo();
        setSize(620, 600);
        setTitle("Dungeon Defense");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(gamePanel, BorderLayout.CENTER);
        add(gameInfo, BorderLayout.EAST);
        add(fieldInfo, BorderLayout.SOUTH);
        pack();
        setResizable(false);

        setVisible(true);

        gamePanel.setFieldInfo(fieldInfo);
        gamePanel.setGameInfo(gameInfo);
        fieldInfo.setGamePanel(gamePanel);
        gameInfo.setFieldInfo(fieldInfo);
        gameInfo.setGamePanel(gamePanel);
        gameInfo.setMainFrame(this);


        ActionListener statusPeformer = e -> gameInfo.tick();

        statusTimer = new Timer(1000, statusPeformer);

        ActionListener repaintPeformer = e -> {
            gamePanel.repaint();
            if (gameInfo.getStatus().equals("Battle")) {
                if (counter == 0)
                    spawnTimer.start();
                if (counter == 10) {
                    stopSpawn();
                    checkEnemyTimer.start();
                }
            }
        };

        ActionListener spawnPeformer = e -> {
            if (counter != 10)
                counter++;
            gamePanel.spawnEnemy();
        };

        ActionListener checkEnemy = e -> {
            if (!gamePanel.checkEnemiesAlive()) {
                gameInfo.buildTime();
                gamePanel.clearEnemy();
                checkEnemyTimer.stop();
            }

        };

        checkEnemyTimer = new Timer(1000, checkEnemy);
        repaintTimer = new Timer(40, repaintPeformer);
        spawnTimer = new Timer(1000, spawnPeformer);
        while (true) {
            statusTimer.start();
            repaintTimer.start();
            if(gameOver(gameInfo))
                break;
        }
        this.dispose();
    }

    public static void main(String[] args) {
        while(true) {

            new MainFrame();
        }
    }

    private void stopSpawn() {
        spawnTimer.stop();
    }

    public void setCounter() {
        this.counter = 0;
    }

    private boolean gameOver(GameInfo gameInfo) {
        if (gameInfo.getHealth() < 1) {
            spawnTimer.stop();
            checkEnemyTimer.stop();
            statusTimer.stop();
            repaintTimer.stop();
            int i = -2;
            Object[] options = {"OK"};
            i = JOptionPane.showOptionDialog(this, "Game Over", "Message", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            while(true) {
                if(i != -2)
                    return true;
            }
        }
        return false;
    }
}
