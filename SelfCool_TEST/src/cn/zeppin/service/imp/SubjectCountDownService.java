package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectCountDownDAO;
import cn.zeppin.entity.SubjectCountdown;
import cn.zeppin.service.api.ISubjectCountDownService;
import cn.zeppin.utility.Dictionary;

public class SubjectCountDownService implements ISubjectCountDownService {

	private ISubjectCountDownDAO subjectCountDownDAO;

	public ISubjectCountDownDAO getSubjectCountDownDAO() {
		return subjectCountDownDAO;
	}

	public void setSubjectCountDownDAO(ISubjectCountDownDAO subjectCountDownDAO) {
		this.subjectCountDownDAO = subjectCountDownDAO;
	}

	/**
	 * 添加考试倒计时
	 */
	@Override
	public void addSubjectCountDown(SubjectCountdown subjectCountDown) {
		this.getSubjectCountDownDAO().save(subjectCountDown);
	}

	/**
	 * 更新
	 */
	@Override
	public void updateSubjectCountDown(SubjectCountdown subjectCountDown) {
		this.getSubjectCountDownDAO().update(subjectCountDown);
	}

	/**
	 * 获取考试倒计时
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public SubjectCountdown getSubjectCountDownById(int id) {
		return this.getSubjectCountDownDAO().get(id);
	}

	/**
	 * 删除
	 * 
	 * @param subjectCountdown
	 */
	public void deleteSubjectCountDown(SubjectCountdown subjectCountdown) {
		subjectCountdown.setStatus(Dictionary.SUBJECT_COUNT_DOWN_STATUS_CLOSED);
		this.getSubjectCountDownDAO().update(subjectCountdown);
	}
	
	/**
	 * 获取考试倒计时
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int getSubjectCountdownCount(Map<String, Object> map) {
		return this.getSubjectCountDownDAO().getSubjectCountdownCount(map);
	}

	/**
	 * 获取考试倒计时
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<SubjectCountdown> getSubjectCountdowns(Map<String, Object> map, int offset, int length) {
		return this.getSubjectCountDownDAO().getSubjectCountdowns(map, offset, length);
	}

}
