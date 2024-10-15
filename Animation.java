import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage scorpionSheet;
    private BufferedImage subzeroSheet;
    private int frameWidth;
    private int frameNum;
    private int animFrame;
    private int animDelay;
    private int delay;
    private HandleAnimation scorpionPunch;
    private HandleAnimation scorpionUppercut;
    private HandleAnimation scorpionHighkick;
    private HandleAnimation scorpionJumpkick;
    private HandleAnimation scorpionSweep;
    private HandleAnimation scorpionIdle;
    private HandleAnimation scorpionWalk;
    private HandleAnimation scorpionJump;
    private HandleAnimation scorpionSpecialFirst;
    private HandleAnimation scorpionSpecialLast;

    private HandleAnimation subzeroPunch;
    private HandleAnimation subzeroUppercut;
    private HandleAnimation subzeroHighkick;
    private HandleAnimation subzeroJumpkick;
    private HandleAnimation subzeroSweep;
    private HandleAnimation subzeroIdle;
    private HandleAnimation subzeroWalk;
    private HandleAnimation subzeroJump;
    private HandleAnimation subzeroSpecial;

    private Scorpion scorpion;
    private SubZero subZero;

    private int animOffsetX;
    private int animOffsetY;

    //constructor
    public Animation(Scorpion scorpion, SubZero subZero) {
        this.scorpionSheet = loadImage("ScorpionSprites.png");
        this.subzeroSheet = loadImage("SubzeroSprites.png");
        this.frameWidth = 234;
        this.animOffsetX = 57;
        this.animOffsetY = 45;

        // SCORPION ATTACKS as new handle animation
        this.scorpionPunch = new HandleAnimation(scorpionSheet, 28, 579, 234, 221, 5, 3, false);
        this.scorpionUppercut = new HandleAnimation(scorpionSheet, 1439, 2566, 234, 221, 5, 4, false);
        this.scorpionHighkick = new HandleAnimation(scorpionSheet, 28, 1570, 234, 221, 5, 5, false);
        this.scorpionJumpkick = new HandleAnimation(scorpionSheet, 3779, 3565, 234, 221, 5, 3, false);
        this.scorpionSweep = new HandleAnimation(scorpionSheet, 1904, 1570, 234, 221, 8, 3, false);
        this.scorpionIdle = new HandleAnimation(scorpionSheet, 28, 59, 234, 221, 7, 4, true);
        this.scorpionWalk = new HandleAnimation(scorpionSheet, 1905, 59, 234, 221, 9, 4, true);
        this.scorpionJump = new HandleAnimation(scorpionSheet, 499, 3562, 234, 221, 7, 4, false);
        this.scorpionSpecialFirst = new HandleAnimation(scorpionSheet, 28, 6644, 234, 221, 3, 1, false);
        this.scorpionSpecialLast = new HandleAnimation(scorpionSheet, 730, 6644, 234, 221, 4, 1, false);
        this.scorpion = scorpion;
        this.scorpionIdle.start();

        // SUBZERO ATTACKS
        this.subzeroPunch = new HandleAnimation(subzeroSheet, 28, 579, 234, 221, 5, 3, false);
        this.subzeroUppercut = new HandleAnimation(subzeroSheet, 1439, 2566, 234, 221, 5, 4, false);
        this.subzeroHighkick = new HandleAnimation(subzeroSheet, 28, 1570, 234, 221, 5, 5, false);
        this.subzeroJumpkick = new HandleAnimation(subzeroSheet, 3779, 3565, 234, 221, 5, 3, false);
        this.subzeroSweep = new HandleAnimation(subzeroSheet, 1904, 1570, 234, 221, 8, 3, false);
        this.subzeroIdle = new HandleAnimation(subzeroSheet, 28, 59, 234, 221, 12, 4, true);
        this.subzeroWalk = new HandleAnimation(subzeroSheet, 3075, 59, 234, 221, 9, 3, true);
        this.subzeroJump = new HandleAnimation(subzeroSheet, 499, 3562, 234, 221, 7, 4, false);
        this.subzeroSpecial = new HandleAnimation(subzeroSheet, 28, 6644, 283, 215, 10, 4, false);

        this.subZero = subZero;
        this.subzeroIdle.start();
    }

    //load in spritesheets as buffered image
    public BufferedImage loadImage(String filename) {
        BufferedImage Image = null;
        try {
            Image = ImageIO.read(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Image;
    }

    public void draw(Graphics2D g) {
        // SCORPION ATTACKS
        if (!this.scorpionPunch.isFinished()) {
            this.scorpionPunch.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } else if (!this.scorpionUppercut.isFinished()) {
            this.scorpionUppercut.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } else if (!this.scorpionHighkick.isFinished()) {
            this.scorpionHighkick.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } else if (!this.scorpionJumpkick.isFinished()) {
            this.scorpionJumpkick.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } else if (!this.scorpionSweep.isFinished()) {
            this.scorpionSweep.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } else if (!this.scorpionSpecialFirst.isFinished()) {
            this.scorpionSpecialFirst.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } else if (!this.scorpionSpecialLast.isFinished()) {
            this.scorpionSpecialLast.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } 
        else if (!this.scorpionJump.isFinished()) {
            this.scorpionSweep.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } else if(this.scorpionWalk.isRunning()){
            this.scorpionWalk.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        } else if(!this.scorpionIdle.isFinished()){
            this.scorpionIdle.draw(g, scorpion.getHitBox().x - animOffsetX, scorpion.getHitBox().y - animOffsetY);
        }

        // SUBZERO ATTACKS
        if (!this.subzeroPunch.isFinished()) {
            this.subzeroPunch.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        } else if (!this.subzeroUppercut.isFinished()) {
            this.subzeroUppercut.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        } else if (!this.subzeroHighkick.isFinished()) {
            this.subzeroHighkick.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        } else if (!this.subzeroJumpkick.isFinished()) {
            this.subzeroJumpkick.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        } else if (!this.subzeroSweep.isFinished()) {
            this.subzeroSweep.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        } else if (!this.subzeroSpecial.isFinished()) {
            this.subzeroSpecial.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        } 
        else if (!this.subzeroJump.isFinished()) {
            this.subzeroSweep.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        } else if(this.subzeroWalk.isRunning()){
            this.subzeroWalk.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        } else if(!this.subzeroIdle.isFinished()){
            this.subzeroIdle.draw(g, subZero.getHitBox().x - animOffsetX, subZero.getHitBox().y - animOffsetY);
        }
    }

    public void update() {
        //SCORPION
        if (scorpion.isPunch()) {
            this.scorpionPunch.start();
        } else if (scorpion.isUpperCut()) {
            this.scorpionUppercut.start();
        } else if (scorpion.isHighKick()) {
            this.scorpionHighkick.start();
        } else if (scorpion.isJumpKick()) {
            this.scorpionJumpkick.start();
        } else if (scorpion.isSweep()) {
            this.scorpionSweep.start();
        } else if (scorpion.isSpecialFirst()) {
            this.scorpionSpecialFirst.start();
        } else if (scorpion.isSpecialLast()) {
            this.scorpionSpecialLast.start();
        } 
        else if (scorpion.isJumping()) {
            this.scorpionJump.start();
        } else if(scorpion.isMoveRight() || scorpion.isMoveLeft()){
            this.scorpionWalk.start();
            System.out.println("test");
        } else {
            this.scorpionWalk.stop();
            this.scorpionIdle.start();  
        }

        if(!scorpion.isJumping()){
            this.scorpionJump.stop();
        }

        //scorpion animation update
        this.scorpionPunch.update();
        this.scorpionUppercut.update();
        this.scorpionHighkick.update();
        this.scorpionJumpkick.update();
        this.scorpionSweep.update();
        this.scorpionSpecialFirst.update();
        this.scorpionSpecialLast.update();
        this.scorpionIdle.update();
        this.scorpionWalk.update();
        this.scorpionJump.update();
        
        //SUBZERO animation start and stop
        if (subZero.isPunch()) {
            this.subzeroPunch.start();
        } else if (subZero.isUpperCut()) {
            this.subzeroUppercut.start();
        } else if (subZero.isHighKick()) {
            this.subzeroHighkick.start();
        } else if (subZero.isJumpKick()) {
            this.subzeroJumpkick.start();
        } else if (subZero.isSweep()) {
            this.subzeroSweep.start();
        } else if (subZero.isSpecial()) {
            this.subzeroSpecial.start();
        }     
        else if (subZero.isJumping()) {
            this.subzeroJump.start();
        } else if(subZero.isMoveRight() || subZero.isMoveLeft()){
            this.subzeroWalk.start();
        } else {
            //when stop walking start idle
            this.subzeroWalk.stop();
            this.subzeroIdle.start();       
        }

        //stop jumping animation when not jumping
        if(!subZero.isJumping()){
            this.subzeroJump.stop();
        }

        //subzero update animation
        this.subzeroPunch.update();
        this.subzeroUppercut.update();
        this.subzeroHighkick.update();
        this.subzeroJumpkick.update();
        this.subzeroSweep.update();
        this.subzeroSpecial.update();
        this.subzeroIdle.update();
        this.subzeroWalk.update();
        this.subzeroJump.update();
    }

}