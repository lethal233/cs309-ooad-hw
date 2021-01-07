import java.awt.*;

public class GreenBall extends Ball{
    public GreenBall(Color color, int xSpeed, int ySpeed, int ballSize, MainPanel subject) {
        super(color, xSpeed, ySpeed, ballSize);
        subject.registerObservers(this);

    }
    @Override
    public void update(char keyChar){

        switch (keyChar) {
            case 'a':
                this.setXSpeed(Math.abs(this.getXSpeed()) * -1);
                break;
            case 'd':
                this.setXSpeed(Math.abs(this.getXSpeed()));
                break;
            case 'w':
                this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
                break;
            case 's':
                this.setYSpeed(Math.abs(this.getYSpeed()));
                break;
        }
    }
}
