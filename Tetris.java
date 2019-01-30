   import javax.swing.*;
   import java.awt.event.*;
   public class Tetris
   {
      static Block[][] game=new Block[20][10];
      static int[] blocks=new int[4];
      static int num, bottomLeftR, bottomLeftC, rots, score, add;
      private static JFrame frame;
      private static TetrisPanel tp;
      static keyListener kl;
      public static void createPanel()
      {
         frame = new JFrame();
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         tp = new TetrisPanel(game, blocks);
         kl=new keyListener();
         frame.addKeyListener(kl);
         frame.add(tp);
         frame.setSize(472, 740);
         frame.setVisible(true);
         frame.setFocusable(true);
      }
      public static void fillBoard()
      {
         score=0;
         for(int i=0; i<4; i++)
            blocks[i]=(int)(Math.random()*7);
         for(int r=0; r<20; r++)
            for(int c=0; c<10; c++)
               game[r][c]=new Block();
      }
      public static void showBoard()
      {
         System.out.println(" ----------");
         for(int r=0; r<20; r++)
         {
            System.out.print("|");
            for(int c=0; c<10; c++)
               System.out.print(game[r][c].color());
            System.out.println("|");
         }
         System.out.println(" ----------");
      }
      public static boolean canSpawnBlock()
      {
         if(blocks[0]<1)
            return game[0][4].num()+game[0][5].num()+game[1][3].num()+game[1][4].num()<1;
         else
            if(blocks[0]<2)
            {
               if(game[0][3].num()>0)
                  return false;
               for(int c=3; c<6; c++)
                  if(game[1][c].num()>0)
                     return false;
            }
            else
               if(blocks[0]<3)
               {
                  if(game[0][4].num()>0)
                     return false;
                  for(int c=3; c<6; c++)
                     if(game[1][c].num()>0)
                        return false;
               }
               else
                  if(blocks[0]<4)
                  {
                     for(int c=3; c<7; c++)
                        if(game[0][c].num()>0)
                           return false;
                  }
                  else
                     if(blocks[0]<5)
                     {
                        for(int r=0; r<2; r++)
                           for(int col=4; col<6; col++)
                              if(game[r][col].num()>0)
                                 return false;
                     }
                     else
                        if(blocks[0]<6)
                        {
                           if(game[0][5].num()>0)
                              return false;
                           for(int co=3; co<6; co++)
                              if(game[1][co].num()>0)
                                 return false;
                        }
                        else
                           return game[0][3].num()+game[0][4].num()+game[1][4].num()+game[1][5].num()<1;
         return true;
      }
      public static void spawnBlock()
      {
         num++;
         rots=0;
         bottomLeftR=1;
         bottomLeftC=3;
         clearFullRows();
         for(int i=0; i<3; i++)
            blocks[i]=blocks[i+1];
         blocks[3]=(int)(Math.random()*7);
         if(canSpawnBlock())
            if(blocks[0]<1)
            {
               game[0][4].setTo(num, 'G');
               game[0][5].setTo(num, 'G');
               game[1][3].setTo(num, 'G');
               game[1][4].setTo(num, 'G');
            }
            else
               if(blocks[0]<2)
               {
                  game[0][3].setTo(num, 'B');
                  for(int c=3; c<6; c++)
                     game[1][c].setTo(num, 'B');
               }
               else
                  if(blocks[0]<3)
                  {
                     game[0][4].setTo(num, 'P');
                     for(int c=3; c<6; c++)
                        game[1][c].setTo(num, 'P');
                  }
                  else
                     if(blocks[0]<4)
                     {
                        for(int c=3; c<7; c++)
                           game[0][c].setTo(num, 'L');
                        bottomLeftR=0;
                     }
                     else
                        if(blocks[0]<5)
                        {
                           for(int r=0; r<2; r++)
                              for(int c=4; c<6; c++)
                                 game[r][c].setTo(num, 'Y');
                        }
                        else
                           if(blocks[0]<6)
                           {
                              game[0][5].setTo(num, 'O');
                              for(int c=3; c<6; c++)
                                 game[1][c].setTo(num, 'O');
                           }
                           else
                           {
                              game[0][3].setTo(num, 'R');
                              game[0][4].setTo(num, 'R');
                              game[1][4].setTo(num, 'R');
                              game[1][5].setTo(num, 'R');
                           }
         else
         {
            System.out.println("You got "+score+" points!");
            System.exit(0);
         }
      }
      public static boolean colCanGoDown(int c)
      {
         if(game[19][c].num()==num)
            return false;
         for(int r=18; r>=0; r--)
            if(game[r][c].num()==num)
            {
               if(game[r+1][c].num()>0)
                  return false;
               return true;
            }
         return true;
      }
      public static boolean canGoDown()
      {
         for(int c=0; c<10; c++)
            if(!colCanGoDown(c))
               return false;
         return true;
      }
      public static void goDown()
      {
         bottomLeftR++;
         for(int r=18; r>=0; r--)
            for(int c=0; c<10; c++)
               if(game[r][c].num()==num)
               {
                  game[r+1][c].setTo(game[r][c]);
                  game[r][c].setTo(0, 'Q');
               }
      }
      public static boolean rowCanGoLeft(int r)
      {
         if(game[r][0].num()==num)
            return false;
         for(int c=1; c<10; c++)
            if(game[r][c].num()==num)
            {
               if(game[r][c-1].num()>0)
                  return false;
               return true;
            }
         return true;
      }
      public static boolean canGoLeft()
      {
         for(int r=0; r<20; r++)
            if(!rowCanGoLeft(r))
               return false;
         return true;
      }
      public static void goLeft()
      {
         if(canGoLeft())
         {
            bottomLeftC--;
            for(int r=0; r<20; r++)
               for(int c=1; c<10; c++)
                  if(game[r][c].num()==num)
                  {
                     game[r][c-1].setTo(game[r][c]);
                     game[r][c].setTo(0, 'Q');
                  }
         }
      }
      public static boolean rowCanGoRight(int r)
      {
         if(game[r][9].num()==num)
            return false;
         for(int c=8; c>=0; c--)
            if(game[r][c].num()==num)
            {
               if(game[r][c+1].num()>0)
                  return false;
               return true;
            }
         return true;
      }
      public static boolean canGoRight()
      {
         for(int r=0; r<20; r++)
            if(!rowCanGoRight(r))
               return false;
         return true;
      }
      public static void goRight()
      {
         if(canGoRight())
         {
            bottomLeftC++;
            for(int r=0; r<20; r++)
               for(int c=8; c>=0; c--)
                  if(game[r][c].num()==num)
                  {
                     game[r][c+1].setTo(game[r][c]);
                     game[r][c].setTo(0, 'Q');
                  }
         }
      }
      public static boolean canRotate()
      {
         if(bottomLeftR<2||bottomLeftC>7||blocks[0]==4)
            return false;
         if(blocks[0]<1)
            if(rots%2<1)
               return game[bottomLeftR-1][bottomLeftC].num()+game[bottomLeftR-2][bottomLeftC].num()<1;
            else
               return game[bottomLeftR][bottomLeftC].num()+game[bottomLeftR-1][bottomLeftC+2].num()<1;
         else
            if(blocks[0]<2)
               if(rots%4<1)
                  return game[bottomLeftR-2][bottomLeftC].num()+game[bottomLeftR-2][bottomLeftC+1].num()<1;
               else
                  if(rots%4<2)
                     return game[bottomLeftR-1][bottomLeftC+1].num()+game[bottomLeftR-1][bottomLeftC+2].num()+game[bottomLeftR][bottomLeftC+2].num()<1;
                  else
                     if(rots%4<3)
                        return game[bottomLeftR][bottomLeftC].num()+game[bottomLeftR][bottomLeftC+1].num()+game[bottomLeftR-2][bottomLeftC+1].num()<1;
                     else
                        return game[bottomLeftR][bottomLeftC+2].num()+game[bottomLeftR-1][bottomLeftC].num()<1;
            else
               if(blocks[0]<3)
                  if(rots%4<1)
                     return game[bottomLeftR-1][bottomLeftC].num()+game[bottomLeftR-2][bottomLeftC].num()<1;
                  else
                     if(rots%4<2)
                        return game[bottomLeftR-1][bottomLeftC+2].num()+game[bottomLeftR][bottomLeftC+1].num()<1;
                     else
                        if(rots%4<3)
                           return game[bottomLeftR-2][bottomLeftC+1].num()<1;
                        else
                           return game[bottomLeftR][bottomLeftC].num()+game[bottomLeftR][bottomLeftC+2].num()<1;
               else
                  if(blocks[0]<4)
                     if(rots%2<1)
                     {
                        if(bottomLeftR<3)
                           return false;
                        for(int i=1; i<4; i++)
                           if(game[bottomLeftR-i][bottomLeftC].num()>0)
                              return false;
                        return true;
                     }
                     else
                     {
                        if(bottomLeftC>6)
                           return false;
                        for(int i=1; i<4; i++)
                           if(game[bottomLeftR][bottomLeftC+i].num()>0)
                              return false;
                        return true;
                     }
                  else
                     if(blocks[0]<6)
                        if(rots%4<1)
                           return game[bottomLeftR-1][bottomLeftC].num()+game[bottomLeftR-2][bottomLeftC].num()<1; 
                        else
                           if(rots%4<2)
                              return game[bottomLeftR-1][bottomLeftC+1].num()+game[bottomLeftR-1][bottomLeftC+2].num()<1;
                           else
                              if(rots%4<3)
                                 return game[bottomLeftR-2][bottomLeftC].num()+game[bottomLeftR-2][bottomLeftC+1].num()+game[bottomLeftR][bottomLeftC+1].num()<1;
                              else
                                 return game[bottomLeftR][bottomLeftC].num()+game[bottomLeftR][bottomLeftC+2].num()+game[bottomLeftR-1][bottomLeftC+2].num()<1;
                     else
                        if(rots%2<1)
                           return game[bottomLeftR][bottomLeftC].num()+game[bottomLeftR-2][bottomLeftC+1].num()<1;
                        else
                           return game[bottomLeftR][bottomLeftC+1].num()+game[bottomLeftR][bottomLeftC+2].num()<1;
      }
      public static void rotate()
      {
         if(canRotate())
         {
            if(blocks[0]<1)
               if(rots%2<1)
               {
                  game[bottomLeftR][bottomLeftC].setTo(0, 'Q');
                  game[bottomLeftR-1][bottomLeftC].setTo(num, 'G');
                  game[bottomLeftR-2][bottomLeftC].setTo(num, 'G');
                  game[bottomLeftR-1][bottomLeftC+2].setTo(0, 'Q');
               }
               else
               {
                  game[bottomLeftR][bottomLeftC].setTo(num, 'G');
                  game[bottomLeftR-1][bottomLeftC].setTo(0, 'Q');
                  game[bottomLeftR-2][bottomLeftC].setTo(0, 'Q');
                  game[bottomLeftR-1][bottomLeftC+2].setTo(num, 'G');
               }
            else
               if(blocks[0]<2)
                  if(rots%4<1)
                  {
                     game[bottomLeftR-2][bottomLeftC].setTo(num, 'B');
                     game[bottomLeftR-2][bottomLeftC+1].setTo(num, 'B');
                     game[bottomLeftR][bottomLeftC+1].setTo(0, 'Q');
                     game[bottomLeftR][bottomLeftC+2].setTo(0, 'Q');
                  }
                  else
                     if(rots%4<2)
                     {
                        game[bottomLeftR][bottomLeftC].setTo(0, 'Q');
                        game[bottomLeftR-2][bottomLeftC].setTo(0, 'Q');
                        game[bottomLeftR-2][bottomLeftC+1].setTo(0, 'Q');
                        game[bottomLeftR-1][bottomLeftC+1].setTo(num, 'B');
                        game[bottomLeftR-1][bottomLeftC+2].setTo(num, 'B');
                        game[bottomLeftR][bottomLeftC+2].setTo(num, 'B');
                     }
                     else
                        if(rots%4<3)
                        {
                           game[bottomLeftR-1][bottomLeftC].setTo(0, 'Q');
                           game[bottomLeftR-1][bottomLeftC+2].setTo(0, 'Q');
                           game[bottomLeftR][bottomLeftC+2].setTo(0, 'Q');
                           game[bottomLeftR][bottomLeftC].setTo(num, 'B');
                           game[bottomLeftR][bottomLeftC+1].setTo(num, 'B');
                           game[bottomLeftR-2][bottomLeftC+1].setTo(num, 'B');
                        }
                        else
                        {
                           game[bottomLeftR-1][bottomLeftC+1].setTo(0, 'Q');
                           game[bottomLeftR-2][bottomLeftC+1].setTo(0, 'Q');
                           game[bottomLeftR][bottomLeftC+2].setTo(num, 'B');
                           game[bottomLeftR-1][bottomLeftC].setTo(num, 'B');
                        }
               else
                  if(blocks[0]<3)
                     if(rots%4<1)
                     {
                        game[bottomLeftR-1][bottomLeftC].setTo(num, 'P');
                        game[bottomLeftR-2][bottomLeftC].setTo(num, 'P');
                        game[bottomLeftR][bottomLeftC+1].setTo(0, 'Q');
                        game[bottomLeftR][bottomLeftC+2].setTo(0, 'Q');
                     }
                     else
                        if(rots%4<2)
                        {
                           game[bottomLeftR][bottomLeftC].setTo(0, 'Q');
                           game[bottomLeftR-2][bottomLeftC].setTo(0, 'Q');
                           game[bottomLeftR-1][bottomLeftC+2].setTo(num, 'P');
                           game[bottomLeftR][bottomLeftC+1].setTo(num, 'P');
                        }
                        else
                           if(rots%4<3)
                           {
                              game[bottomLeftR-1][bottomLeftC+2].setTo(0, 'Q');
                              game[bottomLeftR-2][bottomLeftC+1].setTo(num, 'P');
                           }
                           else
                           {
                              game[bottomLeftR-1][bottomLeftC].setTo(0, 'Q');
                              game[bottomLeftR-2][bottomLeftC+1].setTo(0, 'Q');
                              game[bottomLeftR][bottomLeftC].setTo(num, 'P');
                              game[bottomLeftR][bottomLeftC+2].setTo(num, 'P');
                           }
                  else
                     if(blocks[0]<4)
                        if(rots%2<1)
                           for(int i=1; i<4; i++)
                           {
                              game[bottomLeftR-i][bottomLeftC].setTo(num, 'L');
                              game[bottomLeftR][bottomLeftC+i].setTo(0, 'Q');
                           }
                        else
                           for(int i=1; i<4; i++)
                           {
                              game[bottomLeftR-i][bottomLeftC].setTo(0, 'Q');
                              game[bottomLeftR][bottomLeftC+i].setTo(num, 'L');
                           }
                     else
                        if(blocks[0]<6)
                           if(rots%4<1)
                           {
                              game[bottomLeftR-1][bottomLeftC].setTo(num, 'O');
                              game[bottomLeftR-2][bottomLeftC].setTo(num, 'O');
                              game[bottomLeftR][bottomLeftC+2].setTo(0, 'Q');
                              game[bottomLeftR-1][bottomLeftC+2].setTo(0, 'Q');
                           }
                           else
                              if(rots%4<2)
                              {
                                 game[bottomLeftR][bottomLeftC+1].setTo(0, 'Q');
                                 game[bottomLeftR-2][bottomLeftC].setTo(0, 'Q');
                                 game[bottomLeftR-1][bottomLeftC+1].setTo(num, 'O');
                                 game[bottomLeftR-1][bottomLeftC+2].setTo(num, 'O');
                              }
                              else
                                 if(rots%4<3)
                                 {
                                    game[bottomLeftR-1][bottomLeftC].setTo(0, 'Q');
                                    game[bottomLeftR-1][bottomLeftC+2].setTo(0, 'Q');
                                    game[bottomLeftR][bottomLeftC].setTo(0, 'Q');
                                    game[bottomLeftR-2][bottomLeftC].setTo(num, 'O');
                                    game[bottomLeftR-2][bottomLeftC+1].setTo(num, 'O');
                                    game[bottomLeftR][bottomLeftC+1].setTo(num, 'O');
                                 }
                                 else
                                 {
                                    game[bottomLeftR-1][bottomLeftC+1].setTo(0, 'Q');
                                    game[bottomLeftR-2][bottomLeftC+1].setTo(0, 'Q');
                                    game[bottomLeftR-2][bottomLeftC].setTo(0, 'Q');
                                    game[bottomLeftR][bottomLeftC].setTo(num, 'O');
                                    game[bottomLeftR][bottomLeftC+2].setTo(num, 'O');
                                    game[bottomLeftR-1][bottomLeftC+2].setTo(num, 'O');
                                 }
                        else
                           if(rots%2<1)
                           {
                              game[bottomLeftR][bottomLeftC].setTo(num, 'R');
                              game[bottomLeftR][bottomLeftC+1].setTo(0, 'Q');
                              game[bottomLeftR][bottomLeftC+2].setTo(0, 'Q');
                              game[bottomLeftR-2][bottomLeftC+1].setTo(num, 'R');
                           }
                           else
                           {
                              game[bottomLeftR][bottomLeftC].setTo(0, 'Q');
                              game[bottomLeftR][bottomLeftC+1].setTo(num, 'R');
                              game[bottomLeftR][bottomLeftC+2].setTo(num, 'R');
                              game[bottomLeftR-2][bottomLeftC+1].setTo(0, 'Q');
                           }
            rots++;
         }
      }
      public static boolean rowIsFull(int r)
      {
         for(int c=0; c<10; c++)
            if(game[r][c].color()=='Q')
               return false;
         return true;
      }
      public static void clearFullRows()
      {
         add=1;
         for(int r=0; r<20; r++)
            if(rowIsFull(r))
            {
               add*=10;
               for(int c=0; c<10; c++)
                  game[r][c].setTo(0, 'Q');
               for(int row=r; row>0; row--)
                  for(int c=0; c<10; c++)
                     game[row][c].setTo(game[row-1][c]);
            }
         if(add>1)
         {
            score+=add;
            frame.repaint();
         }
      }
      public static String getScore()
      {
         return "Score: "+score;
      }
      public static void main(String[] args)
      {
         createPanel();
         fillBoard();
         int delay = 750;
         ActionListener taskPerformer = 
            new ActionListener() {
               public void actionPerformed(ActionEvent evt) {
                  if(canGoDown())
                     goDown();
                  else
                     spawnBlock();
                  tp.repaint();
               }
            };
         new Timer(delay, taskPerformer).start();
         while(true)
         {
            kl.setCode(0);
            while(kl.getCode()<1)
            {
            
            }
            tp.repaint();
         }
      }
   }