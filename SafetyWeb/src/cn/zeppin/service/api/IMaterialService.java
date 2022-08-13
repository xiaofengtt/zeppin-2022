/** 
 * Project Name:CETV_TEST 
 * File Name:IMaterialService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */

package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Material;

/**
 * ClassName: IMaterialService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午4:42:39 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IMaterialService {

	/**
	 * 根据id获取材料
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:43:07 <br/>
	 * @return
	 */
	public Material getMaterialById(int id);

	/**
	 * 添加材料
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:44:24 <br/>
	 * @param material
	 */
	public void addMaterial(Material material);

	/**
	 * 跟新材料
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:45:01 <br/>
	 * @param material
	 */
	public void updateMaterial(Material material);
	
	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年8月10日 下午2:32:06 <br/> 
	 * @param material
	 */
	public void deleteMaterial(Material material);

	/**
	 * 获取材料个数
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:45:52 <br/>
	 * @param params
	 * @return
	 */
	public int getMaterialCount(HashMap<String, Object> params);

	/**
	 * 获取材料列表
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:46:47 <br/> 
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Material> getMaterials(HashMap<String, Object> params,String sorts, int offset, int length);
	
	/**
	 * 获取引用材料的试题个数
	 * @author Administrator
	 * @date: 2014年8月10日 下午2:26:45 <br/> 
	 * @param material
	 * @return
	 */
	public int getRefenceMaterialCount(Material material);

}
