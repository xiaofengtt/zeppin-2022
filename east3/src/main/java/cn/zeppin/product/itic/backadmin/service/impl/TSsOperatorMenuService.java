package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorMenuDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorMenuService;
import cn.zeppin.product.itic.core.entity.TSsOperatorMenu;
import cn.zeppin.product.itic.core.service.base.BaseService;

/**
 *
 */

@Service
public class TSsOperatorMenuService extends BaseService implements ITSsOperatorMenuService {	
	
	@Autowired
	private ITSsOperatorMenuDAO tSsOperatoreMenuDAO;
	
	/**
	 * 获取角色权限列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<TSsOperatorMenu> getList(Map<String, String> inputParams) {
		return tSsOperatoreMenuDAO.getList(inputParams);
	}
}
