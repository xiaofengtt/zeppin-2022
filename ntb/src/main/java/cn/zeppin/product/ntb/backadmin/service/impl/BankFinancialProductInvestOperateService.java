/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestOperateDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestRecordsDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountInvestDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest.BankFinancialProductInvestStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateType;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestRecords;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.CompanyAccountInvest;
import cn.zeppin.product.ntb.core.entity.CompanyAccountInvest.CompanyAccountInvestStage;
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
public class BankFinancialProductInvestOperateService extends BaseService implements IBankFinancialProductInvestOperateService {
	
	@Autowired
	private IBankFinancialProductDAO bankFinancialProductDAO;
	
	@Autowired
	private IBankFinancialProductPublishDAO bankFinancialProductPublishDAO;
	
	@Autowired
	private IBankFinancialProductInvestDAO bankFinancialProductInvestDAO;
	
	@Autowired
	private IBankFinancialProductInvestRecordsDAO bankFinancialProductInvestRecordsDAO;
	
	@Autowired
	private IBankFinancialProductInvestOperateDAO bankFinancialProductInvestOperateDAO;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Autowired
	private ICompanyAccountInvestDAO companyAccountInvestDAO;
	
	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Override
	public BankFinancialProductInvestOperate insert(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		return bankFinancialProductInvestOperateDAO.insert(bankFinancialProductInvestOperate);
	}

	@Override
	public BankFinancialProductInvestOperate delete(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		bankFinancialProductInvestOperate.setStatus(BankFinancialProductInvestOperateStatus.DELETED);
		return bankFinancialProductInvestOperateDAO.update(bankFinancialProductInvestOperate);
	}

	@Override
	public BankFinancialProductInvestOperate update(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		return bankFinancialProductInvestOperateDAO.update(bankFinancialProductInvestOperate);
	}

