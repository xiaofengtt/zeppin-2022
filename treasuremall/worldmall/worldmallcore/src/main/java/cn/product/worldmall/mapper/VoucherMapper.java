package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.util.MyMapper;

public interface VoucherMapper extends MyMapper<Voucher> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Voucher> getListByParams(Map<String, Object> params);
}

