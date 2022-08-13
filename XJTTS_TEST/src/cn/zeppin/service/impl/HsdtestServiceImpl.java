/**
 * 
 */
package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IHsdtestDao;
import cn.zeppin.entity.Hsdtest;
import cn.zeppin.service.IHsdtestService;

/**
 * @author Administrator
 *
 */
public class HsdtestServiceImpl extends BaseServiceImpl<Hsdtest, Integer> implements IHsdtestService {

	private IHsdtestDao hsdTestDao;

	public IHsdtestDao getHsdTestDao() {
		return hsdTestDao;
	}

	public void setHsdTestDao(IHsdtestDao hsdTestDao) {
		this.hsdTestDao = hsdTestDao;
	}

	public int getHsdTestCount(Map<String, Object> map) {

		return this.getHsdTestDao().getHsdTestCount(map);

	}

	public List<Hsdtest> getHsdTest(Map<String, Object> map, int offset, int length) {
		return this.getHsdTestDao().getHsdTest(map, offset, length);
	}

}
