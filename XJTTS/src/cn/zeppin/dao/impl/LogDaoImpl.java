package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ILogDao;
import cn.zeppin.entity.Log;

public class LogDaoImpl extends BaseDaoImpl<Log, Integer> implements ILogDao {
	 public Integer getLogCountByParams(Map<String, Object> map){
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("select count(*) from Log where 1=1");
	    	if(map.get("tableName")!=null&&!map.get("tableName").equals("")){
	    		sb.append(" and tableName='").append(map.get("tableName")).append("'");
	    	}
	    	if(map.get("tableId")!=null&&!map.get("tableId").equals("")){
	    		sb.append(" and tableId='").append(map.get("tableId")).append("'");
	    	}
	    	Object obj = this.getObjectByHql(sb.toString(), null);
	    	return Integer.valueOf(obj.toString());
	    }
		
		public List<Log> getLogListByParams(Map<String, Object> map , int offset, int length){
			StringBuilder sb = new StringBuilder();
			sb.append("from Log where 1=1");
			if(map.get("tableName")!=null&&!map.get("tableName").equals("")){
	    		sb.append(" and tableName='").append(map.get("tableName")).append("'");
	    	}
	    	if(map.get("tableId")!=null&&!map.get("tableId").equals("")){
	    		sb.append(" and tableId='").append(map.get("tableId")).append("'");
	    	}
	    	sb.append(" order by id desc");
			return this.getListForPage(sb.toString(), offset, length);
		}
		
		@SuppressWarnings("unchecked")
		public List<String> getTableList(){
			StringBuilder sb = new StringBuilder();
			sb.append("select distinct table_name from Log");
			return (List<String>)this.getListBySQL(sb.toString(), null);
		}
}
