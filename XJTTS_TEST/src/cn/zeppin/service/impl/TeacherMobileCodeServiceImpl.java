package cn.zeppin.service.impl;

import cn.zeppin.dao.ITeacherMobileCodeDao;
import cn.zeppin.entity.TeacherMobileCode;
import cn.zeppin.service.ITeacherMobileCodeService;
	
public class TeacherMobileCodeServiceImpl extends BaseServiceImpl<TeacherMobileCode, Integer> implements ITeacherMobileCodeService {

	private ITeacherMobileCodeDao iTeacherMobileCodeDao;

	
	public ITeacherMobileCodeDao getiTeacherMobileCodeDao() {
		return iTeacherMobileCodeDao;
	}

	public void setiTeacherMobileCodeDao(ITeacherMobileCodeDao iTeacherMobileCodeDao) {
		this.iTeacherMobileCodeDao = iTeacherMobileCodeDao;
	}

	@Override
	public TeacherMobileCode add(TeacherMobileCode t) {
		// TODO Auto-generated method stub
		return this.iTeacherMobileCodeDao.add(t);
	}

	@Override
	public TeacherMobileCode getRecordByUuid(String uuid) {
		// TODO Auto-generated method stub
		return this.iTeacherMobileCodeDao.getRecordByUuid(uuid);
	}
	

}
