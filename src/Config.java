import java.awt.*;

public class Config {
    public static final String[] hID = {"1", "2", "3", "4", "5", "6", "7", "8"};
    public static final String[] vID = {"A", "B", "C", "D", "E", "F", "G", "H"};
    public static final int fieldWidth = 50;
    public static final int fieldHeight = 50;
    public static final int pawnRadius = 35;
    public static final Color lightField = new Color(201, 218, 234);
    public static final Color darkField = new Color(25, 21, 22);
    public static final Color pawnLight = new Color(0, 178, 149);
    public static final Color pawnDark = new Color(171, 35, 70);
    public static final Color crown = new Color(3, 247, 235);
    public static final Color gold = Color.yellow;

    public static String getFieldName(int r, int c) {
        return Config.vID[c] + Config.hID[r];
    }
}
