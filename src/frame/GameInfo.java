package frame;

import javax.swing.*;
import java.awt.*;

public class GameInfo extends JPanel {
    private final Bar timerBar;
    private final Bar healthBar;
    private final JLabel labelTitle;
    private final JLabel labelTitle2;
    private final JLabel labelWave;
    private final JLabel labelStatus;
    private final JLabel labelHealth;
    private final JLabel labelMoney;
    private final JLabel labelCurrMoney;
    JLabel labelNotification;
    FieldInfo fieldInfo;
    GamePanel gamePanel;
    MainFrame mainFrame;
    private int health;
    private int money;
    private int wave;
    private int timer;
    private String status;
    GameInfo() {
        wave = 1;
        status = "Build";
        timer = 20;
        health = 20;
        money = 250;

        labelTitle = new JLabel("Dungeon");
        labelTitle2 = new JLabel("Defense");
        labelWave = new JLabel("Wave: " + wave);
        labelStatus = new JLabel(status);
        labelHealth = new JLabel("Health");
        labelMoney = new JLabel("Money");
        labelCurrMoney = new JLabel(money + " G");
        labelNotification = new JLabel("");

        timerBar = new Bar(Color.blue, 20, timer, 80, 10);
        healthBar = new Bar(Color.red, 20, health, 80, 10);

        setLayout(new GridLayout(20, 1));
        setPreferredSize(new Dimension(110, 500));


        add(labelTitle);
        add(labelTitle2);
        add(labelWave);
        add(labelStatus);
        add(timerBar);
        add(labelHealth);
        add(healthBar);
        add(labelMoney);
        add(labelCurrMoney);
        add(labelNotification);
    }

    public int getHealth() {
        return health;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setFieldInfo(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public void battleTime() {
        status = "Battle";
        labelStatus.setText(status);
        setTimer(20);

        gamePanel.setBuild(false);
        if (gamePanel.removeActivePanel()) {

            fieldInfo.hideAll();
        }
    }

    public void buildTime() {
        money += wave * 10 / 2;
        labelCurrMoney.setText(money + " G");
        wave++;
        labelWave.setText("Wave : " + wave);
        status = "Build";
        labelStatus.setText(status);
        setTimer(20);
        mainFrame.setCounter(0);
        gamePanel.setBuild(true);
        gamePanel.resetActive();
    }

    public void addMoney(int price) {
        money += price;
        labelCurrMoney.setText(money + " G");
    }

    public void tick() {
        if (timer == 1 && status.equals("Build")) {
            battleTime();
        } else if (status.equals("Battle")) ;
        else {
            setTimer(timer - 1);
        }
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getTimer() {
        return timer;
    }

    private void setTimer(int timer) {
        this.timer = timer;
        timerBar.setCurr(timer);
        timerBar.repaint();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void damaged() {
        health--;
        healthBar.setCurr(health);
        healthBar.repaint();
    }

    public boolean buy(int price) {
        if (price > money)
            return false;
        else {
            money -= price;
            labelCurrMoney.setText(money + " G");
            return true;
        }
    }

    public void sell(int price) {
        money += price;
        labelCurrMoney.setText(money + " G");
    }

    public void setNotification(String notification) {
        labelNotification.setText(notification);
    }
}
