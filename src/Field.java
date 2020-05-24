import java.awt.*;
import java.util.Objects;

public class Field {
    int x;
    int y;
    /**
     * 1-8
     */
    int row;
    /**
     * A-H
     */
    int column;
    String name;
    Color color;
    Pawn pawn;

    public Field(int x, int y, int row, int column, String name, Color color) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
        this.name = name;
        this.color = color;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, Config.fieldWidth, Config.fieldHeight);
    }

    public int getDistanceBetween(Field field) {
        return (int) Math.sqrt(Math.pow(row - field.row, 2) + Math.pow(column - field.column, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return x == field.x &&
                y == field.y &&
                row == field.row &&
                column == field.column &&
                Objects.equals(name, field.name) &&
                Objects.equals(color, field.color);
    }
}
