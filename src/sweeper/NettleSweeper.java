package sweeper;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Main class represents the game agent.
 * 
 * @author bl41
 *
 */
public class NettleSweeper {

    public static char[][] knowledgeBase1 = { { 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };

    public static char[][] knowledgeBase2 = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

    public static char[][] knowledgeBase3 = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

    public static int[][] map;
    public static char[][] knowledgemap;
    public static boolean correctDeduce = false;

    public static int mapSize;
    public static int nettlenum;
    public static Scanner sc = new Scanner(System.in);

    /**
     * Method to show the current status of knowledge base.
     */
    public static void printKB() {
        for (char[] row : knowledgemap) {
            System.out.println(Arrays.toString(row));
        }
    }

    /**
     * Main method of the project.
     * 
     * @param args
     *            Arguments
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        int l = selectLevel();
        int n = selectMap();
        switch (l) {
        case 1:
            map = EasyMap.getMap(n);
            knowledgemap = knowledgeBase1;
            mapSize = 5;
            nettlenum = 5;
            break;

        case 2:
            map = MediumMap.getMap(n);
            knowledgemap = knowledgeBase2;
            mapSize = 9;
            nettlenum = 10;
            break;

        case 3:
            map = HardMap.getMap(n);
            knowledgemap = knowledgeBase3;
            mapSize = 10;
            nettlenum = 20;
            break;

        default:
            System.out.println("Invalid Selection");
            System.exit(0);
            break;
        }

        // Deduce the reachable zero cell
        Node start = new Node(0, 0, map[0][0]);
        System.out.println("Start " + Arrays.toString(start.getLocation()));
        Game.deduce(start);

        // Explore the map
        while (nettlenum > 0) {
            if (!Strategy.SPS() && !Strategy.EES()) {
                Strategy.RGS();
            }
        }
        // If all nettles have been found, tell user game win
        System.out.println("You Win!!!");
    }

    /**
     * Method to select level.
     * 
     * @return Return level number
     */
    public static int selectLevel() {
        int lvl = 0;
        System.out.println("Please Select Level(1, 2, 3) ");
        System.out.println("1. Easy: 5 * 5 with 5 nettles");
        System.out.println("2. Medium: 9 * 9 with 10 nettles");
        System.out.println("3. Hard: 10 * 10 with 20 nettles");
        lvl = sc.nextInt();
        return lvl;
    }

    /**
     * Method to select map.
     * 
     * @return Return map number
     */
    public static int selectMap() {
        int num = 0;
        System.out.println("Please Select Map Number (1 ~ 5)");
        num = sc.nextInt();
        return num;
    }

}
