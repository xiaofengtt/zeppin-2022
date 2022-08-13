package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherCertificateDao;
import cn.zeppin.entity.TeacherCertificate;

public class TeacherCertificateDaoImpl extends
		BaseDaoImpl<TeacherCertificate, Integer> implements ITeacherCertificateDao {

	@Override
	public List<TeacherCertificate> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherCertificate pc where 1=1");
		if(params != null){
			if(params.containsKey("id")){
				sb.append(" and pc.id=");
				sb.append(params.get("id"));
			}
			if(params.containsKey("certificate")){
				sb.append(" and pc.certificate='");
				sb.append(params.get("certificate"));
				sb.append("'");
			}
			
			if(params.containsKey("certificationBody")){
				sb.append(" and pc.certificationBody='");
				sb.append(params.get("certificationBody"));
				sb.append("'");
			}
			
			if(params.containsKey("status")){
				sb.append(" and pc.status=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and pc.status>-1");
			}
			
			if(params.containsKey("teacher")){
				sb.append(" and pc.teacher=");
				sb.append(params.get("teacher"));
			}
			
		}
		
		sb.append("order by pc.");
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherCertificate pc where 1=1");
		if(params != null){
			if(params.containsKey("id")){
				sb.append(" and pc.id=");
				sb.append(params.get("id"));
			}
			if(params.containsKey("certificate")){
				sb.append(" and pc.certificate='");
				sb.append(params.get("certificate"));
				sb.append("'");
			}
			
			if(params.containsKey("certificationBody")){
				sb.append(" and pc.certificationBody='");
				sb.append(params.get("certificationBody"));
				sb.append("'");
			}
			
			if(params.containsKey("status")){
				sb.append(" and pc.status=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and pc.status>-1");
			}
			
			if(params.containsKey("teacher")){
				sb.append(" and pc.teacher=");
				sb.append(params.get("teacher"));
			}
			
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
