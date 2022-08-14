package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.CapitalAccountHistory;
import cn.zeppin.product.score.vo.back.StatusCountVO;

public interface CapitalAccountHistoryService extends IService<CapitalAccountHistory>{
	
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
