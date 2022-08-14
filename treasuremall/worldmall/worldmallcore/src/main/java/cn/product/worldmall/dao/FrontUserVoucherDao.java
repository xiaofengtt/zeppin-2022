package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.vo.front.StatusCountVO;

public interface FrontUserVoucherDao extends IDao<FrontUserVoucher>{
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUserVoucher> getListByParams(Map<String, Object> params);
	
	/**
	 * 状态统计
	 * @param params
	 * @return
	 */
	public List<StatusCountVO> getStatusList(Map<String, Object> params);
	
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontUserVoucher> getLeftListByParams(Map<String, Object> params);
	
	/**
	 * 添加
	 * @param listFuv
	 */
	public void insert(List<FrontUserVoucher> listFuv);
	
}