	@Override
	public BankFinancialProductInvestOperate get(String uuid) {
		return bankFinancialProductInvestOperateDAO.get(uuid);
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
		return bankFinancialProductInvestOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductInvestOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param bfpio
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(BankFinancialProductInvestOperate bfpio) throws TransactionException {
		//审核通过更新操作数据
		if(BankFinancialProductInvestOperateStatus.CHECKED.equals(bfpio.getStatus())){
			if(BankFinancialProductInvestOperateType.INVEST.equals(bfpio.getType())){
				//投资
				Map<String, Object> dataMap = JSONUtils.json2map(bfpio.getValue());
				BigDecimal totalAmount = BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalAmount").toString()));
				BigDecimal poundage = BigDecimal.valueOf(Double.parseDouble(dataMap.get("poundage").toString()));
				List<BankFinancialProductInvestRecords> dataList = JSONUtils.json2list(dataMap.get("dataList").toString(), BankFinancialProductInvestRecords.class);
				
				BankFinancialProductInvestRecords firstData = dataList.get(0);
				CompanyAccount ca = this.companyAccountDAO.get(firstData.getCompanyAccount());
				if(ca == null || CompanyAccountStatus.DELETED.equals(ca.getStatus())){
					throw new TransactionException("企业账户不存在！");
				}
				
				if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
					throw new TransactionException("企业账户已停用");
				}
				
				BankFinancialProduct bfp = this.bankFinancialProductDAO.get(bfpio.getBankFinancialProduct());
				if(bfp == null || BankFinancialProductStatus.DELETED.equals(bfp.getStatus())){
					throw new TransactionException("投资产品不存在！");
				}
				
				if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
					throw new TransactionException("投资产品已被删除");
				}
				
				if(!BankFinancialProductStage.COLLECT.equals(bfp.getStage())){
					throw new TransactionException("投资产品当前不可投资");
				}
				
				if(totalAmount.add(poundage).compareTo(ca.getAccountBalance()) > 0){
					throw new TransactionException("企业账户资金不足！");
				}
				
				PlatformAccount pab = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
				
				for(BankFinancialProductInvestRecords bfpir : dataList){
					if(FundPublishUuid.CURRENT.equals(bfpir.getBankFinancialProductPublish())){
						BigDecimal currentBalance = this.fundPublishService.getAccountBalance();
						if(bfpir.getTotalAmount().compareTo(currentBalance) > 0){
							throw new TransactionException("活期理财账户资金不足！");
						}
					}else if(PlatformAccountUuid.BALANCE.equals(bfpir.getBankFinancialProductPublish())){
						if(bfpir.getTotalAmount().compareTo(pab.getTotalAmount().add(pai.getTotalAmount())) > 0){
							throw new TransactionException("平台余额账户资金不足！");
						}
						pab.setTotalAmount(pab.getTotalAmount().subtract(bfpir.getTotalAmount()));
						pab.setInvestment(pab.getInvestment().add(bfpir.getTotalAmount()));
					}else{
						BankFinancialProductPublish bfpp = this.bankFinancialProductPublishDAO.get(bfpir.getBankFinancialProductPublish());
						if(bfpp == null || BankFinancialProductPublishStatus.DELETED.equals(bfpp.getStatus())){
							throw new TransactionException("募集产品不存在！");
						}
						if(BankFinancialProductPublishStage.FINISHED.equals(bfpp.getStage()) || BankFinancialProductPublishStage.EXCEPTION.equals(bfpp.getStage())){
							throw new TransactionException(bfpp.getName() + "当前无法进行投资！");
						}
						if(bfpir.getTotalAmount().compareTo(bfpp.getAccountBalance()) > 0 ){
							throw new TransactionException(bfpp.getName() + "资金不足！");
						}
						
						//募集账户
						bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(bfpir.getTotalAmount()));
						bfpp.setInvestment(bfpp.getInvestment().add(bfpir.getTotalAmount()));
						if(BigDecimal.ZERO.compareTo(bfpp.getAccountBalance()) == 0 && BankFinancialProductPublishStage.UNINVEST.equals(bfpp.getStage())){
							bfpp.setStage(BankFinancialProductPublishStage.INVESTED);
						}
						this.bankFinancialProductPublishDAO.update(bfpp);
					}
					//投资关系
					Map<String, String> bfpiSearchMap = new HashMap<String, String>();
					bfpiSearchMap.put("bankFinancialProductPublish", bfpir.getBankFinancialProductPublish());
					bfpiSearchMap.put("bankFinancialProduct", bfp.getUuid());
					bfpiSearchMap.put("companyAccount", ca.getUuid());
					List<Entity> bfpiList = this.bankFinancialProductInvestDAO.getList(bfpiSearchMap, null, BankFinancialProductInvest.class);
						
					if(bfpiList.size() > 0 ){
						BankFinancialProductInvest bfpis = (BankFinancialProductInvest) bfpiList.get(0);
						BankFinancialProductInvest bfpi = this.bankFinancialProductInvestDAO.get(bfpis.getUuid());
						bfpi.setAccountBalance(bfpi.getAccountBalance().add(bfpir.getTotalAmount()));
						bfpi.setTotalAmount(bfpi.getTotalAmount().add(bfpir.getTotalAmount()));
						bfpi.setStage(BankFinancialProductInvestStage.NORMAL);
						this.bankFinancialProductInvestDAO.update(bfpi);
						
						bfpir.setAccountBalace(bfpi.getAccountBalance());
					}else{
						BankFinancialProductInvest bfpi = new BankFinancialProductInvest();
						bfpi.setUuid(UUID.randomUUID().toString());
						bfpi.setBankFinancialProduct(bfp.getUuid());
						bfpi.setBankFinancialProductPublish(bfpir.getBankFinancialProductPublish());
						bfpi.setCompanyAccount(ca.getUuid());
						bfpi.setStage(BankFinancialProductInvestStage.NORMAL);
						bfpi.setAccountBalance(bfpir.getTotalAmount());
						bfpi.setTotalAmount(bfpir.getTotalAmount());
						bfpi.setTotalRedeem(BigDecimal.ZERO);
						bfpi.setTotalReturn(BigDecimal.ZERO);
						bfpi.setCreatetime(new Timestamp(System.currentTimeMillis()));
						bfpi.setCreator(bfpio.getCreator());
						this.bankFinancialProductInvestDAO.insert(bfpi);
						
						bfpir.setAccountBalace(bfpi.getAccountBalance());
					}
					
					//投资记录
					this.bankFinancialProductInvestRecordsDAO.insert(bfpir);
				}
				
				String newData = JSONUtils.obj2json(dataList);
				dataMap.put("dataList", newData);
				bfpio.setValue(JSONUtils.obj2json(dataMap));
				
				//投资账户
				bfp.setInvestment(bfp.getInvestment().add(totalAmount));
				bfp.setAccountBalance(bfp.getAccountBalance().add(totalAmount));
				bfp.setIsRedeem(false);
				this.bankFinancialProductDAO.update(bfp);
				
				//企业账户历史
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setType(CompanyAccountHistoryType.INVEST);
				cah.setTotalAmount(totalAmount);
				cah.setCompanyAccountOut(ca.getUuid());
				cah.setBankFinancialProduct(bfp.getUuid());
				cah.setPoundage(poundage);
				cah.setStatus(CompanyAccountHistoryStatus.NORMAL);
				cah.setCreatetime(new Timestamp(System.currentTimeMillis()));
				cah.setCreator(bfpio.getCreator());
				
				//20180622增加记录本次余额信息
				cah.setAccountBalance(ca.getAccountBalance().subtract(totalAmount));
				
				if(cah.getPoundage().compareTo(BigDecimal.ZERO) == 1){//手续费大于0才记录 否则不记录
					CompanyAccountHistory cahe = new CompanyAccountHistory();
					cahe.setUuid(UUID.randomUUID().toString());
					cahe.setType(CompanyAccountHistoryType.EXPEND);
					cahe.setTotalAmount(cah.getPoundage());
					cahe.setCompanyAccountOut(ca.getUuid());
					cahe.setPoundage(BigDecimal.ZERO);
					cahe.setStatus(CompanyAccountHistoryStatus.NORMAL);
					cahe.setCreatetime(new Timestamp(System.currentTimeMillis()));
					cahe.setCreator(bfpio.getCreator());
					
					cah.setRelated(cahe.getUuid());
					cahe.setRelated(cah.getUuid());
					
					//20180622增加记录本次余额信息
					cahe.setAccountBalance(ca.getAccountBalance().subtract(cah.getPoundage()));
					
					this.companyAccountHistoryDAO.insert(cah);
					this.companyAccountHistoryDAO.insert(cahe);
				} else {
					this.companyAccountHistoryDAO.insert(cah);
				}
				
				
				
				//企业账户投资
				Map<String, String> caiSearchMap = new HashMap<String, String>();
				caiSearchMap.put("companyAccount", ca.getUuid());
				caiSearchMap.put("bankFinancialProduct", bfp.getUuid());
				List<Entity> caiList = this.companyAccountInvestDAO.getList(caiSearchMap, null, CompanyAccountInvest.class);
				if(caiList.size() > 0){
					CompanyAccountInvest cais = (CompanyAccountInvest) caiList.get(0);
					CompanyAccountInvest cai = this.companyAccountInvestDAO.get(cais.getUuid());
					cai.setAccountBalance(cai.getAccountBalance().add(totalAmount));
					cai.setTotalAmount(cai.getTotalAmount().add(totalAmount));
					cai.setStage(CompanyAccountInvestStage.NORMAL);
					this.companyAccountInvestDAO.update(cai);
				}else{
					CompanyAccountInvest cai = new CompanyAccountInvest();
					cai.setUuid(UUID.randomUUID().toString());
					cai.setBankFinancialProduct(bfp.getUuid());
					cai.setCompanyAccount(ca.getUuid());
					cai.setStage(CompanyAccountInvestStage.NORMAL);
					cai.setTotalAmount(totalAmount);
					cai.setAccountBalance(totalAmount);
					cai.setTotalRedeem(BigDecimal.ZERO);
					cai.setTotalReturn(BigDecimal.ZERO);
					cai.setCreatetime(new Timestamp(System.currentTimeMillis()));
					cai.setCreator(bfpio.getCreator());
					this.companyAccountInvestDAO.insert(cai);
				}
				
				//企业账户
				ca.setAccountBalance(ca.getAccountBalance().subtract(totalAmount).subtract(poundage));
				ca.setInvestment(ca.getInvestment().add(totalAmount));
				companyAccountDAO.update(ca);
				
				//平台账户
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				pa.setTotalAmount(pa.getTotalAmount().subtract(totalAmount).subtract(poundage));
				pa.setInvestment(pa.getInvestment().add(totalAmount));
				platformAccountDAO.update(pa);
				
				pab.setTotalAmount(pab.getTotalAmount().subtract(poundage));
				platformAccountDAO.update(pab);
			}else if(BankFinancialProductInvestOperateType.REDEEM.equals(bfpio.getType())){
				//赎回
				Map<String, Object> dataMap = JSONUtils.json2map(bfpio.getValue());
				BigDecimal totalAmount = BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalAmount").toString()));
				BigDecimal totalReturn = BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalReturn").toString()));
				List<BankFinancialProductInvestRecords> dataList = JSONUtils.json2list(dataMap.get("dataList").toString(), BankFinancialProductInvestRecords.class);
				
				BankFinancialProductInvestRecords firstData = dataList.get(0);
				CompanyAccount ca = this.companyAccountDAO.get(firstData.getCompanyAccount());
				PlatformAccount pab = this.platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				if(ca == null || CompanyAccountStatus.DELETED.equals(ca.getStatus())){
					throw new TransactionException("企业账户不存在！");
				}
				
				if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
					throw new TransactionException("企业账户已停用");
				}
				
