// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

import java.awt.*;
import java.awt.event.*;

import com.use.BoxElement;

public class Button extends BoxElement 
{
     protected String text;
     protected int strDepth;

     public Button(int width, int height, int x, int y, String text)
     {
          super(width, height, x, y);

          strDepth = (height - 10) / 2;
          strDepth += 10;

          this.text = text;
     }

     public void onClick()
     {
          System.out.println("Low Click");
     }

   public void paint(Graphics2D g, Color color)
   {
        drawBox(g, color);
        g.drawString(text, loc[0] + 5, loc[1] + strDepth);
   }
}