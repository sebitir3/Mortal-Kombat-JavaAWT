import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Display {
    private Rectangle background, healthBarPlayerOne, healthBarPlayerTwo, backgroundOfPlayerOneHealthBar,
            backgroundOfPlayerTwoHealthBar;
    private Rectangle ground;
    private BufferedImage backgroundImage;
    private Scorpion scorpion;
    private SubZero subZero;
    private Font font, font2, font3;
    private int attackX;
    private int attackY;
    private int scorpX, scorpY, subX, subY;
    private int punchDist, upperCutDist, highKickDist, jumpKickDist, sweepDist;
    private int roundTime, basicRoundTime, roundNumber;
    private long holdStartTime;
    private int scorpMultiplier, subMultiplier, centralized;
    private float font2Size, font3Size;
    private boolean gameOn;
    private String message;

    public Display(Scorpion scorpion, SubZero subZero) {
        this.font3Size = 50;
        this.roundNumber = 1;
        this.gameOn = false;
        this.font2Size = 20;
        this.holdStartTime = System.currentTimeMillis();
        this.basicRoundTime = 99;
        this.roundTime = 99;
        this.subZero = subZero;
        this.scorpion = scorpion;
        this.background = new Rectangle(0, 0, 799, 300);
        this.ground = new Rectangle(-100, 275, 899, 100);
        this.backgroundImage = loadImage("Background.png");
        this.healthBarPlayerOne = new Rectangle(50, 10, scorpion.getHealth(), 20);
        this.backgroundOfPlayerOneHealthBar = new Rectangle(50, 10, 200, 20);
        this.healthBarPlayerTwo = new Rectangle(549, 10, subZero.getHealth(), 20);
        this.backgroundOfPlayerTwoHealthBar = new Rectangle(549, 10, 200, 20);
        punchDist = 111;
        upperCutDist = 111;
        highKickDist = 111;
        jumpKickDist = 111;
        sweepDist = 111;
        // create the font

        try {
            // create the font to use. Specify the size!
            font = Font.createFont(Font.TRUETYPE_FONT, new File("mk2.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // register the font
            ge.registerFont(font);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        font2 = font.deriveFont(this.font2Size);
        font3 = font.deriveFont(this.font3Size);
    }

    //flip attacks to make sure always facing each player
    private void checkDirection() {
        if (scorpion.getHitBox().x > subZero.getHitBox().x) {
            scorpMultiplier = -1;
            scorpion.setFacingRight(false);
            subMultiplier = 1;
            subZero.setFacingRight(true);
        } else {
            scorpMultiplier = 1;
            scorpion.setFacingRight(true);
            subMultiplier = -1;
            subZero.setFacingRight(false);
        }
    }

    //update hitbox x and y depeending on inputs
    private void updatePositions() {
        checkDirection();
        scorpX = scorpion.getHitBox().x + scorpion.getHitBox().width / 2;
        scorpY = scorpion.getHitBox().y + scorpion.getHitBox().height / 2;
        subX = subZero.getHitBox().x + subZero.getHitBox().width / 2;
        subY = subZero.getHitBox().y + subZero.getHitBox().height / 2;
    }

    //draw healthbars, timer and round sequences
    public void draw(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, null);
        if (this.gameOn) {
            healthBar(g);
            timer(g);
        }
        if (!this.gameOn) {
            roundPopUp(g);
        }
    }

    //rounds pop ups 
    private void roundPopUp(Graphics2D g) {
        g.setFont(font3);
        g.setColor(Color.red);
        if(this.holdStartTime + 4000 < System.currentTimeMillis()){
            message = "FIGHT!";
            centralized = 40;
        }else{
            message = "Round " + roundNumber;
            centralized = 0;
        }       
        g.drawString(message, 300 + centralized, 50);
    }

    //timer display
    private void timer(Graphics2D g) {
        this.roundTime = (int) (this.basicRoundTime - (System.currentTimeMillis() - holdStartTime) / 1000);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(this.roundTime + "", 370, 30);
    }

    //health bars for each player
    private void healthBar(Graphics2D g) {
        this.healthBarPlayerOne.setSize(scorpion.getHealth(), 20);
        this.healthBarPlayerTwo.setSize(subZero.getHealth(), 20);
        //healthbar backgrounds
        g.setColor(Color.black);
        g.draw(this.backgroundOfPlayerOneHealthBar);
        g.draw(this.backgroundOfPlayerTwoHealthBar);
        g.setColor(Color.red);
        g.fill(this.backgroundOfPlayerOneHealthBar);
        g.fill(this.backgroundOfPlayerTwoHealthBar);

        //healthbar 1
        g.setColor(Color.GREEN);
        g.draw(this.healthBarPlayerOne);
        g.fill(this.healthBarPlayerOne);

        //healthbar 2
        g.setColor(Color.GREEN);
        g.draw(this.healthBarPlayerTwo);
        g.fill(this.healthBarPlayerTwo);

        // names on healthbars
        g.setFont(font2);
        g.setColor(Color.black);
        g.drawString("SCORPION", this.backgroundOfPlayerOneHealthBar.x, 30);
        g.drawString("SUBZERO", this.backgroundOfPlayerTwoHealthBar.x + 110, 30);

    }

    //load images
    public BufferedImage loadImage(String filename) {
        BufferedImage Image = null;
        try {
            Image = ImageIO.read(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Image;
    }

    //handle collisions with the ground
    public void handleCollisions(Player player) {
        // does the player hit this block?
        if (player.checkCollision(ground)) {
            // do something about it
            player.handleCollision(ground);
        }

    }

    //update all collisions and health 
    public void update() {
        handleCollisions(scorpion);
        handleCollisions(subZero);
        if (gameOn) {
            pOnPCollisions();
            checkForEndOfRound();
        } else {
            subZero.resetHealthAndPosition();
            scorpion.resetHealthAndPosition();
            roundPopUpCounter();
        }

    }

    //start game
    public void roundPopUpCounter() {
        if (this.holdStartTime + 6000 < System.currentTimeMillis()) {
            gameOn = true;
        }
    }

    //run out of time check
    public void checkForEndOfRound() {
        if ((int) (this.basicRoundTime - (System.currentTimeMillis() - holdStartTime) / 1000) <= 0) {
            gameOn = false;
            roundNumber++;
            this.holdStartTime = System.currentTimeMillis();
            if (this.scorpion.getHealth() > this.subZero.getHealth()) {
                // scorpion win
            } else {
                // sub win
            }
        } else if (this.scorpion.getHealth() <= 0) {
            gameOn = false;
            roundNumber++;
            this.holdStartTime = System.currentTimeMillis();
            // sub wins
        } else if (this.subZero.getHealth() <= 0) {
            gameOn = false;
            roundNumber++;
            this.holdStartTime = System.currentTimeMillis();
            // scorpion wins
        }

    }

    //attack collision
    public void pOnPCollisions() {
        updatePositions();
        // SCORPION ATTACKS
        if (scorpion.isPunch()) {
            //line coordinates
            attackX = scorpX + scorpMultiplier * punchDist;
            attackY = scorpY;
            //test
            System.out.println(subZero.getHitBox());
            System.out.println(scorpX + ", " + scorpY + " " + attackX + " " + attackY);
            //draw line and check if intersecting with other player
            if (subZero.getHitBox().intersectsLine(scorpX, scorpY, attackX, attackY)) {
                subZero.takeThisHealthAway(scorpion.getDamagePunch());
            }
            scorpion.switchPunchToFalse();
        } else if (scorpion.isUpperCut()) {
            attackX = scorpX + scorpMultiplier * upperCutDist;
            attackY = scorpY + scorpMultiplier * upperCutDist;
            if (subZero.getHitBox().intersectsLine(scorpX, scorpY, attackX, attackY)) {
                subZero.takeThisHealthAway(scorpion.getDamageUpperCut());
            }
            scorpion.switchUpperCutToFalse();
        } else if (scorpion.isHighKick()) {
            attackX = scorpX + scorpMultiplier * highKickDist;
            attackY = scorpY + scorpMultiplier * highKickDist;
            if (subZero.getHitBox().intersectsLine(scorpX, scorpY, attackX, attackY)) {
                subZero.takeThisHealthAway(scorpion.getDamageHighKick());
            }
            scorpion.switchHighKickToFalse();
        } else if (scorpion.isJumpKick()) {
            attackX = scorpX + scorpMultiplier * jumpKickDist;
            attackY = scorpY + scorpMultiplier * jumpKickDist;
            if (subZero.getHitBox().intersectsLine(scorpX, scorpY, attackX, attackY)) {
                subZero.takeThisHealthAway(scorpion.getDamageJumpKick());
            }
            scorpion.switchJumpKickToFalse();
        } else if (scorpion.isSweep()) {
            attackX = scorpX + scorpMultiplier * sweepDist;
            attackY = scorpY + scorpMultiplier * sweepDist;
            if (subZero.getHitBox().intersectsLine(scorpX, scorpY, attackX, attackY)) {
                subZero.takeThisHealthAway(scorpion.getDamageSweep());
            }
            scorpion.switchedSweepToFalse();
            ;
        }

        // SUBZERO ATTACKS
        if (subZero.isPunch()) {
            System.out.println("Sub punch");
            attackX = subX + subMultiplier * punchDist;
            attackY = subY + subMultiplier * punchDist;
            System.out.println(scorpion.getHitBox());
            System.out.println(subX + ", " + subY + " " + attackX + " " + attackY);
            if (scorpion.getHitBox().intersectsLine(subX, subY, attackX, attackY)) {
                scorpion.takeThisHealthAway(subZero.getDamagePunch());

            }
            subZero.switchPunchToFalse();
        } else if (subZero.isUpperCut()) {
            System.out.println("Sub uppercut");
            attackX = subX + subMultiplier * upperCutDist;
            attackY = subY + subMultiplier * upperCutDist;
            if (scorpion.getHitBox().intersectsLine(subX, subY, attackX, attackY)) {
                scorpion.takeThisHealthAway(subZero.getDamageUpperCut());
            }
            subZero.switchUpperCutToFalse();
        } else if (subZero.isHighKick()) {
            System.out.println("Sub high kick");
            attackX = subX + subMultiplier * highKickDist;
            attackY = subY + subMultiplier * highKickDist;
            if (scorpion.getHitBox().intersectsLine(subX, subY, attackX, attackY)) {
                scorpion.takeThisHealthAway(subZero.getDamageHighKick());
            }
            subZero.switchHighKickToFalse();
        } else if (subZero.isJumpKick()) {
            System.out.println("Sub jump kick");
            attackX = subX + subMultiplier * jumpKickDist;
            attackY = subY + subMultiplier * jumpKickDist;
            if (scorpion.getHitBox().intersectsLine(subX, subY, attackX, attackY)) {
                scorpion.takeThisHealthAway(subZero.getDamageJumpKick());
            }
            subZero.switchJumpKickToFalse();
        } else if (subZero.isSweep()) {
            System.out.println("Sub sweep");
            attackX = subX + subMultiplier * sweepDist;
            attackY = subY + subMultiplier * sweepDist;
            if (scorpion.getHitBox().intersectsLine(subX, subY, attackX, attackY)) {
                scorpion.takeThisHealthAway(subZero.getDamageSweep());
            }
            subZero.switchedSweepToFalse();
        }
    }

}
