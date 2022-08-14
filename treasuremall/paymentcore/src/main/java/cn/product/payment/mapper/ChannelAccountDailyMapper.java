package cn.product.payment.mapper;

import cn.product.payment.entity.ChannelAccountDaily;
import cn.product.payment.util.MyMapper;

public interface ChannelAccountDailyMapper extends MyMapper<ChannelAccountDaily> {
	
	public void clearAll();
}