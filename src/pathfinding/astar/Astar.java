package pathfinding.astar;

import java.util.Vector;


public class Astar {
    private final int xStart;
    private final int yStart;
    private final Vector<Node> calon_jalur = new Vector<>();
    private final Vector<Node> jalur = new Vector<>();
    private final Vector<Node> realJalur = new Vector<>();
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
        for (int i = 0; i < calon_jalur.size(); i++) {
            for (int j = i; j < calon_jalur.size(); j++) {
                if (calon_jalur.get(i).getTotal() > calon_jalur.get(j).getTotal()) {
                    Node tem = calon_jalur.get(i);
                    calon_jalur.set(i, calon_jalur.get(j));
                    calon_jalur.set(j, tem);
                }
            }
        }
    }

    public void doAstar() {
        //Start
        calon_jalur.add(new Node(yStart, xStart, 0, 0, null));

        while (calon_jalur.size() != 0) {
            //return type object yang kebuang
            Node temp = calon_jalur.remove(0);
            int row = temp.getRow();
            int col = temp.getCol();

            if (row == yTarget && col == xTarget) {
                double distance = discCalc(row, col);
                jalur.add(new Node(row, col, distance, temp.getCost() + Node.fixed_cost, temp));
                traceBack(jalur.lastElement());

                return;
            }

            if (row > 0 && map[row - 1][col] == ' ') {
                double distance = discCalc(row - 1, col);
                calon_jalur.add(new Node(row - 1, col, distance, temp.getCost() + Node.fixed_cost, temp));
                jalur.add(new Node(row - 1, col, distance, temp.getCost() + Node.fixed_cost, temp));
                map[row - 1][col] = '.';
            }
            if (row < 9 && map[row + 1][col] == ' ') {
                double distance = discCalc(row + 1, col);
                calon_jalur.add(new Node(row + 1, col, distance, temp.getCost() + Node.fixed_cost, temp));
                jalur.add(new Node(row + 1, col, distance, temp.getCost() + Node.fixed_cost, temp));
                map[row + 1][col] = '.';
            }
            if (col > 0 && map[row][col - 1] == ' ') {
                double distance = discCalc(row, col - 1);
                calon_jalur.add(new Node(row, col - 1, distance, temp.getCost() + Node.fixed_cost, temp));
                jalur.add(new Node(row, col - 1, distance, temp.getCost() + Node.fixed_cost, temp));
                map[row][col - 1] = '.';
            }
            if (col < 9 && map[row][col + 1] == ' ') {
                double distance = discCalc(row, col + 1);
                calon_jalur.add(new Node(row, col + 1, distance, temp.getCost() + Node.fixed_cost, temp));
                jalur.add(new Node(row, col + 1, distance, temp.getCost() + Node.fixed_cost, temp));
                map[row][col + 1] = '.';
            }
            //kalo mau jadi djikstra tinggal di comment sortNode
            sortNode();


        }
        printMap();
    }

    public void printJalur() {
        System.out.println(jalur.lastElement().getCol() + "--" + jalur.lastElement().getRow());
    }

    private void printMap() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

    public int getCalculatedX() {
        return realJalur.lastElement().getCol();
    }

    public int getCalculatedY() {
        return realJalur.lastElement().getRow();
    }

    private void traceBack(Node node) {
        Node tem = node;
        while (tem.getParent() != null) {
            realJalur.add(tem);
            map[tem.getRow()][tem.getCol()] = '+';
            tem = tem.getParent();

        }

    }
}
