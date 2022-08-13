package cn.zeppin.service.impl;

import java.util.Calendar;

import cn.zeppin.dao.ICountTeacherYearDao;
import cn.zeppin.entity.CountTeacherYear;
import cn.zeppin.service.ICountTeacherYearService;

public class CountTeacherYearServiceImpl extends BaseServiceImpl<CountTeacherYear, Integer> implements ICountTeacherYearService {

	private ICountTeacherYearDao countTeacherYearDao;

	public ICountTeacherYearDao getCountTeacherYearDao() {
		return countTeacherYearDao;
	}

	public void setCountTeacherYearDao(ICountTeacherYearDao countTeacherYearDao) {
		this.countTeacherYearDao = countTeacherYearDao;
	}

	@Override
	public void updateDate() {
		Integer thisYear = Calendar.getInstance().get(Calendar.YEAR);
		this.countTeacherYearDao.updateDate(thisYear);
	}
}
