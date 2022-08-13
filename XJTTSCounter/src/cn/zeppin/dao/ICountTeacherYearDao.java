package cn.zeppin.dao;

public interface ICountTeacherYearDao extends IBaseDao<Object[], Integer> {
	public void updateDate(Integer year);
}
