package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeachingGradeDao;
import cn.zeppin.entity.TeachingGrade;

public class TeachingGradeDaoImpl extends BaseDaoImpl<TeachingGrade, Integer> implements ITeachingGradeDao{

	@Override
	public void deleteTeachingGradeByTeacher(int teacher) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("delete from TeachingGrade tg where 1=1");
		sb.append(" and tg.teacher="+teacher);
		this.executeHSQL(sb.toString());
	}

	@Override
	public List<TeachingGrade> getListByParams(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
    	sb.append("from TeachingGrade where 1=1");
    	if(map.get("teacher")!=null && !map.get("teacher").equals("")){
    		sb.append(" and teacher = '").append(map.get("teacher")).append("'");
    	}
    	if(map.get("id")!=null && !map.get("id").equals("")){
    		sb.append(" and id = '").append(map.get("id")).append("'");
    	}
    	return this.getListByHSQL(sb.toString());
	}

}
