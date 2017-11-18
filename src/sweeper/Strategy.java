package sweeper;

import java.util.ArrayList;
import java.util.Arrays;

public class Strategy {

    public static void RGS() {
        ArrayList<int[]> uncovered = Game.getUncovered();
        System.out.println("Start Random Guessing!");
        int rd = Game.getRandom(uncovered);

        int[] choice = uncovered.get(rd);
        Node pb = new Node(choice[0], choice[1], NettleSweeper.map[choice[0]][choice[1]]);

        System.out.println("Probing " + Arrays.toString(pb.getLocation()));
        if (Game.probe(pb)) {
            NettleSweeper.printKB();
            uncovered = Game.getUncovered();
            if (uncovered.size() == 0) {
                NettleSweeper.printKB();
                System.out.println("You WIN!!!");
            }
            rd = Game.getRandom(uncovered);
            choice = uncovered.get(rd);
            pb = new Node(choice[0], choice[1], NettleSweeper.map[choice[0]][choice[1]]);
        } else {
            System.out.println("BOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOM!!!");
            System.out.println("YOU LOSE");
            System.exit(0);
        }
        
        // while (Game.probe(pb)) {
        // NettleSweeper.printKB();
        // uncovered = Game.getUncovered();
        // System.out.println(uncovered.size());
        // if (uncovered.size() == 0) {
        // NettleSweeper.printKB();
        // System.out.println("You WIN!!!");
        // }
        // rd = Game.getRandom(uncovered);
        // choice = uncovered.get(rd);
        // System.out.println(Arrays.toString(choice));
        // pb = new Node(choice[0], choice[1],
        // NettleSweeper.map[choice[0]][choice[1]]);
        // }
    }

    public static boolean SPS() {
        System.out.println("Single Point Strategy!");
        ArrayList<int[]> uncovered = Game.getUncovered();
        if(uncovered.size() == 0){
            return true;
        }
        ArrayList<Character> states = new ArrayList<>();
        for(char[] x : NettleSweeper.knowledgemap){
            for(char y : x){
                states.add(y);
            }
        }
        while(states.contains('X')){
            
            for (int[] i : uncovered) {
            if (NettleSweeper.knowledgemap[i[0]][i[1]] == 'X') {
                System.out.println("Attempting " + Arrays.toString(i));
                Node current = new Node(i[0], i[1], NettleSweeper.knowledgemap[i[0]][i[1]]);
                Game.setKBNeighbours(current, NettleSweeper.knowledgemap);
                ArrayList<Node> neighbours = new ArrayList<>();
                neighbours.addAll(current.getNeighbours());
                int unknown = 0;
                int mine = 0;
                for (Node n : neighbours) {
                    Game.setKBNeighbours(n, NettleSweeper.knowledgemap);
                    if (n.getState() != 'X' && n.getState() != 'P') {
                        System.out.println("Checking " + Arrays.toString(n.getLocation()));

                        for (Node nei : n.getNeighbours()) {
                            if (nei.getState() == 'X') {
                                unknown++;
                            }
                            if (nei.getState() == 'P') {
                                mine++;
                            }
                        }

                        System.out.println("Mine: " + mine);
                        System.out.println("Unknown: " + unknown);

                        if ((n.getState() - 48) >= unknown && (n.getState() - 48) > mine
                                && unknown <= (n.getState() - 48 - mine)) {
                            System.out.println("Mark!");
                            current.mark();
                            NettleSweeper.nettlenum -= 1;
                            NettleSweeper.knowledgemap[current.getX()][current.getY()] = (char) current.getState();
                            System.out.println((char) current.getState());
                            NettleSweeper.printKB();
                            break;
                        } else if ((n.getState() - 48) == mine) {
                            current.setState(current.getX(), current.getY());
                            System.out.println("Probe!");
                            if(!Game.probe(current)){
                                System.out.println("BOOOOOOOOOOOOOOOOOOOOOOM!!!");
                                System.out.println("YOU LOSE");
                                System.exit(0);
                            } else {
                                
                            }
                            NettleSweeper.printKB();
                            break;
                        } else {
                            System.out.println("Can not decide yet...");
                        }
                        unknown = 0;
                        mine = 0;
                    }
                }
            }
        }
            states = new ArrayList<>();
            for(char[] x : NettleSweeper.knowledgemap){
                for(char y : x){
                    states.add(y);
                }
            }
            int temp = uncovered.size();
            uncovered = Game.getUncovered();
            if(temp == uncovered.size()){
                System.out.println("No Move Can Be Made, Resort to Easy Equation Strategy...");
                NettleSweeper.printKB();
                return false;
            }
        }
        return true;
    }

