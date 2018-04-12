

import javax.swing.JButton;

public class GameModel {
    
    public static final int EASY = 1,NORMAL = 2,HARD = 3; // indexes for levels
    private int currentLevel = EASY; // give current level
    
    
    private int currentscore = 0;
    // to store the buttons in gaming panel
    private JButton [][]btnSet=new JButton[2][10];
    // for confirm button
    private JButton confirmbtn;
    
    public GameModel() {
     
    }
    // getters and setters
    public void setBtnSet(JButton[][] btnSet) {
        this.btnSet = btnSet;
    }

    public void setConfirmbtn(JButton confirmbtn) {
        this.confirmbtn = confirmbtn;
    }

   
    public int getCurrentLevel() {
        return currentLevel;
    }


    public int getCurrentscore() {
        return currentscore;
    }

    public JButton[][] getBtnSet() {
        return btnSet;
    }

    public JButton getConfirmbtn() {
        return confirmbtn;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setCurrentscore(int currentscore) {
        this.currentscore = currentscore;
    }
  
}
