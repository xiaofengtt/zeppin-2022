/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author L
 *
 */
public interface ITnumberRelationDAO extends IBaseDAO<TnumberRelation, Integer> {

	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);
	
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<TnumberRelation> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);
	 
	 /**
	  * 日结删除绑定关系(删除手机号修改)
	  */
	void dailyDelete();
	
	 /**
	  * 日结删除绑定关系(删除其他变动)
	  */
	void dailyDeleteOther();
	
	/**
	  * 日结增加绑定关系(新增绑定关系)
	  */
	void dailyInsert();
	
	void dailyInsert(ToperatorMobile tm);
	
	void dailyUpdate(Integer opCode);
}
