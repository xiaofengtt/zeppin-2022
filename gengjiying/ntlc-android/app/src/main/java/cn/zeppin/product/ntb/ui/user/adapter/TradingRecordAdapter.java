package cn.zeppin.product.ntb.ui.user.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.TradingRecord;

import static cn.zeppin.product.ntb.app.ApiConstants.TradingStatus.CANCELLED;
import static cn.zeppin.product.ntb.app.ApiConstants.TradingStatus.SUCCESS;
import static cn.zeppin.product.ntb.app.ApiConstants.TradingStatus.TRANSACTING;

/**
 * 描述：交易记录
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class TradingRecordAdapter extends BaseQuickAdapter<TradingRecord, BaseViewHolder> {
    public TradingRecordAdapter(List<TradingRecord> data) {
        super(R.layout.item_trading_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TradingRecord item) {
        if (item.isPriceflag()) {//收入
            helper.setText(R.id.tv_tradingType, "收");
            helper.setText(R.id.tv_tradingValue, "+" + item.getPrice());//交易金额
            helper.setBackgroundRes(R.id.tv_tradingType, R.drawable.record_income);
            helper.setTextColor(R.id.tv_tradingValue, mContext.getResources().getColor(R.color.color_E05B59));
        } else {
            helper.setText(R.id.tv_tradingType, "支");
            helper.setBackgroundRes(R.id.tv_tradingType, R.drawable.record_pay);
            helper.setText(R.id.tv_tradingValue, "-" + item.getPrice());//交易金额
            helper.setTextColor(R.id.tv_tradingValue, mContext.getResources().getColor(R.color.trading_record_pay));
        }
        if (TRANSACTING.equals(item.getStatus()) || SUCCESS.equals(item.getStatus())) {//交易中和交易成功
            helper.setTextColor(R.id.tv_tradingResult, mContext.getResources().getColor(R.color.color_797979));
        } else if (ApiConstants.TradingStatus.CLOSED.equals(item.getStatus()) || CANCELLED.equals(item.getStatus())) {//交易关闭和交易取消
            helper.setTextColor(R.id.tv_tradingResult, mContext.getResources().getColor(R.color.alpha_30_black));
        } else {//交易失败
            helper.setTextColor(R.id.tv_tradingResult, mContext.getResources().getColor(R.color.color_E05B59));
        }

        helper.setText(R.id.tv_tradingName, item.getTypeCN());//交易类型
        helper.setText(R.id.tv_tradingTime, item.getCreatetimeLessCN());//交易时间
        helper.setText(R.id.tv_tradingResult, item.getStatusCN());//交易结果
    }
}
