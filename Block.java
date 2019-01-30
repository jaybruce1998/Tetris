   public class Block
   {
      int num;
      char color;
      public Block()
      {
         num=0;
         color='Q';
      }
      public void setTo(Block b)
      {
         num=b.num();
         color=b.color();
      }
      public void setTo(int n, char c)
      {
         num=n;
         color=c;
      }
      public int num()
      {
         return num;
      }
      public char color()
      {
         return color;
      }
   }