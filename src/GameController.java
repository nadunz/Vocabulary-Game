

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;


@SuppressWarnings("unchecked")
public class GameController implements ActionListener {
    
    private static GameModel model;
    private static FindWordGUI view;
    private static int word_size;
    private static int[] buttonSelectedState;
    public static String word;
    public static String shuffledWord;
    static HashMap<Integer, String> wordlist; 
    private static int wordsCount;
    private static ArrayList<Integer> usedWordIndexes = new ArrayList<Integer>();
    private Timer t;
    private int count= 30;
    private boolean timerStatus=false;
    private String time="30";
    
    public GameController(GameModel model, FindWordGUI view) {
        this.model = model;
        this.view = view;
        // store the map first
        storeMap("../words.csv");
        word_size= selectWord(model.getCurrentLevel());
        // create buttons in the gui
        view.addGamingLevelButtons(word_size);
        // set letters
        view.setLetter();
    }
   
    public GameController() {
        
    }
    // set data to model
    public void setBtnToModel(JButton [][]btns){
        model.setBtnSet(btns);
    }
    
    public void setConfirmBtnToModel(JButton button){
        model.setConfirmbtn(button);
    }
   //
   
    // getters
    public static GameModel getModel() {
        return model;
    }

    public static FindWordGUI getView() {
        return view;
    }
    //
    // reset the timer
    public void timerSetDefault(){
        count = 30;
        // set the letters color of timer
        view.getClock().setForeground(Color.WHITE);
        view.setTime(time);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o =e.getSource();
        // if click minimize button
        if(o==view.getMinimize()){
            view.getFrame().setState(JFrame.ICONIFIED);
            
            return;
        }
        // if exiting
        if(o==view.getExit()){
            
            System.exit(0);
            return;
        }
        // if click clear button
        if(o==view.getClearbtn()){
            view.clear();
            
            for(int i=0;i<word_size;i++){
                buttonSelectedState[i]=0;
                model.getBtnSet()[1][i].setBackground(Color.BLACK);
            }
            
            return;
        }
        // if click confirm button
        if(o==view.getConfirmbtn()){
            String answer=getUserInputWord();
            // unless timer is stop
            if(!timerStatus){

                JOptionPane.showMessageDialog(null, "First start the game!", "",JOptionPane.INFORMATION_MESSAGE,null);

            }else{//if timer is started
                if(answer.equals(word)){
                // goto next attempt of current level 
                
                t.stop();// stop the timer
                timerStatus = false;
                // score calculating
                addScore(model.getCurrentLevel());
                // set the score to the gui
                view.setScore(model.getCurrentscore()+"");
                String message=decideMessage(model.getCurrentLevel(), model.getCurrentscore());
                
                // give error message to the user
                JOptionPane.showMessageDialog(null, message, "",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("../images/Happy.png"));
                // reset the timer
                timerSetDefault();
                // start the timer
                t.start();
                timerStatus=true;
                //according to the current score decide the update of game
                if(model.getCurrentscore()==50){
                    usedWordIndexes.clear();
                    model.setCurrentLevel(model.NORMAL);
                    view.setLevelNotification(model.getCurrentLevel());
                }
                if(model.getCurrentscore()==150){
                    usedWordIndexes.clear();
                    model.setCurrentLevel(model.HARD);
                    view.setLevelNotification(model.getCurrentLevel());
                }
                if(model.getCurrentscore()==300){
                    usedWordIndexes.clear();
                    view.newGame();
                    model.setCurrentLevel(model.EASY);
                    model.setCurrentscore(0);
                    
                    for(int i=0;i<word_size;i++){
                        model.getBtnSet()[1][i].setBackground(Color.BLACK);
                    }
                }
              //  update the word length of the programm 
                word_size= selectWord(model.getCurrentLevel());
                //get the the jpanel 
                JPanel gamingPanelLevel = view.getGamingPanelLevel();
                // remove all components of the gamming panel
                gamingPanelLevel.removeAll();
                view.getFrame().remove(gamingPanelLevel);
                // show new gaming panel
                view.addGamingLevelButtons(word_size);
                gamingPanelLevel.revalidate();
                gamingPanelLevel.repaint();
                
                view.setLetter();
            }else{
                int currentlevel=model.getCurrentLevel();
                // stop the timer
                t.stop();
                timerStatus = false;
                if(!checkEmpty()){
                    
                    // give error message to palyer
                    JOptionPane.showMessageDialog(null, "Incorect!", "",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("../images/sad.png"));
                    timerSetDefault();
                    t.start();
                    timerStatus = true;
                    if(currentlevel==model.EASY){
                        model.setCurrentscore(0);
                    }else if(currentlevel==model.NORMAL){
                        model.setCurrentscore(50);
                    }
                    else if(currentlevel==model.HARD){
                        model.setCurrentscore(150);
                    }
                    view.setScore(model.getCurrentscore()+"");
                    playAgain();
                    
                }else{
                    t.stop();
                    JOptionPane.showMessageDialog(null,"Fill the rest" , "",JOptionPane.INFORMATION_MESSAGE,null);
                    
                    // start the timer
                    t.start();
                    timerStatus = true;
                
                }
                
                }   
            }  
            return;
        }
        // if press new game button
        if(o==view.getNewgamebtn()){
            model.setCurrentLevel(model.EASY);
            view.newGame();
            playAgain();
            // stop the timer and reset timer
            if(t!=null)
            t.stop();
            timerStatus = true;
            timerSetDefault();
            if(t!=null)
            t.start();
            timerStatus = false;
            model.setCurrentscore(0);
            
            return;
        }
        // if press timer start button
        if(o==view.getStart()){
            // check the timer state
            if(!timerStatus){
                // create new time
                t = new Timer(1000, new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    time="";
                    // to decide the time string 
                    if(count<10){
                    time = "0"+count;
                    }else{
                    time = count+"";
                    }  
                    // set time to the gui
                   view.setTime(time);
                   
                    if(count<=5){
                        view.setRedColorToTime();
                    }
              
                    if(count ==0){
                        // stop the timer
                        t.stop();
                        timerStatus = false;
                        // error message
                        JOptionPane.showMessageDialog(null, "Timeout!", "",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("../images/sad.png"));
                        //reset the timer
                        timerSetDefault();
                        // start the timer
                        t.start();
                        // time state=true
                        timerStatus = true;
                        // decide the score relevant to the game level
                        if(model.getCurrentLevel()==model.EASY){
                            model.setCurrentscore(0);
                        }else if(model.getCurrentLevel()==model.NORMAL){
                            model.setCurrentscore(50);
                        }
                        else if(model.getCurrentLevel()==model.HARD){
                            model.setCurrentscore(150);
                        }
                        // set score to the gui
                        view.setScore(model.getCurrentscore()+"");
                        // play again the game /reset the game
                        playAgain();
            
                    }
                    
                    count--;
                 }   
            });
            t.start();
            timerStatus = true;
            }
            return;
        }
       
        for(int j=0;j<word_size;j++){
                
                JButton button = model.getBtnSet()[1][j];
                if(o==button){
                    if(timerStatus){
                    
                        if(buttonSelectedState[j]==0){
                            actionButtons(button,j);
                            button.setBackground(new Color(3, 99, 175));
                        
                        }
                    }
                    return;
                }
            
        }
   
    }
        
    
    public static void playAgain(){
        // clear all used words
        usedWordIndexes.clear();
        word_size= selectWord(model.getCurrentLevel());
        JPanel gamingPanelLevel = view.getGamingPanelLevel();
        // remove all the elements of the panel
        gamingPanelLevel.removeAll();
        // remove the old panel
        view.getFrame().remove(gamingPanelLevel);
        // set new panel
        view.addGamingLevelButtons(word_size);
        gamingPanelLevel.revalidate();
        gamingPanelLevel.repaint();
            // set letters to the game    
        view.setLetter();
    }
    
    public boolean checkEmpty(){
        boolean state = false;
        for(int i=0;i<word_size;i++){
            if(model.getBtnSet()[0][i].getText().isEmpty()){
                state=true;
                break;
            }
        }
        return state;
    }
    //to return the message to user according to the score 
    public String decideMessage(int level,int score){
        String msg="";
        if(level==model.EASY){
            if(score<50){
                msg="Next attempt";
            }else if(score==50){
                msg="Level 1 completed";
            }
        }else if(level==model.NORMAL){
            if(score<150){
                msg="Next attempt";
            }else if(score==150){
                msg="Level 2 completed";
            }
        }
        else if(level==model.HARD){
             if(score<300){
                msg="Next attempt";
            }else if(score==300){
                msg="Congratulation you won the game";
            }
        }
        return msg;
    }
    // calculating score
    public void addScore(int level){
        int earlyMarks =model.getCurrentscore();
        
        int addingScore=0;
        if(level==model.EASY){
            addingScore = 10;
        }else if(level==model.NORMAL){
            addingScore = 20;
            
        }
        else if(level==model.HARD){
            addingScore = 30;
        }
        // set score to the model
        model.setCurrentscore(earlyMarks+addingScore);
    }
    // to shuffle the input word that was generated in randomly
    public static String shuffle(String input){
        ArrayList<Character> characters = new ArrayList<Character>();
        StringBuilder output;
        
        while(true){
            for(char c:input.toCharArray()){
                characters.add(c);
            }
            output = new StringBuilder(input.length());
            while(characters.size()!=0){
                int randPicker = (int)(Math.random()*characters.size());
                output.append(characters.remove(randPicker));
            }
            if(!output.toString().equals(input)){
                break;
            }
        }
        
        return output.toString();
    }
    // check the word is suitable for the current running level
    public static boolean isSuitableFor(int wordLength,int level) {
        boolean state=false;
        if(level==model.EASY){
            if(wordLength>=3 && wordLength<=5){
                state=true;
            }
        }else if(level==model.NORMAL){
            if(wordLength>=5 && wordLength<=7){
                state=true;
            }
        }else if(level==model.HARD){
            if(wordLength>=8 && wordLength<=10){
                state=true;
            }
        }
        
        return state; 
    }
    // select word for the flow of the game
    public static int selectWord(int currentlevel){
        
        Random random = new Random();
        int randomIndex = random.nextInt(wordsCount-1)+1;
        
        word = wordlist.get(randomIndex).toString();
        while(!isSuitableFor(word.length(),currentlevel) || isUsedWord(randomIndex, usedWordIndexes)){
            
            randomIndex =random.nextInt(wordsCount-1)+1;
            word=wordlist.get(randomIndex).toString();
        }
        
        usedWordIndexes.add(randomIndex);
        shuffledWord = shuffle(word);
        
        int wordlength = word.length();
        
        buttonSelectedState = new int[wordlength];
        
        return wordlength;
        
    }
    // check word is a used word in the past flow 
    public static boolean isUsedWord(int index,ArrayList list) {
        boolean state = false;
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            int next = iterator.next();
            if(next==index){
                state = true;
                break;
            }
	}
        return state; 
    }
    
    public void selectButton(int index){
        buttonSelectedState[index]=1;
    }
    
    public static void actionButtons(JButton button,int index){
        String text = button.getText();
        int inde = getEmptyButton();
        model.getBtnSet()[0][inde].setText(text);
        buttonSelectedState[index]=1;
       
    }
    // get the empty buttons
    private static int getEmptyButton() {
        int index = 0;
        while(!model.getBtnSet()[0][index].getText().isEmpty()){
            index++;
        }
        return index;
        
    }
