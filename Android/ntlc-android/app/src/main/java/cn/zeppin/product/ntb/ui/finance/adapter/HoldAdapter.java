package cn.zeppin.product.ntb.ui.finance.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.Finance;
import cn.zeppin.product.ntb.widget.TextViewBorder;

/**
 * 描述：持有中，交易中
 * 开发人: geng
 * 创建时间: 17/10/12
 */

public class HoldAdapter extends BaseQuickAdapter<Finance, BaseViewHolder> {
    public HoldAdapter(List<Finance> data) {
        super(R.layout.fragment_hold_item1, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Finance item) {
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + item.getIconColorUrl()).into((ImageView) helper.getView(R.id.iv_bankIcon));
        helper.setText(R.id.tv_bankName, "【" + item.getBankName() + "】" + item.getProductName());
        helper.setText(R.id.tv_price, item.getPriceCN());//购买金额

        TextViewBorder tvDate = helper.getView(R.id.tv_date);

        if (ApiConstants.Finance.PROFIT.equals(item.getStage())) {//持有中
            helper.setText(R.id.tv_date, item.getMaturityDate());//到期日
            helper.setText(R.id.tv_date_des, "到期日");
            tvDate.setBorderColor(mContext.getResources().getColor(R.color.color_D09637));
            helper.setTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.color_D09637));
        } else if (ApiConstants.Finance.CONFIRMING.equals(item.getStage())) {//确认中
            helper.setText(R.id.tv_date, item.getValueDate());//起息日
            helper.setText(R.id.tv_date_des, "起息日");
            tvDate.setBorderColor(mContext.getResources().getColor(R.color.color_71AF51));
            helper.setTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.color_71AF51));
        } else if (ApiConstants.Finance.BALANCE.equals(item.getStage())) {//结算还款中（预计到账日）
            helper.setText(R.id.tv_date, item.getIncomeDate());//预计到账日
            helper.setText(R.id.tv_date_des, "预计到账日");
            tvDate.setBorderColor(mContext.getResources().getColor(R.color.color_6EA3E1));
            helper.setTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.color_6EA3E1));
        }
        if (helper.getLayoutPosition() == 0) {
            helper.setBackgroundRes(R.id.ll_item, R.drawable.finance_item_first);
        } else {
            helper.setBackgroundRes(R.id.ll_item, R.drawable.bg_empty_layout);
//            helper.setBackgroundColor(R.id.ll_item, mContext.getResources().getColor(R.color.transparent));
        }

        helper.setVisible(R.id.iv_cash, item.isFlagCashCoupon());
        helper.setVisible(R.id.iv_interests, item.isFlagInterestsCoupon());
    }
}
