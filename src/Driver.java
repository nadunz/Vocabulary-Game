
import javax.swing.UIManager;

public class Driver {
    
    public static void main(String[] args){
       
        // give nimbus look and feel to the gui
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();// exception-print
        }
       
         
        FindWordGUI view = new FindWordGUI();// view the GUI
        GameModel model=new GameModel();
        //start  game controller
        GameController gameController = new GameController(model,view);
        // set data to the model
        gameController.setBtnToModel(view.getBtnSet());
        gameController.setConfirmBtnToModel(view.getConfirmbtn());
        
        
    }
    
}
