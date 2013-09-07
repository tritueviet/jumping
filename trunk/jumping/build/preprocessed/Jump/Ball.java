package Jump;

public class Ball {

    Ball(int _x, int _y, int _dx, int _dy, int _Step, int _gift) {
        vx = 1;
        x = _x;
        y = _y;
        dx = _dx;
        dy = _dy;
        step = _Step;
        gift = _gift;
        point = false;
    }

    void setBall(int _x, int _y, int _dx, int _dy, int _Step, int _gift) {
        giftX = x = _x;
        y = _y;
        dx = _dx;
        dy = _dy;
        step = _Step;
        gift = _gift;
        vx = 1;
        point = false;
    }
    int x;
    int y;
    int dx;
    int dy;
    int step;
    int gift;
    int giftX;
    int giftY;
    int vx;
    boolean point;
}
