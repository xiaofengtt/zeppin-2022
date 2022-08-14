/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdminMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbAdminService extends IBaseService<QcbAdmin, String> {
	
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
	 * 根据mobile获取信息
	 * @param mobile
	 * @return
	 */
	public QcbAdmin getByMobile(String mobile);
	
	/**
	 * 验证同手机号的企财宝管理员是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistQcbAdminByMobile(String mobile, String uuid);
	
	/**
	 * 注册
	 * @param flag
	 * @param qa
	 * @param qca
	 * @param qcAdmin
	 * @param listQcaMenu
	 */
	public void insertRegisterInfo(boolean flag, QcbAdmin qa, QcbCompanyAccount qca, QcbCompanyAdmin qcAdmin, List<QcbCompanyAdminMenu> listQcaMenu);
	
	/**
	 * 修改手机号
	 * @param flag
	 * @param qa
	 * @param qca
	 */
	public void update(boolean flag, QcbAdmin qa, QcbCompanyAccount qca);
}
