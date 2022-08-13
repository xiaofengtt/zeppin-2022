package cn.zeppin.project.chinamobile.media.web.controller;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.project.chinamobile.media.core.entity.Category;
import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.ICategoryService;
import cn.zeppin.project.chinamobile.media.web.vo.CategoryVO;

/**
 * 对分类对象的页面控制器
 * 
 * @author Clark.R 2016年3月29日
 *
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping(value="/all", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<List<Entity>> listAllCategory(@Valid Category category, @RequestParam Integer pagenum,  @RequestParam Integer pagesize, @RequestParam String sorts) {
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
		
		List<Entity> list = this.categoryService.getListByParams(category, sorts, offset, pagesize, CategoryVO.class);
		int recordCount = this.categoryService.getCountByParams(category);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
		
		ActionResult<List<Entity>> result = new ActionResult<List<Entity>>(list);
		
		result.setMessage("加载成功");
		result.setStatus("success");
		result.setTotalPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalResultCount(recordCount);
		
		return result;
	}
	
	@RequestMapping(value="/load", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<Category> load(@Valid Category category){
		String id = category.getId();

		Category cat = this.categoryService.get(id);
		
		ActionResult<Category> result = new ActionResult<Category>(cat);
		
		result.setMessage("成功");
		result.setStatus("success");
		
		return result;
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<Category> add(@Valid Category category,HttpSession session){
		
		/*
		 * name、parent、status
		 */
		Category cat = new Category();
		ActionResult<Category> result = new ActionResult<Category>();
		//名称
		if(category.getName() != null && !"".equals(category.getName())){
			cat.setName(category.getName());
		}else{
			result.setMessage("名称不能为空");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		
		
		//状态
		if(category.getStatus() != null && !"".equals(category.getStatus())){
			if(BaseEntity.GerneralStatusType.NORMAL.equals(category.getStatus())){
				cat.setStatus(BaseEntity.GerneralStatusType.NORMAL);
			}else{
				cat.setStatus(BaseEntity.GerneralStatusType.STOPPED);
			}
		}else{
			cat.setStatus(BaseEntity.GerneralStatusType.NORMAL);
		}
		
		//scode、level、parent
		String format = "0000";
		DecimalFormat df = new DecimalFormat(format);
		String scode = "";
		Integer level = 1;
		if(category.getParent() != null && !"".equals(category.getParent())){
			Category parentCategory = this.categoryService.get(category.getParent());
			if(parentCategory != null){
				cat.setParent(parentCategory.getId());
				level = parentCategory.getLevel() + 1;
				int count = this.categoryService.getChildCount(parentCategory.getId());
				count++;
				scode = parentCategory.getScode() + df.format(count);
			}
		}
		cat.setLevel(level);
		
		if(level == 1){
			HashMap<String,String> searchMap = new HashMap<String, String>();
			searchMap.put("level", level+"");
			int count = this.categoryService.getCountByParams(searchMap);
			count++;
			scode = df.format(count);
		}
		
		cat.setScode(scode);
		
		//createTime.creator
		cat.setCreatetime(new Timestamp((new Date()).getTime()));
		User user = (User)session.getAttribute("currentUser");
		
		cat.setCreator(user.getId());
		
		try {
			this.categoryService.insert(cat);
			
			result.setData(cat);
			result.setMessage("添加成功");
			result.setStatus("success");
			
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			result.setMessage("添加异常");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}

	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> edit(@Valid Category category){
		ActionResult<String> result = new ActionResult<String>();
		if(category.getId() != null && !"".equals(category.getId())){
			Category cat = this.categoryService.get(category.getId());
			if(cat != null){
				if(category.getName() != null && !"".equals(category.getName())){
					cat.setName(category.getName());
				}else{
					result.setMessage("名字不能为空");
					result.setStatus("fail");
					result.setSuccess(false);
					
					return result;
				}
				
				//状态
				if(category.getStatus() != null && !"".equals(category.getStatus())){
					if(BaseEntity.GerneralStatusType.NORMAL.equals(category.getStatus())){
						cat.setStatus(BaseEntity.GerneralStatusType.NORMAL);
					}else{
						cat.setStatus(BaseEntity.GerneralStatusType.STOPPED);
					}
					
				}else{
					cat.setStatus(BaseEntity.GerneralStatusType.NORMAL);
				}
				
				this.categoryService.update(cat);
				
				result.setMessage("编辑成功");
				result.setStatus("success");
				
				return result;
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
	}
	
	@RequestMapping(value="/remove", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> delete(@Valid Category category){
		ActionResult<String> result = new ActionResult<String>();
		if(category.getId() != null && !"".equals(category.getId())){
			Category cat = this.categoryService.get(category.getId());
			if(cat != null){
				cat.setStatus(BaseEntity.GerneralStatusType.DELETED);
				this.categoryService.update(cat);
				result.setMessage("删除成功");
				result.setStatus("success");
				
				return result;
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
	}
	
	@RequestMapping(value="/loadNav", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<Category>> LoadCategoryNav(@Valid Category category) {
		ActionResult<List<Category>> result = new ActionResult<List<Category>>();
		if(category.getParent() != null && !"".equals(category.getParent())){
			
			Category cat = this.categoryService.get(category.getParent());
			if(cat != null){
				LinkedList<Category> categorylist = new LinkedList<Category>();
				categorylist.add(cat);
				while (cat.getParent() != null && !"".equals(cat.getParent())) {
					cat = this.categoryService.get(cat.getParent());
					categorylist.add(cat);
				}
				List<Category> dataList = new ArrayList<>();
				int i=categorylist.size()-1;
				for(;i>=0;i--){
					
					Category cate = categorylist.get(i);
					dataList.add(cate);
				}
				result.setData(dataList);
				result.setMessage("加载成功");
				result.setStatus("success");
				result.setSuccess(true);
				return result;
			} else {
				result.setMessage("找不到上一级栏目");
				result.setStatus("fail");
				result.setSuccess(false);
				return result;
			}
			
		}else{
			result.setMessage("找不到参数");
			result.setStatus("fail");
			result.setSuccess(false);
			return result;
		}
		
	}
	
}