				BankFinancialProduct bfp = this.bankFinancialProductDAO.get(bfpio.getBankFinancialProduct());
				if(bfp == null || BankFinancialProductStatus.DELETED.equals(bfp.getStatus())){
					throw new TransactionException("投资产品不存在！");
				}
				
				if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
					throw new TransactionException("投资产品已被删除");
				}
				
				if(totalAmount.compareTo(ca.getInvestment()) > 0){
					throw new TransactionException("赎回金额大于企业账户投资总额！");
				}
				
				for(BankFinancialProductInvestRecords bfpir : dataList){
					BigDecimal realReturn = totalReturn.multiply(bfpir.getTotalAmount().divide(totalAmount,10,BigDecimal.ROUND_FLOOR));
							
					//投资关系
					Map<String, String> bfpiSearchMap = new HashMap<String, String>();
					bfpiSearchMap.put("bankFinancialProductPublish", bfpir.getBankFinancialProductPublish());
					bfpiSearchMap.put("bankFinancialProduct", bfp.getUuid());
					bfpiSearchMap.put("companyAccount", ca.getUuid());
					List<Entity> bfpiList = this.bankFinancialProductInvestDAO.getList(bfpiSearchMap, null, BankFinancialProductInvest.class);
					
					if(bfpiList.size() > 0 ){
						BankFinancialProductInvest bfpis = (BankFinancialProductInvest) bfpiList.get(0);
						BankFinancialProductInvest bfpi = this.bankFinancialProductInvestDAO.get(bfpis.getUuid());
						
						if(bfpir.getTotalAmount().compareTo(bfpi.getAccountBalance()) > 0){
							throw new TransactionException("募集账户赎回金额大于投资余额！");
						}
						
						bfpi.setAccountBalance(bfpi.getAccountBalance().subtract(bfpir.getTotalAmount()));
						bfpi.setTotalRedeem(bfpi.getTotalRedeem().add(bfpir.getTotalAmount()));
						bfpi.setTotalReturn(bfpi.getTotalReturn().add(realReturn));
						this.bankFinancialProductInvestDAO.update(bfpi);
						
						bfpir.setAccountBalace(bfpi.getAccountBalance());
					}else{
						throw new TransactionException("存在募集账户账户未进行相应投资！");
					}
					
					//募集账户
					if(PlatformAccountUuid.BALANCE.equals(bfpir.getBankFinancialProductPublish())){
						pab.setTotalAmount(pab.getTotalAmount().add(bfpir.getTotalAmount()).add(realReturn));
						pab.setTotalRedeem(pab.getTotalRedeem().add(bfpir.getTotalAmount()));
						pab.setTotalReturn(pab.getTotalReturn().add(realReturn));
						this.platformAccountDAO.update(pab);
						
					}else{
						BankFinancialProductPublish bfpp = this.bankFinancialProductPublishDAO.get(bfpir.getBankFinancialProductPublish());
						if(bfpp == null || BankFinancialProductPublishStatus.DELETED.equals(bfpp.getStatus())){
							throw new TransactionException("募集产品不存在！");
						}
						
						if(BankFinancialProductPublishStage.FINISHED.equals(bfpp.getStage())){
							pab.setTotalAmount(pab.getTotalAmount().add(bfpir.getTotalAmount()).add(realReturn));
						}
						
						bfpp.setAccountBalance(bfpp.getAccountBalance().add(bfpir.getTotalAmount()).add(realReturn));
						bfpp.setTotalRedeem(bfpp.getTotalRedeem().add(bfpir.getTotalAmount()));
						bfpp.setTotalReturn(bfpp.getTotalReturn().add(realReturn));
						this.bankFinancialProductPublishDAO.update(bfpp);
					}
					
					//投资记录
					this.bankFinancialProductInvestRecordsDAO.insert(bfpir);
				}
				
				String newData = JSONUtils.obj2json(dataList);
				dataMap.put("dataList", newData);
				bfpio.setValue(JSONUtils.obj2json(dataMap));
				
				//企业账户余额
				this.platformAccountDAO.update(pab);
				
				//投资账户
				bfp.setAccountBalance(bfp.getAccountBalance().subtract(totalAmount));
				bfp.setTotalRedeem(bfp.getTotalRedeem().add(totalAmount));
				bfp.setTotalReturn(bfp.getTotalReturn().add(totalReturn));
				if(bfp.getInvestment().compareTo(bfp.getTotalRedeem()) == 0){
					bfp.setIsRedeem(true);
				}
				this.bankFinancialProductDAO.update(bfp);
				
				//企业账户历史
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setType(CompanyAccountHistoryType.REDEEM);
				cah.setTotalAmount(totalAmount);
				cah.setCompanyAccountIn(ca.getUuid());
				cah.setBankFinancialProduct(bfp.getUuid());
				cah.setPoundage(BigDecimal.ZERO);
				cah.setStatus(CompanyAccountHistoryStatus.NORMAL);
				cah.setCreatetime(new Timestamp(System.currentTimeMillis()));
				cah.setCreator(bfpio.getCreator());
				
				//20180622增加记录本次余额信息
				cah.setAccountBalance(ca.getAccountBalance().add(totalAmount));
				
				CompanyAccountHistory cahe = new CompanyAccountHistory();
				cahe.setUuid(UUID.randomUUID().toString());
				cahe.setType(CompanyAccountHistoryType.RETURN);
				cahe.setTotalAmount(totalReturn);
				cahe.setCompanyAccountIn(ca.getUuid());
				cahe.setBankFinancialProduct(bfp.getUuid());
				cahe.setPoundage(BigDecimal.ZERO);
				cahe.setStatus(CompanyAccountHistoryStatus.NORMAL);
				cahe.setCreatetime(new Timestamp(System.currentTimeMillis()));
				cahe.setCreator(bfpio.getCreator());
				
				//20180622增加记录本次余额信息
				cahe.setAccountBalance(ca.getAccountBalance().add(totalReturn));
				
				cah.setRelated(cahe.getUuid());
				cahe.setRelated(cah.getUuid());
				this.companyAccountHistoryDAO.insert(cah);
				this.companyAccountHistoryDAO.insert(cahe);
				
				//企业账户投资
				Map<String, String> caiSearchMap = new HashMap<String, String>();
				caiSearchMap.put("companyAccount", ca.getUuid());
				caiSearchMap.put("bankFinancialProduct", bfp.getUuid());
				List<Entity> caiList = this.companyAccountInvestDAO.getList(caiSearchMap, null, CompanyAccountInvest.class);
				if(caiList.size() > 0){
					CompanyAccountInvest cais = (CompanyAccountInvest) caiList.get(0);
					CompanyAccountInvest cai = this.companyAccountInvestDAO.get(cais.getUuid());
					cai.setAccountBalance(cai.getAccountBalance().subtract(totalAmount));
					cai.setTotalRedeem(cai.getTotalRedeem().add(totalAmount));
					cai.setTotalReturn(cai.getTotalReturn().add(totalReturn));
					cai.setStage(CompanyAccountInvestStage.NORMAL);
					this.companyAccountInvestDAO.update(cai);
				}else{
					throw new TransactionException("企业账户未进行相应投资！");
				}
				
				//企业账户
				ca.setAccountBalance(ca.getAccountBalance().add(totalAmount).add(totalReturn));
				ca.setTotalRedeem(ca.getTotalRedeem().add(totalAmount));
				ca.setTotalReturn(ca.getTotalReturn().add(totalReturn));
				companyAccountDAO.update(ca);
				
				//平台账户
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				pa.setTotalAmount(pa.getTotalAmount().add(totalAmount).add(totalReturn));
				pa.setTotalRedeem(pa.getTotalRedeem().add(totalAmount));
				pa.setTotalReturn(pa.getTotalReturn().add(totalReturn));
				platformAccountDAO.update(pa);
			}
		}
		if(BankFinancialProductInvestOperateStatus.CHECKED.equals(bfpio.getStatus()) && (bfpio.getReason() == null || "".equals(bfpio.getReason()))){
			bfpio.setReason("审核通过！");
		}else if(BankFinancialProductInvestOperateStatus.UNPASSED.equals(bfpio.getStatus()) && (bfpio.getReason() == null || "".equals(bfpio.getReason()))){
			bfpio.setReason("审核不通过！");
		}
		bankFinancialProductInvestOperateDAO.update(bfpio);
	}
	
	/**
	 * 获取投资操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductInvestOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取投资操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductInvestOperateDAO.getTypeList(inputParams, resultClass);
	}
}
