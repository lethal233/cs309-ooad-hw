import java.awt.*;

public class RedBall extends Ball {
    public RedBall(Color color, int xSpeed, int ySpeed, int ballSize, MainPanel subject) {
        super(color, xSpeed, ySpeed, ballSize);
        subject.registerObservers(this);
    }

    @Override
    public void update(char keyChar) {
        if (keyChar == 'a' || keyChar == 'd') {
            int temp = this.getXSpeed();
            this.setXSpeed(this.getYSpeed());
            this.setYSpeed(temp);
        }
    }
}
