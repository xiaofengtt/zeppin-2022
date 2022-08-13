package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IMedalDAO;
import cn.zeppin.entity.Medal;
import cn.zeppin.service.api.IMedalService;

public class MedalService implements IMedalService {
	private IMedalDAO medalDAO;

	public IMedalDAO getMedalDAO() {
		return medalDAO;
	}

	public void setMedalDAO(IMedalDAO medalDAO) {
		this.medalDAO = medalDAO;
	}
	
	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:06:41 <br/>
	 * @param medal
	 */
	public void addMedal(Medal medal){
		this.getMedalDAO().addMedal(medal);
	}

	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:37:20 <br/>
	 * @param map
	 * @return
	 */
	public int getMedalCount(Map<String, Object> map){
		return this.getMedalDAO().getMedalCount(map);
	}

	/**
	 * 获取列表
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:38:26 <br/> 
	 * @param map
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Medal> getMedals(Map<String, Object> map, int offset, int length){
		return this.getMedalDAO().getMedals(map, offset, length);
	}
	
}
