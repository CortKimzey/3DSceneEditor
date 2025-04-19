// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import com.use.Button;
import com.use.BoxElement;

public class DropDown extends BoxElement 
{
    private ArrayList<Button> dropDownList = new ArrayList<Button>(0);
    private int size = 0;

    public DropDown(int width, int height, int x, int y)
    {
        super(width, height, x, y);
    }

    public void addOption(Button btnIn)
    {
        size++;
        dropDownList.add(btnIn);
    }

    public void onClick(int x, int y)
    {
        for (int z = 0; z < size; z++)
            if (dropDownList.get(z).isClicked(x,y))
            {
                dropDownList.get(z).onClick();
                return;
            }
    }

    public void setInactive()
    {
        active = false;
    }

    public int getCase(int x, int y)
    {
        for (int z = 0; z < size; z++)
            if (dropDownList.get(z).isClicked(x,y))
                return z;
        return -1;
    }

    public void paint(Graphics2D g, Color color)
    {
        drawBox(g, color);
        for (int x = 0; x < size; x++)
            dropDownList.get(x).paint(g, color);
    }
}