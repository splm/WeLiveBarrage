package me.splm.app.welivebarrage;

public class SimpleDanmakuItem {
    private String content;
    private int color;

    public SimpleDanmakuItem(String content, int color) {
        this.content = content;
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public int getColor() {
        return color;
    }
}
