/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog;
import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.exception.ProcessException;
import cn.zeppin.product.itic.core.service.base.IBaseService;

/**
 * 
 * 
 * @author L
 * @since 2018年04月16日
 */
public interface ITnumberRelationService extends IBaseService<TnumberRelation, Integer> {
	
    /**
     * 通过主键得到对象
     * @param uuid
     * @return
     */
	public TnumberRelation get(Integer uuid);

	/**
	 * 添加
	 * @param bkController
	 * @return
	 */
	public TnumberRelation insert(TnumberRelation area);
	
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
	  * 级联处理
	  * @param tr
	  * @param tm
	  */
	 void insert(TnumberRelation tr, ToperatorMobile tm);
	 
	 /**
	  * 批处理
	  * @param list
	  * @param tm
	 * @throws ProcessException 
	  */
	 void updateProcess(List<TnumberRelation> list, ToperatorMobile tm) throws ProcessException;
	 
	 /**
	  * 批处理
	  * @param list
	  * @param tm
	 * @throws ProcessException 
	  */
	 void deleteProcess(List<TnumberRelation> list, ToperatorMobile tm) throws ProcessException;
	 
	 /**
	  * 批处理
	  * @param tm
	 * @throws ProcessException 
	  */
	 void insertProcess(ToperatorMobile tm);
	 
	 /**
	  * 批处理
	  * @param tm
	 * @throws ProcessException 
	  */
	 void insertProcess(List<TnumberRelation> list) throws ProcessException;
	 
	 /**
	  * 批处理
	  * @param list
	  * @param tm
	  * @throws ProcessException
	  */
	 void insertProcess(List<TnumberRelation> list, ToperatorMobile tm) throws ProcessException;
	 
	 /**
	  * 级联处理--日志log
	  * @param tr
	  * @param trl
	  */
	 void update(TnumberRelation tr, TnumberRelationLog trl);
	 
	 /**
	  * 级联处理--日志log
	  * @param tr
	  * @param trl
	  * @param trl2
	  */
	 void update(TnumberRelation tr, TnumberRelationLog trl, TnumberRelationLog trl2);
}
