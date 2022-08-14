/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserBet;
import cn.zeppin.product.score.entity.FrontUserBet.FrontUserBetStatus;
import cn.zeppin.product.score.entity.FrontUserBetDetail;
import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.entity.GuessingMatchOdds.GuessingMatchOddsStatus;
import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.entity.SystemParam.SystemParamKey;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserBetDetailService;
import cn.zeppin.product.score.service.FrontUserBetService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.GuessingMatchOddsService;
import cn.zeppin.product.score.service.GuessingMatchService;
import cn.zeppin.product.score.service.GuessingMatchTypeService;
import cn.zeppin.product.score.service.InfoMatchService;
import cn.zeppin.product.score.service.MobileCodeService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.SystemParamService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Base64Util;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.front.FrontUserBetDetailVO;
import cn.zeppin.product.score.vo.front.FrontUserBetVO;

/**
 * 用户竞猜信息
 */

@Controller
@RequestMapping(value = "/front/userBet")
public class FrontUserBetController extends BaseController{
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
	private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private InfoMatchService infoMatchService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MobileCodeService mobileCodeService;
	
	@Autowired
	private SystemParamService systemParamService;
	
	@Autowired
	private GuessingMatchOddsService guessingMatchOddsService;

	@Autowired
	private GuessingMatchService guessingMatchService;
	
	@Autowired
	private GuessingMatchTypeService guessingMatchTypeService;

	@Autowired
	private FrontUserBetService frontUserBetService;

