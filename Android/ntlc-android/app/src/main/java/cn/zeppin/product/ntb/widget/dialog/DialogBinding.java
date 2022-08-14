package cn.zeppin.product.ntb.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.zeppin.product.ntb.R;

/**
 * 描述：银行卡绑定弹框
 * 开发人: geng
 * 创建时间: 17/10/19
 */

public class DialogBinding extends Dialog {
    //是否绑定
    private boolean isBinding;
    private View.OnClickListener confirmClickListener;
    private View.OnClickListener deleteClickListener;

    private String title;
    private String btnMsg;
    private int drawRes;

    public DialogBinding(@NonNull Context context, String title, int drawRes, String btnMsg) {
        super(context, R.style.DialogBinding);
        this.title = title;
        this.drawRes = drawRes;
        this.btnMsg = btnMsg;
    }
//
//    public DialogBinding(@NonNull Context context, @StyleRes int themeResId) {
//        super(context, themeResId);
//    }
//
//    protected DialogBinding(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_binding);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        ImageView image = (ImageView) findViewById(R.id.image);
        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        ImageView ivDelete = (ImageView) findViewById(R.id.iv_close);

        tvTitle.setText(title);
        image.setImageDrawable(getContext().getResources().getDrawable(drawRes));
        btnConfirm.setText(btnMsg);

        btnConfirm.setOnClickListener(confirmClickListener);
        ivDelete.setOnClickListener(deleteClickListener);
        setCancelable(false);

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    public void setConfirmClickListener(View.OnClickListener confirmClickListener) {
        this.confirmClickListener = confirmClickListener;
    }

    public void setDeleteClickListener(View.OnClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
    }
//    public interface OnConfirmClickListener extends View.OnClickListener {
//    }
//
//    public interface OnDeleteClickListener extends View.OnClickListener {
//    }

}
