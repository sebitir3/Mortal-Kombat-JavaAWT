import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class Main {
  // Set up all the game variables to make the window work
  final int FPS = 60;
  final int WIDTH = 799;
  final int HEIGHT = 300;
  final String TITLE = "MortalKombat";
  Drawing drawing;

  Scorpion scorpion = new Scorpion(250, 150, 74, 139);
  SubZero subZero = new SubZero(549, 150, 74, 139);
  Display display = new Display(scorpion, subZero);
  Animation animation = new Animation(scorpion, subZero);

  public Main() {
    // initialize anything you need to before we see it on the screen
    start();
    // create the screen and show it
    drawing = new Drawing(TITLE, WIDTH, HEIGHT, FPS, this);
    // make sure key and mouse events work
    // this is like an actionListener on buttons except it's the keyboard and mouse
    drawing.addKeyListener(new Keyboard());
    Mouse m = new Mouse();
    drawing.addMouseListener(m);
    drawing.addMouseMotionListener(m);
    drawing.addMouseWheelListener(m);
    // start the game loop
    drawing.loop();
  }

  public void start() {

  }

  // put all of your drawing code in this method
  public void paintComponent(Graphics2D g) {
    this.display.draw(g);
    this.scorpion.draw(g);
    this.subZero.draw(g);
    this.animation.draw(g);

  }

  // this is the main game loop
  // it tries to update as fast as the frames per second you set above (deault is
  // 60 updates each second)
  public void loop() {
    this.scorpion.update(subZero);
    this.subZero.update(scorpion);
    this.animation.update();
    this.display.update();
  }

  // Used to implement any of the Mouse Actions
  private class Mouse extends MouseAdapter {

    // if a mouse button has been pressed down
    @Override
    public void mousePressed(MouseEvent e) {

    }

    // if a mouse button has been released
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    // if the scroll wheel has been moved
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    // if the mouse has moved positions
    @Override
    public void mouseMoved(MouseEvent e) {

    }
  }

  // Used to implements any of the Keyboard Actions
  private class Keyboard extends KeyAdapter {

    // if a key has been pressed down
    @Override
    public void keyPressed(KeyEvent e) {
      // determine which key was pressed
      int key = e.getKeyCode();
      if (!scorpion.isFrozen() && !scorpion.isStunned()) {
        if (key == KeyEvent.VK_A) {
          
          scorpion.setMoveLeft(true);
        }
        if (key == KeyEvent.VK_D) {

          scorpion.setMoveRight(true);
        }
        if (key == KeyEvent.VK_W && scorpion.isOnGround()) {
          scorpion.jump();
        }
        if (key == KeyEvent.VK_S) {
          scorpion.switchCrouch(true);
        }
        if (key == KeyEvent.VK_Q && !scorpion.isMoveUsed()) {
          scorpion.kickWasClicked();
          scorpion.setMoveUsedTime(System.currentTimeMillis());
          scorpion.setMoveUsed(true);
        }
        if (key == KeyEvent.VK_E && !scorpion.isMoveUsed()) {
          scorpion.setMoveUsed(true);
          scorpion.setMoveUsedTime(System.currentTimeMillis());
          scorpion.punchWasClicked();
        }
        if (key == KeyEvent.VK_CAPS_LOCK) {
          scorpion.setBlocking(true);
        }
        if (key == KeyEvent.VK_G && !scorpion.isSpecialUsed()) {
          scorpion.Grapple(subZero);
        }
      }

      if (!subZero.isFrozen() && !subZero.isStunned()) {
        if (key == KeyEvent.VK_I && subZero.isOnGround()) {
          subZero.jump();
        }
        if (key == KeyEvent.VK_J) {
          subZero.setMoveLeft(true);
        }
        if (key == KeyEvent.VK_K) {
          subZero.switchCrouch(true);
        }
        if (key == KeyEvent.VK_L) {
          subZero.setMoveRight(true);
        }
        if (key == KeyEvent.VK_U && !subZero.isMoveUsed()) {
          subZero.kickWasClicked();
          subZero.setMoveUsedTime(System.currentTimeMillis());
          subZero.setMoveUsed(true);
        }
        if (key == KeyEvent.VK_O && !subZero.isMoveUsed()) {
          subZero.punchWasClicked();
          subZero.setMoveUsedTime(System.currentTimeMillis());
          subZero.setMoveUsed(true);
        }

        if (key == KeyEvent.VK_ENTER) {
          subZero.setBlocking(true);
        }
        if (key == KeyEvent.VK_9) {
          if (subZero.isOnGround() && !subZero.isSpecialUsed()) {
            subZero.IceBall(scorpion);
          }
        }
      }

    }

    // if a key has been released
    @Override
    public void keyReleased(KeyEvent e) {
      // determine which key was pressed
      int key = e.getKeyCode();
      if (key == KeyEvent.VK_A) {
        scorpion.setMoveLeft(false);
      }
      if (key == KeyEvent.VK_D) {
        scorpion.setMoveRight(false);
      }
      if (key == KeyEvent.VK_S) {
        scorpion.switchCrouch(false);
      }
      if (key == KeyEvent.VK_J) {
        subZero.setMoveLeft(false);
      }
      if (key == KeyEvent.VK_K) {
        subZero.switchCrouch(false);
      }
      if (key == KeyEvent.VK_L) {
        subZero.setMoveRight(false);
      }

      if (key == KeyEvent.VK_CAPS_LOCK) {
        scorpion.setBlocking(false);
      }
      if (key == KeyEvent.VK_ENTER) {
        subZero.setBlocking(false);
      }
    }
  }

  // the main method that launches the game when you hit run
  public static void main(String[] args) {
    Main game = new Main();
  }

}