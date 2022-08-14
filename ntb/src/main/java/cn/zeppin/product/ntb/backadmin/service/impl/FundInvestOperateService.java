/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountInvestDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundInvestDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundInvestOperateDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundInvestOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.Fund.FundStatus;
import cn.zeppin.product.ntb.core.entity.FundInvest;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate.FundInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate.FundInvestOperateType;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class FundInvestOperateService extends BaseService implements IFundInvestOperateService {
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IFundDAO fundDAO;
	
	@Autowired
	private IFundPublishDAO fundPublishDAO;
	
	@Autowired
	private IFundInvestDAO fundInvestDAO;
	
	@Autowired
	private IFundInvestOperateDAO fundInvestOperateDAO;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Autowired
	private ICompanyAccountInvestDAO companyAccountInvestDAO;
	
	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Override
	public FundInvestOperate insert(FundInvestOperate fundInvestOperate) {
		return fundInvestOperateDAO.insert(fundInvestOperate);
	}

	@Override
	public FundInvestOperate delete(FundInvestOperate fundInvestOperate) {
		fundInvestOperate.setStatus(FundInvestOperateStatus.DELETED);
		return fundInvestOperateDAO.update(fundInvestOperate);
	}

	@Override
	public FundInvestOperate update(FundInvestOperate fundInvestOperate) {
		return fundInvestOperateDAO.update(fundInvestOperate);
	}

	@Override
	public FundInvestOperate get(String uuid) {
		return fundInvestOperateDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return fundInvestOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundInvestOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param fio
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(FundInvestOperate fio) throws TransactionException {
		//审核通过更新操作数据
		if(FundInvestOperateStatus.CHECKED.equals(fio.getStatus())){
			if(FundInvestOperateType.INVEST.equals(fio.getType())){
				//投资
				Map<String, Object> dataMap = JSONUtils.json2map(fio.getValue());
				BigDecimal totalAmount = BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalAmount").toString()));
				List<FundInvest> dataList = JSONUtils.json2list(dataMap.get("dataList").toString(), FundInvest.class);
				
				FundInvest firstData = dataList.get(0);
				BigDecimal accountBalance = firstData.getAccountBalance();
				CompanyAccount ca = this.companyAccountDAO.get(firstData.getCompanyAccount());
				if(ca == null || CompanyAccountStatus.DELETED.equals(ca.getStatus())){
					throw new TransactionException("企业账户不存在！");
				}
				
				if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
					throw new TransactionException("企业账户已停用");
				}
				
				Fund f = this.fundDAO.get(fio.getFund());
				if(f == null || FundStatus.DELETED.equals(f.getStatus())){
					throw new TransactionException("投资产品不存在！");
				}
				
				if(!FundStatus.CHECKED.equals(f.getStatus())){
					throw new TransactionException("投资产品已被删除");
				}
				
				if(totalAmount.compareTo(ca.getAccountBalance()) > 0){
					throw new TransactionException("企业账户资金不足！");
				}
				
				PlatformAccount pab = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
				
				for(FundInvest fir : dataList){
					if(FundPublishUuid.CURRENT.equals(fir.getFundPublish())){
						BigDecimal total = this.fundPublishService.getAccountBalance();
						if(fir.getTotalAmount().compareTo(total) > 0){
							throw new TransactionException("活期理财账户资金不足！");
						}
					}else if(PlatformAccountUuid.BALANCE.equals(fir.getFundPublish())){
						if(fir.getTotalAmount().compareTo(pab.getTotalAmount().add(pai.getTotalAmount())) > 0){
							throw new TransactionException("平台余额账户资金不足！");
						}
						pab.setTotalAmount(pab.getTotalAmount().subtract(fir.getTotalAmount()));
					}else{
						throw new TransactionException("募集产品不存在！");
					}
					
					//投资记录
					this.fundInvestDAO.insert(fir);
				}
				
				String newData = JSONUtils.obj2json(dataList);
				dataMap.put("dataList", newData);
				fio.setValue(JSONUtils.obj2json(dataMap));
				
				f.setAccountBalance(accountBalance);
				this.fundDAO.update(f);
				
				//企业账户历史
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setType(CompanyAccountHistoryType.CUR_INVEST);
				cah.setTotalAmount(totalAmount);
				cah.setCompanyAccountOut(ca.getUuid());
				cah.setFund(f.getUuid());
				cah.setPoundage(BigDecimal.ZERO);
				cah.setStatus(CompanyAccountHistoryStatus.NORMAL);
				cah.setCreatetime(new Timestamp(System.currentTimeMillis()));
				cah.setCreator(fio.getCreator());
				
				//20180622增加记录本次余额信息
				cah.setAccountBalance(ca.getAccountBalance().subtract(totalAmount));
				
				this.companyAccountHistoryDAO.insert(cah);
				
				//企业账户
				ca.setAccountBalance(ca.getAccountBalance().subtract(totalAmount));
				companyAccountDAO.update(ca);
				
				//平台账户
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				pa.setTotalAmount(pa.getTotalAmount().subtract(totalAmount));
				platformAccountDAO.update(pa);
				platformAccountDAO.update(pab);
			}else if(FundInvestOperateType.REDEEM.equals(fio.getType())){
				//赎回
				Map<String, Object> dataMap = JSONUtils.json2map(fio.getValue());
				BigDecimal totalAmount = BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalAmount").toString()));
				List<FundInvest> dataList = JSONUtils.json2list(dataMap.get("dataList").toString(), FundInvest.class);
				
				FundInvest firstData = dataList.get(0);
				BigDecimal accountBalance = firstData.getAccountBalance();
				CompanyAccount ca = this.companyAccountDAO.get(firstData.getCompanyAccount());
				PlatformAccount pab = this.platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				if(ca == null || CompanyAccountStatus.DELETED.equals(ca.getStatus())){
					throw new TransactionException("企业账户不存在！");
				}
				
				if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
					throw new TransactionException("企业账户已停用");
				}
				
				Fund f = this.fundDAO.get(fio.getFund());
				if(f == null || FundStatus.DELETED.equals(f.getStatus())){
					throw new TransactionException("投资产品不存在！");
				}
				
				if(!FundStatus.CHECKED.equals(f.getStatus())){
					throw new TransactionException("投资产品已被删除");
				}
				
				for(FundInvest fir : dataList){
					//募集账户
					if(FundPublishUuid.CURRENT.equals((fir.getFundPublish()))){
						
					}else if(PlatformAccountUuid.BALANCE.equals(fir.getFundPublish())){
						pab.setTotalAmount(pab.getTotalAmount().add(fir.getTotalAmount()));
						this.platformAccountDAO.update(pab);
						
					}else{
						throw new TransactionException("募集产品不存在！");
					}
					//投资记录
					this.fundInvestDAO.insert(fir);
				}
				
				String newData = JSONUtils.obj2json(dataList);
				dataMap.put("dataList", newData);
				fio.setValue(JSONUtils.obj2json(dataMap));
				
				//企业账户余额
				this.platformAccountDAO.update(pab);
				
				//投资账户
				f.setAccountBalance(accountBalance);
				this.fundDAO.update(f);
				
				//企业账户历史
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setType(CompanyAccountHistoryType.CUR_REDEEM);
				cah.setTotalAmount(totalAmount);
				cah.setCompanyAccountIn(ca.getUuid());
				cah.setFund(f.getUuid());
				cah.setPoundage(BigDecimal.ZERO);
				cah.setStatus(CompanyAccountHistoryStatus.NORMAL);
				cah.setCreatetime(new Timestamp(System.currentTimeMillis()));
				cah.setCreator(fio.getCreator());
				
				//20180622增加记录本次余额信息
				cah.setAccountBalance(ca.getAccountBalance().add(totalAmount));
				
				this.companyAccountHistoryDAO.insert(cah);
				
				//企业账户
				ca.setAccountBalance(ca.getAccountBalance().add(totalAmount));
				companyAccountDAO.update(ca);
				
				//平台账户
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				pa.setTotalAmount(pa.getTotalAmount().add(totalAmount));
				platformAccountDAO.update(pa);
			}
		}
		if(FundInvestOperateStatus.CHECKED.equals(fio.getStatus()) && (fio.getReason() == null || "".equals(fio.getReason()))){
			fio.setReason("审核通过！");
		}else if(FundInvestOperateStatus.UNPASSED.equals(fio.getStatus()) && (fio.getReason() == null || "".equals(fio.getReason()))){
			fio.setReason("审核不通过！");
		}
		fundInvestOperateDAO.update(fio);
	}
	
	/**
	 * 获取投资操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return fundInvestOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取投资操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return fundInvestOperateDAO.getTypeList(inputParams, resultClass);
	}
}
