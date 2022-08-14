/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductService extends BaseService implements IBankFinancialProductService {

	@Autowired
	private IBankFinancialProductDAO bankFinancialProductDAO;
	
	@Override
	public BankFinancialProduct insert(BankFinancialProduct bankFinancialProduct) {
		return bankFinancialProductDAO.insert(bankFinancialProduct);
	}

	@Override
	public BankFinancialProduct delete(BankFinancialProduct bankFinancialProduct) {
		bankFinancialProduct.setStatus(BankFinancialProductStatus.DELETED);
		return bankFinancialProductDAO.update(bankFinancialProduct);
	}

	@Override
	public BankFinancialProduct update(BankFinancialProduct bankFinancialProduct) {
		return bankFinancialProductDAO.update(bankFinancialProduct);
	}

	@Override
	public BankFinancialProduct get(String uuid) {
		return bankFinancialProductDAO.get(uuid);
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
		return bankFinancialProductDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductDAO.getCount(inputParams);
	}
	
	/**
	 * 查询可发布理财产品列表
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@Override
	public List<Entity> getPublishListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return bankFinancialProductDAO.getPublishListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 查询可发布理财产品个数
	 * @return
	 */
	@Override
	public Integer getPublishCount(Map<String, String> inputParams) {
		return bankFinancialProductDAO.getPublishCount(inputParams);
	}
	
	/**
	 * 获取银行理财产品分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		return bankFinancialProductDAO.getStatusList(resultClass);
	}
	
	/**
	 * 获取银行理财产品分阶段列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStageList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductDAO.getStageList(inputParams, resultClass);
	}
	
	/**
	 * 根据各种时间修改阶段
	 */
	public void updateStage(){
		bankFinancialProductDAO.updateStageCollect();
		bankFinancialProductDAO.updateStageIncome();
		bankFinancialProductDAO.updateStageFinished();
	}
	
	/**
	 * 验证同名的银行理财产品信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductByName(String name, String uuid) {
		return bankFinancialProductDAO.isExistBankFinancialProductByName(name, uuid);
	}
	
	/**
	 * 验证同编号的银行理财产品信息是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductByScode(String scode, String uuid) {
		return bankFinancialProductDAO.isExistBankFinancialProductByScode(scode, uuid);
	}
}
