package me.splm.app.welivebarragedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        for(int i =0;i<500;i++){
            list.add(new SimpleDanmakuItem("A Test BarrageMessage " + i, android.R.color.holo_orange_dark));
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

        final EditText text_ed = findViewById(R.id.text_ed);
        Button text_send_btn = findViewById(R.id.text_send_btn);
        text_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = text_ed.getText().toString();
                if(TextUtils.isEmpty(str)){
                    Toast.makeText(MainActivity.this, "Can't send empty message!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBarrageView.addItem(str, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_1:
                mBarrageView.setSpeed(20);
                break;
            case R.id.action_2:
                mBarrageView.setSpeed(10);
                break;
            case R.id.action_3:
                mBarrageView.setSpeed(5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
