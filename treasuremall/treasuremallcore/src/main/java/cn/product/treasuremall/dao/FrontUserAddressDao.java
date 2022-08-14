package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserAddress;

public interface FrontUserAddressDao extends IDao<FrontUserAddress> {
	
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
    public List<FrontUserAddress> getListByParams(Map<String, Object> params);
    
    /**
	 * 根据参数获取总数
	 * @param frontUserAddress
	 * @return
	 */
	public void addFrontUserAddress(FrontUserAddress frontUserAddress);
}
