// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

/**
package com.file;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

import com.use.Button;

public class Import extends Button 
{
    public Import(int width, int height, int x, int y)
    {
        super(width, height, x, y, "Import");
    }

    public static java.io.File testOut()
    {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

         int returnValue = fileChooser.showOpenDialog(null);
         if (returnValue == JFileChooser.APPROVE_OPTION) 
            return fileChooser.getSelectedFile();

        return null;
    }

    public void paint(Graphics2D g, Color color)
    {
        drawBox(g, color);
        g.drawString(text, loc[0] + 5, loc[1] + strDepth);
    }
}
*/