package Jump;

public class Snow {

    public Snow(float _x, float _y, float _vx, float _vy, int _imgNo) {
        x = _x;
        y = _y;
        vy = _vy;
        vx = _vx;
        imgNo = _imgNo;
    }

    public void set(float _x, float _y, float _vy, float _vx, int _imgNo) {
        x = _x;
        y = _y;
        vy = _vy;
        vx = _vx;
        imgNo = _imgNo;
    }
    float x;
    float y;
    float vy;
    float vx;
    int imgNo;
}
