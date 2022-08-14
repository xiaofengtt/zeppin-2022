package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.FaqsVO;

public interface FaqsLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询全部
	 * 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listByMul(FaqsVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 
	 * 检索
	 * 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList searchFaqs(FaqsVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 新增
	 * 
	 * @param vo
	 * @throws Exception
	 * 
	 * @IN_FAQ_TITLE	NVARCHAR(60)	标题
	 * @IN_FAQ_KEYWORDS	NVARCHAR(200)	关键字
	 * @IN_FAQ_CONTENT	NVARCHAR(MAX)	内容
	 * @IN_INPUT_MAN	INT	操作员
	 * 
	 */
	Integer append(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listBySql(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 编辑
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void modi(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void delete(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 记录支持反对数
	 * 
	 * @param vo
	 * @param flag
	 */
	void countFaq(FaqsVO vo, Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询分类
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listFaqClass(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 获得json的部门
	 * @return
	 * @throws Exception
	 */
	String queryClassJosn(FaqsVO vo, java.util.Locale clientLocale) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 增加分类
	 * @param vo
	 * @throws Exception
	 */
	String addClass(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 编辑分类
	 * @param vo
	 * @throws Exception
	 */
	void modiClass(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除分类
	 * @param vo
	 * @throws Exception
	 */
	void deleteClass(FaqsVO vo) throws Exception;

}