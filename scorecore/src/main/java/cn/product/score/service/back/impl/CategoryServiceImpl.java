package cn.product.score.service.back.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.AdminDao;
import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.Category;
import cn.product.score.entity.Category.CategoryStatus;
import cn.product.score.service.back.CategoryService;
import cn.product.score.entity.Resource;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.CategoryVO;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private AdminDao adminDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		Category category = categoryDao.get(uuid);
		if(category == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("分类不存在");
			return;
		}
		result.setData(category);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		Category category = (Category) paramsMap.get("category");
		
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
		
		Integer totalCount = categoryDao.getCountByParams(searchMap);
		List<Category> list = categoryDao.getListByParams(searchMap);
		List<CategoryVO> voList = new LinkedList<CategoryVO>();
		for(Category cate : list){
			CategoryVO cateVO = new CategoryVO(cate);
			if(cate.getIcon() != null){
				Resource res = resourceDao.get(cate.getIcon());
				if(res != null){
					cateVO.setIconUrl(res.getUrl());
				}
			}
			Admin admin = adminDao.get(cate.getCreator());
			if(admin != null){
				cateVO.setCreatorName(admin.getRealname());
			}
			addCategory(voList, cateVO);
		}
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Category category = (Category) paramsMap.get("category");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(Utlity.checkStringNull(category.getParent())){
			category.setLevel(1);
		}else{
			Category parent = categoryDao.get(category.getParent());
			if(parent != null){
				category.setLevel(parent.getLevel() + 1);
			}else{
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("父级分类存在");
				return;
			}
		}
		
		category.setUuid(UUID.randomUUID().toString());
		category.setCreator(admin);
		category.setCreatetime(new Timestamp(System.currentTimeMillis()));
		categoryDao.insertCategory(category);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Category category = (Category) paramsMap.get("category");
//		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		Category cate = this.categoryDao.get(category.getUuid());
		if(cate == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("分类不存在");
			return;
		}
		
		cate.setName(category.getName());
		cate.setShortname(category.getShortname());
		cate.setIcon(category.getIcon());
		cate.setIstag(category.getIstag());
		cate.setStatus(category.getStatus());
		categoryDao.update(cate);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Category category = (Category) paramsMap.get("category");
		
		if(!CategoryStatus.NORMAL.equals(category.getStatus()) && !CategoryStatus.DISABLE.equals(category.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("变更状态有误");
			return;
		}
		
		Category cate = this.categoryDao.get(category.getUuid());
		if(cate == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("分类不存在");
			return;
		}
		
		cate.setStatus(category.getStatus());
		categoryDao.update(cate);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		Category cate = this.categoryDao.get(uuid);
		if(cate == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("分类不存在");
			return;
		}
		categoryDao.deleteCategory(cate);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("删除成功！");
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
