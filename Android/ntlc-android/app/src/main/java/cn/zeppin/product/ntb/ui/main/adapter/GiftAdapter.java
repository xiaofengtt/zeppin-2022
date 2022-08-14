package cn.zeppin.product.ntb.ui.main.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geng.library.commonutils.CollectionUtils;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.utils.TextStyleUtil;

import static cn.zeppin.product.ntb.app.ApiConstants.CouponType.INTERESTS;

/**
 * 描述：礼包
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class GiftAdapter extends BaseQuickAdapter<Coupon, BaseViewHolder> {
    private boolean isShareSuccess = false;

    public GiftAdapter(List<Coupon> data) {
        super(R.layout.item_gift_coupon, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Coupon item) {
        String value = "优惠券+";
        if (INTERESTS.equals(item.getCouponType())) {//加息券
            value = item.getCouponPriceCN() + "%";

        } else {//现金券
            value = item.getCouponPriceCN() + "元";
        }
        if ("redPacket".equals(item.getCouponType())) {//现金红包
            helper.setText(R.id.tv_limitPrice, "可投资，可提现");
            if (isShareSuccess) {
                helper.setVisible(R.id.tv_add_cash, true);
                value = CollectionUtils.doubleToString(item.getCouponPrice() + 2) + "元";
            } else {
                helper.setVisible(R.id.tv_add_cash, false);
                value = item.getCouponPriceCN() + "元";
            }
        } else {//优惠券
            String limitPrice = item.getMinInvestAccount() > 0 ? "单笔投资" + CollectionUtils.numFormat4UnitString(item.getMinInvestAccount()) + "元起可用" : "单笔投资无限额";
            helper.setText(R.id.tv_limitPrice, limitPrice);
        }
        if (TextUtils.isEmpty(item.getExpiryDateCN())) {
            helper.setText(R.id.tv_expiryDate, "无期限");//有效期至
        } else {
            helper.setText(R.id.tv_expiryDate, item.getExpiryDateCN() + "截止");//有效期至
        }
        helper.setText(R.id.tv_price, TextStyleUtil.setTxtLast8Style(mContext, value, value.length() - 1, value.length()));//
        helper.setText(R.id.tv_type, item.getCouponTypeCN());//
    }

    public void setShareSuccess(boolean shareSuccess) {
        isShareSuccess = shareSuccess;
    }
}
