import javax.swing.*;
import java.awt.*;

public class GameBottom extends JPanel {
    Game game;
    Chessboard chessboard;

    Square turaNumber;
    Square graczColor;

    Square pawnLightNumber;
    Square pawnDarkNumber;

    public GameBottom(Game game, Chessboard chessboard) {
        this.game = game;
        this.chessboard = chessboard;

        this.setPreferredSize(new Dimension(400, 100));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton gracz = new JButton("PLAYER");
        gracz.setEnabled(false);
        graczColor = new Square(chessboard.turn, "", 30, 30, 0,0);
        graczColor.setAlignmentX(50);
        gracz.add(graczColor);
        gracz.setFont(new Font("Consolas", Font.BOLD, 20));
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        this.add(gracz, c);

        JButton tura = new JButton("ROUND");
        tura.setEnabled(false);
        tura.setFont(new Font("Consolas", Font.BOLD, 20));
        turaNumber = new Square(Color.gray, fillWithZeros(chessboard.turnCount), 30, 30, 4, 21);
        turaNumber.setAlignmentX(-50);
        tura.add(turaNumber);
        c.gridx = 3;
        c.gridwidth = 3;
        c.gridy = 0;
        this.add(tura, c);

        JButton newGame = new JButton("NEW GAME");
        newGame.addActionListener(x -> game.newGame());
        newGame.setFont(new Font("Consolas", Font.BOLD, 20));
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 1;
        this.add(newGame, c);

        Square pawnLightColor = new Square(Config.pawnLight, "", 24, 24, 0, 0);
        pawnLightColor.setAlignmentX(25);
        pawnLightNumber = new Square(Color.gray, fillWithZeros(chessboard.killedLightPawns), 35, 24, 3, 18);
        pawnLightNumber.setAlignmentX(-25);

        Square pawnDarkColor = new Square(Config.pawnDark, "", 24, 24, 0, 0);
        pawnDarkColor.setAlignmentX(-25);
        pawnDarkNumber = new Square(Color.gray, fillWithZeros(chessboard.killedDarkPawns), 35, 24, 3, 18);
        pawnDarkNumber.setAlignmentX(25);

        JButton pawnLightCounterButton = new JButton();
        pawnLightCounterButton.setEnabled(false);
        pawnLightCounterButton.add(pawnLightColor);
        pawnLightCounterButton.add(pawnLightNumber);
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 1;
        this.add(pawnLightCounterButton, c);

        JButton pawnDarkCounterButton = new JButton();
        pawnDarkCounterButton.setEnabled(false);
        pawnDarkCounterButton.add(pawnDarkColor);
        pawnDarkCounterButton.add(pawnDarkNumber);
        c.gridx = 3;
        c.gridwidth = 1;
        c.gridy = 1;
        this.add(pawnDarkCounterButton, c);

        JButton rules = new JButton("RULES");
        rules.addActionListener(x -> game.showRules());
        rules.setFont(new Font("Consolas", Font.BOLD, 20));
        c.gridx = 4;
        c.gridwidth = 1;
        c.gridy = 1;
        this.add(rules, c);

        JButton exit = new JButton("EXIT");
        exit.addActionListener(x -> game.exit());
        exit.setFont(new Font("Consolas", Font.BOLD, 20));
        c.gridx = 5;
        c.gridwidth = 1;
        c.gridy = 1;
        this.add(exit, c);
    }

    public void repaintUI() {
        turaNumber.content = fillWithZeros(chessboard.turnCount);
        turaNumber.repaint();

        graczColor.color = chessboard.turn;
        graczColor.repaint();

        pawnLightNumber.content = fillWithZeros(chessboard.killedLightPawns);
        pawnLightNumber.repaint();

        pawnDarkNumber.content = fillWithZeros(chessboard.killedDarkPawns);
        pawnDarkNumber.repaint();
    }

    private String fillWithZeros(int number) {
        if (number > 99)
            return String.valueOf(99);
        else if (number >= 10)
            return String.valueOf(number);
        else
            return "0" + number;
    }
}
