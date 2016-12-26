package frame;


import javax.swing.*;
import java.awt.*;

class Bar extends JPanel {
    private final Color color;
    private final int max;
    private int curr;
    private final int width;
    private final int height;

    public Bar(Color color, int curr) {
        this.color = color;
        this.max = 20;
        this.curr = curr;
        this.width = 80;
        this.height = 10;
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

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, width, height);
        g2d.setColor(color);
        g2d.fillRect(1, 1, curr * width / max - 1, height - 1);
    }
}
