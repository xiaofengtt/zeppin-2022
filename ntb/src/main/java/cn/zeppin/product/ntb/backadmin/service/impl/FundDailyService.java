/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundDailyDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundDailyService;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.FundDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class FundDailyService extends BaseService implements IFundDailyService {

	@Autowired
	private IFundDailyDAO fundDailyDAO;
	@Autowired
	private IFundDAO fundDAO;
	
	@Override
	public FundDaily insert(FundDaily fundDaily) {
		return fundDailyDAO.insert(fundDaily);
	}

	@Override
	public FundDaily delete(FundDaily fundDaily) {
		return fundDailyDAO.delete(fundDaily);
	}

	@Override
	public FundDaily update(FundDaily fundDaily) {
		return fundDailyDAO.update(fundDaily);
	}

	@Override
	public FundDaily get(String uuid) {
		return fundDailyDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询FundDaily结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return fundDailyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundDailyDAO.getCount(inputParams);
	}
	
	/**
	 * 增加每日净值，并同步更新对应的基金信息
	 * @param fundDaily
	 */
	@Override
	public FundDaily addDaily(FundDaily fundDaily) {
		if(fundDaily != null){
			fundDaily = this.fundDailyDAO.insert(fundDaily);
			if(fundDaily.getFund() != null){
				Fund fund = this.fundDAO.get(fundDaily.getFund());
				if(fund != null){
					fund.setNetWorth(fundDaily.getNetValue());
					this.fundDAO.update(fund);
					return fundDaily;
				} else{
					return null;
				}
			} else {
				return null;
			}
			
		} else {
			return null;
		}
	}
}
