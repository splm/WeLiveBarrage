# WeLiveBarrage
You can call it DANMAKU!!!

## Installation

![](https://github.com/splm/WeLiveBarrage/blob/master/untitled.gif)
 

### **Step 1.** Add the JitPack repository to your build file

```xml
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```



### **Step 2.** Add the dependency

```xml
dependencies {
    implementation 'com.github.splm:WeLiveBarrage:1.0.0'
}
```

## Usage

[![](https://jitpack.io/v/splm/WeLiveBarrage.svg)](https://jitpack.io/#splm/WeLiveBarrage)

### 0.Introduction

```xml
<attr name="max_row" format="integer" /> <!-- 最大行数 -->
<attr name="text_size" format="integer" /><!-- 弹幕大小  -->
<attr name="max_per_row" format="integer" /><!-- 每行最大显示弹幕条目 -->
<attr name="color_random" format="boolean" /> <!-- 使用随机颜色 -->
<attr name="internal_time" format="integer" /> <!-- 间隔时间 -->
```

### 1.Insert to XML

```xml
<me.splm.app.welivebarrage.WeLiveBarrageView
        android:id="@+id/barrage"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        app:color_random="true"
        app:internal_time="1000"
        app:layout_constraintBottom_toTopOf="@+id/hide_btn"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:max_per_row="2"
        app:max_row="4"
        app:text_size="20" />
```

### 2.Use in Java file

```java
WeLiveBarrageView mBarrageView = findViewById(R.id.barrage);
List<SimpleDanmakuItem> list=new ArrayList<>();
for(int i =0;i<2;i++){
    list.add(new SimpleDanmakuItem("测试弹幕 " + i, android.R.color.holo_orange_dark));
}
mBarrageView.addItems(list);
mBarrageView.display();
```



END



## License

> Copyright (c) 2019 splm
>
> Permission is hereby granted, free of charge, to any person obtaining a copy
> of this software and associated documentation files (the "Software"), to deal
> in the Software without restriction, including without limitation the rights
> to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
> copies of the Software, and to permit persons to whom the Software is
> furnished to do so, subject to the following conditions:
>
> The above copyright notice and this permission notice shall be included in all
> copies or substantial portions of the Software.
>
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
> IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
> FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
> AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
> LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
> OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
> SOFTWARE.