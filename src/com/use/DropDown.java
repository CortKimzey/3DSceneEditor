// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Drop down that can hold different button elements to be displayed when the dropdown is active

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

    public void setInactive()
    {
        active = false;
    }

    public void onClick(int x, int y)
    {
        dropDownList.forEach(dd -> {
            if (dd.isClicked(x,y))
                dd.onClick();
        });
    }

    public void addOption(Button btnIn)
    {
        size++;
        dropDownList.add(btnIn);
    }
    
    public void paint(Graphics2D g, Color color)
    {
        drawBox(g, color);
        dropDownList.forEach(dd -> {
            dd.drawButton(g, color);
        });
    }
}
