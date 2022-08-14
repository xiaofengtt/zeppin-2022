/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.LinkedList;
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
	public List<Entity> getWebListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, LinkedList<String> sortList, Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishDAO.getWebListForPage(inputParams, pageNum, pageSize, sortList, resultClass);
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
	 * 获取募集产品分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishDAO.getStatusList(resultClass);
	}
	
	/**
	 * 获取募集产品分阶段列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStageList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishDAO.getStageList(inputParams, resultClass);
	}
	
	/**
	 * 根据各种时间修改阶段
	 */
	public void updateStage(){
		bankFinancialProductPublishDAO.updateStageCollect();
		bankFinancialProductPublishDAO.updateStageUninvestByCollect();
		bankFinancialProductPublishDAO.updateStageUninvest();
		bankFinancialProductPublishDAO.updateStageInvested();
		bankFinancialProductPublishDAO.updateStageProfit();
		bankFinancialProductPublishDAO.updateStageBalance();
		bankFinancialProductPublishDAO.updateStageBalance4Finish();
	}
	
	/**
	 * 验证同名的募集产品信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductPublishByName(String name, String uuid) {
		return bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByName(name, uuid);
	}
	
	/**
	 * 验证同编号的募集产品信息是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductPublishByScode(String scode, String uuid) {
		return bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByScode(scode, uuid);
	}

	@Override
	public void updateBatch(List<BankFinancialProductPublish> listUpdate) {
		// TODO Auto-generated method stub
		for(BankFinancialProductPublish bfpp : listUpdate) {
			this.bankFinancialProductPublishDAO.update(bfpp);
		}
	}

	@Override
	public List<Entity> getBankList(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.bankFinancialProductPublishDAO.getBankList(inputParams, pageNum, pageSize, sorts, resultClass);
	}
}
