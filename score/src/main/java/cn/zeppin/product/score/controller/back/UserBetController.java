package cn.zeppin.product.score.controller.back;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserBet;
import cn.zeppin.product.score.entity.FrontUserBet.FrontUserBetStatus;
import cn.zeppin.product.score.entity.FrontUserBetDetail;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserBetDetailService;
import cn.zeppin.product.score.service.FrontUserBetService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.GuessingMatchOddsService;
import cn.zeppin.product.score.service.GuessingMatchService;
import cn.zeppin.product.score.service.GuessingMatchTypeService;
import cn.zeppin.product.score.service.InfoMatchService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.FrontUserBetDetailVO;
import cn.zeppin.product.score.vo.back.FrontUserBetVO;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Controller
@RequestMapping(value = "/back/userBet")
public class UserBetController extends BaseController{
	
	@Autowired
    private FrontUserBetService frontUserBetService;
	
	@Autowired
    private FrontUserBetDetailService frontUserBetDetailService;
	
	@Autowired
    private FrontUserService frontUserService;
	
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
    private GuessingMatchService guessingMatchService;
	
	@Autowired
    private GuessingMatchTypeService guessingMatchTypeService;
	
	@Autowired
    private GuessingMatchOddsService guessingMatchOddsService;
	
	@Autowired
    private AdminService adminService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		FrontUserBet fub = frontUserBetService.get(uuid);
		if(fub == null){
			return ResultManager.createFailResult("投注信息不存在！");
		}
		
		FrontUserBetVO fubvo = new FrontUserBetVO(fub);
		
		FrontUser fu = this.frontUserService.get(fub.getFrontUser());
		if(fu != null){
			fubvo.setFrontUserName(fu.getRealname());
			fubvo.setFrontUserMobile(fu.getMobile());
		}

