package cn.zeppin.product.ntb.ui.finance.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geng.library.commonutils.CollectionUtils;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.bean.FinanceHistory;

/**
 * 描述：购买记录
 * 开发人: geng
 * 创建时间: 17/10/12
 */

public class BuyHistoryAdapter extends BaseQuickAdapter<FinanceHistory, BaseViewHolder> {
    public BuyHistoryAdapter(List<FinanceHistory> data) {
        super(R.layout.item_buy_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinanceHistory item) {
        helper.setText(R.id.tv_price, CollectionUtils.numFormat4UnitString(item.getPrice()) + "元");

        if (item.getCouponName() != null) {
            helper.setText(R.id.tv_name, "(使用" + item.getCouponName() + ")");
        }
        helper.setText(R.id.tv_createTime, item.getCreatetimeCN());
    }
}
