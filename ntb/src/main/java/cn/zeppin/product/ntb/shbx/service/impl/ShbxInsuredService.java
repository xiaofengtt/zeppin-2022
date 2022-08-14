/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.impl;

import java.util.List;
import java.util.Map;

import org.jgroups.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.ShbxInsured;
import cn.zeppin.product.ntb.core.entity.ShbxInsured.ShbxInsuredStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUserInsured.ShbxUserInsuredStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUserInsured;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxInsuredDAO;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserInsuredDAO;
import cn.zeppin.product.ntb.shbx.service.api.IShbxInsuredService;

/**
 * @author hehe
 *
 */
@Service
public class ShbxInsuredService extends BaseService implements IShbxInsuredService {

	@Autowired
	private IShbxInsuredDAO shbxInsuredDAO;
	
	@Autowired
	private IShbxUserInsuredDAO shbxUserInsuredDAO;
	
	@Override
	public ShbxInsured insert(ShbxInsured shbxInsured) {
		return shbxInsuredDAO.insert(shbxInsured);
	}

	@Override
	public ShbxInsured delete(ShbxInsured shbxInsured) {
		shbxInsured.setStatus(ShbxInsuredStatus.DELETED);
		return shbxInsuredDAO.update(shbxInsured);
	}

	@Override
	public ShbxInsured update(ShbxInsured shbxInsured) {
		return shbxInsuredDAO.update(shbxInsured);
	}

	@Override
	public ShbxInsured get(String uuid) {
		return shbxInsuredDAO.get(uuid);
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
		return shbxInsuredDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return shbxInsuredDAO.getCount(inputParams);
	}

	@Override
	public boolean isExistShbxInsuredByIdcard(String idcard, String uuid) {
		return shbxInsuredDAO.isExistShbxInsuredByIdcard(idcard, uuid);
	}

	@Override
	public List<Entity> getListForShbxPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return this.shbxInsuredDAO.getListForShbxPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForShbx(Map<String, String> inputParams) {
		return this.shbxInsuredDAO.getCountForShbx(inputParams);
	}

	@Override
	public void insertShbxInsured(ShbxInsured si) {
		ShbxUserInsured sui = new ShbxUserInsured();
		sui.setUuid(UUID.randomUUID().toString());
		sui.setShbxInsured(si.getUuid());
		sui.setShbxUser(si.getCreator());
		sui.setStatus(ShbxUserInsuredStatus.NORMAL);
		sui.setCreator(si.getCreator());
		sui.setCreatetime(si.getCreatetime());
		
		this.shbxInsuredDAO.insert(si);
		this.shbxUserInsuredDAO.insert(sui);
	}
}
