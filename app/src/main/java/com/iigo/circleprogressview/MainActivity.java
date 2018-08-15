package com.iigo.circleprogressview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.iigo.library.CircleProgressView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private CircleProgressView circleProgressView;
    private Disposable updateProgressDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgressView = findViewById(R.id.cpv_progress);
        updateProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (updateProgressDisposable != null){
            updateProgressDisposable.dispose();
        }
    }

    private void updateProgress(){
        updateProgressDisposable = Observable.interval(0, 50, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        int progress = (circleProgressView.getProgress() + 1) % 100;
                        circleProgressView.setProgress(progress);
                    }
                });
    }
}
