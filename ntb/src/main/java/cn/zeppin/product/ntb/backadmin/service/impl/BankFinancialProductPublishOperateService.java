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

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishOperateService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductPublishOperateService extends BaseService implements IBankFinancialProductPublishOperateService {

	@Autowired
	private IBankFinancialProductPublishOperateDAO bankFinancialProductPublishOperateDAO;
	
	@Autowired
	private IBankFinancialProductPublishDAO bankFinancialProductPublishDAO;
	
	@Override
	public BankFinancialProductPublishOperate insert(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		return bankFinancialProductPublishOperateDAO.insert(bankFinancialProductPublishOperate);
	}

	@Override
	public BankFinancialProductPublishOperate delete(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		bankFinancialProductPublishOperate.setStatus(BankFinancialProductPublishOperateStatus.DELETED);
		return bankFinancialProductPublishOperateDAO.update(bankFinancialProductPublishOperate);
	}

	@Override
	public BankFinancialProductPublishOperate update(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		return bankFinancialProductPublishOperateDAO.update(bankFinancialProductPublishOperate);
	}

	@Override
	public BankFinancialProductPublishOperate get(String uuid) {
		return bankFinancialProductPublishOperateDAO.get(uuid);
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
		return bankFinancialProductPublishOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductPublishOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param bfppo
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(BankFinancialProductPublishOperate bfppo) throws TransactionException {
		
		//审核通过更新操作数据
		if(BankFinancialProductPublishOperateStatus.CHECKED.equals(bfppo.getStatus())){
			if(BankFinancialProductPublishOperateType.ADD.equals(bfppo.getType())){
				//添加
				BankFinancialProductPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);
				Map<String,String> search = new HashMap<String, String>();
				search.put("bankFinancialProduct", bfpp.getBankFinancialProduct());
				search.put("status", BankFinancialProductStatus.CHECKED);
				Integer count = bankFinancialProductPublishDAO.getCount(search);
				if(count != null && count > 0){
					throw new TransactionException("该银行理财产品已存在发布信息！");
				}
				if(bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByName(bfpp.getName(),null)){
					throw new TransactionException("募集产品名称已存在！");
				}
				if(bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByScode(bfpp.getScode(),null)){
					throw new TransactionException("募集产品编号已存在！");
				}
				bfpp.setFlagBuy(false);
				Timestamp ctime = new Timestamp(System.currentTimeMillis());
				if(ctime.after(bfpp.getCollectStarttime())){
					bfpp.setStage(BankFinancialProductPublishStage.COLLECT);
					bfpp.setFlagBuy(true);
				}
				if(ctime.after(bfpp.getCollectEndtime())){
					bfpp.setStage(BankFinancialProductPublishStage.INVESTED);
					bfpp.setFlagBuy(false);
				}
				if(ctime.after(bfpp.getValueDate())){
					bfpp.setStage(BankFinancialProductPublishStage.PROFIT);
					bfpp.setFlagBuy(false);
				}
				if(ctime.after(bfpp.getMaturityDate())){
					bfpp.setStage(BankFinancialProductPublishStage.FINISHED);
					bfpp.setFlagBuy(false);
				}
				bankFinancialProductPublishDAO.insert(bfpp);
			}else if(BankFinancialProductPublishOperateType.EDIT.equals(bfppo.getType())){
				//修改
				BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(bfppo.getBankFinancialProductPublish());
				if(bfpp != null){
					if(!BankFinancialProductPublishStatus.CHECKED.equals(bfpp.getStatus())){
						throw new TransactionException("理财产品发布信息审核状态错误，无法完成操作！");
					}
					BankFinancialProductPublish newBfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);
					if(bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByName(newBfpp.getName(),newBfpp.getUuid())){
						throw new TransactionException("募集产品名称已存在！");
					}
					if(bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByScode(newBfpp.getScode(),newBfpp.getUuid())){
						throw new TransactionException("募集产品编号已存在！");
					}
					
					bfppo.setOld(JSONUtils.obj2json(bfpp));
					
					newBfpp.setFlagBuy(false);
					Timestamp ctime = new Timestamp(System.currentTimeMillis());
					if(ctime.after(newBfpp.getCollectStarttime())){
						newBfpp.setStage(BankFinancialProductPublishStage.COLLECT);
						newBfpp.setFlagBuy(true);
					}
					if(ctime.after(newBfpp.getCollectEndtime())){
						newBfpp.setStage(BankFinancialProductPublishStage.INVESTED);
						newBfpp.setFlagBuy(false);
					}
					if(ctime.after(newBfpp.getValueDate())){
						newBfpp.setStage(BankFinancialProductPublishStage.PROFIT);
						newBfpp.setFlagBuy(false);
					}
					if(ctime.after(newBfpp.getMaturityDate())){
						newBfpp.setStage(BankFinancialProductPublishStage.FINISHED);
						newBfpp.setFlagBuy(false);
					}
					
					bfpp.setName(newBfpp.getName());
					bfpp.setSeries(newBfpp.getSeries());
					bfpp.setScode(newBfpp.getScode());
					bfpp.setShortname(newBfpp.getShortname());
					bfpp.setType(newBfpp.getType());
					bfpp.setUrl(newBfpp.getUrl());
					bfpp.setTarget(newBfpp.getTarget());
					bfpp.setTargetAnnualizedReturnRate(newBfpp.getTargetAnnualizedReturnRate());
					bfpp.setMinInvestAmount(newBfpp.getMinInvestAmount());
					bfpp.setMinInvestAmountAdd(newBfpp.getMinInvestAmountAdd());
					bfpp.setMaxInvestAmount(newBfpp.getMaxInvestAmount());
					bfpp.setTotalAmount(newBfpp.getTotalAmount());
					bfpp.setCollectAmount(newBfpp.getCollectAmount());
					bfpp.setCustodian(newBfpp.getCustodian());
					bfpp.setStyle(newBfpp.getStyle());
					bfpp.setRiskLevel(newBfpp.getRiskLevel());
					bfpp.setCreditLevel(newBfpp.getCreditLevel());
					bfpp.setCurrencyType(newBfpp.getCurrencyType());
					bfpp.setGuaranteeStatus(newBfpp.getGuaranteeStatus());
					bfpp.setFlagCloseend(newBfpp.getFlagCloseend());
					bfpp.setFlagPurchase(newBfpp.getFlagPurchase());
					bfpp.setFlagRedemption(newBfpp.getFlagRedemption());
					bfpp.setFlagFlexible(newBfpp.getFlagFlexible());
					bfpp.setMinAnnualizedReturnRate(newBfpp.getMinAnnualizedReturnRate());
					bfpp.setPaymentType(newBfpp.getPaymentType());
					bfpp.setRecordDate(newBfpp.getRecordDate());
					bfpp.setArea(newBfpp.getArea());
					bfpp.setSubscribeFee(newBfpp.getSubscribeFee());
					bfpp.setPurchaseFee(newBfpp.getPurchaseFee());
					bfpp.setRedemingFee(newBfpp.getRedemingFee());
					bfpp.setManagementFee(newBfpp.getManagementFee());
					bfpp.setCustodyFee(newBfpp.getCustodyFee());
					bfpp.setNetworkFee(newBfpp.getNetworkFee());
					bfpp.setCollectStarttime(newBfpp.getCollectStarttime());
					bfpp.setCollectEndtime(newBfpp.getCollectEndtime());
					bfpp.setValueDate(newBfpp.getValueDate());
					bfpp.setMaturityDate(newBfpp.getMaturityDate());
					bfpp.setTerm(newBfpp.getTerm());
					bfpp.setInvestScope(newBfpp.getInvestScope());
					bfpp.setRevenueFeature(newBfpp.getRevenueFeature());
					bfpp.setRemark(newBfpp.getRemark());
					bfpp.setDocument(newBfpp.getDocument());
					bfpp.setStage(newBfpp.getStage());
					bfpp.setFlagBuy(newBfpp.getFlagBuy());
					
					bankFinancialProductPublishDAO.update(bfpp);
				}else{
					throw new TransactionException("理财产品发布信息不存在");
				}
			}else if(BankFinancialProductPublishOperateType.DELETE.equals(bfppo.getType())){
				//删除
				BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(bfppo.getBankFinancialProductPublish());
				if(bfpp != null){
					if(!BankFinancialProductPublishStatus.CHECKED.equals(bfpp.getStatus())){
						throw new TransactionException("理财产品发布信息审核状态错误，无法完成操作！");
					}
					bfpp.setFlagBuy(false);
					bfpp.setStatus(BankFinancialProductPublishStatus.DELETED);
					bankFinancialProductPublishDAO.update(bfpp);
				}else{
					throw new TransactionException("理财产品发布信息不存在");
				}
			}else if(BankFinancialProductPublishOperateType.EXCEPTION.equals(bfppo.getType())){
				//异常下线
				BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(bfppo.getBankFinancialProductPublish());
				if(bfpp != null){
					bfpp.setFlagBuy(false);
					bfpp.setStage(BankFinancialProductPublishStage.EXCEPTION);
					bankFinancialProductPublishDAO.update(bfpp);
				}else{
					throw new TransactionException("理财产品发布信息不存在");
				}
			}else if(BankFinancialProductPublishOperateType.COLLECT.equals(bfppo.getType())){
				//开启认购
				BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(bfppo.getBankFinancialProductPublish());
				if(bfpp != null){
					if(!BankFinancialProductPublishStage.UNSTART.equals(bfpp.getStage())){
						throw new TransactionException("");
					}
					bfpp.setFlagBuy(true);
					bfpp.setStage(BankFinancialProductPublishStage.COLLECT);
					bankFinancialProductPublishDAO.update(bfpp);
				}else{
					throw new TransactionException("理财产品发布信息不存在");
				}
			}else if(BankFinancialProductPublishOperateType.UNINVEST.equals(bfppo.getType())){
				//结束认购
				BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(bfppo.getBankFinancialProductPublish());
				if(bfpp != null){
					if(!BankFinancialProductPublishStage.COLLECT.equals(bfpp.getStage())){
						throw new TransactionException("");
					}
					bfpp.setFlagBuy(false);
					bfpp.setStage(BankFinancialProductPublishStage.UNINVEST);
					bankFinancialProductPublishDAO.update(bfpp);
				}else{
					throw new TransactionException("理财产品发布信息不存在");
				}
			}
		}
		if(BankFinancialProductPublishOperateStatus.CHECKED.equals(bfppo.getStatus()) && (bfppo.getReason() == null || "".equals(bfppo.getReason()))){
			bfppo.setReason("审核通过！");
		}else if(BankFinancialProductPublishOperateStatus.UNPASSED.equals(bfppo.getStatus()) && (bfppo.getReason() == null || "".equals(bfppo.getReason()))){
			bfppo.setReason("审核不通过！");
		}
		bankFinancialProductPublishOperateDAO.update(bfppo);
	}
	
	/**
	 * 获取募集产品操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取募集产品操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishOperateDAO.getTypeList(inputParams, resultClass);
	}
}
