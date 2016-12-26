package pathfinding.astar;

public class Node {
    public static double fixed_cost = 0.5;
    private int row, col;
    //heuristic di astar pake euclidean distance
    private double distance, cost, total;
    private Node parent;

    public Node(int row, int col, double distance, double cost, Node parent) {
        this.row = row;
        this.col = col;
        this.distance = distance;
        this.cost = cost;
        this.parent = parent;
        total = cost + distance;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
