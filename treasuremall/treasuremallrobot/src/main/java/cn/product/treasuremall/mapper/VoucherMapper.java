package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Voucher;
import cn.product.treasuremall.util.MyMapper;

public interface VoucherMapper extends MyMapper<Voucher> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Voucher> getListByParams(Map<String, Object> params);
}

