package cn.zeppin.product.score.controller.back;

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
import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.entity.GuessingMatch.GuessingMatchStatus;
import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.entity.GuessingMatchType;
import cn.zeppin.product.score.entity.GuessingMatchType.GuessingMatchTypeStatus;
import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.GuessingMatchOddsService;
import cn.zeppin.product.score.service.GuessingMatchService;
import cn.zeppin.product.score.service.GuessingMatchTypeService;
import cn.zeppin.product.score.service.InfoMatchService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.GuessingMatchVO;
import cn.zeppin.product.score.vo.back.MatchVO;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Controller
@RequestMapping(value = "/back/guessingMatch")
public class GuessingMatchController extends BaseController{
	
	@Autowired
    private GuessingMatchService guessingMatchService;
	
	@Autowired
    private GuessingMatchTypeService guessingMatchTypeService;
	
	@Autowired
    private GuessingMatchOddsService guessingMatchOddsService;
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private InfoMatchService infoMatchService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
    private AdminService adminService;

	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		GuessingMatch gm = guessingMatchService.get(uuid);
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			return ResultManager.createFailResult("赛事不存在！");
		}
		
		GuessingMatchVO gmvo = new GuessingMatchVO(gm);
		
		InfoMatch match = this.infoMatchService.get(gm.getInfoMatch());
		if(match != null){
			MatchVO matchVO = new MatchVO(match);
			
			Category cat = this.categoryService.get(match.getCategory());
			matchVO.setCategoryName(cat.getName());
			
			Team homeTeam = this.teamService.get(match.getHometeam());
			matchVO.setHometeamName(homeTeam.getName());
			if(!Utlity.checkStringNull(homeTeam.getIcon())){
				Resource resource = this.resourceService.get(homeTeam.getIcon());
				if(resource != null){
					matchVO.setHometeamIconUrl(resource.getUrl());
				}
			}
			
			Team awayTeam = this.teamService.get(match.getAwayteam());
			matchVO.setAwayteamName(awayTeam.getName());
			if(!Utlity.checkStringNull(awayTeam.getIcon())){
				Resource resource = this.resourceService.get(awayTeam.getIcon());
				if(resource != null){
					matchVO.setAwayteamIconUrl(resource.getUrl());
				}
			}
			gmvo.setMatchDetail(matchVO);
		}
		
		Admin creator = this.adminService.get(gm.getCreator());
		if(creator != null){
			gmvo.setCreator(creator.getRealname());
		}
		
		return ResultManager.createDataResult(gmvo);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			params.put("status", status);
		}
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = guessingMatchService.getCountByParams(params);
		List<GuessingMatch> gmList = guessingMatchService.getListByParams(params);
		
		List<GuessingMatchVO> voList = new ArrayList<GuessingMatchVO>();
		for(GuessingMatch gm : gmList){
			GuessingMatchVO gmvo = new GuessingMatchVO(gm);
			
			InfoMatch match = this.infoMatchService.get(gm.getInfoMatch());
			if(match != null){
				MatchVO matchVO = new MatchVO(match);
				
				Category cat = this.categoryService.get(match.getCategory());
				matchVO.setCategoryName(cat.getName());
				
				Team homeTeam = this.teamService.get(match.getHometeam());
				matchVO.setHometeamName(homeTeam.getName());
				if(!Utlity.checkStringNull(homeTeam.getIcon())){
					Resource resource = this.resourceService.get(homeTeam.getIcon());
					if(resource != null){
						matchVO.setHometeamIconUrl(resource.getUrl());
					}
				}
				
				Team awayTeam = this.teamService.get(match.getAwayteam());
				matchVO.setAwayteamName(awayTeam.getName());
				if(!Utlity.checkStringNull(awayTeam.getIcon())){
					Resource resource = this.resourceService.get(awayTeam.getIcon());
					if(resource != null){
						matchVO.setAwayteamIconUrl(resource.getUrl());
					}
				}
				gmvo.setMatchDetail(matchVO);
			}
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("guessingMatch", match.getUuid());
			List<GuessingMatchType> gmtList = this.guessingMatchTypeService.getListByParams(params);
			Map<String, Object> guessingTypeMap = new HashMap<String, Object>();
			for(GuessingMatchType gmt : gmtList){
				guessingTypeMap.put(gmt.getType(), true);
			}
			gmvo.setGuessingTypeArray(guessingTypeMap.keySet().toArray());
			
			Admin creator = this.adminService.get(gm.getCreator());
			if(creator != null){
				gmvo.setCreator(creator.getRealname());
			}
			voList.add(gmvo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "infoMatch", message="赛事", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER)
	@ResponseBody
    public Result add(GuessingMatch guessingMatch){
		Admin admin = this.getCurrentOperator();
		
		InfoMatch im = this.infoMatchService.get(guessingMatch.getInfoMatch());
		if(im == null){
			return ResultManager.createFailResult("赛事不存在！");
		}
		
		guessingMatch.setUuid(UUID.randomUUID().toString());
		guessingMatch.setTime(im.getTime());
		guessingMatch.setCategory(im.getCategory());
		guessingMatch.setStatus(GuessingMatchStatus.NORMAL);
		guessingMatch.setSort(0);
		guessingMatch.setCreator(admin.getUuid());
		guessingMatch.setCreatetime(new Timestamp(System.currentTimeMillis()));
		guessingMatchService.insert(guessingMatch);
		return ResultManager.createDataResult(guessingMatch);
    }
    
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid){
		GuessingMatch gm = guessingMatchService.get(uuid);
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			return ResultManager.createFailResult("赛事不存在！");
		}
		
		if(!GuessingMatchStatus.NORMAL.equals(gm.getStatus())){
			return ResultManager.createFailResult("赛事无法删除！");
		}
		
		gm.setStatus(GuessingMatchStatus.DELETE);
		this.guessingMatchService.deleteGuessingMatch(gm);
		return ResultManager.createSuccessResult("删除成功！");
	}
	
	@RequestMapping(value="/finish",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "rightArray", message="正确选项", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result finish(String uuid, String[] rightArray){
		Admin admin = this.getCurrentOperator();
		
		GuessingMatch gm = guessingMatchService.get(uuid);
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			return ResultManager.createFailResult("竞猜赛事不存在！");
		}
		
		if(!GuessingMatchStatus.WAITING.equals(gm.getStatus())){
			return ResultManager.createFailResult("竞猜赛事现在无法结算！");
		}
		
		List<GuessingMatchOdds> rightList = new ArrayList<GuessingMatchOdds>();
		for(String gmoUuid : rightArray){
			GuessingMatchOdds gmo = this.guessingMatchOddsService.get(gmoUuid);
			rightList.add(gmo);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("guessingMatch", uuid);
		List<GuessingMatchOdds> gmoList = this.guessingMatchOddsService.getListByParams(params);
		
		for(GuessingMatchOdds gmo : gmoList){
			String gmoSpread = gmo.getSpread() == null ? "" : gmo.getSpread();
			
			Boolean isRight = false;
			for(GuessingMatchOdds right : rightList){
				String rightSpread = right.getSpread() == null ? "" : right.getSpread();
				if(gmo.getGuessingMatchType().equals(right.getGuessingMatchType()) 
						&& gmoSpread.equals(rightSpread) && gmo.getResult().equals(right.getResult())){
					isRight = true;
					break;
				}
			}
			gmo.setIsRight(isRight);
		}
		
		params.put("status", GuessingMatchTypeStatus.PUBLISH);
		List<GuessingMatchType> gmtList = this.guessingMatchTypeService.getListByParams(params);
		
		for(GuessingMatchType gmt : gmtList){
			gmt.setStatus(GuessingMatchTypeStatus.FINISHED);
		}
		
		gm.setStatus(GuessingMatchStatus.FINISHED);
		gm.setOperator(admin.getUuid());
		gm.setOperatetime(new Timestamp(System.currentTimeMillis()));
		
		this.guessingMatchService.finishGuessingMatch(gm, gmtList, gmoList);
		return ResultManager.createSuccessResult("竞猜结果录入成功！");
	}
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<StatusCountVO> list = guessingMatchService.getStatusList();
		return ResultManager.createDataResult(list,list.size());
	}
}
