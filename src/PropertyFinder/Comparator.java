package PropertyFinder;
import PropertyFinder.Map.ComparatorTable;
import javax.swing.*;
import java.awt.*;

public class Comparator extends AppPanel {

    public Comparator(MainWindow mainWindow){
        super("PropertyFinder.Comparator",MainWindow.getMyList(),0,0);
        listings = mainWindow.getMyList();
        JTextArea status = new JTextArea(listings.isEmpty()? "No properties in My List, please add them in the" +
                " single property view.\nIf you have done so already, press the 'refresh button'":"You have "+listings.size()+(listings.size() > 1?" properties":" property"));
        status.setFont(new Font("Arial",Font.PLAIN,18));
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener((e)->{
            System.out.println("Done");
            mainWindow.highPriceClicked();
        });
        JButton compare = new JButton("Compare properties");
        compare.setVisible(!listings.isEmpty());
        compare.addActionListener((e)->{
            try {
                JScrollPane comparison = new JScrollPane(new ComparatorTable(listings).makeTable());
                add(comparison);
                compare.setVisible(false);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        compare.setSize(100,25);
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        buttons.add(compare);
        buttons.add(refresh);
        add(buttons,BorderLayout.AFTER_LAST_LINE);
        add(status,BorderLayout.PAGE_START);
    }



}
