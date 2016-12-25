package collision;


import object.tower.Tower;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;

public class ArcArea {
    Tower tower;
    Shape shape;
    double midXCoor;
    double midYCoor;
    int radius;
    Area area;

    public ArcArea(int radius, int x, int y){
        this.radius = radius;
        midXCoor = x;
        midYCoor = y;
    }

    public ArcArea(int radius, Tower tower){
        this.radius = radius;
        this.tower = tower;
        midXCoor = (double)tower.getCurXTile()*50+25;
        midYCoor = (double)tower.getCurYTile()*50+25;
        shape = new Arc2D.Double(midXCoor-radius/2,midYCoor-radius/2,radius,radius,0,360,1);
        area = new Area(shape);
    }

    public void paintRadius(Graphics2D g){
        g.setColor(Color.yellow);
        g.drawArc((int)midXCoor-radius/2,(int)midYCoor-radius/2,radius,radius,0,360);
        g.draw(shape);
    }

    public boolean collideCheck(Rectangle target){
        if(area.intersects(target)) return true;
        else return false;
    }
}
