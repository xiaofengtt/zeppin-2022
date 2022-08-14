/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorBankcardDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorBankcardService;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard.InvestorBankcardStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class InvestorBankcardService extends BaseService implements IInvestorBankcardService {

	@Autowired
	private IInvestorBankcardDAO investorBankcardDAO;

	
	@Override
	public InvestorBankcard insert(InvestorBankcard investorBankcard) {
		return investorBankcardDAO.insert(investorBankcard);
	}

	@Override
	public InvestorBankcard delete(InvestorBankcard investorBankcard) {
		investorBankcard.setStatus(InvestorBankcardStatus.DELETED);
		return investorBankcardDAO.update(investorBankcard);
	}

	@Override
	public InvestorBankcard update(InvestorBankcard investorBankcard) {
		return investorBankcardDAO.update(investorBankcard);
	}

	@Override
	public InvestorBankcard get(String uuid) {
		return investorBankcardDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询investorBankcard结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return investorBankcardDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return investorBankcardDAO.getCount(inputParams);
	}

	@Override
	public List<Entity> getList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return investorBankcardDAO.getList(inputParams, resultClass);
	}

}
