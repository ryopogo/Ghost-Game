import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {

    public MyPanel(){

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        try {
            Thread.sleep(30);
        }catch(InterruptedException e){
            System.out.println(e);
        }

    }
}
