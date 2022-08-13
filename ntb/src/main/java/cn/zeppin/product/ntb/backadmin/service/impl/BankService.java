/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBranchBankDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.Bank.BankStatus;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.BranchBank.BranchBankStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author elegantclack
 *
 */
@Service
public class BankService extends BaseService implements IBankService {

	@Autowired
	private IBankDAO bankDAO;
	
	@Autowired
	private IBranchBankDAO branchBankDAO;
	
	@Override
	public Bank insert(Bank bank) {
		return bankDAO.insert(bank);
	}

	@Override
	public Bank delete(Bank bank) {
		//搜索支行
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("bank", bank.getUuid());
		
		List<Entity> branchBankList = branchBankDAO.getList(searchMap, BranchBank.class);
		
		//删除支行
		for(Entity e: branchBankList){
			BranchBank branchBank = (BranchBank) e;
			branchBank.setStatus(BranchBankStatus.DELETED);
			branchBank.setName(branchBank.getName() + "_#" + branchBank.getUuid());
			branchBankDAO.update(branchBank);
		}
		
		//删除银行
		bank.setStatus(BankStatus.DELETED);
		bank.setName(bank.getName() + "_#" + bank.getUuid());
		return bankDAO.update(bank);
	}

	@Override
	public Bank update(Bank bank) {
		return bankDAO.update(bank);
	}

	@Override
	public Bank get(String uuid) {
		return bankDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询Bank结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return bankDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankDAO.getCount(inputParams);
	}

	/**
	 * 验证同名的银行信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankByName(String name, String uuid) {
		return bankDAO.isExistBankByName(name, uuid);
	}
}
