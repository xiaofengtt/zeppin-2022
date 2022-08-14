package cn.product.score.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserBetDao;
import cn.product.score.dao.FrontUserBetDetailDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.GuessingMatchDao;
import cn.product.score.dao.GuessingMatchOddsDao;
import cn.product.score.dao.GuessingMatchTypeDao;
import cn.product.score.dao.InfoMatchDao;
import cn.product.score.dao.MobileCodeDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.SystemParamDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Category;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserBet;
import cn.product.score.entity.FrontUserBet.FrontUserBetStatus;
import cn.product.score.entity.FrontUserBetDetail;
import cn.product.score.entity.GuessingMatch;
import cn.product.score.entity.GuessingMatchOdds;
import cn.product.score.entity.GuessingMatchOdds.GuessingMatchOddsStatus;
import cn.product.score.entity.InfoMatch;
import cn.product.score.entity.SystemParam;
import cn.product.score.entity.SystemParam.SystemParamKey;
import cn.product.score.entity.Team;
import cn.product.score.service.front.FrontUserBetService;
import cn.product.score.util.Base64Util;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;
import cn.product.score.vo.front.FrontUserBetDetailVO;
import cn.product.score.vo.front.FrontUserBetVO;

@Service("frontUserBetService")
public class FrontUserBetServiceImpl implements FrontUserBetService{
	
	@Autowired
	private FrontUserBetDao frontUserBetDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private CategoryDao categoryDao;
	
	@Autowired
    private InfoMatchDao infoMatchDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private GuessingMatchOddsDao guessingMatchOddsDao;

	@Autowired
	private GuessingMatchDao guessingMatchDao;
	
	@Autowired
	private GuessingMatchTypeDao guessingMatchTypeDao;

