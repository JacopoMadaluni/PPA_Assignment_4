package Gui;

public class LocalMain {

    public static void main(String[] args){
        GuiTestFrame gui = new GuiTestFrame();
        try{
            Thread.sleep(1000);
        }catch(Exception e) {}
        gui.drawIconTest();
    }
}
