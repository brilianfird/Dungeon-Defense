package frame;


import javax.swing.*;
import java.awt.*;

public class Bar extends JPanel{
    Color color;
    int max;
    int curr;
    int width;
    int height;

    public Bar(Color color, int max, int curr, int width, int height) {
        this.color = color;
        this.max = max;
        this.curr = curr;
        this.width = width;
        this.height = height;
    }

    public int getCurr() {
        return curr;
    }

    public void setCurr(int curr) {
        this.curr = curr;
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.black);
        g2d.drawRect(0,0,width,height);
        g2d.setColor(color);
        g2d.fillRect(1,1,curr*width/max-1,height-1);
    }
}
