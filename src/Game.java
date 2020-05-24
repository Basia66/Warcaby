import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Game {
    JFrame frame;
    GameTop top;
    GameBottom bottom;
    GameWest west;

    public Game() {
       init();
    }

    private void init() {
        frame = new JFrame("Warcaby");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Chessboard chessboard = new Chessboard(this);
        chessboard.addMouseListener(chessboard);
        chessboard.setPreferredSize(new Dimension(400, 400));

        BorderLayout bL = new BorderLayout();
        bottom = new GameBottom(this, chessboard);
        top = new GameTop();
        west = new GameWest();
        JLabel east = new JLabel();
        east.setPreferredSize(new Dimension(50,400));

        bL.preferredLayoutSize(frame);
        frame.setLayout(bL);

        frame.add(top);
        frame.add(bottom);
        frame.add(east);
        frame.add(west);
        frame.add(chessboard);

        bL.addLayoutComponent(top,BorderLayout.NORTH);
        bL.addLayoutComponent(bottom,BorderLayout.SOUTH);
        bL.addLayoutComponent(east,BorderLayout.EAST);
        bL.addLayoutComponent(west,BorderLayout.WEST);
        bL.addLayoutComponent(chessboard,BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void repaintUI() {
        bottom.repaintUI();
    }

    public void endGame() {
        JOptionPane.showMessageDialog(frame, "Wygrałeś! chyba ...", "Wygrana", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showRules() {
        String mess = "WARCABY:\n" +
                "Plansza: 8 x 8 pól. \n" +
                "Pionki: 12x zielonych i 12x czerwonych. \n" +
                "Gra rozgrywana jest na ciemnych polach planszy.\n" +
                "Jest dwóch graczy: Gracz z czerwonymi pionkami i Gracz z zielonymi pionkami.\n" +
                "Cel gry: zbicie wszystkich pionków przeciwnika.\n" +
                "\n" +
                "ROZGRYWKA:\n" +
                "Zaczyna Gracz zielony.\n" +
                "Pionki mogą poruszać się o jedno pole po przekątnej do przodu.\n" +
                "Bicie pionków przeskoczenie przez pionek przeciwnika na puste pole po przekątnej.\n" +
                "Bicie pionków może odbywać się tylko do przodu.\n" +
                "Po zbiciu pionka, pionek zostaje usunięty.\n" +
                "Po dotarciu pionka do ostatniego rzędu po stronie przeciwnika staję się \"damą\".\n" +
                "Dama : pionek danego koloru ze jasnym niebieskim kółkiem w środku. \n" +
                "Dama może poruszać się o jedno pole po przekątnej do przodu i do tyłu.\n" +
                "Bicie damką może odbywać się do przodu i do tyłu.";
        JOptionPane.showMessageDialog(frame, mess, "Zasady", JOptionPane.INFORMATION_MESSAGE);
    }

    public void newGame() {
        frame.dispose();
        init();
    }

    public void exit() {
        System.exit(0);
    }
}
