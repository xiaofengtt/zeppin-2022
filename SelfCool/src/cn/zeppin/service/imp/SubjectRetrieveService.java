package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectRetrieveDAO;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.Retrieve;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectRetrieve;
import cn.zeppin.service.api.ISubjectRetrieveService;

public class SubjectRetrieveService implements ISubjectRetrieveService {

	private ISubjectRetrieveDAO subjectRetrieveDAO;

	public ISubjectRetrieveDAO getSubjectRetrieveDAO() {
		return subjectRetrieveDAO;
	}

	public void setSubjectRetrieveDAO(ISubjectRetrieveDAO subjectRetrieveDAO) {
		this.subjectRetrieveDAO = subjectRetrieveDAO;
	}

	/**
	 * 获取 学科的检索分类
	 * 
	 * @param map
	 * @return
	 */
	public List<SubjectRetrieve> getSubjectRetrieves(Map<String, Object> map) {

		return this.getSubjectRetrieveDAO().getSubjectRetrieves(map);
	}

	/**
	 * 添加
	 * 
	 * @param subject
	 * @param srs
	 */
	public void addSubjectRetrieves(Subject subject, List<Retrieve> srs) {

		// 先删除已有的记录

		this.getSubjectRetrieveDAO().delSubjectRetrieves(subject.getId());

		// 插入新的记录

		for (Retrieve re : srs) {

			SubjectRetrieve sr = new SubjectRetrieve();
			sr.setRetrieve(re);
			sr.setSubject(subject);

			this.getSubjectRetrieveDAO().save(sr);

		}

	}


	/**
	 * 获取用户可添加关注的学科列表
	 * @param ssoUser
	 * @param category
	 * @param retrieves
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getSubjectRetrieves(SsoUser ssoUser,
			Category category, List<Retrieve> retrieves) {
		// TODO Auto-generated method stub
		return this.getSubjectRetrieveDAO().getSubjectRetrieves(ssoUser, category, retrieves);
	}

}
