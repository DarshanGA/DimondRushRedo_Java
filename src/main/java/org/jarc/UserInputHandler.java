package org.jarc;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInputHandler implements KeyListener {

    public boolean moveUp = false,
            moveLeft = false,
            moveRight = false,
            moveDown = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_W){

            moveUp = true;

        }
        else if(e.getKeyCode() == KeyEvent.VK_A){

            moveLeft = true;

        }
        else if(e.getKeyCode() == KeyEvent.VK_S){

            moveDown = true;

        }
        else if(e.getKeyCode() == KeyEvent.VK_D){

            moveRight = true;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_W){

            moveUp = false;

        }
        else if(e.getKeyCode() == KeyEvent.VK_A){

            moveLeft = false;

        }
        else if(e.getKeyCode() == KeyEvent.VK_S){

            moveDown = false;

        }
        else if(e.getKeyCode() == KeyEvent.VK_D){

            moveRight = false;

        }
    }
}
