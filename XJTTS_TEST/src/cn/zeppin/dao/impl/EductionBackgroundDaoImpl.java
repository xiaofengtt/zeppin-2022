package cn.zeppin.dao.impl;

import java.util.List;

import cn.zeppin.dao.IEductionBackgroundDao;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Ethnic;

public class EductionBackgroundDaoImpl extends BaseDaoImpl<EductionBackground, Short> implements IEductionBackgroundDao {

	public List<EductionBackground> getEductionBackgroundByWight()
    {
	// TODO Auto-generated method stub
	String hql = "from EductionBackground t  order by t.id";
	List<EductionBackground> li = this.getListByHSQL(hql);
	return li;
    }
}
