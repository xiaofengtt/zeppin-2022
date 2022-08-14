package cn.product.fenghuangsport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopBar extends RelativeLayout {
    // 定义显示的控件
    private ImageButton leftButton, rightButton;
    private TextView tvTittle;

    // 左边Button的属性
    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;
    private Drawable leftImageDrawable;
    private int leftImageWidth;
    private int leftImageHeight;

    // 右边Button的属性
    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    // 中间Title的属性
    private String tittle;
    private float tittleTextSize;
    private int tittleTextColor;
    private Drawable tittleBackground;
    // 设置三个控件的格式 或者说是布局  LayoutParams可以设置左右按钮和中间文本的布局
    private LayoutParams leftParams, rightParams, tittleParams;

    private TopBarListener topBarListener;
    public void setOnLeftButtonClickListener(TopBarListener topBarListener){
        this.topBarListener = topBarListener;
    }

    public void setTitle(String tittle){
        if(null != tittle){
            tvTittle.setText(tittle);
        }
    }

    public void setTitleSize(float size){
        if(size > 0){
            tvTittle.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        }
    }

    public void setLeftButtonHide(boolean flag){
        if (flag){
            leftButton.setVisibility(View.GONE);
        }else{
            leftButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 在构造函数中完成控件的初始化 获取xml中的属性  并将xml中的属性赋值给我们在类中定义的与之对应的变量
     *
     * @param context
     * @param attrs
     */
    @SuppressLint("NewApi")
    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //重要的数据结构   通过TypedArray我们可以获取到atts.xml文件中的属性内容
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);                          //R.styleable.TopBar就是xml文件中定义的名称*
        leftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);   //TopBar_leftTextColor：安卓系统自动把我们atts.xml中定义的属性重新命名  命名规则：控件名称+下划线+属性名称*
        leftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
        leftText = ta.getString(R.styleable.TopBar_leftText);
        leftImageDrawable = ta.getDrawable(R.styleable.TopBar_leftImageDrawable);
        leftImageWidth = ta.getLayoutDimension(R.styleable.TopBar_leftImageWidth, 0);
        leftImageHeight = ta.getLayoutDimension(R.styleable.TopBar_leftImageHeight, 0);

        rightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, 0);
        rightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        rightText = ta.getString(R.styleable.TopBar_rightText);

        tittle = ta.getString(R.styleable.TopBar_tittle);
        tittleTextColor = ta.getColor(R.styleable.TopBar_tittleTextColor, 0);
        tittleTextSize = ta.getDimension(R.styleable.TopBar_tittleTextSize, 0);
        tittleBackground = ta.getDrawable(R.styleable.TopBar_tittleBackground);
        ta.recycle();//回收系统资源


        //因为不是Activity不能用findViewById(),   所以我们new出三个控件*
        leftButton = new ImageButton(context);
        rightButton = new ImageButton(context);
        tvTittle = new TextView(context);

        //为左边Button设置我们在xml文件中自定义的属性
        //leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setImageDrawable(leftImageDrawable);
        leftButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        leftButton.setMinimumHeight(leftImageHeight);
//        leftButton.setMinimumWidth(leftImageWidth);
        leftButton.setMaxHeight(20);
        leftButton.setMaxWidth(10);
        if("社保熊".equals(tittle)){
            leftButton.setVisibility(View.VISIBLE);
        }else{
            leftButton.setVisibility(View.GONE);
        }
        //leftButton.setText(leftText);

        //为右边Button设置我们在xml文件中自定义的属性
        //rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        //rightButton.setText(rightText);

        //为中间Tittle设置我们在xml文件中自定义的属性
        tvTittle.setText(tittle);
        tvTittle.setTextColor(tittleTextColor);
        tvTittle.setTextSize(tittleTextSize);
        tvTittle.setGravity(Gravity.CENTER);    //布局为居中

//        setBackgroundColor(0xFFF59563);   //设置TopBar的背景颜色
//        setBackgroundColor(0x82FFFFFF);
        setBackground(tittleBackground);

        // 设置左边Button的布局
        // 宽：WRAP_CONTENT 高：WRAP_CONTENT 布局：居左对齐
        //addRule()方法可以设置Button的布局
//        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams = new LayoutParams(leftImageWidth,
                leftImageHeight);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton, leftParams);

        // 右边按钮的属性
        // 宽：WRAP_CONTENT 高：WRAP_CONTENT 布局：居右对齐
        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton, rightParams);

        // 中间Tittle的属性
        tittleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        tittleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(tvTittle, tittleParams);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                topBarListener.leftButtonClick();
            }
        });

    }
}
