// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Input element that recieves and hold inputed number characters

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
          super(x, y, width, height);
     }

     public void keyPressed(char in)
     {
          if (in == 8)
          {
               String hold = "";
               for (int x = 0; x < (size - 1); x++)
                    hold += text.charAt(x);
               
               if (size > 0)
                    size--;
               text = hold;
          }
          else if ((in > 47 && in < 58) || in == 46 || in == 45)
          {
               text += in;
               size++;
          }
     }

     public float getData()
     {
          if (size <= 0)
               return 0;
          else
               return Float.parseFloat(text);
     }

     public void setInactive()
     {
          active = false;
     }

     public void reset()
     {
          active = false;
          text = "";
          size = 0;
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
