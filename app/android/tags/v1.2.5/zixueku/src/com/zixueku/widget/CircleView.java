package com.zixueku.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.zixueku.R;

/**
 * Created by br on 2014/11/28.
 */
public class CircleView extends View {
	private static final String TAG = "MyView";
	private final Context mContext;
	private final Paint paint_;
	public static float SMALL_CIRCLE_MASK_WIDTH = 4.0F;
	public static float CIRCLE_MASK_WIDTH = 130.0F;

	private final int smallCircleWidthPx_;
	private final int cricleWdithPx_;
	private final int smallRadius;
	private final RectF rectFOutside_;
	private final RectF rectFInside_;
	private final RectF rectFSmallUp_;
	private Point centerPoint_;
	private int radius;
	private android.graphics.Path path_;
	private int currentRadian;
	private RectF rectFSmallDown_;
	
	private int color ;

	public CircleView(Context context) {
		super(context);
		mContext = context;
		this.color = mContext.getResources().getColor(R.color.lightgrey);
		
		this.path_ = new Path();
		this.paint_ = new Paint();
		this.paint_.setAntiAlias(true);
		this.paint_.setColor(mContext.getResources().getColor(R.color.lightgrey));
		this.smallCircleWidthPx_ = dipToPixels((int) SMALL_CIRCLE_MASK_WIDTH);
		this.cricleWdithPx_ = dipToPixels((int) CIRCLE_MASK_WIDTH);
		Log.d(TAG, "width=" + cricleWdithPx_ + " smallWidth=" + smallCircleWidthPx_);
		this.centerPoint_ = new Point(this.cricleWdithPx_ / 2, this.cricleWdithPx_ / 2);
		this.radius = (this.cricleWdithPx_ / 2);
		this.smallRadius = (this.smallCircleWidthPx_ / 2);
		this.rectFOutside_ = new RectF(this.centerPoint_.x - this.radius, this.centerPoint_.y - this.radius, this.centerPoint_.x + this.radius,
				this.centerPoint_.y + this.radius);
		this.rectFInside_ = new RectF(this.centerPoint_.x - this.radius + this.smallCircleWidthPx_, this.centerPoint_.y - this.radius
				+ this.smallCircleWidthPx_, this.centerPoint_.x + this.radius - this.smallCircleWidthPx_, this.centerPoint_.y + this.radius
				- this.smallCircleWidthPx_);
		this.rectFSmallUp_ = new RectF(this.centerPoint_.x - this.smallRadius, this.centerPoint_.y - this.radius, this.centerPoint_.x + this.smallRadius,
				this.centerPoint_.y - this.radius + this.smallCircleWidthPx_);
		this.rectFSmallDown_ = new RectF();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (this.currentRadian >= 360) {
			return;
		}
		this.path_.reset();
		if (this.currentRadian == 0) {
			this.paint_.setStyle(Paint.Style.STROKE);
			this.paint_.setStrokeWidth(this.smallCircleWidthPx_);
			canvas.drawCircle(this.centerPoint_.x, this.centerPoint_.y, this.radius - this.smallRadius, this.paint_);
			return;
		}
		this.paint_.setStyle(Paint.Style.FILL);
		// acrto 其画曲线的顺序不能打乱的,其后面添加的是起点会和上次的结束点直线连接上.
		// 画一个外圆曲线,参数3为负数则逆时针画,正则顺时针画
		this.path_.arcTo(this.rectFOutside_, 90 + currentRadian, 360 - currentRadian);
		
		// 连接上面的结束位置画一个小半圆
		this.path_.arcTo(this.rectFSmallUp_, 90.0F, -180.0F);
		
		// 从小半圆的结束位置画一个内圆曲线
		this.path_.arcTo(this.rectFInside_, 90, currentRadian - 360);
		float f1 = (float) (this.centerPoint_.x + Math.sin(Math.toRadians(this.currentRadian)) * (this.radius - this.smallRadius));
		float f2 = (float) (this.centerPoint_.y - Math.cos(Math.toRadians(this.currentRadian)) * (this.radius - this.smallRadius));
		this.rectFSmallDown_.set(f1 - this.smallRadius, f2 - this.smallRadius, f1 + this.smallRadius, f2 + this.smallRadius);
		// 接内圆曲线结束处画一个小半圆,连接到第一个开始出.
	    //this.path_.arcTo(this.rectFSmallDown_, 90 + this.currentRadian, -180.0F);
		
		canvas.drawPath(this.path_, this.paint_);
	}

