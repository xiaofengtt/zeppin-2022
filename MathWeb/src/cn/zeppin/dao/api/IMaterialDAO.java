/** 
 * Project Name:CETV_TEST 
 * File Name:IMaterialDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Material;

/** 
 * ClassName: IMaterialDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午9:40:03 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface IMaterialDAO extends IBaseDAO<Material, Integer> {
	
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
