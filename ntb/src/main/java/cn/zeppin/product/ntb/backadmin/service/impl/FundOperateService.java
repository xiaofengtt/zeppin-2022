/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundOperateService;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.Fund.FundStatus;
import cn.zeppin.product.ntb.core.entity.FundOperate;
import cn.zeppin.product.ntb.core.entity.FundOperate.FundOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundOperate.FundOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class FundOperateService extends BaseService implements IFundOperateService {

	@Autowired
	private IFundOperateDAO fundOperateDAO;
	
	@Autowired
	private IFundDAO fundDAO;
	
	@Override
	public FundOperate insert(FundOperate fundOperate) {
		return fundOperateDAO.insert(fundOperate);
	}

	@Override
	public FundOperate delete(FundOperate fundOperate) {
		fundOperate.setStatus(FundOperateStatus.DELETED);
		return fundOperateDAO.update(fundOperate);
	}

	@Override
	public FundOperate update(FundOperate fundOperate) {
		return fundOperateDAO.update(fundOperate);
	}

	@Override
	public FundOperate get(String uuid) {
		return fundOperateDAO.get(uuid);
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
		return fundOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param fo
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(FundOperate fo) throws TransactionException {
		
		//审核通过更新操作数据
		if(FundOperateStatus.CHECKED.equals(fo.getStatus())){
			if(FundOperateType.ADD.equals(fo.getType())){
				//添加
				Fund f = JSONUtils.json2obj(fo.getValue(), Fund.class);
				if(fundDAO.isExistFundByName(f.getName(),null)){
					throw new TransactionException("活期理财名称已存在！");
				}
				if(fundDAO.isExistFundByScode(f.getScode(),null)){
					throw new TransactionException("活期理财编号已存在！");
				}
				fundDAO.insert(f);
			}else if(FundOperateType.EDIT.equals(fo.getType())){
				//修改
				Fund f = fundDAO.get(fo.getFund());
				if(f != null){
					if(!FundStatus.CHECKED.equals(f.getStatus())){
						throw new TransactionException("活期理财审核状态错误，无法完成操作！");
					}
					Fund newFund = JSONUtils.json2obj(fo.getValue(), Fund.class);
					if(fundDAO.isExistFundByName(newFund.getName(),newFund.getUuid())){
						throw new TransactionException("募集产品名称已存在！");
					}
					if(fundDAO.isExistFundByScode(newFund.getScode(),newFund.getUuid())){
						throw new TransactionException("募集产品编号已存在！");
					}
					
					fo.setOld(JSONUtils.obj2json(f));
					
					f.setName(newFund.getName());
					f.setScode(newFund.getScode());
					f.setShortname(newFund.getShortname());
					
					f.setType(newFund.getType());
					f.setGp(newFund.getGp());
					f.setSetuptime(newFund.getSetuptime());
					f.setPerformanceLevel(newFund.getPerformanceLevel());
					f.setFlagStructured(newFund.getFlagStructured());
					f.setStructuredType(newFund.getStructuredType());
					f.setStructuredRemark(newFund.getStructuredRemark());
					f.setStyle(newFund.getStyle());
					f.setRiskLevel(newFund.getRiskLevel());
					f.setCreditLevel(newFund.getCreditLevel());
					f.setPlaningScale(newFund.getPlaningScale());
					f.setCollectStarttime(newFund.getCollectStarttime());
					f.setCollectEndtime(newFund.getCollectEndtime());
					f.setPurchaseStarttime(newFund.getPurchaseStarttime());
					f.setPurchaseEndtime(newFund.getPurchaseEndtime());
					
					f.setGoal(newFund.getGoal());
					f.setInvestScope(newFund.getInvestScope());
					f.setInvestStaregy(newFund.getInvestStaregy());
					f.setInvestStandard(newFund.getInvestStandard());
					f.setInvestIdea(newFund.getInvestIdea());
					f.setRevenueFeature(newFund.getRevenueFeature());
					f.setRiskManagement(newFund.getRiskManagement());
					
					fundDAO.update(f);
				}else{
					throw new TransactionException("活期理财不存在");
				}
			}else if(FundOperateType.DELETE.equals(fo.getType())){
				//删除
				Fund f = fundDAO.get(fo.getFund());
				if(f != null){
					if(!FundStatus.CHECKED.equals(f.getStatus())){
						throw new TransactionException("活期理财审核状态错误，无法完成操作！");
					}
					f.setStatus(FundStatus.DELETED);
					fundDAO.update(f);
				}else{
					throw new TransactionException("活期理财不存在");
				}
			}
		}
		if(FundOperateStatus.CHECKED.equals(fo.getStatus()) && (fo.getReason() == null || "".equals(fo.getReason()))){
			fo.setReason("审核通过！");
		}else if(FundOperateStatus.UNPASSED.equals(fo.getStatus()) && (fo.getReason() == null || "".equals(fo.getReason()))){
			fo.setReason("审核不通过！");
		}
		fundOperateDAO.update(fo);
	}
	
	/**
	 * 获取募集产品操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return fundOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取募集产品操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return fundOperateDAO.getTypeList(inputParams, resultClass);
	}
}
