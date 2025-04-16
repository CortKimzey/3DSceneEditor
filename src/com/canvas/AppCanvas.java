// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description:

package com.canvas;

import java.io.IOException;

import java.awt.*;
import java.awt.event.*;
import com.menu.Menu;

public class AppCanvas extends Canvas implements KeyListener {
    private Menu menu;
    private int width;
    private int height;
    Dimension d;

    public AppCanvas(int width, int height) throws IOException
    {
        setSize(width, height);
        setFocusable(true);
        requestFocus();

        addKeyListener(this);

        d = getSize();
        width = d.width - 1;
        height = d.height - 1;
        menu = new Menu(width, height);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {

                try {
                    menu.onClick(evt.getX(), evt.getY());
                } catch (IOException e){
                    System.out.println("Bad Click");
                }
                repaint();
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        menu.keyPressed(getChar(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void updateSize()
    {
        d = getSize();
        width = d.width;// - 1;
        height = d.height;// - 1;
        menu.setSize(width, height);
    }

    public void paint(Graphics g)
    {
        updateSize();
        menu.paint(g);
    }

    private char getChar(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_0:
                return '0';
            case KeyEvent.VK_1:
                return '1';
            case KeyEvent.VK_2:
                return '2';
            case KeyEvent.VK_3:
                return '3';
            case KeyEvent.VK_4:
                return '4';
            case KeyEvent.VK_5:
                return '5';
            case KeyEvent.VK_6:
                return '6';
            case KeyEvent.VK_7:
                return '7';
            case KeyEvent.VK_8:
                return '8';
            case KeyEvent.VK_9:
                return '9';
        }
        return 'A';
    }
}