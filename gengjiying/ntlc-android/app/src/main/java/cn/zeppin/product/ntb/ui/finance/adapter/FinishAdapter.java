package cn.zeppin.product.ntb.ui.finance.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.Finance;

/**
 * 描述：持有中，交易中
 * 开发人: geng
 * 创建时间: 17/10/12
 */

public class FinishAdapter extends BaseQuickAdapter<Finance, BaseViewHolder> {
    public FinishAdapter(List<Finance> data) {
        super(R.layout.fragment_hold_item2, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Finance item) {
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + item.getIconColorUrl()).into((ImageView) helper.getView(R.id.iv_bankIcon));
        helper.setText(R.id.tv_bankName, "【" + item.getBankName() + "】" + item.getProductName());
        helper.setText(R.id.tv_price, item.getPriceCN());//购买金额
        helper.setText(R.id.tv_totalReturn, item.getTotalReturn() + "元");//实际收益
        helper.setText(R.id.tv_returnRate, item.getRealReturnRateCN() + "%");//实际收益率

        if (helper.getLayoutPosition() == 0) {
            helper.setBackgroundRes(R.id.ll_item, R.drawable.finance_item_first);
        } else {
            helper.setBackgroundRes(R.id.ll_item, R.drawable.bg_empty_layout);
        }

        helper.setVisible(R.id.iv_cash, item.isFlagCashCoupon());
        helper.setVisible(R.id.iv_interests, item.isFlagInterestsCoupon());
    }
}
