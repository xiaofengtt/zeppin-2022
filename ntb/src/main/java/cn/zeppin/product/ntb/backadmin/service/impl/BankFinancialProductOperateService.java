/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDailyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductOperateService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductOperateDailyVO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductDaily;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductOperateService extends BaseService implements IBankFinancialProductOperateService {

	@Autowired
	private IBankFinancialProductDAO bankFinancialProductDAO;
	
	@Autowired
	private IBankFinancialProductDailyDAO bankFinancialProductDailyDAO;
	
	@Autowired
	private IBankFinancialProductOperateDAO bankFinancialProductOperateDAO;
	
	@Override
	public BankFinancialProductOperate insert(BankFinancialProductOperate bankFinancialProductOperate) {
		return bankFinancialProductOperateDAO.insert(bankFinancialProductOperate);
	}

	@Override
	public BankFinancialProductOperate delete(BankFinancialProductOperate bankFinancialProductOperate) {
		bankFinancialProductOperate.setStatus(BankFinancialProductOperateStatus.DELETED);
		return bankFinancialProductOperateDAO.update(bankFinancialProductOperate);
	}

	@Override
	public BankFinancialProductOperate update(BankFinancialProductOperate bankFinancialProductOperate) {
		return bankFinancialProductOperateDAO.update(bankFinancialProductOperate);
	}

	@Override
	public BankFinancialProductOperate get(String uuid) {
		return bankFinancialProductOperateDAO.get(uuid);
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
		return bankFinancialProductOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param bfpo
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(BankFinancialProductOperate bfpo) throws TransactionException {
		
		//审核通过更新操作数据
		if(BankFinancialProductOperateStatus.CHECKED.equals(bfpo.getStatus())){
			if(BankFinancialProductOperateType.ADD.equals(bfpo.getType())){
				//添加
				BankFinancialProduct bfp = JSONUtils.json2obj(bfpo.getValue(), BankFinancialProduct.class);
				if(bankFinancialProductDAO.isExistBankFinancialProductByName(bfp.getName(),null)){
					throw new TransactionException("银行理财产品名称已存在！");
				}
				if(bankFinancialProductDAO.isExistBankFinancialProductByScode(bfp.getScode(),null)){
					throw new TransactionException("银行理财产品编号已存在！");
				}
				bfp.setIsRedeem(false);
				Timestamp ctime = new Timestamp(System.currentTimeMillis());
				if(ctime.after(bfp.getCollectStarttime())){
					bfp.setStage(BankFinancialProductStage.COLLECT);
				}
				if(ctime.after(bfp.getCollectEndtime())){
					bfp.setStage(BankFinancialProductStage.INCOME);
				}
				if(ctime.after(bfp.getMaturityDate())){
					bfp.setStage(BankFinancialProductStage.FINISHED);
				}
				bankFinancialProductDAO.insert(bfp);
			}else if(BankFinancialProductOperateType.EDIT.equals(bfpo.getType())){
				//修改
				BankFinancialProduct bfp = bankFinancialProductDAO.get(bfpo.getBankFinancialProduct());
				if(bfp != null){
					if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
						throw new TransactionException("理财产品审核状态错误，无法完成操作！");
					}
					BankFinancialProduct newBfpp = JSONUtils.json2obj(bfpo.getValue(), BankFinancialProduct.class);
					if(bankFinancialProductDAO.isExistBankFinancialProductByName(newBfpp.getName(),newBfpp.getUuid())){
						throw new TransactionException("银行理财产品名称已存在！");
					}
					if(bankFinancialProductDAO.isExistBankFinancialProductByScode(newBfpp.getScode(),newBfpp.getUuid())){
						throw new TransactionException("银行理财产品编号已存在！");
					}
					
					bfpo.setOld(JSONUtils.obj2json(bfp));
					
					Timestamp ctime = new Timestamp(System.currentTimeMillis());
					if(ctime.after(newBfpp.getCollectStarttime())){
						newBfpp.setStage(BankFinancialProductStage.COLLECT);
					}
					if(ctime.after(newBfpp.getCollectEndtime())){
						newBfpp.setStage(BankFinancialProductStage.INCOME);
					}
					if(ctime.after(newBfpp.getMaturityDate())){
						newBfpp.setStage(BankFinancialProductStage.FINISHED);
					}
					
					bfp.setName(newBfpp.getName());
					bfp.setSeries(newBfpp.getSeries());
					bfp.setScode(newBfpp.getScode());
					bfp.setShortname(newBfpp.getShortname());
					bfp.setType(newBfpp.getType());
					bfp.setUrl(newBfpp.getUrl());
					bfp.setTarget(newBfpp.getTarget());
					bfp.setTargetAnnualizedReturnRate(newBfpp.getTargetAnnualizedReturnRate());
					bfp.setMinInvestAmount(newBfpp.getMinInvestAmount());
					bfp.setMinInvestAmountAdd(newBfpp.getMinInvestAmountAdd());
					bfp.setMaxInvestAmount(newBfpp.getMaxInvestAmount());
					bfp.setTotalAmount(newBfpp.getTotalAmount());
					bfp.setCustodian(newBfpp.getCustodian());
					bfp.setStyle(newBfpp.getStyle());
					bfp.setRiskLevel(newBfpp.getRiskLevel());
					bfp.setCreditLevel(newBfpp.getCreditLevel());
					bfp.setCurrencyType(newBfpp.getCurrencyType());
					bfp.setGuaranteeStatus(newBfpp.getGuaranteeStatus());
					bfp.setFlagCloseend(newBfpp.getFlagCloseend());
					bfp.setFlagPurchase(newBfpp.getFlagPurchase());
					bfp.setFlagRedemption(newBfpp.getFlagRedemption());
					bfp.setFlagFlexible(newBfpp.getFlagFlexible());
					bfp.setMinAnnualizedReturnRate(newBfpp.getMinAnnualizedReturnRate());
					bfp.setPaymentType(newBfpp.getPaymentType());
					bfp.setRecordDate(newBfpp.getRecordDate());
					bfp.setArea(newBfpp.getArea());
						
					bfp.setSubscribeFee(newBfpp.getSubscribeFee());
					bfp.setPurchaseFee(newBfpp.getPurchaseFee());
					bfp.setRedemingFee(newBfpp.getRedemingFee());
					bfp.setManagementFee(newBfpp.getManagementFee());
					bfp.setCustodyFee(newBfpp.getCustodyFee());
					bfp.setNetworkFee(newBfpp.getNetworkFee());
					bfp.setCollectStarttime(newBfpp.getCollectStarttime());
					bfp.setCollectEndtime(newBfpp.getCollectEndtime());
					bfp.setValueDate(newBfpp.getValueDate());
					bfp.setMaturityDate(newBfpp.getMaturityDate());
					bfp.setTerm(newBfpp.getTerm());
					bfp.setInvestScope(newBfpp.getInvestScope());
					bfp.setRevenueFeature(newBfpp.getRevenueFeature());
					bfp.setRemark(newBfpp.getRemark());
					bfp.setDocument(newBfpp.getDocument());
					bfp.setStage(newBfpp.getStage());
					
					bankFinancialProductDAO.update(bfp);
				}else{
					throw new TransactionException("理财产品不存在，无法完成操作！");
				}
			}else if(BankFinancialProductOperateType.DELETE.equals(bfpo.getType())){
				//删除
				BankFinancialProduct bfp = bankFinancialProductDAO.get(bfpo.getBankFinancialProduct());
				if(bfp != null){
					if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
						throw new TransactionException("理财产品审核状态错误，无法完成操作！");
					}
					bfp.setStatus(BankFinancialProductStatus.DELETED);
					bankFinancialProductDAO.update(bfp);
				}else{
					throw new TransactionException("理财产品不存在，无法完成操作！");
				}
			}else if(BankFinancialProductOperateType.NETVALUE.equals(bfpo.getType())){
				//净值录入
				BankFinancialProduct bfp = bankFinancialProductDAO.get(bfpo.getBankFinancialProduct());
				if(bfp != null){
					if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
						throw new TransactionException("理财产品审核状态错误，无法完成操作！");
					}
					List<BankFinancialProductOperateDailyVO> list = JSONUtils.json2list(bfpo.getValue(), BankFinancialProductOperateDailyVO.class);
					for(BankFinancialProductOperateDailyVO bfpodVO : list){
						if(BankFinancialProductOperateType.ADD.equals(bfpodVO.getType())){//添加净值
							if(bfpodVO.getBankFinancialProductDaily() != null){
								Map<String, String> searchMap = new HashMap<String, String>();
								searchMap.put("statistime", bfpodVO.getBankFinancialProductDaily().getStatistime().toString());
								searchMap.put("bankFinancialProduct",bfpodVO.getBankFinancialProductDaily().getBankFinancialProduct());
								
								List<Entity> eList = this.bankFinancialProductDailyDAO.getListForPage(searchMap, 1, 10, null, BankFinancialProductDaily.class);
								if(eList.size()>0){
									BankFinancialProductDaily bfpd = (BankFinancialProductDaily) eList.get(0);
									bfpd.setNetValue(bfpodVO.getBankFinancialProductDaily().getNetValue());
									bfpd.setCreator(bfpodVO.getBankFinancialProductDaily().getCreator());
								}else{
									bankFinancialProductDailyDAO.insert(bfpodVO.getBankFinancialProductDaily());
								}
							}else{
								throw new TransactionException("净值信息有误，无法完成操作！");
							}
						} else if (BankFinancialProductOperateType.EDIT.equals(bfpodVO.getType())) {
							if(bfpodVO.getBankFinancialProductDaily() != null){
								BankFinancialProductDaily bfpd = this.bankFinancialProductDailyDAO.get(bfpodVO.getBankFinancialProductDaily().getUuid());
								if(bfpd != null){
									bfpd.setNetValue(bfpodVO.getBankFinancialProductDaily().getNetValue());
									bfpd.setCreator(bfpodVO.getBankFinancialProductDaily().getCreator());
									bankFinancialProductDailyDAO.update(bfpd);
								}else{
									throw new TransactionException("净值信息不存在，无法完成操作！");
								}
							}else{
								throw new TransactionException("净值信息有误，无法完成操作！");
							}
						} else if (BankFinancialProductOperateType.DELETE.equals(bfpodVO.getType())) {
							if(bfpodVO.getBankFinancialProductDaily() != null){
								BankFinancialProductDaily bfpd = this.bankFinancialProductDailyDAO.get(bfpodVO.getBankFinancialProductDaily().getUuid());
								if(bfpd != null){
									bankFinancialProductDailyDAO.delete(bfpd);
								}else{
									throw new TransactionException("净值信息不存在，无法完成操作！");
								}
							}else{
								throw new TransactionException("净值信息有误，无法完成操作！");
							}
						} else {
							throw new TransactionException("净值信息更新类型有误，无法完成操作！");
						}
					}
				}else{
					throw new TransactionException("理财产品不存在，无法完成操作！");
				}
			}
		}
		if(BankFinancialProductOperateStatus.CHECKED.equals(bfpo.getStatus()) && (bfpo.getReason() == null || "".equals(bfpo.getReason()))){
			bfpo.setReason("审核通过！");
		}else if(BankFinancialProductOperateStatus.UNPASSED.equals(bfpo.getStatus()) && (bfpo.getReason() == null || "".equals(bfpo.getReason()))){
			bfpo.setReason("审核不通过！");
		}
		bankFinancialProductOperateDAO.update(bfpo);
	}
	
	/**
	 * 获取银行理财产品操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取银行理财产品操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductOperateDAO.getTypeList(inputParams, resultClass);
	}
}
