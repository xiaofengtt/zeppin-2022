package cn.zeppin.product.ntb.ui.user.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geng.library.commonutils.CollectionUtils;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.Bank;

/**
 * 描述：选择银行
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class BankAdapter extends BaseQuickAdapter<Bank, BaseViewHolder> {
    private Bank currentBank;

    public BankAdapter(List<Bank> data, Bank currentBank) {
        super(R.layout.item_bank, data);
        this.currentBank = currentBank;
    }

    @Override
    protected void convert(BaseViewHolder helper, Bank item) {
        if (currentBank != null) {
            if (currentBank.getUuid().equals(item.getUuid())) {
                helper.setVisible(R.id.iv_selected, true);
            } else {
                helper.getView(R.id.iv_selected).setVisibility(View.INVISIBLE);
            }
        }

        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + item.getIconColorUrl()).into((ImageView) helper.getView(R.id.iv_bankIcon));
        helper.setText(R.id.tv_bankName, item.getShortName());
        helper.setText(R.id.tv_bankContent, "单笔限额" + CollectionUtils.numFormat4UnitString(item.getSingleLimit()) + "，每日限额" + CollectionUtils.numFormat4UnitString(item.getDailyLimit()));
    }
}
