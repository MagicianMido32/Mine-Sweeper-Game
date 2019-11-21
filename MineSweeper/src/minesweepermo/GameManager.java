/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweepermo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author MiDo
 */
public class GameManager {
    public  Boolean firstTime = true;
    int[][]scheme;
    Cell[][]Cells;
    int minesCount;
    int minesLock;
    int hight;
    int width;
    JLabel flags ;
    JLabel Timer;
    JFrame Deck;
    JButton smile;
    Boolean countMines;
    ImageIcon smilyIcon;
    ImageIcon deadIcon;
    ImageIcon mineIcon;
   public GameManager(int[][]scheme ,Cell[][]Cells , int minesCount ,int hight,int width,Boolean countMines )
   {
       this.scheme = scheme;
       this.Cells=Cells;
       this.minesCount=minesCount;
       this.minesLock=minesCount;
       this.hight=hight;
       this.width=width;
       this.countMines=countMines;
       smilyIcon= new ImageIcon(getClass().getResource("images/Smiley.png"));
      deadIcon = new ImageIcon(getClass().getResource("images/dead.png"));
      mineIcon= new ImageIcon(getClass().getResource("images/mine.png"));
      
   }
   public void MineFlagged(){
       minesCount-=1;
       if(countMines)flags.setText("Mines : " + minesCount);
      // System.out.println("MINE ! " + minesCount);
       if(minesCount==0){
           JOptionPane.showMessageDialog(null,"you won","Mine Sweeper",JOptionPane.OK_OPTION);
        Deck.dispatchEvent(new WindowEvent(Deck, WindowEvent.WINDOW_CLOSING));  
       }
   }
   public void MineUnFlagged()
   {
       minesCount+=1;
      if(countMines)flags.setText("Mines : " + minesCount);
   }
   public void DrawGame()
    {
       
      Deck = new JFrame("Mine Sweeper");
      
      flags=new JLabel();
      flags.setText("Mines : " + minesCount);
      flags.setSize(100,50);
      flags.setLocation(620, 0);
      Deck.add(flags);
      
       JLabel Wr = new JLabel(" Time :");
       Wr.setSize(100, 50);
       Deck.add(Wr);
        
        Timer = new JLabel("0");
        Timer.setSize(50,50);
        Timer.setLocation(50,0);
        Deck.add(Timer);
        
        smile = new JButton();
        smile.setIcon(smilyIcon);
        smile.setSize(50, 50);
        smile.setLocation(315, 0);
        Deck.add(smile);
   
      new timeThread(this).start();
        JPanel pane = new JPanel();
        pane.setBackground(Color.white);
    pane.setLayout(new GridLayout(hight + 1, width));
           for(int j = 0;j<hight;j++)
           {
              for(int i = 0 ; i <width;i++)
               {
              Cell bx = Cells[j][i];
              
         bx.setBackground(Color.WHITE);
         bx.BindToManager(this);
         bx.addActionListener((ActionEvent e) -> {
                   Object eventSource = e.getSource();
                   Cell clickedButton = (Cell) eventSource;
                    clickedButton.trigger();   
              });
               bx.addMouseListener(new MouseAdapter() {
               @Override
               public void mousePressed(MouseEvent e) {
                  if (e.getButton() == MouseEvent.BUTTON3) {
                   bx.flag();
                  }
               }
            });

pane.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
               pane.add(bx);
               }
     } 
    Deck.add(pane);
    Deck.setPreferredSize(new Dimension(700, 700));//w800 h700
    Deck.pack();
    Deck.setLocationRelativeTo(null);
    Deck.setVisible(true);
    
    }
    public void MineBlown(Cell mine){
                       mine.setBackground(Color.BLACK);
                       mine.setIcon(mineIcon);
                       smile.setIcon(deadIcon);
            JOptionPane.showMessageDialog(null,"you Lost","Mine Sweeper",JOptionPane.OK_OPTION);
           Deck.dispatchEvent(new WindowEvent(Deck, WindowEvent.WINDOW_CLOSING));      
    }
       public void timer() { 
        String[] time = Timer.getText().split(" ");
        int time0 = Integer.parseInt(time[0]);
        ++time0;
        Timer.setText(Integer.toString(time0) + " s");
    }

}

class timeThread implements Runnable {
    private Thread t;
    private final GameManager newGame;

    timeThread(GameManager newGame) {
        this.newGame = newGame;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                newGame.timer();
            }
            catch (InterruptedException e) {
                
            }
        }
    }

    public void start() {
        if (t==null) {
            t = new Thread(this);
            t.start();
        }
    }
}