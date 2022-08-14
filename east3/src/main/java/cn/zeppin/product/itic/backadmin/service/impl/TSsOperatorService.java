/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorService;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.service.base.BaseService;


@Service
public class TSsOperatorService extends BaseService implements ITSsOperatorService {

	@Autowired
	private ITSsOperatorDAO tSsOperatorDAO;
	
	
	@Override
	public TSsOperator insert(TSsOperator role) {
		return tSsOperatorDAO.insert(role);
	}

	@Override
	public TSsOperator delete(TSsOperator role) {
		return tSsOperatorDAO.delete(role);
	}

	@Override
	public TSsOperator update(TSsOperator role) {
		return tSsOperatorDAO.update(role);
	}

	@Override
	public TSsOperator get(String uuid) {
		return tSsOperatorDAO.get(uuid);
	}
	
	/**
	 * 获取用户列表
	 * @return
	 */
	@Override
	public List<TSsOperator> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts){
		return tSsOperatorDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}
	
	/**
	 * 获取用户数
	 * @return
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return tSsOperatorDAO.getCount(inputParams);
	}
	
	/**
	 * 通过账号得到TSsOperator对象
	 * @param loginname
	 * @return TSsOperator
	 */
	@Override
	public TSsOperator getByLoginname(String loginname) {
		return tSsOperatorDAO.getByLoginname(loginname);
	}

}
