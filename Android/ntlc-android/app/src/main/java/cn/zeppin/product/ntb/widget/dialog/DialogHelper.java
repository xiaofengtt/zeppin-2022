package cn.zeppin.product.ntb.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.geng.library.commonutils.ContextUtils;

import cn.zeppin.product.ntb.R;

/**
 * 描述：自定义消息对话框
 * 开发人: geng
 * 创建时间: 17/9/26
 */

public class DialogHelper extends Dialog {

    public DialogHelper(Context context, int theme) {
        super(context, theme);
    }

    public DialogHelper(Context context) {
        super(context);
    }

    public static Builder EnsureAndCancelDialog(Context context, String title, String message, String okTxt, String cancelTxt,
                                                final OnClickListener confirmHandler, final OnClickListener cancelHandler) {

        Builder builder = new Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(okTxt, confirmHandler)
                .setNegativeButton(cancelTxt, cancelHandler)
                .create()
                .show();
        return builder;
    }

    public static Builder EnsureAndCancelDialog(Context context, String title, String message, String okTxt, String cancelTxt,
                                                final OnClickListener confirmHandler, final OnClickListener cancelHandler, final OnKeyListener onKeyListener) {

        Builder builder = new Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(okTxt, confirmHandler)
                .setNegativeButton(cancelTxt, cancelHandler)
                .setKeyListener(onKeyListener)
                .create()
                .show();
        return builder;
    }

    /**
     * 自定义对话框辅助类
     */
    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private int message_height = 0;
        private int message_fontsize = 0;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;

        private OnClickListener
                positiveButtonClickListener,
                negativeButtonClickListener;

        private OnKeyListener onKeyListener;

        private ILoadListenter loadListenter;


        public Builder(Context context) {
            this.context = context;
        }

        private interface ILoadListenter {
            void autoSend();
        }

        public void setLoadListenter(ILoadListenter loadListenter) {
            this.loadListenter = loadListenter;
        }

        /**
         * 设置文本消息
         *
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置资源消息
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * 设置资源标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * 设置文本标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置对话框文本提示高度
         *
         * @return
         */
        public Builder setMessageHeight(int height) {
            this.message_height = height;
            return this;
        }

        /**
         * 设置对话框文本提示字体大小
         *
         * @return
         */
        public Builder setMessageFontSize(int fontsize) {
            this.message_fontsize = fontsize;
            return this;
        }

        public Builder setView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * 设置确定按钮监听器
         *
         * @param positiveButtonText 资源ID
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 设置确定按钮监听器
         *
         * @param positiveButtonText 按钮上显示的文本
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setKeyListener(OnKeyListener listener) {
            this.onKeyListener = listener;
            return this;
        }

        /**
         * 设置取消按钮监听器
         *
         * @param negativeButtonText 资源ID
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 设置取消按钮监听器
         *
         * @param negativeButtonText 按钮上显示的文本
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 创建自定义对话框
         */
        public DialogHelper create() {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final DialogHelper dialog = new DialogHelper(context, R.style.dialog_prompt);
            // View layout = inflater.inflate(R.layout.dialog_prompt, null);
            dialog.setContentView(R.layout.dialog_prompt);
            // dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            // 设置标题
            ((TextView) dialog.findViewById(R.id.tv_dialog_title)).setText(title);
            // 设置确定按钮
            Button positiveButton = (Button) dialog.findViewById(R.id.dialog_ok);
            if (positiveButtonText != null) {
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(
                                    dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {//如果没有设置确定按钮，则隐藏。
                positiveButton.setVisibility(View.GONE);
            }
            //设置取消按钮
            Button negativeButton = (Button) dialog.findViewById(R.id.dialog_cancel);
            if (negativeButtonText != null) {
                negativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(
                                    dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {//如果没有设置取消按钮，则隐藏。
                dialog.findViewById(R.id.dialog_cancel).setVisibility(View.GONE);
            }
            if (onKeyListener != null) {
                dialog.setOnKeyListener(onKeyListener);
                dialog.setCancelable(false);
            }
            // 设置消息内容
            TextView message_tv = (TextView) dialog.findViewById(R.id.tv_dialog_content);
            if (message != null) {
                message_tv.setVisibility(View.VISIBLE);
                message_tv.setText(message);
                if (message_height > 0) {
                    message_tv.setMovementMethod(ScrollingMovementMethod.getInstance()); //使TextView可以滚动
                    message_tv.setHeight(message_height);
                }
                if (message_fontsize > 0) {
                    message_tv.setTextSize(message_fontsize);
                }
            } else {
                message_tv.setVisibility(View.GONE);
            }
            dialog.setCanceledOnTouchOutside(false);
            // 设置宽度为屏宽, 靠近屏幕底部。
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ContextUtils.getSreenWidth(context) * 4 / 5; // 宽度持平
            window.setAttributes(lp);
            dialog.setCancelable(false);
            return dialog;
        }

        public DialogHelper createSendsms() {
//            final DialogHelper sendSmsDialog = new DialogHelper(context, R.style.dialog_prompt);
//            sendSmsDialog.setContentView(R.layout.dialog_sendsms);
//            // 设置标题
//            ((TextView) sendSmsDialog.findViewById(R.id.tv_title)).setText(title);
//            final ClearEditText etCode = (ClearEditText) sendSmsDialog.findViewById(R.id.et_code);
//            final TextView tvGetCode = (TextView) sendSmsDialog.findViewById(R.id.tv_getCode);
//
//            timer = new CountDownTimer(60000, 1000) {
//
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + "s)");
//                }
//
//                @Override
//                public void onFinish() {
//                    tvGetCode.setEnabled(true);
//                    tvGetCode.setText("重新获取");
//                }
//            };
//            // 设置确定按钮
//            Button confirm = (Button) sendSmsDialog.findViewById(R.id.btn_confirm);
//            confirm.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {//确定
//                    positiveButtonClickListener.onClick(sendSmsDialog, DialogInterface.BUTTON_NEGATIVE);
//                }
//            });
//            //设置取消按钮
//            Button cancel = (Button) sendSmsDialog.findViewById(R.id.btn_cancel);
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    sendSmsDialog.dismiss();
//                    timer.cancel();
//                    positiveButtonClickListener.onClick(sendSmsDialog, DialogInterface.BUTTON_NEGATIVE);
//                }
//            });
//            //获取短信验证码
//            tvGetCode.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    tvGetCode.setEnabled(false);
//                    timer.start();
//                    codeButtonClickListener.onClick(sendSmsDialog, DialogInterface.BUTTON_NEGATIVE);
//                }
//            });
//            sendSmsDialog.setCancelable(false);
//
//            // 设置宽度为屏宽, 靠近屏幕底部。
//            Window window = sendSmsDialog.getWindow();
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.gravity = Gravity.CENTER; // 紧贴底部
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//            window.setAttributes(lp);
//            sendSmsDialog.show();
//            //自动获取验证码
//            tvGetCode.setEnabled(false);
//            timer.start();
//            if (loadListenter != null) {
//                loadListenter.autoSend();
//            }
//            return sendSmsDialog;
            return null;
        }
    }
}
