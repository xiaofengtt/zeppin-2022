package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherTrainingCertificate;

public interface ITeacherTrainingCertificateService extends
		IBaseService<TeacherTrainingCertificate, Integer> {

	public List<TeacherTrainingCertificate> getTeacherCertificateListByParams(Map<String, Object> params, Map<String, Object> sortParams, int offset, int limit);
	
	public int getTeacherCertificateListByParams(Map<String, Object> params, Map<String, Object> sortParams);
}
