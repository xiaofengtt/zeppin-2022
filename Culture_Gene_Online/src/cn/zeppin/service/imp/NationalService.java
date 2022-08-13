package cn.zeppin.service.imp;

import java.util.List;

import cn.zeppin.dao.api.INationalDAO;
import cn.zeppin.entity.National;
import cn.zeppin.service.api.INationalService;

public class NationalService implements INationalService {

	
	private INationalDAO nationalDAO;
	
	public INationalDAO getNationalDAO() {
		return nationalDAO;
	}

	public void setNationalDAO(INationalDAO nationalDAO) {
		this.nationalDAO = nationalDAO;
	}
	
	/**
	 * 查找全部
	 */
	public List<National> findAll(){
		return this.getNationalDAO().getAll();
	}
	
	/**
	 * 获取
	 */
	public National getNational(int id){
		return this.getNationalDAO().get(id);
	}
}
