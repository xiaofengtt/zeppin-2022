package cn.zeppin.service.imp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectCountDownDAO;
import cn.zeppin.dao.api.ISubjectDAO;
import cn.zeppin.dao.api.ISubjectItemTypeDAO;
import cn.zeppin.dao.api.IUserSubjectDAO;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.UserSubject;
import cn.zeppin.service.api.IUserSubjectService;
import cn.zeppin.utility.DataTimeConvert;
import cn.zeppin.utility.Dictionary;

public class UserSubjectService implements IUserSubjectService {

	private IUserSubjectDAO userSubjectDAO;
	private ISubjectCountDownDAO subjectCountDownDAO;
	private ISubjectDAO subjectDAO;
	private ISubjectItemTypeDAO subjectItemTypeDAO;

	public IUserSubjectDAO getUserSubjectDAO() {
		return userSubjectDAO;
	}

	public void setUserSubjectDAO(IUserSubjectDAO userSubjectDAO) {
		this.userSubjectDAO = userSubjectDAO;
	}

	public ISubjectCountDownDAO getSubjectCountDownDAO() {
		return subjectCountDownDAO;
	}

	public void setSubjectCountDownDAO(ISubjectCountDownDAO subjectCountDownDAO) {
		this.subjectCountDownDAO = subjectCountDownDAO;
	}

	public ISubjectDAO getSubjectDAO() {
		return subjectDAO;
	}

	public void setSubjectDAO(ISubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}

	public ISubjectItemTypeDAO getSubjectItemTypeDAO() {
		return subjectItemTypeDAO;
	}

	public void setSubjectItemTypeDAO(ISubjectItemTypeDAO subjectItemTypeDAO) {
		this.subjectItemTypeDAO = subjectItemTypeDAO;
	}

	/**
	 * 添加用户学科
	 * 
	 * @param us
	 */
	public UserSubject addUserSubject(UserSubject us) {
		return this.getUserSubjectDAO().save(us);
	}

	/**
	 * 更新
	 */
	@Override
	public UserSubject updateUserSubject(UserSubject us) {
		return this.getUserSubjectDAO().update(us);
	}

	/**
	 * 删除
	 * 
	 * @param us
	 */
	public UserSubject deleteUserSubject(UserSubject us) {
		us.setStatus(Dictionary.CLOSE);
		return this.getUserSubjectDAO().update(us);
	}

	/**
	 * 获取用户绑定学科个数
	 */
	@Override
	public int getUserSubjectCount(Map<String, Object> map) {

		return this.getUserSubjectDAO().getUserSubjectCount(map);
	}

	/**
	 * 获取用户绑定学科
	 * 
	 * @param map
	 * @return
	 */
	public List<UserSubject> getUserSubjectsByMap(Map<String, Object> map, String sort, int offset, int length) {
		return this.getUserSubjectDAO().getUserSubjectsByMap(map, sort, offset, length);
	}

	
	/**
	 * 获取用户关注学科
	 * @param uid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getUserSubjects(Integer uid) {
		// TODO Auto-generated method stub
		return this.getUserSubjectDAO().getUserSubjects(uid);
	}

	/**
	 * 保存用户关注学科
	 * @param ssoUser
	 * @param subjectIDs
	 */
	@Override
	public void saveUserSubjects(SsoUser ssoUser, Integer[] subjectIDs) {
		// TODO Auto-generated method stub
		for (Integer subjectID : subjectIDs) {
			UserSubject userSubject = this.getUserSubjectDAO().getByKey(ssoUser.getId(), subjectID);
			
			//如果不为空，则是之前关注过又删除的，只需要更改状态恢复即可
			if (userSubject != null) {
				userSubject.setStatus(Dictionary.USER_SUBJECT_STATUS_NOMAL);
				userSubject.setCreatetime(new Timestamp(System.currentTimeMillis()));
				this.getUserSubjectDAO().update(userSubject);
			}
			//如果为空，则是第一次关注，新增一个关注学科
			else{
				userSubject = new UserSubject();
				userSubject.setSsoUser(ssoUser);
				
				Subject subject = this.getSubjectDAO().get(subjectID);
//				subject.setId(subjectID);
				userSubject.setSubject(subject);
				
				userSubject.setStatus(Dictionary.USER_SUBJECT_STATUS_NOMAL);
				userSubject.setProgress(0d);
				userSubject.setBrushItemCount(0);
				
				
				Timestamp nextTestDate = this.getSubjectCountDownDAO().getNextExamTime(subjectID);
				
				if (nextTestDate != null){
					userSubject.setNextTestdayCount(DataTimeConvert.getDayDate(nextTestDate));
				}
				else {
					userSubject.setNextTestdayCount(0);
				}
				
				userSubject.setCorrectRate(0d);
				userSubject.setRankingRate(0d);
				userSubject.setCreatetime(new Timestamp(System.currentTimeMillis()));
				
				this.getUserSubjectDAO().save(userSubject);
			}
		}
	}

	/**
	 * 通过唯一索引获取用户关注学科
	 * @param uid
	 * @param subjectId
	 * @return
	 */
	@Override
	public UserSubject getByKey(int uid, int subjectId) {
		// TODO Auto-generated method stub
		return this.getUserSubjectDAO().getByKey(uid, subjectId);
	}

	/**
	 * 获取用户在某个学科下载备考进度排名
	 * @param id
	 * @param id2
	 * @return
	 */
	@Override
	public int getUserSubjectTestProgressRanking(Integer uid, Integer subjectId) {
		// TODO Auto-generated method stub
		return this.getUserSubjectDAO().getUserSubjectTestProgressRanking(uid, subjectId);
	}

	
	/**
	 * 获取用户该学科相关题型信息
	 * @param ssoUser
	 * @param subject
	 * @param isStandard
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getUserSubjectItemType(SsoUser ssoUser, Subject subject, Integer isStandard) {
		// TODO Auto-generated method stub
		return this.getSubjectItemTypeDAO().getSubjectItemType(ssoUser, subject, isStandard);
	}

}
