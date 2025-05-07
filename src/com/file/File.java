// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: File button to hold drop down options to change what the application is doing (Never Used)

package com.file;

import java.awt.*;
import java.awt.event.*;

import com.use.DropDown;
import com.use.Button;
import com.use.BoxElement;

public class File extends Button 
{
    private DropDown drop;

    public File(int x, int y, int width, int height)
    {
        super(x, y, width, height, "File");

        drop = new DropDown(150, 100, cnr[3].getX(), cnr[3].getY());
    }

    public boolean isClicked(int x, int y)
    {
        if (x > loc[0] && x < (loc[0] + width) && y > loc[1] && y < (loc[1] + height))
            return true;
        else if (drop.isActive() && drop.isClicked(x,y))
            return true;
        else
            return false;
    }

    public void dropDown()
    {
        active = !active;
        drop.setActive(!drop.isActive());
    }

    public void setInactive()
    {
        active = false;
        drop.setInactive();
    }

    public int getCase(int x, int y)
    {
        return 0;
    }

    public void paint(Graphics2D g, Color color)
    {
        drawButton(g, color);

        if (drop.isActive())
            drop.paint(g, color);
    }
}
