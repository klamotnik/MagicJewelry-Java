package SEngine;

import SEngine.Interfaces.Tickable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Level extends JPanel implements Tickable, KeyListener {
    protected Level() {
        setSize(640,480);
        setBackground(Color.BLACK);
        setLayout(null);
        setVisible(true);
    }

    public void tick(int deltaTime){
        for(Component c : getComponents()){
            if(c instanceof Tickable){
                if(((Tickable) c).canTick())
                    ((Tickable) c).tick(deltaTime);
            }
        }
    }

    public boolean canTick(){
        return true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(Component c : getComponents()){
            if(c instanceof KeyListener){
                ((KeyListener) c).keyPressed(e);
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}