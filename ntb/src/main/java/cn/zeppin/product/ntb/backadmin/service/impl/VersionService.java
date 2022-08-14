/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IVersionDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IVersionService;
import cn.zeppin.product.ntb.core.entity.Version;
import cn.zeppin.product.ntb.core.entity.Version.VersionStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author elegantclack
 *
 */
@Service
public class VersionService extends BaseService implements IVersionService {

	@Autowired
	private IVersionDAO versionDAO;
	
	@Override
	public Version insert(Version version) {
		return versionDAO.insert(version);
	}
	@Override
	public Version delete(Version version) {
		//删除银行
		version.setStatus(VersionStatus.DELETED);
//		version.setVersion(version.getVersion() + "_#" + version.getUuid());
		return versionDAO.update(version);
	}

	@Override
	public Version update(Version version) {
		return versionDAO.update(version);
	}

	@Override
	public Version get(String uuid) {
		return versionDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询Version结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return versionDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return versionDAO.getCount(inputParams);
	}

	/**
	 * 验证同名的银行信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistVersionByName(Integer name, String device, String uuid) {
		return versionDAO.isExistVersionByName(name, device, uuid);
	}
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return versionDAO.getStatusList(inputParams, resultClass);
	}
}
