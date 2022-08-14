package cn.zeppin.product.ntb.ui.bank.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geng.library.commonutils.CollectionUtils;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.MyBank;

/**
 * 描述：选择银行
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class SelectBankAdapter extends BaseQuickAdapter<MyBank, BaseViewHolder> {
    private String currentBankUuid;
    private boolean isShowLimit = false;

    public SelectBankAdapter(List<MyBank> data, String currentBankUuid, boolean isShowLimit) {
        super(R.layout.dialog_banklist_item, data);
        this.currentBankUuid = currentBankUuid;
        this.isShowLimit = isShowLimit;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBank item) {
        //余额支付
//        if (item.getUuid().equals("000")) {
//            helper.setImageDrawable(R.id.iv_bankIcon, mContext.getResources().getDrawable(R.drawable.icon_account_balance));
//            helper.setText(R.id.tv_bankName, item.getShortName());
//
//            int colorInvalid = mContext.getResources().getColor(R.color.color_invalid);
//            LinearLayout llItem = helper.getView(R.id.ll_item);
//            String accountBalance = item.getName();
//            String balance = "账户可用余额" + accountBalance + "元";
//            if (!item.isAvailable()) {//余额不可点
//                helper.setText(R.id.tv_bankName, balance);
//                helper.setText(R.id.tv_dailyLimit, "");
//                llItem.setEnabled(false);
//                helper.setTextColor(R.id.tv_bankName, colorInvalid);
//                helper.setTextColor(R.id.tv_dailyLimit, colorInvalid);
//            } else {
//                helper.setText(R.id.tv_singleLimit, setTxtStyle(balance, 6, balance.length()));
//                helper.setTextColor(R.id.tv_bankName, mContext.getResources().getColor(R.color.black));
//                helper.setTextColor(R.id.tv_dailyLimit, mContext.getResources().getColor(R.color.alpha_40_black));
//                llItem.setEnabled(true);
//            }
//        } else {
        helper.setVisible(R.id.ll_bankLimit, isShowLimit);
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + item.getIconColor()).into((ImageView) helper.getView(R.id.iv_bankIcon));
        helper.setText(R.id.tv_bankName, item.getShortName() + "（" + item.getBankcard() + "）");
        helper.setText(R.id.tv_singleLimit, CollectionUtils.numFormat4UnitString(item.getSingleLimit()));
        helper.setText(R.id.tv_dailyLimit, CollectionUtils.numFormat4UnitString(item.getDailyLimit()));
//        }
        //当前选择的银行卡
        if (!TextUtils.isEmpty(currentBankUuid) && currentBankUuid.equals(item.getUuid())) {
            helper.setVisible(R.id.iv_selected, true);
        } else {
            helper.setVisible(R.id.iv_selected, false);
        }


    }


    /**
     * 设置字符串start到end之间字符的样式
     *
     * @param content
     * @param start
     * @param end
     * @return
     */
    private SpannableString setTxtStyle(String content, int start, int end) {
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(mContext, R.style.BuyMoneyTxtStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return rate;
    }
}
