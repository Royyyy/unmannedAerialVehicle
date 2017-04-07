package com.unmannedaerialvehicle.neusoft.unmannedaerialvehicle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        //打3秒钟广告进入主界面

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //启动主程序
                Intent intent = new Intent(MainActivity.this, userIndcatorActivity.class);
                startActivity(intent);
                //启动完后,关掉本身界面
                finish();
            }
        }, 3 * 1000);//延迟3秒,执行Runnable


    }
}