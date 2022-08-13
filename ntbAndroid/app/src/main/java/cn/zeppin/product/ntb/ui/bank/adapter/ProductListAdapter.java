package cn.zeppin.product.ntb.ui.bank.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geng.library.commonutils.CollectionUtils;

import java.util.List;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.Product;

import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.UNINVEST;
import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.UNSTART;

/**
 * Created by geng on 17/9/6.
 * class: 银行理财列表adapter
 */

public class ProductListAdapter extends BaseQuickAdapter<Product, BaseViewHolder> {
    public ProductListAdapter(List<Product> data) {
        super(R.layout.item_product_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + item.getIconColorUrl()).into((ImageView) helper.getView(R.id.iv_bankIcon));
        helper.setText(R.id.tv_productName, "【" + item.getCustodianName() + "】" + item.getShortname());
        String rate = item.getTargetAnnualizedReturnRate() + "%";
        String term = item.getTerm() + "天";
        helper.setText(R.id.product_rate, setTxtLastStyle(rate, rate.length() - 1, rate.length()));//预期年化收益率
        String minInvestAmountCN = item.getMinInvestAmountCN();
        if (CollectionUtils.isContainChinese(minInvestAmountCN)) {//包含中文
            helper.setText(R.id.tv_minInvestAmount, setTxtLastStyle(minInvestAmountCN, minInvestAmountCN.length() - 1, minInvestAmountCN.length()));//起购金额
        } else {
            helper.setText(R.id.tv_minInvestAmount, item.getMinInvestAmountCN());//起购金额
        }

        helper.setText(R.id.tv_term, setTxtLastStyle(term, term.length() - 1, term.length()));//产品期限
        helper.setText(R.id.tv_collectEndTime, item.getCollectEndtimeCN());//截止时间
        if (!UNSTART.equals(item.getStage()) && !item.isFlagBuy()) {//已过期 （不是未开始状态，并且flagbuy是false,意为过期）
            helper.setTextColor(R.id.tv_productName, getColor(R.color.color_909090));
            helper.setTextColor(R.id.product_rate, getColor(R.color.color_909090));
            helper.setTextColor(R.id.tv_minInvestAmount, getColor(R.color.color_909090));
            helper.setTextColor(R.id.tv_term, getColor(R.color.color_909090));
            helper.setText(R.id.tv_status, "过期");
            helper.setVisible(R.id.tv_status, true);
        } else {//未过期
            helper.setTextColor(R.id.tv_productName, getColor(R.color.alpha_80_black));
            helper.setTextColor(R.id.product_rate, getColor(R.color.color_F4333C));
            helper.setTextColor(R.id.tv_minInvestAmount, getColor(R.color.color_90_2B2B2B));
            helper.setTextColor(R.id.tv_term, getColor(R.color.color_90_080808));
            //募集已满
            if (UNINVEST.equals(item.getStage())) {//flag=true
                helper.setText(R.id.tv_status, "已满");
                helper.setVisible(R.id.tv_status, true);
            } else {
                helper.setVisible(R.id.tv_status, false);
            }
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
    private SpannableString setTxtLastStyle(String content, int start, int end) {
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(mContext, R.style.ProductRateTxtStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return rate;
    }

    private int getColor(int color) {
        return mContext.getResources().getColor(color);
    }

}
