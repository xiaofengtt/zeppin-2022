/**
 * 
 */
package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserComment;

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
	 * 20201023增加审核通过发送1金币到用户账户
	 * @param frontusercomment
	 */
	public void updateAndGift(FrontUserComment frontUserComment);

}
