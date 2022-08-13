
package cn.zeppin.dao;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Specialist;

public interface ISpecialistDao extends IBaseDao<Specialist, Integer> {
	public Integer getSpecialistCount(HashMap<String,String> searchMap);
	
	public List<Specialist> getSpecialistList(HashMap<String,String> searchMap , String sortname,  String sorttype, int offset, int length);
	
	public int checkUserInfo(Object[] pars);
}
