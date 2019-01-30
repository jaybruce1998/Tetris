import javax.swing.*;
import java.awt.*;
public class TetrisPanel extends JPanel
{
   String location;
   Block[][] game;
   int[] blocks;
   ImageIcon[] icons;
   public TetrisPanel(Block[][] g, int[] b)
   {
      super();
      game=g;
      blocks=b;
      icons=new ImageIcon[7];
      location=System.getProperty("user.dir")+"/";
      for(int i=0; i<7; i++)
         icons[i]=new ImageIcon(location+i+".png");
   }
   public void paintComponent(Graphics g)
   {
      for(int r=0; r<10; r++)
         for(int c=0; c<20; c++)
            g.drawImage(new ImageIcon(location+game[c][r].color()+".png").getImage(), r*35, c*35, 35, 35, null);
      for(int i=1; i<4; i++)
         g.drawImage(icons[blocks[i]].getImage(), 350, i*105-105, 105, 105, null);
         g.drawString(Tetris.getScore(), 350, 325);
   }
}