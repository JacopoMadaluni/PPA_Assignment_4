import javax.swing.*;
import java.util.ArrayList;

public abstract class StatsCentralPanel extends JPanel {
    String name;
    Boolean assigned = false;


    public StatsCentralPanel(){
        this.name = getClass().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isAssigned(){
        return assigned;
    }


    public void setAssigned(boolean value){
        assigned = value;
    }
}
