import javax.swing.*;
import java.awt.*;

public class Comparator {
    private JFrame frame;

    public Comparator(){
        frame = new JFrame("AirBnb Comparator");
        Container container = frame.getContentPane();
        JLabel status = new JLabel(Main.getMyList().isEmpty()? "No properties in My List, please add them in the" +
                "single property view":"You have "+Main.getMyList().size()+" properties");
        JButton viewList = new JButton("View List");
        viewList.setEnabled(!Main.getMyList().isEmpty());
        viewList.addActionListener((e)->{
            BnbTable table = new BnbTable(Main.getMyList());
            table.displayBnbList();
        });
    }


}
