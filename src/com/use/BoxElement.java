// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

import java.awt.*;
import java.awt.event.*;

public class BoxElement 
{
    protected boolean active = false;
    protected Point2D[] cnr = new Point2D[4];

    protected int[] loc = new int[]{0,0};

    protected int width, height;

    public BoxElement(int width, int height)
    {
      cnr[0] = new Point2D(0,0);
      cnr[1] = new Point2D(width,0);
      cnr[2] = new Point2D(width, height);
      cnr[3] = new Point2D(0, height);

      this.width = width;
      this.height = height;
    }

    public BoxElement(int width, int height, int x, int y)
    {
      cnr[0] = new Point2D(x,y);
      cnr[1] = new Point2D(x + width,y);
      cnr[2] = new Point2D(x + width, y + height);
      cnr[3] = new Point2D(x, y + height);

      this.width = width;
      this.height = height;
      loc[0] = x;
      loc[1] = y;
    }

    public void setW(int width)
    {
      this.width = width;

      cnr[1].setX(loc[0] + width);
      cnr[2].setX(loc[0] + width);
    }

    public void setH(int height)
    {
      this.height = height;

      cnr[2].setY(loc[1] + height);
      cnr[3].setY(loc[1] + height);
    }

    public void setWH(int width, int height)
    {
      setW(width);
      setH(height);
    }

    public void setLoc(int x, int y)
    {
      loc[0] = x;
      loc[1] = y;
      cnr[0].setXY(x,y);
      cnr[1].setXY(x + width,y);
      cnr[2].setXY(x + width, y + height);
      cnr[3].setXY(x, y + height);
    }

    public int getX()
    {
      return loc[0];
    }

    public int getY()
    {
      return loc[1];
    }

    public int getW()
    {
      return width;
    }

    public int getH()
    {
      return height;
    }

    public Point2D getCNR(int corner)
    {
      return cnr[corner];
    }

    public int tblr(int TBLR)
    {
      if (TBLR == 0)
            return loc[1];
      else if (TBLR == 1)
            return loc[1] + height;
      else if (TBLR == 2)
            return loc[0];
      else
            return loc[0] + width;
    }

    public boolean isClicked(int x, int y)
    {
        if (x > loc[0] && x < (loc[0] + width) && y > loc[1] && y < (loc[1] + height))
          return true;
        else
          return false;
    }

    public boolean isActive()
    {
      return active;
    }

    public void setActive(boolean next)
    {
      active = next;
    }


    public void print()
    {
      for (int x = 0; x < 4; x++)
        System.out.println("Point " + x + ": " + cnr[x]);
    }

    public void drawBox(Graphics2D g, Color color)
    {
      g.setColor(color);
      g.fillRect(loc[0], loc[1], width, height);

      g.setColor(Color.BLACK);
      for (int x = 0, y = 1; x < 4; x++, y = (++y == 4)? 0 : y)
            g.drawLine(cnr[x].getX(), cnr[x].getY(), cnr[y].getX(), cnr[y].getY());
    }
}