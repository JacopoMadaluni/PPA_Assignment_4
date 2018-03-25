import javax.swing.*;
import java.awt.*;

public class Comparator extends AppPanel {

    public Comparator(){
        super("Comparator",Main.getMyList(),0,0);

        JLabel status = new JLabel(listings.isEmpty()? "No properties in My List, please add them in the" +
                " single property view":"You have "+listings.size()+" properties");
        JButton viewList = new JButton("View List");
        viewList.setEnabled(!listings.isEmpty());
        viewList.addActionListener((e)->{
            BnbTable table = new BnbTable(listings);
            table.displayBnbList();
        });
        add(status);
        add(viewList);

    }

}
