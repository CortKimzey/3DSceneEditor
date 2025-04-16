// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

public class Point2D {
   int x;
   int y;

   public Point2D(int x, int y)
   {
        this.x = x;
        this.y = y;
   }

   public void setXY(int x, int y)
   {
     this.x = x;
     this.y = y;
   }

   public void setX(int x)
   {
     this.x = x;
   }

   public void setY(int y)
   {
     this.y = y;
   }

   public int getX()
   {
        return x;
   }

   public int getY()
   {
        return y;
   }

   public int[] getLoc()
   {
        return new int[]{x,y};
   }

   public String toString()
   {
     return ("X: " + x + ", Y: " + y);
   }
}