// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description:

package com.canvas;

import java.io.IOException;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.event.*;
import com.menu.Menu;

public class AppCanvas extends Canvas implements KeyListener {
    private DimCanvas d;
    private Menu menu;
    private BufferedImage buffer;


    public AppCanvas(int width, int height) throws IOException
    {
        setSize(width, height);
        d = new DimCanvas(getWidth() - 1, getHeight() - 1);

        setFocusable(true);
        requestFocus();
        addKeyListener(this);

        buffer = new BufferedImage(d.getWidth() + 1, d.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);

        menu = new Menu(d);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (evt.getButton() == 1)
                {
                    try {
                        menu.onClick(evt.getX(), evt.getY());
                    } catch (IOException e){
                        System.out.println("Bad Click");
                    }
                }
                else if (evt.getButton() == 3)
                {
                    System.out.println("Right Click");
                }

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent evt)
            {
                //System.out.println("Bye Mouse");
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                menu.onDrag(e.getX(), e.getY());
                repaint();
            }
        });

        //Add Component Listener to watch for application resize
    }

    @Override
    public void update(Graphics g)
    {
        paint(g);
    }

    @Override
    public void paint(Graphics g)
    {
        updateSize();

        Graphics2D g2 = buffer.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,d.getWidth(),d.getHeight());

        menu.paint(g2);

        g2.dispose();
        g.drawImage(buffer,0,0,this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        menu.keyPressed(getChar(e));
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void updateSize()
    {
        d.setDim(getWidth() - 1, getHeight() - 1);
        
        if (buffer == null || buffer.getWidth() != (d.getWidth() + 1) || buffer.getHeight() != (d.getHeight() + 1))
            buffer = new BufferedImage(d.getWidth() + 1, d.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
        
        menu.updateSize();
    }

    private char getChar(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                return 'W';
            case KeyEvent.VK_A:
                return 'A';
            case KeyEvent.VK_S:
                return 'S';
            case KeyEvent.VK_D:
                return 'D';
            case KeyEvent.VK_SPACE:
                return 32;
            case KeyEvent.VK_SHIFT:
                return 14;
            case KeyEvent.VK_R:
                return 'R';
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
            case KeyEvent.VK_PERIOD:
                return '.';
            case KeyEvent.VK_BACK_SPACE:
                return 8;
            default:
                return 'B';
        }
    }
}