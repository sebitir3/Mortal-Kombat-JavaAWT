import java.awt.*;

public class Scorpion extends Player {
    private int GrappleDMG;
    private boolean Expanding;
    private boolean Retracting;
    private Rectangle grappleAttack;
    private int grappleRate;

    //constructor
    public Scorpion(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.GrappleDMG = 10;
        this.grappleRate = 5;
    }

    //draw scoprion & grappling hook
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        super.draw(g);
        if (this.Expanding) {
            g.setColor(Color.YELLOW);
            g.fill(grappleAttack);
        }
    }

    //grapple attack on subzero
    public void Grapple(SubZero subzero) {
        //set condition, timer and expanding 
        this.setSpecialUsed(true);
        this.setSpecialTime(System.currentTimeMillis());
        this.Expanding = true;
        //set subzero to stunned
        this.setStunned(true);
        //create grapple rectangle
        grappleAttack = new Rectangle(this.getHitBox().x, this.getHitBox().y +
                this.getHitBox().height / 2 - 10, 10, 10);
    }

    //update collision with grapple & subzero
    public void update(SubZero subzero) {
        super.update();
        if (this.Expanding) {
            this.GrowingRectangle(subzero);
        }
        if (this.Retracting) {
            this.Retract(subzero);
        }
    }

    //growing rectangle expanding, direction and damage
    private void GrowingRectangle(SubZero subzero) {

        if (this.Expanding) {
            if (this.isFacingRight() && !this.grappleAttack.intersects(subzero.getHitBox())) {
                grappleAttack.width += grappleRate;

            } else if (!this.isFacingRight() && !this.grappleAttack.intersects(subzero.getHitBox())) {
                grappleAttack.width += grappleRate;
                grappleAttack.x -= grappleRate;

            } else {
                subzero.takeThisHealthAway(GrappleDMG);
                this.Retracting = true;
                this.Expanding = false;
            }
        }
    }

    //retracting grapple and stunned state
    private void Retract(SubZero subzero) {
        if (this.isFacingRight() && grappleAttack.width >= 0) {
            grappleAttack.width -= 3 * grappleRate;
            if (grappleAttack.y > subzero.getHitBox().y
                    && grappleAttack.y < (subzero.getHitBox().y + subzero.getHitBox().height)) {
                this.setStunned(true);
                subzero.setStunned(true);
                subzero.getHitBox().x -= 3 * grappleRate;
            }
            //retract based on direction
        } else if (!this.isFacingRight() && grappleAttack.width >= 0) {
            this.setStunned(true);
            subzero.setStunned(true);
            grappleAttack.width -= 3 * grappleRate;
            subzero.getHitBox().x += 3 * grappleRate;
            //stunned wear off
        } else {
            this.setStunned(false);
            subzero.setStunned(false);
            this.grappleAttack = null;
            this.Retracting = false;
        }
    }
    //grapple animations gettors
    public boolean isSpecialFirst(){
        return this.Expanding;
    }
    public boolean isSpecialLast(){
        return this.Retracting;
    }
}
