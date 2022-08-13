package cn.zeppin.product.ntb.ui.user.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geng.library.commonutils.CollectionUtils;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.utils.TextStyleUtil;

import static cn.zeppin.product.ntb.app.ApiConstants.CouponType.INTERESTS;

/**
 * 描述：优惠券
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class CouponAdapter extends BaseQuickAdapter<Coupon, BaseViewHolder> {
    private boolean isAvailable = true;
    private Coupon currentCoupon;

    public CouponAdapter(List<Coupon> data, boolean isAvailable, Coupon currentCoupon) {
        super(R.layout.fragment_coupon_list_item, data);
        this.isAvailable = isAvailable;
        this.currentCoupon = currentCoupon;
    }

    @Override
    protected void convert(BaseViewHolder helper, Coupon item) {
        if (currentCoupon != null) {
            String uuid = currentCoupon.getUuid();
            if (item.getUuid().equals(uuid)) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
        }
        String value = "优惠券+";
        if (INTERESTS.equals(item.getCouponType())) {//加息券
            value = item.getCouponPriceCN() + "%";

        } else {//现金券
            value = item.getCouponPriceCN() + "元";
        }
        helper.setText(R.id.tv_value, TextStyleUtil.setTxtLast14Style(mContext, value, value.length() - 1, value.length()));//
        helper.setText(R.id.tv_type, item.getCouponTypeCN());//
        String limitPrice = item.getMinInvestAccount() > 0 ? "单笔投资" + CollectionUtils.numFormat4UnitString(item.getMinInvestAccount()) +"元起可用": "单笔投资无限额";
        helper.setText(R.id.tv_content, limitPrice);
        helper.setText(R.id.tv_expiryDate, "有效期至：" + item.getExpiryDateCN());//有效期至

        if (isAvailable) {//可用
            helper.setTextColor(R.id.tv_value, getColor(R.color.color_F6A623));
            helper.setTextColor(R.id.tv_type, getColor(R.color.color_F6A623));
        } else {//历史
            helper.setTextColor(R.id.tv_value, getColor(R.color.alpha_30_black));
            helper.setTextColor(R.id.tv_type, getColor(R.color.alpha_30_black));
            if (ApiConstants.CouponStatus.USED.equals(item.getStatus())) {//已使用
                helper.setBackgroundRes(R.id.iv_status, R.drawable.coupon_used);
            }
            if (ApiConstants.CouponStatus.EXPIRED.equals(item.getStatus())) {//已过期
                helper.setBackgroundRes(R.id.iv_status, R.drawable.coupon_expired);
            }
            helper.setVisible(R.id.iv_status, true);
        }

        if (item.isChecked()) {
            helper.setBackgroundRes(R.id.rl_top, R.drawable.coupon_top_selected_bg);
            helper.setBackgroundRes(R.id.rl_bottom, R.drawable.coupon_bottom_selected_bg);
            helper.setVisible(R.id.tv_selected, true);
        } else {
            helper.setBackgroundRes(R.id.rl_top, R.drawable.coupon_top_normal_bg);
            helper.setBackgroundRes(R.id.rl_bottom, R.drawable.coupon_bottom_normal_bg);
            helper.setVisible(R.id.tv_selected, false);
        }
    }

    private int getColor(int color) {
        return mContext.getResources().getColor(color);
    }
}
