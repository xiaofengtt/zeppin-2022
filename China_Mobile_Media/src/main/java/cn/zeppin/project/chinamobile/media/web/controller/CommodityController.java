package cn.zeppin.project.chinamobile.media.web.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.ICommodityDisplayService;
import cn.zeppin.project.chinamobile.media.web.service.api.ICommodityService;
import cn.zeppin.project.chinamobile.media.web.service.api.IResourceService;
import cn.zeppin.project.chinamobile.media.web.vo.CommodityDisplayVO;
import cn.zeppin.project.chinamobile.media.web.vo.CommodityVO;

/**
 * 对分类对象的页面控制器
 * 
 * @author Clark.R 2016年3月29日
 *
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ICommodityService commodityService;
	
	@Autowired
	private ICommodityDisplayService commodityDisplayService;
	
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<CommodityVO>> listAllCommodity(@Valid Commodity commodity, @RequestParam Integer pagenum,  @RequestParam Integer pagesize, @RequestParam String sorts) {
//		List<Category> list = categoryService.getAll();
//		int pagenum = 1;
//		int pagesize = 10;
		if(pagenum == null || pagenum == 0){
			pagenum = 1;
		}
		if(pagesize == null || pagesize == 0){
			pagesize = 10;
		}
		int offset = (pagenum - 1) * pagesize;
		
		if(sorts == null || "".equals(sorts)){
			sorts = "createtime";
		}else{
			sorts = sorts.replaceAll("-", " ");
		}
		
//		if(category.getStatus() == null || "".equals(category.getStatus())){
//			category.setStatus(BaseEntity.GerneralStatusType.NORMAL);
//		}
		commodity.setStatus(BaseEntity.GerneralStatusType.NORMAL);
		List<Entity> list = this.commodityService.getListByParams(commodity, sorts, offset, pagesize, CommodityVO.class);
		List<CommodityVO> listData = new ArrayList<CommodityVO>();
		if(list != null && list.size() > 0){
			for(Entity entity: list){
				CommodityVO cv = (CommodityVO)entity;
				if(cv.getCover() != null && !"".equals(cv.getCover())){
					Resource re = this.resourceService.get(cv.getCover());
					if(re != null){
						cv.setCoverURL(re.getUrl());
					}else{
						cv.setCoverURL("/assets/img/default_productBig.jpg");
					}
				}else{
					cv.setCoverURL("/assets/img/default_productBig.jpg");
				}
				listData.add(cv);
			}
		}
		int recordCount = this.commodityService.getCountByParams(commodity);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
		
		ActionResult<List<CommodityVO>> result = new ActionResult<List<CommodityVO>>(listData);
		
		result.setMessage("加载成功");
		result.setStatus("success");
		result.setTotalPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalResultCount(recordCount);
		
		return result;
	}
	
	@RequestMapping(value="/load", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<Map<String,Object>> load(@Valid Commodity commodity){
		String id = commodity.getId();
		ActionResult<Map<String,Object>> result = new ActionResult<Map<String,Object>>();
		if(id != null && !"".equals(id)){
			Commodity cat = this.commodityService.get(id);
			if(cat != null){
				
				List<Entity> list = this.commodityDisplayService.getListByCommodity(cat, CommodityDisplayVO.class);
				
				Map<String,Object> comMap = new HashMap<String, Object>();
				comMap.put("id", cat.getId());
				comMap.put("name", cat.getName());
				comMap.put("price", cat.getPrice());
				comMap.put("originalprice", cat.getOriginalPrice());
				comMap.put("url", cat.getUrl());
				if(cat.getCover() != null && !"".equals(cat.getCover())){
					Resource cover = this.resourceService.get(cat.getCover());
					if(cover != null){
						comMap.put("cover", cover.getId());
						comMap.put("coverURL", cover.getUrl());
					}
				}else{
					comMap.put("coverURL", "/assets/img/default_productBig.jpg");
				}
				
				if(list != null && list.size()>0){
					comMap.put("displays",list);
				}
				
				result.setData(comMap);
				result.setMessage("成功");
				result.setStatus("success");
				
				return result;
			}else{
				
				result.setMessage("商品不存在");
				result.setStatus("fail");
				
				return result;
			}
			
		}else{
			
			result.setMessage("参数异常");
			result.setStatus("fail");
			
			return result;
		}
		
		
		
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> add(@Valid Commodity commodity,@RequestParam String displays,HttpSession session){
		
		ActionResult<String> result = new ActionResult<String>();
		
		/*
		 * name price oprice cover url display
		 */
		
		Commodity com = new Commodity();
		if(commodity.getName() != null && !"".equals(commodity.getName())){
			com.setName(commodity.getName());
		}else{
			result.setMessage("名称不能为空");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		
		if(commodity.getPrice() !=null && !BigDecimal.ZERO.equals(commodity.getPrice())){
			com.setPrice(commodity.getPrice());
		}else{
			result.setMessage("销售价格不能为空");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		
		if(commodity.getOriginalPrice() !=null && !BigDecimal.ZERO.equals(commodity.getOriginalPrice())){
			com.setOriginalPrice(commodity.getOriginalPrice());
		}
		
		if(commodity.getUrl() != null && !"".equals(commodity.getUrl())){
			com.setUrl(commodity.getUrl());
		}else{
			result.setMessage("商品链接不能为空");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		
		if(commodity.getCover() != null && !"".equals(commodity.getCover())){
			com.setCover(commodity.getCover());
		}else{
			com.setCover("");
		}
		
		com.setStatus(BaseEntity.GerneralStatusType.NORMAL);
		User user = (User)session.getAttribute("currentUser");
		com.setCreator(user.getId());
		com.setCreatetime(new Timestamp((new Date()).getTime()));
		
		try {
			if(displays != null && !"".equals(displays)){
				String[] display = displays.split(",");
				this.commodityService.insert(com,display,user.getId());
			}else{
				this.commodityService.insert(com);
			}
			
			result.setMessage("添加成功");
			result.setStatus("success");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setMessage("添加异常");
			result.setStatus("fail");
			result.setSuccess(false);
			
		}
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> edit(@Valid Commodity commodity,@RequestParam String displays,HttpSession session){
		ActionResult<String> result = new ActionResult<String>();
		if(commodity.getId() != null && !"".equals(commodity.getId())){
			Commodity com = this.commodityService.get(commodity.getId());
			if(com != null){
				if(commodity.getName() != null && !"".equals(commodity.getName())){
					com.setName(commodity.getName());
				}else{
					result.setMessage("名称不能为空");
					result.setStatus("fail");
					result.setSuccess(false);
					
					return result;
				}
				
				if(commodity.getPrice() !=null && !BigDecimal.ZERO.equals(commodity.getPrice())){
					com.setPrice(commodity.getPrice());
				}else{
					result.setMessage("销售价格不能为空");
					result.setStatus("fail");
					result.setSuccess(false);
					
					return result;
				}
				
				if(commodity.getOriginalPrice() !=null && !BigDecimal.ZERO.equals(commodity.getOriginalPrice())){
					com.setOriginalPrice(commodity.getOriginalPrice());
				}
				
				if(commodity.getUrl() != null && !"".equals(commodity.getUrl())){
					com.setUrl(commodity.getUrl());
				}else{
					result.setMessage("商品链接不能为空");
					result.setStatus("fail");
					result.setSuccess(false);
					
					return result;
				}
				
				if(commodity.getCover() != null && !"".equals(commodity.getCover())){
					com.setCover(commodity.getCover());
				}else{
					com.setCover("");
				}
				User user = (User)session.getAttribute("currentUser");
				try {
					if(displays != null && !"".equals(displays)){
						String[] display = displays.split(",");
						this.commodityService.update(com, display,user.getId());
					}else{
						this.commodityService.update(com);
					}
					
					result.setMessage("添加成功");
					result.setStatus("success");
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					result.setMessage("添加异常");
					result.setStatus("fail");
					result.setSuccess(false);
					
				}
				
			}else{
				result.setMessage("没有这个栏目");
				result.setStatus("fail");
				result.setSuccess(false);
				
				return result;
			}
		}else {
			result.setMessage("参数异常");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		return result;
	}
	
	@RequestMapping(value="/remove", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<String> delete(@Valid Commodity commodity){
		ActionResult<String> result = new ActionResult<String>();
		if(commodity.getId() != null && !"".equals(commodity.getId())){
			Commodity cat = this.commodityService.get(commodity.getId());
			if(cat != null){
				cat.setStatus(BaseEntity.GerneralStatusType.DELETED);
				this.commodityService.update(cat);
				result.setMessage("删除成功");
				result.setStatus("success");
				
				return result;
			}else{
				result.setMessage("没有这个商品");
				result.setStatus("fail");
				result.setSuccess(false);
				
				return result;
			}
		}else {
			result.setMessage("参数异常");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
	}
	
	@RequestMapping(value="/search", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<Entity>> searchAllCommodity(@Valid Commodity commodity) {
//		List<Category> list = categoryService.getAll();
//		int pagenum = 1;
//		int pagesize = 10;
//		if(pagenum == null || pagenum == 0){
//			pagenum = 1;
//		}
//		if(pagesize == null || pagesize == 0){
//			pagesize = 10;
//		}
//		int offset = (pagenum - 1) * pagesize;
//		
//		String sorts = createtime
//		if(sorts == null || "".equals(sorts)){
//			sorts = "createtime";
//		}else{
//			sorts = sorts.replaceAll("-", " ");
//		}
		
//		if(category.getStatus() == null || "".equals(category.getStatus())){
//			category.setStatus(BaseEntity.GerneralStatusType.NORMAL);
//		}
		commodity.setStatus(BaseEntity.GerneralStatusType.NORMAL);
		List<Entity> list = this.commodityService.getListByParams(commodity, "createtime", -1, -1, CommodityVO.class);
		int recordCount = this.commodityService.getCountByParams(commodity);
//		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
		
		ActionResult<List<Entity>> result = new ActionResult<List<Entity>>(list);
		
		result.setMessage("加载成功");
		result.setStatus("success");
//		result.setTotalPageCount(pageCount);
//		result.setPageNum(pagenum);
//		result.setPageSize(pagesize);
		result.setTotalResultCount(recordCount);
		
		return result;
	}
}
