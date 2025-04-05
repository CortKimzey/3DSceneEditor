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
}