package PropertyFinder;
import PropertyFinder.Map.BnbTable;
import javax.swing.*;
import java.awt.*;

public class Comparator extends AppPanel {

    public Comparator(MainWindow mainWindow){
        super("PropertyFinder.Comparator",MainWindow.getMyList(),0,0);
        listings = mainWindow.getMyList();
        JLabel status = new JLabel(listings.isEmpty()? "No properties in My List, please add them in the" +
                " single property view or, if you have done so already, press the 'refresh button'":"You have "+listings.size()+(listings.size() > 1?" properties":" property"));
        JButton viewList = new JButton("View List");
        viewList.setEnabled(!listings.isEmpty());
        viewList.addActionListener((e)->{
            BnbTable table = new BnbTable(listings);
            table.displayBnbList();
        });
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener((e)->{
            System.out.println("Done");
            mainWindow.highPriceClicked();
        });
        JButton compare = new JButton("Compare properties");
        compare.setVisible(!listings.isEmpty());
        compare.addActionListener((e)->{
            setLabels();
        });
        add(refresh,BorderLayout.AFTER_LAST_LINE);
        add(status);
        add(viewList);
    }

    public void setLabels(){
        String [] messages = {"The cheaptest property is: ",""};
    }

}
