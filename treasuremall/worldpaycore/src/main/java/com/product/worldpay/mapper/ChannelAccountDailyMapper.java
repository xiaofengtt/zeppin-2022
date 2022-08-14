package com.product.worldpay.mapper;

import com.product.worldpay.entity.ChannelAccountDaily;
import com.product.worldpay.util.MyMapper;

public interface ChannelAccountDailyMapper extends MyMapper<ChannelAccountDaily> {
	
	public void clearAll();
}