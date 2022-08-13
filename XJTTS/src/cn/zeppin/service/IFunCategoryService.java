package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.FunCategory;

public interface IFunCategoryService extends IBaseService<FunCategory, Integer> {

	public List<FunCategory> getListByParams(Map<String, Object> params,String sort,int offset,int length);
	
	public int getCountByParams(Map<String, Object> params);
}
