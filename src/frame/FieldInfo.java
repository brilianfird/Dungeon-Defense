package frame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FieldInfo extends JPanel implements MouseListener{
    private GamePanel gamePanel;

    JLabel nameLabel;
    JLabel upgradeTowerInfo;
    JLabel upgradeTowerDesc;
    JButton stoneButton;
    JButton sellButton;
    JButton normalButton;
    JButton splashButton;
    JButton pierceButton;

    JPanel namePanel;
    JPanel actionPanel;
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void hideAll(){
        nameLabel.setVisible(false);
        upgradeTowerInfo.setVisible(false);
        upgradeTowerDesc.setVisible(false);
        stoneButton.setVisible(false);
        sellButton.setVisible(false);
        normalButton.setVisible(false);
        splashButton.setVisible(false);
        pierceButton.setVisible(false);

    }

    public void show(char obj){
        if(obj == 'A'){
            stoneButton.setVisible(true);
        }
        else if(obj == 'S'){
            nameLabel.setText("Stone");
            nameLabel.setVisible(true);
            sellButton.setVisible(true);
            normalButton.setVisible(true);
            splashButton.setVisible(true);
            pierceButton.setVisible(true);
        }
        else if(obj == 'N' || obj == 'O' || obj == 'P'){
            if(obj == 'N')
                nameLabel.setText("Normal Tower");
            else if(obj =='O')
                nameLabel.setText("Splash Tower");
            else
                nameLabel.setText("Pierce Tower");

            nameLabel.setVisible(true);
            sellButton.setVisible(true);

        }
    }

    FieldInfo(){
        setPreferredSize(new Dimension(620,100));
        setLayout(new GridLayout(2,2));

        nameLabel = new JLabel("");
        upgradeTowerDesc = new JLabel("Desc");
        upgradeTowerInfo = new JLabel("Info");
        stoneButton = new JButton("Stone");
        sellButton = new JButton("Sell");
        normalButton = new JButton("Normal");
        splashButton = new JButton("Splash");
        pierceButton = new JButton("Pierce");

        stoneButton.addMouseListener(this);
        normalButton.addMouseListener(this);
        pierceButton.addMouseListener(this);
        sellButton.addMouseListener(this);
        splashButton.addMouseListener(this);

        namePanel = new JPanel(new FlowLayout());
        actionPanel = new JPanel(new FlowLayout());

        namePanel.add(nameLabel);
        namePanel.add(stoneButton);

        actionPanel.add(sellButton);
        actionPanel.add(normalButton);
        actionPanel.add(splashButton);
        actionPanel.add(pierceButton);

        add(namePanel);
        add(upgradeTowerInfo);
        add(actionPanel);
        add(upgradeTowerDesc);
        hideAll();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == stoneButton){
            gamePanel.buildStone();
        }
        else if(e.getSource() == normalButton){
            gamePanel.addTower("Normal");
        }
        else if(e.getSource() == splashButton){
            gamePanel.addTower("Splash");
        }else if(e.getSource() == pierceButton)
            gamePanel.addTower("Pierce");
        else if(e.getSource() == sellButton){
            gamePanel.sell();
        }
        hideAll();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object source = e.getSource();
        if(source == normalButton){
            upgradeTowerInfo.setText("Normal Tower[Price : 50]");
            upgradeTowerDesc.setText("Normal Range Single Target with Normal Speed");
            upgradeTowerDesc.setVisible(true);
            upgradeTowerInfo.setVisible(true);
        }
        else if(source == splashButton){
            upgradeTowerInfo.setText("Splash Tower[Price : 55]");
            upgradeTowerDesc.setText("Normal Range Many Target with Slow Speed");
            upgradeTowerDesc.setVisible(true);
            upgradeTowerInfo.setVisible(true);
        }
        else if(source == pierceButton){
            upgradeTowerInfo.setText("Pierce Tower[Price : 55]");
            upgradeTowerDesc.setText("Small Range Single Target with Fast Speed");
            upgradeTowerDesc.setVisible(true);
            upgradeTowerInfo.setVisible(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();
        if(source == normalButton || source == pierceButton || source == splashButton) {
            upgradeTowerDesc.setVisible(false);
            upgradeTowerInfo.setVisible(false);
        }
    }
}
