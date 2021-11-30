import java.awt.*;
import javax.swing.*;

class MyCanvas extends JComponent {
    int x1;
    int y1;
    int x2;
    int y2;
    MyCanvas(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public void paint(Graphics g)
    {
        g.drawLine(x1, y1, x2, y2);
    }
}

public class GFG1 {
    public static void main(String[] a)
    {

        // creating object of JFrame(Window popup) 
        JFrame window = new JFrame();

        // setting closing operation 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setting size of the pop window 
        window.setBounds(30, 30, 1000, 800);

        // setting canvas for draw 
        window.getContentPane().add(new MyCanvas(0,0 ,200,200));

        // set visibilit
        window.setVisible(true);
    }
} 