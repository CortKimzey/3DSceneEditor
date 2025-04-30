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

     public Button(int x, int y, String text)
     {
          super(x, y);
          this.text = text;
     }
}