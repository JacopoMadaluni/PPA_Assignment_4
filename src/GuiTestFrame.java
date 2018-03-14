import java.awt.*;
import javax.swing.*;
/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GuiTestFrame
{
    // instance variables - replace the example below with your own
    private JFrame frame;
    private Map map;

    public GuiTestFrame(){
        makeFrame();
    }

    public void makeFrame(){
        frame = new JFrame("test");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        map = null;
        try{
            map = new Map();

        }catch(Exception e){
            System.err.println("che dio ci aiuti");
        }
        frame.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        contentPane.add(map, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

    }

    public void drawIconTest(){
        map.drawIcon(map.getGraphics());
    }
}