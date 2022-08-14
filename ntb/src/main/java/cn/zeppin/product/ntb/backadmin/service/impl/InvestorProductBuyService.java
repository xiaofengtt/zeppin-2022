/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyService;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeProductBuyDAO;

/**
 *
 */
@Service
public class InvestorProductBuyService extends BaseService implements IInvestorProductBuyService {

	@Autowired
	private IInvestorProductBuyDAO investorProductBuyDAO;
	
	@Autowired
	private IQcbEmployeeProductBuyDAO qcbEmployeeProductBuyDAO;
	
	@Override
	public InvestorProductBuy insert(InvestorProductBuy investorProductBuy) {
		return investorProductBuyDAO.insert(investorProductBuy);
	}

	@Override
	public InvestorProductBuy delete(InvestorProductBuy investorProductBuy) {
		return investorProductBuyDAO.delete(investorProductBuy);
	}

	@Override
	public InvestorProductBuy update(InvestorProductBuy investorProductBuy) {
		return investorProductBuyDAO.update(investorProductBuy);
	}

	@Override
	public InvestorProductBuy get(String uuid) {
		return investorProductBuyDAO.get(uuid);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return investorProductBuyDAO.getCount(inputParams);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return investorProductBuyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public void updateBatch(List<InvestorProductBuy> listUpdate,
			List<InvestorProductBuy> listInsert) {
		if(!listUpdate.isEmpty()){
			for(InvestorProductBuy ipb : listUpdate){
				this.investorProductBuyDAO.update(ipb);
			}
		}
		
		if(!listInsert.isEmpty()){
			for(InvestorProductBuy ipb : listInsert){
				this.investorProductBuyDAO.insert(ipb);
			}
		}
	}

	@Override
	public void updateStage() {
		this.investorProductBuyDAO.updateConfirmStage();
		this.investorProductBuyDAO.updatePofitStage();
		this.investorProductBuyDAO.updateBalanceStage();
	}

	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return this.investorProductBuyDAO.getListForCountPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
	
	@Override
	public Integer getAllUserCount(Map<String, String> inputParams) {
		Integer iCount = investorProductBuyDAO.getCount(inputParams);
		Integer qeCount = qcbEmployeeProductBuyDAO.getCount(inputParams);
		return iCount + qeCount;
	}

	@Override
	public List<Entity> getAllUserListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return investorProductBuyDAO.getAllUserListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
}
