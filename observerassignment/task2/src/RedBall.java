import java.awt.*;

public class RedBall extends Ball implements BallObserver {
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

    @Override
    public int update(int x, int y) {
        int xd = x - this.getX();
        int yd = y - this.getY();
        if (xd * xd + yd * yd < 4900) {
            this.setColor(new Color(255, 102, 102));
            if (xd < 0) {// 绿球在左边
                this.setX(this.getX() + 40);
            } else {
                this.setX(this.getX() - 40);
            }
            if (yd < 0) {
                this.setY(this.getY() + 40);
            } else {
                this.setY(this.getY() - 40);
            }
            return 1;
        }
        return 0;
    }
}
