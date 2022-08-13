/** 
 * Project Name:CETV_TEST 
 * File Name:MaterialService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */

package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IMaterialDAO;
import cn.zeppin.entity.Material;
import cn.zeppin.service.api.IMaterialService;

/**
 * ClassName: MaterialService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午4:42:39 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class MaterialService implements IMaterialService {

	private IMaterialDAO materialDAO;

	public IMaterialDAO getMaterialDAO() {
		return materialDAO;
	}

	public void setMaterialDAO(IMaterialDAO materialDAO) {
		this.materialDAO = materialDAO;
	}

	/**
	 * 根据id获取材料
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:43:07 <br/>
	 * @return
	 */
	public Material getMaterialById(int id) {

		return this.getMaterialDAO().get(id);
	}

	/**
	 * 添加材料
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:44:24 <br/>
	 * @param material
	 */
	public void addMaterial(Material material) {
		this.getMaterialDAO().save(material);
	}

	/**
	 * 跟新材料
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:45:01 <br/>
	 * @param material
	 */
	public void updateMaterial(Material material) {
		this.getMaterialDAO().update(material);
	}

	@Override
	public void deleteMaterial(Material material) {
		this.getMaterialDAO().delete(material);
	}

	/**
	 * 获取材料个数
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:45:52 <br/>
	 * @param params
	 * @return
	 */
	public int getMaterialCount(HashMap<String, Object> params) {
		return this.getMaterialDAO().getMaterialCount(params);
	}

	/**
	 * 获取材料列表
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:46:47 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Material> getMaterials(HashMap<String, Object> params, String sorts, int offset, int length) {
		return this.getMaterialDAO().getMaterials(params, sorts, offset, length);
	}

	@Override
	public int getRefenceMaterialCount(Material material) {
		return this.getRefenceMaterialCount(material);
	}

}
