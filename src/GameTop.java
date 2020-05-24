import javax.swing.*;
import java.awt.*;

public class GameTop extends JPanel {
    public GameTop(){
        this.setPreferredSize(new Dimension(400,100));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(new Font("Consolas",Font.BOLD,60));
        g.drawString("W A R C A B Y",35,60);

        g.setFont(new Font("Consolas",Font.BOLD,30));
        g.drawString("A  B  C  D  E  F  G  H",60,90);
    }
}
