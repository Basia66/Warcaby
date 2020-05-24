import javax.swing.*;
import java.awt.*;

public class GameWest extends JPanel {
    public GameWest(){
        this.setPreferredSize(new Dimension(50,400));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        String[] numbers = {"1","2","3","4","5","6","7","8"};
                g.setFont(new Font("Consolas",Font.BOLD,30));

        for (int i = 0; i < 8; i++ ){
            g.drawString(numbers[i],15,(i+1)*50-15);
        }
    }
}