	@Autowired
	private FrontUserBetDetailService frontUserBetDetailService;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, Integer pageNum, Integer pageSize, String sort, HttpServletRequest request){
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frontUser", fu.getUuid());
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			params.put("status", status);
		}
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = frontUserBetService.getCountByParams(params);
		List<FrontUserBet> fubList = frontUserBetService.getListByParams(params);
		
		List<FrontUserBetVO> voList = new ArrayList<FrontUserBetVO>();
		for(FrontUserBet fub : fubList){
			FrontUserBetVO fubvo = new FrontUserBetVO(fub);
			voList.add(fubvo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid, HttpServletRequest request){
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		
		FrontUserBet fub = frontUserBetService.get(uuid);
		if(fub == null){
			return ResultManager.createFailResult("投注不存在！");
		}
		if(!fub.getFrontUser().equals(fu.getUuid())){
			return ResultManager.createFailResult("投注记录有误！");
		}
		FrontUserBetVO fubvo = new FrontUserBetVO(fub);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frontUserBet", uuid);
		List<FrontUserBetDetail> fubdList = this.frontUserBetDetailService.getListByParams(params);
		
		List<FrontUserBetDetailVO> fubvoList = new ArrayList<FrontUserBetDetailVO>();
		for(FrontUserBetDetail fubd : fubdList){
			FrontUserBetDetailVO fubdvo = new FrontUserBetDetailVO(fubd);
			
			GuessingMatch gm = this.guessingMatchService.get(fubd.getGuessingMatch());
			if(gm != null){
				InfoMatch im = this.infoMatchService.get(gm.getInfoMatch());
				if(im != null){
					fubdvo.setTime(im.getTime());
					fubdvo.setFinalResult(im.getFinalresult());
					Category category = this.categoryService.get(im.getCategory());
					if(category != null){
						fubdvo.setCategoryName(category.getName());
					}
					
					Team hometeam = this.teamService.get(im.getHometeam());
					if(hometeam != null){
						fubdvo.setHometeamName(hometeam.getName());
					}
					
					Team awayteam = this.teamService.get(im.getAwayteam());
					if(awayteam != null){
						fubdvo.setAwayteamName(awayteam.getName());
					}
				}
			}
			
			GuessingMatchOdds gmo = this.guessingMatchOddsService.get(fubd.getGuessingMatchOdds());
			if(gmo != null){
				fubdvo.setSpread(gmo.getSpread());
				fubdvo.setResult(gmo.getResult());
				fubdvo.setIsRight(gmo.getIsRight());
			}
			
			fubvoList.add(fubdvo);
		}
		fubvo.setDetailList(fubvoList);
		
		return ResultManager.createDataResult(fubvo);
	}
	
	
	/**
	 * 竞猜投注
	 * @param bet 
	 * @param price
	 * @param detail 
	 * @return
	 */
	@RequestMapping(value = "/bet", method = RequestMethod.POST)
	@ActionParam(key = "bet", type = DataType.STRING, message = "投注方式", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "投注总额", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "投注类型", required = true)
	@ActionParam(key = "details", type = DataType.STRING_ARRAY, message = "投注详情", required = true)
	@ResponseBody
	public Result bet(String bet, String price, String type, String[] details, HttpServletRequest request){
		if(details.length == 0){
			return ResultManager.createFailResult("未选择投注场次!");
		}
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency(price)) {
			return ResultManager.createFailResult("投注总额输入错误!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));

		String[] betTypes = bet.split("@_@");
		if(betTypes.length == 0){
			return ResultManager.createFailResult("未选择投注方式!");
		}
		
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fu.getUuid());
		if(fua == null){
			return ResultManager.createFailResult("用户账户状态异常， 请联系管理员！");
		}
		
		if(fua.getBalanceFree().compareTo(pay) < 0){
			return ResultManager.createFailResult("用户账户余额不足， 请先充值！");
		}
		
		SystemParam monovalentParam = this.systemParamService.get(SystemParamKey.BET_MONOVALENT);
		BigDecimal monovalent = new BigDecimal(monovalentParam.getParamValue());
		
		Map<String, Integer> betMap = new HashMap<String, Integer>();
		for(String betTypeString : betTypes) {
			String[] betInfo = betTypeString.split(":");
			if(!Utlity.isNumeric(betInfo[0]) || !Utlity.isNumeric(betInfo[1])) {
				return ResultManager.createFailResult("投注信息错误!");
			}
			String betType = betInfo[0];
			Integer betMutiple = Integer.valueOf(betInfo[1]);
			betMap.put(betType, betMutiple);
		}
		
		FrontUserBet fub = new FrontUserBet();
		fub.setUuid(UUID.randomUUID().toString());
		fub.setFrontUser(fu.getUuid());
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
				return ResultManager.createFailResult("数据异常，请刷新页面重新投注！");
			}
			
			GuessingMatchOdds gmo = this.guessingMatchOddsService.get(detail[0]);
			Boolean flagCorrent = Boolean.valueOf(detail[1]);
			if(gmo == null){
				return ResultManager.createFailResult("数据异常，请刷新页面重新投注！");
			}
			
			if(!GuessingMatchOddsStatus.NORMAL.equals(gmo.getStatus())) {
				return ResultManager.createFailResult("赔率已变更，请刷新页面重新确认！");
			}
			
			FrontUserBetDetail fubd = new FrontUserBetDetail();
			fubd.setUuid(UUID.randomUUID().toString());
			fubd.setFrontUserBet(fub.getUuid());
			fubd.setGuessingMatchOdds(gmo.getUuid());
			fubd.setGuessingMatch(gmo.getGuessingMatch());
			fubd.setFlagCorrect(flagCorrent);
			fubd.setOdds(gmo.getOdds());
			fubdList.add(fubd);
			
			GuessingMatch gm = this.guessingMatchService.get(gmo.getGuessingMatch());
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
					return ResultManager.createFailResult("数据异常，请刷新页面重新投注！");
				}
				
				matchDetailList.add(fubd);
			}else if(notCorrentMatchMap.get(gm.getUuid()) != null){
				List<FrontUserBetDetail> matchDetailList = notCorrentMatchMap.get(gm.getUuid());
				
				if(matchDetailList.get(0).getFlagCorrect() != flagCorrent){
					return ResultManager.createFailResult("数据异常，请刷新页面重新投注！");
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
				return ResultManager.createFailResult("数据异常，请刷新页面重新投注！");
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
			return ResultManager.createFailResult("数据异常，请刷新页面重新投注！");
		}
		fub.setCompose(JSONUtils.obj2json(totalComposeList));
		
		this.frontUserBetService.bet(fua, fub, fubdList);
		
		return ResultManager.createSuccessResult("操作成功！");
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
