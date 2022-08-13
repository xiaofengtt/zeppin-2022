package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IFunCategoryDao;
import cn.zeppin.entity.FunCategory;

public class FunCategoryDaoImpl extends BaseDaoImpl<FunCategory, Integer> implements IFunCategoryDao {

	@Override
	public List<FunCategory> getListByParams(Map<String, Object> params,
			String sort, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from FunCategory fc where 1=1");
		if(params != null){
			if(params.containsKey("status") && params.get("status") != null){
				sb.append(" and fc.status=");
				sb.append(params.get("status"));
			}
			
			if(params.containsKey("name") && params.get("name") != null){
				sb.append(" and fc.name=");
				sb.append(params.get("name"));
			}
			
			if(params.containsKey("level") && params.get("level") != null){
				sb.append(" and fc.level=");
				sb.append(params.get("level"));
			}
			
			if(params.containsKey("funCategory") && params.get("funCategory") != null){
				sb.append(" and fc.funCategory=");
				sb.append(params.get("funCategory"));
			}
			
			if(params.containsKey("code") && params.get("code") != null){
				sb.append(" and fc.code=");
				sb.append(params.get("code"));
			}
		}
		
		if(sort != null){
			sb.append(" order by fc.");
			sb.append(sort);
		}else{
			sb.append(" order by fc.weight");
		}
		
		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from FunCategory fc where 1=1");
		if(params != null){
			if(params.containsKey("status") && params.get("status") != null){
				sb.append(" and fc.status=");
				sb.append(params.get("status"));
			}
			
			if(params.containsKey("name") && params.get("name") != null){
				sb.append(" and fc.name=");
				sb.append(params.get("name"));
			}
			
			if(params.containsKey("level") && params.get("level") != null){
				sb.append(" and fc.level=");
				sb.append(params.get("level"));
			}
			
			if(params.containsKey("funCategory") && params.get("funCategory") != null){
				sb.append(" and fc.funCategory=");
				sb.append(params.get("funCategory"));
			}
			
			if(params.containsKey("code") && params.get("code") != null){
				sb.append(" and fc.code=");
				sb.append(params.get("code"));
			}
		}
		
		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

}
