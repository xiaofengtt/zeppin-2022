/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDailyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishOperateService;
import cn.zeppin.product.ntb.backadmin.vo.FundPublishOperateDailyVO;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishStatus;
import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class FundPublishOperateService extends BaseService implements IFundPublishOperateService {

	@Autowired
	private IFundPublishOperateDAO fundPublishOperateDAO;
	
	@Autowired
	private IFundPublishDAO fundPublishDAO;
	
	@Autowired
	private IFundPublishDailyDAO fundPublishDailyDAO;
	
	@Override
	public FundPublishOperate insert(FundPublishOperate fundPublishOperate) {
		return fundPublishOperateDAO.insert(fundPublishOperate);
	}

	@Override
	public FundPublishOperate delete(FundPublishOperate fundPublishOperate) {
		fundPublishOperate.setStatus(FundPublishOperateStatus.DELETED);
		return fundPublishOperateDAO.update(fundPublishOperate);
	}

	@Override
	public FundPublishOperate update(FundPublishOperate fundPublishOperate) {
		return fundPublishOperateDAO.update(fundPublishOperate);
	}

	@Override
	public FundPublishOperate get(String uuid) {
		return fundPublishOperateDAO.get(uuid);
	}
	
	/**
	 * ??????????????????????????????(??????????????????),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return fundPublishOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * ??????????????????????????????
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundPublishOperateDAO.getCount(inputParams);
	}
	
	/**
	 * ??????
	 * @param bfppo
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(FundPublishOperate bfppo) throws TransactionException {
		
		//??????????????????????????????
		if(FundPublishOperateStatus.CHECKED.equals(bfppo.getStatus())){
			if(FundPublishOperateType.ADD.equals(bfppo.getType())){
				//??????
				FundPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), FundPublish.class);
				fundPublishDAO.insert(bfpp);
			}else if(FundPublishOperateType.EDIT.equals(bfppo.getType())){
				//??????
				FundPublish fund = fundPublishDAO.get(bfppo.getFundPublish());
				if(fund != null){
					if(!FundPublishStatus.CHECKED.equals(fund.getStatus())){
						throw new TransactionException("??????????????????????????????????????????????????????????????????");
					}
					FundPublish newBfpp = JSONUtils.json2obj(bfppo.getValue(), FundPublish.class);
					bfppo.setOld(JSONUtils.obj2json(fund));
					
					
					fund.setName(newBfpp.getName());
					fund.setScode(newBfpp.getScode());
					fund.setShortname(newBfpp.getShortname());
					
					fund.setType(newBfpp.getType());
					fund.setGp(newBfpp.getGp());
					fund.setSetuptime(newBfpp.getSetuptime());
					fund.setPerformanceLevel(newBfpp.getPerformanceLevel());
					fund.setFlagStructured(newBfpp.getFlagStructured());
					fund.setStructuredType(newBfpp.getStructuredType());
					fund.setStructuredRemark(newBfpp.getStructuredRemark());
					fund.setStyle(newBfpp.getStyle());
					fund.setRiskLevel(newBfpp.getRiskLevel());
					fund.setCreditLevel(newBfpp.getCreditLevel());
					fund.setPlaningScale(newBfpp.getPlaningScale());
					fund.setCollectStarttime(newBfpp.getCollectStarttime());
					fund.setCollectEndtime(newBfpp.getCollectEndtime());
					fund.setPurchaseStarttime(newBfpp.getPurchaseStarttime());
					fund.setPurchaseEndtime(newBfpp.getPurchaseEndtime());
					
					fund.setGoal(newBfpp.getGoal());
					fund.setInvestScope(newBfpp.getInvestScope());
					fund.setInvestStaregy(newBfpp.getInvestStaregy());
					fund.setInvestStandard(newBfpp.getInvestStandard());
					fund.setInvestIdea(newBfpp.getInvestIdea());
					fund.setRevenueFeature(newBfpp.getRevenueFeature());
					fund.setRiskManagement(newBfpp.getRiskManagement());
					
					fundPublishDAO.update(fund);
				}else{
					throw new TransactionException("?????????????????????????????????");
				}
			}else if(FundPublishOperateType.DELETE.equals(bfppo.getType())){
				//??????
				FundPublish bfpp = fundPublishDAO.get(bfppo.getFundPublish());
				if(bfpp != null){
					if(!FundPublishStatus.CHECKED.equals(bfpp.getStatus())){
						throw new TransactionException("??????????????????????????????????????????????????????????????????");
					}
					bfpp.setStatus(FundPublishStatus.DELETED);
					fundPublishDAO.update(bfpp);
				}else{
					throw new TransactionException("?????????????????????????????????");
				}
			}else if(FundPublishOperateType.NETVALUE.equals(bfppo.getType())){
				//????????????
				FundPublish bfp = fundPublishDAO.get(bfppo.getFundPublish());
				if(bfp != null){
					if(!FundPublishStatus.CHECKED.equals(bfp.getStatus())){
						throw new TransactionException("????????????????????????????????????????????????????????????");
					}
					List<FundPublishOperateDailyVO> list = JSONUtils.json2list(bfppo.getValue(), FundPublishOperateDailyVO.class);
					for(FundPublishOperateDailyVO bfpodVO : list){
						if(FundPublishOperateType.ADD.equals(bfpodVO.getType())){//????????????
							if(bfpodVO.getFundPublishDaily() != null){
								Map<String, String> searchMap = new HashMap<String, String>();
								searchMap.put("statistime", bfpodVO.getFundPublishDaily().getStatistime().toString());
								searchMap.put("fundPublish",bfpodVO.getFundPublishDaily().getFundPublish());
								
								List<Entity> eList = this.fundPublishDailyDAO.getListForPage(searchMap, 1, 10, null, FundPublishDaily.class);
								if(eList.size()>0){
									FundPublishDaily bfpd = (FundPublishDaily) eList.get(0);
									bfpd.setNetValue(bfpodVO.getFundPublishDaily().getNetValue());
									bfpd.setCreator(bfpodVO.getFundPublishDaily().getCreator());
								}else{
									fundPublishDailyDAO.insert(bfpodVO.getFundPublishDaily());
								}
							}else{
								throw new TransactionException("??????????????????????????????????????????");
							}
						} else if (FundPublishOperateType.EDIT.equals(bfpodVO.getType())) {
							if(bfpodVO.getFundPublishDaily() != null){
								FundPublishDaily bfpd = this.fundPublishDailyDAO.get(bfpodVO.getFundPublishDaily().getUuid());
								if(bfpd != null){
									bfpd.setNetValue(bfpodVO.getFundPublishDaily().getNetValue());
									bfpd.setCreator(bfpodVO.getFundPublishDaily().getCreator());
									fundPublishDailyDAO.update(bfpd);
								}else{
									throw new TransactionException("?????????????????????????????????????????????");
								}
							}else{
								throw new TransactionException("??????????????????????????????????????????");
							}
						} else if (FundPublishOperateType.DELETE.equals(bfpodVO.getType())) {
							if(bfpodVO.getFundPublishDaily() != null){
								FundPublishDaily bfpd = this.fundPublishDailyDAO.get(bfpodVO.getFundPublishDaily().getUuid());
								if(bfpd != null){
									fundPublishDailyDAO.delete(bfpd);
								}else{
									throw new TransactionException("?????????????????????????????????????????????");
								}
							}else{
								throw new TransactionException("??????????????????????????????????????????");
							}
						} else {
							throw new TransactionException("??????????????????????????????????????????????????????");
						}
					}
				}else{
					throw new TransactionException("???????????????????????????????????????????????????");
				}
			}
		}
		if(FundPublishOperateStatus.CHECKED.equals(bfppo.getStatus()) && (bfppo.getReason() == null || "".equals(bfppo.getReason()))){
			bfppo.setReason("???????????????");
		}else if(FundPublishOperateStatus.UNPASSED.equals(bfppo.getStatus()) && (bfppo.getReason() == null || "".equals(bfppo.getReason()))){
			bfppo.setReason("??????????????????");
		}
		fundPublishOperateDAO.update(bfppo);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return fundPublishOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return fundPublishOperateDAO.getTypeList(inputParams, resultClass);
	}
}
