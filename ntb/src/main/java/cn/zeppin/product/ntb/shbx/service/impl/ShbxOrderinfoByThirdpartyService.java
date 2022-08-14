/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.shbx.service.api.IShbxOrderinfoByThirdpartyService;

/**
 * @author hehe
 *
 */
@Service
public class ShbxOrderinfoByThirdpartyService extends BaseService implements IShbxOrderinfoByThirdpartyService {

	@Autowired
	private IShbxOrderinfoByThirdpartyDAO shbxOrderinfoByThirdpartyDAO;
	
	@Override
	public ShbxOrderinfoByThirdparty insert(ShbxOrderinfoByThirdparty shbxOrderinfoByThirdparty) {
		return shbxOrderinfoByThirdpartyDAO.insert(shbxOrderinfoByThirdparty);
	}

	@Override
	public ShbxOrderinfoByThirdparty delete(ShbxOrderinfoByThirdparty shbxOrderinfoByThirdparty) {
		return shbxOrderinfoByThirdpartyDAO.delete(shbxOrderinfoByThirdparty);
	}

	@Override
	public ShbxOrderinfoByThirdparty update(ShbxOrderinfoByThirdparty shbxOrderinfoByThirdparty) {
		return shbxOrderinfoByThirdpartyDAO.update(shbxOrderinfoByThirdparty);
	}

	@Override
	public ShbxOrderinfoByThirdparty get(String uuid) {
		return shbxOrderinfoByThirdpartyDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询ShbxOrderinfoByThirdparty结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return shbxOrderinfoByThirdpartyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return shbxOrderinfoByThirdpartyDAO.getCount(inputParams);
	}

	@Override
	public void insertBatch(List<ShbxOrderinfoByThirdparty> listOrder) {
		for(ShbxOrderinfoByThirdparty obt : listOrder){
			this.shbxOrderinfoByThirdpartyDAO.insert(obt);
		}
	}
}
