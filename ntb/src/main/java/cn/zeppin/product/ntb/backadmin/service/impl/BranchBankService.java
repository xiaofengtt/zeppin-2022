/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBranchBankDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.BranchBank.BranchBankStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class BranchBankService extends BaseService implements IBranchBankService {

	@Autowired
	private IBranchBankDAO branchBankDAO;

	
	@Override
	public BranchBank insert(BranchBank branchBank) {
		return branchBankDAO.insert(branchBank);
	}

	@Override
	public BranchBank delete(BranchBank branchBank) {
		branchBank.setStatus(BranchBankStatus.DELETED);
		branchBank.setName(branchBank.getName() + "_#" + branchBank.getUuid());
		return branchBankDAO.update(branchBank);
	}

	@Override
	public BranchBank update(BranchBank branchBank) {
		return branchBankDAO.update(branchBank);
	}

	@Override
	public BranchBank get(String uuid) {
		return branchBankDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询BranchBank结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return branchBankDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return branchBankDAO.getCount(inputParams);
	}

	/**
	 * 验证同名的银行信息是否已经存在
	 * @param name
	 * @return
	 */
	@Override
	public boolean isExistBranchBank(String bank, String name, String uuid) {
		return branchBankDAO.isExistBranchBank(bank, name, uuid);
	}
}
