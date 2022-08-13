package cn.zeppin.dao.impl;


import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherTrainingCertificateDao;
import cn.zeppin.entity.TeacherTrainingCertificate;

public class TeacherTrainingCertificateDaoImpl extends BaseDaoImpl<TeacherTrainingCertificate, Integer> implements
		ITeacherTrainingCertificateDao {

	@Override
	public List<TeacherTrainingCertificate> getTeacherCertificateListByParams(
			Map<String, Object> params, Map<String, Object> sortParams,
			int offset, int limit) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherTrainingCertificate ttc where 1=1");
		
		if(params != null && params.size() > 0){
			if(params.containsKey("ttrId") && params.get("ttrId") != null && Integer.parseInt(params.get("ttrId").toString()) > 0){
				sb.append(" and ttc.teacherTrainingRecords=");
				sb.append(params.get("ttrId"));
			}
		}
		
		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = (String)sortParams.get(key);
				sb.append("ttre." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		
		return this.getListForPage(sb.toString(), offset, limit);
	}

	@Override
	public int getTeacherCertificateListByParams(Map<String, Object> params,
			Map<String, Object> sortParams) {
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherTrainingCertificate ttc where 1=1");
		
		if(params != null && params.size() > 0){
			if(params.containsKey("ttrId") && params.get("ttrId") != null && Integer.parseInt(params.get("ttrId").toString()) > 0){
				sb.append(" and ttc.teacherTrainingRecords=");
				sb.append(params.get("ttrId"));
			}
		}
		
		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = (String)sortParams.get(key);
				sb.append("ttre." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}


}
