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
     protected int strDepth = 10;
     private Font font = new Font("Dialog", Font.PLAIN, 12);

     public Button(int x, int y, String text)
     {
          super(x, y);
          this.text = text;
     }

     public Button(int x, int y, int width, int height, String text)
     {
          super(x, y, width, height);
          this.text = text;
          strDepth += (this.height - 10) / 2;
     }

     public String getName()
     {
          return text;
     }

     public void drawButton(Graphics2D g, Color color)
     {
          FontMetrics fontDetail = g.getFontMetrics(font);
          int strBack = (this.width - fontDetail.stringWidth(text)) / 2;
          drawBox(g, color);
          g.drawString(text, loc[0] + strBack, loc[1] + strDepth);
     }

     public void drawText(Graphics2D g, Color color)
     {
          g.setColor(color);
          g.drawString(text, loc[0] + 5, loc[1] + strDepth);
     }

     public void onClick()
     {
          
     }
}