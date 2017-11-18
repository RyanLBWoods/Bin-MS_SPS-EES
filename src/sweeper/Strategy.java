package sweeper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for logic strategies.
 * 
 * @author bl41
 *
 */
public class Strategy {

    /**
     * Random guessing strategy.
     */
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
    }

    /**
     * Single point strategy.
     * 
     * @return Return true if successfully uncovered any cells
     */
    public static boolean SPS() {
        System.out.println("Single Point Strategy!");
        // Get unexplored cells
        ArrayList<int[]> uncovered = Game.getUncovered();
        if (uncovered.size() == 0) {
            return true;
        }
        ArrayList<Character> states = new ArrayList<>();
        for (char[] x : NettleSweeper.knowledgemap) {
            for (char y : x) {
                states.add(y);
            }
        }
        while (states.contains(Configurations.UNCOVER)) {

            for (int[] i : uncovered) {
                if (NettleSweeper.knowledgemap[i[0]][i[1]] == Configurations.UNCOVER) {
                    System.out.println("Attempting " + Arrays.toString(i));
                    Node current = new Node(i[0], i[1], NettleSweeper.knowledgemap[i[0]][i[1]]);
                    Game.setKBNeighbours(current, NettleSweeper.knowledgemap);
                    ArrayList<Node> neighbours = new ArrayList<>();
                    neighbours.addAll(current.getNeighbours());
                    int unknown = 0;
                    int mine = 0;
                    for (Node n : neighbours) {
                        Game.setKBNeighbours(n, NettleSweeper.knowledgemap);
                        // If the the node has been probed
                        if (n.getState() != Configurations.UNCOVER && n.getState() != Configurations.MARK) {
                            System.out.println("Checking " + Arrays.toString(n.getLocation()));

                            for (Node nei : n.getNeighbours()) {
                                if (nei.getState() == Configurations.UNCOVER) {
                                    unknown++;
                                }
                                if (nei.getState() == Configurations.MARK) {
                                    mine++;
                                }
                            }

                            System.out.println("Mine: " + mine);
                            System.out.println("Unknown: " + unknown);

                            if ((n.getState() - 48) >= unknown && (n.getState() - 48) > mine
                                    && unknown <= (n.getState() - 48 - mine)) {
                                // If all marked neighbour
                                System.out.println("Mark!");
                                current.mark();
                                NettleSweeper.nettlenum -= 1;
                                NettleSweeper.knowledgemap[current.getX()][current.getY()] = (char) current.getState();
                                NettleSweeper.printKB();
                                break;
                            } else if ((n.getState() - 48) == mine) {
                                // If all free neighbour
                                current.setState(current.getX(), current.getY());
                                System.out.println("Probe!");
                                if (!Game.probe(current)) {
                                    System.out.println("BOOOOOOOOOOOOOOOOOOOOOOM!!!");
                                    System.out.println("YOU LOSE");
                                    System.exit(0);
                                }
                                NettleSweeper.printKB();
                                break;
                            } else {
                                System.out.println("Can not decide yet...");
                            }
                            // Reset variable for next go
                            unknown = 0;
                            mine = 0;
                        }
                    }
                }
            }
            // Check if current round uncovered any cell
            // Return false when it didn't indicate no move can be make by this
            // strategy
            states = new ArrayList<>();
            for (char[] x : NettleSweeper.knowledgemap) {
                for (char y : x) {
                    states.add(y);
                }
            }
            int temp = uncovered.size();
            uncovered = Game.getUncovered();
            if (temp == uncovered.size()) {
                System.out.println("No Move Can Be Made, Resort to Easy Equation Strategy...");
                NettleSweeper.printKB();
                return false;
            }
        }
        return true;
    }

    /**
     * Easy equation Strategy.
     * 
     * @return Return true if successfully uncovered any cells
     */
    public static boolean EES() {
        System.out.println("Try Easy Equation Strategy");
        // Get unexplored cells
        ArrayList<int[]> uncovered = Game.getUncovered();
        ArrayList<Character> states = new ArrayList<>();
        for (char[] x : NettleSweeper.knowledgemap) {
            for (char y : x) {
                states.add(y);
            }
        }
        while (states.contains(Configurations.UNCOVER)) {
            // Find uncleared cells
            ArrayList<Node> uncleared = Game.getUnclearedCell();
            System.out.println(uncleared.size() + " Unclear cell");
            // Set uncleared cells to pairs
            ArrayList<Node[]> pairs = pairCells(uncleared);
            // Check each pair
            for (Node[] pair : pairs) {
                Node cell1 = pair[0];
                Node cell2 = pair[1];

                System.out.println("Checking " + Arrays.toString(cell1.getLocation()) + " and "
                        + Arrays.toString(cell2.getLocation()));
                if (cell1.getState() == Configurations.MARK || cell2.getState() == Configurations.MARK) {
                    System.out.println("Have a mark...");
                    continue;
                }
                int c1Marked = 0;
                int c2Marked = 0;
                // Get unknown cells lists
                ArrayList<Node> c1unknown = new ArrayList<>();
                ArrayList<Node> c2unknown = new ArrayList<>();

                for (Node c1n : cell1.getNeighbours()) {
                    if (c1n.getState() == Configurations.UNCOVER) {
                        c1unknown.add(c1n);
                    }
                }
                for (Node c2n : cell2.getNeighbours()) {
                    if (c2n.getState() == Configurations.UNCOVER) {
                        c2unknown.add(c2n);
                    }
                }
                // Get number of marked cells
                for (Node nei : cell1.getNeighbours()) {
                    if (nei.getState() == Configurations.MARK) {
                        c1Marked++;
                    }
                }
                for (Node nei : cell2.getNeighbours()) {
                    if (nei.getState() == Configurations.MARK) {
                        c2Marked++;
                    }
                }
                // Calculate diff
                int diff = Math.abs(((cell1.getState() - 48) - c1Marked) - ((cell2.getState() - 48) - c2Marked));
                System.out.println("diff " + diff);
                // Check overlapping
                ArrayList<Node> unoverlap = removeOverlap(c1unknown, c2unknown);
                System.out.println("Unoverlap " + unoverlap.size());
                // If fully overlapping
                if (unoverlap.size() != 0) {
                    if (diff == 0) {
                        System.out.println("All Clear! Probe!");
                        for (Node n : unoverlap) {
                            n.setState(n.getX(), n.getY());
                            if (!Game.probe(n)) {
                                System.out.println("BOOOOOOOOOOOOOOOOOOOOOOM!!!");
                                System.out.println("YOU LOSE");
                                System.exit(0);
                            }
                            NettleSweeper.printKB();
                            break;
                        }
                    } else {
                        if (diff == unoverlap.size()) {
                            System.out.println("Mark All!");
                            for (Node n : unoverlap) {
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
            // Check if current round uncovered any cell
            // Return false when it didn't indicate no move can be make by this
            // strategy
            states = new ArrayList<>();
            for (char[] x : NettleSweeper.knowledgemap) {
                for (char y : x) {
                    states.add(y);
                }
            }
            int temp = uncovered.size();
            uncovered = Game.getUncovered();
            if (temp == uncovered.size()) {
                System.out.println("No Move Can Be Made, Resort to Random Guess...");
                NettleSweeper.printKB();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    /**
     * Method to check overlapping.
     * 
     * @param unknown1
     *            Unknown cell list of cell in pair
     * @param unknown2
     *            Unknown cell list of cell in pair
     * @return Return array list of not overlapped cells only if fully
     *         overlapping
     */
    public static ArrayList<Node> removeOverlap(ArrayList<Node> unknown1, ArrayList<Node> unknown2) {

        ArrayList<Node> overlap1 = new ArrayList<>();
        ArrayList<Node> overlap2 = new ArrayList<>();

        for (Node n1 : unknown1) {
            for (Node n2 : unknown2) {
                if (Arrays.equals(n1.getLocation(), n2.getLocation())) {
                    overlap1.add(n1);
                    overlap2.add(n2);
                }
            }
        }

        unknown1.removeAll(overlap1);
        unknown2.removeAll(overlap2);

        if (unknown1.isEmpty()) {
            return unknown2;
        } else if (unknown2.isEmpty()) {
            return unknown1;
        } else {
            System.out.println("Not fully overlaped");
            return new ArrayList<Node>();
        }
    }

    /**
     * Set uncleared cell to pairs.
     * 
     * @param uncleared
     *            Array list of uncleared cell
     * @return Return an array list of paired cells
     */
    public static ArrayList<Node[]> pairCells(ArrayList<Node> uncleared) {
        ArrayList<Node[]> pairs = new ArrayList<>();
        for (int i = 0; i < uncleared.size(); i++) {
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
        }
        return pairs;
    }
}
