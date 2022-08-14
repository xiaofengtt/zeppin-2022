package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Orderinfo;

public interface OrderinfoService extends IService<Orderinfo>{
	
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
	public List<Orderinfo> getListByParams(Map<String, Object> params);
}
