package pathfinding.floodfill;

import java.util.ArrayList;


public class FloodFill {
    private final char[][] map;
    private int monsterLair;
    private int baseCamp;
    private final ArrayList<Node> node = new ArrayList<>();
    public FloodFill(char[][] map){
        this.map = new char[10][10];
        for (int y = 0; y < 10; y++) {
            System.arraycopy(map[y], 0, this.map[y], 0, 10);
        }
        for(int y = 0;y < 10;y++){
            if(map[y][0] == 'M')
                monsterLair = y;
            if(map[y][9] == 'B')
                baseCamp = y;
        }
        node.add(new Node(0,monsterLair));
    }
    public void printMap(){
        for(int i = 0;i <100;i++)
            System.out.println();
        for(int y = 0; y < 10; y++){
            for(int x = 0; x < 10; x++){
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

    public boolean doFloodFill(){
        while(!node.isEmpty()){
            int x = node.get(0).getX();
            int y = node.get(0).getY();
            //left
            if(x != 0){
                if(map[y][x-1] == ' ') {
                    node.add(new Node(x - 1, y));
                    map[y][x-1] = '.';
                }else if(x-1 == 9 && y == baseCamp){
                    
                    return true;
                }
            }
            //right
            if(x != 9){
                if(map[y][x+1] == ' ') {
                    node.add(new Node(x + 1, y));
                    map[y][x+1] = '.';
                }else if(x+1 == 9 && y == baseCamp){
                    
                    return true;
                }
            }

            //up
            if(y != 0){
                if(map[y-1][x] == ' ') {
                    node.add(new Node(x, y - 1));
                    map[y-1][x] = '.';
                }else if(x == 9 && y-1 == baseCamp){
                    
                    return true;
                }
            }

            //down
            if(y != 9){
                if(map[y+1][x] == ' ') {
                    node.add(new Node(x, y + 1));
                    map[y+1][x] = '.';
                }else if(x == 9 && y+1 == baseCamp){
                    
                    return true;
                }
            }

            node.remove(0);
        }
        
        return false;
    }
}
