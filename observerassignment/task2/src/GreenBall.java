import java.awt.*;
import java.util.ArrayList;

public class GreenBall extends Ball {
    ArrayList<BallObserver> observers = new ArrayList<>();

    public GreenBall(Color color, int xSpeed, int ySpeed, int ballSize, MainPanel subject) {
        super(color, xSpeed, ySpeed, ballSize);
        subject.registerObservers(this);
    }

    //register
    public void registerObservers(BallObserver ballObserver) {
        observers.add(ballObserver);
    }

    //remove !!!!
    public void removeObservers(int i) {
        observers.remove(i);
    }

    //notify
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            BallObserver ballObserver = (BallObserver) observers.get(i);
            int flag = ballObserver.update(this.getX(), this.getY());
            if (flag == 1) {
                removeObservers(i);
            }
        }
    }

    @Override
    public void move() {
        super.move();
        notifyObservers();
    }

    @Override
    public void update(char keyChar) {
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
