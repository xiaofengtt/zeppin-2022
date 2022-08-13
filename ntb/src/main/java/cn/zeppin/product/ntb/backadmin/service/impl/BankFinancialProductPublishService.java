/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductPublishService extends BaseService implements IBankFinancialProductPublishService {

	@Autowired
	private IBankFinancialProductPublishDAO bankFinancialProductPublishDAO;
	
	@Override
	public BankFinancialProductPublish insert(BankFinancialProductPublish bankFinancialProductPublish) {
		return bankFinancialProductPublishDAO.insert(bankFinancialProductPublish);
	}

	@Override
	public BankFinancialProductPublish delete(BankFinancialProductPublish bankFinancialProductPublish) {
		bankFinancialProductPublish.setStatus(BankFinancialProductPublishStatus.DELETED);
		return bankFinancialProductPublishDAO.update(bankFinancialProductPublish);
	}

	@Override
	public BankFinancialProductPublish update(BankFinancialProductPublish bankFinancialProductPublish) {
		return bankFinancialProductPublishDAO.update(bankFinancialProductPublish);
	}

	@Override
	public BankFinancialProductPublish get(String uuid) {
		return bankFinancialProductPublishDAO.get(uuid);
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
		return bankFinancialProductPublishDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductPublishDAO.getCount(inputParams);
	}
	
	/**
	 * 根据参数查询WEB结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getWebListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishDAO.getWebListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询WEB结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getWebCount(Map<String, String> inputParams) {
		return bankFinancialProductPublishDAO.getWebCount(inputParams);
	}
	
	/**
	 * 获取银行理财产品发布分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishDAO.getStatusList(resultClass);
	}
	
	/**
	 * 获取银行理财产品发布分阶段列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStageList(Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishDAO.getStageList(resultClass);
	}
	
	/**
	 * 根据各种时间修改阶段
	 */
	public void updateStage(){
		bankFinancialProductPublishDAO.updateStageCollect();
		bankFinancialProductPublishDAO.updateStageFinished();
	}
	
	/**
	 * 验证同名的银行理财产品发布信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductPublishByName(String name, String uuid) {
		return bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByName(name, uuid);
	}
	
	/**
	 * 验证同编号的银行理财产品发布信息是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductPublishByScode(String scode, String uuid) {
		return bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByScode(scode, uuid);
	}
}
