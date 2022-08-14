package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.CapitalAccountHistory;
import cn.product.worldmall.vo.back.StatusCountVO;

public interface CapitalAccountHistoryDao extends IDao<CapitalAccountHistory>{
	
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
	public List<CapitalAccountHistory> getListByParams(Map<String, Object> params);
	
	/**
	 * 获取分类型列表
	 * @param params
	 * @return
	 */
	public List<StatusCountVO> getTypeList(Map<String, Object> params);
}
