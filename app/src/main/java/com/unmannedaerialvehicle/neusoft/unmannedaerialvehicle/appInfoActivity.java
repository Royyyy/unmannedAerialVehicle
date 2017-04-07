package com.unmannedaerialvehicle.neusoft.unmannedaerialvehicle;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.TextView;

import view.ToggleView;

public class appInfoActivity extends AppCompatActivity {

//    private TextView tView;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
//land
        }
        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
//port
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_app_info);

//        tView = (TextView)findViewById(R.id.tv);
        ToggleView toggleView = (ToggleView) findViewById(R.id.toggleView);
        //通过接口加载图片资源
        toggleView.setSwitchBackgroundResource(R.drawable.bkg_switch);
        //通过接口加载滑盖资源
        toggleView.setSlideButtonResource(R.drawable.btn_slip);
        //通过接口控件开关的状态
        toggleView.setSwitchState(true);
//        toggleView.setOnSwitchStateListener(new ToggleView.OnSwitchStateListener() {
//            @Override
//            public void onSwitchStateUpdate(boolean switchState) {
//                //实现业务逻辑
//                tView.setText("开关目前的状态"+switchState);
//            }
//        });
    }

}
