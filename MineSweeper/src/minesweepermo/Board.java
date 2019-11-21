/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweepermo;

import java.util.Random;

/**
 *
 * @author MiDo
 */
public class Board {
    public Cell[][] Cells ;
    
    public void drawBoard(int numberOfMines , int hight,int width,int difficulty , Boolean countMines)
    {
        difficulty+=1;
        Cells=new Cell[hight][width];
        Random rand = new Random();
        int minesCounter=numberOfMines;
        
        int [][]scheme = new int [hight][width];
        //problem in randomization
     while(minesCounter!=0){
        for (int j = 0 ; j <hight;j++ )
        {
            for(int i =0;i<width;i++)
            {
                int random =rand.nextInt(difficulty);
                if(random ==1 && minesCounter!=0 && scheme[j][i]!= 1){
                    minesCounter-=1;
                    scheme[j][i]= 1;
                }else{ 
                    if( scheme[j][i]!= 1) scheme[j][i]= 0;
                }
            }  
          } 
     }
      Boolean xMine;
      for (int j = 0 ; j <hight;j++ )
        {
            for(int i =0;i<width;i++)
            {
                xMine = (scheme[j][i]==1);
                Cells[j][i]= new Cell(j,i,xMine);
            }
        }
       for (int j = 0 ; j <hight;j++ )
        {
            for(int i =0;i<width;i++)
            {
                  if(j-1!=-1 && i-1!=-1)Cells[j][i].neighbours.add(Cells[j-1][i-1]);
                  if(i-1!=-1)           Cells[j][i].neighbours.add(Cells[j][i-1]);
                  if(j+1!=hight&&i-1!=-1)  Cells[j][i].neighbours.add(Cells[j+1][i-1]);
                  if(j-1!=-1)           Cells[j][i].neighbours.add(Cells[j-1][i]);
                  if(j+1!=hight)           Cells[j][i].neighbours.add(Cells[j+1][i]);
                  if(j-1!=-1&&i+1!=width)  Cells[j][i].neighbours.add(Cells[j-1][i+1]);
                  if(i+1!=width)           Cells[j][i].neighbours.add(Cells[j][i+1]);
                  if(j+1!=hight&&i+1!=width)  Cells[j][i].neighbours.add(Cells[j+1][i+1]);
            }
       } 
         new GameManager(scheme,Cells,numberOfMines,hight,width,countMines).DrawGame();
    }
}
