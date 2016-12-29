package pathfinding.astar;

import java.util.Vector;


public class Astar {
    private final int xStart;
    private final int yStart;
    private final Vector<Node> pathCandidate = new Vector<>();
    private final Vector<Node> path = new Vector<>();
    private final Vector<Node> pathToReturn = new Vector<>();
    private final int xTarget;
    private final int yTarget;
    private final char[][] map;

    public Astar(int xStart, int yStart, int yTarget, char[][] cmap) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xTarget = 9;
        this.yTarget = yTarget;
        map = new char[10][10];
        for (int y = 0; y < 10; y++)
            for (int x = 0; x < 10; x++) {
                this.map[y][x] = cmap[y][x];
                if (cmap[y][x] == 'B' || cmap[y][x] == 'A' || cmap[y][x] == 'H') map[y][x] = ' ';
            }
    }

    private double discCalc(int row, int col) {
        return Math.sqrt(Math.pow(row - yTarget, 2) + Math.pow((col - xTarget), 2));
    }

    private void sortNode() {
        for (int i = 0; i < pathCandidate.size(); i++) {
            for (int j = i; j < pathCandidate.size(); j++) {
                if (pathCandidate.get(i).getTotal() > pathCandidate.get(j).getTotal()) {
                    Node tem = pathCandidate.get(i);
                    pathCandidate.set(i, pathCandidate.get(j));
                    pathCandidate.set(j, tem);
                }
            }
        }
    }

    public void doAstar() {
        pathCandidate.add(new Node(yStart, xStart, 0, 0, null));

        while (pathCandidate.size() != 0) {
            Node temp = pathCandidate.remove(0);
            int row = temp.getY();
            int col = temp.getX();

            if (row == yTarget && col == xTarget) {
                double distance = discCalc(row, col);
                path.add(new Node(row, col, distance, temp.getCost() + Node.fixed_cost, temp));
                traceBack(path.lastElement());

                return;
            }

            if (row > 0 && map[row - 1][col] == ' ') {
                double distance = discCalc(row - 1, col);
                pathCandidate.add(new Node(row - 1, col, distance, temp.getCost() + Node.fixed_cost, temp));
                path.add(new Node(row - 1, col, distance, temp.getCost() + Node.fixed_cost, temp));
                map[row - 1][col] = '.';
            }
            if (row < 9 && map[row + 1][col] == ' ') {
                double distance = discCalc(row + 1, col);
                pathCandidate.add(new Node(row + 1, col, distance, temp.getCost() + Node.fixed_cost, temp));
                path.add(new Node(row + 1, col, distance, temp.getCost() + Node.fixed_cost, temp));
                map[row + 1][col] = '.';
            }
            if (col > 0 && map[row][col - 1] == ' ') {
                double distance = discCalc(row, col - 1);
                pathCandidate.add(new Node(row, col - 1, distance, temp.getCost() + Node.fixed_cost, temp));
                path.add(new Node(row, col - 1, distance, temp.getCost() + Node.fixed_cost, temp));
                map[row][col - 1] = '.';
            }
            if (col < 9 && map[row][col + 1] == ' ') {
                double distance = discCalc(row, col + 1);
                pathCandidate.add(new Node(row, col + 1, distance, temp.getCost() + Node.fixed_cost, temp));
                path.add(new Node(row, col + 1, distance, temp.getCost() + Node.fixed_cost, temp));
                map[row][col + 1] = '.';
            }
            sortNode();


        }
    }


    public int getCalculatedX() {
        return pathToReturn.lastElement().getX();
    }

    public int getCalculatedY() {
        return pathToReturn.lastElement().getY();
    }

    private void traceBack(Node node) {
        Node tem = node;
        while (tem.getParent() != null) {
            pathToReturn.add(tem);
            map[tem.getY()][tem.getX()] = '+';
            tem = tem.getParent();

        }

    }
}
