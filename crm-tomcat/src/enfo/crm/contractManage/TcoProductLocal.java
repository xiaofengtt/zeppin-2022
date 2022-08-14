package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoProductVO;

public interface TcoProductLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询产品列表
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List listProcAllExt(TcoProductVO vo) throws BusiException;

	/**
		 * @ejb.interface-method view-type = "local"
		 * 添加产品信息
		 * @param vo
		 * @throws BusiException
		 * 
	 */
	void append(TcoProductVO vo) throws BusiException;

	/**
	 * 查询--分页（产品列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询---无分页（产品列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoProductVO vo) throws BusiException;

	/**
	 * 修改--产品
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--产品
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoProductVO vo) throws BusiException;

	/**
	 * 审核--产品
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void check(TcoProductVO vo) throws BusiException;

}