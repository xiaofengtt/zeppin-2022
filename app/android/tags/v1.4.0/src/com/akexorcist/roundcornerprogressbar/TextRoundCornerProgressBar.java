/*

Copyright 2015 Akexorcist

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package com.akexorcist.roundcornerprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import com.zixueku.R;

public class TextRoundCornerProgressBar extends LinearLayout {
    private final static int DEFAULT_PROGRESS_BAR_HEIGHT = 30;

    private LinearLayout layoutBackground;
    private LinearLayout layoutProgress;
    private LinearLayout layoutSecondaryProgress;
    private TextView textViewValue;

    private int backgroundWidth = 0;
    private int backgroundHeight = 0;

    private boolean isProgressBarCreated = false;
    private boolean isProgressSetBeforeDraw = false;
    private boolean isMaxProgressSetBeforeDraw = false;
    private boolean isBackgroundColorSetBeforeDraw = false;
    private boolean isProgressColorSetBeforeDraw = false;

    private boolean autoTextChange = false;

    private String text = "";
    private String textUnit = "";
    private float max = 100;
    private float progress = 0;
    private float secondaryProgress = 0;
    private int radius = 10;
    private int padding = 5;
    private int textSize = 18;
    private int textPadding = 10;
    private int textWidth = 100;

    private int textColor = Color.parseColor("#ff333333");
    private int progressColor = Color.parseColor("#ff7f7f7f");
    private int secondaryProgressColor = Color.parseColor("#7f7f7f7f");
    private int backgroundColor = Color.parseColor("#ff5f5f5f");

    @SuppressLint("NewApi")
    public TextRoundCornerProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            isProgressBarCreated = false;
            isProgressSetBeforeDraw = false;
            isMaxProgressSetBeforeDraw = false;
            isBackgroundColorSetBeforeDraw = false;
            isProgressColorSetBeforeDraw = false;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.round_corner_with_text_layout, this);
            setup(context, attrs);
            isProgressBarCreated = true;
            return;
        }

        setBackgroundColor(backgroundColor);

        setGravity(Gravity.CENTER);

        setPadding(0, (int)dp2px(5), 0, (int)dp2px(5));

        TextView tv = new TextView(context);
        tv.setText("TextRoundCornerProgressBar");
        addView(tv);
    }

    @SuppressLint("NewApi")
    private void setup(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerProgress);

        autoTextChange = typedArray.getBoolean(R.styleable.RoundCornerProgress_rcAutoTextChange, false);

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, metrics);

        radius = (int) typedArray.getDimension(R.styleable.RoundCornerProgress_rcBackgroundRadius, radius);
        textViewValue = (TextView) findViewById(R.id.round_corner_progress_text);
        textSize = (int) typedArray.getDimension(R.styleable.RoundCornerProgress_rcTextProgressSize, textSize);
        textViewValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textViewValue.setTextColor(typedArray.getColor(R.styleable.RoundCornerProgress_rcTextProgressColor, textColor));
        text = typedArray.getString(R.styleable.RoundCornerProgress_rcTextProgress);
        text = (text == null) ? "" : text;
        textViewValue.setText(text);
        textPadding = (int) typedArray.getDimension(R.styleable.RoundCornerProgress_rcTextProgressPadding, textPadding);
        textViewValue.setPadding(textPadding, 0, textPadding, 0);
        textUnit = typedArray.getString(R.styleable.RoundCornerProgress_rcTextProgressUnit);
        textUnit = (textUnit == null) ? "" : textUnit;
        textWidth = (int) typedArray.getDimension(R.styleable.RoundCornerProgress_rcTextProgressWidth, textWidth);

        layoutBackground = (LinearLayout) findViewById(R.id.round_corner_progress_background);
        padding = (int) typedArray.getDimension(R.styleable.RoundCornerProgress_rcBackgroundPadding, padding);
        layoutBackground.setPadding(padding, padding, padding, padding);
        if (!isBackgroundColorSetBeforeDraw) {
            setBackgroundColor(typedArray.getColor(R.styleable.RoundCornerProgress_rcBackgroundColor, backgroundColor));
        }
        ViewTreeObserver observer = layoutBackground.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutBackground.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    backgroundWidth = layoutBackground.getMeasuredWidth() - textWidth;
                    height = layoutBackground.getMeasuredHeight();
                } else {
                    backgroundWidth = layoutBackground.getWidth() - textWidth;
                    height = layoutBackground.getHeight();
                }
                backgroundHeight = (height == 0) ? (int) dp2px(DEFAULT_PROGRESS_BAR_HEIGHT) : height ;
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)layoutBackground.getLayoutParams();
                params.width = backgroundWidth;
                params.height = backgroundHeight;
                layoutBackground.setLayoutParams(params);

                setProgress();
                setSecondaryProgress();
            }
        });

        layoutProgress = (LinearLayout) findViewById(R.id.round_corner_progress_progress);
        layoutSecondaryProgress = (LinearLayout) findViewById(R.id.round_corner_progress_secondary_progress);
        if (!isProgressColorSetBeforeDraw) {
            setProgressColor(
                    typedArray.getColor(R.styleable.RoundCornerProgress_rcProgressColor, progressColor),
                    typedArray.getColor(R.styleable.RoundCornerProgress_rcSecondaryProgressColor, secondaryProgressColor)
            );
        }

        if (!isMaxProgressSetBeforeDraw) {
            max = typedArray.getFloat(R.styleable.RoundCornerProgress_rcMax, 0);
        }

        if (!isProgressSetBeforeDraw) {
            progress = typedArray.getFloat(R.styleable.RoundCornerProgress_rcProgress, 0);
            secondaryProgress = typedArray.getFloat(R.styleable.RoundCornerProgress_rcSecondaryProgress, 0);
        }

        typedArray.recycle();
    }

    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
    private void setProgressColor(ViewGroup layout, int color) {
        int radius = this.radius - (padding / 2);
        GradientDrawable gradient = new GradientDrawable();
        gradient.setShape(GradientDrawable.RECTANGLE);
        gradient.setColor(color);
        gradient.setCornerRadii(new float [] {radius, radius, radius, radius, radius, radius, radius, radius});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackground(gradient);
        } else {
            layout.setBackgroundDrawable(gradient);
        }
    }

    public void setProgressColor(int color) {
        progressColor = color;

        setProgressColor(layoutProgress, color);

        if (!isProgressBarCreated) {
            isProgressColorSetBeforeDraw = true;
        }
    }

    public void setProgressColor(int color, int secondaryColor) {
        progressColor = color;
        secondaryProgressColor = secondaryColor;

        setProgressColor(layoutProgress, color);
        setProgressColor(layoutSecondaryProgress, secondaryColor);

        if (!isProgressBarCreated) {
            isProgressColorSetBeforeDraw = true;
        }
    }

    public void setTextUnit(String unit) {
        textUnit = unit;
        setProgress();
    }

    public String getTextUnit() {
        return textUnit;
    }

    public void setTextProgress(CharSequence text) {
        textViewValue.setText(text);
    }

    public CharSequence getTextProgress() {
        return textViewValue.getText();
    }

    public void setTextColor(int color) {
        textColor = color;
        textViewValue.setTextColor(color);
    }

    public int getTextColor() {
        return textColor;
    }

    public void setAutoTextChange(boolean isAuto) {
        autoTextChange = isAuto;
    }

    public boolean isAutoTextChange() {
        return autoTextChange;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundColor(int color) {
        backgroundColor = color;
        GradientDrawable gradient = new GradientDrawable();
        gradient.setShape(GradientDrawable.RECTANGLE);
        gradient.setColor(backgroundColor);
        gradient.setCornerRadius(radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layoutBackground.setBackground(gradient);
        } else {
            layoutBackground.setBackgroundDrawable(gradient);
        }

        if (!isProgressBarCreated) {
            isBackgroundColorSetBeforeDraw = true;
        }
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public int getSecondaryProgressColor( ) {
        return secondaryProgressColor;
    }

    public void setProgress(float progress) {
        progress = (progress > max) ? max : progress;
        progress = (progress < 0) ? 0 : progress;
        this.progress = progress;
        float ratio = max / progress;

        ViewGroup.LayoutParams params = layoutProgress.getLayoutParams();
        params.width = (int)((backgroundWidth - (padding * 2)) / ratio);
        layoutProgress.setLayoutParams(params);

        if (!isProgressBarCreated) {
            isProgressSetBeforeDraw = true;
        }

        if (autoTextChange) {
            String strProgress = NumberFormat.getInstance().format(
                    (progress % 1 == 0) ? (int) progress : progress
            );

            textViewValue.setText(strProgress + " " + textUnit);
        }
    }

    private void setProgress() {
        setProgress(progress);
    }

    public void setSecondaryProgress(float secondaryProgress) {
        secondaryProgress = (secondaryProgress > max) ? max : secondaryProgress;
        secondaryProgress = (secondaryProgress < 0) ? 0 : secondaryProgress;
        this.secondaryProgress = secondaryProgress;
        float ratio = max / secondaryProgress;

        ViewGroup.LayoutParams params = layoutSecondaryProgress.getLayoutParams();
        params.width = (int)((backgroundWidth - (padding * 2)) / ratio);
        layoutSecondaryProgress.setLayoutParams(params);

        if (!isProgressBarCreated) {
            isProgressSetBeforeDraw = true;
        }
    }

    private void setSecondaryProgress() {
        setSecondaryProgress(secondaryProgress);
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        if (!isProgressBarCreated) {
            isMaxProgressSetBeforeDraw = true;
        }
        this.max = max;
        setProgress(progress);
    }

    public float getProgress() {
        return progress;
    }

    public float getSecondaryProgress() {
        return secondaryProgress;
    }

    @SuppressLint("NewApi")
    private float dp2px(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