// read the file
   public static void storeMap(String csvFile){
       
	FileReader fileRd=null; 
	BufferedReader reader=null; 

	   try { 
	           fileRd = new FileReader(csvFile); 
	           reader = new BufferedReader(fileRd); 

	           /* read the CSV file's first line which has 
	            * the names of fields. 
	            */   
            
	           // get a new hash map
	           wordlist = new HashMap<Integer, String>(); 

	           /* read each line, getting it split by , 
	            * use the indexes to get the key and value 
	            */
	           int index=1;
	           for(String line = reader.readLine(); 
		      line != null; 
		      line = reader.readLine()) { 
		
		      wordlist.put(index,line); 
                
                        index++;
	           }
                    wordsCount = index;
	    
	           if(fileRd != null) fileRd.close();
	           if(reader != null) reader.close();
	    
	           // I can catch more than one exceptions 
	       } catch (ArrayIndexOutOfBoundsException e) { 
	           System.out.println("Malformed CSV file");
	           System.out.println(e);
	       } catch (IOException e) { 
	           System.out.println(e);
	           System.exit(-1); 
	       }
        
        
    
       
   }

    private String getUserInputWord() {
      String word="";
      JButton button = null;
        for(int i=0;i<word_size;i++){
            //get the button set
            button = model.getBtnSet()[0][i];
            //get the texts of button set
            String text = button.getText();
           //concat the word
            word = word + text;
            
        }
        
        return word;
        
    }
  
}
