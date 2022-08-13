package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherEduEvidenceDao;
import cn.zeppin.entity.TeacherEduEvidence;

public class TeacherEduEvidenceDaoImpl extends
		BaseDaoImpl<TeacherEduEvidence, Integer> implements ITeacherEduEvidenceDao {

	@Override
	public List<TeacherEduEvidence> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherEduEvidence tee where 1=1");
		if(params != null){
			if(params.containsKey("teacherEduAdvance")){
				sb.append(" and teaa.teacherEduAdvance=");
				sb.append(params.get("teacherEduAdvance"));
			}
			
			if(params.containsKey("document")){
				sb.append(" and teaa.document=");
				sb.append(params.get("document"));
			}
			
		}
		
		sb.append("order by tee.");
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherEduEvidence tee where 1=1");
		if(params != null){
			if(params.containsKey("teacherEduAdvance")){
				sb.append(" and teaa.teacherEduAdvance=");
				sb.append(params.get("teacherEduAdvance"));
			}
			
			if(params.containsKey("document")){
				sb.append(" and teaa.document=");
				sb.append(params.get("document"));
			}
			
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
