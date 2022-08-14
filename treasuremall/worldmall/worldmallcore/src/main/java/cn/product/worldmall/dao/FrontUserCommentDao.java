/**
 * 
 */
package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserComment;

/**
 *
 */
public interface FrontUserCommentDao extends IDao<FrontUserComment> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<FrontUserComment> getListByParams(Map<String, Object> params);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 是否已晒单
	 * @param frontUserPaymentOrder
	 * @return
	 */
	public Boolean isComment(String frontUserPaymentOrder);

}
