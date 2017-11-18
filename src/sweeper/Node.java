package sweeper;

import java.util.ArrayList;

public class Node {

    private int x;
    private int y;
    private int state;
    
//    private ArrayList<Node> uncovered;
//    private int marked;
    
    Node luNeighbour;
    Node uNeighbour;
    Node ruNeighbour;
    Node lNeighbour;
    Node rNeighbour;
    Node ldNeighbour;
    Node dNeighbour;
    Node rdNeighbour;
    
    ArrayList<Node> neighbours = new ArrayList<>();
    
    public Node(int x, int y, int state){
        this.x = x;
        this.y = y;
        this.state = state;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getState(){
        return state;
    }
    
    public int[] getLocation() {
        int[] location = { x, y };
        return location;
    }
    
    public ArrayList<Node> getNeighbours(){
        
        ArrayList<Node> neighbours = new ArrayList<>();
        
        if(this.luNeighbour != null){
            neighbours.add(luNeighbour);
        }
        
        if(this.uNeighbour != null){
            neighbours.add(uNeighbour);
        }
        
        if(this.ruNeighbour != null){
            neighbours.add(ruNeighbour);
        }
        
        if(this.lNeighbour != null){
            neighbours.add(lNeighbour);
        }
        
        if(this.rNeighbour != null){
            neighbours.add(rNeighbour);
        }
        
        if(this.ldNeighbour != null){
            neighbours.add(ldNeighbour);
        }
        
        if(this.dNeighbour != null){
            neighbours.add(dNeighbour);
        }
        
        if(this.rdNeighbour != null){
            neighbours.add(rdNeighbour);
        }
        
        return neighbours;
    }
    
    public void mark(){
        this.state = Configurations.MARK;
    }
    
    public void setState(int x, int y){
        this.state = NettleSweeper.map[x][y];
    }
}