		if(fub.getChecker() != null){
			Admin checker = this.adminService.get(fub.getChecker());
			if(checker != null){
				fubvo.setCheckerName(checker.getRealname());
			}
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserBet", fub.getUuid());
		List<FrontUserBetDetail> fubdList = this.frontUserBetDetailService.getListByParams(searchMap);
		
		List<FrontUserBetDetailVO> fudbvoList = new ArrayList<FrontUserBetDetailVO>();
		for(FrontUserBetDetail fubd : fubdList){
			FrontUserBetDetailVO fubdvo = new FrontUserBetDetailVO(fubd);
			
			GuessingMatch gm = this.guessingMatchService.get(fubd.getGuessingMatch());
			if(gm != null){
				InfoMatch im = this.infoMatchService.get(gm.getInfoMatch());
				if(im != null){
					fubdvo.setTime(im.getTime());
					
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
			}
			
			fudbvoList.add(fubdvo);
		}
		
		fubvo.setDetailList(fudbvoList);
		
		return ResultManager.createDataResult(fubvo);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = frontUserBetService.getCountByParams(params);
		List<FrontUserBet> fubList = frontUserBetService.getListByParams(params);
		
		List<FrontUserBetVO> voList = new ArrayList<FrontUserBetVO>();
		for(FrontUserBet fub : fubList){
			FrontUserBetVO fubvo = new FrontUserBetVO(fub);
			
			FrontUser fu = this.frontUserService.get(fub.getFrontUser());
			if(fu != null){
				fubvo.setFrontUserName(fu.getRealname());
				fubvo.setFrontUserMobile(fu.getMobile());
			}

			if(fub.getChecker() != null){
				Admin checker = this.adminService.get(fub.getChecker());
				if(checker != null){
					fubvo.setCheckerName(checker.getRealname());
				}
			}
			
			voList.add(fubvo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/confirm",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result confirm(String uuid){
		Admin admin = this.getCurrentOperator();
		
		FrontUserBet fub = frontUserBetService.get(uuid);
		if(fub == null){
			return ResultManager.createFailResult("投注不存在！");
		}
		
		if(!FrontUserBetStatus.NORMAL.equals(fub.getStatus())){
			return ResultManager.createFailResult("投注状态异常！");
		}
		
		fub.setChecker(admin.getUuid());
		fub.setChecktime(new Timestamp(System.currentTimeMillis()));
		this.frontUserBetService.comfirmFrontUserBet(fub);
		
		return ResultManager.createSuccessResult("确认成功！");
	}
	
	@RequestMapping(value="/refund",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result refund(String uuid){
		Admin admin = this.getCurrentOperator();
		
		FrontUserBet fub = frontUserBetService.get(uuid);
		if(fub == null){
			return ResultManager.createFailResult("投注不存在！");
		}
		
		if(!FrontUserBetStatus.NORMAL.equals(fub.getStatus())){
			return ResultManager.createFailResult("投注状态异常！");
		}
		
		fub.setChecker(admin.getUuid());
		fub.setChecktime(new Timestamp(System.currentTimeMillis()));
		this.frontUserBetService.refundFrontUserBet(fub);
		
		return ResultManager.createSuccessResult("退款成功！");
	}
	
	/**
	 * 用户投注开奖
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/lottery", method = RequestMethod.POST)
	@ResponseBody
	public Result lottery() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", FrontUserBetStatus.CONFIRM);
		params.put("lastTime", Utlity.timestampFormat(new Timestamp(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
		List<FrontUserBet> fubList = this.frontUserBetService.getListByParams(params);
		
		Map<String, Boolean> oddsResultMap = new HashMap<String, Boolean>();
		for(FrontUserBet fub : fubList){
			Map<String, Object> betMap = JSONUtils.json2map(fub.getBet());
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUserBet", fub.getUuid());
			
			List<FrontUserBetDetail> fubdList = this.frontUserBetDetailService.getListByParams(searchMap);
			Map<String, Boolean> detailResultMap = new HashMap<String, Boolean>();
			Map<String, BigDecimal> detailOddsMap = new HashMap<String, BigDecimal>();
			for(FrontUserBetDetail fubd : fubdList){
				if(oddsResultMap.get(fubd.getGuessingMatchOdds()) == null){
					GuessingMatchOdds gmo = this.guessingMatchOddsService.get(fubd.getGuessingMatchOdds());
					detailOddsMap.put(fubd.getUuid(), gmo.getOdds());
					if(gmo.getIsRight() == null){
						return ResultManager.createFailResult("有已结束的比赛尚未确认结果！");
					}
					oddsResultMap.put(gmo.getUuid(), gmo.getIsRight());
				}
				
				detailResultMap.put(fubd.getUuid(), oddsResultMap.get(fubd.getGuessingMatchOdds()));
			}
			
			List<List> composeList = JSONUtils.json2list(fub.getCompose(), List.class);
			BigDecimal award = BigDecimal.ZERO;
			for(List<String> singleList : composeList){
				Boolean flag = true;
				BigDecimal odds = BigDecimal.ONE;
				for(String uuid : singleList){
					if(!detailResultMap.get(uuid)){
						flag = false;
						break;
					}
					odds = odds.multiply(detailOddsMap.get(uuid));
				}
				if(flag){
					BigDecimal betNum = BigDecimal.valueOf(Integer.valueOf(betMap.get(singleList.size() + "").toString()));
					BigDecimal singleAward = fub.getMonovalent().multiply(odds);
					
					award = award.add(singleAward.multiply(betNum).setScale(2, BigDecimal.ROUND_FLOOR));
				}
			}
			
			fub.setAward(award);
			fub.setStatus(FrontUserBetStatus.FINISHED);
			
			if(fub.getAward().compareTo(BigDecimal.ZERO) > 0){
				FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fub.getFrontUser());
				
				FrontUserHistory fuh = new FrontUserHistory();
				fuh.setUuid(UUID.randomUUID().toString());
				fuh.setFrontUser(fub.getFrontUser());
				fuh.setFrontUserBet(fub.getUuid());
				fuh.setFrontUserAccount(fua.getUuid());
				fuh.setPay(BigDecimal.ZERO);
				fuh.setIncome(fub.getAward());
				fuh.setPoundage(BigDecimal.ZERO);
				fuh.setBalance(fua.getBalanceFree());
				fuh.setType(FrontUserHistoryType.USER_BET_AWARD);
				fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
				fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
				
				fua.setBalanceFree(fua.getBalanceFree().add(fub.getAward()));
				this.frontUserBetService.lottery(fua, fub, fuh);
			}else{
				this.frontUserBetService.update(fub);
			}
		}
		
		return ResultManager.createSuccessResult("操作成功！");
	}
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<StatusCountVO> list = frontUserBetService.getStatusList();
		return ResultManager.createDataResult(list,list.size());
	}
}
