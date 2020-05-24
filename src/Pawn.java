import java.awt.*;

public class Pawn {
    Color defaultColor;
    Color color;
    Field field;
    boolean isQueen = false;
    boolean isSelected = false;

    public Pawn(Color color, Field field) {
        this.color = this.defaultColor = color;
        this.field = field;
        this.field.pawn = this;
    }

    public void paint(Graphics g) {
        int drawX = field.x + ((Config.fieldWidth - Config.pawnRadius) / 2);
        int drawY = field.y + ((Config.fieldHeight - Config.pawnRadius) / 2);

        g.setColor(color);
        g.fillOval(drawX, drawY, Config.pawnRadius, Config.pawnRadius);
        if (isQueen) {
            g.setColor(Config.crown);
            g.fillOval(drawX + 10, drawY + 10, Config.pawnRadius - 20, Config.pawnRadius - 20);
        }
    }

    public void jumpToField(Field field) {
        this.field.pawn = null;
        this.field = field;
        this.field.pawn = this;

        if ((this.defaultColor == Config.pawnLight && field.row == 7) ||
                (this.defaultColor == Config.pawnDark && field.row == 0)) {
            setQueen();
        }
    }

    public void setQueen() {
        isQueen = true;
    }

    public void unSelect() {
        isSelected = false;
        color = defaultColor;
    }

    public void select() {
        isSelected = true;
        color = Config.gold;
    }
}
