/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.ShbxUserInsured;
import cn.zeppin.product.ntb.core.entity.ShbxUserInsured.ShbxUserInsuredStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserInsuredDAO;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserInsuredService;

/**
 * @author hehe
 *
 */
@Service
public class ShbxUserInsuredService extends BaseService implements IShbxUserInsuredService {

	@Autowired
	private IShbxUserInsuredDAO shbxUserInsuredDAO;
	
	@Override
	public ShbxUserInsured insert(ShbxUserInsured shbxUserInsured) {
		return shbxUserInsuredDAO.insert(shbxUserInsured);
	}

	@Override
	public ShbxUserInsured delete(ShbxUserInsured shbxUserInsured) {
		shbxUserInsured.setShbxInsured(shbxUserInsured.getShbxInsured()+"_#"+shbxUserInsured.getUuid());
		shbxUserInsured.setShbxUser(shbxUserInsured.getShbxUser()+"_#"+shbxUserInsured.getUuid());
		shbxUserInsured.setStatus(ShbxUserInsuredStatus.DELETED);
		return shbxUserInsuredDAO.update(shbxUserInsured);
	}

	@Override
	public ShbxUserInsured update(ShbxUserInsured shbxUserInsured) {
		return shbxUserInsuredDAO.update(shbxUserInsured);
	}

	@Override
	public ShbxUserInsured get(String uuid) {
		return shbxUserInsuredDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return shbxUserInsuredDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return shbxUserInsuredDAO.getCount(inputParams);
	}
}
