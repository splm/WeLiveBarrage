package me.splm.app.welivebarrage;

import android.graphics.Canvas;

public interface IDanmakuItem {

    void doDraw(Canvas canvas);

    void setTextSize(int sizeInDip);

    void setTextColor(int colorResId);

    void setStartPosition(int x, int y);

    void setSpeed(int speed);

    void setSpeedFactor(float factor);

    float getSpeedFactor();

    boolean isOut();

    boolean willHit(IDanmakuItem runningItem);

    void release();

    int getWidth();

    int getHeight();

    int getCurrX();

    int getCurrY();
}
