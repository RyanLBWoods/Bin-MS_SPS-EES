package sweeper;

import java.util.ArrayList;

/**
 * Algorithms for game.
 * 
 * @author bl41
 *
 */
public class Game {
    /**
     * Deduce the reachable empty cells of map from (0, 0).
     * 
     * @param start
     *            The start node
     */
    public static void deduce(Node start) {
        // Probe the node
        probe(start);
        // Search if have a reachable zero cell
        for (int i = 0; i < NettleSweeper.knowledgemap.length; i++) {
            for (int j = 0; j < NettleSweeper.knowledgemap[i].length; j++) {
                if (NettleSweeper.knowledgemap[i][j] == '0') {
                    Node n = new Node(i, j, NettleSweeper.knowledgemap[i][j] - 48);
                    probe(n);
                }
            }
        }
        NettleSweeper.printKB();
    }

    /**
     * Method to probe the cell.
     * 
     * @param n
     *            The node to probe
     * @return Return true if it is not a nettle
     */
    public static boolean probe(Node n) {
        if (n.getState() == -1) {
            // Return false if probe a -1
            return false;
        } else if (n.getState() == 0) {
            // Cover all neighbours if current state is 0
            NettleSweeper.knowledgemap[n.getX()][n.getY()] = (char) (n.getState() + 48);
            // Get neighbours
            setNeighbours(n, NettleSweeper.map);
            ArrayList<Node> neighbour = n.getNeighbours();
            for (Node nei : neighbour) {
                NettleSweeper.knowledgemap[nei.getX()][nei.getY()] = (char) (nei.getState() + 48);
                if (nei.getState() == 0) {
                    // Uncover all neighbours around zero cell
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
     * Method to uncover all neighbours of zero cell.
     * 
     * @param zero
     *            The zero cell
     */
    public static void openZero(Node zero) {
        setNeighbours(zero, NettleSweeper.map);
        ArrayList<Node> neighbour = zero.getNeighbours();
        for (Node nei : neighbour) {
            NettleSweeper.knowledgemap[nei.getX()][nei.getY()] = (char) (nei.getState() + 48);
        }

    }

    /**
     * Method to get position of unexplored cell.
     * 
     * @return Return an array list of integer array that indicates the location
     */
    public static ArrayList<int[]> getUncovered() {
        ArrayList<int[]> uncovered = new ArrayList<>();
        for (int i = 0; i < NettleSweeper.knowledgemap.length; i++) {
            for (int j = 0; j < NettleSweeper.knowledgemap[i].length; j++) {
                if (NettleSweeper.knowledgemap[i][j] == 'X') {
                    int[] location = { i, j };
                    uncovered.add(location);
                }
            }
        }
        return uncovered;
    }

    /**
     * Method to generate a random number for random guessing/. the number is
     * between 0 to number of unknown cells
     * 
     * @param uncovered
     *            Array list of unexplored cells' location
     * @return Return a random integer
     */
    public static int getRandom(ArrayList<int[]> uncovered) {
        return (int) (Math.random() * uncovered.size());
    }

    /**
     * Method to set neighbours retrieving from game map.
     * 
     * @param n
     *            The node to set its neighbour
     * @param map
     *            The game map
     */
    public static void setNeighbours(Node n, int[][] map) {
        if (n.getX() - 1 >= 0) {
            n.uNeighbour = new Node(n.getX() - 1, n.getY(), map[n.getX() - 1][n.getY()]);
            if (n.getY() - 1 >= 0) {
                n.luNeighbour = new Node(n.getX() - 1, n.getY() - 1, map[n.getX() - 1][n.getY() - 1]);
            }
        }
        if (n.getX() + 1 < NettleSweeper.mapSize) {
            n.dNeighbour = new Node(n.getX() + 1, n.getY(), map[n.getX() + 1][n.getY()]);
            if (n.getY() - 1 >= 0) {
                n.ldNeighbour = new Node(n.getX() + 1, n.getY() - 1, map[n.getX() + 1][n.getY() - 1]);
            }
        }
        if (n.getY() + 1 < NettleSweeper.mapSize) {
            n.rNeighbour = new Node(n.getX(), n.getY() + 1, map[n.getX()][n.getY() + 1]);
            if (n.getX() - 1 >= 0) {
                n.ruNeighbour = new Node(n.getX() - 1, n.getY() + 1, map[n.getX() - 1][n.getY() + 1]);
            }
            if (n.getX() + 1 < NettleSweeper.mapSize) {
                n.rdNeighbour = new Node(n.getX() + 1, n.getY() + 1, map[n.getX() + 1][n.getY() + 1]);
            }
        }
        if (n.getY() - 1 >= 0) {
            n.lNeighbour = new Node(n.getX(), n.getY() - 1, map[n.getX()][n.getY() - 1]);
        }
    }

    /**
     * Method to set neighbours for node from knowledge base.
     * 
     * @param n
     *            Node to find neighbours
     * @param map
     *            Knowledge base map
     */
    public static void setKBNeighbours(Node n, char[][] map) {
        // TODO Auto-generated method stub
        if (n.getX() - 1 >= 0) {
            n.uNeighbour = new Node(n.getX() - 1, n.getY(), map[n.getX() - 1][n.getY()]);
            if (n.getY() - 1 >= 0) {
                n.luNeighbour = new Node(n.getX() - 1, n.getY() - 1, map[n.getX() - 1][n.getY() - 1]);
            }
        }
        if (n.getX() + 1 < NettleSweeper.mapSize) {
            n.dNeighbour = new Node(n.getX() + 1, n.getY(), map[n.getX() + 1][n.getY()]);
            if (n.getY() - 1 >= 0) {
                n.ldNeighbour = new Node(n.getX() + 1, n.getY() - 1, map[n.getX() + 1][n.getY() - 1]);
            }
        }
        if (n.getY() + 1 < NettleSweeper.mapSize) {
            n.rNeighbour = new Node(n.getX(), n.getY() + 1, map[n.getX()][n.getY() + 1]);
            if (n.getX() - 1 >= 0) {
                n.ruNeighbour = new Node(n.getX() - 1, n.getY() + 1, map[n.getX() - 1][n.getY() + 1]);
            }
            if (n.getX() + 1 < NettleSweeper.mapSize) {
                n.rdNeighbour = new Node(n.getX() + 1, n.getY() + 1, map[n.getX() + 1][n.getY() + 1]);
            }
        }
        if (n.getY() - 1 >= 0) {
            n.lNeighbour = new Node(n.getX(), n.getY() - 1, map[n.getX()][n.getY() - 1]);
        }
    }

    /**
     * Method to get uncleared cells.
     * 
     * @return Return an array list of uncleared cells.
     */
    public static ArrayList<Node> getUnclearedCell() {
        ArrayList<Node> uncleared = new ArrayList<>();
        for (int i = 0; i < NettleSweeper.knowledgemap.length; i++) {
            for (int j = 0; j < NettleSweeper.knowledgemap[i].length; j++) {
                Node current = new Node(i, j, NettleSweeper.knowledgemap[i][j]);
                if (current.getState() != Configurations.UNCOVER) {
                    setKBNeighbours(current, NettleSweeper.knowledgemap);
                    ArrayList<Node> curNeighbours = current.getNeighbours();
                    for (Node n : curNeighbours) {
                        if (n.getState() == Configurations.UNCOVER) {
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
