// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

import java.awt.*;
import java.awt.event.*;

import com.use.BoxElement;

public class Input extends BoxElement 
{
     private String text = "";
     int size = 0;

     public Input(int width, int height, int x, int y)
     {
          super(width, height, x, y);
     }

     public void keyPressed(char in)
     {
          if (in == 8)
          {
               //System.out.println(text.charAt(size - 1));
               String hold = "";
               for (int x = 0; x < (size - 1); x++)
                    hold += text.charAt(x);
               size--;
               text = hold;
          }
          else if ((in > 47 && in < 58) || in == 46)
          {
               text += in;
               size++;
          }
     }

     public double getData()
     {
          return Double.parseDouble(text);
     }

     public void setInactive()
     {
          active = false;
     }

     public void onClick()
     {
          active = true;
     }

     public void paint(Graphics2D g)
     {
          FontMetrics metrics = g.getFontMetrics(g.getFont());
          int width = metrics.stringWidth(text) + 4;
          //System.out.println(metrics.stringWidth("File"));
          drawBox(g, Color.white);
          g.setColor(Color.black);
          g.drawString(text, tblr(3) - width, loc[1] + 13);
     }

     public String toString()
     {
          return text;
     }
}