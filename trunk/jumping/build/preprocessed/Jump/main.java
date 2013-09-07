package Jump;

import Jump.Resize;
import com.nokia.mid.ui.DeviceControl;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.sensor.ChannelInfo;
import javax.microedition.sensor.Data;
import javax.microedition.sensor.SensorConnection;
import javax.microedition.sensor.SensorInfo;
import javax.microedition.sensor.SensorManager;

public class main extends Canvas
        implements Runnable {

    private int tocdonhay = 30;
    private int nhaynhe = 6;
    private SensorConnection accSensor;
    private Image imgSensor;
    private Image imgPause, imgPlay;
    private Image imgUnSensor;
    private boolean Sensor = true;
    private boolean play = true;

    private void processSensors() {
        try {
            if (Sensor == true) {
                Data[] data = accSensor.getData(1);
                // data[0] = x, [1] = y, [2] = z
                double speedX = data[0].getDoubleValues()[0];
                double speedY = data[1].getDoubleValues()[0];
                if (speedX < 0) {
                    flag = 2;
                } else {
                    flag = 1;
                }
                data = null;

                Runtime.getRuntime().freeMemory();
                Runtime.getRuntime().gc();
            }
//            Runtime.getRuntime().freeMemory();
//            Runtime.getRuntime().gc();

            //System.out.println("x=" + speedX + ", y=" + speedY);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public main(MIDlet midlet)
            throws IOException, MediaException {
        DeviceControl.setLights(0, 100);

        SensorInfo[] sensorInfos = SensorManager.findSensors("acceleration", null);
        if (sensorInfos.length > 0) {
            // Find an acceleration sensor that returns double values
            for (int i = 0; i < sensorInfos.length; i++) {
                if (sensorInfos[i].getChannelInfos()[0].getDataType() == ChannelInfo.TYPE_DOUBLE) {
                    accSensor = (SensorConnection) Connector.open(sensorInfos[i].getUrl());
                }
                Runtime.getRuntime().freeMemory();
                Runtime.getRuntime().gc();
            }
            Runtime.getRuntime().freeMemory();
            Runtime.getRuntime().gc();
        }



        newScore = 0;
        newshow = 0;
        mStripCount = 5;
        mAniCount = 5;
        sound = 0;
        mTime = 500;
        mDcr = 0;
        playerJump = -5;
        ft = 0;
        spring = 0;
        springT = 0;
        inc = 1;
        mRectWidth = 20;
        mRectHieght = 10;
        greatY = 240;
        flag = 0;
        jump = -15;
        isGamePlay = true;
        ch_drag = false;
        menu_count = 0;
        Score = 0;
        mCluadCunt = 4;
        mRand = new Random();
        gameover_check = false;
        rs = null;
        setFullScreenMode(true);
        openRecStore();
        menu_count = 1;
        mid = midlet;
        mMaxX = getWidth();
        mMaxY = getHeight();
        img_load();
        gameInit();
    }

    public void img_load() {
        try {
            imgPause = Image.createImage("/pause.png");
            imgPlay = Image.createImage("/play.png");
            imgSensor = Image.createImage("/sensor.png");
            imgUnSensor = Image.createImage("/unSensor.png");
            mImg_BG = Image.createImage("/bg.png");
            mImg_BGMenu = Image.createImage("/splash.png");
            mImg_Land = new Image[2];
            mImg_Land[0] = Image.createImage("/land.png");
            mImg_Land[1] = Image.createImage("/levelend.png");
            mImg_Step = new Image[5];
            mImg_Step[0] = Image.createImage("/bigstep.png");
            mImg_Step[1] = Image.createImage("/smallstep.png");
            mImg_Step[2] = Image.createImage("/left2rightstep.png");
            mImg_Step[3] = Image.createImage("/updownstep.png");
            mImg_Step[4] = Image.createImage("/transtep.png");
            mImg_snow = new Image[3];
            mImg_snow[0] = Image.createImage("/snow1.png");
            mImg_snow[1] = Image.createImage("/snow2.png");
            mImg_snow[2] = Image.createImage("/snow3.png");
            mImg_Boy = new Image[4];
            mImg_Boy[0] = Image.createImage("/boy1.png");
            mImg_Boy[1] = Image.createImage("/boy2.png");
            mImg_Boy[2] = Image.createImage("/boy3.png");
            mImg_Boy[3] = Image.createImage("/boy4.png");
            mImg_BoyS = new Image[4];
            mImg_BoyS[0] = Image.createImage("/spring1.png");
            mImg_BoyS[1] = Image.createImage("/spring2.png");
            mImg_BoyS[2] = Image.createImage("/spring3.png");
            mImg_BoyS[3] = Image.createImage("/spring4.png");
            mImg_rocketpow = Image.createImage("/rocketpow.png");
            mImg_BoyRock = new Image[2];
            mImg_BoyRock[0] = Image.createImage("/rocketani1.png");
            mImg_BoyRock[1] = Image.createImage("/rocketani2.png");
            mImg_mashaniR = new Image[2];
            mImg_mashaniL = new Image[2];
            mImg_mashaniR[0] = Image.createImage("/mashani1.png");
            mImg_mashaniL[0] = Image.createImage(mImg_mashaniR[0]);
            mImg_mashaniR[1] = Image.createImage("/mashani2.png");
            mImg_mashaniL[1] = Image.createImage(mImg_mashaniR[1]);
            mImg_shield = Image.createImage("/shield.png");
            mImg_SPower = Image.createImage("/shieldpow.png");
            mImg_Coin = new Image[4];
            mImg_Coin[0] = Image.createImage("/fruit1.png");
            mImg_Coin[1] = Image.createImage("/fruit2.png");
            mImg_Coin[2] = Image.createImage("/fruit3.png");
            mImg_Coin[3] = Image.createImage("/fruit4.png");
            mImg_Claud = new Image[2];
            mImg_Claud[0] = Image.createImage("/cloud1.png");
            mImg_Claud[1] = Image.createImage("/cloud2.png");
            mImg_superspring = Image.createImage("/superspring.png");
            mImg_Retry = new Image[2];
            mImg_Retry[0] = Image.createImage("/retry.png");
            mImg_Retry[1] = Image.createImage("/retryselect.png");
            mImg_GameMore = new Image[2];
            mImg_GameMore[0] = Image.createImage("/morebt.png");
            mImg_GameMore[1] = Image.createImage("/morebtselect.png");
            mImg_Back = new Image[2];
            mImg_Back[0] = Image.createImage("/back.png");
            mImg_Back[1] = Image.createImage("/backselect.png");
            mImg_highPopUp = Image.createImage("/highscorepopup.png");
            mImg_hippo = Image.createImage("/hippo.png");
            mImg_pints = new Image[5];
            mImg_pints[0] = Image.createImage("/pt50.png");
            mImg_pints[1] = Image.createImage("/pt100.png");
            mImg_pints[2] = Image.createImage("/pt150.png");
            mImg_pints[3] = Image.createImage("/pt200.png");
            mImg_pints[4] = Image.createImage("/newhighscore.png");
            mImg_Pupup = Image.createImage("/gameoverpopup.png");
            mImg_Fontstrip = Image.createImage("/fontstrip.png");
            mImg_Score = Image.createImage("/scoretitle.png");
            mImg_Sound = new Image[2];
            mImg_Sound[0] = Image.createImage("/soundon.png");
            mImg_Sound[1] = Image.createImage("/soundoff.png");
            mImg_start = new Image[2];
            mImg_start[0] = Image.createImage("/start.png");
            mImg_start[1] = Image.createImage("/startselect.png");
            mImg_score = new Image[2];
            mImg_score[0] = Image.createImage("/score.png");
            mImg_score[1] = Image.createImage("/scoreselect.png");
            mImg_more = new Image[2];
            mImg_more[0] = Image.createImage("/more.png");
            mImg_more[1] = Image.createImage("/moreselect.png");
            mImg_Exit = new Image[2];
            mImg_Exit[0] = Image.createImage("/exit.png");
            mImg_Exit[1] = Image.createImage("/exitselect.png");
            soundplay = new Player[3];
            soundplay[0] = Manager.createPlayer(getClass().getResourceAsStream("/bounce.mp3"), "audio/mpeg");
            soundplay[1] = Manager.createPlayer(getClass().getResourceAsStream("/bouncespring.mp3"), "audio/mpeg");
            soundplay[2] = Manager.createPlayer(getClass().getResourceAsStream("/rocket.mp3"), "audio/mpeg");
            jiglebell = Manager.createPlayer(getClass().getResourceAsStream("/1.mid"), "audio/midi");


            Resize re = new Resize();
            mImg_BGMenu = re.CreateScaledImage(mImg_BGMenu, getWidth(), getHeight());

        } catch (Exception exception) {
            System.out.println("_____eee_______" + exception);
        }
    }

    void gameInit() {
        mCoinPoint = new Ball[20];
        for (int i = 0; i < 20; i++) {
            mCoinPoint[i] = new Ball(-100, -100, 0, 0, 0, 0);
        }

        mCluad = new Ball[mCluadCunt];
        for (int j = 0; j < mCluadCunt; j++) {
            mCluad[j] = new Ball(0, 0, 0, 0, 0, 0);
        }

        mBall = new Ball[100];
        for (int k = 0; k < 100; k++) {
            mBall[k] = new Ball(0, 0, 0, 0, 0, 0);
        }

        mPlayer = new Ball(0, 0, 0, 0, 0, 0);
        mSnow = new Snow[50];
        int l = mMaxY;
        for (int i1 = 0; i1 < 50; i1++) {
            int j1 = Math.abs(mRand.nextInt()) % mMaxX;
            l = (int) ((float) l - (float) mMaxY / 50F);
            float f = Math.abs(mRand.nextFloat()) + 0.5F;
            mSnow[i1] = new Snow(j1, l, 0.0F, f, j1 % 3);
        }

        gameReset();
    }

    void gameReset() {
        greatY = mMaxY;
        GameScreen = 0;
        try {
            Score = newScore = newshow = 0;
        } catch (Exception exception) {
        }
        if (mCoinPoint == null) {
            gameInit();
        }
        if (mMaxY > 500) {
            jump = -18;
            mRectWidth = 20;
            mRectHieght = 20;
        }
        for (int i = 0; i < 20; i++) {
            mCoinPoint[i].setBall(-100, -100, 0, 0, 0, 0);
        }

        for (int j = 0; j < mCluadCunt; j++) {
            int i1 = (j % 2) * (mMaxX - mImg_Claud[j % 2].getWidth());
            int l1 = -200 * (j + 1);
            int j2 = j % 2;
            mCluad[j].setBall(i1, l1, 0, 0, j2, 0);
        }

        Levellenth = 0;
        for (int k = 0; k < 100; k++) {
            int j1 = 0;
            int i2 = 0;
            if (greatY > 0) {
                i2 = mMaxX;
            } else {
                i2 = Math.abs(mRand.nextInt()) % (mMaxX - mImg_Step[1].getWidth());
            }
            j1 = Math.abs(mRand.nextInt() % 30 + 2 * mImg_Step[0].getHeight());
            greatY = greatY - j1;
            Levellenth++;
            byte byte0 = 0;
            int k2 = Math.abs(mRand.nextInt());
            if (k2 % 15 == 0) {
                byte0 = 1;
            } else if (k2 % 15 == 1 && Levellenth > 220) {
                byte0 = 2;
            } else if (k2 % 15 == 3 && Levellenth > 220) {
                byte0 = 3;
            } else if (k2 % 15 == 4) {
                byte0 = 4;
            } else if (k2 % 15 == 5) {
                byte0 = 5;
            } else if (k2 % 15 == 6) {
                byte0 = 6;
            } else if (k2 % 15 == 7) {
                byte0 = 7;
            } else if (k2 % 15 == 8 && Levellenth > 220) {
                byte0 = 8;
            } else if (k2 % 15 == 9 && Levellenth > 330) {
                byte0 = 9;
            } else if (k2 % 15 == 10 && Levellenth > 110) {
                byte0 = 10;
            } else if (k2 % 15 == 11) {
                byte0 = 11;
            }
            if (byte0 == 8) {
                mBall[k].setBall(i2, greatY, mImg_Step[2].getWidth(), mImg_Step[2].getHeight(), 2, byte0);
            } else if (byte0 == 9) {
                mBall[k].setBall(i2, greatY, mImg_Step[3].getWidth(), mImg_Step[3].getHeight(), 3, byte0);
            } else if (byte0 == 10) {
                mBall[k].setBall(i2, greatY, mImg_Step[4].getWidth(), mImg_Step[4].getHeight(), 4, byte0);
            } else if (mRand.nextInt() % 2 == 0) {
                System.out.println();
                mBall[k].setBall(i2, greatY, mImg_Step[1].getWidth(), mImg_Step[1].getHeight(), 1, byte0);
            } else {
                mBall[k].setBall(i2, greatY, mImg_Step[0].getWidth(), mImg_Step[0].getHeight(), 0, byte0);
            }
            mBall[k].giftX = i2;
        }

        mPlayer.setBall(mMaxX / 5, mMaxY - mImg_Land[0].getHeight() * 4, mImg_Boy[0].getWidth(), mImg_Boy[0].getHeight(), 0, 0);
        moveBG3 = 0;
        moveBG2 = moveBG3 - mImg_BG.getWidth();
        moveBG = moveBG2 - mImg_BG.getWidth();
        isGamePlay = false;
        moveFoot = mMaxY - mImg_Land[0].getHeight();
        int l = mImg_Land[0].getHeight();
        int k1 = mImg_start[1].getHeight();
        startY = mMaxY - l * 3 - k1 * 3;
        scoreY = mMaxY - l * 3 - k1 * 2;
        moreY = mMaxY - l * 3 - k1;
        ExitY = mMaxY - l * 3;
        HippoY = 0;
        startSel = scoreSel = moreSel = ExitSel = 0;
    }

    void Retry2Start() {
        byte byte0 = 10;
        startY -= byte0;
        scoreY -= byte0;
        moreY -= byte0;
        ExitY -= byte0;
        HippoY -= byte0;
        moveBG -= byte0;
        moveBG2 -= byte0;
        moveBG3 -= byte0;
        for (int i = 0; i < 100; i++) {
            mBall[i].y -= byte0;
        }

        for (int j = 0; j < mCluadCunt; j++) {
            mCluad[j].y -= byte0;
        }

        if (moveBG3 < 0) {
            GameScreen = 0;
            gameReset();
        }
    }

    void Draw_number(Graphics g, int i, int j, int k) {
        String s = "" + i;
        for (int l = 0; l < s.length(); l++) {
            char c = s.charAt(l);
            g.drawRegion(mImg_Fontstrip, ((c - 48) * mImg_Fontstrip.getWidth()) / 10, 0, mImg_Fontstrip.getWidth() / 10, mImg_Fontstrip.getHeight(), 0, j + (l * (mImg_Fontstrip.getWidth() - 20)) / 10, k, 0);
        }

    }

    int findLowest() {
        int i = -10;
        for (int j = 0; j < 100; j++) {
            if (i > mBall[j].y) {
                i = mBall[j].y;
            }
        }

        return i;
    }

    boolean Rect2RectIntersection(int i, int j, int k, int l, int i1, int j1, int k1,
            int l1) {
        return i + k > i1 && j + l > j1 && i1 + k1 > i && j1 + l1 > j;
    }

    int abs(int i) {
        return i >= 0 ? i : -i;
    }

    protected void paint(Graphics g) {
        if (GameScreen == 4) {
            Retry2Start();
        } else {
            GameLogic();
        }
        counter++;
        if (counter % 5 == 0) {
            counter2++;
        }
        g.drawImage(mImg_BG, 0, moveBG, 0);
        g.drawImage(mImg_BG, 0, moveBG2, 0);
        if (isGamePlay) {
            g.drawImage(mImg_BG, 0, moveBG3, 0);
        } else {
            g.drawImage(mImg_BGMenu, 0, moveBG3, 0);
        }
        for (int i = 0; i < 50; i++) {
            mSnow[i].y += mSnow[i].vy;
            g.drawImage(mImg_snow[mSnow[i].imgNo], (int) mSnow[i].x, (int) mSnow[i].y, 0);
            if (mSnow[i].y > (float) mMaxY) {
                mSnow[i].y = -20F;
            }
        }

        g.drawImage(mImg_hippo, mMaxX - mImg_hippo.getWidth() - 10, HippoY + 5, 0);
        g.drawImage(mImg_start[startSel], mMaxX - mImg_start[startSel].getWidth(), startY, 0);
        g.drawImage(mImg_score[scoreSel], mMaxX - mImg_score[scoreSel].getWidth(), scoreY, 0);
        g.drawImage(mImg_Exit[ExitSel], mMaxX - mImg_Exit[ExitSel].getWidth(), moreY, 0);
        switch (menu_count) {
            case 1: // '\001'
                g.drawImage(mImg_start[1], mMaxX - mImg_start[1].getWidth(), startY - 2, 0);
                break;

            case 2: // '\002'
                g.drawImage(mImg_score[1], mMaxX - mImg_score[1].getWidth(), scoreY - 2, 0);
                break;

            case 3: // '\003'
                g.drawImage(mImg_Exit[1], mMaxX - mImg_Exit[1].getWidth(), moreY - 2, 0);
                break;
        }
        for (int j = 0; j < mCluadCunt; j++) {
            g.drawImage(mImg_Claud[mCluad[j].step % 2], mCluad[j].x, mCluad[j].y, 0);
        }

        for (int k = 0; k < 100; k++) {
            if (mBall[k].y <= 0) {
                continue;
            }
            int k1 = Math.abs(mBall[k].gift - 4) % 4;
            int l1 = (mImg_Step[mBall[k].step].getWidth() - mImg_Coin[k1].getWidth()) / 2;
            int k2 = mImg_Coin[k1].getHeight();
            if (mBall[k].gift == 9) {
                mBall[k].giftX += mBall[k].vx;
                mBall[k].giftY = mBall[k].y + mBall[k].giftX;
                if (mBall[k].giftY > mBall[k].y + 100) {
                    mBall[k].vx = -1;
                }
                if (mBall[k].giftY < mBall[k].y) {
                    mBall[k].vx = 1;
                }
                g.drawImage(mImg_Step[mBall[k].step], mBall[k].x, mBall[k].giftY, 0);
            } else {
                g.drawImage(mImg_Step[mBall[k].step], mBall[k].x, mBall[k].y, 0);
            }
            if (mBall[k].gift == 1) {
                g.drawImage(mImg_rocketpow, mBall[k].x, mBall[k].y - mImg_rocketpow.getHeight(), 0);
            }
            if (mBall[k].gift == 2) {
                if (mBall[k].giftX > (mBall[k].x + mImg_Step[mBall[k].step].getWidth()) - mImg_mashaniR[0].getWidth() || mBall[k].giftX < mBall[k].x) {
                    mBall[k].vx = -mBall[k].vx;
                }
                mBall[k].giftX += mBall[k].vx;
                mBall[k].giftY = mBall[k].y - mImg_mashaniR[1].getHeight();
                if (mBall[k].vx > 0) {
                    g.drawImage(mImg_mashaniL[counter2 % 2], mBall[k].giftX, mBall[k].giftY, 0);
                } else {
                    g.drawImage(mImg_mashaniR[counter2 % 2], mBall[k].giftX, mBall[k].giftY, 0);
                }
            }
            if (mBall[k].gift == 3) {
                g.drawImage(mImg_SPower, mBall[k].x, mBall[k].y - mImg_SPower.getHeight(), 0);
            }
            if (mBall[k].gift == 4) {
                g.drawImage(mImg_Coin[k1], mBall[k].x + l1, mBall[k].y - k2, 0);
            }
            if (mBall[k].gift == 5) {
                g.drawImage(mImg_Coin[k1], mBall[k].x + l1, mBall[k].y - k2, 0);
            }
            if (mBall[k].gift == 6) {
                g.drawImage(mImg_Coin[k1], mBall[k].x + l1, mBall[k].y - k2, 0);
            }
            if (mBall[k].gift == 7) {
                g.drawImage(mImg_Coin[k1], mBall[k].x + l1, mBall[k].y - k2, 0);
            }
            if (mBall[k].gift == 8) {
                if (mBall[k].x > mMaxX - mImg_Step[mBall[k].step].getWidth() || mBall[k].x < 0) {
                    mBall[k].vx = -mBall[k].vx;
                }
                mBall[k].x += mBall[k].vx;
            }
            if (mBall[k].gift == 11) {
                int i2 = (mImg_Step[mBall[k].step].getWidth() - mImg_superspring.getWidth()) / 2;
                int l2 = mImg_superspring.getHeight();
                g.drawImage(mImg_superspring, mBall[k].x + i2, mBall[k].y - l2, 0);
            }
        }

        if (Power > 0) {
            g.drawImage(mImg_shield, mPlayer.x - (mImg_shield.getWidth() - mImg_Boy[0].getWidth()) / 2, mPlayer.y - (mImg_shield.getHeight() - mImg_Boy[0].getHeight()) / 2, 0);
        }
        if (ft > 0) {
            g.drawImage(mImg_BoyRock[counter2 % 2], mPlayer.x, mPlayer.y, 0);
        } else if (playerJump < -14) {
            if (springT > 0) {
                g.drawImage(mImg_BoyS[ft % 2], mPlayer.x, mPlayer.y, 0);
            } else {
                g.drawImage(mImg_Boy[ft % 2], mPlayer.x, mPlayer.y, 0);
            }
        } else if (playerJump < -12) {
            if (springT > 0) {
                g.drawImage(mImg_BoyS[1], mPlayer.x, mPlayer.y, 0);
            } else {
                g.drawImage(mImg_Boy[1], mPlayer.x, mPlayer.y, 0);
            }
        } else if (playerJump < -10) {
            if (springT > 0) {
                g.drawImage(mImg_BoyS[2], mPlayer.x, mPlayer.y, 0);
            } else {
                g.drawImage(mImg_Boy[2], mPlayer.x, mPlayer.y, 0);
            }
        } else if (playerJump < 5) {
            if (springT > 0) {
                g.drawImage(mImg_BoyS[3], mPlayer.x, mPlayer.y, 0);
            } else {
                g.drawImage(mImg_Boy[3], mPlayer.x, mPlayer.y, 0);
            }
        } else if (springT > 0) {
            g.drawImage(mImg_BoyS[1], mPlayer.x, mPlayer.y, 0);
        } else {
            g.drawImage(mImg_Boy[1], mPlayer.x, mPlayer.y, 0);
        }
        for (int l = 0; l < 20; l++) {
            if (mCoinPoint[l].y > -100) {
                g.drawImage(mImg_pints[mCoinPoint[l].step % 5], mCoinPoint[l].x, mCoinPoint[l].y, 0);
            }
        }

        if (GameScreen == 2) {
            int i1 = mMaxY / 2 + mImg_Pupup.getHeight();
            g.drawImage(mImg_Pupup, (mMaxX - mImg_Pupup.getWidth()) / 2, mMaxY / 5, 0);
            g.drawImage(mImg_Retry[RetrySel], (mMaxX - mImg_Retry[RetrySel].getWidth()) / 2, i1 - 90, 0);
            String s = "" + Score;
            Draw_number(g, Score, mMaxX / 2 - (mImg_Fontstrip.getWidth() / 20) * s.length(), (mMaxY / 2 + mImg_Pupup.getHeight()) - mImg_Fontstrip.getHeight() * 2 - 90);
        }
        if (GameScreen == 3) {
            int j1 = mMaxY / 2 + mImg_highPopUp.getHeight();
            g.drawImage(mImg_BGMenu, 0, 0, 0);
            g.drawImage(mImg_highPopUp, (mMaxX - mImg_highPopUp.getWidth()) / 2, mMaxY / 5, 0);
            g.drawImage(mImg_Back[RetrySel], (mMaxX - mImg_Back[RetrySel].getWidth()) / 2, j1 - 100, 0);
            String s2 = "" + newScore;
            Draw_number(g, newScore, mMaxX / 2 - (mImg_Fontstrip.getWidth() / 20) * s2.length(), (mMaxY / 2 + mImg_highPopUp.getHeight()) - mImg_Fontstrip.getHeight() * 4 - 55);
            for (int j2 = 0; j2 < 50; j2++) {
                mSnow[j2].y += mSnow[j2].vy;
                g.drawImage(mImg_snow[mSnow[j2].imgNo], (int) mSnow[j2].x, (int) mSnow[j2].y, 0);
                if (mSnow[j2].y > (float) mMaxY) {
                    mSnow[j2].y = -20F;
                }
            }

        }
        g.drawImage(mImg_Land[0], 0, moveFoot, 0);
        if (GameScreen == 1) {
            g.drawImage(mImg_Score, 0, 0, 0);
            Draw_number(g, Score, mImg_Score.getWidth(), 0);
        } else {
            springT = 0;
        }
        g.drawImage(mImg_Sound[sound], mMaxX - mImg_Sound[sound].getWidth(), 0, 0);

        if (Sensor == true) {
            g.drawImage(imgSensor, getWidth() - 40, getHeight() - 40, Graphics.LEFT | Graphics.TOP);

        } else {
            g.drawImage(imgUnSensor, getWidth() - 40, getHeight() - 40, Graphics.LEFT | Graphics.TOP);
        }
        if (play == true) {
            g.drawImage(imgPause, 0, getHeight() - 40, Graphics.LEFT | Graphics.TOP);

        } else {
            g.drawImage(imgPlay, 0, getHeight() - 40, Graphics.LEFT | Graphics.TOP);
        }
    }

    public void pointerPressed(int i, int j) {
        menu_count = 0;
        if (i > getWidth() - 40 && j > getHeight() - 40) {
            if (Sensor == true) {
                Sensor = false;
            } else {
                Sensor = true;
            }
        }
        if (i < 40 && j > getHeight() - 40) {
            if (play == true) {
                play = false;
            } else {
                play = true;
            }
        }
        if (j < 50 && i > getWidth() - 50) {
            if (sound == 0) {
                sound = 1;
            } else {
                sound = 0;
            }
        }
        switch (GameScreen) {
            case 1: // '\001'
                if (i < getWidth() / 2) {
                    flag = 1;
                } else {
                    flag = 2;
                }

            default:
                break;

            case 0: // '\0'
                Handle(i, j);
//                if (i < getWidth() / 2) {
//                    if (menu_count <= 2) {
//                        menu_count++;
//                    } else {
//                        menu_count = 1;
//                    }
//                    System.out.println("down___" + menu_count);
//                } else {
//
//                    if (menu_count > 1) {
//                        menu_count--;
//                    } else {
//                        menu_count = 3;
//                    }
//                    System.out.println("up__" + menu_count);
//
//                }

                break;

            case 3: // '\003'
                if (Rect2RectIntersection((mMaxX - mImg_Back[RetrySel].getWidth()) / 2, (mMaxY / 2 + mImg_highPopUp.getHeight() + 20) - 100, mImg_Back[RetrySel].getWidth(), mImg_Back[RetrySel].getHeight(), i, j, 5, 5)) {
                    RetrySel = 1;
                } else {
                    RetrySel = 0;
                }
                break;

            case 2: // '\002'
                int k = mMaxY / 2 + mImg_Pupup.getHeight();
                if (Rect2RectIntersection((mMaxX - mImg_Retry[RetrySel].getWidth()) / 2, k - 90, mImg_Back[RetrySel].getWidth(), mImg_Back[RetrySel].getHeight(), i, j, 5, 5)) {
                    System.out.println("ha ha ha ha ha");
                    RetrySel = 1;
                } else {
                    RetrySel = 0;
                }
                break;
        }

        // bat sang phai trai


    }

    public void pointerReleased(int i, int j) {

        switch (GameScreen) {
            case 1: // '\001'

            default:
                break;

            case 0: // '\0'
                if (startSel == 1) {
                    spring = tocdonhay;
                    playerJump = jump;
                    newScore = readRecords1();
                    gameover_check = false;
                    GameScreen = 1;
                }
                if (scoreSel == 1) {
                    gameover_check = true;
                    GameScreen = 3;
                    newScore = readRecords1();
                }
                if (ExitSel == 1) {
                    mid.notifyDestroyed();
                    return;
                }
                RetrySel = startSel = scoreSel = moreSel = ExitSel = 0;
                break;

            case 3: // '\003'
                if (RetrySel == 1 && Rect2RectIntersection((mMaxX - mImg_Back[RetrySel].getWidth()) / 2, (mMaxY / 2 + mImg_highPopUp.getHeight() + 20) - 100, mImg_Back[RetrySel].getWidth(), mImg_Back[RetrySel].getHeight(), i, j, 5, 5)) {
                    GameScreen = 0;
                    return;
                }
                break;

            case 2: // '\002'
                int k = mMaxY / 2 + mImg_Pupup.getHeight();
                if (!Rect2RectIntersection((mMaxX - mImg_Retry[RetrySel].getWidth()) / 2, k - 90, mImg_Back[RetrySel].getWidth(), mImg_Back[RetrySel].getHeight(), i, j, 5, 5) || RetrySel != 1) {
                    break;
                }
                if (Score > readRecords1()) {
                    WinnerRes(Score + "");
                }
                RetrySel = startSel = scoreSel = moreSel = ExitSel = 0;
                GameScreen = 4;
                isGamePlay = false;
                moveBG3 = mImg_BG.getHeight();
                moveBG2 = moveBG3 - mImg_BG.getWidth();
                moveBG = moveBG2 - mImg_BG.getWidth();
                int l = mImg_Land[0].getHeight();
                int i1 = mImg_start[1].getHeight();
                startY = (moveBG3 + mMaxY) - l * 3 - i1 * 3;
                scoreY = (moveBG3 + mMaxY) - l * 3 - i1 * 2;
                moreY = (moveBG3 + mMaxY) - l * 3 - i1;
                ExitY = (moveBG3 + mMaxY) - l * 3;
                HippoY = moveBG3 + 0;
                startSel = scoreSel = moreSel = ExitSel = 0;
                break;
        }
    }

    public void Handle(int i, int j) {
        if (Rect2RectIntersection(mMaxX - mImg_start[startSel].getWidth(), startY, mImg_start[startSel].getWidth(), mImg_start[startSel].getHeight(), i, j, 2, 2)) {
            startSel = 1;
        } else {
            startSel = 0;
        }
        if (Rect2RectIntersection(mMaxX - mImg_score[scoreSel].getWidth(), scoreY, mImg_score[scoreSel].getWidth(), mImg_score[moreSel].getHeight(), i, j, 2, 2)) {
            scoreSel = 1;
        } else {
            scoreSel = 0;
        }
        if (Rect2RectIntersection(mMaxX - mImg_more[moreSel].getWidth(), moreY, mImg_more[moreSel].getWidth(), mImg_more[moreSel].getHeight(), i, j, 2, 2)) {
            ExitSel = 1;
        } else {
            ExitSel = 0;
        }
    }

    protected void keyPressed(int i) {
        int j = getGameAction(i);
        if (i == 42) {
            if (sound == 0) {
                sound = 1;
            } else {
                sound = 0;
            }
        }
        label0:
        switch (GameScreen) {
            default:
                break;

            case 1: // '\001'
                switch (j) {
                    case 2: // '\002'
                        flag = 1;
                        break;

                    case 5: // '\005'
                        flag = 2;
                        break;
                }
                break;

            case 0: // '\0'
                switch (j) {

                    default:
                        break;

                    case 2: // '\002'
                        flag = 1;
                        //System.out.println("phai trai");
                        break label0;

                    case 5: // '\005'
                        flag = 2;
                        break label0;

//                    case 6: // '\006'
//                        if (menu_count <= 2) {
//                            menu_count++;
//                        } else {
//                            menu_count = 1;
//                        }
//                        System.out.println("down___" + menu_count);
//                        break label0;
//
//                    case 1: // '\001'
//                        if (menu_count > 1) {
//                            menu_count--;
//                        } else {
//                            menu_count = 3;
//                        }
//                        System.out.println("up__" + menu_count);
//                        break label0;

                    case 8: // '\b'
                        switch (menu_count) {
                            case 1: // '\001'
                                spring = tocdonhay;
                                playerJump = jump;
                                newScore = readRecords1();
                                gameover_check = false;
                                GameScreen = 1;
                                break;

                            case 2: // '\002'
                                GameScreen = 3;
                                newScore = readRecords1();
                                gameover_check = true;
                                break;

                            case 3: // '\003'
                                mid.notifyDestroyed();
                                return;
                        }
                        break;
                }
                break;

            case 2: // '\002'
                switch (j) {
                    default:
                        break;

                    case 8: // '\b'
                        if (Score > readRecords1()) {
                            WinnerRes(Score + "");
                        }
                        gameover_check = false;
                        RetrySel = startSel = scoreSel = moreSel = ExitSel = 0;
                        GameScreen = 4;
                        isGamePlay = false;
                        moveBG3 = mImg_BG.getHeight();
                        moveBG2 = moveBG3 - mImg_BG.getWidth();
                        moveBG = moveBG2 - mImg_BG.getWidth();
                        int k = mImg_Land[0].getHeight();
                        int l = mImg_start[1].getHeight();
                        startY = (moveBG3 + mMaxY) - k * 3 - l * 3;
                        scoreY = (moveBG3 + mMaxY) - k * 3 - l * 2;
                        moreY = (moveBG3 + mMaxY) - k * 3 - l;
                        ExitY = (moveBG3 + mMaxY) - k * 3;
                        HippoY = moveBG3 + 0;
                        menu_count = 1;
                        startSel = scoreSel = moreSel = ExitSel = 0;
                        break;
                }
                break;

            case 3: // '\003'
                switch (j) {
                    case 8: // '\b'
                        menu_count = 1;
                        gameover_check = true;
                        GameScreen = 0;
                        break;
                }
                break;
        }
    }

    public void GameLogic() {
        for (int i = 0; i < 20; i++) {
            if (mCoinPoint[i].y <= -100) {
                continue;
            }
            if (mCoinPoint[i].step == 4 && mCoinPoint[i].gift > 0) {
                mCoinPoint[i].gift--;
            } else {
                mCoinPoint[i].y -= 10;
            }
        }

        if (Score > newScore && newshow == 0) {
            newshow++;
            getpoint(0, 1);
        }
        if (ft < 1 && spring < 1 && playerJump < 15) {
            playerJump = playerJump + inc;
        } else {
            if (ft > 0) {
                ft--;
            }
            if (spring > 0) {
                spring--;
            }
        }
        if (Power > 0) {
            Power--;
        }
        for (int j = 0; j < mCluadCunt; j++) {
            mCluad[j].y += mDcr;
            if (mCluad[j].y > mMaxY) {
                mCluad[j].step = abs(mRand.nextInt() % 2);
                mCluad[j].y = -mImg_Claud[mCluad[j].step].getHeight();
                mCluad[j].x = abs(mRand.nextInt() % (mMaxX - mImg_Claud[mCluad[j].step].getWidth()));
            }
        }

        for (int k = 0; k < 100; k++) {
            mBall[k].y = mBall[k].y + mDcr * 2;
            if (mBall[k].y > mMaxY) {
                gretatCounter++;
                mBall[k].y = findLowest() - 80 - abs(mRand.nextInt() % 30);
                Levellenth++;
                Levellenth++;
                if (Levellenth == 110 || Levellenth == 220 || Levellenth == 440 || Levellenth == 440) {
                    if (newScore >= Score);
                }
                int l = 0;
                int k1 = Math.abs(mRand.nextInt());
                if (k1 % 15 == 0) {
                    l = 1;
                } else if (k1 % 15 == 1 && Levellenth > 110) {
                    l = 2;
                } else if (k1 % 15 == 3 && Levellenth > 110) {
                    l = 3;
                } else if (k1 % 15 == 4) {
                    l = 4;
                } else if (k1 % 15 == 5) {
                    l = 5;
                } else if (k1 % 15 == 6) {
                    l = 6;
                } else if (k1 % 15 == 7) {
                    l = 7;
                } else if ((k1 % 15 == 12 || k1 % 15 == 13 || k1 % 15 == 8) && Levellenth > 220) {
                    l = 8;
                } else if ((k1 % 15 == 14 || k1 % 15 == 15 || k1 % 15 == 9) && Levellenth > 330) {
                    l = 9;
                } else if (k1 % 15 == 10 && Levellenth > 330) {
                    l = 10;
                } else if (k1 % 15 == 11) {
                    l = 11;
                }
                mBall[k].gift = l;
                mBall[k].x = Math.abs(mRand.nextInt()) % (mMaxX - mImg_Step[1].getWidth());
                if (mBall[k].gift == 8) {
                    mBall[k].setBall(mBall[k].x, greatY, mImg_Step[2].getWidth(), mImg_Step[2].getHeight(), 2, l);
                } else if (mBall[k].gift == 9) {
                    mBall[k].setBall(mBall[k].x, greatY, mImg_Step[3].getWidth(), mImg_Step[3].getHeight(), 3, l);
                } else if (mBall[k].gift == 10) {
                    mBall[k].setBall(mBall[k].x, greatY, mImg_Step[4].getWidth(), mImg_Step[4].getHeight(), 4, l);
                } else if (mRand.nextInt() % 2 == 0) {
                    mBall[k].setBall(mBall[k].x, greatY, mImg_Step[1].getWidth(), mImg_Step[1].getHeight(), 1, l);
                } else {
                    mBall[k].setBall(mBall[k].x, greatY, mImg_Step[0].getWidth(), mImg_Step[0].getHeight(), 0, l);
                }
            }
            if (GameScreen == 0 && moveFoot < mMaxY && (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght > moveFoot) {
                playerJump = jump;
                mp3play(0);
            }
            if (GameScreen != 1) {
                continue;
            }
            if (mBall[k].gift == 2 && Power < 1 && GameScreen != 2 && playerJump < 0) {
                if (Rect2RectIntersection(mBall[k].giftX, mBall[k].y - mImg_mashaniR[0].getHeight(), mImg_mashaniR[0].getWidth(), mImg_mashaniR[0].getHeight(), mPlayer.x, mPlayer.y, mPlayer.dx, mPlayer.dy) && ft < 1) {
                    if (!gameover_check) {
                        GameScreen = 2;
                    }
                    Power = 0;
                }
            } else if (mBall[k].gift == 9) {
                if (Rect2RectIntersection(mBall[k].x, mBall[k].giftY - mRectHieght, mBall[k].dx, mRectHieght, (mPlayer.x + mImg_Boy[0].getWidth() / 2) - mRectWidth / 2, (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght, mRectWidth, mRectHieght) && mBall[k].y > 0 && playerJump >= 0) {
                    if (springT > 0) {
                        spring = nhaynhe;
                        mp3play(1);
                    } else {
                        mp3play(0);
                    }
                    playerJump = jump;
                    if (!mBall[k].point) {
                        mBall[k].point = true;
                        Score += 10;
                    }
                }
            } else if (Rect2RectIntersection(mBall[k].x, mBall[k].y - mRectHieght, mBall[k].dx, mRectHieght, (mPlayer.x + mImg_Boy[0].getWidth() / 2) - mRectWidth / 2, (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght, mRectWidth, mRectHieght) && mBall[k].y > 0 && playerJump >= 0) {
                if (mBall[k].gift == 10) {
                    mBall[k].y = mMaxY + 10;
                }
                if (springT > 0) {
                    spring = nhaynhe;
                }
                if (springT > 0) {
                    springT--;
                }
                playerJump = jump;
                if (!mBall[k].point) {
                    mBall[k].point = true;
                    Score += 10;
                }
                if (mBall[k].gift == 2 && Rect2RectIntersection(mBall[k].giftX, mBall[k].y - mRectHieght, mImg_mashaniR[0].getWidth(), mRectHieght, (mPlayer.x + mImg_Boy[0].getWidth() / 2) - mRectWidth / 2, (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght, mRectWidth, mRectHieght) && mBall[k].y > 0) {
                    getpoint(k, 0);
                }
                if (mBall[k].gift == 1 && Rect2RectIntersection(mBall[k].x, mBall[k].y - mRectHieght, mImg_SPower.getWidth(), mRectHieght, (mPlayer.x + mImg_Boy[0].getWidth() / 2) - mRectWidth / 2, (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght, mRectWidth, mRectHieght) && mBall[k].y > 0) {
                    ft = 100;
                    springT = 0;
                    mBall[k].gift = 0;
                }
                if (mBall[k].gift == 3 && Rect2RectIntersection(mBall[k].x, mBall[k].y - mRectHieght, mImg_rocketpow.getWidth(), mRectHieght, (mPlayer.x + mImg_Boy[0].getWidth() / 2) - mRectWidth / 2, (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght, mRectWidth, mRectHieght) && mBall[k].y > 0) {
                    Power = 200;
                    mBall[k].gift = 0;
                }
                if (mBall[k].gift == 4 || mBall[k].gift == 5 || mBall[k].gift == 6 || mBall[k].gift == 7) {
                    int i1 = Math.abs(mBall[k].gift - 4) % 4;
                    int l1 = (mImg_Step[mBall[k].step].getWidth() - mImg_Coin[i1].getWidth()) / 2;
                    int i2 = mImg_Coin[i1].getHeight();
                    if (Rect2RectIntersection(mBall[k].x + l1, mBall[k].y - i2, mImg_Coin[i1].getWidth(), mImg_Coin[i1].getHeight(), (mPlayer.x + mImg_Boy[0].getWidth() / 2) - mRectWidth / 2, (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght, mRectWidth, mRectHieght) && mBall[k].y > 0) {
                        getpoint(k, 0);
                    }
                }
                if (mBall[k].gift == 11) {
                    int j1 = (mImg_Step[mBall[k].step].getWidth() - mImg_superspring.getWidth()) / 2;
                    if (Rect2RectIntersection(mBall[k].x + j1, mBall[k].y - mRectHieght, mImg_superspring.getWidth(), mRectHieght, (mPlayer.x + mImg_Boy[0].getWidth() / 2) - mRectWidth / 2, (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght, mRectWidth, mRectHieght) && mBall[k].y > 0) {
                        spring = nhaynhe;
                        springT = 5;
                        mBall[k].gift = 0;
                    }
                }
                if (ft == 100) {
                    mp3play(2);
                } else if (springT > 0) {
                    mp3play(1);
                } else {
                    mp3play(0);
                }
            }
            if (moveFoot >= mMaxY || !Rect2RectIntersection(0, moveFoot - mRectHieght, mMaxX, mRectHieght, (mPlayer.x + mImg_Boy[0].getWidth() / 2) - mRectWidth / 2, (mPlayer.y + mImg_Boy[0].getHeight()) - mRectHieght, mRectWidth, mRectHieght) || playerJump < 0) {
                continue;
            }
            playerJump = jump;
            if (!mBall[k].point) {
                mBall[k].point = true;
                Score += 10;
            }
            if (springT > 0) {
                spring = nhaynhe;
                mp3play(1);
            } else {
                mp3play(0);
            }
        }

        if (HippoY < mMaxY) {
            HippoY += mDcr;
        }
        if (startY < mMaxY) {
            startY += mDcr;
        }
        if (scoreY < mMaxY) {
            scoreY += mDcr;
        }
        if (moreY < mMaxY) {
            moreY += mDcr;
        }
        if (ExitY < mMaxY) {
            ExitY += mDcr;
        }
        moveBG += mDcr;
        if (moveBG > mMaxY) {
            moveBG = moveBG2 - mImg_BG.getWidth();
        }
        moveBG2 += mDcr;
        if (moveBG2 > mMaxY) {
            moveBG2 = moveBG3 - mImg_BG.getWidth();
        }
        moveBG3 += mDcr;
        if (moveBG3 > mMaxY) {
            isGamePlay = true;
            moveBG3 = moveBG - mImg_BG.getWidth();
        }
        moveFoot += mDcr;
        if (play) {
            mPlayer.y = mPlayer.y + playerJump + mDcr;
            if (mPlayer.y < mMaxY / 3) {
                mDcr = -playerJump;
                mPlayer.y = mMaxY / 3 + 1;
            } else {
                mDcr = 0;
            }
            if (mPlayer.y < mMaxY / 2) {
                if (playerJump >= 0);
            }
            if (flag == 1) {
                mPlayer.x -= 2;
            } else if (flag == 2) {
                mPlayer.x += 2;
            }
            if (mPlayer.x > mMaxX) {
                mPlayer.x = 0;
            }
            if (mPlayer.x < -mImg_BoyRock[0].getWidth()) {
                mPlayer.x = mMaxX - mImg_BoyRock[0].getWidth();
            }

        }
        if (mPlayer.y > mMaxY && GameScreen != 2 && !gameover_check) {
            GameScreen = 2;
        }
    }

    void getpoint(int i, int j) {
        int k = 0;
        do {
            if (k >= 20) {
                break;
            }
            if (mCoinPoint[k].y <= -100) {
                if (j == 0) {
                    mCoinPoint[k].x = mBall[i].x;
                    mCoinPoint[k].y = mBall[i].y;
                    mCoinPoint[k].step = mBall[i].gift % 4;
                    Score += (mCoinPoint[k].step + 1) * 50;
                    mBall[i].gift = 0;
                } else {
                    mCoinPoint[k].x = mMaxX / 2 - mImg_pints[4].getWidth() / 2;
                    mCoinPoint[k].y = mMaxY / 2 - mImg_pints[4].getHeight() / 2;
                    mCoinPoint[k].step = 4;
                    mCoinPoint[k].gift = 50;
                }
                break;
            }
            k++;
        } while (true);
    }

    public boolean readRecords() {
        boolean flag1 = false;
        try {
            byte abyte0[] = new byte[5];
            for (int j = 1; j <= rs.getNumRecords(); j++) {
                if (rs.getRecordSize(j) > abyte0.length) {
                    abyte0 = new byte[rs.getRecordSize(j)];
                }
                flag1 = true;
                int i = rs.getRecord(j, abyte0, 0);
                String s = new String(abyte0, 0, i);
                newScore = Integer.parseInt(s);
            }

        } catch (Exception exception) {
        }
        return flag1;
    }

    public int readRecords1() {
        try {
            byte abyte0[] = new byte[5];
            for (int j = 1; j <= rs.getNumRecords(); j++) {
                if (rs.getRecordSize(j) > abyte0.length) {
                    abyte0 = new byte[rs.getRecordSize(j)];
                }
                int i = rs.getRecord(j, abyte0, 0);
                System.out.println("------------------------------");
                System.out.println("Record " + j + " : " + new String(abyte0, 0, i));
                int k = Integer.parseInt(new String(abyte0, 0, i));
                System.out.println("int is __________" + k);
                score1 = k;
                System.out.println("------------------------------");
            }

        } catch (Exception exception) {
        }
        return score1;
    }

    public void WinnerRes(String s) {
        deleteRecStore();
        writeRecord(s);
    }

    public void openRecStore() {
        try {
            rs = RecordStore.openRecordStore("hipposcore", true);
        } catch (Exception exception) {
        }
    }

    public void deleteRecStore() {
        if (RecordStore.listRecordStores() != null) {
            try {
                RecordStore.deleteRecordStore("hipposcore");
            } catch (Exception exception) {
            }
        }
    }

    public void writeRecord(String s) {
        byte abyte0[] = s.getBytes();
        try {
            rs.addRecord(abyte0, 0, abyte0.length);
        } catch (Exception exception) {
        }
    }

    public void mp3play(int i) {
        try {
            if (sound == 0) {
                soundplay[i].realize();
                soundplay[i].prefetch();
                soundplay[i].start();
            } else {
                soundplay[i].stop();
            }
        } catch (Exception exception) {
        }
    }

    public void jiglebellsong()
            throws MediaException {
        if (sound == 0) {
            jiglebell.realize();
            jiglebell.prefetch();
            jiglebell.start();
            jiglebell.setLoopCount(-1);
        } else {
            jiglebell.stop();
        }
    }

    public void run() {
        do {
            try {

                processSensors();

                Thread.sleep(0L);

                repaint();
                if (sound == 0) {

                    jiglebellsong();
                } else {
                    jiglebell.stop();
                }
                Runtime.getRuntime().freeMemory();
                Runtime.getRuntime().gc();

            } catch (Exception exception) {
            }
        } while (true);
    }

    /*  public void dataReceived(SensorConnection sensorconnection, Data adata[], boolean flag1)
    {
    sx = adata[0].getDoubleValues()[0];
    sy = adata[1].getDoubleValues()[0];
    sz = adata[2].getDoubleValues()[0];
    if(sx > 0.0D)
    {
    flag = 1;
    mPlayer.x -= sx * 2D;
    } else
    {
    mPlayer.x -= sx * 2D;
    flag = 2;
    }
    }
     */
    MIDlet mid;
    Image mImg_BGMenu;
    Image mImg_BG;
    Image mImg_rocketpow;
    Image mImg_BoyRock[];
    Image mImg_mashaniR[];
    Image mImg_mashaniL[];
    Image mImg_shield;
    Image mImg_SPower;
    Image mImg_Land[];
    Image mImg_Step[];
    Image mImg_Boy[];
    Image mImg_BoyS[];
    Image mImg_Coin[];
    Image mImg_Claud[];
    Image mImg_superspring;
    Image mImg_pints[];
    Image mImg_Pupup;
    Image mImg_Fontstrip;
    Image mImg_Score;
    Image mImg_Sound[];
    Image mImg_start[];
    Image mImg_score[];
    Image mImg_more[];
    Image mImg_Exit[];
    Image mImg_Retry[];
    Image mImg_GameMore[];
    Image mImg_highPopUp;
    Image mImg_Back[];
    Image mImg_hippo;
    int startY;
    int scoreY;
    int moreY;
    int ExitY;
    int HippoY;
    int startSel;
    int scoreSel;
    int moreSel;
    int ExitSel;
    int RetrySel;
    int newScore;
    int newshow;
    Snow mSnow[];
    Snow mSnow2[];
    final int mSnowNo = 50;
    final int mSnowNo2 = 200;
    Image mImg_snow[];
    int mStripCount;
    int mSmallWidth;
    int mSmallHight;
    int mAniCount;
    int sound = 0;
    int mMaxX;
    int mMaxY;
    int mTime;
    int acc_x;
    int acc_y;
    int acc_z;
    Ball mBall[];
    Ball mCoinPoint[];
    Ball mPlayer;
    final int mTotalBall = 100;
    final int mTotalcoin = 20;
    int mDcr;
    int playerJump;
    int ft;
    int Power;
    int spring;
    int springT;
    int inc;
    int mRectWidth;
    int mRectHieght;
    int greatY;
    int gretatCounter;
    int flag;
    int jump;
    int moveBG;
    int moveBG2;
    int moveBG3;
    int moveFoot;
    int counter;
    int counter2;
    boolean isGamePlay;
    boolean ch_drag;
    int menu_count;
    int Levellenth;
    int Score;
    Ball mCluad[];
    int mCluadCunt;
    public Random mRand;
    boolean adv;
    public static final int springJump = 10;
    public static final int springTime = 5;
    public static final int RocketJump = 100;
    public static final int jumpPoint = 10;
    public static final int Levellenth1 = 110;
    public static final int Levellenth2 = 220;
    public static final int Levellenth3 = 330;
    public static final int Levellenth4 = 440;
    public static final int Levellenth5 = 6000;
    boolean gameover_check;
    static final String WIN_REC_STORE = "hipposcore";
    private RecordStore rs;
    int GameScreen;
    static final int GameStart = 0;
    static final int GamePlay = 1;
    static final int GameOver = 2;
    static final int GameHightScore = 3;
    static final int mGameRetry2Start = 4;
    //  private static SensorConnection mconnection;
    // SensorInfo asensorinfo[];
    // SensorInfo sensorinfo;
    private int score1;
    private VolumeControl vc;
    private Player soundplay[];
    public Player jiglebell;
    String s1;
    public double sx;
    public double sy;
    public double sz;
}
