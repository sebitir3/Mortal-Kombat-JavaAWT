import java.awt.*;

public class SubZero extends Player {

    private int range;
    private int IceBallDMG;
    private boolean iceBallThrown;
    private Rectangle iceBall;
    private int ballSpeed;
    private int ballSize;
    private int ballStart;

    //constructor
    public SubZero(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.range = 350;
        this.IceBallDMG = 10;
        this.iceBallThrown = false;
        this.ballSpeed = 5;
        this.ballSize = 10;
    }

    //iceball used, starting pos, timer set, rectangle create
    public void IceBall(Scorpion scorpion) {
        ballStart = this.getHitBox().x;
        this.iceBallThrown = true;
        this.setSpecialUsed(true);
        this.setSpecialTime(System.currentTimeMillis());
        this.iceBall = new Rectangle(this.getHitBox().x, this.getHitBox().y + this.getHitBox().height / 2 - 10,
                ballSize, ballSize);
    }

    //iceball movement based on direction
    private void throwIceBall(Scorpion scorpion) {
        if (this.isFacingRight()) {
            this.iceBall.x += ballSpeed;
        } else {
            this.iceBall.x -= ballSpeed;
        }

        // hits scorpion
        if (this.iceBall.intersects(scorpion.getHitBox())) {
            // if scorpion alr frozen call DoubleIceBackFire()
            if (scorpion.isFrozen()) {
                this.setFreezeTime(6000);
                this.frozen();
                scorpion.takeThisHealthAway(IceBallDMG / 5);
            } else {
                // freeze & dmg scorpion
                scorpion.setFreezeTime(6000);
                scorpion.frozen();
                scorpion.takeThisHealthAway(IceBallDMG);
            }
            this.iceBall = null;
            this.iceBallThrown = false;

            // goes off map
        } else if (iceBall.x < 0 || iceBall.x > 800) {
            // remove t ice ball
            this.iceBall = null;
            this.iceBallThrown = false;
        } else if (Math.abs(this.iceBall.x - this.ballStart) > range) {
            this.iceBall = null;
            this.iceBallThrown = false;
        }
    }

    //update iceball collision
    public void update(Scorpion scorpion) {
        super.update();
        if (this.iceBallThrown) {
            this.throwIceBall(scorpion);
        }
    }

    //draw iceball
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        super.draw(g);
        if (iceBallThrown) {
            g.setColor(Color.BLUE);
            g.fill(iceBall);
        }
    }

    //animation gettor
    public boolean isSpecial(){
        return this.iceBallThrown;
    }

    
}