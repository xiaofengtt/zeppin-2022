/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDailyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundRateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.Fund.FundStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class FundService extends BaseService implements IFundService {

	@Autowired
	private IFundDAO fundDAO;
	
	@Autowired
	private IFundRateDAO fundRateDAO;
	
	@Autowired
	private IFundPublishDailyDAO fundPublishDailyDAO;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	@Override
	public Fund insert(Fund fund) {
		return fundDAO.insert(fund);
	}

	@Override
	public Fund delete(Fund fund) {
		fund.setStatus(FundStatus.DELETED);
		return fundDAO.update(fund);
	}

	@Override
	public Fund update(Fund fund) {
		return fundDAO.update(fund);
	}

	@Override
	public Fund get(String uuid) {
		return fundDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询Fund结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return fundDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundDAO.getCount(inputParams);
	}
	
	/**
	 * 验证同名的活期理财是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistFundByName(String name, String uuid) {
		return fundDAO.isExistFundByName(name, uuid);
	}
	
	/**
	 * 验证同编号的活期理财是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistFundByScode(String scode, String uuid) {
		return fundDAO.isExistFundByScode(scode, uuid);
	}
}
