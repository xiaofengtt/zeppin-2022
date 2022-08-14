package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.util.MyMapper;
import cn.product.treasuremall.vo.front.StatusCountVO;

public interface FrontUserVoucherMapper extends MyMapper<FrontUserVoucher> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserVoucher> getListByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontUserVoucher> getLeftListByParams(Map<String, Object> params);
	
	public List<StatusCountVO> getStatusList(Map<String, Object> params);
}