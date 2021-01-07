import java.awt.*;

public class BlueBall extends Ball implements BallObserver {
    public BlueBall(Color color, int xSpeed, int ySpeed, int ballSize, MainPanel subject) {
        super(color, xSpeed, ySpeed, ballSize);
        subject.registerObservers(this);
    }

    @Override
    public void update(char keyChar) {
        this.setXSpeed(-1 * this.getXSpeed());
        this.setYSpeed(-1 * this.getYSpeed());
    }

    @Override
    public int update(int x, int y) {
        int xd = x - this.getX();
        int yd = y - this.getY();
        if (xd * xd + yd * yd < 6400) {
            this.setColor(new Color(51, 153, 255));
            if (xd < 0) {// 绿球在左边
                this.setX(this.getX() + 30);
            } else {
                this.setX(this.getX() - 30);
            }
            if (yd < 0) {
                this.setY(this.getY() + 30);
            } else {
                this.setY(this.getY() - 30);
            }
            return 1;
        }
        return 0;
    }
}
