package cn.zeppin.product.ntb.ui.user.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geng.library.commonutils.ContextUtils;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.MyBank;

/**
 * 描述：我的银行卡
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class MyBankAdapter extends BaseQuickAdapter<MyBank, BaseViewHolder> {
    public MyBankAdapter(List<MyBank> data) {
        super(R.layout.item_bank_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBank item) {
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + item.getIcon()).into((ImageView) helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_name, item.getShortName());
        helper.setText(R.id.tv_bankcard, "**** **** **** " + item.getBankcard());

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(Color.parseColor(item.getColor()));
        gd.setCornerRadius(ContextUtils.dip2px(mContext, 5));
        helper.getView(R.id.ll_card).setBackgroundDrawable(gd);
    }
}
