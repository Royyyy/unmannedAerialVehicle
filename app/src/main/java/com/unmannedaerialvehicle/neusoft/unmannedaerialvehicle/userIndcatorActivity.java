package com.unmannedaerialvehicle.neusoft.unmannedaerialvehicle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;



import java.util.ArrayList;
import java.util.List;

public class userIndcatorActivity extends AppCompatActivity {


    private Button btn_start;
    private LinearLayout ll;
    private ViewPager viewpager;
    private List<ImageView> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_indcator);
        SharedPreferences prefs = getSharedPreferences("data",MODE_PRIVATE);
        if (prefs.getBoolean("used",false)){
            Intent intent = new Intent(userIndcatorActivity.this,appInfoActivity.class);
            startActivity(intent);
            finish();

        }else {
            //初始化空间
            initViews();
            //初始化数据
            initData();
            //初始化事件
            initEvents();

            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            editor.putBoolean("used",true);
            editor.commit();
        }
    }

    private void initEvents() {
        //为ViewPager设置适配器
        viewpager.setAdapter(new IndexAdapter());
        ll.getChildAt(0).setEnabled(true);
        //为ViewPager设置一个监听器
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int lastPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //把当前页的小灰点变白
                ll.getChildAt(position).setEnabled(true);
                //把上一页的小白变小灰
                ll.getChildAt(lastPosition).setEnabled(false);
                //保存当前的位置
                lastPosition = position;

                if (position == list.size() - 1) {
                    btn_start.setVisibility(View.VISIBLE);
                    //开始体验,启动主程序
                    btn_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent2 = new Intent(userIndcatorActivity.this,appInfoActivity.class);
                            startActivity(intent2);
                            finish();
                        }
                    });

                } else {
                    btn_start.setVisibility(View.GONE);

               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initData() {
        int[] resId = {R.mipmap.user1, R.mipmap.user2, R.mipmap.user3, R.mipmap.user4, R.mipmap.user5};

        list = new ArrayList<ImageView>();//多态，类似儿子变老子

        //为ViewPager准备要显示的内容
        getApplicationContext();
        System.out.println("!!!!!!!!!!"+resId.length);
        for (int i = 0; i < resId.length; i++) {
            ImageView imageView = new ImageView(this);  //this默认是上面getApplicationContext获取到的参数
            imageView.setBackgroundResource(resId[i]);
            list.add(imageView);

            //初始化指示器 ----View或者TextView
            View view = new View(this);
            view.setBackgroundResource(R.drawable.pointer_bg_selector);
//            ll.addView(view);  给一个窗口添加子控件

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);  //要和你布局文件的布局方式对应起来,相对布局就用RelativeLayout
//            ll.addView(view,20,20);  给一个窗口添加子控件，并且设置子控件的大小
            params.leftMargin = 15;   //实例化了param，就可以设置控件的左边距
            ll.addView(view, params);
            ll.getChildAt(i).setEnabled(false);  //获取当前的控件，设置属性为false，在selector里面



        }

    }

    private void initViews() {
        btn_start = (Button) findViewById(R.id.btn_start);
        ll = (LinearLayout) findViewById(R.id.ll);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

    }

    class IndexAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = list.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}