package pathfinding.astar;

public class Node {
    public static final double fixed_cost = 0.5;
    private int y, x;
    private double distance, cost, total;
    private Node parent;

    public Node(int y, int x, double distance, double cost, Node parent) {
        this.y = y;
        this.x = x;
        this.distance = distance;
        this.cost = cost;
        this.parent = parent;
        total = cost + distance;
    }

    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }


    public double getCost() {
        return cost;
    }

    public double getTotal() {
        return total;
    }


    public Node getParent() {
        return parent;
    }

}
