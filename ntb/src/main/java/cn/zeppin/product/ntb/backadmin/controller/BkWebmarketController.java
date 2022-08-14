/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkWebmarketSwitchService;
import cn.zeppin.product.ntb.backadmin.service.api.IVersionService;
import cn.zeppin.product.ntb.backadmin.vo.BkWebmarketSwitchVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BkWebmarketSwitch;
import cn.zeppin.product.ntb.core.entity.BkWebmarketSwitch.BkWebmarketSwitchStatus;
import cn.zeppin.product.ntb.core.entity.Version;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 *
 * 应用商店控制管理
 */

@Controller
@RequestMapping(value = "/backadmin/market")
public class BkWebmarketController extends BaseController {
 
	@Autowired
	private IBkWebmarketSwitchService bkWebmarketSwitchService;
	
	@Autowired
	private IVersionService versionService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	/**
	 * 根据条件查询应用市场开关 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "version", message="版本号", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, String version, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!Utlity.checkStringNull(version) && !"all".equals(version)){
			searchMap.put("device", version);
		}
		
		
		//查询符合条件的应用市场开关的总数
		Integer totalResultCount = bkWebmarketSwitchService.getCount(searchMap);
		//查询符合条件的应用市场开关列表
		List<Entity> list = bkWebmarketSwitchService.getListForPage(searchMap, pageNum, pageSize, sorts, BkWebmarketSwitchVO.class);
		List<BkWebmarketSwitchVO> listvo = new ArrayList<BkWebmarketSwitchVO>();
		for(Entity entity : list){
			BkWebmarketSwitchVO vo = (BkWebmarketSwitchVO)entity;
			
			if(vo.getCreator() != null){
				BkOperator op = this.bkOperatorService.get(vo.getCreator());
				if(op != null){
					vo.setCreatorCN(op.getRealname());
				} else {
					vo.setCreatorCN("-");
				}
			} else {
				vo.setCreatorCN("-");
			}
			listvo.add(vo);
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
//	/**
//	 * 获取应用市场开关分状态列表
//	 * @return
//	 */
//	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
//	@ResponseBody
//	public Result statusList() {
//		List<Entity> list = versionService.getStatusList(StstusCountVO.class);
//		return ResultManager.createDataResult(list,list.size());
//	}
	
	/**
	 * 获取一条应用市场开关
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取应用市场开关
		BkWebmarketSwitch market = bkWebmarketSwitchService.get(uuid);
		if (market == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//界面返回封装对象
		BkWebmarketSwitchVO marketVO = new BkWebmarketSwitchVO(market);
		if(marketVO.getVersion() != null){
			Version ver = this.versionService.get(marketVO.getVersion());
			marketVO.setVersionNum(ver.getVersion());
		}
		
		if(market.getCreator() != null){
			BkOperator op = this.bkOperatorService.get(market.getCreator());
			if(op != null){
				marketVO.setCreatorCN(op.getRealname());
			} else {
				marketVO.setCreatorCN("-");
			}
		} else {
			marketVO.setCreatorCN("-");
		}
		

		return ResultManager.createDataResult(marketVO);
	}
	
	/**
	 * 添加一条应用市场开关
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "webmarket", message="应用市场标识码", type = DataType.STRING, required = true)
	@ActionParam(key = "webmarketName", message="应用市场名称", type = DataType.STRING, required = true)
	@ActionParam(key = "version", message="版本号", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result add(String webmarket, String webmarketName, String version) {
		
		//判断版本情况是否正确
		Version ver = versionService.get(version);
		if(ver == null){
			return ResultManager.createFailResult("版本号不存在！");
		}
		
//		if(!Utlity.DEVICE_NUMBER_ANDROID.equals(ver.getDevice())){
//			return ResultManager.createFailResult("版本号错误，请重新选择！");
//		}
		
		//验证是否有重名的情况
		if (bkWebmarketSwitchService.isExistBkWebmarketSwitch(webmarket, version, null)) {
			return ResultManager.createFailResult("该应用市场版本号已存在！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建应用市场开关
		BkWebmarketSwitch market = new BkWebmarketSwitch();
		market.setUuid(UUID.randomUUID().toString());
		market.setVersion(version);
		market.setWebMarket(webmarket);
		market.setWebMarketName(webmarketName);
		market.setFlagSwitch(false);
		market.setStatus(BkWebmarketSwitchStatus.NORMAL);
		market.setCreator(currentOperator.getUuid());
		market.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		bkWebmarketSwitchService.insert(market);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条应用市场开关
	 * @param uuid
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "webmarket", message="应用市场标识码", type = DataType.STRING, required = true)
	@ActionParam(key = "webmarketName", message="应用市场名称", type = DataType.STRING, required = true)
	@ActionParam(key = "version", message="版本号", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result edit(String uuid, String webmarket, String webmarketName, String version) {
		
		//获取应用市场开关
		BkWebmarketSwitch market = bkWebmarketSwitchService.get(uuid);
		if(market != null && uuid.equals(market.getUuid())){
			//判断版本情况是否正确
			Version ver = versionService.get(version);
			if(ver == null){
				return ResultManager.createFailResult("版本号不存在！");
			}
			
			if(!Utlity.DEVICE_NUMBER_ANDROID.equals(ver.getDevice())){
				return ResultManager.createFailResult("版本号错误，请重新选择！");
			}
			//验证是否有重名的情况
			if (bkWebmarketSwitchService.isExistBkWebmarketSwitch(webmarket, version, uuid)) {
				return ResultManager.createFailResult("该应用市场版本号已存在！");
			}
			
			//修改应用市场开关
			market.setVersion(version);
			market.setWebMarket(webmarket);
			market.setWebMarketName(webmarketName);
			bkWebmarketSwitchService.update(market);
			
			return ResultManager.createSuccessResult("保存成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}

	/**
	 * 开关一条应用市场开关
	 * @param uuid
	 * @param switchFlag true false
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "switchFlag", message="开关", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result change(String uuid, Boolean switchFlag) {
		
		//获取应用市场开关
		BkWebmarketSwitch market = bkWebmarketSwitchService.get(uuid);
		if(market != null && uuid.equals(market.getUuid())){
			if(!BkWebmarketSwitchStatus.DELETED.equals(market.getStatus())){
				market.setFlagSwitch(switchFlag);
				bkWebmarketSwitchService.update(market);
			} else {
				return ResultManager.createFailResult("状态错误，操作失败！");
			}
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条应用市场开关
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取应用市场开关
		BkWebmarketSwitch market = bkWebmarketSwitchService.get(uuid);
		if(market != null && uuid.equals(market.getUuid())){
			//删除应用市场开关
			bkWebmarketSwitchService.delete(market);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
}
