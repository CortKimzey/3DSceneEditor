// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

import java.awt.*;
import java.awt.event.*;

import com.use.Box2D;

public class Input extends Box2D 
{
    private boolean active = false;
   private String text = "";

   public Input(int width, int height, int x, int y)
   {
        super(width, height, x, y);
   }

   public void isClicked()
   {
        System.out.println("Hello world: ");
        //java.util.Scanner keyboard = new java.util.Scanner(System.in);
        //text = keyboard.nextLine();
        System.out.println(text);
   }

   public void paint(Graphics g)
   {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int width = metrics.stringWidth(text) + 4;
        drawBox(g, Color.white);
        g.setColor(Color.black);
        g.drawString(text, getTBLR(3) - width, loc[1] + 13);
   }
}