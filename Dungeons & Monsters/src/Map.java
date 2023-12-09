import java.util.Scanner;
import java.awt.Point;
import java.lang.*;
import java.io.*;

public class Map {
    private char[][] map;
    private boolean[][] revealed;
    private static Map instance = null;

    /**
     * Constructor for char map (room types) and boolean map (rooms visited), both of size: 5 x 5
     */
    private Map() {
        map = new char[5][5];
        revealed = new boolean[5][5];
    }

    /**
     * Singleton method to ensure only one instance of Map exists
     * @return singular instance of Map to be used
     */
    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    /**
     * Resets boolean array, Reads map from desired file and stores values in char array
     * @param mapNum number of desired map to load (0-2)
     */
    public void loadMap(int mapNum) {

        // Resetting boolean array
        revealed = new boolean[][]{{false, false, false, false, false},
                                    {false, false, false, false, false},
                                    {false, false, false, false, false},
                                    {false, false, false, false, false},
                                    {false, false, false, false, false}};

        // Assign file name to desired map file (1 = Map1, 2 = Map2, 0 = Map3)
        String mapFile = "";
        if (mapNum == 1) {
            mapFile = "Map1.txt";
        } else if (mapNum == 2) {
            mapFile = "Map2.txt";
        } else if (mapNum == 0) {
            mapFile = "Map3.txt";
        }

        // Read in each character and copy into char array
        try {
            Scanner read = new Scanner(new File(mapFile));
            int i = 0;
            int j = 0;
            while (read.hasNext()) {
                String nextString = read.next();
                char nextChar = nextString.charAt(0);
                map[i][j] = nextChar;
                if (j == map[0].length - 1) {
                    i++;
                    j = 0;
                } else {
                    j++;
                }
            }
            read.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found.");
        }
    }

    /**
     * Returns the character at location p on the map
     * @param p specified point on the map
     * @return the character stored at that point
     */
    public char getCharAtLoc(Point p) {
        return map[(int) p.getX()][(int) p.getY()];
    }

    /**
     * Finds the designated starting point for the player on the map
     * @return the point where the player must start from
     */
    public Point findStart() {
        Point start = new Point(0, 0);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                Character currentChar = map[i][j];
                if (currentChar.equals('s')) {
                    start = new Point(i, j);
                }
            }
        }
        return start;
    }

    /**
     * "Reveals" a room by setting corresponding boolean to "true"
     * @param p point of room to be revealed
     */
    public void reveal(Point p) {
        revealed[(int) p.getX()][(int) p.getY()] = true;
    }

    /**
     * "Removes" a room by settings corresponding char to 'n' (i.e., nothing here)
     * @param p point of room to be removed
     */
    public void removeCharAtLoc(Point p) {
        map[(int) p.getX()][(int) p.getY()] = 'n';
    }

    /**
     * Display map with hero's current position (*), revealed rooms (char at point), and unrevealed rooms (x)
     * @param p current position of hero
     * @return string to display map with characters as described above
     */
    public String mapToString(Point p) {

        // Create a combined char array that contains hero's position and revealed and unrevealed rooms
        char[][] combinedMap = new char[map.length][map[0].length];
        for (int i = 0; i < combinedMap.length; i++) {
            for (int j = 0; j < combinedMap[0].length; j++) {
                if ((i == p.getX()) && (j == p.getY())) {
                    combinedMap[i][j] = '*';
                } else if (revealed[i][j]) {
                    combinedMap[i][j] = map[i][j];
                } else {
                    combinedMap[i][j] = 'x';
                }
            }
        }

        // Convert char array to string with proper formatting
        String mapString = "";
        for (int i = 0; i < combinedMap.length; i++) {
            for (int j = 0; j < combinedMap[0].length; j++) {
                mapString += combinedMap[i][j];
                if (j == combinedMap[0].length - 1) {
                    mapString += "\n";
                } else {
                    mapString += " ";
                }
            }
        }
        return mapString;
    }
}
