package com.zixueku.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-27 下午4:55:10
 */
public class NoteTextView extends TextView {

	private Paint paint;

	public NoteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint = new Paint();
		paint.setColor(0xff188ffc);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth((float) 2.5); // 设置线宽
		paint.setAlpha(70);
	}

	/**
	 * 重画界面
	 * 
	 * @see android.widget.TextView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		int lineHeight = this.getLineHeight();

		int topPadding = this.getPaddingTop();
		int leftPadding = this.getPaddingLeft();

		float textSize = getTextSize();
		setGravity(Gravity.LEFT | Gravity.TOP);

		int y = (int) (topPadding + textSize);

		for (int i = 0; i < getLineCount(); i++) {

			canvas.drawLine(leftPadding-5, y + 10, getRight() - leftPadding, y + 10, paint);
			y += lineHeight;
		}
		canvas.translate(0, 0);

		super.onDraw(canvas);
	}

	@Override
	protected int computeVerticalScrollRange() {
		return super.computeVerticalScrollRange();
	}

}
