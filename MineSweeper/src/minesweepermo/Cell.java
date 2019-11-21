/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweepermo;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author MiDo
 */
public class Cell extends JButton {
    public int y=0;
    public int x=0;
    //public int countDeMines;
    public  ArrayList<Cell> neighbours =new ArrayList<>();
    public Boolean isMine=false;
    public Boolean isFlagged=false;
    public Boolean hasTriggered=false;
    public GameManager myManager;
     ImageIcon flagIcon;
     
     
    public Cell(int y,int x , Boolean isMine )
    {
        this.x=x;
        this.y=y;
        this.isMine=isMine;
        flagIcon  = new ImageIcon(getClass().getResource("images/flag.png"));    
       setFont( new Font("Arial", Font.CENTER_BASELINE, 10));
    }
    public void BindToManager(GameManager myManager)
    {
         this.myManager=myManager;
    }
    public void flag(){
     //   System.out.println("flaged");
        if(hasTriggered )return ;
        
        if(isFlagged){
             myManager.minesLock+=1;
            isFlagged=false;
            setBackground(Color.white);
            setIcon(null);
            if(isMine)myManager.MineUnFlagged();
          
            return ;
        }
        if (myManager.minesLock==0)return ;
        if(isMine){
        setBackground(Color.red);
            setIcon(flagIcon);
        myManager.MineFlagged();
        }
        
        isFlagged=true;
        setBackground(Color.red);
       setIcon(flagIcon);
        myManager.minesLock-=1;
    }
  public void CellTrigger()
    {
        if(hasTriggered)return;
        if(isMine)return;
         hasTriggered=true;
         
        if(isFlagged)
        {isFlagged=false;
         myManager.minesLock+=1;
        }
        setBackground(Color.green);
        setIcon(null);
       
        int countDeMines=0;
        for(Cell C : neighbours)
        {   if(C.isMine)countDeMines+=1;
        }
       if(countDeMines!=0){
           setText(Integer.toString(countDeMines));
       }else{
       for(Cell C : neighbours)C.CellTrigger();
       }
    }
    public void trigger()
    {
       if (hasTriggered)return  ;
       else hasTriggered=true; 
       if(isFlagged)
        {isFlagged=false;
         myManager.minesLock+=1;
        }
       
        if(isMine){
           if(myManager.firstTime)
           {
               isMine=false;
               myManager.minesCount-=1;
               myManager.minesLock-=1;
               myManager.firstTime=false;//*
           }else{myManager.MineBlown(this);}
        }
        setBackground(Color.green);
        setIcon(null);
        int countDeMines=0;
        for(Cell C : neighbours)
        {
           if(C.isMine) countDeMines+=1;
           C.CellTrigger();
        } 
        if(countDeMines!=0)setText(Integer.toString(countDeMines));
        myManager.firstTime=false;
      
    }
}
