package me.splm.app.welivebarragedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import me.splm.app.welivebarrage.SimpleDanmakuItem;
import me.splm.app.welivebarrage.WeLiveBarrageView;

public class MainActivity extends AppCompatActivity {

    private WeLiveBarrageView mBarrageView;
    private boolean isShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBarrageView = findViewById(R.id.barrage);
        List<SimpleDanmakuItem> list=new ArrayList<>();
        for(int i =0;i<20;i++){
            list.add(new SimpleDanmakuItem("测试弹幕 " + i, android.R.color.holo_orange_dark));
        }
        mBarrageView.addItems(list);
        mBarrageView.display();

        Button hideAndShowBtn = findViewById(R.id.hide_btn);
        hideAndShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = !isShow;
                if(isShow){
                    mBarrageView.hide();
                }else{
                    mBarrageView.display();
                }
            }
        });
    }
}
