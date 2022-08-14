package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.GoodsCoverImage;

public interface GoodsCoverImageDao extends IDao<GoodsCoverImage> {
	
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
    public List<GoodsCoverImage> getListByParams(Map<String, Object> params);
    
    /**
	 * 根据所属品删除所属图片
	 * @param belongs
	 * @return
	 */
    public void deleteByBelongs(String belongs);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
    public List<GoodsCoverImage> getListByParams(String belongs);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
    public List<GoodsCoverImage> getListByParams(String belongs, String type);
}
