package cn.zeppin.dao;


import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherTrainingCertificate;

public interface ITeacherTrainingCertificateDao extends IBaseDao<TeacherTrainingCertificate, Integer> {
	
	public List<TeacherTrainingCertificate> getTeacherCertificateListByParams(Map<String, Object> params, Map<String, Object> sortParams, int offset, int limit);
	
	public int getTeacherCertificateListByParams(Map<String, Object> params, Map<String, Object> sortParams);

}
