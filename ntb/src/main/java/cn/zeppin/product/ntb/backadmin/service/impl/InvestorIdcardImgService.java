/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorIdcardImgDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorIdcardImgService;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg.InvestorIdcardImgStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class InvestorIdcardImgService extends BaseService implements IInvestorIdcardImgService {

	@Autowired
	private IInvestorIdcardImgDAO investorIdcardImgDAO;

	
	@Override
	public InvestorIdcardImg insert(InvestorIdcardImg investorIdcardImg) {
		return investorIdcardImgDAO.insert(investorIdcardImg);
	}

	@Override
	public InvestorIdcardImg delete(InvestorIdcardImg investorIdcardImg) {
		investorIdcardImg.setStatus(InvestorIdcardImgStatus.DELETED);
		return investorIdcardImgDAO.update(investorIdcardImg);
	}

	@Override
	public InvestorIdcardImg update(InvestorIdcardImg investorIdcardImg) {
		return investorIdcardImgDAO.update(investorIdcardImg);
	}

	@Override
	public InvestorIdcardImg get(String uuid) {
		return investorIdcardImgDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询investorIdcardImg结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return investorIdcardImgDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return investorIdcardImgDAO.getCount(inputParams);
	}

	@Override
	public List<Entity> getList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return investorIdcardImgDAO.getList(inputParams, resultClass);
	}

	@Override
	public List<Entity> getListForBackPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return investorIdcardImgDAO.getListForBackPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForBackPage(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return investorIdcardImgDAO.getCountForBackPage(inputParams);
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return investorIdcardImgDAO.getStatusList(inputParams, resultClass);
	}

}
