/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorRedPacketDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorRedPacketService;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class InvestorRedPacketService extends BaseService implements IInvestorRedPacketService {

	@Autowired
	private IInvestorRedPacketDAO investorRedPacketDAO;

	@Override
	public InvestorRedPacket insert(InvestorRedPacket investorRedPacket) {
		return investorRedPacketDAO.insert(investorRedPacket);
	}

	@Override
	public InvestorRedPacket delete(InvestorRedPacket investorRedPacket) {
		return investorRedPacketDAO.delete(investorRedPacket);
	}

	@Override
	public InvestorRedPacket update(InvestorRedPacket investorRedPacket) {
		return investorRedPacketDAO.update(investorRedPacket);
	}

	@Override
	public InvestorRedPacket get(String uuid) {
		return investorRedPacketDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return investorRedPacketDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return investorRedPacketDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return investorRedPacketDAO.getCount(inputParams);
	}
}
