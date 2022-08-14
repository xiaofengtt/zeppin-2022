/**
 * 
 */
package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserEdit;

/**
 *
 */
public interface FrontUserEditDao extends IDao<FrontUserEdit> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<FrontUserEdit> getListByParams(Map<String, Object> params);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<FrontUserEdit> getLeftListByParams(Map<String, Object> params);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	/**
	 * 根据编辑类型获取数据
	 * @param frontUser
	 * @param type
	 * @return
	 */
	public FrontUserEdit getByEditType(String frontUser, String type);
	
	/**
	 * 审核
	 * @param frontUserEdit
	 */
	public void check(FrontUserEdit frontUserEdit, FrontUser fu);

}
