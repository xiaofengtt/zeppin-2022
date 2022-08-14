/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorInformationDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorInformationService;
import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class InvestorInformationService extends BaseService implements IInvestorInformationService {

	@Autowired
	private IInvestorInformationDAO investorInformationDAO;

	@Override
	public InvestorInformation insert(InvestorInformation investorInformation) {
		return investorInformationDAO.insert(investorInformation);
	}

	@Override
	public InvestorInformation delete(InvestorInformation investorInformation) {
		return investorInformationDAO.delete(investorInformation);
	}

	@Override
	public InvestorInformation update(InvestorInformation investorInformation) {
		return investorInformationDAO.update(investorInformation);
	}

	@Override
	public InvestorInformation get(String uuid) {
		return investorInformationDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return investorInformationDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return investorInformationDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return investorInformationDAO.getCount(inputParams);
	}

	@Override
	public void updateBatchRead(String investor) {
		// TODO Auto-generated method stub
		this.investorInformationDAO.updateBatchRead(investor);
	}

	@Override
	public InvestorInformation getInfo(String uuid) {
		// TODO Auto-generated method stub
		InvestorInformation ii = this.investorInformationDAO.get(uuid);
		if( ii != null){
			ii.setStatus(InvestorInformationStatus.NORMAL);
			ii = this.investorInformationDAO.update(ii);
			return ii;
		} else {
			return null;
		}
	}
}
