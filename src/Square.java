import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {
    Color color;
    String content;
    int width;
    int height;
    int xOffset;
    int yOffset;

    public Square(Color color, String content, int width, int height, int xOffset, int yOffset) {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));

        this.color = color;
        this.content = content;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        if (content.equals("")) {
            g.fillRect(0, 0, width, height);
        } else {
            g.setFont(new Font("Consolas", Font.BOLD, 20));
            g.drawString(content, xOffset, yOffset);
//            g.drawString(content, 3, 18);
        }
    }
}
