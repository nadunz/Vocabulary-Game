

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FindWordGUI implements MouseListener,MouseMotionListener{
    
    private final Dimension FRAME_SIZE = new Dimension(465, 540);
    private int currentX,currentY;
    private JFrame frame;
    private JPanel mainPanel,sidebar,northPanel,btnPanel,levelpanel,gamingPanelLevel;
    private JLabel clock,score,levelText,topic;
    private JButton newgamebtn,confirmbtn,exit,minimize,clearbtn,start;
    private JLabel levelLabel[]=new JLabel[3];
    private JButton [][]btnSet=new JButton[2][10];
  
    GameController controller=new GameController();
    private int wordlength;

    public FindWordGUI() {
        // create a jframe for container
        addFrame();
        // create main panel to the frame
        addMainPanel();
        // add the clock text and icon
        addClockAndScorePanel();
       
        addNewGameButton();
        addConfirmButton();
        addClearButton();
        addExitAndMinimize();
        // side bar
        addSideBar();
        
        // label "find the word" - Topic for the game
        JLabel topic = new JLabel("Find the Word");
        topic.setForeground(Color.WHITE);
        topic.setFont(new Font("Tahoma",Font.PLAIN , 28));
        topic.setSize(300, 50);
        topic.setLocation(150, 24);
        mainPanel.add(topic);
        
        //runTimer();
        
        addStartStopButton();
        
        mainPanel.addMouseListener(this); 
        mainPanel.addMouseMotionListener(this);
       
        
        
        setLevelNotification(GameModel.EASY);
        setScore(0+"");
        showGameGUI();
        
       
      
    }
    
  
    private void addFrame(){
        frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setSize(FRAME_SIZE);
    }
    
    private void showGameGUI(){
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 0, 0));
        mainPanel.setSize(FRAME_SIZE);
        frame.add(mainPanel);
    }

    public JPanel getGamingPanelLevel() {
        return gamingPanelLevel;
    }
    
    
    
    private void addClockAndScorePanel(){
        northPanel = new JPanel(new GridLayout(1 ,2,10,10));
        clock = new JLabel("00:30", new ImageIcon("../images/Alarm.png"), SwingConstants.LEFT);
        clock.setForeground(Color.WHITE);
        clock.setFont(new Font("Tahoma",Font.PLAIN , 20));
        clock.setOpaque(false);
        northPanel.add(clock);
        
        score = new JLabel("Score :  50",SwingConstants.CENTER);
        score.setForeground(Color.WHITE);
        score.setFont(new Font("Tahoma",Font.PLAIN , 20));
        
        score.setOpaque(false);
        northPanel.add(score);
        
        northPanel.setSize(395, 100);
        northPanel.setLocation(62, 90);
        northPanel.setOpaque(false);
        mainPanel.add(northPanel);
    }
    
    private void addNewGameButton(){
        
        newgamebtn = new JButton("New Game");
        newgamebtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        newgamebtn.setForeground(Color.WHITE);
        newgamebtn.setBackground(Color.BLACK);
        newgamebtn.setSize(110, 55);
        newgamebtn.setLocation(70, 465);
        mainPanel.add(newgamebtn);
        newgamebtn.addActionListener(controller);
    }
    
    private void addConfirmButton(){
        
        confirmbtn = new JButton("Confirm");
        confirmbtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        confirmbtn.setForeground(Color.WHITE);
        confirmbtn.setBackground(Color.BLACK);
        confirmbtn.setSize(110, 55);
        confirmbtn.setLocation(340, 465);
        mainPanel.add(confirmbtn);
        confirmbtn.addActionListener(controller);
        
    }
    
    private void addClearButton(){
        
        clearbtn = new JButton(new ImageIcon("../images/Clear.png"));
        clearbtn.setSize(55, 55);
        clearbtn.setLocation(410, 350);
        mainPanel.add(clearbtn);
        clearbtn.addMouseListener(this); 
        clearbtn.addActionListener(controller);
            
    }
    
    private void addStartStopButton(){
        
        start = new JButton("Start");
        start.setSize(80, 40);
        start.setBackground(Color.BLACK);
        start.setForeground(Color.WHITE);
        start.setLocation(70, 360);
        mainPanel.add(start);
        start.addMouseListener(this); 
        start.addActionListener(controller);
            
    }
    
    public void addGamingLevelButtons(int wordsize){
        
        this.wordlength = wordsize;
        gamingPanelLevel = new JPanel(new GridLayout(2, 10 ,3 ,20));
        gamingPanelLevel.setSize(404, 150);
        gamingPanelLevel.setOpaque(false);
        gamingPanelLevel.setLocation(60, 200);
        
        
        for(int i=0;i<2;i++){
            for(int j=0;j<wordsize;j++){
                btnSet[i][j]=new JButton();
                
                if(i==0){
                    btnSet[i][j].setForeground(Color.BLACK);
                    btnSet[i][j].setEnabled(false);
                    
                }else{
                   btnSet[i][j].setForeground(Color.WHITE);
                   btnSet[i][j].setBackground(Color.BLACK);
                }
                btnSet[i][j].setFont(new Font("Tahoma", Font.PLAIN, 12));
                btnSet[i][j].addActionListener(controller);
                gamingPanelLevel.add(btnSet[i][j]);
            }
        }
        
        mainPanel.add(gamingPanelLevel);
        
    }

    public JButton getStart() {
        return start;
    }
    
    
    
    private void addSideBar(){
        sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBackground(new Color(16, 16, 16));
        sidebar.setSize(55, 540);
        mainPanel.add(sidebar);
        
        levelText = new JLabel("LEVEL",SwingConstants.CENTER);
        levelText.setSize(55, 15);
        levelText.setLocation(0, 55);
        levelText.setFont(new Font("Tahoma",Font.PLAIN , 14));
        levelText.setForeground(Color.GRAY);
        sidebar.add(levelText);
        
        levelpanel = new JPanel(new GridLayout(3, 1, 0, 4));
        levelpanel.setOpaque(false);
        levelpanel.setLocation(0, 100);
        levelpanel.setSize(55, 350);
        
        for(int i =0;i<3;i++){
            String text=null;
            if(i==0){
                text = "<HTML>e<br>a<br>s<br>y<HTML>";
            }else if(i==1){
                text = "<HTML>n<br>o<br>r<br>m<br>a<br>l<HTML>";
            }else{
                text = "<HTML>h<br>a<br>r<br>d<HTML>";
            }
            levelLabel[i]= new JLabel(text, SwingConstants.CENTER);
            levelLabel[i].setFont(new Font("Tahoma",Font.BOLD , 13));
            levelLabel[i].setForeground(Color.WHITE); 
            levelLabel[i].setOpaque(true);
            levelpanel.add(levelLabel[i]);
        }
        sidebar.add(levelpanel);
        
    }
    
    public void setLevelNotification(int level){
        
        for(int i=0;i<3;i++){
            if(i==(level-1)){
               levelLabel[i].setBackground(new Color(3, 99, 175));   
            }else{
               levelLabel[i].setBackground(Color.BLACK); 
            }
         
        }
       
    }
    
    public void setTime(String time){
        clock.setText("00:"+time);
    }

    public JLabel getClock() {
        return clock;
    }
    
    
    public void setScore(String currentscore){
        score.setText("Score : "+currentscore);  
    }
    
    public void setRedColorToTime(){
        clock.setForeground(Color.red);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
                
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentX=e.getX();
        currentY=e.getY();
                
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        frame.setOpacity((float)1.0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
            Object o=e.getSource();
            if(o == clearbtn){
                clearbtn.setIcon(new ImageIcon("../images/Clear_selected.png"));
                return;
            }
            if(o == exit){
                exit.setIcon(new ImageIcon("../images/exit_selected.png"));
                return;
            }
            if(o == minimize){
                minimize.setIcon(new ImageIcon("../images/Minimize_selected.png"));
                return;
            }
            
                
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o=e.getSource();
        if(o == clearbtn){
            clearbtn.setIcon(new ImageIcon("../images/Clear.png"));  
            return;
        }
        if(o == exit){
            exit.setIcon(new ImageIcon("../images/exit.png"));  
            return;
        }
        if(o == minimize){
            minimize.setIcon(new ImageIcon("../images/Minimize.png"));   
            return;
        }
               
    }
    
     
    @Override
    public void mouseDragged(MouseEvent e) {
        frame.setOpacity((float)0.8);
        int x=e.getXOnScreen();
        int y=e.getYOnScreen();
        frame.setLocation(x-currentX, y-currentY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
     
    }
    

    public JButton getConfirmbtn() {
        return confirmbtn;
    }

    public JButton[][] getBtnSet() {
        return btnSet;
    }

    public JButton getNewgamebtn() {
        return newgamebtn;
    }

    public JButton getExit() {
        return exit;
    }

    public JButton getMinimize() {
        return minimize;
    }

    public JButton getClearbtn() {
        return clearbtn;
    }

    public JFrame getFrame() {
        return frame;
    }

  
    
    public void clear(){
        for(int i=0;i<wordlength;i++){
            btnSet[0][i].setText("");
        }
        
    }
    public void newGame(){
        setLevelNotification(GameModel.EASY);
        setScore(0+"");
        
    }
    
    public void setLetter(){
        char[] charray=GameController.shuffledWord.toCharArray();
        for(int i=0;i<wordlength;i++){
            btnSet[1][i].setText(charray[i]+"");
        }
    }

    private void addExitAndMinimize() {
        exit = new JButton(new ImageIcon("../images/exit.png"));
        minimize = new JButton(new ImageIcon("../images/Minimize.png"));
        
        
        // btn panel
        btnPanel = new JPanel(new GridLayout(1, 2));
        btnPanel.setSize(110, 55);
        exit.setSize(55,55);
        minimize.setSize(55,55);
        btnPanel.setLocation(354, 0);
        btnPanel.add(minimize);
        btnPanel.add(exit);
        mainPanel.add(btnPanel);
        
        exit.addMouseListener(this); 
        minimize.addMouseListener(this);
         
        exit.addActionListener(controller);
        minimize.addActionListener(controller);
    }
    
    
    
}
