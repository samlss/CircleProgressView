
## CircleProgressView
一个简约的圆圈进度view

[![Api reqeust](https://img.shields.io/badge/api-1+-green.svg)](https://github.com/samlss/CircleProgressView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/CircleProgressView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)


![screenshot](https://github.com/samlss/CircleProgressView/blob/master/screenshots/screenshot1.gif)


## 使用 <br>
在根目录的build.gradle添加这一句代码：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```
dependencies {
    implementation 'com.github.samlss:CircleProgressView:1.0'
}
```

## 属性说明：

| 属性        | 说明           |
| ------------- |:-------------:|
| circleColor      | 设置后面圆的颜色 |
| progress | 设置进度，范围为0-100 |
| progressColor | 设置旋转的进度条的颜色 |
| progressTextColor | 设置进度文本颜色 |
| progressTextSize | 设置进度文本大小 |


## 布局中使用：
```
   <com.iigo.library.CircleProgressView
        android:id="@+id/cpv_progress"
        android:layout_centerInParent="true"
        app:progress="50"
        app:progressTextSize="10dp"
        app:progressColor="@android:color/white"
        app:circleColor="#2968F7"
        app:progressTextColor="@android:color/white"
        android:layout_width="60dp"
        android:layout_height="60dp" />
```

## 代码中使用：
```
  circleProgressView.setProgress(int progress); //设置进度，范围为0-100
  circleProgressView.setProgressColor(Color.RED); //设置旋转的
  circleProgressView.setProgressTextColor(Color.RED); // 设置进度文本颜色
  circleProgressView.setProgressTextSize(20); //设置进度文本大小
```


## [LICENSE](https://github.com/samlss/CircleProgressView/blob/master/LICENSE)
