/**
 * 
 */
package cn.zeppin.product.score.controller.back;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
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
import cn.zeppin.product.score.entity.Category.CategoryStatus;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.CategoryVO;

/**
 * 分类管理
 */

@Controller
@RequestMapping(value = "/back/category")
public class CategoryController extends BaseController{

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 根据条件查询列表
	 * @param name
	 * @param shortname
	 * @param parent
	 * @param scode
	 * @param level
	 * @param istag
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING)
	@ActionParam(key = "parent", message = "父级", type = DataType.STRING)
	@ActionParam(key = "scode", message = "编码", type = DataType.STRING)
	@ActionParam(key = "level", message = "级别", type = DataType.NUMBER)
	@ActionParam(key = "istag", message = "是否是标签", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result list(Category category, Integer pageNum, Integer pageSize) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", category.getName());
		searchMap.put("shortname", category.getShortname());
		searchMap.put("parent", category.getParent());
		searchMap.put("scode", category.getScode());
		searchMap.put("level", category.getLevel());
		searchMap.put("istag", category.getIstag());
		searchMap.put("status", category.getStatus());
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "level,scode");
		
		Integer totalCount = categoryService.getCountByParams(searchMap);
		List<Category> list = categoryService.getListByParams(searchMap);
		List<CategoryVO> voList = new LinkedList<CategoryVO>();
		for(Category cate : list){
			CategoryVO cateVO = new CategoryVO(cate);
			if(cate.getIcon() != null){
				Resource res = resourceService.get(cate.getIcon());
				if(res != null){
					cateVO.setIconUrl(res.getUrl());
				}
			}
			Admin admin = adminService.get(cate.getCreator());
			if(admin != null){
				cateVO.setCreatorName(admin.getRealname());
			}
			addCategory(voList, cateVO);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		Category category = categoryService.get(uuid);
		if(category == null){
			return ResultManager.createFailResult("分类不存在");
		}
		
		
		return ResultManager.createDataResult(category);
	}
	
	/**
	 * 添加
	 * @param name
	 * @param shortname
	 * @param parent
	 * @param icon
	 * @param istag
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING, required = true)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING, required = true)
	@ActionParam(key = "parent", message = "父级", type = DataType.STRING)
	@ActionParam(key = "icon", message = "图标", type = DataType.STRING)
	@ActionParam(key = "istag", message = "是否是标签", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(Category category) {
		Admin admin = this.getCurrentOperator();
		
		if(Utlity.checkStringNull(category.getParent())){
			category.setLevel(1);
		}else{
			Category parent = categoryService.get(category.getParent());
			if(parent != null){
				category.setLevel(parent.getLevel() + 1);
			}else{
				return ResultManager.createFailResult("父级分类存在");
			}
		}
		
		category.setUuid(UUID.randomUUID().toString());
		category.setCreator(admin.getUuid());
		category.setCreatetime(new Timestamp(System.currentTimeMillis()));
		categoryService.insertCategory(category);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param shortname
	 * @param icon
	 * @param istag
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING, required = true)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING, required = true)
	@ActionParam(key = "icon", message = "图标", type = DataType.STRING)
	@ActionParam(key = "istag", message = "是否是标签", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(Category category) {
		Category cate = this.categoryService.get(category.getUuid());
		if(cate == null){
			return ResultManager.createFailResult("分类不存在");
		}
		
		cate.setName(category.getName());
		cate.setShortname(category.getShortname());
		cate.setIcon(category.getIcon());
		cate.setIstag(category.getIstag());
		cate.setStatus(category.getStatus());
		categoryService.update(cate);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 变更状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(Category category) {
		if(!CategoryStatus.NORMAL.equals(category.getStatus()) && !CategoryStatus.DISABLE.equals(category.getStatus())){
			return ResultManager.createFailResult("变更状态有误");
		}
		
		Category cate = this.categoryService.get(category.getUuid());
		if(cate == null){
			return ResultManager.createFailResult("分类不存在");
		}
		
		cate.setStatus(category.getStatus());
		categoryService.update(cate);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		Category cate = this.categoryService.get(uuid);
		if(cate == null){
			return ResultManager.createFailResult("分类不存在");
		}
		categoryService.deleteCategory(cate);
		return ResultManager.createSuccessResult("删除成功！");
	}
	
	//多级分类添加
	static Boolean addCategory(List<CategoryVO> voList, CategoryVO vo){
		Boolean flag = false;
		for(CategoryVO parent : voList){
			if(vo.getScode().startsWith(parent.getScode())){
				flag = true;
				addCategory(parent.getChildren(), vo);
				break;
			}
		}
		if(!flag){
			voList.add(vo);
		}
		return flag;
	}
}
