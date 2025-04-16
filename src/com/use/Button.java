// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

import java.awt.*;
import java.awt.event.*;

import com.use.Box2D;

public class Button extends Box2D 
{
   private String text;

   public Button(int width, int height, String text)
   {
        super(width, height);

        this.text = text;
   }

   public void paint(Graphics g, Color color)
   {
        drawBox(g, color);
        g.drawString(text, loc[0], loc[1] + 15);
   }
}