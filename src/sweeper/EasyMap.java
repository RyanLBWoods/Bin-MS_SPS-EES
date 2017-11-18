package sweeper;

/**
 * Maps of easy levels.
 * 
 * @author bl41
 *
 */
public class EasyMap {

    private static int[][] nworld1 = new int[][] { { 0, 0, 0, 2, -1 }, { 0, 0, 0, 2, -1 }, { 1, 2, 1, 2, 1 },
            { -1, 3, -1, 2, 0 }, { 1, 3, -1, 2, 0 }, };

    private static int[][] nworld2 = new int[][] { { 0, 0, 1, -1, -1 }, { 0, 0, 1, 2, 2 }, { 1, 2, 1, 1, 0 },
            { -1, 2, -1, 2, 1 }, { 1, 2, 2, -1, 1 }, };

    private static int[][] nworld3 = new int[][] { { 0, 0, 1, 2, 2 }, { 0, 1, 3, -1, -1 }, { 0, 2, -1, -1, 3 },
            { 0, 2, -1, 3, 1 }, { 0, 1, 1, 1, 0 }, };

    private static int[][] nworld4 = new int[][] { { 0, 0, 0, 0, 0 }, { 1, 2, 1, 1, 0 }, { -1, 2, -1, 1, 0 },
            { 3, 5, 3, 2, 0 }, { -1, -1, -1, 1, 0 }, };

    private static int[][] nworld5 = new int[][] { { 0, 0, 0, 1, -1 }, { 1, 1, 1, 1, 1 }, { 2, -1, 2, 0, 0 },
            { 3, -1, 3, 1, 0 }, { -1, 3, -1, 1, 0 }, };

    /**
     * Method to get map according to user.
     * 
     * @param num
     *            Number input by user
     * @return Return the map
     */
    public static int[][] getMap(int num) {
        switch (num) {
        case 1:
            return nworld1;

        case 2:
            return nworld2;

        case 3:
            return nworld3;

        case 4:
            return nworld4;

        case 5:
            return nworld5;

        default:
            System.out.println("Invalid number");
            return null;
        }
    }
}
