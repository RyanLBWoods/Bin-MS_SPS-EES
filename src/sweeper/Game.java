package sweeper;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    /**
     * 
     * @param start
     */
    public static void deduce(Node start){
        probe(start);
        
        for(int i = 0; i < NettleSweeper.knowledgemap.length; i++){
            for(int j = 0; j < NettleSweeper.knowledgemap[i].length; j++){
                if(NettleSweeper.knowledgemap[i][j] == '0'){
                    Node n = new Node(i, j, NettleSweeper.knowledgemap[i][j] - 48);
                    probe(n);
                }
            }
        }
        NettleSweeper.printKB();
    }
    /**
     * 
     * @param n
     * @return
     */
    public static boolean probe(Node n){
        if(n.getState() == -1){
            // Return false if probe a -1
            return false;
        } else if (n.getState() == 0) {
            // Cover all neighbours if current state is 0
            NettleSweeper.knowledgemap[n.getX()][n.getY()] = (char) (n.getState() + 48);
            // Get neighbours
            setNeighbours(n, NettleSweeper.map);
            ArrayList<Node> neighbour = n.getNeighbours();
            for(Node nei : neighbour){
                NettleSweeper.knowledgemap[nei.getX()][nei.getY()] = (char) (nei.getState() + 48);
                if(nei.getState() == 0){
                    openZero(nei);
                }
            }
            return true;
        } else {
            // Give state to knowledge base if it is not a -1
            char c = (char) (n.getState() + 48);
            NettleSweeper.knowledgemap[n.getX()][n.getY()] = c;
            return true;
        }
    }
    /**
     * 
     * @param zero
     */
    public static void openZero(Node zero){
        setNeighbours(zero, NettleSweeper.map);
        ArrayList<Node> neighbour = zero.getNeighbours();
        for(Node nei : neighbour){
            NettleSweeper.knowledgemap[nei.getX()][nei.getY()] = (char) (nei.getState() + 48);
        }
        
    }
    /**
     * 
     * @return
     */
    public static ArrayList<int[]> getUncovered(){
        ArrayList<int[]> uncovered = new ArrayList<>();
        for(int i = 0; i < NettleSweeper.knowledgemap.length; i++){
            for(int j = 0; j < NettleSweeper.knowledgemap[i].length; j++){
                if(NettleSweeper.knowledgemap[i][j] == 'X'){
                    int[] location = {i, j};
                    uncovered.add(location);
                }
            }
        }
        return uncovered;
    }
    /**
     * 
     * @param uncovered
     * @return
     */
    public static int getRandom(ArrayList<int[]> uncovered){
        return (int) (Math.random() * uncovered.size());
    }
    /**
     * 
     * @param n
     * @param map
     */
    public static void setNeighbours(Node n, int[][] map){
        if(n.getX() - 1 >= 0){
            n.uNeighbour = new Node(n.getX() - 1, n.getY(), map[n.getX() - 1][n.getY()]);
            if(n.getY() - 1 >= 0){
                n.luNeighbour = new Node(n.getX() - 1, n.getY() - 1, map[n.getX() - 1][n.getY() - 1]);
            }
        }
        if(n.getX() + 1 < NettleSweeper.mapSize){
            n.dNeighbour = new Node(n.getX() + 1, n.getY(), map[n.getX() + 1][n.getY()]);
            if(n.getY() - 1 >= 0){
                n.ldNeighbour = new Node(n.getX() + 1, n.getY() - 1, map[n.getX() + 1][n.getY() - 1]);
            }
        }
        if(n.getY() + 1 < NettleSweeper.mapSize){
            n.rNeighbour = new Node(n.getX(), n.getY() + 1, map[n.getX()][n.getY() + 1]);
            if(n.getX() - 1 >= 0){
                n.ruNeighbour = new Node(n.getX() - 1, n.getY() + 1, map[n.getX() - 1][n.getY() + 1]);
            }
            if(n.getX() + 1 < NettleSweeper.mapSize){
                n.rdNeighbour = new Node(n.getX() + 1, n.getY() + 1, map[n.getX() + 1][n.getY() + 1]);
            }
        }
        if(n.getY() - 1 >= 0){
            n.lNeighbour = new Node(n.getX(), n.getY() - 1, map[n.getX()][n.getY() - 1]);
        }
    }
    /**
     * 
     * @return
     */
    public static ArrayList<Node> getCells(){
        int[][] map = EasyMap.getMap(1);
        ArrayList<Node> cells = new ArrayList<>();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                Node cell = new Node(i, j, map[i][j]);
                if(i - 1 > 0 && j -1 > 0){
                    cell.luNeighbour = new Node(i - 1, j - 1, map[i - 1][j - 1]);
                    cell.lNeighbour = new Node(i, j - 1, map[i][j - 1]);
                    cell.uNeighbour = new Node(i - 1, j, map[i - 1][j]);
                }
                if(i + 1 < NettleSweeper.mapSize && j + 1 < NettleSweeper.mapSize){
                    cell.rdNeighbour = new Node(i + 1, j + 1, map[i + 1][j + 1]);
                    cell.rNeighbour = new Node(i, j + 1, map[i][j + 1]);
                    cell.dNeighbour = new Node(i + 1, j, map[i + 1][j]);
                }
                if(i - 1 > 0 && j + 1 < NettleSweeper.mapSize){
                    cell.ruNeighbour = new Node(i - 1, j + 1, map[i - 1][j + 1]);
                }
                if(i + 1 < NettleSweeper.mapSize && j - 1 > 0){
                    cell.ldNeighbour = new Node(i + 1, j - 1, map[i + 1][j - 1]);
                }
                cells.add(cell);
            }
        }
        
        System.out.println(cells.size());
        return cells;
    }
    
    public static ArrayList<int[]> getNettlePosition(int[][] map){
        ArrayList<int[]> nettles = new ArrayList<>();
//        int a = 0;
        
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == -1){
                    int[] nettle_position = {i, j};
                    nettles.add(nettle_position);
//                    System.out.println(Arrays.toString(nettles.get(a)));
//                    a++;
                }
            }
        }
        
        return nettles;
    }

    public static ArrayList<int[]> getDeducedNettlePosition(char[][] knowledgemap) {
        // TODO Auto-generated method stub
        ArrayList<int[]> nettles = new ArrayList<>();
//        int a = 0;
        for(int i = 0; i < knowledgemap.length; i++){
            for(int j = 0; j < knowledgemap[i].length; j++){
                if(knowledgemap[i][j] == -1){
                    int[] nettle_position = {i, j};
                    nettles.add(nettle_position);
//                    System.out.println(Arrays.toString(nettles.get(a)));
//                    a++;
                }
            }
        }
        
        return nettles;
    }
    /**
     * 
     * @param n
     * @param map
     */
    public static void setKBNeighbours(Node n, char[][] map) {
        // TODO Auto-generated method stub
        if(n.getX() - 1 >= 0){
            n.uNeighbour = new Node(n.getX() - 1, n.getY(), map[n.getX() - 1][n.getY()]);
            if(n.getY() - 1 >= 0){
                n.luNeighbour = new Node(n.getX() - 1, n.getY() - 1, map[n.getX() - 1][n.getY() - 1]);
            }
        }
        if(n.getX() + 1 < NettleSweeper.mapSize){
            n.dNeighbour = new Node(n.getX() + 1, n.getY(), map[n.getX() + 1][n.getY()]);
            if(n.getY() - 1 >= 0){
                n.ldNeighbour = new Node(n.getX() + 1, n.getY() - 1, map[n.getX() + 1][n.getY() - 1]);
            }
        }
        if(n.getY() + 1 < NettleSweeper.mapSize){
            n.rNeighbour = new Node(n.getX(), n.getY() + 1, map[n.getX()][n.getY() + 1]);
            if(n.getX() - 1 >= 0){
                n.ruNeighbour = new Node(n.getX() - 1, n.getY() + 1, map[n.getX() - 1][n.getY() + 1]);
            }
            if(n.getX() + 1 < NettleSweeper.mapSize){
                n.rdNeighbour = new Node(n.getX() + 1, n.getY() + 1, map[n.getX() + 1][n.getY() + 1]);
            }
        }
        if(n.getY() - 1 >= 0){
            n.lNeighbour = new Node(n.getX(), n.getY() - 1, map[n.getX()][n.getY() - 1]);
        }
    }
    
    public static ArrayList<Node> getUnclearedCell(){
        ArrayList<Node> uncleared = new ArrayList<>();
        for(int i = 0; i < NettleSweeper.knowledgemap.length; i++){
            for(int j = 0; j < NettleSweeper.knowledgemap[i].length; j++){
                Node current = new Node(i, j, NettleSweeper.knowledgemap[i][j]);
                if(current.getState() != 'X'){
                    setKBNeighbours(current, NettleSweeper.knowledgemap);
                    ArrayList<Node> curNeighbours = current.getNeighbours();
                    for(Node n : curNeighbours){
                        if(n.getState() == 'X'){
                            uncleared.add(current);
                            break;
                        }
                    }
                }
            }
        }
        return uncleared;
    }
    
}
