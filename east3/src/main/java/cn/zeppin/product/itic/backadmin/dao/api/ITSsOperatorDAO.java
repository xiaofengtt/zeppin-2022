/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TSsOperator;

public interface ITSsOperatorDAO extends IBaseDAO<TSsOperator, String> {
	
	/**
	 * 通过账号得到BkOperator对象
	 * @param loginname
	 * @return TSsOperator
	 */
	public TSsOperator getByLoginname(String loginname);
	
	/**
	 * 获取用户列表
	 * @return
	 */
	public List<TSsOperator> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);
	
	/**
	 * 获取用户数
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
}
