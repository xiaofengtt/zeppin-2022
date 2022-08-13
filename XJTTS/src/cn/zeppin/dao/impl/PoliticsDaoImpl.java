package cn.zeppin.dao.impl;

import java.util.List;

import cn.zeppin.dao.IPoliticsDao;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Politics;

public class PoliticsDaoImpl extends BaseDaoImpl<Politics, Integer> implements IPoliticsDao{

	public List<Politics> getPoliticsByWight()
    {
	// TODO Auto-generated method stub
	String hql = "from Politics t  order by t.id";
	List<Politics> li = this.getListByHSQL(hql);
	return li;
    }
}
