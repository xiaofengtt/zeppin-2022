/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundInvestDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDailyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishStatus;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeHistoryDAO;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class FundPublishService extends BaseService implements IFundPublishService {

	@Autowired
	private IFundPublishDAO fundPublishDAO;
	
	@Autowired
	private IFundPublishDailyDAO fundPublishDailyDAO;
	
	@Autowired
	private IFundDAO fundDAO;
	
	@Autowired
	private IFundInvestDAO fundInvestDAO;
	
	@Autowired
	private IInvestorDAO investorDAO;
	
	@Autowired
	private IInvestorAccountHistoryDAO investorAccountHistoryDAO;
	
	@Autowired
	private IQcbEmployeeDAO qcbEmployeeDAO;
	
	@Autowired
	private IQcbEmployeeHistoryDAO qcbEmployeeHistoryDAO;
	
	@Autowired
	private IBankFinancialProductInvestDAO bankFinancialProductInvestDAO;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Override
	public FundPublish insert(FundPublish fund) {
		return fundPublishDAO.insert(fund);
	}

	@Override
	public FundPublish delete(FundPublish fund) {
		fund.setStatus(FundPublishStatus.DELETED);
		return fundPublishDAO.update(fund);
	}

	@Override
	public FundPublish update(FundPublish fund) {
		return fundPublishDAO.update(fund);
	}

	@Override
	public FundPublish get(String uuid) {
		return fundPublishDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询FundPublish结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return fundPublishDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundPublishDAO.getCount(inputParams);
	}
	
	
	/**
	 * 更新活期理财净值
	 */
	public void netvalueUpdate(){
		FundPublish fp = this.fundPublishDAO.get(FundPublishUuid.CURRENT);
		
		String time = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("fundPublish", FundPublishUuid.CURRENT);
		inputParams.put("statistime", Utlity.getFullTime(time));
		List<Entity> list = this.fundPublishDailyDAO.getListForPage(inputParams, -1, -1, null, FundPublishDaily.class);
		
		if(list != null && list.size() > 0){
			Entity e = list.get(0);
			FundPublishDaily fd = (FundPublishDaily) e;
			fp.setNetWorth(fd.getNetValue());
			this.fundPublishDAO.update(fp);
		}
	}
	
	/**
	 * 第二天没净值时短信提醒
	 */
	public void netvalueMessage(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		String time = Utlity.timeSpanToDateString(new Timestamp(c.getTimeInMillis()));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("fundPublish", FundPublishUuid.CURRENT);
		inputParams.put("statistime", Utlity.getFullTime(time));
		List<Entity> list = this.fundPublishDailyDAO.getListForPage(inputParams, -1, -1, null, FundPublishDaily.class);
		
		if(list == null || list.size() == 0){
			SendSms.sendSms("牛投理财活期理财未录入明日净值！请提前录入", "18701376560");
		}
	}
	
	/**
	 * 获取活期募集总额
	 */
	public BigDecimal getTotalAmount(){
		Double totalInvestor = this.investorDAO.getTotalCurrentAccount();
		Double totalQcbEmployee = this.qcbEmployeeDAO.getTotalCurrentAccount();
		BigDecimal totalAmount = BigDecimal.valueOf(totalInvestor).add(BigDecimal.valueOf(totalQcbEmployee));
		
		FundPublish fp = fundPublishDAO.get(FundPublishUuid.CURRENT);
		if(fp == null || fp.getNetWorth() == null){
			return BigDecimal.ZERO;
		}
		
		return totalAmount.multiply(fp.getNetWorth()).setScale(2, RoundingMode.FLOOR);
	}
	
	/**
	 * 获取活期余额
	 */
	public BigDecimal getAccountBalance(){
		Double totalInvestor = this.investorAccountHistoryDAO.getCurrentTotalAmount();
		Double totalQcbEmployee = this.qcbEmployeeHistoryDAO.getCurrentTotalAmount();
		BigDecimal totalAmount = BigDecimal.valueOf(totalInvestor + totalQcbEmployee);
		
		Double totalCurrentInvest = this.fundInvestDAO.getTotalCurrentInvest();
		Double totalCurrentRedeem = this.fundInvestDAO.getTotalCurrentRedeem();
		BigDecimal totalInvest = BigDecimal.valueOf(totalCurrentInvest).subtract(BigDecimal.valueOf(totalCurrentRedeem));
		
		return totalAmount.subtract(totalInvest).setScale(2, RoundingMode.FLOOR);
	}
}
