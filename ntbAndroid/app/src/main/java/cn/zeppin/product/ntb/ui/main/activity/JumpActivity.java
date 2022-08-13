package cn.zeppin.product.ntb.ui.main.activity;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Gift;

import static cn.zeppin.product.ntb.app.AppConstant.INTENT_GIFT;

/**
 * 描述：新用户红包提醒
 * 开发人: geng
 * 创建时间: 17/12/12
 */

public class JumpActivity extends BaseActivity {
    @Bind(R.id.iv_jump)
    ImageView ivJump;
    private int mHeight;
    private Gift data = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_jump;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            data = (Gift) bundle.getSerializable(INTENT_GIFT);
        }
        ivJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(INTENT_GIFT, data);
                startActivity(GiftActivity.class, bundle1);
                finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            Rect outRect = new Rect();
            getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
            mHeight = outRect.height();

            beginTransAnimation();
        }
    }

    private void beginTransAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivJump, "translationY", -mHeight, 0);
        animator.setInterpolator(new JumpInterpolator());
        animator.setDuration(2000);
        animator.start();
    }


    public class JumpInterpolator implements TimeInterpolator {

        //        @Override
//        public float getInterpolation(float input) {
//            Log.d("YYYY", input + "");
//
//            if (input <= 2 / 5f) {
//                Log.d("YYYY", (5 / 5f * input * input) + "");
//                return 25 / 5f * input * input;
//            } else if (input <= 4 / 5f) {
//                Log.d("YYYY", (1 / 2f + 25 / 2f * (input - 3 / 5f) * (input - 3 / 5f)) + "");
//                return 1 / 2f + 25 / 2f * (input - 3 / 5f) * (input - 3 / 5f);
//            } else {
//                Log.d("YYYY", (3 / 4f + 25 * (input - 9 / 10f) * (input - 9 / 10f)) + "");
//                return 3 / 4f + 25 * (input - 9 / 10f) * (input - 9 / 10f);
//            }
//
//        }
        public JumpInterpolator() {
        }

        @SuppressWarnings({"UnusedDeclaration"})
        public JumpInterpolator(Context context, AttributeSet attrs) {
        }

        private float bounce(float t) {
            return t * t * 8.0f;
        }

        @Override
        public float getInterpolation(float t) {
            // _b(t) = t * t * 8
            // bs(t) = _b(t) for t < 0.3535
            // bs(t) = _b(t - 0.54719) + 0.7 for t < 0.7408
            // bs(t) = _b(t - 0.8526) + 0.9 for t < 0.9644
            // bs(t) = _b(t - 1.0435) + 0.95 for t <= 1.0
            // b(t) = bs(t * 1.1226)
            t *= 1.1226f;
            if (t < 0.3535f) return bounce(t);
            else if (t < 0.7408f) return bounce(t - 0.54719f) + 0.8f;
            else if (t < 0.9644f) return bounce(t - 0.8526f) + 0.9f;
            else return bounce(t - 1.0435f) + 0.95f;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
