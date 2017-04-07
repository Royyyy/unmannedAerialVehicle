package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Roy on 2017/3/25.
 */
public class ToggleView extends View {
    private Bitmap backgroundBitmap;
    private Paint paint;
    private Bitmap slideBitmap;
    private Boolean switchState = false; //开关状态，默认是关
    private float currentX;

    /**
     * 如果在布局文件中，执行该方法
     * @param context
     * @param attrs
     */
    public ToggleView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    /**
     * 如果在布局文件中，并且为该控件设置了style属性，才执行该方法，否则就会执行两个参数的方法
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String namespace = "http://schemas.android.com/apk/res/com.unmannedaerialvehicle.neusoft.unmannedaerialvehicle";
        int switch_backgroundResourceValue = attrs.getAttributeResourceValue(namespace , "switch_background", -1);
        setSwitchBackgroundResource(switch_backgroundResourceValue);
        int slide_buttonResourceValue = attrs.getAttributeResourceValue(namespace, "slide_button", -1);
        setSlideButtonResource(slide_buttonResourceValue);

        switchState = attrs.getAttributeBooleanValue(namespace, "switch_state", false);
        //初始化对象
        Log.e("TAG", "backgroud:" + switch_backgroundResourceValue +"switch_state:" + switchState);
        init();
    }

    /**
     * 初始化相关工作
     */
    private void init() {
        paint = new Paint();
    }

    /**
     * 测量：一般主要用于设置控件大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension(int measuredWidth, int measuredHeight)
        //自己重写这个方法
        setMeasuredDimension(backgroundBitmap.getWidth(),backgroundBitmap.getHeight());

    }

    /**
     * 如果在代码使用中，调用该方法
     * @param context
     */
    public ToggleView(Context context) {
        super(context);
    }

    /**
     * 获取背景图片资源,更具图片资源的ID，把它转换成一张位图---成品
     * @param switchBackground
     */
    public void setSwitchBackgroundResource(int switchBackground) {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);

    }

    /**
     * 获取滑块资源
     * @param slideButton
     */
    public void setSlideButtonResource(int slideButton) {
        slideBitmap = BitmapFactory.decodeResource(getResources(), slideButton);

    }

    /**
     * 获取控件状态
     * @param switchState
     */
    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }

    /**
     * 绘制图片   参数为画布  画布进行画图
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //[1]绘制背景模块
        canvas.drawBitmap(backgroundBitmap,0,0,paint);

        //[2]绘制滑块的模块
        //根据滑块的滑动，设置滑块的位置
        if (isTouchMode){
            float newLeft = (int)currentX;
            if (currentX <= 0){
                newLeft = 0;
            }else if (currentX >backgroundBitmap.getWidth()-slideBitmap.getWidth()){
                currentX = backgroundBitmap.getWidth()-slideBitmap.getWidth();
            }
            canvas.drawBitmap(slideBitmap,newLeft,0,paint);
        }else {
            //滑块的最后停留的位置
            if (switchState) {  //开
                canvas.drawBitmap(slideBitmap, backgroundBitmap.getWidth() - slideBitmap.getWidth(), 0, paint);
            } else { //关
                canvas.drawBitmap(slideBitmap, 0, 0, paint);

            }

        }
    }

    boolean isTouchMode = false;
    @Override  //控件的触摸事件
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:  //被按下
                Log.e("TAG","MotionEvent.ACTION_DOWN"+event.getX());
                isTouchMode = true;
                break;
            case MotionEvent.ACTION_MOVE:  //滑动
                Log.e("TAG","MotionEvent.ACTION_MOVE"+event.getX());
                break;
            case MotionEvent.ACTION_UP:  //松开
//                Log.e("TAG","MotionEvent.ACTION_UP"+event.getX());
                isTouchMode = false;
                currentX = event.getX();
                //比较当前位置的值跟中心点位置的值的大小，把比较的结果给开关标志位switchState
                float center = backgroundBitmap.getWidth()/2.0f;
                boolean state = currentX>center;
                if (state!= switchState && onSwitchStateListener !=null) {
                    //开关的状态更新了，并且把这个状态传递到外部去
                    onSwitchStateListener.onSwitchStateUpdate(state);
                }
                switchState = state;
                break;
            default:
                break;
        }
        //执行该方法才能够重新绘制，不能直接调用onDraw()
        invalidate();
        return true; //允许相应的触摸事件
    }


    public void setOnSwitchStateListener(OnSwitchStateListener onSwitchStateListener) {
        this.onSwitchStateListener = onSwitchStateListener;
    }

    public OnSwitchStateListener getOnSwitchStateListener() {
        return onSwitchStateListener;
    }

    //声明接口变量，并且设置它的set/get方法
    private OnSwitchStateListener onSwitchStateListener;
    //开关监听器，把开关内部更新的状态，传给外部
    //外部拿到这个值，可以实现业务逻辑的要求
    //仿照OnClickListener写的接口
    public interface OnSwitchStateListener{
        public void onSwitchStateUpdate(boolean switchState);
    }
}