    public static boolean EES(){
        System.out.println("Try Easy Equation Strategy");
        ArrayList<int[]> uncovered = Game.getUncovered();
        System.out.println(uncovered.size());
        ArrayList<Character> states = new ArrayList<>();
        for(char[] x : NettleSweeper.knowledgemap){
            for(char y : x){
                states.add(y);
            }
        }
        while(states.contains('X')){
            ArrayList<Node> uncleared = Game.getUnclearedCell();
            System.out.println(uncleared.size() + "Unclear cell");
            for(Node n : uncleared){
                System.out.println(Arrays.toString(n.getLocation()));
            }
            
            ArrayList<Node[]> pairs = pairCells(uncleared);
            System.out.println(pairs.size());
            
            for(Node[] pair : pairs){
                Node cell1 = pair[0];
                Node cell2 = pair[1];
                
                System.out.println("Checking " + Arrays.toString(cell1.getLocation()) + " and " + Arrays.toString(cell2.getLocation()));
                if(cell1.getState() == 'P' || cell2.getState() == 'P'){
                    System.out.println("Have a mark...");
                    continue;
                }
                int c1Marked = 0;
                int c2Marked = 0;
                
                ArrayList<Node> c1unknown = new ArrayList<>();
                ArrayList<Node> c2unknown = new ArrayList<>();
                
                for(Node c1n : cell1.getNeighbours()){
                    if(c1n.getState() == 'X'){
                        c1unknown.add(c1n);
                    }
                }
                
                for(Node c2n : cell2.getNeighbours()){
                    if(c2n.getState() == 'X'){
                        c2unknown.add(c2n);
                    }
                }
                
                for(Node nei: cell1.getNeighbours()){
                    if(nei.getState() == 'P'){
                        c1Marked++;
                    }
                }
                
                for(Node nei: cell2.getNeighbours()){
                    if(nei.getState() == 'P'){
                        c2Marked++;
                    }
                }
                
                int diff = Math.abs(((cell1.getState() - 48) - c1Marked) - ((cell2.getState() - 48) - c2Marked));
                System.out.println("diff " + diff);
                ArrayList<Node> unoverlap = removeOverlap(c1unknown, c2unknown);
                System.out.println("Unoverlap " + unoverlap.size());
                if(unoverlap.size() != 0){
                    if(diff == 0){
                        System.out.println("All Clear! Probe!");
                        for(Node n : unoverlap){
                            n.setState(n.getX(), n.getY());
                            if(!Game.probe(n)){
                                System.out.println("BOOOOOOOOOOOOOOOOOOOOOOM!!!");
                                System.out.println("YOU LOSE");
                                System.exit(0);
                            }
                            NettleSweeper.printKB();
                            break;
                        }
                        
                    } else {
                        if(diff == unoverlap.size()){
                            System.out.println("Mark All!");
                            for(Node n : unoverlap){
                                n.mark();
                                NettleSweeper.nettlenum -= 1;
                                NettleSweeper.knowledgemap[n.getX()][n.getY()] = (char) n.getState();
                                NettleSweeper.printKB();
                            }
                        } else {
                            System.out.println("Can not decide yet...");
                        }
                    }
                }
            }
            
            states = new ArrayList<>();
            for(char[] x : NettleSweeper.knowledgemap){
                for(char y : x){
                    states.add(y);
                }
            }
            int temp = uncovered.size();
            uncovered = Game.getUncovered();
            if(temp == uncovered.size()){
                System.out.println("No Move Can Be Made, Resort to Random Guess...");
                NettleSweeper.printKB();
                return false;
            } else {
                return true;
            }
        
        }

    return true;

    }

    public static ArrayList<Node> removeOverlap(ArrayList<Node> unknown1, ArrayList<Node> unknown2){
        
        ArrayList<Node> overlap1 = new ArrayList<>();
        ArrayList<Node> overlap2 = new ArrayList<>();
        
        for(Node n1 : unknown1){
            for(Node n2 : unknown2){
                if(Arrays.equals(n1.getLocation(), n2.getLocation())){
                    overlap1.add(n1);
                    overlap2.add(n2);
                }
            }
        }
        
        unknown1.removeAll(overlap1);
        unknown2.removeAll(overlap2);
        
        if(unknown1.isEmpty()){
            return unknown2;
        } else if (unknown2.isEmpty()){
            return unknown1;
        } else {
            System.out.println("Not fully overlaped");
            return new ArrayList<Node>();
        }
    }

    public static ArrayList<Node[]> pairCells(ArrayList<Node> uncleared) {
        ArrayList<Node[]> pairs = new ArrayList<>();
        for (int i = 0; i < uncleared.size(); i++) {
            // if(i != uncleared.size() - 1){
            Node current = uncleared.get(i);
            for (int j = i; j < uncleared.size(); j++) {
                if (j != uncleared.size() - 1) {
                    Node n = uncleared.get(j + 1);
                    if (Math.abs(n.getX() - current.getX()) + Math.abs(n.getY() - current.getY()) == 1) {
                        Node[] p = { current, n };
                        pairs.add(p);
                    }
                }
            }
            // }

        }

        return pairs;
    }
}
