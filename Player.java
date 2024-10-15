import java.awt.*;

public class Player {
    private Rectangle hitBox;
    private int speed;
    private boolean moveLeft;
    private boolean moveRight;
    private int moveY;
    private boolean onGround;
    private boolean isCrouched, isHighKick, isJumpKick, isPunch, isSweep, isUpperCut;
    private int health;
    private int positionY;
    private int positionX, gravity;
    private int damageHighKick, damageJumpKick, damagePunch, damageSweep, damageUpperCut;

    private boolean isBlocking;
    private boolean facingRight;
    private boolean isFrozen;
    private boolean isStunned;
    public boolean isJumping;

    private long frozenTime;
    private int freezeTime, holdingResetX, holdingResetY;
    private boolean specialUsed;
    private long specialTime;
    private int coolDownTime = 5000;
    private boolean moveUsed;
    private long MoveUsedTime;
    private int moveCoolDownTime = 500;



    public Player(int x, int y, int width, int height) {
        hitBox = new Rectangle(x, y, width, height);
        holdingResetX = x;
        holdingResetY = y;
        speed = 5;
        health = 200;
        damagePunch = 5;
        damageHighKick = 7;
        damageSweep = 7;
        damageJumpKick = 5;
        damageUpperCut = 7;
        onGround = false;
        moveY = 0;
        gravity = 1;
        isCrouched = false;
        isJumping = false;

    }

    //after new round
    public void resetHealthAndPosition() {
        this.health = 200;
        this.hitBox.x = holdingResetX;
        this.hitBox.y = holdingResetY;
    }

    //GETTORS & SETTORS
    public long getMoveUsedTime() {
        return MoveUsedTime;
    }

    public void setMoveUsedTime(long moveUsedTime) {
        MoveUsedTime = moveUsedTime;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setFacingRight(boolean x) {
        facingRight = x;
    }

    public void setFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    public boolean isSpecialUsed() {
        return specialUsed;
    }

    public void setSpecialUsed(boolean specialUsed) {
        this.specialUsed = specialUsed;
    }

    public long getSpecialTime() {
        return specialTime;
    }

    public void setSpecialTime(long specialTime) {
        this.specialTime = specialTime;
    }

    public boolean isStunned() {
        return isStunned;
    }

    public void setStunned(boolean isStunned) {
        this.isStunned = isStunned;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getDamageHighKick() {
        return damageHighKick;
    }

    public int getDamageUpperCut() {
        return damageUpperCut;
    }

    public int getDamageJumpKick() {
        return damageJumpKick;
    }

    public int getDamagePunch() {
        return damagePunch;
    }

    public int getDamageSweep() {
        return damageSweep;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public int getMoveY() {
        return moveY;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setBlocking(boolean p) {
        isBlocking = p;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isHighKick() {
        return isHighKick;
    }

    public boolean isJumpKick() {
        return isJumpKick;
    }

    public boolean isPunch() {
        return isPunch;
    }

    public boolean isSweep() {
        return isSweep;
    }

    public boolean isUpperCut() {
        return isUpperCut;
    }

    public void switchUpperCutToFalse() {
        this.isUpperCut = false;
    }

    public void switchedSweepToFalse() {
        this.isSweep = false;
    }

    public void switchPunchToFalse() {
        this.isPunch = false;
    }

    public void switchJumpKickToFalse() {
        this.isJumpKick = false;
    }

    public void switchHighKickToFalse() {
        this.isHighKick = false;
    }

    public boolean isMoveUsed() {
        return moveUsed;
    }

    public void setMoveUsed(boolean moveUsed) {
        this.moveUsed = moveUsed;
    }

    public int getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(int freezeTime) {
        this.freezeTime = freezeTime;
    }

    public boolean isCrouched() {
        return isCrouched;
    }

    public boolean isJumping(){
        return isJumping;
    }

    public void switchCrouch(boolean t) {
        isCrouched = t;
    }

    //OTHER METHODS
    //damage
    public void takeThisHealthAway(int h) {
        health -= h;
        System.out.println(health);
    }

    //frozen timer 
    public void frozen() {
        this.setFrozen(true);
        frozenTime = System.currentTimeMillis();
    }

    //check punch attacks
    public void punchWasClicked() {
        if (isCrouched) {
            this.isUpperCut = true;
        } else {
            this.isPunch = true;
        }
    }

    //check kick attacks
    public void kickWasClicked() {
        if (!onGround) {
            this.isJumpKick = true;
        } else if (isCrouched) {
            this.isSweep = true;
        } else {
            this.isHighKick = true;
        }
    }

    
    //check on ground to jump
    public void jump() {
        // if on the ground, can jump
        if (onGround) {
            // set a negative upwards force
            this.moveY = -20;
            this.onGround = false;
            this.isJumping = true; 
        }
    }

    //hitbox test
    public void draw(Graphics2D g) {
        //g.fill(this.hitBox);
        //g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    
    //establish borders on screen
    public void stayOnScreen() {
        if (this.hitBox.x < 0) {
            this.hitBox.x = 0;
        }
        if (this.hitBox.x > 799 - this.hitBox.width) {
            this.hitBox.x = 799 - this.hitBox.width;
        }
    }

    //update jump, timers and cooldowns
    public void update() {
        this.stayOnScreen();
        if (!this.onGround) {
            // apply gravity
            this.moveY += gravity;
        }
        // sideways movement
        if (this.moveLeft) {
            this.hitBox.x -= this.speed;
        } else if (this.moveRight) {
            this.hitBox.x += this.speed;
        }
        // move vertically
        this.hitBox.y += this.moveY;

        this.frozenTimer();

        if(this.moveUsed){
            this.coolDown();
        }

        if (this.specialUsed) {
            this.coolDown();
        }

    }

    //when hit by iceball, or double ice back fire, freeze for 3 seconds & then unfreeze
    private void frozenTimer() {
        if (this.isFrozen) {
            if (System.currentTimeMillis() < this.frozenTime + this.freezeTime) {
                System.out.println(this.frozenTime + " " + System.currentTimeMillis());
                System.out.println("frozen");

            } else {
                System.out.println("unfrozen");
                this.setFrozen(false);
            }
        }
    }

    //special move and reg. attack cooldowns
    private void coolDown() {
        if (this.specialUsed && System.currentTimeMillis() > this.getSpecialTime() + coolDownTime) {
            this.setSpecialUsed(false);
        } else if(this.moveUsed && System.currentTimeMillis() > this.getMoveUsedTime() + moveCoolDownTime){
            this.moveUsed=false;
        }
    }

    //ground collisions
    public boolean checkCollision(Rectangle block) {
        return this.hitBox.intersects(block);
    }
 
    public void handleCollision(Rectangle r) {
        // get collision overlap
        Rectangle overlap = this.hitBox.intersection(r);
        // what is the smallest thing to correct
        if (overlap.width < overlap.height) {
            // fix left or right?
            if (this.hitBox.x < r.x) {
                // on the left
                this.hitBox.x -= overlap.width;
            } else {
                // on the right
                this.hitBox.x += overlap.width;
            }
        } else {
            // fix up or down
            if (this.moveY < 0) {
                // moving up
                this.hitBox.y += overlap.height;
                // stop moving more up
                this.moveY = 0;
            } else {
                // moving down
                this.hitBox.y -= overlap.height;
                // stop moving down
                this.moveY = 0;
                // on the ground
                this.onGround = true;
                this.isJumping = false;
            }
        }
    }

}