	private int dipToPixels(int dip) {
		Resources r = getResources();
		/*
		 * DisplayMetrics metrics = return value * metrics.density;
		 */
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
		return (int) px;
	}

	public void update(int paramAnonymousInt) {
		currentRadian = paramAnonymousInt;
		invalidate();
	}

	public CircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		this.path_ = new Path();
		this.paint_ = new Paint();
		this.paint_.setAntiAlias(true);
		this.paint_.setColor(mContext.getResources().getColor(R.color.lightgrey));
		this.smallCircleWidthPx_ = dipToPixels((int) SMALL_CIRCLE_MASK_WIDTH);
		this.cricleWdithPx_ = dipToPixels((int) CIRCLE_MASK_WIDTH);
		Log.d(TAG, "width=" + cricleWdithPx_ + " smallWidth=" + smallCircleWidthPx_);
		this.centerPoint_ = new Point(this.cricleWdithPx_ / 2, this.cricleWdithPx_ / 2);
		this.radius = (this.cricleWdithPx_ / 2);
		this.smallRadius = (this.smallCircleWidthPx_ / 2);
		this.rectFOutside_ = new RectF(this.centerPoint_.x - this.radius, this.centerPoint_.y - this.radius, this.centerPoint_.x + this.radius,
				this.centerPoint_.y + this.radius);
		this.rectFInside_ = new RectF(this.centerPoint_.x - this.radius + this.smallCircleWidthPx_, this.centerPoint_.y - this.radius
				+ this.smallCircleWidthPx_, this.centerPoint_.x + this.radius - this.smallCircleWidthPx_, this.centerPoint_.y + this.radius
				- this.smallCircleWidthPx_);
		this.rectFSmallUp_ = new RectF(this.centerPoint_.x - this.smallRadius, this.centerPoint_.y - this.radius, this.centerPoint_.x + this.smallRadius,
				this.centerPoint_.y - this.radius + this.smallCircleWidthPx_);
		this.rectFSmallDown_ = new RectF();
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		this.path_ = new Path();
		this.paint_ = new Paint();
		this.paint_.setAntiAlias(true);
		this.paint_.setColor(mContext.getResources().getColor(R.color.lightgrey));
		this.smallCircleWidthPx_ = dipToPixels((int) SMALL_CIRCLE_MASK_WIDTH);
		this.cricleWdithPx_ = dipToPixels((int) CIRCLE_MASK_WIDTH);
		Log.d(TAG, "width=" + cricleWdithPx_ + " smallWidth=" + smallCircleWidthPx_);
		this.centerPoint_ = new Point(this.cricleWdithPx_ / 2, this.cricleWdithPx_ / 2);
		this.radius = (this.cricleWdithPx_ / 2);
		this.smallRadius = (this.smallCircleWidthPx_ / 2);
		this.rectFOutside_ = new RectF(this.centerPoint_.x - this.radius, this.centerPoint_.y - this.radius, this.centerPoint_.x + this.radius,
				this.centerPoint_.y + this.radius);
		this.rectFInside_ = new RectF(this.centerPoint_.x - this.radius + this.smallCircleWidthPx_, this.centerPoint_.y - this.radius
				+ this.smallCircleWidthPx_, this.centerPoint_.x + this.radius - this.smallCircleWidthPx_, this.centerPoint_.y + this.radius
				- this.smallCircleWidthPx_);
		this.rectFSmallUp_ = new RectF(this.centerPoint_.x - this.smallRadius, this.centerPoint_.y - this.radius, this.centerPoint_.x + this.smallRadius,
				this.centerPoint_.y - this.radius + this.smallCircleWidthPx_);
		this.rectFSmallDown_ = new RectF();
	}
	
	
}
