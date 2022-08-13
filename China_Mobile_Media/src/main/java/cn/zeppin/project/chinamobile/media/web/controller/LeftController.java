package cn.zeppin.project.chinamobile.media.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.project.chinamobile.media.core.entity.Category;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.ICategoryService;
import cn.zeppin.project.chinamobile.media.web.vo.iface.WebCategoryVO;

/**
 * 对分类对象的页面控制器
 * 
 * @author Clark.R 2016年3月29日
 *
 */
@Controller
@RequestMapping("/left")
public class LeftController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping(value="/list", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<WebCategoryVO>> categoryList(){
		ActionResult<List<WebCategoryVO>> result = new ActionResult<List<WebCategoryVO>>();
		Category category = new Category();
		category.setLevel(1);
		category.setStatus(BaseEntity.GerneralStatusType.NORMAL);
		
		int recordCount = this.categoryService.getCountByParams(category);
		List<Entity> list = this.categoryService.getListByParams(category, WebCategoryVO.class);
		List<WebCategoryVO> resultData = new ArrayList<WebCategoryVO>();
		for(Entity entity : list){
			WebCategoryVO wcat = (WebCategoryVO) entity;
			Category cat = new Category();
			cat.setParent(wcat.getId());
			List<Entity> childlist = this.categoryService.getListByParams(cat, WebCategoryVO.class);
			List<WebCategoryVO>childLists = new ArrayList<WebCategoryVO>();
			for(Entity ent:childlist){
				WebCategoryVO wcats = (WebCategoryVO) ent;
				childLists.add(wcats);
			}
			wcat.setChild(childLists);
			resultData.add(wcat);
		}
		result.setData(resultData);
		result.setTotalResultCount(recordCount);
		result.setMessage("获取成功");
		result.setStatus("success");
		return result;
	}
}
