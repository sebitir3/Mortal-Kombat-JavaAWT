import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class HandleAnimation{
    private BufferedImage[] frames;
    private int currentFrame;
    private int delay;
    private int frameCount;
    private boolean isRepeat;
    private boolean running;

    //constructor
    public HandleAnimation(BufferedImage spritesheet, int x, int y, int w, int h, int numFrames, int delay, boolean isRepeat){
        frames = new BufferedImage[numFrames];
        //frame array cycle through by width
        for(int i = 0; i < numFrames; i++){
            frames[i]= spritesheet.getSubimage(x + i*w, y, w, h);
        }
        this.currentFrame = 0;
        this.delay = delay;
        this.frameCount = 0;
        this.isRepeat = isRepeat;
        this.running = false;
    }

    //draw current animation frame
    public void draw(Graphics2D g , int x, int y){
        g.drawImage(frames[currentFrame], x, y, null);
    }

    public void update(){
        //if no animation running
        if(!this.running){
            return;
        }
        //increase frame count every update
        this.frameCount ++;
        //when reaches delay, shift to next animation frame
        if(this.frameCount > this.delay){
            this.frameCount = 0;
            this.currentFrame ++;
        }
        //when animation finished
        if(this.currentFrame == frames.length){
            if(isRepeat){
                //cycle animation when animation is repeating (i.e. walk and idle)
                this.currentFrame = 0;
            } else {
                //stop animation
                this.running = false;
            }
        }

    }

    //load spritesheets as buffered images
    public BufferedImage loadImage(String filename){
        BufferedImage Image = null;
        try{
            Image = ImageIO.read(new File(filename));
        }catch(Exception e){
            e.printStackTrace();
        }
        return Image;
    }

    //animation is finished
    public boolean isFinished(){
        //stop running
        return this.currentFrame >= frames.length || !this.running;
    }

    //animation is running
    public boolean isRunning(){
        return this.running;
    }

    //reset frame when start
    public void start(){
        if(running){
            return;
        }
        currentFrame = 0;
        running = true;
    }

    //stop running
    public void stop(){
        running = false;
    }
}