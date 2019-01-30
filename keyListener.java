import java.awt.event.*;
public class keyListener extends KeyAdapter
{
   private int key = 0;
   public void keyPressed(KeyEvent k)
   {
      key=k.getKeyCode();
      if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S||key==KeyEvent.VK_NUMPAD2)
      {
         if(Tetris.canGoDown())
            Tetris.goDown();
      }
      else
         if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A||key==KeyEvent.VK_NUMPAD4)
            Tetris.goLeft();
         else
            if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D||key==KeyEvent.VK_NUMPAD6)
               Tetris.goRight();
            else
               if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W||key==KeyEvent.VK_NUMPAD8)
                  Tetris.rotate();
               else
                  if(key==KeyEvent.VK_SPACE)
                     while(Tetris.canGoDown())
                        Tetris.goDown();
   }
   public void setCode(int c)
   {
      key=c;
   }
   public int getCode()
   {
      return key;
   }
}