/**
 * 
 */
package cn.zeppin.service.impl;

import cn.zeppin.dao.IHsdtestscoreDao;
import cn.zeppin.entity.Hsdtestscore;
import cn.zeppin.service.IHsdtestscoreService;

/**
 * @author Administrator
 *
 */
public class HsdtestscoreServiceImpl extends BaseServiceImpl<Hsdtestscore, Integer> implements IHsdtestscoreService {
	
	private IHsdtestscoreDao hsdTestScoreDao;

	public IHsdtestscoreDao getHsdTestScoreDao() {
		return hsdTestScoreDao;
	}

	public void setHsdTestScoreDao(IHsdtestscoreDao hsdTestScoreDao) {
		this.hsdTestScoreDao = hsdTestScoreDao;
	}
	
	
	
}
