package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.DictparamVO;

public interface DictparamLocal extends IBusiExLocal {

	/** 
	 * @ejb.interface-method view-type = "local"
	 * 查询打印模板类型
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList getPrintCatalog(DictparamVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询打印模板
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList getPrintTemplate(DictparamVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改启用标志
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	void modiPrintTemplateEnable(DictparamVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改打印模板
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	void modiPrintTemplate(DictparamVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询打印要素
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList getPrintElement(DictparamVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增打印模板
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	void addPrintTemplate(DictparamVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询系统控制信息
	 */
	List listSysControl(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改系统控制信息
	 */
	void modiSysControl(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询参数CRM
	 */
	List listDictparamAll(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询参数INTRUST
	 */
	List listDictparamAllIntrust(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询参数INTRUST省份
	 */
	List listDictparamAllIntrustProvince(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除参数
	 */
	void delDictparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改参数
	 */
	void modiDictparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 20080606
	 * 可以修改ADDITIVE_VALUE的值
	 *   	@IN_SERIAL_NO
			@IN_TYPE_ID
			@IN_TYPE_NAME
			@IN_TYPE_VALUE
			@IN_TYPE_CONTENT
			@IN_INPUT_MAN
			@IN_ADDITIVE_VALUE
			@IN_AML_VALUE
	 * */
	void modiDictparam1(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 *  新增参数
	 */
	void addDictparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询系统开关Value
	 */
	List listSysControlValue(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询系统控制信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listByMul(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询系统参数信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listDictparamAll(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询报表参数信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listReportAll(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增报表参数
	 */
	void addReportparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除报表参数
	 */
	void delReportparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询科目
	 */
	IPageList listSummaryByMul(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	IPageList listFzType(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 */
	List listTotherbusiFlag(DictparamVO vo);

	/**
	 * @ejb.interface-method view-type = "local"
	 * add by tsg 2008-06-27
	 * 
	 * 更新业务收支类别信息
	 * 
	 * SP_ADD_TOTHERBUSIFLAG @IN_BOOK_CODE     INT,
							 @IN_BUSI_ID       VARCHAR(10),
							 @IN_SUB_CODE_1_1  VARCHAR(6),
							 @IN_SUB_CODE_2_1  VARCHAR(6),
							 @IN_INPUT_MAN     INT
	 * */
	void updateTotherbusiFlag(DictparamVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询外部链接
	 */
	IPageList pagelistDictparamAll(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 *  新增外部链接
	 */
	void addWebLinks(DictparamVO vo, Integer input_operatorCode) throws BusiException;

}