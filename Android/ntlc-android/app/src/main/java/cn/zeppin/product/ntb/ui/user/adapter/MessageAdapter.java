package cn.zeppin.product.ntb.ui.user.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geng.library.commonutils.TimeUtil;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.bean.Message;

/**
 * 描述：消息
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class MessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

    public MessageAdapter(List<Message> data) {
        super(R.layout.activity_message_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        TextView tvMessage = helper.getView(R.id.tv_message);
        if (item.isEllipsize()) {
            tvMessage.setEllipsize(TextUtils.TruncateAt.END);//省略
            tvMessage.setSingleLine(true);
        } else {
            tvMessage.setEllipsize(null);
            tvMessage.setSingleLine(false);
        }
        helper.setText(R.id.tv_time, TimeUtil.formatTimeInMillis(item.getCreatetime(), TimeUtil.sdf3));//
        helper.setText(R.id.tv_title, item.getInfoTitle());
        tvMessage.setText(item.getInfoText());//

        if (item.isFlagRead()) {//已读
            helper.setTextColor(R.id.tv_title, getColor(R.color.alpha_30_black));
            helper.setTextColor(R.id.tv_message, getColor(R.color.alpha_30_black));
        } else {//未读
            helper.setTextColor(R.id.tv_title, getColor(R.color.black));
            helper.setTextColor(R.id.tv_message, getColor(R.color.alpha_50_black));
        }
    }


    private int getColor(int color) {
        return mContext.getResources().getColor(color);
    }
}