	@Autowired
	private FrontUserBetDetailDao frontUserBetDetailDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("frontUser", fu);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			paramsls.put("status", status);
		}
		paramsls.put("pageNum", pageNum);
		paramsls.put("pageSize", pageSize);
		paramsls.put("sort", sort);
		
		Integer totalCount = frontUserBetDao.getCountByParams(paramsls);
		List<FrontUserBet> fubList = frontUserBetDao.getListByParams(paramsls);
		
		List<FrontUserBetVO> voList = new ArrayList<FrontUserBetVO>();
		for(FrontUserBet fub : fubList){
			FrontUserBetVO fubvo = new FrontUserBetVO(fub);
			voList.add(fubvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		
		FrontUserBet fub = frontUserBetDao.get(uuid);
		if(fub == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("投注不存在！");
			return;
		}
		if(!fub.getFrontUser().equals(fu)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("投注记录有误！");
			return;
		}
		FrontUserBetVO fubvo = new FrontUserBetVO(fub);
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("frontUserBet", uuid);
		List<FrontUserBetDetail> fubdList = this.frontUserBetDetailDao.getListByParams(paramsls);
		
		List<FrontUserBetDetailVO> fubvoList = new ArrayList<FrontUserBetDetailVO>();
		for(FrontUserBetDetail fubd : fubdList){
			FrontUserBetDetailVO fubdvo = new FrontUserBetDetailVO(fubd);
			
			GuessingMatch gm = this.guessingMatchDao.get(fubd.getGuessingMatch());
			if(gm != null){
				InfoMatch im = this.infoMatchDao.get(gm.getInfoMatch());
				if(im != null){
					fubdvo.setTime(im.getTime());
					fubdvo.setFinalResult(im.getFinalresult());
					Category category = this.categoryDao.get(im.getCategory());
					if(category != null){
						fubdvo.setCategoryName(category.getName());
					}
					
					Team hometeam = this.teamDao.get(im.getHometeam());
					if(hometeam != null){
						fubdvo.setHometeamName(hometeam.getName());
					}
					
					Team awayteam = this.teamDao.get(im.getAwayteam());
					if(awayteam != null){
						fubdvo.setAwayteamName(awayteam.getName());
					}
				}
			}
			
			GuessingMatchOdds gmo = this.guessingMatchOddsDao.get(fubd.getGuessingMatchOdds());
			if(gmo != null){
				fubdvo.setSpread(gmo.getSpread());
				fubdvo.setResult(gmo.getResult());
				fubdvo.setIsRight(gmo.getIsRight());
			}
			
			fubvoList.add(fubdvo);
		}
		fubvo.setDetailList(fubvoList);
		
		result.setData(fubvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void bet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		String bet = paramsMap.get("bet") == null ? "" : paramsMap.get("bet").toString();
		String price = paramsMap.get("price") == null ? "" : paramsMap.get("price").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String[] details = (String[]) paramsMap.get("details");
		
		if(details.length == 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("未选择投注场次!");
			return;
		}
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency(price)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("投注总额输入错误!");
			return;
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));

		String[] betTypes = bet.split("@_@");
		if(betTypes.length == 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("未选择投注方式!");
			return;
		}
		
		
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fu);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户状态异常， 请联系管理员！");
			return;
		}
		
		if(fua.getBalanceFree().compareTo(pay) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户余额不足， 请先充值！");
			return;
		}
		
		SystemParam monovalentParam = this.systemParamDao.get(SystemParamKey.BET_MONOVALENT);
		BigDecimal monovalent = new BigDecimal(monovalentParam.getParamValue());
		
		Map<String, Integer> betMap = new HashMap<String, Integer>();
		for(String betTypeString : betTypes) {
			String[] betInfo = betTypeString.split(":");
			if(!Utlity.isNumeric(betInfo[0]) || !Utlity.isNumeric(betInfo[1])) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("投注信息错误!");
				return;
			}
			String betType = betInfo[0];
			Integer betMutiple = Integer.valueOf(betInfo[1]);
			betMap.put(betType, betMutiple);
		}
		
		FrontUserBet fub = new FrontUserBet();
		fub.setUuid(UUID.randomUUID().toString());
		fub.setFrontUser(fu);
		fub.setType(type);
		fub.setPrice(pay);
		fub.setMonovalent(monovalent);
		fub.setBet(JSONUtils.obj2json(betMap));
		fub.setStatus(FrontUserBetStatus.NORMAL);
		fub.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		Timestamp lastTime = new Timestamp(0);
		List<FrontUserBetDetail> fubdList = new ArrayList<FrontUserBetDetail>();
		Map<String, List<FrontUserBetDetail>> correntMatchMap = new HashMap<String, List<FrontUserBetDetail>>();
		Map<String, List<FrontUserBetDetail>> notCorrentMatchMap = new HashMap<String, List<FrontUserBetDetail>>();
		for(String detailString : details) {
			String[] detail = detailString.split("@_@");
			if(detail.length != 2){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("数据异常，请刷新页面重新投注！");
				return;
			}
			
			GuessingMatchOdds gmo = this.guessingMatchOddsDao.get(detail[0]);
			Boolean flagCorrent = Boolean.valueOf(detail[1]);
			if(gmo == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("数据异常，请刷新页面重新投注！");
				return;
			}
			
			if(!GuessingMatchOddsStatus.NORMAL.equals(gmo.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("赔率已变更，请刷新页面重新确认！");
				return;
			}
			
			FrontUserBetDetail fubd = new FrontUserBetDetail();
			fubd.setUuid(UUID.randomUUID().toString());
			fubd.setFrontUserBet(fub.getUuid());
			fubd.setGuessingMatchOdds(gmo.getUuid());
			fubd.setGuessingMatch(gmo.getGuessingMatch());
			fubd.setFlagCorrect(flagCorrent);
			fubd.setOdds(gmo.getOdds());
			fubdList.add(fubd);
			
			GuessingMatch gm = this.guessingMatchDao.get(gmo.getGuessingMatch());
			if(gm.getTime().after(lastTime)){
				lastTime = gm.getTime();
			}
			
			if(correntMatchMap.get(gm.getUuid()) == null && notCorrentMatchMap.get(gm.getUuid()) == null){
				List<FrontUserBetDetail> newMatchDetailList = new ArrayList<FrontUserBetDetail>();
				newMatchDetailList.add(fubd);
				if(flagCorrent){
					correntMatchMap.put(gm.getUuid(), newMatchDetailList);
				}else{
					notCorrentMatchMap.put(gm.getUuid(), newMatchDetailList);
				}
			}else if(correntMatchMap.get(gm.getUuid()) != null){
				List<FrontUserBetDetail> matchDetailList = correntMatchMap.get(gm.getUuid());
				
				if(matchDetailList.get(0).getFlagCorrect() != flagCorrent){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("数据异常，请刷新页面重新投注！");
					return;
				}
				
				matchDetailList.add(fubd);
			}else if(notCorrentMatchMap.get(gm.getUuid()) != null){
				List<FrontUserBetDetail> matchDetailList = notCorrentMatchMap.get(gm.getUuid());
				
				if(matchDetailList.get(0).getFlagCorrect() != flagCorrent){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("数据异常，请刷新页面重新投注！");
					return;
				}
				
				matchDetailList.add(fubd);
			}
		}
		fub.setLastTime(lastTime);
		
		List<List<String>> totalComposeList = new ArrayList<List<String>>();
		BigDecimal totalCount = new BigDecimal(totalComposeList.size());
		for(String typeName : betMap.keySet()){
			BigDecimal typeMultiple = new BigDecimal(betMap.get(typeName));
			int typeMode = Integer.valueOf(typeName);
			if(typeMode < correntMatchMap.keySet().size()){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("数据异常，请刷新页面重新投注！");
				return;
			}
			
			if(typeMode == 1){
				List<List<String>> composeList = new ArrayList<List<String>>();
				for(List<FrontUserBetDetail> detailList : correntMatchMap.values()){
					for(FrontUserBetDetail fubd :detailList){
						List<String> compose = new ArrayList<String>();
						compose.add(fubd.getUuid());
						composeList.add(compose);
					}
				}
				for(List<FrontUserBetDetail> detailList : notCorrentMatchMap.values()){
					for(FrontUserBetDetail fubd :detailList){
						List<String> compose = new ArrayList<String>();
						compose.add(fubd.getUuid());
						composeList.add(compose);
					}
				}
				totalComposeList.addAll(composeList);
				totalCount = totalCount.add((new BigDecimal(composeList.size())).multiply(typeMultiple));
			}else{
				List<List<String>> correntComposeList = new ArrayList<List<String>>();
				List<List<String>> notCorrentComposeList = new ArrayList<List<String>>();
				
				Object[] correntKeyArray = correntMatchMap.keySet().toArray();
				for(int i=0 ; i<correntKeyArray.length ; i++){
					List<List<String>> newComposeList = new ArrayList<List<String>>();
					
					List<FrontUserBetDetail> detailList = correntMatchMap.get(correntKeyArray[i].toString());
					if(i==0){
						for(FrontUserBetDetail fubd :detailList){
							List<String> newCompose = new ArrayList<String>();
							newCompose.add(fubd.getUuid());
							newComposeList.add(newCompose);
						}
					}else{
						for(FrontUserBetDetail fubd :detailList){
							for(List<String> compose : correntComposeList){
								List<String> newCompose = new ArrayList<String>();
								newCompose.addAll(compose);
								newCompose.add(fubd.getUuid());
								newComposeList.add(newCompose);
							}
						}
					}
					correntComposeList = newComposeList;
				}
				
				int notCorrentCount = typeMode - correntMatchMap.keySet().size();
				List<FrontUserBetDetail> notCorrentList= new ArrayList<FrontUserBetDetail>();
				for(List<FrontUserBetDetail> composes : notCorrentMatchMap.values()){
					for(FrontUserBetDetail fubd : composes){
						notCorrentList.add(fubd);
					}
				}
				
				List<List<FrontUserBetDetail>> notCorrentDetailList = new ArrayList<List<FrontUserBetDetail>>();
				combinerSelect(notCorrentList, new ArrayList<FrontUserBetDetail>(), notCorrentCount, notCorrentDetailList);
				
				for(int i=0; i<notCorrentDetailList.size(); i++){
					List<FrontUserBetDetail> composes = notCorrentDetailList.get(i);
					Map<String, FrontUserBetDetail> matchMap = new HashMap<String, FrontUserBetDetail>();
					for(FrontUserBetDetail fubd : composes){
						if(matchMap.get(fubd.getGuessingMatch()) != null){
							notCorrentDetailList.remove(i);
							i--;
							break;
						}
						matchMap.put(fubd.getGuessingMatch(), fubd);
					}
				}
				
				for(List<FrontUserBetDetail> composes : notCorrentDetailList){
					List<String> uuidList = new ArrayList<String>();
					for(FrontUserBetDetail fubd : composes){
						uuidList.add(fubd.getUuid());
					}
					notCorrentComposeList.add(uuidList);
				}
				
				List<List<String>> typeComposeList = new ArrayList<List<String>>();
				for(List<String> composeList : correntComposeList){
					for(List<String> notComposeList : notCorrentComposeList){
						List<String> typeCompse = new ArrayList<String>();
						typeCompse.addAll(composeList);
						typeCompse.addAll(notComposeList);
						typeComposeList.add(typeCompse);
					}
				}
				totalComposeList.addAll(typeComposeList);
				totalCount = totalCount.add((new BigDecimal(typeComposeList.size())).multiply(typeMultiple));
			}
		}
		
		if(monovalent.multiply(totalCount).compareTo(pay) != 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("数据异常，请刷新页面重新投注！");
			return;
		}
		fub.setCompose(JSONUtils.obj2json(totalComposeList));
		
		this.frontUserBetDao.bet(fua, fub, fubdList);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	public static void combinerSelect(List<FrontUserBetDetail> data, List<FrontUserBetDetail> workSpace, int k, List<List<FrontUserBetDetail>> result) {
		List<FrontUserBetDetail> copyData;
		List<FrontUserBetDetail> copyWorkSpace;
		
		if(workSpace.size() == k) {
			List<FrontUserBetDetail> single = new ArrayList<FrontUserBetDetail>();
			for(FrontUserBetDetail c : workSpace){
				single.add(c);
			}
			result.add(single);
		}
		
		for(int i = 0; i < data.size(); i++) {
			copyData = new ArrayList<FrontUserBetDetail>(data);
			copyWorkSpace = new ArrayList<FrontUserBetDetail>(workSpace);
			
			copyWorkSpace.add(copyData.get(i));
			for(int j = i; j >=  0; j--){
				copyData.remove(j);
			}
			combinerSelect(copyData, copyWorkSpace, k, result);
		}
	}
}
