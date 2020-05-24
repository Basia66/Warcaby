import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Chessboard extends JPanel implements MouseListener {
    Game game;
    int y = 0;
    int x = 0;
    Pawn selectedPawn;
    ArrayList<Field> fields = new ArrayList<>();
    ArrayList<Pawn> pawns = new ArrayList<>();
    ArrayList<Field> availableFields = new ArrayList<>();
    ArrayList<Pawn> mandatoryPawns = new ArrayList<>();
    public Color turn = Config.pawnLight;
    public int turnCount = 0;

    public int killedLightPawns = 0;
    public int killedDarkPawns = 0;

    public Chessboard(Game game) {
        this.game = game;

        // creating chessboard
        int nx = x;
        int ny = y;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color col1 = (i % 2 == 0 ? Config.lightField : Config.darkField);
                Field field1 = new Field(nx, ny, i, j, Config.getFieldName(i, j), col1);
                nx = nx + Config.fieldWidth;
                j++;
                Color col2 = (i % 2 == 0 ? Config.darkField : Config.lightField);
                Field field2 = new Field(nx, ny, i, j, Config.getFieldName(i, j), col2);
                nx = nx + Config.fieldWidth;

                fields.add(field1);
                fields.add(field2);
            }
            nx = 0;
            ny = ny + Config.fieldHeight;
        }

        // creating pawns
        for (int i = 0; i < 24; i++) {
            if (fields.get(i).color == Config.darkField) {
                pawns.add(new Pawn(Config.pawnLight, fields.get(i)));
            }
        }

        for (int i = 40; i < 64; i++) {
            if (fields.get(i).color == Config.darkField) {
                pawns.add(new Pawn(Config.pawnDark, fields.get(i)));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Field field : fields)
            field.paint(g);

        //display available fields
        for (Field field : availableFields) {
            g.setColor(Config.crown);
            g.fillRect(field.x, field.y, Config.fieldWidth, Config.fieldHeight);
        }

        //display mandatory fields
        int pawnXShift = ((Config.fieldWidth - Config.pawnRadius) / 2) - 3;
        int pawnYShift = ((Config.fieldHeight - Config.pawnRadius) / 2) - 3;
        g.setColor(Config.lightField);
        for (Pawn pawn : mandatoryPawns) {
            g.fillOval(pawn.field.x + pawnXShift, pawn.field.y + pawnYShift, Config.pawnRadius + 6, Config.pawnRadius + 6);
        }

        //pawn
        for (Pawn p : pawns) {
            p.paint(g);
        }
    }

    private Field getFieldFromRowAndColumn(int r, int c) {
        if (r < 0 || c < 0 || r > 8 || c > 8)
            return null;

        for (Field field : fields) {
            if (field.row == r && field.column == c)
                return field;
        }

        return null;
    }

    private Field getFieldFromCoordinates(int x, int y) {
        int row = (int) Math.floor(y / (float) Config.fieldHeight);
        int column = (int) Math.floor(x / (float) Config.fieldWidth);
        return this.getFieldFromRowAndColumn(row, column);
    }

    private Field getFieldBetween(Field field1, Field field2) {
        Field field = getFieldFromRowAndColumn(
                (field1.row + field2.row) / 2,
                (field1.column + field2.column) / 2);
        return (field != null && !field.equals(field1) && !field.equals(field2)) ? field : null;
    }

    private void calculateAvailableFields(Field field, boolean onlyFights) {
        availableFields.clear();
        for (Field x : fields) {

            // wykluczenie pól z pionkami
            if (x.pawn != null)
                continue;

            // wykluczenie pół w pionie i poziomie
            if (field.color != x.color)
                continue;

            // jeśli pionek jest zwykły
            if (!field.pawn.isQueen) {
                // sprawdzanie czy pole x jest pod pionkiem
                if (field.pawn.defaultColor == Config.pawnDark && field.row < x.row)
                    continue; // ominięcie pola x

                    // sprawdzanie czy pole x jest nad pionkiem
                else if (field.pawn.defaultColor == Config.pawnLight && field.row > x.row)
                    continue; // ominięcie pola x
            }

            // sprawdzenie możliwości bicia
            if (field.getDistanceBetween(x) == 2) {
                Field center = getFieldBetween(field, x);
                // jeśli nie ma pola lub nie ma na nim pionka
                if (center == null || center.pawn == null)
                    continue;

                // zgodność kolorów do bicia
                if (center.pawn.defaultColor == field.pawn.defaultColor)
                    continue;

                onlyFights = true;
            }
            // wykluczenie pozostałych pól z inną odległością
            else if (field.getDistanceBetween(x) != 1)
                continue;

            availableFields.add(x);
        }
        if (onlyFights) {
            ArrayList<Field> moves = new ArrayList<>();
            availableFields.stream()
                    .filter(x -> field.getDistanceBetween(x) == 1)
                    .forEach(moves::add);
            availableFields.removeAll(moves);
        }
    }

    private void calculateMandatoryPawns() {
        mandatoryPawns.clear();
        for (Pawn pawn : pawns) {
            if (pawn.defaultColor == turn) {
                availableFields.clear();
                calculateAvailableFields(pawn.field, true);
                if (!availableFields.isEmpty()) {
                    mandatoryPawns.add(pawn);
                    availableFields.clear();
                }
            }
        }
    }

    private boolean destroyBetween(Field field1, Field field2) {
        Field field = getFieldBetween(field1, field2);
        if (field != null) {
            boolean isAnyDeleted = pawns.remove(field.pawn);
            if(isAnyDeleted) {
                if(field.pawn.defaultColor == Config.pawnLight)
                    killedLightPawns++;
                else if(field.pawn.defaultColor == Config.pawnDark)
                    killedDarkPawns++;
            }
            field.pawn = null;
            return isAnyDeleted;
        }
        return false;
    }

    private void endTurn() {
        turn = (turn == Config.pawnLight ? Config.pawnDark : Config.pawnLight);
        turnCount++;

        calculateMandatoryPawns();
        repaint();
        game.repaintUI();

        if (checkEndGameCondition()) {
            game.endGame();
        }
    }

    private boolean checkEndGameCondition() {
        int a = 0;
        int b = 0;
        for (Pawn pawn : pawns) {
            if (Config.pawnLight.equals(pawn.defaultColor)) {
                if (b > 0)
                    return false;

                a++;
            } else if (Config.pawnDark.equals(pawn.defaultColor)) {
                if (a > 0)
                    return false;

                b++;
            }
        }

        return a == 0 || b == 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Field clickedField = getFieldFromCoordinates(e.getX(), e.getY());
        System.out.println(clickedField.name);

        // sprawdzenie czy rusza się dobry kolor
        if (clickedField.pawn != null && clickedField.pawn.defaultColor != turn)
            return;

        if (selectedPawn != null) {
            // klikneliśmy w dostępne pole
            if (availableFields.contains(clickedField)) {
                if (destroyBetween(clickedField, selectedPawn.field)) {
                    selectedPawn.jumpToField(clickedField);
                    calculateAvailableFields(clickedField, true);
                    if (availableFields.isEmpty()) {
                        endTurn();
                    } else {
                        calculateMandatoryPawns();
                    }
                } else {
                    selectedPawn.jumpToField(clickedField);
                    endTurn();
                }
            }
            selectedPawn.unSelect();
            availableFields.clear();
            selectedPawn = null;
            repaint();
            return;
        }

        // sprawdzenie wymuszonego bicia
        if (!mandatoryPawns.isEmpty() && !mandatoryPawns.contains(clickedField.pawn))
            return;

        for (Pawn pawn : pawns) {
            if (pawn.field.equals(clickedField)) {
                selectedPawn = pawn;
                selectedPawn.select();
                calculateAvailableFields(clickedField, false);
                repaint();
                return;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
