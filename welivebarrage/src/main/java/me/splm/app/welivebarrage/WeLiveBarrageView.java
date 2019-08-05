package me.splm.app.welivebarrage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WeLiveBarrageView extends View {
    private static final int[] COLORLEGENDS={
            android.R.color.holo_blue_bright,
            android.R.color.holo_blue_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_purple,
            android.R.color.holo_red_light};
    private SparseArray<List<IDanmakuItem>> mChannelMaps;
    private Deque<IDanmakuItem> mWaitingQueue = new LinkedList<>();
    private int[] mChannelY;
    private float mYTopOffset = 0.1f;
    private float mYBottomOffset = 0.5f;

    private int mMaxRow = 10;
    private int mTextSize = 18;
    private boolean mAllowColorRandom;

    private int mMaxRunningPerRow = 4;

    private int mViewHeight;

    private int mCurrentStatus;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int STOP = -1;

    private int mPickInternalTime = 1000;
    private long mPreviousTime;

    private Random mRandom = new Random();

    public WeLiveBarrageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeLiveBarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WeLiveBarrageView);
        mMaxRow = array.getInteger(R.styleable.WeLiveBarrageView_max_row, mMaxRow);
        mTextSize = array.getInteger(R.styleable.WeLiveBarrageView_text_size, mTextSize);
        mMaxRunningPerRow = array.getInteger(R.styleable.WeLiveBarrageView_text_size, mMaxRunningPerRow);
        mAllowColorRandom = array.getBoolean(R.styleable.WeLiveBarrageView_color_random, mAllowColorRandom);
        mPickInternalTime = array.getInteger(R.styleable.WeLiveBarrageView_internal_time, mPickInternalTime);
        array.recycle();
        init();
    }

    private void init() {
        drawBackground(Color.TRANSPARENT);
        initChannelAndRows();
        initChannelY();
    }

    private void drawBackground(int color){
        setBackgroundColor(color);
        setDrawingCacheBackgroundColor(color);
    }

    private void initChannelAndRows() {
        mChannelMaps = new SparseArray<>(mMaxRow);
        for (int i = 0; i < mMaxRow; i++) {
            List<IDanmakuItem> list = new ArrayList<>();
            mChannelMaps.put(i, list);
        }
    }

    private void initChannelY() {
        if (mChannelY == null) {
            mChannelY = new int[mMaxRow];
        }
        mViewHeight = getHeight();
        mViewHeight = px2dip(mViewHeight);
        float rowHeight = mViewHeight * (1 - mYTopOffset - mYBottomOffset) / mMaxRow;
        float basicHeight = mViewHeight * mYTopOffset;
        for (int i = 0; i < mMaxRow; i++) {
            int y = px2dip(mTextSize);
            mChannelY[i] = (int) (basicHeight + rowHeight * i * y);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initChannelY();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCurrentStatus == RUNNING) {
            for (int i = 0; i < mMaxRow; i++) {
                List<IDanmakuItem> list = mChannelMaps.get(i);
                for (int j = 0; j < list.size(); j++) {
                    IDanmakuItem item = list.get(j);
                    if (item.isOut()) {
                        //删除这个弹幕Item
                        list.remove(item);
                    } else {
                        //如果弹幕还在屏幕内，该如何绘制？
                        item.doDraw(canvas);
                    }
                }
            }

            if (System.currentTimeMillis() - mPreviousTime > mPickInternalTime) {
                mPreviousTime = System.currentTimeMillis();
                IDanmakuItem item = mWaitingQueue.pollFirst();
                if (item != null) {
                    int indY = findVacant(item);
                    if (indY >= 0) {
                        int x = this.getWidth();
                        int y = mChannelY[indY];
                        item.setStartPosition(x, y);
                        item.doDraw(canvas);
                        mChannelMaps.get(indY).add(item);
                    } else {
                        addItem(item);
                    }
                }
            }

            invalidate();
        } else {
            canvas.drawColor(Color.TRANSPARENT);
            mCurrentStatus = STOP;
        }
    }

    private int findVacant(IDanmakuItem item) {
        try {
            for (int i = 0; i < mMaxRow; i++) {
                List<IDanmakuItem> list = mChannelMaps.get(i);
                if (list.size() == 0) {
                    return i;
                }
            }
            int ind = mRandom.nextInt(mMaxRow);
            for (int i = 0; i < mMaxRow; i++) {
                List<IDanmakuItem> list = mChannelMaps.get((i + ind) % mMaxRow);
                if (list.size() > mMaxRunningPerRow) {//每个弹道最多mMaxRunning个弹幕
                    continue;
                }
                IDanmakuItem di = list.get(list.size() - 1);
                if (!item.willHit(di)) {
                    return (i + ind) % mMaxRow;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void display() {
        mCurrentStatus = RUNNING;
        invalidate();
    }

    public void hide() {
        mCurrentStatus = PAUSE;
        invalidate();
    }

    private void addItem(IDanmakuItem item){
        mWaitingQueue.add(item);
    }

    public void addItem(String content,int color) {
        if(mAllowColorRandom){
            color=randomColor();
        }
        addItem(new DanmakuItem(getContext(), content, this.getWidth(), color, mTextSize));
    }

    public void addItems(List<SimpleDanmakuItem> list) {
        if(list!=null){
            for(SimpleDanmakuItem item : list){
                addItem(item.getContent(), item.getColor());
            }
        }
    }

    public void setSpeed(int speed){
        Iterator<IDanmakuItem> iterator = this.mWaitingQueue.iterator();
        while(iterator.hasNext()){
            iterator.next().setSpeed(speed);
        }
        this.postInvalidate();
    }

    private int randomColor(){
        Random random=new Random();
        int i = random.nextInt(COLORLEGENDS.length);
        return COLORLEGENDS[i];
    }

    private int px2dip(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
