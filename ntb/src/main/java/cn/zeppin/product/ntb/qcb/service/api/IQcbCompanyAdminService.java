/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdminMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbCompanyAdminService extends IBaseService<QcbCompanyAdmin, String> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 添加
	 * @param flag
	 * @param qa
	 * @param qca
	 * @param listMenu
	 * @param mc
	 * @throws TransactionException
	 */
	public void insertCompanyAdmin(boolean flag, QcbAdmin qa, QcbCompanyAdmin qca, List<QcbCompanyAdminMenu> listMenu, MobileCode mc) throws TransactionException;
	
	/**
	 * 修改
	 * @param qca
	 * @param listMenu
	 */
	public void update(QcbCompanyAdmin qca, List<QcbCompanyAdminMenu> listMenu);
	
	/**
	 * 删除
	 * @param qca
	 * @param listMenu
	 */
	public void delete(QcbCompanyAdmin qca, List<QcbCompanyAdminMenu> listMenu);
	
}
