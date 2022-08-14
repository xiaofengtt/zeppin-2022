/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyAgreementDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyAgreementService;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class InvestorProductBuyAgreementService extends BaseService implements IInvestorProductBuyAgreementService {

	@Autowired
	private IInvestorProductBuyAgreementDAO investorProductBuyAgreementDAO;
	
	@Override
	public InvestorProductBuyAgreement insert(InvestorProductBuyAgreement investorProductBuyAgreement) {
		return investorProductBuyAgreementDAO.insert(investorProductBuyAgreement);
	}

	@Override
	public InvestorProductBuyAgreement delete(InvestorProductBuyAgreement investorProductBuyAgreement) {
		return investorProductBuyAgreementDAO.delete(investorProductBuyAgreement);
	}

	@Override
	public InvestorProductBuyAgreement update(InvestorProductBuyAgreement investorProductBuyAgreement) {
		return investorProductBuyAgreementDAO.update(investorProductBuyAgreement);
	}

	@Override
	public InvestorProductBuyAgreement get(String uuid) {
		return investorProductBuyAgreementDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询investorProductBuyAgreement结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return investorProductBuyAgreementDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return investorProductBuyAgreementDAO.getCount(inputParams);
	}

	@Override
	public Boolean getCheckScode(String scode) {
		// TODO Auto-generated method stub
		return investorProductBuyAgreementDAO.getCheckScode(scode);
	}

	@Override
	public void updateBatch(List<InvestorProductBuyAgreement> listUpdate,
			List<InvestorProductBuyAgreement> listInsert) {
		// TODO Auto-generated method stub
		if(!listUpdate.isEmpty()){
			for(InvestorProductBuyAgreement ipba : listUpdate){
				this.investorProductBuyAgreementDAO.update(ipba);
			}
		}
		
		if(!listInsert.isEmpty()){
			for(InvestorProductBuyAgreement ipba : listInsert){
				this.investorProductBuyAgreementDAO.insert(ipba);
			}
		}
	}
}
