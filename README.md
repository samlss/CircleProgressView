## CircleProgressView
A simple circle progress view.


### [中文](https://github.com/samlss/CircleProgressView/blob/master/README-ZH.md) 
<br>

[![Api reqeust](https://img.shields.io/badge/api-1+-green.svg)](https://github.com/samlss/CircleProgressView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/CircleProgressView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)


![screenshot](https://github.com/samlss/CircleProgressView/blob/master/screenshots/screenshot1.gif)


## Use <br>
Add it in your root build.gradle at the end of repositories：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```
dependencies {
    implementation 'com.github.samlss:CircleProgressView:1.0'
}
```

## Attributes description：

| attr        | description           |
| ------------- |:-------------:|
| circleColor      | set color of the circle |
| progress | set progress(0-100) |
| progressColor | set color of the progress rotating bar |
| progressTextColor | set the color of progress text |
| progressTextSize | set the size of progress text |


## in layout.xml：
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

## in java code：
```
  circleProgressView.setProgress(int progress); //set the progress, the range is 0-100
  circleProgressView.setProgressColor(Color.RED); //set color of the progress rotating bar
  circleProgressView.setProgressTextColor(Color.RED); // set the color of progress text 
  circleProgressView.setProgressTextSize(20); // set the size of progress text
```


## [LICENSE](https://github.com/samlss/CircleProgressView/blob/master/LICENSE)
