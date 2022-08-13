package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.dao.ICountTeachterDao;
import cn.zeppin.service.ICountTeacherService;

public class CountTeacherServiceImpl extends BaseServiceImpl<Object[], Integer>
		implements ICountTeacherService {
	private ICountTeachterDao countTeacherDao;

	public ICountTeachterDao getCountTeacherDao() {
		return countTeacherDao;
	}

	public void setCountTeacherDao(ICountTeachterDao countTeacherDao) {
		this.countTeacherDao = countTeacherDao;
	}

	@Override
	public List<Object[]> countBySex(String scode, String currentYear) {
		return countTeacherDao.countBySex(scode, currentYear);
	}

	@Override
	public List countByAge(String scode, String currentYear) {
		return countTeacherDao.countByAge(scode, currentYear);

	}

	@Override
	public List countByTeachingAge(String scode, String currentYear) {
		return countTeacherDao.countByTeachingAge(scode, currentYear);
	}

	@Override
	public List<Object[]> countBySchoolType(String scode, String currentYear) {
		return countTeacherDao.countBySchoolType(scode, currentYear);
	}

	@Override
	public List<Object[]> countByJopTitle(String scode, String currentYear) {
		return countTeacherDao.countByJopTitle(scode, currentYear);
	}

	@Override
	public List<Object[]> countByPolitice(String scode, String currentYear) {
		return countTeacherDao.countByPolitice(scode, currentYear);
	}

	@Override
	public List<String> countByIsMutiLanguage(String scode, String currentYear) {
		long countChinese = 0l;
		List<String> list = new ArrayList<String>();
		List<Object[]> countByMutiLanguage = countTeacherDao
				.countByMutiLanguage(scode, currentYear);
		List<Object[]> countByChinese = countTeacherDao.countByChinese(scode,
				currentYear);
		list.add(0, countByMutiLanguage.get(0) + "");
		for (Object[] objects : countByChinese) {
			String name = (String) objects[0];
			int count = Integer.parseInt(objects[1].toString());
			if (name != null) {
				if (name.equals("汉语")) {// 汉语统计
					list.add(1, count + "");
				} else {// 非汉语统计
					countChinese += count;
				}
			}else {// 非汉语统计
				countChinese += count;
			}
		}
		list.add(2, countChinese + "");
		return list;
	}

	@Override
	public List<Object[]> countByTeachingLanguage(String scode, String currentYear) {
		return countTeacherDao.countByTeachingLanguage(scode, currentYear);
	}

	@Override
	public List<Object[]> countByTeachingGrade(String scode, String currentYear) {
		return countTeacherDao.countByTeachingGrade(scode, currentYear);
	}

	@Override
	public List<Object[]> countByTeachingSubject(String scode, String currentYear) {
		return countTeacherDao.countByTeachingSubject(scode, currentYear);
	}

	@Override
	public List<Object[]> countByTeacherAddress(String scode, String currentYear) {
		return countTeacherDao.countByTeacherAddress(scode, currentYear);
	}

	@Override
	public List<Object[]> countByLastYear(String scode, int year) {
		return countTeacherDao.countByLastYear(scode, year);
	}

	@Override
	public List<Object[]> countByAttribute(String scode, String currentYear) {
		return countTeacherDao.countByAttribute(scode, currentYear);
	}

	@Override
	public List<Object[]> countByCurrentYear(String scode, int year) {
		return countTeacherDao.countByCurrentYear(scode, year);
	}

}
